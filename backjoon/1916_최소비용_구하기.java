import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */


public class Main {

    static int N, E;
    static int start, end;
    static ArrayList<ArrayList<Edge>> cities;
    static int dist[];

    static boolean visit[];
    static final int INF = Integer.MAX_VALUE;

    static class Edge{
        int node;
        int weight;

        Edge(int node, int weight){
            this.node = node;
            this.weight = weight;
        }

    }


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        E = Integer.parseInt(br.readLine());

        dist = new int[N+1];
        visit = new boolean[N+1];
        cities = new ArrayList<>();

        for(int i=0; i<=N; i++){
            cities.add(new ArrayList<>());
            dist[i] = INF;
        }

        for(int i=0; i<E; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            cities.get(s).add(new Edge(e,c));
        }

        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        dijkstra();

        System.out.println(dist[end]);


    }

    static void dijkstra(){

        dist[start] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1,e2)-> e1.weight - e2.weight);
        pq.add(new Edge(start,0));

        while(!pq.isEmpty()){

            Edge cur_node = pq.poll();

            if(cur_node.weight > dist[cur_node.node]) continue;

            if(visit[cur_node.node]) continue;

            visit[cur_node.node] = true;


            for(Edge edge : cities.get(cur_node.node)){
                if(!visit[edge.node] && edge.weight + dist[cur_node.node] < dist[edge.node]){
                    dist[edge.node] = dist[cur_node.node] + edge.weight;
                    pq.add(new Edge(edge.node,dist[edge.node]));
                }

            }


        }

    }

}
/*
* 이 문제는 다익스트라자 완전 기초 문제로 다익스트라자를 적용만 할 수 있으면 풀 수 있다.
* */