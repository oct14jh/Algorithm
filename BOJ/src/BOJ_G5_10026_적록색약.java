import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class BOJ_G5_10026_적록색약 {
	private static class Point{
		int row;
		int col;
		public Point() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Point(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}
		
	}
	private static boolean[][] visitedX;
	private static boolean[][] visitedO;
	private static int N;
	private static char[][] map;
	private static int countX, countO;
	private static int[] dr = {-1, 1, 0, 0};
	private static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());

		map = new char[N][N];
		visitedX = new boolean[N][N];
		visitedO = new boolean[N][N];
		for(int i = 0; i < N; i++) {
			String temp = br.readLine();
			for(int j = 0; j < temp.length(); j++) {
				char tmp = temp.charAt(j);
				map[i][j] = tmp;
			}
		}
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(!visitedX[i][j]) {	// 적록색약아닌
					BFSX(new Point(i,j), visitedX);
					
					countX++;
				}
				if(!visitedO[i][j]) {	// 적록색약인
					BFSO(new Point(i,j), visitedO);
					print();
					countO++;
				}
			}
		}
		
		System.out.println(countX + " " + countO);
		br.close();
	}
	private static void BFSX(Point p, boolean[][] visit) {
		Queue<Point> q = new LinkedList<>();
		Point current = p;
		q.offer(current);
		visit[current.row][current.col] = true;
		
		while(!q.isEmpty()) {
			current = q.poll();
			
			for(int m = 0; m < 4; m++) {
				int nr = current.row + dr[m];
				int nc = current.col + dc[m];
				
				if(isLineOut(nr, nc) || visit[nr][nc])
					continue;
				
				if(map[nr][nc] == map[current.row][current.col]) {
					q.offer(new Point(nr,nc));
					visit[nr][nc] = true;
				}
			}
		}
	}
	
	// 적색과 녹색이 같고, 블루만
	private static void BFSO(Point p, boolean[][] visit) {
		Queue<Point> q = new LinkedList<>();
		Point current = p;
		q.offer(current);
		visit[current.row][current.col] = true;
		
		while(!q.isEmpty()) {
			current = q.poll();
			
			for(int m = 0; m < 4; m++) {
				System.out.println("m s");
				int nr = current.row + dr[m];
				int nc = current.col + dc[m];
				
				if(isLineOut(nr, nc) || visit[nr][nc])
					continue;
				
				if((map[nr][nc] == 'R') && (map[current.row][current.col]!='B')) {
					q.offer(new Point(nr,nc));
					visit[nr][nc] = true;
				}
				if((map[nr][nc] == 'G') && (map[current.row][current.col]!='B')) {
					q.offer(new Point(nr,nc));
					visit[nr][nc] = true;
				}
				if((map[nr][nc] == 'B') && (map[current.row][current.col]=='B')) {
					q.offer(new Point(nr,nc));
					visit[nr][nc] = true;
				}
			}
		}
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= N)
			return true;
		return false;
	}
	
	private static void print() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(visitedO[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
