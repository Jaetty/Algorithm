import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int[][] list;
        StringTokenizer st;

        for(int tc=0; tc<3; tc++) {

            int N = Integer.parseInt(br.readLine());
            int sum = 0;
            list = new int[N][2];

            for(int n=0; n<N; n++) {
                st = new StringTokenizer(br.readLine());

                int val = Integer.parseInt(st.nextToken());
                int cnt = Integer.parseInt(st.nextToken());

                sum += val * cnt;
                list[n] = new int[] {val, cnt};

            }

            if(sum % 2 !=0){
                sb.append(0).append("\n");
                continue;
            }

            if(dp(list, sum/2)){
                sb.append(1).append("\n");
            }
            else{
                sb.append(0).append("\n");
                continue;
            }
        }
        System.out.print(sb.toString());

    }

    static boolean dp(int[][] list, int target){

        boolean[][] dp = new boolean[list.length+1][target+1];

        for(int i=1; i<=list.length; i++) {

            int[] val = list[i-1];

            // 동전을 꺼내서 그 동전의 갯수만큼 전부 true 처리 해준다.
            for(int j=1; j<=val[1]; j++){
                if(val[0]*j>target) break;
                dp[i][val[0]*j] = true;
            }

            for(int j=1; j<=target; j++) {

                // 만약 지금 동전의 값보다 작은 값이면 전 기록을 그대로 가져온다.
                if(j<val[0]){
                    dp[i][j] = dp[i-1][j];
                }
                // 만약 j값이 내 동전 값보다 크다면
                if(j>val[0]){
                    // 동전의 갯수만큼 반복하면서 만들 수 있는 경우의 수가 있는지 모두 체크함.
                    for(int k=1; k<=val[1]; k++) {
                        int tmp = val[0] * k;
                        if(tmp>j) break;
                        dp[i][j] = (dp[i-1][j-tmp] || dp[i-1][j] ||dp[i][j]);
                    }
                }

            }
        }

        return dp[list.length][target];

    }

}

/*

1943 동전 분배

배낭 문제

해당 문제의 경우 먼저 다음과 같은 풀이 방법을 생각했습니다.
1. 동전들의 합산 값이 짝수일 때만 돈을 서로 나눌 수 있다. (예시: 3,5,7,9원처럼 홀수의 경우 공평히 나누지 못한다.)
2. (합산한 값 / 2)를 동전을 조합하여 만들 수 있는지 확인해본다. (예시: 합산 200이면, 어떤 경우라도 100원을 만들 수만 있으면 된다.)

그렇다면, 이에 적합한 방법은 모든 경우를 만드는게 아니라 배낭문제를 활용하면 되겠다고 생각이 들었습니다.
배낭의 무게 부분을 동전으로 만들 수 있는 값으로 설정하고, 한 번이라도 그 값을 만들 수 있는지 확인해주었습니다.

처음엔 동전의 총 갯수만큼.. 즉 dp[동전 총 갯수][합산/2]로 배열을 만들었더니 메모리 초과가 되었습니다.
그래서, 메모리를 줄이기 위해 dp[N][합산/2] 형태로 수정하고 각각 동전에서 갯수만큼 반복하는 코드를 추가하여 문제를 해결했습니다.

*/