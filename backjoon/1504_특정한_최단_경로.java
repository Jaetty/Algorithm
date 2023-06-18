import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N,E, v1, v2;

    static long case1;
    static long answer;

    static int[] dist;
    static boolean[] visit;

    static List<List<Edge>> vertex;

    static class Edge{
        int v,c;
        Edge(int v, int c){
            this.v = v;
            this.c = c;
        }
    }

    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());


        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        vertex = new ArrayList<>();
        vertex.add(new ArrayList<>());

        for(int i=0; i<N; i++){
            vertex.add(new ArrayList<>());
        }

        for(int i=0; i<E; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            vertex.get(a).add(new Edge(b,c));
            vertex.get(b).add(new Edge(a,c));
        }

        st = new StringTokenizer(br.readLine());
        answer = 0;

        v1 = Integer.parseInt(st.nextToken());
        v2 = Integer.parseInt(st.nextToken());

        dijkstra(1, v1);
        if(dist[v1]==INF){
            System.out.println(-1);
            return;
        }
        dijkstra(v1, v2);
        if(dist[v2]==INF){
            System.out.println(-1);
            return;
        }
        dijkstra(v2, N);
        if(dist[N]==INF){
            System.out.println(-1);
            return;
        }
        case1 = answer;
        answer = 0;
        dijkstra(1, v2);
        dijkstra(v2, v1);
        dijkstra(v1, N);


        System.out.println(Math.min(case1, answer));

    }

    static void dijkstra(int start, int end){

        PriorityQueue<Edge> pq = new PriorityQueue<>((e1,e2)->e1.c-e2.c);

        pq.add(new Edge(start,0));

        dist = new int[N+1];
        visit = new boolean[N+1];

        Arrays.fill(dist, INF);
        dist[start] = 0;

        while (!pq.isEmpty()){

            Edge edge = pq.poll();

            if(visit[edge.v]) continue;

            if(edge.c > dist[edge.v]) continue;

            visit[edge.v] = true;

            if(edge.v==end){
                answer += dist[edge.v];
                return;
            }

            for(Edge ne : vertex.get(edge.v)){
                if(dist[edge.v]!=INF && dist[ne.v] > dist[edge.v] + ne.c){
                    dist[ne.v] = dist[edge.v] + ne.c;
                    pq.add(new Edge(ne.v, dist[ne.v]));
                }
            }

        }

    }
}
/*
* 특정 경로를 지나는 최단거리 문제로 포인트는 다익스트라자 기초 응용문제이다.
* 최단거리로 1에서 부터 시작하여 최종적으로 N을 가되 v1, v2를 순서 상관없이 반드시 지나야한다면
* 1 -> v1 -> v2 -> N 경로를 가거나 1 -> v2 -> v1 -> N의 경로로 가면 된다.
* 즉 다익스트라로 1에서 v1에 가는 최단경로 구하고, v1에서 v2로 가는 최단경로, v2에서 N으로 도착하는 최단경로를 구해주면 된다.
* 그 과정에서 아예 길이 끊겨서 못가는 경우는 -1이 나오도록만 표시해준다.
*
* */