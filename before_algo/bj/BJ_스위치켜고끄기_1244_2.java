package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 입력을 받으면서 처리할 수 있는 문제는 반드시 그렇게 처리하도록 노력 ( 입력을 위한 자료구조를 만들 필요 X)
// switches : int -> boolean
public class BJ_스위치켜고끄기_1244_2 {
	static int N, COUNT;
	static boolean[] switches; // 스위치 배열
	static int gender, num; // 남여, 부여받은 숫자 <= local variable 무방 // 입력 자료구조를 만들어서 받아놓고 하나씩 처리 X 입력받으면 바로 처리
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		switches = new boolean[N+1]; // 0 dummy, 문제의 번호가 1부터 시작
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		for(int i=1; i<=N; i++) {
//			switches[i] = Integer.parseInt(st.nextToken()) == 1 ? true : false;
			switches[i] = st.nextToken().equals("1"); // false 는 boolean 의 default
		}
		
		COUNT = Integer.parseInt(br.readLine());
		for(int i=0; i<COUNT; i++) {
			st = new StringTokenizer(br.readLine());
			gender = Integer.parseInt(st.nextToken()); // 현재 성별
			num = Integer.parseInt(st.nextToken()); // 현재 받은 번호
			
			if(gender == 1) {
				male();
			}
			else if(gender == 2) {
				female();
			}
		}
		
		// 결과 출력
		for(int i=1; i<=N; i++) {
			System.out.print(switches[i] ? 1 : 0);
			if(i!=N) {
				if(i%20==0) System.out.println(); // 개행
			}else {
				System.out.print(" "); // 왜 안됨?
			}
		}
		
	}

	// 자기가 받은 번호의 배수 바꿔야한다.
	// 1 2 3 4 5 6 7 8
	// num == 3
	static void male() {
		
//		for(int i=1; i<=N; i++) {
//			if(i%num==0) { // 스위치 번호가 N의 배수이면
//				switches[i] = switches[i] == 0 ? 1 : 0; // 반대로
//			}
//		}
		
		for(int i=num; i<=N; i+=num) { // 첫번째 이득 (배수 인덱스만 찾아 간다.)
			// if 비교 X 두 번째 이득
			switches[i] = !switches[i];
		}
	}
	
	static void female() {
		// 자신이 받은 번호의 스위치를 우선 변경
		// 자신 좌우 대칭인 것을 찾아서 변경
		switches[num] = !switches[num];
		
		// before
//		int cnt = 1;
//		while(num-cnt >=1 && num+cnt <= N) {
//			if(switches[num-cnt]==switches[num+cnt]) {
//				switches[num-cnt] = switches[num-cnt] == 0 ? 1:0;
//				switches[num+cnt] = switches[num+cnt] == 0 ? 1:0;
//			}
//			else {
//				break;
//			}
//			cnt++;
//		}
		
		int left = num-1;
		int right = num+1;
		while(left >=1 && right <= N) {
			if(switches[left]==switches[right]) {
				switches[left] = !switches[left];
				switches[right] = switches[left];
				// or
//				switches[right] = switches[left] = !switches[left];
			}
			else {
				break;
			}
			left--;
			right++;
		}
		
	}
}
