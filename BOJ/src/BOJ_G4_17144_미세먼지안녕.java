import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class BOJ_G4_17144_미세먼지안녕 {
	static int[][] map, copy;
	static int R, C, T, result;
	
	static int[] airCleaner = new int[2];
	static int cntAC = 0;		
	
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		R = Integer.parseInt(st.nextToken());	
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		map = new int[R][C];
		copy = new int[R][C];
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				
				if(map[i][j] == -1)		// 공기청정기 위치 체크(행만 기억하면, 열은 0~)
					airCleaner[cntAC++] = i;
			}
		}
		
		for(int time = 0; time < T; time++) {	// t초간 과정(미세먼지확산,공기청정)
			
			// 미세먼지 확장 처리 (1-미세먼지확장된부분 처리, 2-처리된부분을 원배열에 적용
			//미세먼지 확장---1
			for(int i = 0; i < R; i++) {
				for(int j = 0; j < C; j++) {
					if(map[i][j] > 0)		// 미세먼지 있는 경우
						spread(i, j);		// 미세먼지 확장
				}
			}
			// 미세먼지 확장 다 처리한 copy부분을 이제 map과 함께 처리---2
			for(int i = 0; i < R; i++) {
				for(int j = 0; j < C; j++) {
					if(map[i][j]==-1)
						continue;
					map[i][j] = copy[i][j];
				}
			}
			
//			print(map);
//			System.out.println("@@@@@");
			// 이제는 공기청정기 기능 처리
			for(int i = 0; i < 2; i++) 
				refresh(airCleaner[i], i);
			
			
			for (int i = 0; i < R; i++) {
				for (int j = 0; j < C; j++) {
					copy[i][j] = 0;
				}
			}
		}
		result = sum();
		System.out.println(result+2);
		br.close();
	}
	
	private static void spread(int row, int col) {
		int nr=0, nc=0;
		int count = 0;
		int expansionValue = map[row][col] / 5;
		for(int move = 0; move < 4; move++) {
			nr = row + dr[move];
			nc = col + dc[move];
			if(!isLineOut(nr, nc)) {
				copy[nr][nc] += expansionValue;	// 확장부분에 확장값만큼 사이즈 커짐
				count++;
			}
		}
		copy[row][col] += (map[row][col] - (expansionValue*count)); // 기존 미세먼지 부분에선 사이즈 작아짐
	}

	private static boolean isLineOut(int row, int col) {	// 배열밖+공기청정기 부분인경우를 라인밖으로 인식
		if(row<0 || col<0 || row>=R || col>= C || map[row][col] == -1)
			return true;
		return false;
	}
	
	  private static void refresh(int row, int part) {
	      if(part==0) {   // 0이면 윗부분 (반시계방향)
	         int UL=map[0][0], DL=map[row][0], UR=map[0][C-1], DR=map[row][C-1];
	         for(int c=C-1; c>=1; c--)    //반시계1-밑가로
	            map[row][c] = map[row][c-1];
	         for(int r=0; r<=row-1; r++)    //반시계2-우세로
	            map[r][C-1] = map[r+1][C-1];
	         for(int c=0; c<C-1; c++)   //반시계3-윗가로(<---)
	            map[0][c] = map[0][c+1];
	         for(int r=row; r>=1; r--)   //반시계4-좌세로
	            map[r][0] = map[r-1][0];
	         map[row-1][C-1] = DR;
	         map[0][C-2] = UR;
	         map[1][0] = UL;
	         map[row][1] = 0;
	         map[row][0] = DL;
	      }
	      else {         // 1이면 아랫부분 (시계방향)
	         int UL=map[row][0], DL=map[R-1][0], UR=map[row][C-1], DR=map[R-1][C-1];
	         for(int c=C-1; c>=1; c--)
	            map[row][c] = map[row][c-1];
	         for(int r=R-1; r>=row+1; r--)
	            map[r][C-1] = map[r-1][C-1];
	         for(int c=0; c<C-1; c++)
	            map[R-1][c] = map[R-1][c+1];
	         for(int r=row; r<R-1; r++)
	            map[r][0] = map[r+1][0];
	         map[row+1][C-1]= UR;
	         map[R-1][C-2] = DR;
	         map[R-2][0] = DL;
	         map[row][1] = 0;
	         map[row][0] = UL;
	      }
	   }
	
	private static int sum() {
		int temp = 0;
		for(int[] i : map) {
			for(int j : i)
				temp += j;
		}
		return temp;
	}
	
	private static void print(int[][] arr) {
		for(int i=0; i<R;i++) {
			for(int j=0; j<C;j++) {
				System.out.print(arr[i][j] + " " );
			}
			System.out.println();
		}
		System.out.println();
	}
}