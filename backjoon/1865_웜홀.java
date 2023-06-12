import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */


public class Main {
    static ArrayList<ArrayList<Edge>> vertex;
    static final int INF = 1000000000;
    static StringBuilder sb;
    static int N,M,W;

    static long[] dist;

    static class Edge{
        int v; // 정점
        int c; // 비용
        public Edge(int v, int c) {
            this.v = v;
            this.c = c;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int TC = Integer.parseInt(br.readLine());
        sb = new StringBuilder();

        for(int testcase=0; testcase<TC; testcase++){

            StringTokenizer st = new StringTokenizer(br.readLine());

            // 정점의 개수, 간선의 개수
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            dist = new long[N+1];
            vertex = new ArrayList<>();

            for(int i=0; i<=N; i++){
                vertex.add(new ArrayList<>());
            }


            for (int i = 0; i < M+W; i++) {
                st = new StringTokenizer(br.readLine());
                int S = Integer.parseInt(st.nextToken());
                int E = Integer.parseInt(st.nextToken());
                int T = Integer.parseInt(st.nextToken());

                if(i<M){
                    vertex.get(S).add(new Edge(E,T));
                    vertex.get(E).add(new Edge(S,T));
                }
                else{
                    vertex.get(S).add(new Edge(E,-T));
                }

            }

            if(!bellmanFord(1)){
                sb.append("YES\n");
            }
            else sb.append("NO\n");

        }
        System.out.println(sb);

    }

    static boolean bellmanFord(int start){

        Arrays.fill(dist, INF);
        dist[start] = 0;

        for (int i=1; i<=N; i++){

            for(int j=1; j<=N; j++){
                for (Edge edge : vertex.get(j)){

                    if(dist[edge.v] > dist[j] + edge.c){
                        dist[edge.v] = dist[j] + edge.c;
                        if(i==N){
                            return false;
                        }
                    }

                }
            }
        }

        return true;
    }
}

/*
 *
 * 기초적인 벨만 포드 알고리즘에서 약간 응용해서 풀면 된다.
 * 문제의 포인트는 기본 벨만 포드 알고리즘 처럼 최소 비용을 찾는 것이 아니다. 무한 순환이 일어나는지만 확인하면 된다.
 * 기본 벨만 포드 알고리즘에서 dist[a] != INF 조건으로 아직 방문하지 않은 노드에 대해서 무시하고 다음 노드에 대한 간선을 가져오는데
 * 이 조건 부분만 없애주면 아직 방문하지 않았더라도 노드에서 부터 탐색이 시작된다. 결국 모든 노드에 탐색을 허용하면 최소 비용은 찾지 못하더라도
 * 무한 순환이 일어나는지는 판단할 수 있게 된다.
 *
 * */