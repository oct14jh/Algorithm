import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_G5_14502_연구소 {
	
	/* 위치값 row, col을 한 세트로 처리하기 위해 Point 생성 */
	private static class Point {
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
	
	/* 행-N, 열-M 그리고 2차원 배열 map*/
	private static int N, M;
	private static int[][] map;

	/* 처음 맵의 벽과 바이러스 위치를 리스트로 저장 */
	private static List<Point> initBlock = new ArrayList<>();
	private static List<Point> initVirus = new ArrayList<>();
	
	
	/* 최대 안전영역개수 출력 */
	private static int result = Integer.MIN_VALUE;
	
	
	public static void main(String[] args) throws IOException {
		/* 입력 처리 */
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 2) { // 초기 바이러스 위치를 리스트에 저장
					initVirus.add(new Point(i, j));
				}
			}
		}
		
		// 3개의 벽 설치하는 위치 경우의 수 조합하기
		block3Combination(0, 0, 0);
		
		// 결과 출력
		System.out.println(result);
		br.close();
	}
	
	
	/* 빈 방중 3개의 벽을 설치하는 경우의 수 구하기 
	 * 카운트만 체킹하면 된다.. 데이터는 전역배열 접근하면되기때문에 
	*/
	private static void block3Combination(int row, int col, int count) {	
		if(count == 3) { // 3개의 벽이 다 설치되었다면,
			virusingBFS(); // 해당 맵에서 바이러스 퍼지기
			return;
		}
		
		for(int i = row; i < N; i++) {
			for(int j = col; j < M; j++) {
				if(map[i][j] != 0)
					continue;
				map[i][j] = 1;
				block3Combination(i, j + 1, count + 1);
				map[i][j] = 0;
			}
			col = 0; // 이중포문의 내부 포문에서 j+1로써, col이 최댓값 상태이므로 i반복문이 다음 돌기 전에 col = 0 해주는 것이다.
		}
	}
	
	private static void virusingBFS() {
		/* 안전영역 개수 체크 => 3개의 벽 설치된 각 맵 조합마다 안전영역이 계산될 것이다... */
		int safety = 0;
		
		/* 상하좌우 탐색을 위한 배열 2개 */
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};
		
		/* BFS에 필요한 큐와 visited배열 */
		Queue<Point> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];
		
		/* 맵처리를 위한 카피용 */
		int[][] copy = new int[N][M];
//		copy = map;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				copy[i][j] = map[i][j];
			}
		}
	
		// 처음의 바이러스 놈들 큐에 삽입시켜주기.. (방문처리까지)
		for(Point temp : initVirus) {
			q.offer(temp);
			visited[temp.row][temp.col] = true;
		}
		
		while(!q.isEmpty()) {
			Point current = q.poll();
			
			for(int m = 0; m < 4; m++) {
				int nr = current.row + dr[m];
				int nc = current.col + dc[m];
				
				if(isLineOut(nr,nc) || visited[nr][nc])
					continue;
				if(copy[nr][nc] == 0) {
					copy[nr][nc] = 2;
					q.offer(new Point(nr,nc));
					visited[nr][nc] = true;
				}
			}
		}
		

		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(copy[i][j] == 0)
					safety++;
			}
		}
		
		if(result < safety) {
			result = safety;
		}
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= M)
			return true;
		return false;
	}
}
