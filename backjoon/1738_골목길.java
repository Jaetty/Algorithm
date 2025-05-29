import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static List<int[]> edges[];
    static int[] dist;

    static int history[];
    static final int MIN = Integer.MIN_VALUE;
    static LinkedList<Integer> answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        edges = new List[N+1];
        dist = new int[N+1];
        history = new int[N+1];
        answer = new LinkedList<>();

        for (int i = 1; i <= N; i++){
            edges[i] = new ArrayList<>();
        }

        for(int i=0; i<M; i++){

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges[a].add(new int[]{b,c});
        }

        if(bellmanFord()){

            dfs(N);

            for(int val : answer){
                sb.append(val).append(" ");
            }
            System.out.print(sb.toString());
            return;
        }
        else{
            System.out.print("-1");
            return;
        }

    }

    static boolean bellmanFord(){

        Arrays.fill(dist, MIN);
        dist[1] = 0;
        history[1] = 1;
        List<Integer> check = new ArrayList<>();

        for (int i=1; i<=N; i++){
            // 변화가 발생하는지 확인하는 변수
            boolean updated = false;

            for(int j=1; j<=N; j++){
                for (int[] edge : edges[j]){

                    if( dist[j] != MIN && dist[edge[0]] < dist[j] + edge[1]){
                        dist[edge[0]] = dist[j] + edge[1];
                        history[edge[0]] = j;
                        updated = true;

                        // 사이클이 발생하는 노드들을 모으기
                        if(i==N){
                            check.add(edge[0]);
                        }
                    }

                }
            }
            // 변화가 없다면 굳이 N*N을 다 돌필요가 없음
            if(!updated){
                break;
            }
        }

        // 사이클이 발생한 노드에서부터 N까지 도달하는지 확인하여, 중요 길목에서 발생한 사이클인지 확인
        for(int val : check){

            if(canReach(val)) return false;

        }

        return true;
    }

    static boolean canReach(int start){

        Queue<Integer> queue = new ArrayDeque<>();
        queue = new ArrayDeque<>();

        boolean[] visited = new boolean[N+1];
        visited[start] = true;

        queue.add(start);

        while(!queue.isEmpty()){

            int cur = queue.poll();

            for(int [] val : edges[cur]){

                if(!visited[val[0]]){
                    visited[val[0]] = true;
                    if(visited[N]) return true;
                    queue.add(val[0]);
                }

            }

        }
        return false;
    }

    static void dfs(int start){

        answer.addFirst(start);

        if(start <= 1){
            return;
        }

        dfs(history[start]);

    }

}

/*

1738 골목길

밸만 포드 + 역추적 문제

해당 문제의 핵심 로직은 다음과 같습니다.
1. 밸만 포드로 가장 가중치 값이 크게 N에 도달하는 경로를 찾는다.
2. 마지막 구간에서부터 경로를 역추적하여 올바른 경로를 찾는다.
3. 만약, 사이클이 발생했다면 그 사이클이 경로에 관계된 사이클인지 확인하고, 맞다면 -1을 출력한다.

밸만 포드 알고리즘을 안다면 1번부터 2번까지는 금방 풀이가 가능할 것으로 예상합니다.
다만, 저의 경우 해당 문제에서 꽤나 오랜 시간을 썼는데.. 밸만 포드 문제가 너무 오랜만이라 3번을 미처 생각하지 못했습니다.

3번의 핵심은 만약에 1에서부터 N까지 가는데 꼭 거쳐가야하는 길에 사이클이 발생한다면? N에 도달하지 못합니다.
그런데, 그거랑 별개로 1에서부터 N까지 가는데 굳이 들릴 필요가 없는 변두리에 사이클이 생겼다면? 고려대상이 아닙니다.
그러므로, 사이클이 발생하게 된다면 그 노드에서부터 N까지 도달이 가능한지를 BFS/DFS로 확인해주면 중요 길목의 사이클인지 파악할 수 있습니다.

이 점만 유의해서 1~3번까지의 로직을 구현하면 문제를 풀이할 수 있습니다.


*/