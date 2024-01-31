import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, weight[], marble[];
    static boolean valid[];
    static boolean[][] dp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        weight = new int[N+1];

        st = new StringTokenizer(br.readLine());

        for(int i=1; i<=N; i++){
            weight[i] = Integer.parseInt(st.nextToken());
        }

        M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        marble = new int[M];

        for(int i=0; i<M; i++){
            marble[i] = Integer.parseInt(st.nextToken());
        }

        dp = new boolean[N+1][40001];

        for(int i=1; i<=N; i++){

            dp[i][weight[i]] = true;

            for(int j=1; j<=15000; j++){


                if(dp[i-1][j]){

                    dp[i][j] = dp[i-1][j];

                    if(j+weight[i] <= 15000) dp[i][j+weight[i]] = true;

                    if(j-weight[i] > 0){
                        dp[i][j-weight[i]] = true;
                    }

                    if(weight[i]-j >0){
                        dp[i][weight[i]-j] = true;
                    }

                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for(int var : marble){
            if(dp[N][var]) sb.append("Y ");
            else sb.append("N ");
        }

        System.out.print(sb.toString());

    }

}

/*
* 다이나믹 프로그래밍 + 배낭 문제
*
* !! 해당 문제는 풀이에 실패하여 답을 참고했습니다 !!
*
* 문제의 포인트는 무게추를 빼서 혹은 더해서 만들 수 있는 경우의 수를 메모이제이션으로 나타내는 것 이었습니다.
* 이 문제를 풀기 위해 무게 추를 어떻게 선택하게 할까 고민을 했는데 이를 DP에 적용하지를 못해서 풀이에 실패하였습니다.
*
* 제가 참고한 포인트 부분은 아래 3가지 조건으로 위의 포인트를 해결한 부분입니다.
*
* if(j+weight[i] <= 15000) dp[i][j+weight[i]] = true;
*
* if(j-weight[i] > 0){
*   dp[i][j-weight[i]] = true;
* }
*
* if(weight[i]-j >0){
*       dp[i][weight[i]-j] = true;
* }
*
* 이 3가지 if문을 통해 내 앞에 만들어진 무게의 경우를 가지고 다음에 선택된 무게추를 뺏을때 더했을 때 등의 무게를 만들 수 있는 경우를 모두 기억하게 할 수 있습니다.
*
* 이번 문제를 통해서 이러한 해결방법이 있다는 점을 깨닫게 되었습니다.
*
* */