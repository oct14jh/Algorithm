package concept;

import java.util.Arrays;
import java.util.Scanner;

public class Combination_1_Basic {
	private static int N = 4;
	private static int R = 2;
	
	private static int[] input, output;
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		R = sc.nextInt();
		
		input = new int[N];
		output = new int[R];
		
		for(int i = 0; i < N; i++)
			input[i] = sc.nextInt();
		
		combination(0, 0);
	}

	private static void combination(int start, int count) {
		if(count == R) {	
			System.out.println(Arrays.toString(output));
			return;
		}
		
		for(int i = start; i < N; i++) {
			output[count] = input[i];	
			combination(i + 1, count + 1);	
		}
	}
}
