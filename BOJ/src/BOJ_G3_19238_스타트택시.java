import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_G3_19238_스타트택시 {
	private static class Pos {
		int r;
		int c;
		int no;
		public Pos(int r, int c, int no) {
			super();
			this.r = r;
			this.c = c;
			this.no = no;
		}
	}
	
	private static int[] dr = {0, 0, 1, -1};
	private static int[] dc = {1, -1, 0, 0};
	private static int N, M, fuel;
	private static int[][] map;
	private static Pos Taxi;
	private static ArrayList[] startEndList;
	private static int[] shortestList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		fuel = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				int wall = Integer.parseInt(st.nextToken());
				if(wall == 1)
					map[i][j] = -2;
				else
					map[i][j] = -1;
			}
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		Taxi = new Pos(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1, -3);
		
		startEndList = new ArrayList[M];
		shortestList = new int[M];
		for(int person = 0; person < M; person++) {
			startEndList[person] = new ArrayList<Pos>();
			
			st = new StringTokenizer(br.readLine(), " ");
			
			int startR = Integer.parseInt(st.nextToken()) - 1;
			int startC = Integer.parseInt(st.nextToken()) - 1;
			int endR = Integer.parseInt(st.nextToken()) - 1;
			int	endC = Integer.parseInt(st.nextToken()) - 1;
			
			startEndList[person].add(new Pos(startR, startC, person));
			startEndList[person].add(new Pos(endR, endC, person));
			map[startR][startC] = person;
			map[endR][endC] = person;
		}
		
		fuel = process();
		System.out.println(fuel);
	}
	
	private static int process() {
		boolean driveIsFail = false;
		int count = 0;
		while(true) {
			Taxi = nearClient(Taxi);
			if(Taxi == null) {
				return -1;
			}
			driveIsFail = driving(Taxi);
			if(driveIsFail) {
				return -1;
			}
			if(++count == M) {
				return fuel;
			}
		}
	}
	private static boolean driving(Pos Taxi) {
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		Pos current = Taxi;
		int fuelDec = 0;
		q.offer(current);
		visited[current.r][current.c] = true;
		while(!q.isEmpty()) {
			current = q.poll();
			if(Taxi.no == current.no) {
				break;
			}
			
			for(int move = 0; move < 4; move++) {
				int nr = current.r + dr[move];
				int nc = current.c + dc[move];
				
				if(!isLineOut(nr,nc) && !visited[nr][nc] && map[nr][nc] != -2) {
					q.offer(new Pos(nr,nc,-1));
					visited[nr][nc] = true;
				}
			}
			if(++fuelDec < fuel) {
				return true;
			}
		}
		fuel -= fuelDec;
		fuel += fuelDec * 2;
		return false;
	}
	
	private static Pos nearClient(Pos Taxi) {
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		Pos current = null;
		int distance = 0;
		q.offer(Taxi);
		visited[Taxi.r][Taxi.c] = true; 
		while(!q.isEmpty()) {
			int sizes = q.size();
			ArrayList<Pos> candidate = new ArrayList<>();
			distance++;
			for(int i = 0; i < sizes; i++) {
				current = q.poll();

				for(int move = 0; move < 4; move++) {
					int nr = current.r + dr[move];
					int nc = current.c + dc[move];
					
					if(!isLineOut(nr,nc) && !visited[nr][nc] && map[nr][nc] != -2) {
						if(map[nr][nc] == -1) {
							q.offer(new Pos(nr,nc, map[nr][nc]));
							visited[nr][nc] = true;
						}else {
							candidate.add(new Pos(nr,nc, map[nr][nc]));
						}
					}
				}
				if(candidate.size()>=1)
					break;
			}
			if(candidate.size() >= 2) {
				//오름차순
				Collections.sort(candidate, new Comparator<Pos>() {
					public int compare(Pos o1, Pos o2) {
						if(o1.r < o2.r) return 1;
						if(o1.r == o2.r) {
							if(o1.c < o2.c) return 1;
							else return -1;
						}
						return -1;
					}
				});
			}
			current = candidate.get(0);
			fuel -= distance;
			if(fuel <= 0) {
				fuel = -1;
				return null;
			}
			break;
		}
		return current;
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= N)
			return true;
		return false;
	}
}


/**
 * 0. 입력처리
 * 	- 시작점(사람), 도착점(도착지) 각 리스트 존재
 * 1. 승객고르기
 * 	- 택시 현위치에서 가장 가까운 승객 체크하기 
 *  - 최단경로 같은애들끼리는 행/열 작은것 우선
 * 2. 이동
 * 	- 해당승객위치로 이동 (연료 바로소비하기)
 *  - 해당 승객위치에서 그 승객의 도착지까지 이동거리만큼 연료소모
 *  - 승객태운시점부터 도착까지의 연료소모한 양을 *2 충전
 * 3. 종료조건
 * 	- 모든 손님 다 이동시켰고, 마지막연료충전까지 했을 때,남은연료량 구하기.
 * 	- 이동도중 연료 0 or 모든 손님 못태운 경우 or 승객을 데려다줄수 없는 경우 -1을 출력
 *  - 연료충전 전에 연료 0 이면, 실패한게 아니다. (이말은도착즉시0되었을때, 충전해서 갠찬타는거아닌가..?)
 */
/**
풀이

*/