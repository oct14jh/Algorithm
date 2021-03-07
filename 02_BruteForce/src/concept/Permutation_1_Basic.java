package concept;

import java.util.Arrays;
import java.util.Scanner;

public class Permutation_1_Basic {
	private static int N = 4;
	private static int R = 2;
	
	private static int[] input, output;
	
	private static boolean[] visited;
	
	public static void main(String[] args) {	
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		R = sc.nextInt();
		
		input = new int[N];
		visited = new boolean[N];
		output = new int[R];
		
		for(int i = 0; i < N; i++)
			input[i] = sc.nextInt();
		
		permutation(0);
		
	}

	private static void permutation(int count) {
		if(count == R) {
			System.out.println(Arrays.toString(output));
			return;
		}
		
		for(int i = 0; i < N; i++) {
			if(visited[i] == true)
				continue;
			
			output[count] = input[i];
			visited[i] = true;
			permutation(count + 1);
			visited[i] = false;
		}
	}
}
