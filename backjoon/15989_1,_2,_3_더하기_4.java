import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        long[][] dp = new long[10001][4];

        dp[1][1] = 1;

        dp[2][1] = 1;
        dp[2][2] = 1;

        dp[3][1] = 1;
        dp[3][2] = 1;
        dp[3][3] = 1;

        for(int i=4; i<=10000; i++){
            dp[i][1] = dp[i-1][1];
            dp[i][2] = dp[i-2][1] + dp[i-2][2];
            dp[i][3] = dp[i-3][1] + dp[i-3][2] + dp[i-3][3];
        }

        for(int i=0; i<N; i++){
            int val = Integer.parseInt(br.readLine());
            long sum = 0;
            for(int j=1; j<=3; j++){
                sum += dp[val][j];
            }
            sb.append(sum+"\n");
        }

        System.out.print(sb.toString());

    }

}
/*
 * 다이나믹 프로그래밍 문제
 *
 * 이 문제의 경우 간략히 설명하면 어떤 숫자를 구성하는 마지막 숫자가 중요합니다.
 * 예를 들어, 1~5까지의 구성을 알아보면 다음과 같습니다.
 *
 * 1의 경우
 * '1'
 *
 * 2의 경우
 * 1 + '1'
 * '2'
 *
 * 3의 경우
 * 1 + 1 + '1'
 * 2 + '1'
 * 1 + '2'
 * '3'
 *
 * 4의 경우
 * 2 + 1 + '1'
 * 1 + 2 + '1'
 * 1 + 1 + '2'
 * 1 + 1 + 1 + '1'
 * 2 + '2'
 * 3 + '1'
 * 1 + '3'
 *
 * 5의 경우
 * 1 + 1 + 1 + 1 + '1'
 * 2 + 1 + 1 + '1'
 * 1 + 2 + 1 + '1'
 * 1 + 1 + 2 + '1'
 * 1 + 1 + 1 + '2'
 * 2 + 2 + '1'
 * 2 + 1 + '2'
 * 1 + 2 + '2'
 * 2 + '3'
 * 3 + '2'
 *
 * 이렇게 나타낼 수 있습니다. 여기서 중복을 제거하는 방법은 '더하기를 구성하는 숫자를 오름차순으로 했을 때도 성립하는가?'로 판단합니다.
 * 예를 들어 4의 경우 1로 끝나는 경우는 다음과 같습니다.
 * 2 + 1 + '1'
 * 1 + 2 + '1'
 * 1 + 1 + 1 + '1'
 * 3 + '1'
 *
 * 그러나 여기서 오름차순을 만족하는 경우는?
 * 1 + 1 + 1 + '1' 밖에 없습니다. 나머지는 마지막 수 1 앞에 2, 3이 존재하여 오름차순이 성립되지 못합니다.
 *
 * 마찬가지로 4를 구성하는 2와 3을 보겠습니다.
 * 1 + 1 + '2' -> 오름차순 성립
 * 2 + '2' -> 오름차순 성립
 * 1 + '3' ->  오름차순 성립
 *
 * 이런 논리를 기조로 dp용 배열을 구성해보면 어떤 숫자 N이 있다면 다음과 같아집니다.
 * dp[N][1] = N을 만드는 숫자 구성 중 마지막이 1로 끝나며 오름차순이 성립하는 경우의 수.
 * dp[N][2] = N을 만드는 숫자 구성 중 마지막이 2로 끝나며 오름차순이 성립하는 경우의 수.
 * dp[N][3] = N을 만드는 숫자 구성 중 마지막이 3로 끝나며 오름차순이 성립하는 경우의 수.
 *
 * 그리고 이를 구하는 방법은 다음과 같아집니다.
 * dp[N][1] = dp[N-1][1]
 * dp[N][2] = dp[N-2][1] + dp[N-2][2]
 * dp[N][3] = dp[N-3][1] + dp[N-3][2] + dp[N-3][3]
 *
 * */