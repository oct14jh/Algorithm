package aaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_5567_결혼식_S1 {
	private static int n, m;
	private static ArrayList<Integer> list[];
	private static int result;
	private static int[] visited;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());
		visited = new int[n+1];

		list = new ArrayList[n+1];
		for(int i = 0; i < n+1; i++) {
			list[i] = new ArrayList<>();
		}
		

		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine().trim(), " ");
			int from = Integer.parseInt(st.nextToken().trim());
			int to = Integer.parseInt(st.nextToken().trim());
			list[from].add(to);
			list[to].add(from); // 이건 안해줘도 상관없지 않나??
		}
		
		bfs(1);
		System.out.println(result);
		br.close();
	}
	private static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		int current = start;
		q.offer(current);
		visited[current] = 1;
		
		while(!q.isEmpty()) {
			current = q.poll();
			
			for(int i : list[current]) {
				if(!visited[i]) {
					q.offer(i);
					visited[i] = true;
					result++;
				}
			}
		}
	}
}
