import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static int[] arr;
    static int[][] dp;
    static List<Integer>[] edges;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine().trim());

        dp = new int[N+1][2];
        arr = new int[N+1];
        edges = new List[N+1];
        visited = new boolean[N+1];

        st = new StringTokenizer(br.readLine().trim());

        for (int i = 1; i < N+1; i++) {
            edges[i] = new ArrayList<>();
            Arrays.fill(dp[i], -1);
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges[a].add(b);
            edges[b].add(a);
        }

        int answer = 0;

        answer = Math.max(answer, Math.max(dfs(1, 1) + arr[1], dfs(1,0)));

        System.out.println(answer);

    }

    static int dfs(int node, int choose){

        if(dp[node][choose]!=-1) return dp[node][choose];
        dp[node][choose] = 0;
        visited[node] = true;

        for(int next : edges[node]) {

            if(visited[next]) continue;

            if(choose == 1){
                dp[node][choose] += dfs(next,0);
            }
            else{
                dp[node][choose] += Math.max(dfs(next, 1) + arr[next], dfs(next,0));
            }

        }

        visited[node] = false;
        return dp[node][choose];

    }
}
/*

1949 우수 마을

트리 + 다이나믹 프로그래밍 문제

해당 문제의 조건을 다시 써보자면 다음과 같습니다.
1. 기본적으로 한 노드에서부터 트리 탐색을 시작한다. (어느 노드에서 시작하건 상관없다)
2. 마을은 우수 마을로 선정하거나 선정하지 않거나 두 가지 경우를 선택할 수 있다.
3. 다만, 지금 마을 바로 직전에 탐색한 마을이 우수 마을로 선정되었다면 이 마을은 우수 마을이 되지 못하고 다음 노드로 이동만 해야한다.

위의 내용을 구현하라고 하면 간단히 구현할 것입니다.
다만, 문제의 핵심은 이렇게 탐색을 하는 와중 가장 우수 마을로 선정된 마을의 인구수 총합이 가장 큰 경우를 구해야하는 것 입니다.

모든 경우를 다 돌면 N^2을 넘는 시간이 필요할 것입니다. N이 최대 10000이기 때문에 N^2 이상의 연산은 시간 초과를 예상할 수 있습니다.

여기서 문제를 한번 다음과 같이 접근해보겠습니다.
1번 2번 노드만 있는 높이 1의 조그만 트리가 있고, 1번 노드엔 10명이 살고, 2번 노드엔 20명이 산다고 해보겠습니다.

만약 1번 노드를 우수 마을로 선정한다면 2번은 우수 마을로 선택 못하니 이 트리의 최대 우수 마을 값은 10입니다.
반대로 1번을 그냥 마을로 두고, 2번 노드를 우수 마을로 선정하면 이 트리의 최대 우수 마을 값은 20입니다.
즉, 1번 노드를 우수 마을로 선정했을 때 최댓값은 10이고, 선정안했을 때 최댓값은 20입니다.

이제 인구수 30의 3번 마을이 추가됐다고 해보겠습니다. 3번 마을은 1번 마을과 이어졌습니다.
그럼 3번 마을에서 트리 탐색을 시작한다고 하면, 3번 마을을 우수 마을로 선정하거나 선정안했을 때 최대값은 어떻게 될까요?

먼저 3번 마을을 우수 마을로 선정했다면?
1번 마을은 우수 마을로 선정되지 못하고, 2번 마을은 선정하거나 안하거나 선택할 수 있습니다.
그런데, 앞선 탐색에서 1번 마을을 우수 마을로 선정하지 않았을 때 최댓값이 20인걸 알고 있습니다.
그렇다면 굳이 2번 마을까지가서 선택해보거나 안해보거나 경우의 수에 맞춰서 탐색할 필요가 없습니다.

이는 3번 마을이 우수 마을로 선정되지 않을 때도 똑같습니다.
1번 마을을 우수 마을로 선정하거나 안하거나 선택할 수 있는데 이미 선정했을 때 안했을 때 최댓값을 둘다 알고 있습니다.
그렇다면 2번 마을까지 탐색할 필요가 전혀 없다는 것입니다.

즉, DP를 이용해서 딱 한번 탐색이 되었다면 그때 기억해뒀던 선택했을 때 선택 안했을 때의 최댓값을 불러오기만 하는 구조를 구현하면 됩니다.
이렇게 하면 대략 O(2*N) = O(N)의 시간에 문제를 풀이할 수 있게 됩니다.

*/