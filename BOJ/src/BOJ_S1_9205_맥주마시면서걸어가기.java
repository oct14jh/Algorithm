import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_S1_9205_맥주마시면서걸어가기 {
	private static class Pos {
		int r, c;
		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	private static int t, n;
	private static Pos player, festival;
	private static Pos[] CU;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		t = Integer.parseInt(br.readLine());
		
		for(int testCase = 0; testCase < t; testCase++) {
			n = Integer.parseInt(br.readLine());
			CU = new Pos[n+1];
			
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			player = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			
			for(int i = 0; i <= n; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				CU[i] = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}

//			st = new StringTokenizer(br.readLine(), " ");
//			festival = new Pos(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			
			String result = process();
			System.out.println(result);
		}
	}
	
	private static String process() {
		Queue<Pos> q = new LinkedList<>();
		boolean[] visited = new boolean[n+1];
		Pos current = player;
		q.offer(current);
		while(!q.isEmpty()) {
			current = q.poll();
			if((current.r == CU[n].r) && (current.c == CU[n].c))
				return "happy";
			
			for(int i = 0; i <= n; i++) {
				if(!visited[i] && !isOverDistance(current, CU[i])) {
					q.add(CU[i]);
					visited[i] = true;
				}
			}
		}
		return "sad";
	}
	
	private static boolean isOverDistance(Pos a, Pos b) {
		if(Math.abs(a.r-b.r) + Math.abs(a.c-b.c) <= 1000)
			return false;
		return true;
	}
}

/**
출발은 상근이집 [맥주한박스들고=20개]
50미터 당 1병씩 마심 (즉, 50미터를 가기위해선 직전에 1병있어야함)

무조건 20병이 맥스다.
편의점 나선 직후, 1병 또마심(50미터안갔지만)

편의점,상근집,페스티벌 좌표 찍힌다.
맥주를 들고가서 잘 도착할 수 있는 프로그램

테케 수 
for(int t = 0; t < tc; t++) {
	편의점 수n
	for(int nn 0~n;n++){
		편의점 좌표(x,y)
	}
	상근이집 좌표
	락페스티벌좌표
}
*/