package zzzz;

import java.util.Scanner;

public class Main2 {
	private static int N, K;
	private static int[] arr = new int[26];
	private static char answer;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		
		/* N과 K 값 잡아주기 */
		arr[0] = 3;
		for(int i = 1; i <= 25; i++) {
			if(N <= arr[i-1]) {
				K = i - 1;
				break;
			}
			arr[i] = (2*arr[i-1]) + i + 3;
		}
		/* 값 구하기 */
		DP(N, K);
		System.out.println(answer);
	}

	private static void DP(int n, int k) {
		if (k == 0) {
			if(n==1)
				answer = 'm';
			else if(n==2 || n==3)
				answer = '0';
		}
		else if(k == 1) {
			if(n==1 || n==4 || n==8)
				answer = 'm';
			answer = 'o';
		}
		else if(n <= (arr[k]-(k+3)) / 2) {
			DP(n, k-1);
		}
		else if((n > (arr[k]-(k+3)) / 2) && (n <= (arr[k-1]+(k+3)))) {
			if(n == ((arr[k]-(k+3)) / 2)+1) {
				answer = 'm';
			}
			else
				answer = 'o';
		}
		else {
			n = n - (arr[k] - arr[k-1]) - ((k + 3));
			DP(n, k-1);
		}
	}
}
