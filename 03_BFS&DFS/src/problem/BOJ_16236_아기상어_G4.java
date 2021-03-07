package problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_16236_아기상어_G4 {
	
	/* 위치별 계산을 위한 포인터 (위치, 거리) */
	private static class Point {
		int row, col;	// 위치
		int distance;	// 이동한 거리
		public Point() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Point(int row, int col, int distance) {
			super();
			this.row = row;
			this.col = col;
			this.distance = distance;
		}
		
	}
	
	/* 아기상어의 위치 및 크기 */
	private static int sharkRow, sharkCol, sharkSize=2;
	
	/* 공간 상 존재하는 물고기 */
	private static ArrayList<Point> fishList;
	
	/* 공간 크기 및 공간 배열 */
	private static int N;
	private static int[][] map;
	
	/* 4방 탐색 (상, 하, 좌, 우) */
	private static int[] dr = {-1, 1, 0, 0};
	private static int[] dc = {0, 0, -1, 1};
	
	/* 먹은 물고기 수 (아기상어 크기만큼 먹으면 리셋되는 거 생각해두기) */
	private static int eatCount;
	
	/* 출력할 정답 */
	private static int answer;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 9) { // 아기상어 위치 파악
					sharkRow = i;
					sharkCol = j;
					map[i][j] = 0;	// 아기상어가 존재한 곳은 9가 아닌 0으로 처리
				}
			}
		}
		
		while(true) {	// 먹을 수 있는 물고기가 존재하는 한 무한반복 (먹은 물고기 위치에서 BFS)
			BFS(sharkRow, sharkCol);
			if(fishList.size() == 0)	// 더이상 먹을 물고기가 없다면
				findSharkMom();			// 엄마 상어 부르기
			priorityEating();
		}
	}

	/* 먹을 물고기 탐색 */
	private static void BFS(int sharkRow, int sharkCol) {
		boolean[][] visited = new boolean[N][N];
		Queue<Point> q = new LinkedList<>();		
		fishList = new ArrayList<Point>();		
		
		q.offer(new Point(sharkRow, sharkCol, 0));
		visited[sharkRow][sharkCol] = true;
		
		while(!q.isEmpty()) {
			Point shark = q.poll();
			
			for(int move = 0; move < 4; move++) {
				int nr = shark.row + dr[move];
				int nc = shark.col + dc[move];
				
				if(isLineOut(nr, nc) || visited[nr][nc])	// 공간 벗어났거나, 방문한 공간이면 continue
					continue;
				
				if(map[nr][nc] > 0 && map[nr][nc] < sharkSize) {	// 물고기가 존재하되 + 상어크기보다 작을 때
					q.offer(new Point(nr, nc, shark.distance + 1));	// 지나가기에 distance + 1 처리 및 아기상어 위치 이동
					fishList.add(new Point(nr, nc, shark.distance + 1));	// 먹을 수 있는 물고기 등록
					visited[nr][nc] = true;
				}
				
				else if(map[nr][nc] == sharkSize || map[nr][nc] == 0) {	// 물고기크기=아기상어크기 혹은 0 => 단순 지나가기만 하기
					q.offer(new Point(nr, nc, shark.distance + 1));		// 지나가기에 distance + 1만 해준다
					visited[nr][nc] = true;
				}
			}
		}
	}

	
	/* 공간 벗어났는지 확인 유무 */
	private static boolean isLineOut(int row, int col) {
		if(row < 0 || col < 0 || row >= N || col >= N)
			return true;
		return false;
	}


	/* 먹은 물고기 처리 */
	private static void priorityEating() {
		Point temp = fishList.get(0);	// 먹을 수 있는 물고기 첫번째 선택
		for (int i = 1; i < fishList.size(); i++) {	// 이후 반복문으로 처리
			if (fishList.get(i).distance < temp.distance)	// 임의 물고기보다 거리 더 짧은 물고기를 처리할 물고기로 선택
				temp = fishList.get(i);
			
			if(temp.distance == fishList.get(i).distance) {	// 거리가 같을 때에는 '상' 그리고 '좌' 고려하기
				if(temp.row > fishList.get(i).row)	// 더 높다는 말은 row값이 더 작다는 말.
					temp = fishList.get(i);
				
				if(temp.row == fishList.get(i).row) {	// 높이도 같을 땐, 좌측에 있는거 우선하기
					if(temp.col > fishList.get(i).col)	// 더 좌측에 있다는 말은 col값이 더 작다는 말.
						temp = fishList.get(i);
				}
			}
		}
		// 먹을 물고기 중 처리할 물고기를 채택하고나면,
		answer += temp.distance;	// 거리만큼 체크(1거리 이동=1초 이므로, 거리값이 곧 출력할 이동시간)
		eatCount++;	
		map[temp.row][temp.col] = 0;	// 처리한 물고기의 자리는 '0'으로 처리
		
		if(eatCount == sharkSize) {	// 먹은 물고기 수가 상어 크기와 일치할 땐, 상어크기 증가 및 먹은 수 초기화
			sharkSize++;
			eatCount = 0;
		}
		sharkRow = temp.row;		// 먹은(처리한) 물고기의 자리가 곧 아기상어의 자리
		sharkCol = temp.col;
	}
	
	/* 더이상 먹을 물고기 없을 때, 엄마상어 호출 */
	private static void findSharkMom() {
		System.out.println(answer);
		System.exit(0);
	}


}
