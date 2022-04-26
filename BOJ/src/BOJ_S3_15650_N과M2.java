import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_S3_15650_N과M2 {
	private static int N, M;
	
	private static int[] input, output;
	private static boolean[] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		input = new int[N];
		output = new int[M];
		visited = new boolean[N];
		
		for(int i = 0; i < N; i++) 
			input[i] = i + 1;
		
		Permutation(0);
	}
	
	private static void Permutation(int count) {
		if(count == M) {
			for(int i = 0; i < M; i++) 
				System.out.print(output[i] + " ");
			System.out.println();
			return;
		}
		
		for(int i = 0; i < N; i++) {
			if(visited[i])
				continue;
			if((count >= 1) && (output[count-1] > input[i]))
				continue;
			output[count] = input[i];
			visited[i] = true;
			Permutation(count + 1);
			visited[i] = false;
		}
	}
}
