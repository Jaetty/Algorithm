package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SW_규영이와인영이의카드게임_6808_2 {
    
    static int T, win, lose, N = 9;
    static int[] input = new int[19];
    static int[] guCard = new int[9];    // 테케에서 고정
    static int[] inCard = new int[9];    // guCard에 없는 번호를 입력 <= 순열을 만들기 위해 선택할 수 있는 src    
    static boolean[] select = new boolean[N];
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        
        for(int t = 1; t <= T; t++) {
            // 초기화
            win = 0;
            lose = 0;
            Arrays.fill(input, 0);
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 규영이카드
            int num = 0;
            for(int i = 0; i < N; i++) {
                num = Integer.parseInt(st.nextToken());
                guCard[i] = num;
                input[num] = 1;    // 인영이카드 설정 위함
            }
            
            //인영이카드 - 순열의 src
            num = 0;    // 맨 앞
            for(int i = 1; i <= 18; i++) {
                if(input[i] == 0) inCard[num++] = i;
            }
            
            perm(0, 0, 0);
            System.out.println("#"+t+" "+win+" "+lose);
        }
        

    }
	
	static void perm(int tgtIdx, int guSum, int inSum) {
		// 기저 조건
		// 규영이의 카드로부터 임의의 카드를 순열로 완성한 경우
		if(tgtIdx == N) {
			// complete cod
			if(guSum>inSum) win ++;
			if(guSum<inSum) lose ++;
			return;
		}
		
		for(int inIdx=0; inIdx<N; inIdx++) {
			// 이미 선택된 i 제외
			if(select[inIdx]) continue;
			
			select[inIdx] = true;
			
			if(guCard[tgtIdx] > inCard[inIdx]) {
				perm(tgtIdx+1, guSum+guCard[tgtIdx]+inCard[inIdx],inSum);
			}
			else {
				perm(tgtIdx+1, guSum,inSum+guCard[tgtIdx]+inCard[inIdx]);
			}
			
			select[inIdx] = false;
		}
		
	}

}
