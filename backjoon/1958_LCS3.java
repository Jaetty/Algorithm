import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = new String[3];

        for(int i=0; i<3; i++){
            input[i] = " " + br.readLine();
        }


        int N = input[0].length()-1, M= input[1].length()-1, K= input[2].length()-1;
        int[][][] dp = new int[N+1][M+1][K+1];

        for(int i=1; i<=N; i++){

            for(int j=1; j<=M; j++){

                for(int k=1; k<=K; k++){

                    if(input[0].charAt(i) == input[1].charAt(j) && input[1].charAt(j) == input[2].charAt(k)){
                        dp[i][j][k] = dp[i-1][j-1][k-1]+1;
                    }
                    else{
                        dp[i][j][k] = Math.max(dp[i-1][j][k], Math.max(dp[i][j-1][k], dp[i][j][k-1]));
                    }

                }
            }
        }

        System.out.print(dp[N][M][K]);

    }

}
/*
 * 다이나믹 프로그래밍 문제
 *
 * 이 문제의 풀이법을 떠올릴려면 먼저 LCS1~2 문제를 풀이할 줄 알아야합니다.
 * LCS를 풀었다는 전제로 하면 이 문제의 경우 3개의 문장에 대해서 LCS를 적용해야하니, 3차원 배열을 쓰면 된다는 점을 알 수 있습니다.
 *
 * 그리고 핵심은 다음 2가지 조건을 적용하면 풀이할 수 있습니다.
 * input[0].charAt(i) == input[1].charAt(j) == input[2].charAt(k)
 * dp[i][j][k] = max( dp[i-1][j][k], dp[i][j-1][k], dp[i][j][k-1] );
 *
 *
 * */