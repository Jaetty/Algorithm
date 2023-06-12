package bj;

import java.util.Scanner;

public class BJ_1로만들기_1463 {
	
	static int[] memoi;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		memoi = new int[N+1]; // 0 dummy
		
		memoi[1] = 0;
		
		System.out.println(dp(N));
	}
	
	static int dp(int n) {
		
		// 기저 조건
		if(n == 1) return 0;
		
		// n 에 대해서 미리 계산된 값이 있으면 그 값을 활용
		if(memoi[n] > 0) return memoi[n];
		
		// 아직 n 에 대해서 계산딘 값이 없다.
		
		// 이전 단계 + 1
		memoi[n] = dp(n-1) + 1; // n 보다 1작은 숫자를 만드는 경우의 수 + 1 (횟수)
		
		// 2로 나누어지면 이전 단계 (n/2) + 1
		if(n%2==0) memoi[n] = Math.min(memoi[n], dp(n/2)+1);
		// 3로 나누어지면 이전 단계 (n/3) + 1
		if(n%3==0) memoi[n] = Math.min(memoi[n], dp(n/3)+1);
		
		return memoi[n];
	}

}
