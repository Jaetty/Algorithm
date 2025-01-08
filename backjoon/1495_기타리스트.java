import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        boolean[][] dp = new boolean[N+1][M+1];
        dp[0][S] = true;

        st = new StringTokenizer(br.readLine());

        for(int i=1; i<=N; i++){

            int val = Integer.parseInt(st.nextToken());

            for(int j=0; j<=M; j++){
                if(dp[i-1][j]){

                    if(j+val <= M){
                        dp[i][j+val] = true;
                    }

                    if(j-val >= 0){
                        dp[i][j-val] = true;
                    }

                }
            }
        }

        for (int i= M; i>=0; i--){
            if(dp[N][i]){
                System.out.print(i);
                return;
            }
        }

        System.out.print(-1);

    }
}

/*
 * 1495 기타리스트
 *
 * 다이나믹 프로그래밍 문제
 *
 * 문제의 조건은 N <= 50이고 각각의 단계에서 더하거나 빼거나의 연산을 수행하므로, 발생하는 경우의 수는
 * 2 + 4 + 8 + 16.... 최대 = 2^50 즉 O(2^N) 입니다.
 * 비록 문제의 경우 1000 이상과 0 미만의 숫자의 경우 고려하지 않는다고 조건이 붙어 있어서 실제로는 2^N 보다야 경우가 적겠지만
 * 여전히 브루트포스로 풀이 시 시간초과를 벗어날 수 없는건 확실합니다.
 *
 * 여기서 DP를 이용하면 문제 없이 풀이가 가능합니다.
 * 다음과 같이 예시가 있다고 해보겠습니다.
 *
 * N = 4, S = 3, M = 5
 * V[] = 1 2 1 2
 *
 * 이 문제의 핵심은
 * 1. 더하거나 빼서 최소 0에서 최대 5까지의 숫자만 만들 수 있다.
 * 2. 어떤 조합으로 만들어졌건 상관 할 필요 없이, 만들 수 있는 가장 큰 값만 도출하면 된다.
 *
 * 즉, 0~M까지 만들어졌는지 아닌지만 기억하는 배열을 DP 배열로 사용하면 된다는 뜻입니다.
 * 그렇다면 초기 DP 배열은 다음과 같습니다.
 *
 * [F, F, F, T, F, F] => S=3이므로 3에 해당하는 부분은 T 처리.
 *
 * 여기서 True가 되어있던, 3에 V[1] = 1 값을 가져와서 더하거나 빼 봅시다.
 *
 * [F, F, F, T, F, F]
 *         /   \
 * [F, F, T, F, T, F]
 *
 * 다음으로 똑같이 T에 있는 부분에 V[2] = 2 값을 가져와서 각각 T가 되어있는 숫자에 더하거나 빼보면
 *
 * [F, F, T, F, T, F]
 *       / \  /  \
 *     /    X      \
 *   /    /   \      \
 * [T, F, T, F, T, F]  나감.
 *
 * 이걸 N번까지 반복하면 최대 O(N * M) 반복을 하게 되므로 2^N보다 훨씬 시간을 절약하며 문제를 풀이할 수 있습니다.
 *
 * */