import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[] dist;
    static List<int[]>[] path;
    static int[][] parent;
    static int[] energy;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        int LOG = 1;

        while((1<<LOG) <= N){
            LOG++;
        }

        dist = new int[N+1];
        energy = new int[N+1];
        path = new List[N+1];
        parent = new int[N+1][LOG];

        for (int i = 1; i <= N; i++) {
            path[i] = new ArrayList<>();
            energy[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 0; i < N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            path[a].add(new int[]{b,c});
            path[b].add(new int[]{a,c});
        }

        // N번 반복
        dfs(1,0,0);

        // parent 세팅
        for(int log = 1; log < LOG; log++) {
            for(int node = 1; node <= N; node++) {
                parent[node][log] = parent[parent[node][log-1]][log-1];
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int idx = 1; idx <= N; idx++) {

            int cur_energy = energy[idx];
            int cur_node = idx;

            for(int log = LOG-1; log >= 0; log--) {
                if(cur_node == 1){
                    break;
                }
                if(parent[cur_node][log] != 0 && dist[cur_node]-dist[parent[cur_node][log]] <= cur_energy) {
                    cur_energy -= dist[cur_node]-dist[parent[cur_node][log]];
                    cur_node = parent[cur_node][log];
                }

            }
            sb.append(cur_node).append("\n");
        }

        System.out.print(sb.toString());

    }

    static void dfs(int cur, int distance, int par){

        dist[cur] = distance;
        parent[cur][0] = par;

        for(int[] next : path[cur]){
            if(next[0] != par){
                dfs(next[0],distance+next[1], cur);
            }
        }
    }

}

/*

14942 개미

희소 배열 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. 문제의 입력은 트리 형태로 주어지고, 바이너리 리프팅을 이용하여 N Log N만큼의 탐색을 수행하면 풀이할 수 있다.
2. 바이너리 리프팅으로 개미가 부모 노드로 오르기 전에 먼저 가지고 있는 에너지가 부모 노드에 도달할 만큼 충분한지 확인한다.
3. 2번 조건을 만족하는 한에서 최대한 부모노드에 가깝게 올라간 값을 모두 기억한다.

우선, 입력으로 주어지는 조건을 분석해보면 1번이 땅에 가깝고 모든 노드들은 단 하나의 경로만을 이용하여 모두 연결되어있다고 합니다.
이는 1번을 루트로 하고, 그 아래로 뻗어나가는 트리 형태의 입력이 주어진다는 뜻입니다. 그러므로 트리 탐색으로 문제를 좁힐 수 있습니다.

결국 문제는 "i번 방의 개미가 루트 노드까지 올라갈 때, 주어진 에너지만큼만 올라갈 수 있을 때, 가장 높이 갈 수 있는 곳은?" 입니다.

그런데 문제에서 주어지는 N의 최대는 10만입니다. 만약 편향트리로 주어지면 O(N^2)만큼의 반복이 필요하므로 (물론, 실제론 그것보다 작지만)
시간초과를 벗어날 수 없습니다. 그렇다면 N번 미만의 반복으로 위로 올라갈 방법을 찾아야합니다.

이때 사용할 수 있는 것이 바이너리 리프팅입니다. 바이너리 리프팅은 log N으로 트리를 올라갈 수 있는 방법을 제공합니다.
그럼, 주어진 N개의 방에 대해서 log N만큼 올라가는 반복만 수행하면 되기 때문에 O(N log N)으로 처리할 수 있습니다.

여기서 중요한 점은 개미의 에너지입니다. 저의 경우, 1번 노드에서부터 시작해서 아래로 갈 수록 거리를 점점 누적해갔습니다.
즉, 1번 노드에서 2번 노드로 갈 때 굴이가 10의 길이면 dist[2] = 10이 되고,
2번 노드에서 3번 노드로 가는데 길이가 10이면 dist[3] = 20, 3번에서 4번이 10의 길이면 dist[4] = 30이 됩니다.

그러면 4번 굴에서 만약 22의 에너지를 가진 개미가 있을 때 다음의 공식으로 올라갈 수 있는지 확인할 수 있습니다.
1. dist[현재 위치] - dist[다음 위치] = 총 거리
2. if 총거리 <= 에너지 ? => 올라갈 수 있음.
3. 에너지 -= 총거리

위와 같이 1~3번의 연산을 반복하면 최대한 에너지를 다 쓸 때까지 올라가게 됩니다.
저는 이러한 방법을 해당 문제를 풀이할 수 있었습니다.

*/