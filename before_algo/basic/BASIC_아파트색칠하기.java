package basic;

public class BASIC_아파트색칠하기 {
	
	static int memoi[] = new int[11];
	
	public static void main(String[] args) {
		// 초기 시작 data
		memoi[0] = 0;
		memoi[1] = 1;
		memoi[2] = 2;
		
		// target 번호 ( 인덱스 )
		for( int i=3; i<=10; i++) {
			// 규칙 ( 수식 )을 i 러ㅏ는 일반화 된 식으로 표현
			// 현재 ( i ) 의 경우의 수 = 이전 ( i - 1 )의 경우의 수 + 하나 더 이전 ( i - 2 ) 의 경우의 수 합 
		}
		
		System.out.println(dp(8));
		
	}

	static int dp(int n) {
		if(memoi[n]!=0) return memoi[n];
		return memoi[n] = dp(n-1) + dp(n-2);
	}
}

// DP 속도가 엄청 빠르다.
// 코드가 간결하다.

// 완탐으로 어떤 문제를 푸는 데 시간초과가 난다면?
// => 그리디 하게 푼다. (검증된 수식 등을 사용하여..)
// => dp로 푼다.