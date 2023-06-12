package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_파이프옮기기_17070_dfs {
	
	static int N, cnt;
	static int[][] map;
	static int[][][] delta = {
			{{1,1}, {0,1}, {1,0}}, // 가로 : 0 => 대각선, 가로, X(dummy)
			{{1,1}, {0,1}, {0,0}}, // 가로 : 1 => 대각선, 가로, X(dummy)
			{{1,1}, {0,0}, {1,0}}, // 세로 : 2 => 대각선, X(dummy), 세로
	};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new int[N+1][N+1];
		for(int i=1; i<=N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=1; j<=N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		dfs(1,2,1);
		System.out.println(cnt);
		
	}
	
	static void dfs(int y, int x, int d) {
		// 기저 조건
		if(y==N && x == N) {
			cnt++;
			return;
		}
		
		for(int i=0; i<3; i++) {
			int ny = y+delta[d][i][0];
			int nx = x+delta[d][i][1];
			
			// dummy
			if(ny==y && nx == x) continue;
			
			// 범위
			if(ny>N || nx>N || map[ny][nx]==1) continue;
			
			// 대각선일 경우는 2개 더 따져야 한다.
			if(i==0 && (map[ny][nx-1]==1 || map[ny-1][nx]==1)) continue;
			
			dfs(ny,nx,i);
		}
	}

}