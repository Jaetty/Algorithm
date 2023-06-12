package basic;

public class BASIC_막대연결하기 {
	// f(6)
	static int[] memoi = new int[7];
	public static void main(String[] args) {
		memoi[1] = 2;
		memoi[2] = 5;
		// f(i) = f(i-1) , f(i-2)..... 규칙
		for(int i=3; i<=6; i++) {
			// i 길이를 만들기 위해서는
			// 우리가 가진 막대(수단/선택) 은 2가지 <= 길이가 1인 막대, 길이가 2인 막대
			// => 이전 길이 현재 길이를 완성할 때 1 늘어나거나, 2 늘어나거나
			// 길이 1개 짜리를 연결하는 경우의 수는 i-1 길이의 경우의 수 X 길이가 1인 막대의 수(2)
			// 길이 2개 짜리를 연결하는 경우의 수는 i-2 길이의 경우의 수 X 길이가 2인 막대의 수(1)
			memoi[i] = memoi[i-1]*2 + memoi[i-2];
		}
		
		System.out.println(memoi[6]);
	}

}
