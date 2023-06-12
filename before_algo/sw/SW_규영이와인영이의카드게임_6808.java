package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SW_규영이와인영이의카드게임_6808 {
	
	static int T,win,lose,N=9;
	static int[] input = new int[19];
	static int[] guCard = new int[9]; // 테케에서 고정
	static int[] inCard = new int[9]; // guCard이 없는 번호를 입력 <= 순열을 만들기 위해 선택할 수 있는 src
	static int[] tgt = new int[9];
	
	static boolean[] select = new boolean[N];
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			win = 0;
			lose = 0;
			Arrays.fill(input, 0);
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int num = 0;
			for(int i=0; i < N; i++) {
				num = Integer.parseInt(st.nextToken());
				guCard[i] = num;
				input[num] = 1;
			}
			
			num = 0;
			for(int i=1; i<=18; i++) {
				if(input[i]==0) inCard[num++] = i;
			}
			
		}
		
	}
	
	static void perm(int tgtIdx) {
		// 기저 조건
		// 규영이의 카드로부터 임의의 카드를 순열로 완성한 경우
		if(tgtIdx == N) {
			// complete cod
			check();
			return;
		}
		
		for(int i=0; i<N; i++) {
			// 이미 선택된 i 제외
			if(select[i]) continue;
			
			tgt[tgtIdx] = inCard[i];
			select[i] = true;
			perm(tgtIdx+1); // 현재 i를 선택해ㅓㅅ 모든 경우의 순열을 따져 본 후에 return 되어서 돌아오면 다음 i의 선택으로 따져봄
			select[i] = false;
		}
		
	}
	static void check() {
		int guSum = 0;
		int inSum = 0;
		for(int i=0; i<N; i++) {
			if(guCard[i] > tgt[i]) guSum += guCard[i] + tgt[i];
			else inSum += guCard[i] + tgt[i];
		}
		if(guSum>inSum) win++;
		else if(guSum<inSum) lose++;
	}

}
