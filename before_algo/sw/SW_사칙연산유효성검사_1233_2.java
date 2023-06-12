package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SW_사칙연산유효성검사_1233_2 {
	
	static int N, ans;
	static char[] node;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for(int t=1; t<=10; t++) {
			N = Integer.parseInt(br.readLine());
			node = new char[N+1]; // 0 dummy
			
			for(int i = 1; i <= N; i++) {
				node[i] = br.readLine().split(" ")[1].charAt(0);
			}
			
			ans = dfs(1) ? 1 : 0; // 맨 위 (처음) 노드부터 시작
			
			System.out.println("#"+t+" "+ans);
		}
		
	}
	
	static boolean dfs(int x) { // x: index
		// 기저 조건
		if(x>N) return false; // 이전 인덱스에서 더 따져보려고 했는데 N을 초과한다. 이전이 연산자라는 의미
		
		if( Character.isDigit(node[x])) { // 현재 index 의 노드가 숫자 <= 유효하려면 자식이 없어야 한다.
			if( x*2 > N) return true;
			return false;
		}else { // 현재 index 의 노드가 연산자
			return(dfs(x*2) && dfs(x*2+1));
		}
	}

}
