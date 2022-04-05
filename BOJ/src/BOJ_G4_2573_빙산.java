import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_G4_2573_빙산 {
	private static class Pos {
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	
	private static int[] dr = {0, 0, 1, -1};
	private static int[] dc = {1, -1, 0, 0};
	private static int N, M, years = 0;
	private static boolean is2Group=false, isAllMelt=false;
	private static int[][] map;
	private static ArrayList<Pos> iceList = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				int height = Integer.parseInt(st.nextToken());
				
				//성능을 위해, 빙산 리스트를 따로 모아준다.
				if(height > 0) 
					iceList.add(new Pos(i,j));
				
				map[i][j] = height;
			}
		}
		
		// 기저조건 내, 무한반복
		while(true) {
			// 기저조건1 : 2그룹 이상으로 나눠진 순간, 알고리즘 종
			if(is2Group) {
				break;
			}
			// 기저조건2 : 전부 녹은 경우, 0 출력
			if(iceList.size() == 0) {
				years = 0;
				break;
			}
			
			// 1년이 흐르고
			years++;
			
			// 빙하가 녹음
			iceList = melting();
			printMap();
			// 녹은 것 처리하면, 더이상 빙산이 존재하지않으면, 끝내기위함(is2Group으로 그냥 써버림)
			if(iceList.size() == 0) 
				isAllMelt = true;
			
			// 그룹처리
			if(!is2Group)
				is2Group = grouping();
			
			if(isAllMelt && !is2Group) {
				years = 0;
				break;
			}
		}
		System.out.println(years);
	}
	
	// 녹는 것 처리 => 녹은 것 처리하면서,살아있는 빙산의 리스트를 새로이 반환
	// 주의사항 : 카피맵을 써야함. 안그러면 실시간으로 빙산녹은것도 적용되기에 문제발생.
	private static ArrayList<Pos> melting() {
		ArrayList<Pos> newIceList = new ArrayList<>();
		int[][] copyMap = new int[N][M];
		// 카피맵 : copyMap = map; 이렇게하면 같은 포인터기에 문제 발생
		copyMap = copyingMap(copyMap);
		
		// 빙산리스트의 각 빙산들을 체크 (카피맵 최초의 맵 기준으로 처리)
		for(Pos checkIce : iceList) {
			int r = checkIce.r;
			int c = checkIce.c;
			
			int checkHeight = map[r][c];
			int nearSeaCount = 0;
			for(int move = 0; move < 4; move++) {
				int nr = r + dr[move];
				int nc = c + dc[move];
				
				// map은 실시간 맵이 아니기에, 영향을주지않는다.
				if(!isLineOut(nr,nc) && map[nr][nc] == 0)
					nearSeaCount++;
			}
			if(nearSeaCount >= checkHeight) {
				checkHeight = 0;
				copyMap[r][c] = checkHeight;
			}
			else {
				copyMap[r][c] -= nearSeaCount;
				newIceList.add(new Pos(r, c));
			}
		}
		map = copyMap;
		return newIceList;
	}
	
	private static boolean grouping() {
		int groupCount = 0;
		// 방문처리 배열은 매 그루핑 처리때마다 초기화
		boolean[][] visited = new boolean[N][M];
		
		// 맵을 돌건데, 최초 찍히는 빙산 기준으로 한번 돌기 시작한다.
		// 2번이상 찍혔다는건, 그루핑할영역이 2개이상이라는 
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] > 0 && !visited[i][j]) {
					dfs(i, j, visited); //인접지점 끝가지 체크하기 위한 dfs
					groupCount++;
				}
			}
		}
		if(groupCount >= 2)
			return true;
		return false;
	}
	
	private static void dfs(int r, int c, boolean[][] visited) {
		visited[r][c] = true;
		
		for(int move = 0; move < 4; move++) {
			int nr = r + dr[move];
			int nc = c + dc[move];
			if(!isLineOut(nr,nc) && !visited[nr][nc] && map[nr][nc] > 0)
				dfs(nr, nc, visited);
		}
	}
	
	private static int[][] copyingMap(int[][] copyMap) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		return copyMap;
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= M)
			return true;
		return false;
	}

	private static void printMap() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("---");
	}
}

/**
 * 알고리즘
 * 0. 입력
 * 1. 현재 맵 기준 녹이기
 * 2. 녹아든 상태의 맵에서 그룹핑 처리
 * 3. 만약, 그룹핑이 2개 이상이면 해당년도 출력
 * 4. 전부 녹을때까지 분리되지않으면 0 출
 * 
 */