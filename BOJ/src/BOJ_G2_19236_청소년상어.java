import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class BOJ_G2_19236_청소년상어 {

	static class Shark {
		int row;
		int col;
		int dir;
		int feed;
		public Shark(int row, int col, int dir, int feed) {
			super();
			this.row = row;
			this.col = col;
			this.dir = dir;
			this.feed = feed;
		}
	}
	
	static class Fish implements Comparable<Fish> {
		int row;
		int col;
		int num;
		int dir;
		boolean alive;
		
		public Fish(int row, int col, int num, int dir, boolean alive) {
			super();
			this.row = row;
			this.col = col;
			this.num = num;
			this.dir = dir;
			this.alive = alive;
		}

		@Override
		public int compareTo(Fish that) {
			return this.num - that.num;
		}
	}
	
	
	private static int[][] map = new int[4][4];
	private static ArrayList<Fish> fishList = new ArrayList<>();
	private static int[] dr = {-1, -1, 0, 1, 1, 1, 0, -1};
	private static int[] dc = {0, -1, -1, -1, 0, 1, 1, 1};
	private static Shark shark;
	private static int result;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 0. 입력처리
		for(int i = 0; i < 4; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < 4; j++) {
				int num = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken()) - 1;
				
				fishList.add(new Fish(i, j, num, dir, true));
				map[i][j] = num;
			}
		}
		
		Collections.sort(fishList);
		
		Fish tempFish = fishList.get(map[0][0] - 1); // 1~16 번호이므로, 인덱스 접근 시에는 -1 추가작업
		shark = new Shark(0, 0, tempFish.dir, tempFish.num);
		tempFish.alive = false;
		map[0][0] = -1;
		
		dfs(shark, fishList, map);
		
		System.out.println(result);
	}

	private static void dfs(Shark shark, ArrayList<Fish> fishList, int[][] map) {
        if (result < shark.feed) {
            result = shark.feed;
        }

        // 모든 물고기 순서대로 이동
        for(Fish fish : fishList) {
        	moveFish(fish, fishList, map);
        }
        
        // 상어 이동
        for (int moveStep = 1; moveStep < 4; moveStep++) {
            int nr = shark.row + (dr[shark.dir] * moveStep);
            int nc = shark.col + (dc[shark.dir] * moveStep);
    
            if (!isLineOut(nr, nc) && map[nr][nc] >= 1) {
            	int[][] tempMap = copyMap(map);
                ArrayList<Fish> tempFishList = copyFishes(fishList);
                
                tempMap[shark.row][shark.col] = 0;
                Fish fish = tempFishList.get(map[nr][nc] - 1);
                Shark moveShark = new Shark(fish.row, fish.col, fish.dir, shark.feed + fish.num);
                fish.alive = false;
                tempMap[fish.row][fish.col] = -1;
                
                dfs(moveShark, tempFishList, tempMap);
            }
        }
    }
    
    static void moveFish(Fish fish, ArrayList<Fish> fishList, int[][] map) {
    	if (!fish.alive) 
        	return;

        for (int i = 0; i < 8; i++) {
            int nr = fish.row + dr[(fish.dir+i)%8];
            int nc = fish.col + dc[(fish.dir+i)%8];

            if (!isLineOut(nr, nc) && map[nr][nc] >= 0) {
                map[fish.row][fish.col] = 0;
                
                if (map[nr][nc] == 0) {
                    fish.row = nr;
                    fish.col = nc;
                    fish.dir = (fish.dir + i) % 8;
                } 
                else {
                    Fish temp = fishList.get(map[nr][nc] - 1);
                    temp.row = fish.row;
                    temp.col = fish.col;
                    map[fish.row][fish.col] = temp.num;

                    fish.row = nr;
                    fish.col = nc;
                    fish.dir = (fish.dir + i) % 8;
                }

                map[nr][nc] = fish.num;
                return;
            }
        }
    }

    private static int[][] copyMap(int[][] maps) {
        int[][] temp = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = maps[i][j];
            }
        }
        return temp;
    }

    private static ArrayList<Fish> copyFishes(ArrayList<Fish> fishList) {
        ArrayList<Fish> temp = new ArrayList<>();
        
        for(Fish fish : fishList) {
        	temp.add(new Fish(fish.row, fish.col, fish.num, fish.dir, fish.alive));
        }

        return temp;
    }
    
    private static boolean isLineOut(int r, int c) {
    	if(r < 0 || c < 0 || r >= 4 || c >= 4)
    		return true;
    	return false;
    }
}

/*
0. 입력처리. 맵에는 물고기 넘버만 존재. 리스트로 방향과 위치 따로 가지고 있기?. 상어 먹은 값 따로 저장해두기.
	- Fish[][] fishMap = new Fish[4][4]; => 각 칸에는 물고기 번호, 물고기 방향 [물고기번호가 -1이면 상어, 0이면 빈칸, 1~16 물고]

1. 0,0에 상어 입장
2. 물고기 이동(번호가 작은 물고기 번호 순으로 이동)
	- 자신이 가진 방향으로 1칸 이동
		- 빈칸일 경우, 해당위치로 이동
		- 물고기 있는 경우, 서로 위치 스왑
	- 이동 바로 불가능한 경우, [상어 존재 or 벽]
		- 방향 회전(반시계방향)
		- 가능할때까지.. (총 8방향 탐색)
			- 끝까지 없으면 제자
3. 모든 물고기 이동 끝나면, 상어 이동
	- 자신이 가진 방향으로만 이동(1칸이상으로 이동가능)
	- 물고기 있는 칸으로만 이동
		- 물고기먹기 + 방향도 이어 받기
	- 자신이 가진 방향으로 물고기가 없을 시 stop
	- 완탐돌려야함.자신이 갈 방향에 여러물고기잡아먹을 경우의 수 발생하기 때문에..
	- 완탐 기준, 최대값으로 물고기 쳐먹은 걸 최종 상어가먹은 물고기 값에 적용
4.결과도
 
 */