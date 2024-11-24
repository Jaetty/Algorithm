import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int V, E;
    static final int INF = 1000000000;
    static int[][] edges;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        edges = new int[V+1][V+1];

        for(int i=1; i<=V; i++){
            for(int j=1; j<=V; j++){
                edges[i][j] = INF;
                if(i==j) edges[i][j] = 0;
            }
        }

        for(int i=0; i<E; i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken()), to = Integer.parseInt(st.nextToken()), cost = Integer.parseInt(st.nextToken());
            edges[start][to] = cost;
        }

        for(int k=1; k<=V; k++){
            for(int i=1; i<=V; i++){
                for(int j=1; j<=V; j++){
                    edges[i][j] = Math.min(edges[i][j], edges[i][k] + edges[k][j]);
                }
            }
        }

        int answer = INF;

        for(int i=1; i<=V; i++){
            for(int j=1; j<=V; j++){

                System.out.print(edges[i][j]+" ");
                if(i==j) continue;
                answer = Math.min(answer, edges[i][j] + edges[j][i]);

            }
            System.out.println();
        }

        System.out.println(answer>=INF ? -1 : answer);

    }
}

/*
 * 플로이드-워셜 문제
 *
 * 해당 문제의 핵심은 결국 플로이드-워셜 알고리즘 적용입니다.
 *
 * 문제를 읽어보면 가장 작은 비용으로 사이클이 발생하는 구간을 찾고 있습니다.
 * 그렇다면 먼저 모든 정점에서부터 다른 모든 정점에 최소의 비용으로 가는 방법을 구합니다.
 * 그리고 이렇게 구한 비용을 바탕으로 정점 a 에서 b, b 에서 a로 가는 비용의 합이 가장 작은 값을 찾아주면 풀이할 수 있습니다.
 *
 *
 * */