import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


public class BOJ_G3_16236_아기상어 {
	
	static class Pos {
		int row;
		int col;
		int distance;
		
		public Pos(int row, int col, int distance) {
			super();
			this.row = row;
			this.col = col;
			this.distance = distance;
		}
	}
	
	private static int[] dr = {-1, 1, 0, 0};
	private static int[] dc = {0, 0, -1, 1};
	private static int N, resultTime;
	
	private static List<Pos> fishList;
	private static int sharkRow, sharkCol, sharkSize, sharkEat;
	
	private static int[][] map;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				int value = Integer.parseInt(st.nextToken());
				
				map[i][j] = value;
				
				if(value == 9) {
					sharkRow = i;
					sharkCol = j;
					sharkSize = 2;
					map[i][j] = 0;
				}
			}
		}
		
		while(true) {			
			bfs();
			
			if(fishList.isEmpty())
				callMomShark();
			
			eating();
		}
		
	}
	
	private static void bfs() {
		fishList = new ArrayList<>();
		
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		Pos current = new Pos(sharkRow, sharkCol, 0);
		
		q.offer(current);
		visited[current.row][current.col] = true;
		
		while(!q.isEmpty()) {
			current = q.poll();
			
			for(int move = 0; move < 4; move++) {
				int nr = current.row + dr[move];
				int nc = current.col + dc[move];
				
				if(isLineOut(nr,nc) || visited[nr][nc])
					continue;
				
				if(map[nr][nc] > 0 && map[nr][nc] < sharkSize) {
					q.offer(new Pos(nr, nc, current.distance + 1));
					visited[nr][nc] = true;
					
					fishList.add(new Pos(nr, nc, current.distance + 1));
				}
				else if(map[nr][nc] == 0 || map[nr][nc] == sharkSize) {
					q.offer(new Pos(nr, nc, current.distance + 1));
					visited[nr][nc] = true;
				}
			}
		}
	}

	private static void eating() {
		Pos delFish = fishList.get(0);
		
		for(Pos fish : fishList) {
			if(delFish.distance > fish.distance)
				delFish = fish;
			
			if(delFish.distance == fish.distance) {
				if(delFish.row > fish.row)
					delFish = fish;
				else if(delFish.row == fish.row) {
					if(delFish.col > fish.col)
						delFish = fish;
				}
			}
		}
		
		resultTime += delFish.distance;
		sharkEat++;
		map[delFish.row][delFish.col] = 0;
		
		if(sharkEat == sharkSize) {
			sharkSize++;
			sharkEat = 0;
		}
		
		sharkRow = delFish.row;
		sharkCol = delFish.col;
	}
	
	private static void callMomShark() {
		System.out.println(resultTime);
		System.exit(0);
	}
	
	private static void searchShark(int[][] map) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] == 9) {
					sharkRow = i;
					sharkCol = j;
					map[i][j] = 0;
				}
			}
		}
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= N)
			return true;
		return false;
	}
}



