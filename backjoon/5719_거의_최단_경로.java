import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, S, D;
    static class Edge{
        int to, cost;

        Edge(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
    }

    static class Node{
        int vertex, cost;

        Node(int vertex, int cost){
            this.vertex = vertex;
            this.cost = cost;
        }
    }

    static Edge[] edges;
    static List<Integer>[] vertices;
    static Set<Integer>[] history, before;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        while(true){

            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            if(N==0 && M==0){
                break;
            }
            st = new StringTokenizer(br.readLine());
            S = Integer.parseInt(st.nextToken());
            D = Integer.parseInt(st.nextToken());

            vertices = new List[N];
            edges = new Edge[M];

            for(int i=0; i<N; i++){
                vertices[i] = new ArrayList();
            }

            for(int i=0; i<M; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                vertices[a].add(i);
                edges[i] = new Edge(b, c);
            }

            dijkstra();
            eliminate();
            int answer = dijkstra();
            sb.append( (answer == Integer.MAX_VALUE ? -1 : answer) + "\n");
        }

        System.out.print(sb.toString());


    }

    static int dijkstra(){

        int[] dist = new int[N];
        boolean[] visited = new boolean[N];
        history = new Set[N];
        before = new Set[N];

        for(int i=0; i<N; i++){
            dist[i] = Integer.MAX_VALUE;
            history[i] = new HashSet<>();
            before[i] = new HashSet<>();
        }
        dist[S] = 0;


        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>( (e1, e2) -> e1.cost - e2.cost);
        priorityQueue.add(new Node(S,0));

        while(!priorityQueue.isEmpty()){

            Node node = priorityQueue.poll();

            if(visited[node.vertex]) continue;
            visited[node.vertex] = true;

            for(int next : vertices[node.vertex]){

                if(edges[next]!=null){
                    int cost = node.cost + edges[next].cost;
                    int to = edges[next].to;

                    // 최소로 도착했다면 그 vertex까지 가는 경로들 기억하기
                    if(dist[to] >= cost){

                        // 지금까지 기억한 경로들 다 잊기
                        if(dist[to] > cost){

                            dist[to] = cost;

                            history[to].clear();
                            before[to].clear();

                            Node nNode = new Node(to, cost);
                            priorityQueue.add(nNode);

                        }

                        history[to].add(next);
                        before[to].add(node.vertex);

                    }
                }
            }

        }

        return dist[D];

    }

    static void eliminate(){

        boolean[] visited = new boolean[N];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(D);

        while(!queue.isEmpty()){

            int vertex = queue.poll();
            if(visited[vertex]) continue;
            visited[vertex] = true;

            Iterator<Integer> it = before[vertex].iterator();

            Iterator<Integer> his = history[vertex].iterator();

            while(his.hasNext()){
                int e = his.next();
                edges[e] = null;
            }

            while(it.hasNext()){
                int val = it.next();
                queue.add(val);
            }
        }

    }

}

/*
 * 다익스트라 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 최단 경로에 속하는 경로(edge)를 하나도 지나지 않고서 S에서 D에 도달할 수 있는 최단 경로를 찾아야한다.
 * 2. edge는 단방향으로 주어진다. A 구간에서 B 구간으로 가는 edge는 하나 밖에 없다. (단방향 이므로 B->A로 가는 edge는 같은 edge가 아닌 다른 edge)
 * 3. 최대 weight = 1000, N <= 500, M <= 10000
 *
 * 우선 3, 2번 포인트로 충분히 int 범위 내에서 문제가 해결되는 다익스트라 문제라는 것을 파악할 수 있습니다.
 *
 * 1번 포인트가 핵심인데 이에 맞춰서 로직을 생각해보면 단순합니다.
 * 1. 다익스트라를 실행해준다.
 * 2. 다익스트라 과정에서 최소 비용으로 지나왔던 간선을 기억해둔다.
 * 3. 다익스트라가 끝나면 그 간선들을 모두 제거하는 작업을 수행한다.
 * 4. 다시 한번 다익스트라를 돌려준다. 여기서 S에서 D로 가는 최소 비용을 출력해준다.
 *
 * 여기서 핵심은 2번인데 이 과정에서 시간과 메모리를 많이 차지하지 않게 만들어야합니다.
 * 저는 2번을 구현하기 위해 간선을 배열로 만들어서 각각의 index 값이 곧 그 간선을 의미하도록 만들었습니다.
 * 이후 만약 vertex A에서 B로 도착했다면 어떤 index 값의 간선을 타고 왔는지 기억하게 만들었습니다.
 * history가 어떤 정점에 최소 비용으로 도착할 때 지나쳐온 간선의 index를 기억하고,
 * before는 이 정점에 도착하기 직전의 vertex를 기억해놓습니다.
 * 그러면 S에서 출발해서 D까지 갈 때 거쳐간 모든 간선과, vertex들을 알 수 있습니다.
 *
 * 위와 같은 로직을 코드로 구현하면 문제를 해결할 수 있습니다.
 *
 *
 * */