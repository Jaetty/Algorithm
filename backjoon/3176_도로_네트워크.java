import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    // [현재 노드][k칸 위][{최솟값, 최댓값}]
    static long[][][] distance;
    // Binary Lifting 부모
    static int[][] parent;
    // Level 값 기억
    static int[] level;

    // int[] {연결된 노드, 거리값}
    static List<int[]> path[];

    static int N, K, LOG_N;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        // LOG_N 값을 구한다.
        LOG_N = 1;
        while(1<<LOG_N <= N){
            LOG_N++;
        }

        // 아래는 초기화
        path = new List[N+1];
        for(int i = 0; i <= N; i++){
            path[i] = new ArrayList<>();
        }

        level = new int[N+1];
        distance = new long[N+1][LOG_N][2];
        parent = new int[N+1][LOG_N];

        // 경로 입력 받기
        for(int i=0; i<N-1; i++){

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            path[a].add(new int[]{b,d});
            path[b].add(new int[]{a,d});

        }

        // 1번이 루트 노드이기 때문에 여기에 초기 세팅
        distance[1][0][0] = Long.MAX_VALUE;
        distance[1][0][1] = Long.MIN_VALUE;

        dfs_setting(1, 0, 0);
        log_setting();

        K = Integer.parseInt(br.readLine());
        for(int i=0; i<K; i++){
            st = new StringTokenizer(br.readLine());
            long[] ans = LCA(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            sb.append(ans[0]).append(" ").append(ans[1]).append("\n");
        }
        System.out.print(sb.toString());
    }

    static long[] LCA(int node_a, int node_b){

        long[] res = new long[]{Long.MAX_VALUE, Long.MIN_VALUE};

        int big_node = level[node_a] >= level[node_b] ? node_a : node_b;
        int small_node = big_node == node_a ? node_b : node_a;

        // 최초에 레벨이 서로 같지 않다면 Binary Lifting으로 맞춰준다.
        if(level[big_node] != level[small_node]){

            for(int k=LOG_N-1; k>=0; k--){
                if(level[big_node] - (1<<k) >= level[small_node]){
                    res[0] = Math.min(res[0], distance[big_node][k][0]);
                    res[1] = Math.max(res[1], distance[big_node][k][1]);
                    big_node = parent[big_node][k];
                }
            }
        }

        // 만약 레벨이 같으나 서로 같은 노드가 아니라면 Binary Lifting으로 맞춰준다.
        if(big_node != small_node){

            // 서로 안맞을 때 까지 반복한다.
            for (int k = LOG_N - 1; k >= 0; k--) {
                if (parent[big_node][k] != 0 && parent[big_node][k] != parent[small_node][k]) {

                    res[0] = Math.min(res[0], Math.min(distance[big_node][k][0], distance[small_node][k][0]));
                    res[1] = Math.max(res[1], Math.max(distance[big_node][k][1], distance[small_node][k][1]));

                    big_node = parent[big_node][k];
                    small_node = parent[small_node][k];
                }
            }

            // 마지막으로 한칸 바로 위를 기준으로 해준다.
            res[0] = Math.min(res[0], Math.min(distance[big_node][0][0], distance[small_node][0][0]));
            res[1] = Math.max(res[1], Math.max(distance[big_node][0][1], distance[small_node][0][1]));

        }
        return res;
    }

    // Binary Lifting 전처리 과정
    static void log_setting(){

        for(int k=1; k<LOG_N; k++){
            for(int node=1; node<=N; node++){

                // distance 초기화
                distance[node][k][0] = Long.MAX_VALUE;
                distance[node][k][1] = Long.MIN_VALUE;
                // 2^k 칸 위의 부모를 적어주기
                parent[node][k] = parent[parent[node][k-1]][k-1];

                // 2^(k-1) 번째 칸의 부모가 있어야한다.
                int par = parent[node][k-1];

                // 루트 노드는 1번이기 때문에, 0번 노드는 존재하지 않는다. 그러므로 par 값이 루트 노드를 넘지 않았다면 수행.
                if(par >= 1){
                    // par까지 도달하는데 걸린 가장 최솟값과 최댓값을 가져와서 지금까지의 최댓값과 최솟값과 비교해서 갱신해준다.
                    distance[node][k][0] = Math.min(distance[node][k-1][0], distance[par][k-1][0]);
                    distance[node][k][1] = Math.max(distance[node][k-1][1], distance[par][k-1][1]);
                }
            }
        }
    }

    // 트리의 레벨(깊이) 세팅
    static void dfs_setting(int curr, int par, int dist){

        // 바로 위의 부모 기억하기.
        parent[curr][0] = par;
        // 현재 노드의 레벨(깊이) 설정하기.
        level[curr] = level[par]+1;

        if(level[curr] > 1){
            // 각각 바로 직전의 부모에서 노드로 가는 비용 넣어주기
            // [0]은 최솟값, [1]은 최댓값 둘 다 비용이 같다.
            distance[curr][0][0] = dist;
            distance[curr][0][1] = dist;
        }

        for(int[] next : path[curr]){
            if(next[0] == par){
                continue;
            }
            dfs_setting(next[0], curr, next[1]);
        }

    }

}

