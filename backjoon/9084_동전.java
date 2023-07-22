import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

         int T = Integer.parseInt(br.readLine());
         StringBuilder sb = new StringBuilder();

         for(int tc=0; tc<T; tc++){

             int N = Integer.parseInt(br.readLine());
             int coin[] = new int[N+1];
             StringTokenizer st = new StringTokenizer(br.readLine());

             for(int i=1; i<=N; i++){
                 coin[i] = Integer.parseInt(st.nextToken());
             }

             int M = Integer.parseInt(br.readLine());

             int[][] dp = new int[N+1][M+1];

             for(int i=1; i<=N; i++){
                 for(int j=1; j<=M; j++){
                     if(j==coin[i]){
                         dp[i][j] = dp[i-1][j]+1;
                     }
                     else if(coin[i]<j){
                         dp[i][j] = Math.max(dp[i-1][j], dp[i][j-coin[i]]+dp[i-1][j]);
                     }
                     else dp[i][j] = dp[i-1][j];
                 }
             }

             sb.append(dp[N][M]+"\n");
         }

         System.out.print(sb);

    }

}

/*
 * 다이나믹 프로그래밍 문제
 *
 * 이 문제는 배낭 문제(knapsack)입니다. 해설은 knapsack을 알고있다는 가정하에 진행할 예정이라 knapsack을 아셔야합니다.
 * knapsack의 개념은 https://en.wikipedia.org/wiki/Knapsack_problem 혹은 구글에 상세히 나와있습니다.
 *
 * 배낭 문제를 안다는 것을 전제로 설명하자면
 *
 * 가장 기초적인 배낭 문제는 무게가 있고, 가치가 있어서 이것을 어느 무게에 갔을 때 가장 가치있는 경우를 따지는 방법이었습니다.
 * 해당 문제는 여기서 살짝 응용하여 동전이 있고 그것으로 만들 수 있는 돈이 있다고 친다면 가장 많이 만들 수 있는 경우의 수를 따져줍니다.
 * 예를들어 다음과 같은 입력값이 주어졌다고 하겠습니다.
 * 1
 * 2
 * 2 3
 * 12
 *
 * 이것을 2차원 배열로 나타내면 열(col)이 만든 돈을 나타내고 행(row)이 제가 가진 동전을 나타낸다면 초기값은 다음과 같습니다.
 * 0    1   2   3   4   5   6   7   8   9   10  11  12
 * 0    0   0   0   0   0   0   0   0   0   0   0   0
 * 2    0   0   0   0   0   0   0   0   0   0   0   0
 * 3    0   0   0   0   0   0   0   0   0   0   0   0
 *
 * 여기서 2원 동전으로 만들 수 있는 경우의 수는 다음과 같습니다.
 *
 * 0    1   2   3   4   5   6   7   8   9   10  11  12
 * 0    0   0   0   0   0   0   0   0   0   0   0   0
 * 2    0   1   0   1   0   1   0   1   0   1   0   1
 * 3    0   0   0   0   0   0   0   0   0   0   0   0
 *
 * 저희들은 간단히 생각해보면 2로 짝수의 돈을 만들 수 있는 경우는 하나 밖에 없는게 당연하지만
 * 점화식으로는 어떻게 표현해야할지 생각하게 됩니다.
 * 2로 2원을 만들 수 있는 경우는 1인데 이는 자신보다 위에 있는 0원으로 2를 만들 수 있는 경우의 수에 +1을 해준것과 같습니다.
 * 즉 다음과 같은 식이 성립됩니다.
 * j==2 then dp[1][2] = dp[0][2] + 1
 *
 * 그렇다면 4는 어떻게 1이 나온걸까를 생각하게 됩니다.
 * 4원이 되려면 2원 + 2원의 경우 밖에 없습니다. 여기서 저희는 2원을 만들 수 있는 경우의 수를 알고 있습니다.
 * 2원이 있을 때를 고려했을 때의 경우의 수가 dp[i][j-2] = 1
 * 2가 없어도 4를 만들었을 때의 경우의 수가 dp[i-1][j] = 0
 * 이므로 이 둘을 더해주면 1이 나와서 1이 되는 겁니다. dp[1][4] = dp[1][4-2] + dp[1-1][4];
 *
 * 마지막으로 만약 목표 금액이 2원보다 작거나 2원을 고려해도 경우의 수가 나오지 않을 땐
 * 2원을 고려하지 않았을 때 그 금액을 만들 수 있는 값을 그대로 가져오면 됩니다. => dp[i][j] = dp[i-1][j]
 *
 * 그렇다면 이제 이것을 3에도 똑같이 적용해주면
 *
 * 0    1   2   3   4   5   6   7   8   9   10  11  12
 * 0    0   0   0   0   0   0   0   0   0   0   0   0
 * 2    0   1   0   1   0   1   0   1   0   1   0   1
 * 3    0   1   1   1   1   2   1   2   2   2   2   3
 *
 * 이렇게 나오고 정답은 가장 맨 오른쪽 아래에 있는 3이 됩니다.
 * 이런 식으로 배낭문제를 살짝 응용하면 풀 수 있는 문제입니다.
 *
 * */