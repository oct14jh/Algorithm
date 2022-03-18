import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_G3_20057_마법사상어와토네이도 {
	
	static class Pos {
		int r;
		int c;
		
		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	
	//서남동
	private static int[] dr = {0, 1, 0, -1};
	private static int[] dc = {-1, 0, 1, 0};
	
	private static int N, result;
	private static int[][] map;
	private static Pos Tornado;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		Tornado = new Pos(N/2, N/2);
		
		result = process(Tornado);
		System.out.println(result);
	}
	
	private static int process(Pos tornado) {
		int exitSand = 0;
		Queue<Pos> tornadoList = new LinkedList<>();
		
		tornadoList = tornadoRoute(tornado);
		
		exitSand = tornadoWind(tornadoList);
		
		return exitSand;
	}
	
	private static int tornadoWind(Queue<Pos> tornadoList) {
		int total = 0;
		for(Pos tornado : tornadoList) {
			total += wind(tornado.r, tornado.c);
		}
		
		return total;
	}
	
	private static int wind(int row, int col) {
		int exitSand = 0;
		int sandOrigin = map[row][col];
		int sandY = sandOrigin;
		int sand1 = sandOrigin * 1 / 100;
		int sand2 = sandOrigin * 2 / 100;
		int sand5 = sandOrigin * 5 / 100;
		int sand7 = sandOrigin * 7 / 100;
		int sand10 = sandOrigin * 10 / 100;
	
		if(!isLineOut(row-1, col+1)) {map[row-1][col+1] += sand1; map[row][col] -= sand1;}
		if(isLineOut(row-1, col+1)) {exitSand += sand1; map[row][col] -= sand1;}
		
		if(!isLineOut(row+1, col+1)) {map[row+1][col+1] += sand1; map[row][col] -= sand1;}
		if(isLineOut(row+1, col+1)) {exitSand += sand1; map[row][col] -= sand1;}

		if(!isLineOut(row-2, col)) {map[row-2][col] += sand2; map[row][col] -= sand2;}
		if(isLineOut(row-2, col)) {exitSand += sand2; map[row][col] -= sand2;}

		if(!isLineOut(row+2, col)) {map[row+2][col] += sand2; map[row][col] -= sand2;}
		if(isLineOut(row+2, col)) {exitSand += sand2; map[row][col] -= sand2;}

		if(!isLineOut(row, col-2)) {map[row][col-2] += sand5; map[row][col] -= sand5;}
		if(isLineOut(row, col-2)) {exitSand += sand5; map[row][col] -= sand5;}

		if(!isLineOut(row-1, col)) {map[row-1][col] += sand7; map[row][col] -= sand7;}
		if(isLineOut(row-1, col)) {exitSand += sand7; map[row][col] -= sand7;}

		if(!isLineOut(row+1, col)) {map[row+1][col] += sand7; map[row][col] -= sand7;}
		if(isLineOut(row+1, col)) {exitSand += sand7; map[row][col] -= sand7;}

		if(!isLineOut(row-1, col-1)) {map[row-1][col-1] += sand10; map[row][col] -= sand10;}
		if(isLineOut(row-1, col-1)) {exitSand += sand10; map[row][col] -= sand10;}
		
		if(!isLineOut(row+1, col-1)) {map[row+1][col-1] += sand10; map[row][col] -= sand10;}
		if(isLineOut(row+1, col-1)) {exitSand += sand10; map[row][col] -= sand10;}

		if(!isLineOut(row, col-1)) {map[row][col-1] += map[row][col]; map[row][col] = 0;}
		if(isLineOut(row, col-1)) {exitSand += map[row][col]; map[row][col] = 0;}
	
		return exitSand;
	}
	
	private static Queue<Pos> tornadoRoute(Pos tornado) {
		Queue<Pos> temp = new LinkedList<>();
		int row = tornado.r;
		int col = tornado.c;
		int power = 1;

		// 뱅글뱅글 무한 경계벗어나기 전까지)
		for(int move = 0; !isLineOut(row, col); move++) {
			int nr = row + (dr[move%4] * power);
			int nc = col + (dc[move%4] * power); 
			
			if(isLineOut(nr,nc) && nr==0 && nc < 0) {
				temp.add(new Pos(0,0));
				break;
			}
			if(isLineOut(nr,nc))
				break;
			
			// 서남,동북 세트별 길이 +1씩 증
			if((move+1)%2 == 0)
				power++;

			// 토네이도 위치값 추가 그리고 다음 체크할 토네이도의 현위치 업데이트
			temp.add(new Pos(nr, nc));
			row = nr;
			col = nc;
		}
		
		return temp;
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= N)
			return true;
		return false;
	}
	
}
