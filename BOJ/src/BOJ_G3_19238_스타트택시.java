import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_G3_19238_스타트택시 {

}


/**
0. 현재 연료가 1 이상, 손님존재 확인하기(손님이존재하는데 연료가 0이하면 -1 출력),(손님이존재하지않으면 무조건 결과 출)
1. 택시 위치에서 가장 가까운 손님의 위치로 이동 (거리 같을 시에는 행/열 값 더 작은거 우선)
	- 조건1
	- 조건2
2. 현재연료량 - (태우기위해 이동한 거리 + 모시는 거리) + (모시는거리 * 2) < 0 이면, 3번문 수행, 아니면 4번
	- 손님출발위치에 도착하면, 태우기 위해 이동한 거리 저장되어있어야함.
	- 손님출발위치와 손님도착위치 미리 계산해야함(모시는거리)
	- 1번 조
3. 2번째로 가까운 손님 위치로 이동 그리고 2번 수행...
	- 1번 메소드 
4. 연료 계산하기 + 손님 처리

결과출력 => 최종적으로 남은 연료의 
**/
