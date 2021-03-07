package aaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_5014_스타트링크_G5 {
	private static int F, S, G, U, D;
	private static int result;
	private static boolean[] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		F = Integer.parseInt(st.nextToken()); // 건물 최대 높이
		S = Integer.parseInt(st.nextToken()); // 시작 높이
		G = Integer.parseInt(st.nextToken()); // 도착 높이
		U = Integer.parseInt(st.nextToken()); // 업
		D = Integer.parseInt(st.nextToken()); // 다운
		
		/* 1~F 층이어야한다.. 그래서 0~F-1 인덱스 접근 가능토록하면 된다. */
		visited = new boolean[F];
		
		if(S == G) { // 시작점 도착점 애당초 같다면 바로 출력
			result = 0;
		}else { // 시작점 도착점 다르다면 bfs 돌리기
			result = BFS(S);
		}
		
		/* 출력 */
		if(result >= 0)
			System.out.println(result);
		else
			System.out.println("use the stairs");
		br.close();
	};
	
	private static int BFS(int start) {
		Queue<Integer> q = new LinkedList<>();
		int current = start;
		//
		
	}
}