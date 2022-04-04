import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_G5_14503_로봇청소기 {
	
	private static class Pos {
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	
	private static class Robot extends Pos {
		int d;

		public Robot(int r, int c, int d) {
			super(r, c);
			this.d = d;
		}
	}
	
	//로봇방향기준 : 북동남서
	private static int[] dr = {-1,0,1,0};
	private static int[] dc = {0,1,0,-1};
	
	private static int N, M, totalCleanCnt=0;
	private static int[][] map;
	
	private static Robot robot;
	private static boolean[][] visited;
	private static boolean flag = false;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		visited = new boolean[N][M];
		
		st = new StringTokenizer(br.readLine(), " ");
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		
		robot = new Robot(r,c,d);
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		process(robot);
		
		System.out.println(totalCleanCnt);
		br.close();
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= M)
			return true;
		return false;
	}
	
	private static void process(Robot robot) {
		if(map[robot.r][robot.c] == 0 && !visited[robot.r][robot.c]) {
			totalCleanCnt++;
			visited[robot.r][robot.c] = true; 
		}
		
		int originDir = robot.d;
		int move = 0;
		for(move = 0; move < 4; move++) {
			int nd = (robot.d + 4 - 1) % 4;
			int nr = robot.r + dr[nd];
			int nc = robot.c + dc[nd];
			// 현위치 기준, 좌측이 청소안한 공간이라면,
			// 청소기 위치 이동 + 재귀 + break;?
			if(!isLineOut(nr,nc) && map[nr][nc] == 0 && !visited[nr][nc]) {
				process(new Robot(nr, nc, nd));
				break;
			}else {
				robot.d = nd;
			}
		}
		if(move==4) {
			int backD = (originDir + 2) % 4;
			int nr = robot.r + dr[backD];
			int nc = robot.c + dc[backD];
			
			if(!isLineOut(nr,nc) && map[nr][nc] == 1) {
				return;
			}
			else if(!isLineOut(nr,nc) && map[nr][nc] == 0) {
				process(new Robot(nr, nc, originDir));
				return;
			}
		}else
			return;
	}
}

/**
 * 인덱스 0,0 으로 접근
 * 
 * 알고리즘은 다음과 같다.
 * 1. 로봇의 위치 확인
 * 2. 현위치 청소
 * 3. 탐색
 * 	- 현위치 기준 좌측이 청소안한 빈공간이라면, 왼쪽회전 후, 1칸 전진 그리고 1. 로 점프
 *  - 이외의 경우라면, 왼쪽회전을한다. (4번 돈다면, 로봇위치 기준 뒤쪽이 벽인지 확인)
 *  	- 뒤쪽이 벽이면, 탐색 종료
 *  	- 뒤쪽이 벽이 아니라면, 뒤로 후진 
 */

