import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int tc=0; tc<T; tc++){

            int N = Integer.parseInt(br.readLine());
            int[] input = new int[N+1];

            st = new StringTokenizer(br.readLine());

            for(int i=1; i<=N; i++){
                input[i] = Integer.parseInt(st.nextToken());
            }

            int MAX = Integer.parseInt(br.readLine());

            int[][] dp = new int[N+1][MAX+1];

            for(int i=1; i<=N; i++){

                for(int j=1; j<=MAX; j++){

                    if(j-input[i]<0){
                        dp[i][j] = dp[i-1][j];
                    }
                    else if(j==input[i]){
                        dp[i][j] = dp[i-1][j]+1;
                    }
                    else{
                        dp[i][j] = dp[i-1][j] + dp[i][j-input[i]];
                    }
                }
            }

            sb.append(dp[N][MAX]).append("\n");
        }
        System.out.print(sb.toString());
    }

}

/*
 * 다이나믹 프로그래밍 문제
 *
 * 해당 문제의 포인트는 경우의 수를 다이나믹 프로그래밍으로 구하는 것 입니다.
 *
 * 제가 풀이할 때 쓴 아래의 예시를 통해 설명하겠습니다.
 *
 * 1
 * 2
 * 5 2
 * 20
 *
 * 각각 5와 2로 20까지의 숫자를 어떻게 만들 수 있을지 경우의 수를 따져보면 아래와 같습니다.
 * 맨 앞에 0으로 만들 수 있는 경우의 수가 있다고 가정하고, 5로 만들 수 있는 경우는 아래와 같아집니다.
 *
 *      1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20
 * 0    0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0
 * 5    0   0   0   0   1   0   0   0   0   1   0   0   0   0   1   0   0   0   0   1
 *
 * 5코인으로 20이하의 숫자를 만들 수 있는  경우의 수를 나타낸 표로 5코인 만 써서 20을 만들 수 있는 경우는 5+5+5+5로 하나밖에 없습니다.
 *
 * 다음으로 2와 5를 통해 20까지 만들 수 있는 경우의 수를 세어보면 다음과 같아집니다.
 *      1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20
 * 0    0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0   0
 * 5    0   0   0   0   1   0   0   0   0   1   0   0   0   0   1   0   0   0   0   1
 * 2    0   1   0   1   1   1   1   1   1   2   1   2   1   2   2   2   2   2   2   3
 *
 * 여기 표를 통해 정규식을 세울 수 있는 규칙성을 알 수 있는데 각각 다음과 같습니다.
 * 1. 각 코인보다 낮은 숫자의 경우 바로 위의 경우의 수를 그대로 받습니다. (5와 2는 각각 5와 2를 만나기 전엔 0입니다. 즉 dp[i][j]=dp[i-1][j])
 * 2. 각 코인이 최초로 자신과 같은 값을 가질 때 바로 위에서 구한 경우의 수 +1의 값을 가지게 됩니다. (표에서 5와 2는 각각 dp[i-1][j]+1 입니다.)
 * 3. 나보다 높은 숫자의 경우 바로 위의 값과 왼쪽으로 나의 크기 만큼을 뺀 값의 합 입니다. (표에서 [2][10]은 dp[i-1][j] + dp[i][j-2] 입니다.)
 *
 * 이를 통해 점화식을 알아냈고 이를 구현해주면 풀이할 수 있습니다.
 *
 * */