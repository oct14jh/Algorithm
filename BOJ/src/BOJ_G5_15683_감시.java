import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_G5_15683_감시 {

	static class CCTV {
		int row;
		int col;
		int type;
		
		public CCTV(int row, int col, int type) {
			this.row = row;
			this.col = col;
			this.type = type;
		}
	}
	
	private static int[] dr = {0,1,0,-1};
	private static int[] dc = {1,0,-1,0}; 
	private static int N, M, blind, result = Integer.MAX_VALUE;
	private static int[][] map;
	private static List<CCTV> cctvList = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] >= 1 && map[i][j] <= 5)
					cctvList.add(new CCTV(i, j, map[i][j]));
				if(map[i][j] == 0) 
					blind++;
			}
		}
		
		dfs(0, 0, map);
		
		System.out.println(result);
	}
	
	// 카메라체크
	private static void dfs(int cctvIdx, int checkTotal, int[][] tempMap) {
		if(cctvIdx == cctvList.size()) {
			result = Math.min(result, blind-checkTotal);
			return;
		}
		
		CCTV currentCCTV = cctvList.get(cctvIdx);
		switch(currentCCTV.type) {
			case 1: REC1(cctvIdx, checkTotal, tempMap); break;
			case 2: REC2(cctvIdx, checkTotal, tempMap); break;
			case 3: REC3(cctvIdx, checkTotal, tempMap); break;
			case 4: REC4(cctvIdx, checkTotal, tempMap); break;
			default: REC5(cctvIdx, checkTotal, tempMap);
		}
		
		
	}
	
	// 촬영
	private static void REC1(int cctvIdx, int checkTotal, int[][] tempMap) {
		CCTV currentCCTV = cctvList.get(cctvIdx);
		for(int move = 0; move < 4; move++) {
			int[][] copyMap = copyMapping(tempMap);
			int deleteBlind = 0;

			int nr = currentCCTV.row + dr[move];
			int nc = currentCCTV.col + dc[move];
			
			while(!isLineOut(nr,nc)) {
				if(copyMap[nr][nc] == 0) {
					copyMap[nr][nc] = 9;
					deleteBlind++;
				}
				
				nr += dr[move];
				nc += dc[move];
			}
			dfs(cctvIdx + 1, checkTotal + deleteBlind, copyMap);
		}
	}
	private static void REC2(int cctvIdx, int checkTotal, int[][] tempMap) {
		CCTV currentCCTV = cctvList.get(cctvIdx);
		for(int move = 0; move < 4; move+=2) {
			int[][] copyMap =copyMapping(tempMap);
			int deleteBlind = 0;
			
			int nr1 = currentCCTV.row + dr[move];
			int nc1 = currentCCTV.col + dc[move];
			int nr2 = currentCCTV.row + dr[move+1];
			int nc2 = currentCCTV.col + dc[move+1];
			
			while(!isLineOut(nr1,nc1)) {
				if(copyMap[nr1][nc1] == 0) {
					copyMap[nr1][nc1] = 9;
					deleteBlind++;
				}
				nr1 += dr[move];
				nc1 += dc[move];
			}
			while(!isLineOut(nr2, nc2)) {
				if(copyMap[nr2][nc2] == 0) {
					copyMap[nr2][nc2] = 9;
					deleteBlind++;
				}
				nr2 += dr[move+1];
				nc2 += dc[move+1];
			}
			dfs(cctvIdx + 1, checkTotal + deleteBlind, copyMap);
		}
	}
	
	// ㄱ,ㄴ자 =>dr,dc의 인덱스를 잘 고려해보자 => 뱅글 돌면인덱스를 %로 접근 할 수 있다.
	private static void REC3(int cctvIdx, int checkTotal, int[][] tempMap) {
		CCTV currentCCTV = cctvList.get(cctvIdx);
		
		for(int move = 0; move < 4; move++) {
			int[][] copyMap = copyMapping(tempMap);
			int deleteBlind = 0;
			
			int nr1 = currentCCTV.row + dr[move];
			int nc1 = currentCCTV.col + dc[move];
			int nr2 = currentCCTV.row + dr[(move+1)%4];
			int nc2 = currentCCTV.row + dc[(move+1)%4];
			
			while(!isLineOut(nr1,nc1)) {
				if(copyMap[nr1][nc1] == 0) {
					copyMap[nr1][nc1] = 9;
					deleteBlind++;
				}
				nr1 += dr[move];
				nc1 += dc[move];
			}
			while(!isLineOut(nr2,nr2)) {
				if(copyMap[nr2][nc2] == 0) {
					copyMap[nr2][nc2] = 9;
					deleteBlind++;
				}
				nr2 += dr[(move+1)%4];
				nc2 += dc[(move+1)%4];
			}
			dfs(cctvIdx + 1, checkTotal + deleteBlind, copyMap);
		}
	}
	// ㅗ,ㅜ자 => dr,dc인덱스 잘 고려 => 뱅글 돌면서 인덱스 접근 시, %로 접근 가
	private static void REC4(int cctvIdx, int checkTotal, int[][] tempMap) {
		CCTV currentCCTV = cctvList.get(cctvIdx);
		
		for(int move = 0; move < 4; move++) {
			int[][] copyMap = copyMapping(tempMap);
			int deleteBlind = 0;
			
			int nr1 = currentCCTV.row + dr[move];
			int nc1 = currentCCTV.col + dc[move];
			int nr2 = currentCCTV.row + dr[(move+1)%4];
			int nc2 = currentCCTV.col + dc[(move+1)%4];
			int nr3 = currentCCTV.row + dr[(move+2)%4];
			int nc3 = currentCCTV.col + dc[(move+2)%4];
			
			while(!isLineOut(nr1,nc1)) {
				if(copyMap[nr1][nc1] == 0) {
					copyMap[nr1][nc1] = 9;
					deleteBlind++;
				}
				nr1 += dr[move];
				nc1 += dc[move];
			}
			while(!isLineOut(nr2,nr2)) {
				if(copyMap[nr2][nc2] == 0) {
					copyMap[nr2][nc2] = 9;
					deleteBlind++;
				}
				nr2 += dr[(move+1)%4];
				nc2 += dc[(move+1)%4];
			}
			while(!isLineOut(nr3,nr3)) {
				if(copyMap[nr3][nc3] == 0) {
					copyMap[nr3][nc3] = 9;
					deleteBlind++;
				}
				nr3 += dr[(move+2)%4];
				nc3 += dc[(move+2)%4];
			}
			dfs(cctvIdx + 1, checkTotal + deleteBlind, copyMap);
		}
	}
	private static void REC5(int cctvIdx, int checkTotal, int[][] tempMap) {
		CCTV currentCCTV = cctvList.get(cctvIdx);
		
		int[][] copyMap = copyMapping(tempMap);
		int deleteBlind = 0;
		
		for(int move = 0; move < 4; move++) {
			int nr = currentCCTV.row + dr[move];
			int nc = currentCCTV.col + dc[move];
			
			while(!isLineOut(nr,nc)) {
				if(copyMap[nr][nc] == 0) {
					copyMap[nr][nc] = 9;
					deleteBlind++;
				}
				nr += dr[move];
				nc += dc[move];
			}
		}
		dfs(cctvIdx+1, checkTotal+deleteBlind, copyMap);
	}

	// map copy
	private static int[][] copyMapping(int[][] tempMap) {
		int[][] copy = new int[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				copy[i][j] = tempMap[i][j];
			}
		}
		return copy;
	}
	
	// 촬영 종료 조건 (경계밖 or 벽)
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= M) return true;
		if(map[r][c] == 6) return true;
		return false;
	}

}