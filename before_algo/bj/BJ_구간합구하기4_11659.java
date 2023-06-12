package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_구간합구하기4_11659 {
	
	static int N, M;
	static int[] accu; // memoization
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		accu = new int[N + 1]; // 0 dummy
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<=N; i++) {
			accu[i] = Integer.parseInt(st.nextToken() + accu[i-1]);
		}
		
		StringBuilder sb = new StringBuilder();
		// M개의 구간합
		for(int i = 1; i<M; i++) {
			// 두 개
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			
//			System.out.println(accu[to] - accu[from]);
			sb.append(accu[to] - accu[from-1]).append("\n");
			
		}
		System.out.println(sb);
	}

}
