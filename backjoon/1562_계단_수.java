import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static long[][][] dp;
    static final int MOD = 1_000_000_000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        dp = new long[N+1][10][1<<10];

        for(int i=1; i<10; i++){
            dp[1][i][1 << i] = 1;
        }


        for(int i=2; i<=N; i++){

            for(int j=0; j<10; j++){

                for(int bit=0; bit < (1<<10); bit++){

                    int nBit = bit | (1 << j);

                    if(j==0){
                        dp[i][j][nBit] += dp[i-1][j+1][bit] % MOD;
                    }
                    else if(j==9){
                        dp[i][j][nBit] += dp[i-1][j-1][bit] % MOD;
                    }
                    else{
                        dp[i][j][nBit] += dp[i-1][j+1][bit] % MOD + dp[i-1][j-1][bit] % MOD;
                    }
                    dp[i][j][nBit] %= MOD;
                }
            }
        }

        long answer = 0;
        for(int i=0; i<10; i++){
            answer += dp[N][i][(1<<10)-1];
            answer %= MOD;
        }

        System.out.println(answer%MOD);
        System.out.println( (1<<10) -2);

    }
}

/*
* 비트마스킹 + 다이나믹 프로그래밍 문제
*
* @@@ 해당 문제의 경우 풀이에 실패하여 답을 참고하였습니다. @@@
*
* 풀이의 경우 10844 쉬운 계단 수 문제와 같이
* dp[자릿수][0~9 숫자] = dp[자릿수-1][0~9 숫자 -1 ] + dp[자릿수-1][0~9 숫자 +1] 형태를 취했습니다. (0과 9는 따로 취급)
* 다만 가장 큰 난관은 "어떻게 0~9까지 포함하는지 기억하게 만들 수 있을까?" 였습니다.
*
* 결국 알고리즘 분류를 열어서 확인한 결과 비트필드를 이용한 다이나믹 프로그래밍이라는 분류라는 것을 알게되었는데 해당 알고리즘을 잘 몰라서 답을 참고하였습니다.
*
* 문제의 핵심 매커니즘은 0~9까지의 포함을 비트 필드로 나타내는 것 이었습니다.
* 즉 만약 0~9 중 8 6 4 3 을 포함하고 있다고 해보겠습니다.
* 먼저 처음부터 비어있는 비트를 나타내고 0000000000(2) 앞에서부터 9876543210을 나타내고 있습니다.
* 8 6 4 3 위치에 있는 비트를 1로 바꿔서 나타내게되면 0101011000(2) 이와 같게 됩니다.
*
* 이를 바탕으로 모든 숫자를 포함하는 경우를 포함하는 즉 1111111111(2)를 만족하는 계단 수의 갯수를 세면 되는 문제였습니다.
*
* */