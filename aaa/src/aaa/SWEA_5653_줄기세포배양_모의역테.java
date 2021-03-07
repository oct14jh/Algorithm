package aaa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA_5653_줄기세포배양_모의역테 {
	private static class Node {
		int x;
		int state;
		public Node() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Node(int x, int state) {
			super();
			this.x = x;
			this.state = state;
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		@Override
		public String toString() {
			return "Node [x=" + x + ", state=" + state + "]";
		}
	}
	private static int N, M, K;
	private static Node[][] map = new Node[401][401];
	private static int result;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int testCase = 1; testCase <= T; testCase++) {
			result = 0;
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken()); // 세로라 하는데,,, 걍 row
			M = Integer.parseInt(st.nextToken()); // 가로라 하는데,,, 걍 col
			K = Integer.parseInt(st.nextToken()); // 배양 시간
			
			Arrays.fill(map, new Node(0, -1));
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int j = 0; j < M; j++) {
					map[200+i][200+j].x = Integer.parseInt(st.nextToken());
					map[200+i][200+j].state = 1;	// 안씀=-1, 죽음=0, 비활성화=1, 활성화=2
				}
			}
			
			for(int k = 0; k < K; k++) {
				bfs(200, 200);
			}
			
			System.out.println("#" + testCase + result);
		}
	}
	
	private static void bfs(int i, int j) {
		int[] dr = {-1, 1, 0, 0};
		int[] dc = 
		Queue<Node> q = new LinkedList<>();
		Node current = map[i][j];
		
		while(!q.isEmpty()) {
			
		}
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= 401 || c >= 401)
			return true;
		return false;
	}
}
