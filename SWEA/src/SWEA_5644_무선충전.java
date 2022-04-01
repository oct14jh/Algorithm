import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SWEA_5644_무선충전 {
	
	private static int M, aCnt;			// 시간, 충전소 개수
	private static int[] pathA, pathB;	// 플레이어의 인덱스는 위치 값(x,y) 각각 저장
	private static int[] playerA = new int[2];
	private static int[] playerB = new int[2];
	private static int[][] ap;			// 리스트?
	
	/** 0이동하지않음, 1상, 2우, 3하, 4좌**/
	private static int[] dx = {0, 0, 1, 0, -1};	// = dc
	private static int[] dy = {0, -1, 0, 1, 0}; // = dr
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine().trim());
		
		StringTokenizer st = null;
		
		for(int testCase = 1; testCase <= T; testCase++) {
			st = new StringTokenizer(br.readLine());
			
			M = Integer.parseInt(st.nextToken());
			aCnt = Integer.parseInt(st.nextToken());
			
			playerA[0] = playerA[1] = 1;
			playerB[0] = playerB[1] = 10;
			
			pathA = new int[M+1];	// 제자리(처음위치) 즉, 0초에서도 충전시간 체킹해준다 했기때문에 +1
			pathB = new int[M+1];
			ap = new int[aCnt][4];	// 한 ap마다 4개의 정보를 필요로함. x,y,충전가능거리, 충전량
			
			String charsA = br.readLine();
			String charsB = br.readLine();
		
			for(int c = 0; c < M; c++) {
				pathA[c + 1] = charsA.charAt(c*2) - '0';	// 공백 1칸씩 존재하기때문에.. c가 1씩 순차증가라면 c*2해서 접근하면 된다.
				pathB[c + 1] = charsB.charAt(c*2) - '0';
			}
			
			for(int a = 0; a < aCnt; a++) {
				st = new StringTokenizer(br.readLine(), " ");
				ap[a][0] = Integer.parseInt(st.nextToken());	// x
				ap[a][1] = Integer.parseInt(st.nextToken());	// y
				ap[a][2] = Integer.parseInt(st.nextToken());	// c (충전 가능 범위)
				ap[a][3] = Integer.parseInt(st.nextToken());	// p (충전량)
			}
			System.out.println("#" + testCase + " " + move());
		}
	}

	// 매 시간마다 두 플레이어의 충전량의 합의 최대값을 구하고, 그 값을 모든 시간 동안 누적
	private static int move() {
		ArrayList<Integer> apListA, apListB;
		int totalSum = 0;
		int time = 0;
		
		while(time <= M) {
			// 두 플레이어를 해당 시간의 이동 정보에 맞게 이동
			playerA[0] += dx[pathA[time]];
			playerA[1] += dy[pathA[time]];
			playerB[0] += dx[pathB[time]];
			playerB[1] += dy[pathB[time]];
			// 두 플레이어의 자신의 위치 기준으로 충전 가능한 충전소리스트 가져오기
			apListA = getAp(playerA[0], playerA[1]);
			apListB = getAp(playerB[0], playerB[1]);
			
			totalSum += getCharge(apListA, apListB);
			++time;
		}
		
		return totalSum;
	}


	private static ArrayList<Integer> getAp(int x, int y) {
		ArrayList<Integer> apList = new ArrayList<Integer>();
		int d = 0;
		for(int a = 0; a < aCnt; a++) {
			d = Math.abs(ap[a][0] - x) + Math.abs(ap[a][1] - y);
			
			if(d <= ap[a][2])
				apList.add(a);
		}
		return apList;
	}

	private static int getCharge(ArrayList<Integer> apListA, ArrayList<Integer> apListB) {
		int max = 0, temp = 0;
		int aSize = apListA.size(), bSize = apListB.size();
		
		if(aSize == 0 && bSize == 0)
			return 0;
		else if(aSize == 0)
			return getMaxPower(apListB);	// 충전가능한 것들 중 최대값을 반영하기 위함 (플레이어B만 충전 가능)
		else if(bSize == 0)
			return getMaxPower(apListA);	// 충전가능한 것들 중 최대값을 반영하기 위함 (플레이어A만 충전 가능)
		
		// 나머지 경우는 조합을 통해 따져봐야함. (즉, 플레이어 A,B 모두 충전가능한 상황일 때의 조합)
		for (Integer a : apListA) {
			for(Integer b : apListB) {
				if(a != b)
					temp = ap[a][3] + ap[b][3];	// 서로 다르면 그냥 더한다.
				else
					temp = Math.max(ap[a][3], ap[b][3]);
				
				if(max < temp)
					max = temp;
			}
		}
		return max;
	}

	// 최대값 체크 및 갱신
	private static int getMaxPower(ArrayList<Integer> apList) {
		int max = 0;
		for(Integer a : apList) {
			if(max < ap[a][3]) max = ap[a][3];
		}
		return max;
	}
}	

/*
 * 충전가능한 범위 : 두 좌표 차이값(절대값) 합
 * 
 * 테케 20개
 * 충전하려는 플레이어 수 = 2명 고정
 * 충전가능한 BC => 1~8 개
 * 
 * 
 * 
 * 각 궤도의 위치마다 두 플레이어의 충전가능한 AP리스트 구하기
 * 플레이어 A의 AP 리스트, 플레이어 B의 AP리스트 간의 조합 활용. (두 플레이어의 충전량 합이 최대가 되는 것을 구하고자)
 * 위 두 줄의 과정을 매 시간마다 반복.
 * 고로, 매 시간마다의 최대충전량 누적합을 구하는 것이다.
 * 
 * 겹치는 경우, 결국 2등분이기 때문에, 절반씩 나누어서 합하거나 그럴 필요없이 그냥 해당 BC의 충전량 모든 값 자체 하나를 더하면 된다.
 * 
 * 초기위치(0초)부터 충전을 할 수 있다 => 3초까지 할 수 있다하면, 0초,1초,2초,3초까지의 누적합을 구하는 것이다.
 * 
 * 같은 위치에 플레이어가 존재할 수 있다. 단, 같은 위치에 2개이상의 BC가 설치되는 경우는 없다.
*/
