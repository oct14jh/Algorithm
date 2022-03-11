import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_G4_20058_마법사상어와파이어스톰 {

	private static int[] dr = {-1, 0, 1, 0};
	private static int[] dc = {0, 1, 0, -1};
	
	private static int N, Q, SIZE;
	private static int[][] map;
	private static int[] L;
	private static int resultTotal, resultSectionSize;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		SIZE = (int)Math.pow(2, N);
		map = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < SIZE; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine(), " ");
		for(int step = 0; step < Q; step++) {
			int L = Integer.parseInt(st.nextToken());
			process(L);
		}
		
	}

	private static void process(int L) {

	}
	
	private static void rotate() {
		
	}
	
	private static void melt() {
		
	}

	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= SIZE || c >= SIZE)
			return true;
		return false;
	}
}

/**
1. 2^N 크기만큼의 구역을 나누고,해당 구역을 회전한다.(90도)
2. 전체 탐색하면서, 얼음을 녹인다(-1)
	-한 지점에서 인접(동서남북) 중 3곳 이상이 얼음이 있으면 그곳을 녹인다.
	-얼음가진인접칸이 2개 이하면(3개미만), 자신의 칸의 얼음을 녹인다.
3. 1-2과정을 Q단계만큼 수행
4. 제일 큰덩어리와 얼음의 합을 출
*/