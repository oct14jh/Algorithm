import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_S1_2178_미로탐색 {
	static class Pos {
		int row;
		int col;
		
		public Pos(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	
	private static int[] dr = {-1, 1, 0, 0};
	private static int[] dc = {0, 0, -1, 1};
	private static int N, M, result=Integer.MAX_VALUE;
	private static int[][] map;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		for(int i = 0; i < N; i++) {
			String[] temp = br.readLine().trim().split("");
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(temp[j]);
			}
		}
		
		bfs(0,0);
		System.out.println(result);
		
	}
	
	private static void bfs(int r, int c) {
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];
		Pos current = new Pos(r,c);
		int depth = 0;
		
		q.offer(current);
		visited[r][c] = true;
		
		while(!q.isEmpty()) {
			int sizes = q.size();
			depth++; // 이러면, 미리 층을 올려주고 시작하는거다. (지금부터 체크한 것들은 이레벨이다라고)
			
			for(int level = 0; level < sizes; level++) {
				current = q.poll();
				if(current.row == N-1 && current.col == M-1) { 
					result = depth;
					return;
				}
				for(int move = 0; move < 4; move++) {
					int nr = current.row + dr[move];
					int nc = current.col + dc[move];
					
					if(isLineOut(nr,nc) || visited[nr][nc] || map[nr][nc] == 0)
						continue;
					
					if(map[nr][nc] == 1) {
						q.offer(new Pos(nr,nc));
						visited[nr][nc] = true;
					}
				}
			}
//			depth++; // 이러면, 동 계층(너비)를 다 체크한 후, 얘네들의 레벨을 증가시켜준것.
		}
		
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= M)
			return true;
		return false;
	}

}


