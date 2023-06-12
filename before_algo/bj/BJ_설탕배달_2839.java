package bj;

import java.util.Scanner;

public class BJ_설탕배달_2839 {
	
	static int N, min;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		min = 5000;
		
		comb(0,0);
		
		min = min == 5 ? -1 : min;
		
		System.out.println(min);
		
	}
	
	static void comb(int five, int three) {
		int sum = five *5 + three * 3;
		
		if(sum == N) { // 2 종류의 봉투로 Nkg 을 만들었다.
			min = Math.min(min, five + three);
			return;
		}else if(sum>N) {
			return;
		}
		
		comb(five+1, three);
		comb(five, three+1);
		
	}

}
