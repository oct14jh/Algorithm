import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_S1_2468_안전영역 {
	
	private static class Pos {
		int r, c;
		public Pos(int r, int c) {
			this.r = r;
			this.c = c;
			
		}
	}
	private static int[] dr = {0, 0, 1, -1};
	private static int[] dc = {1, -1, 0, 0};
	private static int N, result=0;
	private static ArrayList<Integer> heightList = new ArrayList<>();
	private static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		heightList.add(0);
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j< N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(!heightList.contains(map[i][j]))
					heightList.add(map[i][j]);
			}
		}
		
		for(int rain : heightList) {
			process(rain);
		}
		System.out.println(result);
	}
	
	private static void process(int rain) {
		ArrayList<Pos> safeSpaceList = new ArrayList<>();
		
		safeSpaceList = raining(rain);
		if(safeSpaceList == null)
			return;
		if(safeSpaceList.size() == N*N) {
			result = Math.max(result, 1);
			return;
		}
		result = Math.max(result, bfs(safeSpaceList, rain, 0));
	}
	
	private static int bfs(ArrayList<Pos> safeSpaceList, int rain, int spaceCnt) {
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		
		for(Pos check : safeSpaceList) {
			if(visited[check.r][check.c])
				continue;

			Pos current = check;
			q.offer(current);
			visited[current.r][current.c] = true;
			
			while(!q.isEmpty()) {
				current = q.poll();
				
				for(int move = 0; move < 4; move++) {
					int nr = current.r + dr[move];
					int nc = current.c + dc[move];
					
					if(!isLineOut(nr,nc) && !visited[nr][nc] && map[nr][nc] > rain) {
						q.offer(new Pos(nr,nc));
						visited[nr][nc] = true;
					}
				}
			}
			spaceCnt++;
		}
		return spaceCnt;
	}
	
	
	private static ArrayList<Pos> raining(int rain) {
		ArrayList<Pos> temp = new ArrayList<>();
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] > rain)
					temp.add(new Pos(i,j));
			}
		}
		return temp;
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= N)
			return true;
		return false;
	}
}


/**
 *그 지역에 많은 비가 내렸을 때, 물에 잠기지 않는 안전한 영역 최대 몇개가 만들어지는지..
 *
 *N*N 맵 크기 [각 칸은 높이]
 *비가 내려서, 얼마의 높이 이하인 모든 지점은 물에 잠기면 회색처리..
 *안전영역이라 함은 물에 잠기지 않는 지점들이 상하좌우로 인접해있으며, 그 크기가 최대인 영역
 *
 *0. 입력
 *1. 각 높이 오름차순 정렬해서
 *해당 높이만큼 차례대로 자른다...
 */
