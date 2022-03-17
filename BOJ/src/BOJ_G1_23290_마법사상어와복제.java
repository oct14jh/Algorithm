import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_G1_23290_마법사상어와복제 {
	
	
	static class Node {
		boolean shark;
		ArrayList<Fish> fishes;
		int smell;
		public Node(boolean shark, ArrayList<Fish> fishes, int smell) {
			super();
			this.shark = shark;
			this.fishes = fishes;
			this.smell = smell;
		}
	}
	
	static class Fish {
		int r, c, d;

		public Fish(int r, int c, int d) {
			super();
			this.r = r;
			this.c = c;
			this.d = d;
		}
	}
	
	private static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
	private static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};
	private static ArrayList<Fish> fishList = new ArrayList<>();
	private static Fish MagicShark;
	private static int M, S, result;
	private static Node[][] map;
	private static int eatingFishMax = Integer.MIN_VALUE;
	private static ArrayList<Integer> sharkDirList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		// 맵 초기
		map = new Node[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				map[i][j] = new Node(false, new ArrayList<>(), 0);
			}
		}

		// 입력 처리
		M = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		for(int sTime = 0; sTime < S; sTime++) {
			if(sTime == 0) {
				for(int mTime = 0; mTime < M; mTime++) {
					st = new StringTokenizer(br.readLine(), " ");
					
					int r = Integer.parseInt(st.nextToken()) - 1;
					int c = Integer.parseInt(st.nextToken()) - 1;
					int d = Integer.parseInt(st.nextToken()) - 1;
						
					Fish fish = new Fish(r, c, d);
					fishList.add(fish);
					map[r][c].fishes.add(fish);
					
				}
				st = new StringTokenizer(br.readLine(), " ");
				int r = Integer.parseInt(st.nextToken()) - 1;
				int c = Integer.parseInt(st.nextToken()) - 1;
				map[r][c].shark = true;
				MagicShark = new Fish(r, c, 0);
			}
			// 마법연습
			magicPractice();
			
			// 연습 한바퀴 돌면 냄새 1씩 없애기
			smellDelete();
		}
		
		result = resulting();
		System.out.println(result);
		br.close();
	}
	
	// 마법연습
	private static void magicPractice() {
		ArrayList<Fish> copyFishList = new ArrayList<>();
		// 1. 물고기 복제 마법 시전(반영은 5번 알고리즘에서 수행)
		copyFishList = copyFishMagic(fishList);
		
		// 2. 모든 물고기 1칸 이동
		movingFish(fishList);
		
		// 3. 상어의 연속 3칸 이동
		movingShark();
		
		// 4. 연습2번 처리될때마다, 냄새 제거 => 이건 그냥 연습 처리에서 처리
		
		// 5. 1에서 사용한 복제마법
		for(Fish copy : copyFishList) {
			fishList.add(copy);
		}
		
	}
	
	// 1. 물고기 복제 마법
	private static ArrayList<Fish> copyFishMagic(ArrayList<Fish> fishList) {
		ArrayList<Fish> copyFishList = new ArrayList<>();
		for(Fish copyFish : fishList) {
			int r = copyFish.r;
			int c = copyFish.c;
			int d = copyFish.d;
			copyFishList.add(new Fish(r,c,d));
		}
		return copyFishList;
	}

	// 2. 모든 물고기 1칸 이동 (단, 상어,냄새,경계 밖이면 45도반시계 회전하면서 이동할 곳 서칭. // 다돌았는데 없으면제자리)
	private static void movingFish(ArrayList<Fish> fishList) {
		ArrayList<Fish> realFishList = new ArrayList<>();
		
		for(int i = 0; i < fishList.size(); i++) {
			Fish checkFish = fishList.get(i);
			
			int nd = possibleDirection(checkFish);
			//제자리위치
			if(nd == -1) {
				realFishList.add(new Fish(checkFish.r, checkFish.c, checkFish.d));
			}
			//해당위치로 1칸 이동
			else {
				int nr = checkFish.r + dr[nd];
				int nc = checkFish.c + dc[nd];
				realFishList.add(new Fish(nr, nc, nd));
			}
		}
		fishList.clear();
		fishList = realFishList;
	}

	// 3. 상어의 연속 3칸 이동처리
	private static void movingShark() {
		// 상어가 어떤 순열로 이동했을 때 가장 물고기를 많이 먹는지 체크하기 (만약먹은물고기수가 같다면,사전처리 필요)
		// 이동처리전 최대먹은물고기수 초기화 + 방향리스도 초기
		eatingFishMax = Integer.MIN_VALUE;
		sharkDirList = new ArrayList<>();
		// 맵에 상어 이동에 따른 처리
		permutation(sharkDirList, 0, 0, MagicShark, map);
		processMovingShark(sharkDirList, MagicShark);
	}
	
	// 4. 연습1번당 냄새 제거
	private static void smellDelete() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(map[i][j].smell > 0)
					map[i][j].smell -= 1;
			}
		}
	}
	// 번외 - 맵에 상어 이동에 따른 처리
	private static void processMovingShark(ArrayList<Integer> sharkDirList, Fish MagicShark) {
		for(int i = 0; i < 3; i++) {
			int r = MagicShark.r;
			int c = MagicShark.c;
			int d = sharkDirList.get(i);
			map[r][c].shark = false;
		
			
			int nr = MagicShark.r + dr[d];
			int nc = MagicShark.c + dc[d];
			
			map[nr][nc].fishes.clear();
			map[nr][nc].smell = 2;
			map[nr][nc].shark = true;
			MagicShark.r = nr;
			MagicShark.c = nc;
		}
	}
	
	// 번외 - 상어 3번 이동의 순열
	private static void permutation(ArrayList<Integer> moveBox, int count, int eatenFish, Fish shark, Node[][] copyMap) {
		int eatingFish = 0;
		if(count == 3) {
			if(eatenFish > eatingFishMax) {
				eatingFishMax = eatenFish;
				sharkDirList = moveBox;
				return;
			}
			if(eatenFish == eatingFishMax) {
				sharkDirList = convertValue(sharkDirList, moveBox);
				return;
			}
			return;
		}
		for(int d = 0; d < 7; d+=2) {
			int nr = shark.r + dr[d];
			int nc = shark.c + dc[d];
			
			if(isLineOut(nr,nc))
				continue;
			else if(!copyMap[nr][nc].fishes.isEmpty()) {
				eatingFish = copyMap[nr][nc].fishes.size();
				ArrayList<Fish> temp = new ArrayList<>();
				for(Fish tmp : copyMap[nr][nc].fishes) {
					temp.add(tmp);
				}
				copyMap[nr][nc].fishes.clear();
				copyMap[nr][nc].smell = 2;
				shark.r = nr;
				shark.c = nc;
				moveBox.add(d);
				permutation(moveBox, count + 1, eatenFish + eatingFish, shark, copyMap);
				moveBox.remove(moveBox.size()-1);
				copyMap[nr][nc].fishes = temp;
				copyMap[nr][nc].smell = 0;
				shark.r -= dr[d];
				shark.c -= dc[d];
			}
			else {
				shark.r = nr;
				shark.c = nc;
				moveBox.add(d);
				permutation(moveBox, count + 1, eatenFish + eatingFish, shark, copyMap);
				moveBox.remove(moveBox.size()-1);
				shark.r -= dr[d];
				shark.c -= dc[d];
			}
		}
	}
	
	// 번외 - 사전 처리 순
	private static ArrayList<Integer> convertValue(ArrayList<Integer> a, ArrayList<Integer> b) {
		int valueA = 0, valueB = 0;
		for(int idx = 0; idx < 3; idx++) {
			int checkA = a.get(idx);
			int checkB = b.get(idx);
			if(idx == 0) {
				valueA += checkA * 100;
				valueB += checkB * 100;
			}else if(idx == 1) {
				valueA += checkA * 100;
				valueB += checkB * 100;
			}else {
				valueA += checkA * 1;
				valueA += checkB * 1;
			}
		}
		
		if(valueA < valueB)
			return a;
		else
			return b;
	}
	
	// 번외 - 물고기 이동 방향 반환
	private static int possibleDirection(Fish fish) {
		int turnCnt = 0;
		while(true) {
			int nr = fish.r + dr[fish.d];
			int nc = fish.c + dc[fish.d];
			
			if(isLineOut(nr,nc)) {
				fish.d = (fish.d + 1) % 8;
				turnCnt++;
				continue;
			}
			if(map[nr][nc].shark || map[nr][nc].smell > 0) {
				// 한바퀴 체크다해도 걸린거라면 제자리
				if(turnCnt == 8)
					return -1;
				
				// 갈려는 곳이 문제니 반시계방향회전
				fish.d = (fish.d + 1) % 8;
				turnCnt++;
				continue;
			} 
			else 
				break;
		}
		return fish.d;
	}
	
	// 번외 - 경계 확인
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= 4 || c >= 4)
			return true;
		return false;
	}

	// 결과 출력
	private static int resulting() {
		int total = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(!map[i][j].fishes.isEmpty())
					total += map[i][j].fishes.size();
			}
		}
		return total;
	}
}

