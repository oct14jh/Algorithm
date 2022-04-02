import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_S4_1913_달팽이 {
	// 북동남
	private static int[] dr = {-1,0,1,0};
	private static int[] dc = {0,1,0,-1};
	private static int N, specificNum;
	private static int[][] map;
	private static int ROW, COL;
	private static int[] specificXY = new int[2];
	private static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		specificNum = Integer.parseInt(br.readLine());
		
		ROW = COL = N/2;
		map = new int[N][N];
		map[ROW][COL] = 1;
		process();
		
		mapping2();
		//mapping();
		System.out.print(sb.toString());
		System.out.println(specificXY[0] + " " + specificXY[1]);
	}
	
	private static void process() {
		int value = 2;
		int d = 0, len = 1;
		while(true) {
			for(int step = 0; step < len; step++) {
				if(map[ROW][COL] == specificNum) {
					specificXY[0] = ROW + 1;
					specificXY[1] = COL + 1;
				}
				ROW += dr[d];
				COL += dc[d];
				
				if(isLineOut(ROW,COL)) {
					if(ROW < 0) ROW = 0;
					if(COL < 0) COL = 0;
					return;
				}
				
				map[ROW][COL] = value;
				value++;
			}
			
			if(d%2 == 1) len++;
			
			if(++d == 4) d = 0;
		}
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= N)
			return true;
		return false;
	}
	
	private static void mapping() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	private static void mapping2() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				sb.append(map[i][j]+" ");
			}
			sb.append("\n");
		}
	}
}

/**
 * 단순,포문돌때마다 프린트 해주면 시간초과 뜰 것이다.
 * 프린트 메소드 자체도 수행 시간이 존재하는데, 그걸 이제 999999사이즈의 2차배열을 수행한다고 해보자
 * 고로, 이럴때에는 StringBuilder 사용할 것을 추천.
 * */
