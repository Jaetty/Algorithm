package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SW_한빈이와Spotmart_3 {
	// 삼각형모양에서 대각선으로
	static int T, N, M, ans;
	static int[] src1, src2;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());

		for(int t=1; t<=T; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			
			// 초기화
			src1 = new int[N];
			src2 = new int[N];
			
			ans = -1;
			
			// 입력 처리
			st = new StringTokenizer(br.readLine());
			for(int i=0; i<N; i++) {
				src2[i] = src1[i] = Integer.parseInt(st.nextToken());
			}
			
			// 중복 계산
//			for(int i=0; i < N; i++) {
//				for(int j=0; j < N; j++) {
//					if(i==j) continue;
//					if(src1[i] + src2[j] <= M && src1[i] + src2[j] > ans) ans = src1[i] + src2[j];
//				}
//			}
			
			// 대각선 밑으로
			for(int i=0; i < N; i++) {
				for(int j=0; j < i; j++) {
					int sum = src1[i] + src2[j]; 
					if( sum > M ) continue;
					ans = Math.max(ans, sum);
				}
			}
			
			System.out.println("#"+t+" "+ans);
		}
	}

}
