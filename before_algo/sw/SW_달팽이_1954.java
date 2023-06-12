package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// 좌<--> 우 반복 
// 좌<--> 우 반복되는 중간에 상하가 반복된다.
// 좌<--> 우 한번 실행 되면 그 다음 실행되는 숫자의 수가 하나 준다.
public class SW_달팽이_1954 {

	static int T, N;
	static int[][] snail;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for( int t=1; t<=T; t++) {
			N = Integer.parseInt(br.readLine());
			snail = new int[N][N];
			
			// 시작좌표, 방향
			int y = 0;
			int x = -1; // 1이 더해질 것이므로 미리 보정
			int d = 1; // 상하좌우 개념 X, 1 : 증가, -1 : 감소
			
			int num = 1; // 시작 숫자 (계속 1씩 증가)
			
			int count = N; // 시작부터 계속 이어지는 방향에 대해 숫자를 기록할 갯수, 점점 줄어든다.
			int total = N*N; // while 문
			
			while(num <= total) {
				
				// 우 -> 좌 -> 우 -> 좌 반복
				for (int i=0; i<count; i++) {
					x += d;
					snail[y][x] = num++;
				}
				
				count --; // 우 다 기록또는 좌 다 기록하면
				
				// 하 -> 상 -> 하 -> 상.. 반복
				for (int i=0; i<count; i++) {
					y += d;
					snail[y][x] = num++;
				}
				
				// 방향 전환
				d = d * (-1);
			}
			
			System.out.println("#"+t);
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					System.out.print(snail[i][j] + " ");
				}
				System.out.println(); // 개행
			}
			
		}

	}

}
