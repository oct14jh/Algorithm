import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_G5_5014_스타트링크 {
	
	private static int F, S, G, U, D, result=Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		F = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		G = Integer.parseInt(st.nextToken());
		U = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		//DFS(S, 0);
		BFS(0);
		
		if(result == Integer.MAX_VALUE)
			System.out.println("use the stairs");
		else
			System.out.println(result);
	}
	
	// BFS알고리즘
	private static void BFS(int count) {
		Queue<Integer> q = new LinkedList<>();
		boolean[] visited = new boolean[F+2];
		int current = S;
		q.offer(current);
		visited[current] = true;
		while(!q.isEmpty()) {
			// 최단 경로 = > 뎁스
			int size = q.size();
			
			// 뎁스 크기만큼만 처리
			for (int i = 0; i < size; i++) {
				current = q.poll();
				
				if(current == G) {
					result = Math.min(result, count);
					return;
				}
				
				if(!floorIsOut(current + U) && !visited[current+U]) {
					int tempUpFloor = current + U;
					q.offer(tempUpFloor);
					visited[tempUpFloor] = true;
				}
				if(!floorIsOut(current - D) && !visited[current-D]) {
					int tempDownFloor = current - D;
					q.offer(tempDownFloor);
					visited[tempDownFloor] = true;
				}
			}
			count++;
		}
	}
	
	// DFS알고리즘 => 이걸로는 풀수없다.
	private static void DFS(int currentFloor, int count) {
		// 탐색 중,최소버튼수 넘으면 더이상 탐색할 필요없
		if(count > result)
			return;
		
		// 현재층이 도착층이면, 탐색 종료
		if(currentFloor == G) {
			result = Math.min(result, count);
			return;
		}
		
		if(!floorIsOut(currentFloor+U))
			DFS(currentFloor+U, count+1);
		if(!floorIsOut(currentFloor-D))
			DFS(currentFloor-D, count+1);
	}
	
	// 건물 벗어나는 경
	private static boolean floorIsOut(int floor) {
		if(floor < 1 || floor > F)
			return true;
		return false;
	}
}

/**
 * F층으로 이루어진 고층 건물, 스타트링크위치는 G층
 * 강호는 s층,
 * 
 * U/D
 * 
 * S층에서 G층에 도착하려면, 버튼을 적어도 몇 번 눌러야하는가.
 * 100층 건물에서 2층에 있는데 1층도착해야한다면,
 * 
 * 1층 ~ F층 내
 * S층 기준 +U, -D 통해서 G층 도착하려면 최소 몇번 눌러야하는지
 * 
 * S층 기준으로 DFS
 * 	- 탐색 중인데 벌써 최소 버튼 개수 넘으면 바로해당 탐색가지치기
 *  - 탐색중인데, 다음작업하려는 0층 혹은 F+1층가려하면 바로 탐색가지치
 * */

// DFS로는 풀수없다 => DFS는 최단 경로 보장을 모한다.


// BFS메소드 내용은 최단 경로 구할 때, 거의 공식이다.
