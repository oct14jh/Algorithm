import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_G5_2668_숫자고르기 {
	
	private static int N, result;
	private static int[] arr;
	private static int[] resultArr, indexArr;
	private static boolean isResult;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		arr = new int[N];
		
//		for(int i = 0; i < N; i++) {
//			arr[i] = Integer.parseInt(br.readLine()) -1 ;
//			System.out.print(arr[i]+" ");
//		}
//		System.out.println("------");
		for(int limit=N; limit >0 ; limit--) {
			resultArr = new int[limit];
			indexArr = new int[limit];
			isResult = false;
			
			combination(0, 0, limit);
			if(isResult) {
				result = limit;
				break;
			}
		}
		System.out.println(result);
		for(int i = 0; i < indexArr.length; i++) {
			if(i == indexArr[i])
				System.out.println(i);
		}
		
	}
	
	private static void combination(int index, int count, int limit) {
//		boolean isResult = false;
		if(count == limit) {
			Arrays.sort(resultArr);
			Arrays.sort(indexArr);

			for(int i = 0; i < limit; i++) {
				if(resultArr[i] != indexArr[i]) {
					isResult = false;
					return;
				}
			}
			isResult = true;
			return;
		}
		
		for(int i = index; i < N; i++) {
			resultArr[count] = arr[i];
			indexArr[count] = i;
			combination(i+1, count+1, limit);
		}
	}
}


/*
 * 가로 윗줄은 인덱스다.
 * 가로 아랫줄은 값이다.
 * 
 * 인덱스조합으로 인덱스 셋트와
 * 그 인덱스로 뽑은 인덱스 세트와 같은지를 비교한다.
 * 
 * 그리고,같으면 해당 세트의 크기를 출력하고 다음줄에는 세트를 오름차순으로 1개의 값씩출력한다.
 * 
 * */
