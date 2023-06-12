package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_RGB거리_1149 {
	
	static int N;
	static int[][] cost, memoi;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		cost = new int[N+1][3];
		for(int i=1; i<=N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			cost[i][0] = Integer.parseInt(st.nextToken());
			cost[i][1] = Integer.parseInt(st.nextToken());
			cost[i][2] = Integer.parseInt(st.nextToken());
		}
		
		memoi = new int[N+1][3]; // 처음에 R, G, B 를 각각 선택한 경우를 모두 따져본다.
		// memoi[i][0] i 번째 R 선택, memoi[i][1] i번째 G, memoi[i][2] = B
		memoi[1][0] = cost[1][0]; // 첫번 째 R 선택
		memoi[1][1] = cost[1][1]; // 첫번 째 G 선택
		memoi[1][2] = cost[1][2]; // 첫번 째 B 선택
		
		for(int i=2; i<=N; i++) {
			// i 번째에 R 을 선택할 경우, i-1 번째에 G, B가 선택되어야 한다. 이 중 최소 비용을 선택
			memoi[i][0] = Math.min(memoi[i-1][1], memoi[i-1][2]) + cost[i][0];
			
			// i 번째에 G 을 선택할 경우, i-1 번째에 R, B가 선택되어야 한다. 이 중 최소 비용을 선택
			memoi[i][0] = Math.min(memoi[i-1][1], memoi[i-1][2]) + cost[i][0];
			
			// i 번째에 B 을 선택할 경우, i-1 번째에 R, G가 선택되어야 한다. 이 중 최소 비용을 선택
			memoi[i][0] = Math.min(memoi[i-1][1], memoi[i-1][2]) + cost[i][0];
		}
		
		// 3가지로 배열을 만들어서 따져 본 것은 처음에 어느색을 선택했을 때 가장 최소 비용인지 모르기 때문에 세 가지 색을 모두 따져본다.
		System.out.println(Math.min(Math.min(memoi[N][0], memoi[N][1]), memoi[N][2]));
	}

}
