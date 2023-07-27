import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][] dp;
    static int N,C;
    static int[][] arr;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        int answer = Integer.MAX_VALUE;

        dp = new int[N+1][100001];
        arr = new int[N+1][2];

        for(int i=1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            arr[i][0] = a;
            arr[i][1] = b;
        }

        for(int i=1; i<=N; i++){
            for(int j=1; j<=100000; j++){
                if(arr[i][0]<=j){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-arr[i][0]]+arr[i][1]);
                    if(dp[i][j]>=C) answer = Math.min(answer, j);
                }
                else dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);

            }
        }
        System.out.println(answer);
    }
}

/*
 * 다이나믹 프로그램 문제
 *
 * 해당 문제는 배낭문제를 응용한 문제입니다.
 * 배낭 문제를 안다는 전제하에 설명할 생각이고 모르시다면 배낭 문제 (knapsack)을 검색하시면 알아보실 수 있습니다.
 * 배낭 문제 : https://en.wikipedia.org/wiki/Knapsack_problem
 *
 * 해당 문제의 경우 저는 배낭의 무게에 해당하는 부분을 비용으로 맞추었습니다.
 * 비용은 최대 C의 최대값과 N의 최대값인 1000*100으로 두었고
 * 각 비용마다 둘 수 있는 최대의 고객 수를 기록하도록 하였습니다.
 *
 * 그리고 고객 수가 갱신될때마다 목표인 C보다 크다면 비용이 가장 작은 값을 기록합니다.
 * 이러한 풀이로 해당 문제를 풀 수 있었습니다.
 *
 * */