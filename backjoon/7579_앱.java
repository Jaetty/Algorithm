import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N,M;
    static int[] memory, cost;
    static long[][] dp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        memory = new int[N+1];
        cost = new int[N+1];

        st = new StringTokenizer(br.readLine());
        StringTokenizer st2 = new StringTokenizer(br.readLine());

        for(int i=1; i<=N; i++){
            memory[i] = Integer.parseInt(st.nextToken());
            cost[i] = Integer.parseInt(st2.nextToken());
        }

        dp = new long[N+1][100001];

        int answer = Integer.MAX_VALUE;

        for(int i=1; i<=N; i++){

            for(int j=1; j<=10000; j++){

                if(cost[i]<=j){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-cost[i]]+memory[i]);
                }
                else dp[i][j] = dp[i-1][j];

                if(dp[i][j]>=M) answer = Math.min(answer, j);
            }
        }

        System.out.println(answer);

    }

}

/*
 * 다이나믹 프로그래밍 문제
 *
 * 해당 문제는 배낭문제(knapsack)를 안다는 전제에서 설명하겠습니다.
 *
 * knapsack의 응용 문제입니다. 문제의 핵심 포인트는 어느 값을 무게 값으로 두어야할까.
 *
 * 배낭 문제에서 무게값에 대하여 가장 가치가 높은 값들을 넣었는데 문제의 경우 그런 무게에 해당할 것 같은 M값이 너무 커서 시간초과가 당연하다는 문제가 있습니다.
 * 그렇다면 무게에 해당하는 값을 다른 값으로 두어야하지 않을까 라는 의문이 듭니다.
 *
 * 거기서 해결방법은 '시간에 해당하는 값을 무게처럼 사용하면 된다' 입니다.
 * N과 c1...cN 이 모두 0이상 100 이하의 값입니다. 그러면 시간 최대 값은 100*100 = 10000이라는 뜻이기 때문에 최대 100*10000번 반복하게 되면 시간상 크게 문제가 없습니다.
 * 그렇다면 시간에 관하여 가장 용량을 많이 쓰는 값을 dp를 통해 기록하다가 목표하는 M보다 큰 값을 가진 시간 값 중 가장 최소 값을 출력하면 됩니다.
 *
 *
 * */