/*

3176 도로 네트워크

최소 공통 조상 문제

해당 문제를 풀이하기 위해서는 먼저, Binary Lifting 알고리즘을 알아야합니다.

기초적인 LCA 문제의 경우, 각 비교군 노드 a와 b의 조상을 하나씩 올려가면서 가장 먼저 만나는 공통 조상을 리턴하면 됩니다.
그러나, 이는 O(N)이 걸리는 작업이고 노드나 시도 횟수가 많아지면 많아질 수록 소요 시간이 크게 증가합니다.

이때 바이너리 리프팅 알고리즘을 이용하면 조상을 빠르게 찾아낼 수 있습니다.
이 바이너리 리프팅의 핵심 아이디어는 2의 거듭제곱 단위로 조상을 미리 기억하는 것 입니다.
그러니까 A라는 노드의 1(2^0)칸 위의 조상, 2(2^1)칸 위의 조상, 4(2^2)칸 위의 조상, 8(2^3)칸 위의 조상, 16(2^4)칸 위의 조상...

이런 식으로 저장하게 되면 만약 A라는 노드의 K번째 조상을 찾고 있다면 LogN의 속도로 금방 찾을 수 있게 됩니다.

예를 들어 A라는 노드의 13칸 위의 조상을 찾는다고 해보겠습니다. 평범히 찾으려면 13번 일일히 건너니까 O(13)의 시간이 필요합니다.
그러나 바이너리 리프팅을 하게 되면 다음과 같이 진행됩니다.
1. 2^4칸 위가 13칸 이하인지 확인한다. => 16이 되므로 아니다. 그러므로 2^4칸은 무시한다.
2. 2^3칸 위가 13칸 이하인지 확인한다. => 8이 되므로 맞다. 그럼 현재 위치를 8칸 위 조상까지 점프한다.
3. 2^2칸 위가 13칸 이하인지 확인한다. => 12가 되므로 맞다. 그럼 4칸 위까지 점프한다.
4. 2^1칸 위가 13칸 이하인지 확인한다. => 14가 되므로 아니다. 넘어간다.
5. 2^0칸 위가 13칸 이하인지 확인한다. => 13이 되므로 맞다. 그럼 1칸 위로 점프한다.

위와 같은 방식으로 Log N = 4라면, 5번의 탐색만으로 조상을 찾아낼 수 있게 됩니다.
이렇듯 O(N)을 Log(N)의 속도로 조상을 찾는 바이너리 리프팅 알고리즘을 우선 알아야 풀이할 수 있습니다.

=========================================================================

결국, 해당 문제는 바이너리 리프팅을 알고 있다면 다음과 같이 풀이할 수 있게 됩니다.
1. 각각의 자식 노드는 어떤 K번째 부모에서부터 지금 나의 위치로 갈 때 최대, 최소 비용을 기억한다. (distance 배열)
2. 바이너리 리프팅을 통해서 입력받은 a와 b의 공통 부모를 찾는 과정에서 기억했던 비용 값들을 꺼내오면서 최솟값과 최댓값을 갱신해준다.
3. LCA 까지 도달하면 마지막으로 갱신된 최소 비용과 최대 비용을 출력한다.

바이너리 리프팅을 제외하고 문제의 핵심은 어떻게 비용을 기억할까? 입니다.
만약 부모 node에 비용을 기억하게 만들면 여러 갈래에서 나온 자식들이 최솟값과 최댓값을 수정해버리는 문제가 발생합니다.
그러므로 k칸 위의 부모에서 현재의 노드에 도달할 때까지 나오는 비용 중 최솟값과 최댓값을 기억하면 됩니다.
이를 저는 distance[curr node][k][0,1] (0은 최소, 1은 최대)로 기억하게 만들었습니다.

이제 이것을 활용하여 바이너리 리프팅 과정 중 최솟값과 최댓값을 갱신해나가면 문제를 해결할 수 있습니다.

*/