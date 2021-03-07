package zzzz;

import java.util.Scanner;

public class Main {
	private static int N;
	private static char answer;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();

		DP(N);

		System.out.println(answer);
		sc.close();
	}

	private static void DP(int n) {
		int mooSize = 3;
		int mooFront = 0;
		int K = 0;

		if (n == 1) {
			answer = 'm';
		} else if (n <= 3) {
			answer = 'o';
		} else {
			while (mooSize < n) {
				mooSize = (2 * mooSize) + K + 4;
				K++;
			}
			mooFront = (mooSize - (K + 3)) / 2;

			if (n >= mooSize - mooFront + 1) {
				DP(n - mooSize + mooFront);
			} else if (n == mooFront + 1)
				answer = 'm';
			else
				answer = 'o';
		}
	}
}
