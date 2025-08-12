import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static int[] W;
    static int[][] dp;
    static List<Integer>[] edges;
    static boolean[] visited;
    static PriorityQueue<Integer> pq;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        W = new int[N+1];
        dp = new int[N+1][2];
        edges = new List[N+1];
        pq = new PriorityQueue<>();

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= N; i++) {
            W[i] = Integer.parseInt(st.nextToken());
            edges[i] = new ArrayList<>();
            dp[i][0] = dp[i][1] = -1;
        }

        for (int i = 1; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges[a].add(b);
            edges[b].add(a);
        }

        // left가 부분 집합 소속으로 고르지 않았을 때.
        visited = new boolean[N+1];
        int left = dfs(1, 0);

        // right가 부분 집합 소속으로 골랐을 때.
        visited = new boolean[N+1];
        int right = dfs(1, 1);

        visited = new boolean[N+1];
        if(left > right){
            System.out.println(left);
            path(1,0);
        }
        else {
            System.out.println(right);
            path(1,1);
        }

        while(!pq.isEmpty()){
            sb.append(pq.poll()).append(" ");
        }

        System.out.print(sb.toString());

    }

    static void path(int cur, int chosen){

        visited[cur] = true;
        if(chosen == 1){
            pq.add(cur);
            for(int next : edges[cur]){
                if(!visited[next]){
                    path(next, 0);
                }
            }
        }
        else{
            for(int next : edges[cur]){
                if(visited[next]){
                    continue;
                }
                if(dp[next][0] > dp[next][1]){
                    path(next, 0);
                }
                else{
                    path(next, 1);
                }
            }
        }

    }

    static int dfs(int cur, int chosen){

        if(dp[cur][chosen] != -1) return dp[cur][chosen];
        dp[cur][chosen] = 0;
        visited[cur] = true;

        if(chosen == 1){
            dp[cur][chosen] = W[cur];
        }

        for(int next : edges[cur]){

            if(visited[next]){
                continue;
            }

            if(chosen == 1){
                dp[cur][chosen] += dfs(next, 0);
                continue;
            }

            dp[cur][chosen] += Math.max(dfs(next, 0), dfs(next, 1));
        }

        visited[cur] = false;

        return dp[cur][chosen];
    }

}

/*

2213 트리의 독립집합

트리 + 다이나믹 프로그래밍 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. 트리를 순회하면서 어떤 노드를 선택하거나 선택 하지 않는다.
2. 선택했을 경우, 다음 노드는 선택할 수 없다.
3. 선택했을 때, 선택안했을 때의 모든 과정에서 얻은 최댓값을 dp에 넣어준다.
4. 트리를 다시 dp를 기준삼아 순회하면서 최댓값을 구성하는 노드들을 오름차순으로 가져와 출력한다.

해당 문제는 트리에서의 dp 문제에 역추적이 첨가된 문제입니다.
먼저 트리에서의 dp(3번 로직)의 핵심은 마지막 노드에 도달했을 때, 선택/비선택 최댓값은 각자 하나가 정해진다는 점입니다.
가령 1-2-3-4가 한줄로 쭉 이어진 편향 트리가 있을 때, 4에서 이미 선택/비선택 시의 최댓값을 알아냈다면
3이 4로 들어가 탐색할 필요가 없어집니다. 또 2도 3의 최댓값을 알고 있다면 3-4 구간의 탐색이 불필요합니다.
때문에 이러한 로직을 DP로 구현하면 됩니다.

저는 역추적 구현인 4에서 좀 헤매고 오류를 발생시켰는데, 핵심은 구했던 dp를 이용하여 갔던 길을 추적하는 것 입니다.
DP는 근본적으로 각 과정마다 최선의 선택이 담겨있습니다.
즉, 선택/비선택 DP에 들어간 최댓값 중 더 큰쪽이 전체 선택에서도 선택된 쪽입니다.
그렇기 때문에 트리를 탐색해가며 선택/비선택 중 더 큰쪽에 맞춰서 트리를 순회하면 부분 집합의 노드들을 알 수 있습니다.

위와 같이 3,4번 로직에 주의하여 구현하면 문제를 해결할 수 있습니다.

*/