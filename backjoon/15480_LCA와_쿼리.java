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
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            int answer = LCA(a,b);
            int bc = LCA(b,c);
            int ac = LCA(a,c);



            if(dist[answer] < dist[bc]){
                answer = bc;
            }
            if(dist[answer] < dist[ac]){
                answer = ac;
            }

            sb.append(answer).append("\n");

        }

        System.out.print(sb.toString());

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

15480 LCA와 쿼리

최소 공통 조상 문제

@ 해당 문제는 풀이를 참고했습니다.

해당 문제의 핵심 풀이 아이디어는, "LCA(r,u), LCA(r,v), LCA(u,v) 중 가장 레벨이 높은 값이 정답" 입니다.

만약 모든 root마다 LCA를 한다고 하면 O(N^2)이 필요하고, 희소 배열을 root 경우마다 쓰면 전처리 중 O(N^2)이 필요하게 됩니다.
(이 때문에 저는 희소 배열을 한 번만 만들어서 해결하는 방법을 떠올리다가 실패했습니다.)

이 문제는 root가 계속 바뀌는 상황이지만, 사실 LCA 자체의 정의는 변하지 않습니다.
즉, 전체 트리에 대해 한 번만 희소 배열을 구성해두면, 각 쿼리마다 r, u, v 세 노드에 대해서만 LCA를 취해주면 됩니다.

예를 들어, root가 r로 바뀐 상태에서 u, v의 공통 조상을 구한다고 합시다.
만약 r이 u와 v의 경로 중간에 껴 있다면 LCA(r,u) 혹은 LCA(r,v)가 답이 되고,
그렇지 않다면 원래의 LCA(u,v)가 그대로 답이 됩니다.

따라서 세 개를 비교해 가장 깊은 것을 택하면 그것이 정답이 되는 원리를 찾아내는 것이 문제 풀이의 핵심이었습니다.
트리의 성질을 잘 이해하여야 한다는 점을 배울 수 있었습니다.

*/