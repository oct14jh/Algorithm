import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_S2_2644_촌수계산 {
	private static int N, A, B, M, result;
	private static int[][] relations;
	private static BufferedReader br;
	private static StringTokenizer st;
	public static void main(String[] args) throws IOException {
		
		processInput();
		
		processOutput();
		System.out.println(result);
		
	}
	
	private static void processInput() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine(), " ");
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		
		relations = new int[M][2];
		
		for(int i = 0; i < M; i++) {
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			relations[i][0] = x; // 자식
			relations[i][1] = y; // 부모
		}
	}

	private static void processOutput() {
		
	}
}
