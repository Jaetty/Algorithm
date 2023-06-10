import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */


public class Main {
    static ArrayList<ArrayList<Edge>> vertex;
    static final int INF = 1000000000;
    static StringBuilder sb;
    static int N,M;

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

        sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());

        // 정점의 개수, 간선의 개수
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dist = new long[N+1];
        vertex = new ArrayList<>();

        for(int i=0; i<=N; i++){
            vertex.add(new ArrayList<>());
        }


        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            int T = Integer.parseInt(st.nextToken());

            vertex.get(S).add(new Edge(E,T));
        }

        if(bellmanFord(1)){
            for(int i=2; i<=N; i++){
                if(dist[i]==INF) dist[i] = -1;
                sb.append(dist[i]).append("\n");
            }
        }
        else{
            sb.append("-1\n");
        }

        System.out.println(sb);
    }

    static boolean bellmanFord(int start){

        Arrays.fill(dist, INF);
        dist[start] = 0;

        for (int i=1; i<=N; i++){

            for(int j=1; j<=N; j++){
                for (Edge edge : vertex.get(j)){

                    if( dist[j] != INF && dist[edge.v] > dist[j] + edge.c){
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
* 기초적인 벨만 포드 알고리즘으로 풀면 된다.
* 문제 조건이 underFlow를 발생시킬 수 있으므로 dist는 long타입으로 지정한다.
* 벨만 포드 알고리즘은 다익스트라자와 다르게 음수 값이 있어도 수행할 수 있는 최저거리 찾기 알고리즘이다.
* 눈여겨봐야 할 포인트로는 n-1번까지 순회하고 n번째 순회에도 값에 변화가 생기면 무한 싸이클이 발생한다는 것을 알 수 있다.
* 자세한 설명은 검색을 하면 잘 설명되어 있다.
*
* */