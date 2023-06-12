package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_다리놓기_1010_2 {
	
	// DP로 푸는 방법
	
	static int T, N, M;
	static int[][] dp; // 역할은 재귀호출을 줄여주는 효과
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=0; t<T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			dp = new int[M+1][N+1];
			
			// dp 로 배열 완성하기
			for(int i=0; i<=N; i++) {
				for(int j=0; j<=Math.min(i, N); j++) {
					if(j==0 || j==i) dp[i][j] = 1;
					else dp[i][j] = (dp[i-1][j-1] + dp[i-1][j]);
				}
			}
			
			System.out.println(dp[M][N]);
		}
	}
	
}
