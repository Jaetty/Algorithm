import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, dp[], answer[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        dp = new int[N+1];
        dp[N] = 0;

        // dp 해결 부분.
        for(int i=N-1; i>=1; i--){

            dp[i] = dp[i+1]+1;

            if(i*2 <= N){
                dp[i] = Math.min(dp[i], dp[i*2]+1);
            }
            if(i*3 <= N){
                dp[i] = Math.min(dp[i], dp[i*3]+1);
            }

            System.out.println(i+" " + dp[i]);

        }

        StringBuilder sb = new StringBuilder();

        answer = new int[dp[1]+1];
        answer[0] = 1;

        dfs(1, 1);

        sb.append(dp[1]).append("\n");
        for(int j=dp[1]; j>=0; j--){
            sb.append(answer[j]+" ");
        }
        System.out.print(sb.toString());
    }

    static boolean dfs(int depth, int idx){

        if(idx > N || depth > dp[1]){
            return depth == dp[1]+1;
        }

        if(idx*3 <= N && dp[idx*3] == dp[answer[depth-1]]-1){
            answer[depth] = idx*3;
            if(dfs(depth+1, idx*3)){
                return true;
            }
        }

        if(idx*2 <= N && dp[idx*2] == dp[answer[depth-1]]-1){
            answer[depth] = idx*2;
            if(dfs(depth+1, idx*2)){
                return true;
            }
        }

        if(idx+1 <=N && dp[idx+1] == dp[answer[depth-1]]-1){
            answer[depth] = idx+1;
            if(dfs(depth+1, idx+1)){
                return true;
            }
        }

        return false;
    }

}

/*
 * 12852 1로 만들기 2
 *
 * 다이나믹 프로그래밍 + 그래프 이론 문제
 *
 * @@ 이 문제를 풀기 전에 먼저 '1463 1로 만들기' 문제를 풀면 도움이 됩니다.
 *
 * 문제의 포인트는 'DP로 먼저 최단 경로를 구한 후, 경로에 맞춰서 DFS 탐색을 수행하라.' 입니다.
 *
 * 우선 문제를 보게 되면, 현재의 숫자 x에 -1을 하고 움직여주거나, x%3==0이면 3으로 나누고, x%2==0이면 2로 나눌 수 있습니다.
 * 그렇다면, dp[N]은 첫 시작 위치이니 dp[N] = 0인 상태로 만들어주고,
 * 어떤 값 x (x < N)에 대해서 x*2 <= N을 성립한다면 dp[x] = min(dp[x*2]+1, dp[x+1]+1)이 성립됩니다.
 * 또, 3도 마찬가지로 x*3 <= N 이라면 dp[x] = min(dp[x*3]+1, dp[x+1]+1)이 성립합니다.
 * 이를 바탕으로 dp 문제 부분을 해결할 수 있습니다.
 *
 * dp 문제 부분이 전부 풀이가 된다면, dp 배열은 누락된 값 없이 모든 값들이 들어있을 것 입니다.
 * 예를 들어 10의 입력이 주어지면 dp 배열은 다음과 같습니다.
 *
 * dp[11] = { 0, 3, 3, 2, 2, 1, 4, 3, 2, 1, 0} *(맨 처음 0번 인덱스는 쓰이지 않습니다.)
 *
 * 여기서 dp[1]부터 dp[N]까지 가되, 조건으로 dp[next] == dp[before]-1과 같은지를 확인하면 됩니다.
 * 예를 들어 지금 dp[1] = 3이므로 다음으로 갈 수 있는 값들은 1*2, 1*3, 1+1 이면서, dp[x] = 2인 값이어야 합니다.
 *
 * 이러한 조건을 맞춰서 탐색을 해주면 정답을 도출할 수 있습니다.
 *
 * */