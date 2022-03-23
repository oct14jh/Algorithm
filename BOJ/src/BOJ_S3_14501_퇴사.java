import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_S3_14501_퇴사 {
	
	private static int N, result = Integer.MIN_VALUE;
	private static int[][] arr;
	private static boolean[] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		
		visited = new boolean[N];
		arr = new int[N][2];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int time = Integer.parseInt(st.nextToken());
			int price = Integer.parseInt(st.nextToken());
			
			arr[i][0] = time;
			arr[i][1] = price;
		}
		
		dfs(0, 0);
		
		System.out.println(result);
	}
	

	private static void dfs(int today, int totalPrice) {
		if(today >= N ) {
			result = Math.max(result, totalPrice);
			return;
		}
		
		if(today + arr[today][0] <= N)
			dfs(today+arr[today][0], totalPrice+arr[today][1]);
		else
			dfs(today+arr[today][0], totalPrice);
		
		dfs(today+1, totalPrice);
	}

}