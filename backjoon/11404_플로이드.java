import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N, E;
    static int dist[][];

    static final int INF = 100000000;


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        E = Integer.parseInt(br.readLine());

        dist = new int[N+1][N+1];

        for(int i=1; i<=N; i++){
            for(int j=1; j<=N; j++){
                if(i==j) dist[i][j] = 0;
                else dist[i][j] = INF;
            }
        }

        for(int i=0; i<E; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            dist[a][b] = Math.min(dist[a][b], c);

        }

        for(int k=1; k<=N; k++){
            for(int i=1; i<=N; i++){
                for(int j=1; j<=N; j++){
                    dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
                }
            }
        }

        for(int i=1; i<=N; i++){
            for(int j=1; j<=N; j++){
                if(dist[i][j]==INF) System.out.print(0+" ");
                else System.out.print(dist[i][j]+" ");
            }
            System.out.println();
        }

    }
}
/*
* 이 문제는 플로이드-워셜 알고리즘을 적용하면 풀 수 있다.
* */