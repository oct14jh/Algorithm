import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_G2_17143_낚시왕 {
	
	private static class Pos {
		int r, c;

		public Pos(int r, int c) {
			super();
			this.r = r;
			this.c = c;
		}
	}
	
	private static class Shark {
		int s, d, z;

		public Shark(int s, int d, int z) {
			super();
			this.s = s;
			this.d = d;
			this.z = z;
		}
	}
	
	// 1부터임.. 상,하,우,
	private static int[] dr = {-1,1,0,0};
	private static int[] dc = {0,0,1,-1};
	
	private static int R, C, M, answer;
	private static ArrayList<Shark>[][] map;
	private static ArrayList<Pos> sharkList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		sharkList = new ArrayList<>();
		map = new ArrayList[R][C];
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				map[i][j] = new ArrayList<Shark>();
			}
		}
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken()) - 1;
			int z = Integer.parseInt(st.nextToken());
			map[r][c].add(new Shark(s,d,z));
			sharkList.add(new Pos(r,c));
		}
		
		for(int i = -1; i < C; i++) {
			// 1. 낚시왕 이동
			int ktgCol = i + 1;
			if(ktgCol == C)
				break;
			
			// 2. 상어 낚시 (서있는 열에서, 땅에서 가장가까운(row값 제일 작은 값) 상어낚시하기
			fishingShark(ktgCol);
			
			// 3. 상어 이동
			sharkList = movingShark();
		}
		
		System.out.println(answer);
	}
	
	private static void fishingShark(int ktgCol) {
		int index = 0;
		for(int row = 0; row < R; row++) {
			if(map[row][ktgCol].size() > 0) {
				answer += map[row][ktgCol].get(0).z;
				System.out.println("먹은크기:" + map[row][ktgCol].get(0).z + ", 방향:" + row + "," + ktgCol);
				map[row][ktgCol].clear();
				
				for(int i = 0; i < sharkList.size(); i++) {
					Pos temp = sharkList.get(i);
					if(temp.r == row && temp.c == ktgCol) {
						sharkList.remove(i);
						break;
					}
				}
				return;
			}
		}
	}

	private static ArrayList<Pos> movingShark() {
		ArrayList<Pos> newSharkList = new ArrayList<>();
		
		for(Pos shark : sharkList) {
			int r = shark.r;
			int c = shark.c;
			
			process(map[r][c], r, c);
		}
		
		newSharkList = sharkKing();
		
		return newSharkList;
	}
	
	private static ArrayList<Pos> sharkKing() {
		ArrayList<Pos> temp = new ArrayList<>();
		
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				if(map[i][j].size() == 1)
					temp.add(new Pos(i,j));
				else if(map[i][j].size() >= 2) {
					Pos newSharkKing = processKing(map[i][j], i, j);
					temp.add(newSharkKing);
				}
			}
		}
		
		return temp;
	}
	
	private static Pos processKing(ArrayList<Shark> lists, int r, int c) {
		Shark currentKing = lists.get(0);
		for(int i = 1; i < lists.size(); i++) {
			if(currentKing.z < lists.get(i).z)
				currentKing = lists.get(i);
		}
		
		map[r][c].clear();
		map[r][c].add(currentKing);
		return new Pos(r,c);
	}
	
	private static void process(ArrayList<Shark> shark, int r, int c) {
		int s = shark.get(0).s;
		int d = shark.get(0).d;
		int z = shark.get(0).z;
		int tempR = r, tempC = c;
		for(int i = 0; i < s; i++) {
			int nr = tempR + dr[d];
			int nc = tempC + dc[d];
			
			if(isLineOut(nr,nc)) {
				d = reverseDir(d);
				nr = tempR + dr[d];
				nc = tempC + dc[d];
			}
			tempR = nr;
			tempC = nc;
		}
		map[r][c].clear();
		map[tempR][tempC].add(new Shark(s, d, z));
	}
	
	private static int reverseDir(int d) {
		switch(d) {
		case 0:return 1;
		case 1:return 0;
		case 2:return 3;
		default:return 2;
		}
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= R || c >= C)
			return true;
		return false;
	}
}


// map[R][C]
// 상어는 크기와 속도 가짐

/*
 * 낚시왕은 최초 (0,-1)에 위치. 가장 오른쪽 열의 오른쪽 칸(0,C)에 위치하면멈춘다.
 * 1. 오른쪽으로 1칸 이동
 * 2. 낚시왕이 서 있는 해당 열에서 땅과 가장 가까운 상어 잡는다. (잡으면, 격자에서 상어 사라짐)
 * 3. 상어가 이동한다.
 * 	- 경계를 넘는 경우, 방향을 반대로 바꿔서 속력 유지한채로 이동
 *  	-즉 속력만큼 가다가 경계에 다다르면, 방향바꿔서 남은 속력만큼 재이동
 * 4. 상어가 이동 마치고 나면, 한칸에 상어가 2마리 이상 있을 수도 있다.
 * 	- 이때, 크기가 가장 큰상어가 그 칸의 상어들 다 잡아먹는다.
 * 
 * 이때, 낚시왕이 잡은 상어 크기의 합을 구하라.
 * 
 * R,C,M(상어수)
 * M줄만큼반복입력 : r,c, s(속력), d(방향), z(크기)
 * 
 * */
