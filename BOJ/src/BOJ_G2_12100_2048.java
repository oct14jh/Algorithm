import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_G2_12100_2048 {

	private static int N, resultMax = Integer.MIN_VALUE;
	private static int[][] map;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(resultMax < map[i][j])
					resultMax = map[i][j];
			}
		}
		
		dfs(0);
		
		System.out.println(resultMax);
	}
	
	private static void dfs(int count) {
		if(count == 5) {
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					resultMax = Math.max(resultMax, map[i][j]);
				}
			}
			return;
		}
		
		int[][] copyMap = new int[N][N];
		copyingMap(copyMap, map);
		
		for(int dir = 0; dir < 4; dir++) {
			merge(dir);
			dfs(count+1);
			copyingMap(map, copyMap);
		}
	}
	
	private static void merge(int dir) {
		Queue<Integer> q = new LinkedList<>();
		
		switch(dir) {
			case 0: UP(q); break;
			case 1: DOWN(q); break;
			case 2: LEFT(q); break;
			default: RIGHT(q);
		}
	}
	
	private static void copyingMap(int[][] a, int[][] b) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				a[i][j] = b[i][j];
			}
		}
	}

	private static void UP(Queue<Integer> q) {
		for(int i = 0; i < N; i++) {
			// 세로 기준, 좌측부터 한줄
			for(int j = 0; j < N; j++) {
				// 값이 있으면, 큐 삽
				if(map[j][i] != 0)
					q.offer(map[j][i]);
				map[j][i] = 0;
			}
			
			int idx = 0;
			int popValue;
				
			while(!q.isEmpty()) {
				popValue = q.poll();
				
				if(map[idx][i] == 0)
					map[idx][i] = popValue;
				else if(map[idx][i] == popValue)
					map[idx++][i] = popValue * 2;
				else 
					map[++idx][i] = popValue;
			}
		}
	}
	private static void DOWN(Queue<Integer> q) {
		for(int i = 0; i < N; i++) {
			for(int j = N-1; j >= 0; j--) {
				if(map[j][i] != 0)
					q.offer(map[j][i]);
				map[j][i] = 0;
			}
			
			int idx = N - 1;
			int popValue;
			
			while(!q.isEmpty()) {
				popValue = q.poll();
				
				if(map[idx][i] == 0)
					map[idx][i] = popValue;
				else if(map[idx][i] == popValue) 
					map[idx--][i] = popValue * 2;
				else 
					map[--idx][i] = popValue;
			}
		}
	}
	private static void LEFT(Queue<Integer> q) {
		for(int i = 0; i < N; i++) {
			for(int j = N - 1; j >= 0; j--) {
				if(map[i][j] != 0)
					q.offer(map[i][j]);
				map[i][j] = 0;
			}
			
			int idx = N - 1;
			int popValue;
			
			while(!q.isEmpty()) {
				popValue = q.poll();
				
				if(map[i][idx] == 0)
					map[i][idx] = popValue;
				else if(map[i][idx] == popValue) 
					map[i][idx--] = popValue * 2;
				else 
					map[i][--idx] = popValue;
				
			}
		}
	}
	private static void RIGHT(Queue<Integer> q) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] != 0)
					q.offer(map[i][j]);
				map[i][j] = 0;
			}
			
			int idx = 0;
			int popValue;
			
			while(!q.isEmpty()) {
				popValue = q.poll();
				
				if(map[i][idx] == 0) 
					map[i][idx] = popValue;
				else if(map[i][idx] == popValue) 
					map[i][idx++] = popValue * 2;
				else 
					map[i][++idx] = popValue;
			}
		}
	}
}

// 1번 이동 => 상하좌우 중 한방향으로 이동(끝까지?)
// 같은 값을 가진 블록끼리 충돌하면, 하나로 합쳐진다(값이 2배되네)
// 1번 이동에서 이미 합쳐진 블록은 더이상 못합친다.