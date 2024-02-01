import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, time;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        time = Integer.parseInt(st.nextToken());
        int[][] dp = new int[N+1][time+1];

        for(int i=1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            int study = Integer.parseInt(st.nextToken());
            int point = Integer.parseInt(st.nextToken());

            for(int j=1; j<=time; j++){

                if(j<study){
                    dp[i][j] = dp[i-1][j];
                }
                else if(j==study){
                    dp[i][j] = Math.max(point, dp[i-1][j]);
                }
                else{
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);

                    if(j-study>0){
                        dp[i][j] = Math.max(dp[i-1][j-study]+point, dp[i][j]);
                    }
                }
            }
        }

        System.out.println(dp[N][time]);

    }

}

/*
* 다이나믹 프로그래밍 + 배낭 문제
*
* 해당 문제는 전형적인 배낭 문제로 배낭 문제를 알고 있다면 쉽게 풀이가 가능합니다.
*
* 배낭문제의 포인트는 한정된 무게의 배낭에 가장 값이 높을 수 있게 배낭에 물건을 채울 수 있는 방법을 구하는 것 입니다.
*
* 배낭 문제의 경우 설명이 길게 필요해서 주석으로만 나타내기는 어렵습니다.
* 그러므로 제가 예전에 배낭문제를 공부할 때 참고했던 다른 개발자님들의 블로그 링크를 아래에 올립니다.
* https://gsmesie692.tistory.com/113
* https://jeonyeohun.tistory.com/86
*
*
*
* */