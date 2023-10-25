import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, K;
    static int[][] dp;



    public static void main(String[] args) throws Exception {


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dp = new int[N+1][K+1];

        for(int i=1; i<=N; i++){
            int val = Integer.parseInt(br.readLine());
            for(int j=1; j<=K; j++){
                if(j-val==0){
                    dp[i][j] = dp[i-1][j]+1;
                }
                else if(j-val<0){
                    dp[i][j] = dp[i-1][j];
                }
                else dp[i][j] = dp[i][j-val] + dp[i-1][j];
            }
        }

        System.out.println(dp[N][K]);

    }
}

/*
 * 다이나믹 프로그래밍 문제
 *
 * 해당 문제는 이차원 배열로 메모이제이션을 사용하여 풀 수 있습니다.
 * 이 문제에서 동전의 구성이 같다면 경우의 수로 중복 카운팅 하지 않습니다.
 * 즉 1과 2 동전으로 4를 만든다고 할 때 (2+1+1)과 (1+2+1)과 (1+1+2)는 모두 하나로 칩니다.
 *
 * 이 문제의 핵심은 메모이제이션으로 입력으로 주어진 값을 통해 1~K값 까지 만들 수 있는 경우의 수 중 가장 큰 값을 구한다는 점입니다.
 *
 * 예시로 다음과 같은 입력이 있다고 한다면
 * 2 6
 * 2
 * 3
 *
 * 우선 비어있는 2차원 배열을 만들어보겠습니다.
 *   0 1 2 3 4 5 6
 * 0 0 0 0 0 0 0 0
 * 2 0 0 0 0 0 0 0
 * 3 0 0 0 0 0 0 0
 *
 * 첫 번째 입력인 2를 통해 1~6까지 만들 수 있는 경우를 배열로 나타내보면 다음과 같습니다.
 *   0 1 2 3 4 5 6
 * 0 0 0 0 0 0 0 0
 * 2 0 0 1 0 1 0 1
 * 3 0 0 0 0 0 0 0
 *
 * 이제 두 번째 입력인 3을 포함하여 1에서부터 6까지 만드는 경우를 배열로 만들면 다음과 같습니다.
 *   0 1 2 3 4 5 6
 * 0 0 0 0 0 0 0 0
 * 2 0 0 1 0 1 0 1
 * 3 0 0 1 1 1 1 2
 *
 * 위 결과에서 규칙성을 찾아보면 다음과 같습니다.
 * 바로 내 위의 인덱스 값은 이때까지 내가 구한 가장 큰 경우의 수를 뜻합니다.
 * 1. 현재 나와 같은 숫자일 때는 반드시 이때까지 구한 경우의 수 값에 +1 해줍니다. (2가 2를 만났을 때 바로 내 위의 인덱스 0값에 +1을하고 3이 3을 만났을 때도 바로 위 인덱스 값에 +1을 하게 된 겁니다.)
 * 2. 지금 탐색한 값에 현재 나의 값을 뺀만큼의 인덱스가 가르키는 값을 내 위의 값과 더해줍니다.
 * 예를 들어 내 현재 값이 2이고 탐색중인 값이 3이라면 3-2 = 1이고 1의 인덱스의 값은 0 입니다. 그러므로 0+바로 내 위 인덱스 값(0) = 0 이므로 2만으로 3을 만들지 못하게됩니다.
 * 3. 만약 내 값이 현재 탐색중인 값보다 크다면 내 바로 위 인덱스에서 만들어진 값을 가져옵니다.
 * 예를 들어 3은 1과 2보다 큽니다. 그러므로 바로 내 위의 인덱스 값인 0을 가져온 것이고 2보다도 크기 때문에 2의 가장 큰 경우의 수인 1을 그대로 가져온 것입니다.
 *
 * 이 규칙들을 구현해주면 문제를 풀이할 수 있습니다.
 */