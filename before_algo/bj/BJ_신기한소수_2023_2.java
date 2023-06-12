package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class BJ_신기한소수_2023_2 {

	static int N;
	static Deque<Item> stack = new ArrayDeque<>();
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		// search(idx, num)
		// 어차피 맨 앞자리가 소수가 아니면 출발 X <= 2, 3, 5, 7
		search(2);
		search(3);
		search(5);
		search(7);
		System.out.println(sb.toString());

	}

	// idx : 자리수
	// 현재 자리수의 수가 소수이면 다음 자리수로 재귀 호출
	// 다음 자리수는 선택해서 진행 {1, 3, 7, 9} <= 짝수와 5제외
	static void search(int num) {
		
		stack.push(new Item(num,1));
		
		

		while( !stack.isEmpty()) {
			
			Item item = stack.pop();
			
			if(item.idx == N) {
				sb.append(item.n).append("\n");
				continue;
			}
			
			// 소수인지 따져보고 맞으면 재귀호출
			// 2 다음자리 1을 따지기 위해서 21을 만드는 건 2를 문자열로 바꾼 뒤 +1 하고 => 다시 숫자

			int nextNum = nextNum(item.n, 1); // 1을 고려
			if (isPrime(nextNum)) stack.add(new Item(nextNum, item.idx+1));

			nextNum = nextNum(item.n, 3); // 3을 고려
			if (isPrime(nextNum)) stack.add(new Item(nextNum, item.idx+1));
			
			nextNum = nextNum(item.n, 7); // 7을 고려
			if (isPrime(nextNum)) stack.add(new Item(nextNum, item.idx+1));
			
			nextNum = nextNum(item.n, 9); // 9을 고려
			if (isPrime(nextNum)) stack.add(new Item(nextNum, item.idx+1));
			
		}
		
	}

	// 개선된 소수 판별법
	static boolean isPrime(int num) {
		if (num == 1)
			return false;
		int sqrt = (int) Math.sqrt(num);
		for (int i = 2; i <= sqrt; i++) {
			if (num % i == 0)
				return false;
		}
		return true;
	}

	static int nextNum(int num, int next) {

		return num * 10 + next;
	}
	
	static class Item{
		int n;
		int idx;
		
		public Item(int n, int idx) {
			this.n = n;
			this.idx = idx;
		}

		@Override
		public String toString() {
			return "Item [n=" + n + ", idx=" + idx + "]";
		}
		
	}

}
