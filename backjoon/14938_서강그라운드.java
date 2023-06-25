import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int n,m,r;
    static int[][] vertex;
    static int[] value;
    static final int INF = 1000000000;

    static int answer;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        value = new int[n+1];

        st = new StringTokenizer(br.readLine());
        vertex = new int[n+1][n+1];

        for(int i=1; i<=n; i++){
            value[i] = Integer.parseInt(st.nextToken());
            for (int j=1; j<=n; j++){
                vertex[i][j] = INF;
            }
            vertex[i][i] = 0;
        }

        for(int i=0; i<r; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            vertex[a][b] = c;
            vertex[b][a] = c;
        }

        answer = 1;

        floyd_warshall();

        for(int i=1; i<=n; i++){
            int sum = 0;
            for(int j=1; j<=n; j++){
                if(vertex[i][j] <= m){
                    sum+=value[j];
                }
            }
            answer = Math.max(answer, sum);
        }

        System.out.println(answer);

    }

    static void floyd_warshall(){

        for(int k=1; k<=n; k++){
            for(int i=1; i<=n; i++){
                for(int j=1; j<=n; j++){
                    if(vertex[i][j] > vertex[i][k] + vertex[k][j]){
                        vertex[i][j] = vertex[i][k] + vertex[k][j];
                    }
                }
            }
        }

    }

}
/*
* 그래프 문제 (플로이드 워셜, 다익스트라 등으로 풀 수 있음)
* 입력으로 n = 5, m = 8, r = 4가 입력되었다고 한다면
* 플로이드 워셜은 모든 최단 경로를 구하기 때문에 꼭지점 1에서 출발했을 때, 2에서 출발했을 때 ... 5에서 출발 했을 때의 모든 최단경로를 구할 수 있다.
* 플로이드 워셜을 쓰기 전 고려해야하는 사항은 플로이드 워셜의 경우 O(N^3) 이기 때문에 N이 알고리즘을 작동할 정도로 작기만 하면 수행이 가능하다.
* n 최대 100 이므로 충분히 가능한 범위이고 플로이드 워셜로 모든 최단경로를 구한 후, 거리가 m값 이하인 경우를 모두 더해주고 가장 큰 값을 고르면 된다.
*
* 다익스트라도 이와 원리는 같다. 다익스트라는 특정 꼭지점에서 모든 꼭지점으로 가는 최단 경로를 구하기 때문에
* 꼭지점 개수 만큼 다익스트라를 실행시키면 된다. 그렇게 얻은 최단 경로를 통해 거리가 m값 이하면 더해주어 가장 큰 값을 고르면 된다.
* 다익스트라로 푼 코드는 다음과 같다.

import java.io.*;
import java.util.*;

public class Main {

    static int n,m,r;
    static List<List<Vertex>> vertex;
    static int[] dist, value;
    static boolean visit[];
    static final int INF = 1000000000;

    static class Vertex{
        int v,c;
        Vertex(int v, int c){
            this.v = v;
            this.c = c;
        }
    }

    static int answer;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        value = new int[n+1];
        dist = new int[n+1];

        st = new StringTokenizer(br.readLine());
        vertex = new ArrayList<>();
        vertex.add(new ArrayList<>());

        for(int i=1; i<=n; i++){
            vertex.add(new ArrayList<>());
            value[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<r; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            vertex.get(a).add(new Vertex(b,c));
            vertex.get(b).add(new Vertex(a,c));
        }

        answer = 1;

        for (int i=1; i<=n; i++){
            dijkstra(i);
        }

        System.out.println(answer);

    }

    static void dijkstra(int start){

        PriorityQueue<Vertex> queue = new PriorityQueue<>((e1,e2)->e1.c-e2.c);
        Arrays.fill(dist, INF);
        dist[start] = 0;
        visit = new boolean[n+1];

        queue.add(new Vertex(start,0));

        while(!queue.isEmpty()){

            Vertex curr = queue.poll();

            if(dist[curr.v] < curr.c) continue;

            if(visit[curr.v]) continue;
            visit[curr.v] = true;

            for(Vertex node : vertex.get(curr.v)){
                if(dist[curr.v] != INF && dist[node.v] > dist[curr.v] + node.c){
                    dist[node.v] = dist[curr.v] + node.c;
                    queue.add(new Vertex(node.v, dist[node.v]));
                }
            }
        }
        int result = 0;

        for(int i=1; i<=n; i++){
            if(dist[i]<=m){
                result += value[i];
            }
        }

        answer = Math.max(answer, result);

    }

}
*
* */