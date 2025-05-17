import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long dp[][];

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        dp = new long[31][31];
        setDp();

        while(true){

            int N = Integer.parseInt(br.readLine());
            if(N == 0) break;
            sb.append(dp[N][N]).append("\n");

        }
        System.out.print(sb.toString());
    }

    static void setDp() {

        for (int w = 1; w <= 30; w++) {
            for (int h = 0; h <= 30; h++) {
                if (h > w) break;
                if(h==0){
                    dp[w][h] = 1;
                    continue;
                }
                dp[w][h] = dp[w][h-1] + dp[w-1][h];
            }
        }

    }

}

/*

4811 알약

dp 문제

@ 해당 문제는 풀이를 참고했습니다.

문제의 핵심 포인트는 다음과 같습니다.
1. W가 나온 이후에야 H가 1개 나올 수 있다. 즉, H는 W의 갯수와 같거나 작아야한다.
2. W와 H는 각각 최대 N개 나올 수 있다.

@ 저는 처음에 위의 포인트를 바탕으로 1차원 배열만 이용하여 점화식을 찾기 위해 노력했지만 실패하였습니다.
@ 결국, 다른 사람의 풀이에서 2차원 배열을 이용한 것을 보고 힌트를 받아 풀이를 진행하였습니다.
@ (1차원 배열을 이용해서 풀이하신 분들도 있지만 2차원 배열의 풀이가 제가 원하던 방식이라 2차원을 채택했습니다.)

먼저 dp[W][H] 배열은 경우의 수 배열입니다.
각각, W가 x개 일 때, H를 y개 사용하여 만들 수 있는 문자열의 경우의 수를 나타냅니다.

예시로 N=5일 때의 dp 배열을 만들어보면 다음과 같습니다.

W/H     0   1   2   3   4   5
0       0   0   0   0   0   0
1       1   1   0   0   0   0
2       1   2   2   0   0   0
3       1   3   5   5   0   0
4       1   4   9   14  14  0
5       1   5   14  28  28  42

표를 중심으로 해설해보자면 이렇습니다.
W가 1개 있다고 해보겠습니다. 그러면 H는 1번 포인트에 따라 최소 0개 최대 1개 나올 수 있습니다.

문자열로 표현해보자면 W가 1개 있고 H가 0개 있다면? ["W"]로 문자열이 1개입니다.
W가 1개에, H가 1개 있다면? 이 경우도 ["WH"]로 문자열이 1개 입니다.
이를 경우의 수 배열에 넣으면 dp[W 갯수][H 갯수] : dp[1][0] =1, dp[1][1] = 1로 표현할 수 있게 됩니다.

그 다음, W가 2개 있다고 해보겠습니다. ("WW")
H는 0~2개까지 있을 수 있습니다. H가 0일 때는 ["WW"], H가 1일 때는 ["WHW", "WWH"], H가 2개면 ["WHWH", "WWHH"]가 됩니다.

그런데 여기서 보면 경우의 수의 규칙성을 알 수 있는 것이 dp[w][h] = dp[w-1][h] + dp[w][h-1]라는 점화식이 생기는 점입니다.
이를 바탕으로 핵심 포인트를 고려하여

if (h > w) break; // h의 갯수는 w 갯수 이하.
if(h==0){ // H가 하나도 없는 경우는 N개의 길이로 "W"로만 이루어진 1개의 문자열만 있음.
    dp[w][h] = 1;
    continue;
}
dp[w][h] = dp[w][h-1] + dp[w-1][h]; // 위에서 구한 점화식을 적용

이 dp 점화식 코드를 구현하게되면 문제를 해결할 수 있습니다.

*/