/*
 * 상어의 마법 연습 알고리즘
 * 1. 모든 물고기에게 복제 마법 시전 => (5번에타이밍에 구현)
 * 2. 모든 물고기가 한칸 이동. 
 * 		- 단, 상어가 있거나, 물고기의 냄새가 있는 칸, 격자 범위 벗어나는 칸은 X
 * 		- 각 물고기는 자신이 가지고 있는 이동방향이 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 회전
 * 			- 단, 이동할 수 있는 칸이 없으면 이동하지 않는다.
 * 			- 이외의 경우에는 무조건 그 칸으로 이동.
 * 3. 상어가 연속해서 3칸 이동
 * 		- 현재 위치에서 상하좌우로 인접한 칸 이동 가능
 * 		- 연속해서 이동하는 칸 중에서 격자의 범위를 벗어나는 칸이 있으면, 그 방법은 불가능한 이동 방법
 * 		- 연속해서 이동하는 중에, 상어가 물고기 존재하는 칸으로 이동하게 되면, 그 칸에 있는 모든 물고기는 격자에서 삭제되고, 냄새를 남긴다.
 * 		- 가능한 이동 방법 중에서 제외되는 물고기 수가 가장 많은 방법으로 이동하되, 같다면 사전 순으로 가장 앞서는 방법 이용
 * 4. 두 번 전 연습에서 생긴 물고기 냄새가 격자에서 사라진다.
 * 5. 1에서 사용한 복제 마법 완료. 모든 복제된 물고기는 1에서의 위치와방향 그대로 가진다.
 * 
 * 최종적으로, 격자에 있는 물고기 수 구하라.
 */
