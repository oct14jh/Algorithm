import java.util.Arrays;
import java.util.Scanner;

public class BOJ_B2_2309_일곱난쟁이 {
	private static int[] members = new int[9];
	private static int[] realMembers = new int[7];
	private static int[] result = new int[7];
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < members.length; i++) {
			members[i] = sc.nextInt();
		}
		
		Arrays.sort(members);
		
		combination(0, 0);
		
	}
	
	private static void combination(int index, int count) {
		if(count == 7) {
			int sum=0;
			for(int i=0; i<7; i++) {
				sum += realMembers[i];
				if(sum == 100) {
					for(int j=0; j<7; j++) {
						System.out.println(realMembers[j]);
					}
					System.exit(0);
				}
			}
			return;
		}
		
		for(int i=index; i < 9; i++) {
			realMembers[count] = members[i];
			combination(index + 1, count + 1);
		}
	}
}