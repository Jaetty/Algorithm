import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static Integer[][] dp;
    static int[][] input;

    public static void main(String[] args) throws Exception {

        System.out.println(1<<20);

        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        input = new int[N+1][N];
        dp = new Integer[N+1][1<<N];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                input[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dp[0][0] = 0;

        for(int p = 1; p <= N; p++){

            for(int cur = 0; cur < 1<<N; cur++){

                if(dp[p-1][cur] == null) continue;

                for(int pick = 0; pick < N; pick++){

                    if( (cur & (1<<pick)) != 0) continue;

                    if(dp[p][cur|(1<<pick)] == null){
                        dp[p][cur|(1<<pick)] = dp[p-1][cur] + input[p][pick];
                    }
                    else{
                        dp[p][cur|(1<<pick)] = Math.min(dp[p][cur|(1<<pick)], dp[p-1][cur] + input[p][pick]);
                    }

                }
            }
        }

        System.out.println(dp[N][(1<<N)-1]);

    }

}

/*

1311 할 일 정하기 1

비트마스킹 + 다이나믹 프로그래밍 문제

@@@ !! 해당 문제는 다른 풀이를 참고했습니다.

풀이를 보기 전 제가 세웠던 해당 문제의 풀이 로직은 다음과 같습니다.
1. 순서를 고려해야하기 때문에, 비트마스킹 다이나믹 프로그래밍으로 각 순서마다 최솟값을 기억하게 둔다.
2. 전의 최솟값과 선택한 순서를 바탕으로 이번에 고른 값의 합 중 최솟 값을 선택한다.

비트마스킹 다이나믹 프로그래밍 문제의 핵심은 기억하는 것이 단순히 값만이 아닌 순서를 기억할 때 자주 사용됩니다.
그래서 문제를 읽고 비트마스킹 DP 풀이를 시도하려고 했으나, 구체적으로 로직을 세워놓지 않다보니 구현에서 막혀서 해메게 되었습니다.

결국, 다른 분의 풀이를 봤는데 제게 부족했던 로직이 다음과 같았습니다.
3. dp[각 번호의 사람][어떤 것을 선택했을 때의 최솟값] 형태를 유지한다.

즉, 문제를 해결하기 위해선 순차적으로 x번째 사람이 선택하는 순서일 때, 전의 사람들이 선택한 경우의 수 중 가장 작은 수와
내가 현재 어떤 위치의 값을 더했을 때의 최솟값을 선택하는 것이 핵심이었습니다.

*/