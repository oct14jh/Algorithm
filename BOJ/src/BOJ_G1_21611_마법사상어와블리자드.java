import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_G1_21611_마법사상어와블리자드 {
	private static class Pos { 
		int row;
		int col;

		public Pos(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}
	}
	
	//상하좌우
	private static int[] dr = {-1,1,0,0};
	private static int[] dc = {0,0,-1,1};
	
	//반시계로테이션(좌,하,우,상)
	private static int[] rr = {0, 1, 0, -1};
	private static int[] rc = {-1, 0, 1, 0};
	
	private static int N, M, answer;
	private static int[][] map;
	private static int[][] magic;
	private static int[] bombMarbleCnt = new int[3];
	private static int[] shark = new int[2];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		magic = new int[M][2];
		
		shark[0] = shark[1] = ((N+1)/2) - 1;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j< N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()) - 1;
			}
		}
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			magic[i][0] = Integer.parseInt(st.nextToken()) - 1;
			magic[i][1] = Integer.parseInt(st.nextToken());
		}
		
		// 마법 M번 시전
		for(int i = 0; i < M; i++) {
			process(magic[i][0], magic[i][1]);
		}
		
		answer = resulting();
		System.out.println(answer);
	}
	
	// 마법 알고리즘 (이동-폭발..)
	private static void process(int d, int s) {
		List<Pos> delMarbleList = new ArrayList<>();
		// 1. 구슬 파괴 - 얼음 파편
		delMarbleList = bombMarble_ice(d,s);
		// 얼음파편 폭발에 대한 이동
		moveMarble_ice(delMarbleList);
		
		// 2. 구슬 파괴 - 시퀀스 폭발
		
		
		// 3. 구슬 변화 (그룹짓기)
		
	}
	
	// 1. 구슬 파괴 - 얼음 파편
	private static List<Pos> bombMarble_ice(int d, int s) {
		List<Pos> temp = new ArrayList<>();
		int row = shark[0];
		int col = shark[1];
		while(s-- != 0) {
			row += dr[d];
			col += dc[d];
			if(isLineOut(row,col))
				break;
			else {
				map[row][col] = 0;
				temp.add(new Pos(row,col));
			}
		}
		return temp;
	}
	
	// 얼음파편 폭발에 대한 이동
	//
	private static void moveMarble_ice(List<Pos> delMarbleList) {
	
		List<Pos> startEnding = new ArrayList<>();
		startEnding.add(new Pos(shark[0],shark[1]));
		int startR = shark[0], startC = shark[1], endR=0, endC=0;
		int move = 0, step=1, turn=0;
		
		while(!(startR==endR && startC==endC)) {
			startR += rr[move%4];
			startC += rc[move%4];
			startEnding.add(new Pos(startR, startC));
		}
		
		
	}
	
	// 결과값
	private static int resulting() {
		return (1 * bombMarbleCnt[0]) + (2 * bombMarbleCnt[1]) + (3 * bombMarbleCnt[2]);
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= N)
			return true;
		return false;
	}
	
	// 맵복사
	private static int[][] copyMapping() {
		int[][] temp = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) 
				temp[i][j] = map[i][j];
		}
		return temp;
	}

}

/**
 * 달팽이관을 하드코딩..흠?
 * 달팽이관모양을 리스트 혹은 1차원배열로 처리하는 접근은 맞는듯..
 * 
 * 
 *
 * */
