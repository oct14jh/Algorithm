import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_G5_1759_암호만들기 {
	
	private static int L, C;
	private static String[] list;
	private static String[] result;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		L = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		list = new String[C];
		result = new String[L];
		st = new StringTokenizer(br.readLine(), " ");
		for(int i = 0; i < C; i++) {
			list[i] = st.nextToken();
		}
		
		Arrays.sort(list);
		combination(0, 0);
	}
	
	private static void combination(int idx, int count) {
		if(count == L) {
			if(isCondition(result)) {
				for(int i = 0; i < L; i++) {
					System.out.print(result[i]);
				}
				System.out.println();
			}
			return;
		}
		
		for(int i = idx; i < C; i++) {
			result[count] = list[i];
			combination(i+1, count+1);
		}
	}
	
	private static boolean isCondition(String[] str) {
		int jaum = 0;
		int moum = 0;
		for(int i = 0; i < L; i++) {
			if(str[i].equals("a") || str[i].equals("e") || str[i].equals("i") || str[i].equals("o") || str[i].equals("u"))
				moum++;
			else
				jaum++;
		}
		if(moum >=1 && jaum >= 2)
			return true;
		else
			return false;
	}
}

/*
서로 다른 L개의 소문자 알파벳과 최소 1개의 모음(a,e,i,o,u)와 자음 최소 2개로 구성
오름차순 느낌으로 알파벳 정렬

문자종류 C가지
*/