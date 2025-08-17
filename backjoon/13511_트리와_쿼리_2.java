import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int N, M, LOG;
    static List<int[]>[] edges;
    static int[][] parent;
    static int[] depth;
    static long[] distance;

    static class LCA{

        int LCA;
        long dist;

        LCA(int LCA, long dist){
            this.LCA = LCA;
            this.dist = dist;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        distance = new long[N + 1];
        edges = new List[N + 1];
        depth = new int[N + 1];

        LOG = 1;
        while( (1<<LOG) <= N) LOG++;

        parent = new int[N + 1][LOG];
        int a,b,c,d;

        distance[0] = 0L;

        for (int i = 0; i <= N; i++){
            edges[i] = new ArrayList<>();
            distance[i] = -1;
        }

        for(int i=0; i<N-1; i++){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            edges[a].add(new int[]{b,c});
            edges[b].add(new int[]{a,c});
        }

        init();

        M = Integer.parseInt(br.readLine());

        for (int i = 0; i < M; i++){

            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());

            if(a==1){
                sb.append(findLCA(b,c).dist).append("\n");

            }else{
                d = Integer.parseInt(st.nextToken());
                sb.append(findK(b,c,d)).append("\n");
            }

        }

        System.out.print(sb.toString());

    }

    static LCA findLCA(int a, int b){

        long dist = 0;
        int left=a;
        int right=b;

        // 같은 레벨 맞추기
        if(depth[left] > depth[right]){

            for(int log=LOG-1; log>=0; log--){
                if(depth[left] - (1<<log) >= depth[right]){
                    left = parent[left][log];
                }
            }

            dist += distance[a] - distance[left];
            a= left;

        }
        else if(depth[left] < depth[right]){

            for(int log=LOG-1; log>=0; log--){
                if(depth[right] - (1<<log) >= depth[left]){
                    right = parent[right][log];
                }
            }

            dist += distance[b] - distance[right];
            b= right;
        }

        if(left == right){
            return new LCA(left, dist);
        }

        for(int log=LOG-1; log>=0; log--){
            if(parent[left][log] != parent[right][log]){
                left = parent[left][log];
                right = parent[right][log];
            }
        }

        int LCA = parent[left][0];

        dist += distance[a] - distance[LCA];
        dist += distance[b] - distance[LCA];

        return new LCA(LCA, dist);
    }

    static int findK(int a, int b, int K){

        int lca = findLCA(a,b).LCA;

        // a에서 LCA까지의 거리 계산
        int distA_LCA = depth[a] - depth[lca];

        // K번째 노드가 a에서 LCA로 올라가는 경로에 있는 경우
        if(K <= distA_LCA + 1){
            K--; // 1부터 시작하므로 인덱스 조정
            for(int log = LOG-1; log >= 0; log--){
                if((1 << log) <= K){
                    a = parent[a][log];
                    K -= (1 << log);
                }
            }
            return a;
        }
        // K번째 노드가 LCA를 지나 b로 가는 경로에 있는 경우
        else {
            // b에서 LCA까지의 거리
            int distB_LCA = depth[b] - depth[lca];

            // LCA를 지난 후 b까지 남은 거리 계산
            int remainK = (distA_LCA + 1) + distB_LCA - K;

            for(int log = LOG-1; log >= 0; log--){
                if((1 << log) <= remainK){
                    b = parent[b][log];
                    remainK -= (1 << log);
                }
            }
            return b;
        }
    }

    static void init(){

        dfs(1, 0, 0);

        for(int log=1; log<LOG; log++){
            for(int cur=1; cur<=N; cur++){
                parent[cur][log] = parent[parent[cur][log-1]][log-1];
            }
        }

    }

    static void dfs(int cur, int before, long weight){

        parent[cur][0] = before;
        distance[cur] = weight;
        depth[cur] = depth[before] + 1;

        for(int[] next : edges[cur]){

            if(distance[next[0]] > -1){
                continue;
            }
            dfs(next[0], cur, weight+next[1]);
        }
    }

}
/*

13511 트리와 쿼리 2

LCA 문제

해당 문제 풀이는 LCA를 위한 바이너리 리프팅을 안다는 전제로 풀이하겠습니다.
바이너리 리프팅 알고리즘 개념은 아래 제 블로그를 참고해주세요
https://jaetty.tistory.com/25

문제는 LCA를 알고 있다면 로직은 다음과 같습니다..
1번째 쿼리 :
u에서 v로 가는 비용 구하기는 LCA를 찾은 후 각각 u와 v가 LCA와 얼마나 떨어져있는지를 출력하면 됩니다.

2번째 쿼리 :
이진 트리가 있을 때, u가 왼쪽, v가 오른쪽에 있다고 가정하고 설명하겠습니다.
u에서 v로 가는 중 K번째 노드 구하기도 LCA를 우선 알아냅니다.
만약 u에서 LCA까지의 depth 차이가 K보다 더 높다면 왼쪽에 K번째 노드가 존재한다는 뜻입니다.
만약 K가 depth 차이보다 더 높다면 오른쪽에 K번째 노드가 존재한다는 뜻입니다.

따라서, 이를 토대로 왼쪽에 있다면 남은 K만큼 올려주면 정답을 찾을 수 있고
오른쪽에 있다면 오른쪽 방향에서 K번째 노드까지 올라가야하는 거리(remainK = (distA_LCA + 1) + distB_LCA - K)만큼 올려주면 정답이 나옵니다.

이와 같이 LCA를 응용하면 풀이할 수 있는 문제입니다.

*/