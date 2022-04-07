import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_S2_2644_촌수계산 {

	private static int N, A, B, M, result = -1;
	private static boolean[] visited;
	private static int[][] relation;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		relation = new int[N+1][N+1];
		visited = new boolean[N+1];
		
		st = new StringTokenizer(br.readLine(), " ");
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		
		M = Integer.parseInt(br.readLine());
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			relation[x][y] = 1;
			relation[y][x] = 1;
		}
		
		process(A, 0);
		System.out.println(result);
	}
	
	private static void process(int person, int depth) {
		visited[person] = true;
		
		if(person == B) {
			result = depth;
			return;
		}
		
		for(int index=1; index < N+1; index++) {
			if(!visited[index] && relation[person][index] == 1) {
				visited[index] = true;
				process(index, depth+1);
				visited[index] = false;
			}
		}
	}
}
