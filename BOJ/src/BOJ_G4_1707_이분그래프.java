import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_G4_1707_이분그래프 {
	
	private static boolean BipartiteState;
	
	private static List<Integer> graph[];
	private static int[] color;
	private static int K, V, E;	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		K = Integer.parseInt(br.readLine().trim());
		
		for(int testCase = 0; testCase < K; testCase++) {
			BipartiteState = true; // 우선 무조건 이분그래프라 생각한다.
			String result = null; // YES, NO 결과출력을 위한 변수
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());

			// 그래프 각 인덱스별로 배열리스트 선언
			graph = new ArrayList[V];
			for(int i = 0; i < V; i++) {
				graph[i] = new ArrayList<>();
			}
			
			// 색깔은 각 정점별의 색깔을 구분할 때 쓰이는데, int형으로 쓴다. (0=색깔안갖춤, 1=RED, -1=BLUE ,,,, 이렇게한 이유는
			/* 
			 * 진짜 이게 개쩌는 생각인데,,, 색깔 구분은 INT형으로 한다.
			 * 0은 색깔없는 상태 
			 * 1은 RED인 상태
			 * -1은 BLUE인 상태
			 * 1과 -1로 둔 이유는 둘이 색깔다르다는것을 표현하고자 했다..(서로 다른색깔 RED와 BLUE가 더해지면 0이다.. 나머지 경우는 색깔이 무조건 같은 경우니..(2혹은 -2)
			 *  */
			color = new int[V];
			Arrays.fill(color, 0); // 이건 배열 값 초기화 상태를 0으로 다 집어넣기 위함이다.

			
			
			// 그래프 우선 연결... 인덱스로 접근하기에 from,to는 -1씩해줘야만 한다.
			for(int edge = 0; edge < E; edge++) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken()) - 1;
				int to = Integer.parseInt(st.nextToken()) - 1;
				
				graph[from].add(to);
				graph[to].add(from);
			}
			
			// 연결되어 있는 그래프를 이분 그래프인지 구분하고자 색깔 넣기 작업
			for(int i = 0; i < V; i++) {
				if(!BipartiteState) {
					result = "NO";
					break;
				}
				if  (color[i] == 0) {
					result = "YES";
					BipartiteState = bfs(i, 1);
				}
//				else {
//					result = "YES";
//					BipartiteState = bfs(i, 1);
//				}
			}
			System.out.println(result);
		}
	}

	private static boolean bfs(int start, int initColor) {
		Queue<Integer> q = new LinkedList<>();
		int current = start;
		color[start] = initColor;
		q.offer(current);
		while(!q.isEmpty()) {
			current = q.poll();
			
			for(int check : graph[current]) {
				if(color[check] == 0) {
					int changeColor = color[current] * -1;
					color[check] = changeColor;
					q.offer(check);
					
				}
				else if(color[current] + color[check] != 0 ) { // 색깔 같을 때,,, 0이 뜨는경우는 오로지 색깔다를때만이다...
					return false;
				}
			}
			
		}
		return true;
	}
	
}
