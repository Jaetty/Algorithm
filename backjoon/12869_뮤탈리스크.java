import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[3];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int[][][] dp = new int[61][61][61];

        for(int i=60; i>=0; i--){
            for(int j=60; j>=0; j--){
                for(int k=60; k>=0; k--){
                    dp[i][j][k] = 1000000000;
                }
            }
        }

        dp[arr[0]][arr[1]][arr[2]] = 0;

        for(int i=arr[0]; i>=0; i--){
            for(int j=arr[1]; j>=0; j--){
                for(int k=arr[2]; k>=0; k--){

                    dp[i-9 < 0 ? 0 : i-9][j-3 < 0 ? 0 : j-3][k-1 < 0 ? 0 : k-1] = Math.min(dp[i-9 < 0 ? 0 : i-9][j-3 < 0 ? 0 : j-3][k-1 < 0 ? 0 : k-1], dp[i][j][k]+1);
                    dp[i-9 < 0 ? 0 : i-9][j-1 < 0 ? 0 : j-1][k-3 < 0 ? 0 : k-3] = Math.min(dp[i-9 < 0 ? 0 : i-9][j-1 < 0 ? 0 : j-1][k-3 < 0 ? 0 : k-3], dp[i][j][k]+1);
                    dp[i-3 < 0 ? 0 : i-3][j-9 < 0 ? 0 : j-9][k-1 < 0 ? 0 : k-1] = Math.min(dp[i-3 < 0 ? 0 : i-3][j-9 < 0 ? 0 : j-9][k-1 < 0 ? 0 : k-1], dp[i][j][k]+1);
                    dp[i-3 < 0 ? 0 : i-3][j-1 < 0 ? 0 : j-1][k-9 < 0 ? 0 : k-9] = Math.min(dp[i-3 < 0 ? 0 : i-3][j-1 < 0 ? 0 : j-1][k-9 < 0 ? 0 : k-9], dp[i][j][k]+1);
                    dp[i-1 < 0 ? 0 : i-1][j-9 < 0 ? 0 : j-9][k-3 < 0 ? 0 : k-3] = Math.min(dp[i-1 < 0 ? 0 : i-1][j-9 < 0 ? 0 : j-9][k-3 < 0 ? 0 : k-3], dp[i][j][k]+1);
                    dp[i-1 < 0 ? 0 : i-1][j-3 < 0 ? 0 : j-3][k-9 < 0 ? 0 : k-9] = Math.min(dp[i-1 < 0 ? 0 : i-1][j-3 < 0 ? 0 : j-3][k-9 < 0 ? 0 : k-9], dp[i][j][k]+1);

                }
            }
        }

        System.out.println(dp[0][0][0]);

    }

}

/*
 * 다이나믹 프로그래밍 문제
 *
 * @ 해당 문제의 경우 BFS로도 풀이가 가능한가 봅니다. 저 같은 경우 BFS로 풀고 60 60 60으로 테스트했더니 안돼서 DP로 바꿨습니다.
 *
 * 해당 문제의 경우 풀이 설명이 어려워서 예시로 적어보겠습니다.
 *
 * 먼저 다음과 같은 입력이 있다면.
 * 3
 * 12 10 4
 *
 * 체력이 각각 12, 10, 4 일 때 뮤탈리스크는 공격하지 않았으므로 이를 배열로 표현해서 dp[12][10][4] = 0. (dp는 뮤탈의 공격 횟수 기억)
 * 그러면 여기서 12->10->4와 12->4->10, 10->12->4... 이렇게 6가지 공격의 경우가 나옵니다.
 * 그러면 이를 각각 배열로 표현해서 6가지 공격을 했을 때 최솟값을 갱신해주면 되지 않을까 싶었습니다.
 *
 * 쉽게 예를 들어 2가지 경우만 있다고 해보고 -y을 하거나 -z를 해야한다고 하겠습니다.
 * 그러면 마치 다음과 같이 될 것 같았습니다.
 *
 * x                0
 *                 / \
 * x-y            1   \
 * x-z                 1
 * 다음과 같이 내가 있는 위치에서 어떤 숫자를 고려한 값, 즉 dp[x-y] = min(dp[x-y], dp[x]+1) / dp[x-z] = min(dp[x-z], dp[x]+1)
 * 이렇게 갱신해주면 되지 않을까? 그리고 이 원리를 이 문제에 적용만 해주면 되겠다고 판단했습니다.
 *
 * 그래서 dp[][][]이 있고 각자 6가지 경우의 수를 다 고려해주면 됩니다.
 * 여기서 0을 넘어갈 땐 그대로 0으로 표현하게끔 3항연산자를 이용하여 만들어주었고 문제를 풀이할 수 있었습니다.
 *
 *
 * */