package zzzz;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class reviewData {
	
	private static Random random = new Random();
	private static List<String> commentList[] = new ArrayList[4];
	public static void main(String[] args) {
		
		for(int i = 0; i < 4; i++) { commentList[i] = new ArrayList<>(); }
		commentWork0();
		commentWork1();
		commentWork2();
		commentWork3();
	
		try {
			PrintWriter write = new PrintWriter("C://SSAFY/output.txt");
			
			for(int srNo = 1; srNo < 60000; srNo++) { // 리뷰 고유번호 수만큼 반복(6만개라 잡음)
				StringBuilder sb = new StringBuilder();
				
				if(srNo <= 10318) {
					int tempSNo = srNo;	// 최초 상가에 하나씩은 리뷰 넣어주기 위함...
					int tempUNo = randomUserNo();
					int tempScore = randomScore() + 20; // 1~80 + 20인형태,,
					tempScore = randomControlScore(tempScore);	// 스코어 조정한 값
					String tempComment = randomComment(tempScore);
					sb.append(srNo).append(", ")
						.append(tempSNo).append(", ")
						.append(tempUNo).append(", ")
						.append(tempComment).append(", ")
						.append(tempScore);
				}
				else {
					int tempSNo = random.nextInt(10318)+1;
					int tempUNo = randomUserNo();
					int tempScore = randomScore() + 20;
					tempScore =randomControlScore(tempScore);
					String tempComment = randomComment(tempScore);
					sb.append(srNo).append(", ")
					.append(tempSNo).append(", ")
					.append(tempUNo).append(", ")
					.append(tempComment).append(", ")
					.append(tempScore);
				}
				write.println(sb.toString());
			}
			write.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int randomUserNo() {
		return (random.nextInt(100)+1); // 1~100 출력
	}
	private static int randomScore() {
		return (random.nextInt(80)+1); // 1~80 출력
	}
	private static int randomControlScore(int score) {
		if(score <= 39) { return 1; }
		if(score >= 40 && score <= 42) { return 5; }
		if(score >= 43 && score <= 45) { return 10; }
		if(score >= 46 && score <= 50) { return 30; }
		if(score >= 51 && score <= 54) { return 45; }
		else { return score; }
	}	
	private static String randomComment(int score) {
		int group = 0;
		if(score >= 85) { group = 0;	return commentList[group].get(random.nextInt(10)); }
		else if(score >= 70 && score <= 84) { group = 1;	return commentList[group].get(random.nextInt(10)); }
		else if(score >= 50 && score <= 69) { group = 2;	return commentList[group].get(random.nextInt(10)); }
		else { group = 3;	return commentList[group].get(random.nextInt(10)); }
	}
	// 10개의 코멘트 들어있음(총40개)
	private static void commentWork0() {
		commentList[0].add("가슴이 웅장해집니다. 너무 맛있어요.");
		commentList[0].add("쩌럿!!!!!!!!!!!!!!");
		commentList[0].add("우리 남편도 딸도 너무 맛있게 먹었어요");
		commentList[0].add("내 인생 가게");
		commentList[0].add("둘이 먹다 죽음");
		commentList[0].add("죽어도 여한이 없어여!!!");
		commentList[0].add("인테리어도 위생도 다 좋은데, 맛이 제일입니다");
		commentList[0].add("번창하세요!!! 훌륭합니다");
		commentList[0].add("진짜 킹성비 띵쳤다");
		commentList[0].add("완전강추드립니다 여러분. 꼭 방문해보세요");
	}
	private static void commentWork1() {
		commentList[1].add("맛있게 먹었어요");
		commentList[1].add("맛은 좋지만, 가격이 아쉬운,, 근데 진짜맛있음");
		commentList[1].add("가성비는 미쳤는데,,, 아 진자 가격대비 개쩔음");
		commentList[1].add("추천하면 욕은 먹지않을 수준 굿!!!");
		commentList[1].add("잘먹었습니당. 다음에 또 올게요");
		commentList[1].add("ㅋㅋㅋㅋㅋㅋ잘먹었어요ㅎㅎ");
		commentList[1].add("무난무난 쏘굿");
		commentList[1].add("저는 개인적으로 좋은데, 여자친구는 싫어하네요ㅠㅠ");
		commentList[1].add("진짜 여기 오붓함...");
		commentList[1].add("인싸들은 꼭 방문했을 가게입니다. 맛은 낫배드ㅎㅎ");
	}
	private static void commentWork2() {
		commentList[2].add("가격은 좀 많이 비싸네요.");
		commentList[2].add("맛은 좋은데 위생이 좀...");
		commentList[2].add("인테리어는 훌륭하지만 맛은 그냥저냥이에요");
		commentList[2].add("난 갠적으로 별로..근데 애들은 잘먹음");
		commentList[2].add("호불호 백프로 갈립니다!");
		commentList[2].add("무난..?");
		commentList[2].add("킹성비인줄 알고 갔는데, 그냥 그래요..");
		commentList[2].add("양 씹창입니다.ㅋㅋㅋㅋㅋ 아 맛은 나쁘지않음");
		commentList[2].add("양으로 승부하는가게라 엄청 배고플때가면 조흥 가게");
		commentList[2].add("잘먹고 갑니다.");
	}
	private static void commentWork3() {
		commentList[3].add("할많하않ㅋ");
		commentList[3].add("ㅋㅋ");
		commentList[3].add("장사접으세여");
		commentList[3].add("이걸먹을바에 굶겠음");
		commentList[3].add("둘이먹다가진짜 뒤질뻔햇어요;;독약임독약");
		commentList[3].add("이야 맛 실화냐?ㅋㅋㅋㅋ어이가없네진짜");
		commentList[3].add("솔직히 두번은 안갈것같아요");
		commentList[3].add("1점도 아까움 ㅋㅋㅋㅋㅋ");
		commentList[3].add("손님 대응 이런식으로 밖에 못하나요? 별로네요");
		commentList[3].add("아니 평점 나쁘지않은데,, 조작한건가요?");
	}
}

/*
 sr_no 는 1번부터 쭈욱 포문 돌려서 계속
 sr_s_no는 상가 번호 내에서 랜덤으로..(1~10318)
 sr_u_no는 유저 번호 내에서 랜덤으로..(일단 1~10)
 sr_score는 30~100점 내에서 랜덤으로..
 	- 30~39점 => 1점으로 변환
 	- 40~42점 => 5점으로 변환
 	- 43~45점 => 10점으로 변환
 	- 46~50점 => 30점으로 변환
 	- 51~54점 => 45점으로 변환
 sr_content는 내가 만든 string 배열 내 랜덤으로 뽑기 (4그룹으로 분류)
 	- 0그룹 (맛있다) 85점 이상 : 1그룹의 string리스트 아무거나 출력
 	- 1그룹 (쏘쏘) 70점~84점 : 2그룹의 string리스트 아무거나 출력
 	- 2그룹 (별로) 50~69점 : 3그룹의 String리스트 아무거나 출력
 	- 3그룹 (핵극혐) 49점 이하 : 4그룹의 String리스트 아무거나 출력
*/