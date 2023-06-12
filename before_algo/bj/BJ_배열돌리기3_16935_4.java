package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// Array
// 연산 1~6까지 다르게 처리 후 출력
public class BJ_배열돌리기3_16935_4 {

	static int N, M, R;
	static int[] line, line2; // 1차원 배열로 표현
	static int[] src, tgt; // from to 
	static int[] P;
	static int rotate;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] read = br.readLine().split(" ");
		
		N = Integer.parseInt(read[0]);
		M = Integer.parseInt(read[1]);
		R = Integer.parseInt(read[2]);
		
		line = new int[N*M];
		line2 = new int[N*M];
		src = line2; // for change()
		tgt = line;
		
		for (int i = 0; i < N; i++) {
			String[] str = br.readLine().split(" ");
			for (int j = 0; j < M; j++) {
				line[i*M + j] = Integer.parseInt(str[j]);
			}
		}
		
		String[] line = br.readLine().split(" ");
		P = new int[R];
		
		for (int i = 0; i < R; i++) {
			P[i] = Integer.parseInt(line[i]);
		}
		
		for (int i = 0; i < R; i++) {
			
			change();
			switch(P[i]) {
				case 1 : do1(); break;
				case 2 : do2(); break;
				case 3 : do3(); break;
				case 4 : do4(); break;
				case 5 : do5(); break;
				case 6 : do6(); break;
			}				
		}

		print();

	}

	// 상하 반전
	static void do1() {
		int idx = 0;
		// 아래 -> 위 방향으로 N 만큼 반복
		for (int i = N-1; i >=0 ; i--) {
			// 좌 -> 우 방향으로 M 만큼 반복 
			for (int j = 0; j < M; j++) {
				tgt[idx++] = src[ i*M + j ];
			}
		}
		
		//printTemp(1);
	}
	
	// 좌우 반전
	static void do2() {
		int idx = 0;
		// 위 -> 아래 방향으로 N 만큼 반복
		for (int i = 0; i < N ; i++) {
			// 우 -> 좌 방향으로 M 만큼 반복 
			for (int j = M-1; j >= 0; j--) {
				tgt[idx++] = src[ i*M + j ];
			}
		}
	}
	
	// 90 오른쪽 ( 시계 방향 )
	// 행 <-> 열
	static void do3() {
		int idx = 0;
		
		// 열 만큼 반복 - 점점 증가 ( + i )
		for (int i = 0; i < M ; i++) {
			// 행 만큼 반복 - 큰 수 부터
			for (int j = N-1; j >= 0; j--) {
				// 큰 수
				tgt[idx++] = src[ j*M + i ];
			}
		}
		int temp = N;
		N = M;
		M = temp;
	}
	
	// 90 왼쪽 ( 반시계 방향 )
	// 행 <-> 열
	static void do4() {
		int idx = 0;
		
		// 열 만큼 반복 - 점점 감소 ( - i )
		for (int i = 0; i < M ; i++) {
			// 행 만큼 반복 - 작은 수 부터
			for (int j = 0; j < N; j++) {
				// 큰 수
				tgt[idx++] = src[ ((j+1)*M - 1) - i ];
			}
		}
		
		int temp = N;
		N = M;
		M = temp;
	}
	
	// 1-2-3-4-1
	// 시계방향
	static void do5() {
		
		int idx = 0;
		
		// 위  ( 1 과 4 )(원 배열의 앞 부분)
		for (int i = 0; i < N/2; i++) {
			// 앞 반 ( 4 부분 먼저 )
			for (int j = 0; j < M/2; j++) {
				tgt[idx++] = src[ M*(N/2) + M*i + j ];
			}
			// 뒤 반 ( 1 부분 나중 )
			for (int j = 0; j < M/2; j++) {
				tgt[idx++] = src[ M*i + j ];
			}
		}
		
		// 아래  ( 2 와 3 )(원 배열의 뒷 부분)
		for (int i = 0; i < N/2; i++) {
			// 앞 반 ( 3 부분 먼저 )
			for (int j = M/2; j < M; j++) {
				tgt[idx++] = src[ M*(N/2) + M*i + j ];
			}
			// 뒤 반 ( 2 부분 나중 )
			for (int j = M/2; j < M; j++) {
				tgt[idx++] = src[ M*i + j ];
			}
		}
	}
	
	// 1-4-3-2-1
	// 반시계 방향
	static void do6() {
		
		int idx = 0;
		
		// 위  ( 1 과 4 )(원 배열의 앞 부분)
		for (int i = 0; i < N/2; i++) {
			
			// 뒤 반
			for (int j = M/2; j < M; j++) {
				tgt[idx++] = src[ M*i + j ];
			}

			// 앞 반
			for (int j = M/2; j < M; j++) {
				tgt[idx++] = src[ M*(N/2) + M*i + j ];
			}			
		}
		
		// 아래  ( 2 와 3 )(원 배열의 뒷 부분)
		for (int i = 0; i < N/2; i++) {
			// 뒤 반
			for (int j = 0; j < M/2; j++) {
				tgt[idx++] = src[ M*i + j ];
			}
			// 앞 반
			for (int j = 0; j < M/2; j++) {
				tgt[idx++] = src[ M*(N/2) + M*i + j ];
			}			
		}
	}
	
	static void change() {
		if( src == line ) {
			src = line2;
			tgt = line;
		}else {
			src = line;
			tgt = line2;
		}
	}

	static void print() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M-1; j++) {
				System.out.print(tgt[i*M + j] + " ");
			}
			System.out.println(tgt[i*M + M-1]);
		}
	}

}