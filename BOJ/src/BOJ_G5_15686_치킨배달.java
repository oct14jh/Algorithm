import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_G5_15686_치킨배달 {
	static class Pos{
		int r,c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	private static int[][] map;
	private static List<Pos> homeList = new ArrayList<>();
	private static List<Pos> chickenList = new ArrayList<>();

	private static int[] visitedIdx;
	private static int N, M, result=Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 1)
					homeList.add(new Pos(i,j));
				else if(map[i][j] == 2)
					chickenList.add(new Pos(i,j));
			}
		}
		visitedIdx = new int[M];
		
		combination(0, 0);
		System.out.println(result);
	}
	
	private static void combination(int idx, int count) {
		if(count == M) {
			int shorttestD = 0;

			for(Pos home : homeList) 
				shorttestD += searching(home);
			
			
			result = Math.min(result, shorttestD);
			
			return;
		}
		
		for(int i = idx; i < chickenList.size(); i++) {
			visitedIdx[count] = i;
			combination(i+1, count+1); // idx+1이아니다.. 제발... =>시간초과
		}
	}
	
	private static int searching(Pos home) {
		int tempResult = Integer.MAX_VALUE;
		int temp = 0;
		
		for(int idx : visitedIdx) {
			temp = calDistance(home, chickenList.get(idx));
			tempResult = Math.min(temp, tempResult);
		}
		return tempResult;
	}
	
	private static int calDistance(Pos h, Pos c) {
		return Math.abs(h.r - c.r)+Math.abs(h.c - c.c);
	}
}

