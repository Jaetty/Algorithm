import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static List<Integer>[] edges;
    static int[][] parent;
    static int[] dist;
    static int N, M, LOG;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        edges = new List[N + 1];

        LOG = 1;
        while(1<<LOG < N) LOG++;

        parent = new int[N+1][LOG+1];
        dist = new int[N+1];
        Arrays.fill(dist, -1);

        for (int i = 0; i < N+1; i++) {
            edges[i] = new ArrayList<>();
        }

        for(int i=0; i<N-1; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges[a].add(b);
            edges[b].add(a);
        }

        setParent(0, 0, 1);

        for(int log=1; log<=LOG; log++){
            for(int node=1; node<=N; node++){
                parent[node][log] = parent[parent[node][log-1]][log-1];
            }
        }

        M = Integer.parseInt(br.readLine());
        int cur = 1;
        long answer = 0;

        for(int i=0; i<M; i++){

            int target = Integer.parseInt(br.readLine());

            answer += LCA_Distance(cur, target);
            cur = target;

        }

        System.out.println(answer);

    }

    static int LCA(int a, int b){

        // 언제나! b를 더 깊은 노드로 만들어주자. 코드가 간결해짐.
        if(dist[a] > dist[b]){
            int temp = a;
            a = b;
            b = temp;
        }

        // b를 a 레벨에 맞추기.
        for(int log = LOG; log>=0; log--){
            if(dist[b] - (1<<log) >= dist[a] ){
                b = parent[b][log];
            }
        }

        if(a==b){
            return a;
        }

        for(int log = LOG; log>=0; log--){
            if(parent[a][log] != parent[b][log]){
                a = parent[a][log];
                b = parent[b][log];
            }
        }


        return parent[a][0];

    }

    static int LCA_Distance(int a, int b){

        int LCA = LCA(a, b);
        return (dist[a] - dist[LCA]) + (dist[b] - dist[LCA]);
    }

    static void setParent(int depth, int before, int cur){

        parent[cur][0] = before;
        dist[cur] = depth;

        for(int next : edges[cur]){

            if(dist[next] > -1){
                continue;
            }
            setParent(depth+1, cur, next);
        }
    }

}
/*

8012 한동이는 영업사원!

최소 공통 조상 문제

해당 문제의 풀이 로직은 다음과 같습니다.

1. 트리를 입력 받은 후 전처리로 dfs 탐색, 희소 배열과 깊이 배열을 세팅합니다.
2. 출발지와 도착지의 LCA를 구한 후 LCA와 출발지, 도착지의 거리를 합산하고, 출발지를 도착지로 바꿔줍니다.
3. 2번 과정을 M번 반복합니다.

해당 문제는 전형적인 LCA 문제입니다.

문제의 핵심은 결국, 출발지에서 도착지까지 얼마나 걸리는지 금방 알아내는 것 입니다.
dfs 탐색으로 거리를 알아내려면 N번 걸리니까 연산 시간이 O(N^2)이므로 시간 초과를 피할 수 없습니다.
이때 LCA를 이용해서 출발지와 도착지의 최소 공통 조상까지 걸리는 길이를 더해주면 a에서 b로 가는 거리를 알 수 있게 되는 것 입니다.

*/