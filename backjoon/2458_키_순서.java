import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long[][] dist;
    static int N, M, answer;
    static final int INF = 100000000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        answer = 0;

        dist = new long[N+1][N+1];

        for(int i=1; i<=N; i++){
            for(int j=1; j<=N; j++) dist[i][j] = INF;
            dist[i][i] = 0;
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            dist[a][b] = 0;
        }

        for(int k=1; k<=N; k++){
            for(int i=1; i<=N; i++){
                for(int j=1; j<=N; j++){
                    dist[i][j] = Math.min(dist[i][j], dist[k][j] + dist[i][k]);
                }
            }
        }

        for(int i=1; i<=N; i++){
            int count = 0;
            for(int j=1; j<=N; j++){
                if(dist[i][j] != INF || dist[j][i] != INF) count++;
            }
            if(count == N) answer++;
        }

        System.out.println(answer);

    }

}

/*
* 그래프 이론 문제
*
* 해당 문제는 그래프 이론을 토대로 풀 수 있으며 이번에 제가 선택한 방법은 플로이드 워셜 방법이었습니다.
* (플로이드 워셜을 선택한 이유는 N의 크기를 보고 가능하다고 판단되었고, 자주 쓰지 않았던 알고리즘이어서 복습을 위해서 선택했습니다.)
*
* 플로이드 워셜은 모든 노드에서 다른 모든 노드로 가는 최단 경로를 모두 구해야하는 경우 사용할 수 있습니다.
* 다만 문제점은 이는 O(N^3)이기 때문에 노드가 충분히 작아야만 이용할 수 있는 알고리즘 입니다.
*
* N이 최대 500이었기 때문에 500^3이면 제한시간 1초의 시간초과 위험이 없다고 판단되었습니다.
*
* 해당 문제는 정확히 키 순서를 알고 있는 학생이 몇명인지 묻고 있습니다.
* 그렇다면 내가 나를 제외하고 다른 사람과 모두 키를 비교하면 정확히 키 순서를 알 수 있습니다.
* 이것을 그래프적으로 생각하면 어떤 노드가 있고 이 노드가 다른 모든 노드와 방향에 관계없이 연결되어있다면 키 순서를 알 수 있는 것입니다.
*
* 먼저 서로 연결되지 않은 노드들의 거리는 INF라는 매우 큰 값으로 초기화해줍니다.
* 그리고 플로이드 워셜을 통해 연결된 노드들은 거리를 0으로 만들어줍니다.
* 그러면 연결된 노드끼리는 거리가 0이 될 것이고 이를 비교하여 모든 거리가 노드 N개의 모든 거리가 0인 노드의 갯수를 세어주면 정답을 도출할 수 있습니다.
*
*
* */