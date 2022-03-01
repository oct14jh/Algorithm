import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_G5_7569_토마토 {
	static class Pos {
		int row;
		int col;
		int height;
		public Pos(int row, int col, int height) {
			super();
			this.row = row;
			this.col = col;
			this.height = height;
		}
	}
	private static int[] dr = {0, 0, -1, 1, 0, 0};
	private static int[] dc = {0, 0, 0, 0, -1, 1};
	private static int[] dh = {-1, 1, 0, 0, 0, 0};
	
	private static int M,N,H, result=Integer.MAX_VALUE, tomatoCnt;
	private static List<Pos> tomatoList = new ArrayList<>();
	private static int[][][] map;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		map = new int[H][N][M];
		
		for(int height = 0; height < H; height++) {
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for(int j = 0; j < M; j++) {
					map[height][i][j] = Integer.parseInt(st.nextToken());
					if(map[height][i][j] == 1) 
						tomatoList.add(new Pos(i, j, height));
					if(map[height][i][j] == 0)
						tomatoCnt++;
				}
			}
		}
		
		result = bfs();
		if(tomatoCnt > 0)
			result = -1;
		System.out.println(result);
	}
	
	private static int bfs() {
		Queue<Pos> q = new LinkedList<>();
		boolean[][][] visited = new boolean[H][N][M];
		int level = 0;
		for(Pos p : tomatoList) {
			q.offer(p);
			visited[p.height][p.row][p.col] = true;
		}
		
		while(!q.isEmpty()) {
			int size=q.size();
			
			for(int i=0; i<size; i++) {
				Pos current = q.poll();
				
				for(int move = 0; move < 6; move++) {
					int nr = current.row + dr[move];
					int nc = current.col + dc[move];
					int nh = current.height + dh[move];
					
					if(isLineOut(nr,nc,nh) || visited[nh][nr][nc])
						continue;
					
					if(map[nh][nr][nc] == -1)
						continue;
					
					if(map[nh][nr][nc] == 0) {
						q.offer(new Pos(nr,nc,nh));
						visited[nh][nr][nc] = true;
						tomatoCnt--;
					}
				}
			}
			level++;
		}
		return level-1;
		
	}
	private static boolean isLineOut(int r, int c, int h) {
		if(r < 0 || c < 0 || h < 0 || r >= N || c >= M || h >= H)
			return true;
		return false;
	}
}
