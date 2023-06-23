import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N, M, X;
    static List<List<Edge>> vertex = new ArrayList<>();
    static boolean[] visit;
    static int[] comeback_cost;
    static int[][] going_cost;
    static PriorityQueue<Edge> pqueue = new PriorityQueue<>( (e1, e2) -> e1.c - e2.c );
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        visit = new boolean[N +1];
        comeback_cost = new int[N +1];
        going_cost = new int[N +1][N+1];

        for (int i = 0; i <= N; i++) {
            vertex.add(new ArrayList<Edge>());
            comeback_cost[i] = INF; // 충분히 큰 값으로
            for(int j=0; j<=N; j++) going_cost[i][j] = INF;
        }

        // edge
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            // 방향
            vertex.get(v1).add(new Edge(v2, w));
        }
        // 돌아가는 길 다익스트라자
        dijkstra();

        // 파티장으로 가는 다익스트라자
        for (int i = 1; i <= N; i++) {
            if(i==X) continue;
            dijkstra(i);
        }

        int min = 0;

        for (int i = 1; i <= N; i++) {
            if(i==X) continue;
            min = Math.max(min, comeback_cost[i]+going_cost[i][X]);
        }

        System.out.println(min);
    }

    static void dijkstra() {
        // 시작 정점 K
        comeback_cost[X] = 0;
        pqueue.offer(new Edge(X, 0));

        while( ! pqueue.isEmpty() ) {
            // 꺼내면 비용이 가장 작은 것
            Edge e = pqueue.poll();

            if( e.c > comeback_cost[e.v] ) continue; // 가지치기

            // visit check
            if( visit[e.v] ) continue;

            // e.v 정점으로부터 갈 수 있는 다른 정점을 고려
            // 고려하는 목적은 cost[] 갱신하기 위해
            visit[e.v] = true;

            for (Edge ne : vertex.get(e.v)) {
                if( ! visit[ne.v] && comeback_cost[e.v] + ne.c  <  comeback_cost[ne.v]) {
                    comeback_cost[ne.v] = comeback_cost[e.v] + ne.c;
                    pqueue.offer(new Edge(ne.v, comeback_cost[ne.v]));
                }
            }

        }
    }

    static void dijkstra(int start) {
        // 시작 정점 K
        going_cost[start][start] = 0;
        visit = new boolean[N +1];
        pqueue.offer(new Edge(start, 0));

        while( ! pqueue.isEmpty() ) {
            // 꺼내면 비용이 가장 작은 것
            Edge e = pqueue.poll();

            if( e.c > going_cost[start][e.v] ) continue; // 가지치기

            // visit check
            if( visit[e.v] ) continue;

            // e.v 정점으로부터 갈 수 있는 다른 정점을 고려
            // 고려하는 목적은 cost[] 갱신하기 위해
            visit[e.v] = true;

            for (Edge ne : vertex.get(e.v)) {
                if( ! visit[ne.v] && going_cost[start][e.v] + ne.c  <  going_cost[start][ne.v]) {
                    going_cost[start][ne.v] = going_cost[start][e.v] + ne.c;
                    pqueue.offer(new Edge(ne.v, going_cost[start][ne.v]));
                }
            }

        }
    }

    static class Edge{
        int v; // 정점
        int c; // 비용
        public Edge(int v, int c) {
            this.v = v;
            this.c = c;
        }
    }

}

/*
 *
 * 다익스트라 문제로 파티장으로 갈 때의 비용을 다익스트라로 구하고 집으로 돌아올때의 비용도 다익스트라로 구해서
 * 그 중 가장 값이 높은 것을 출력하면 됩니다.
 *
 * */