package aaa;

public class asd {
	public static void main(String[] args) {
		int count = 1;
		int[][] map = new int[4][4];
		int[][] copyFor = new int[4][4];
		int[][] copySimple = new int[4][4];
		
		for(int i=0;i<4;i++) {
			for(int j=0; j<4;j++) {
				map[i][j] = count++;
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j=0;j<4;j++) {
				copyFor[i][j] = map[i][j];
			}
		}
		
		copySimple = map;
		System.out.println("======");
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(copyFor[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("======");
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.print(copySimple[i][j] + " ");
			}
			System.out.println();
		}
		
		
	
	}
}
