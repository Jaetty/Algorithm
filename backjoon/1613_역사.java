import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, K, S;
    static Integer[][] dist;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dist = new Integer[N+1][N+1];

        for(int i=0; i<K; i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            dist[start][to] = 0;
        }

        for(int k=1; k<=N; k++){
            for(int i=1; i<=N; i++){
                for(int j=1; j<=N; j++){

                    if(i==j || j==k || dist[i][j] != null) continue;
                    if(dist[i][k] != null && dist[k][j] != null) dist[i][j] = 0;
                }
            }
        }


        S = Integer.parseInt(br.readLine());

        for(int i=0; i<S; i++){

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(dist[b][a]!=null){
                sb.append(1).append("\n");
            }
            else if(dist[a][b]!=null){
                sb.append(-1).append("\n");
            }
            else{
                sb.append(0).append("\n");
            }
        }
        System.out.print(sb.toString());

    }

}

/*
 * 그래프 이론(플로이드 워셜) 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. N <= 400, K <= 50,000, S <= 50,000
 * 2. 간선은 단방향으로 주어지고, 사이클이 형성되지는 않는다.
 *
 * 이 문제의 핵심은 a에서 b로 갈 수 있다면 a는 b보다 먼저 일어난 역사.
 * b에서 a로 갈 수 있다면 b가 먼저 일어난 역사.
 * 둘 다 서로 도달 못한다면 모르는 역사 라는 점 입니다.
 *
 * 먼저 이 문제는 S만큼 일일히 BFS를 수행하면 시간초과임을 금방 알 수 있습니다.
 * 왜냐하면 a부터 출발해서 b가 있는지 확인 작업. 만약 아닐 시 b에서 a로 갈 수 있는지 확인을 해야하기 때문에
 * 대략 50000 * 50000번 반복해야함으로 시간초과 및 메모리 초과를 벗어나지 못할 것 입니다.
 *
 * 그렇다면 되도록 한번의 탐색으로 서로의 경로가 이어져있는지 확인하도록 해야합니다.
 * 다익스트라도 방법일 수는 있지만 다익스트라는 Edge의 크기만큼 시간이 더 필요합니다.
 * 그러나 플로이드 워셜의 경우 Edge의 크기와 전혀 상관없이 O(V^3)만큼의 시간만으로 모든 정점간의 연결을 알 수 있습니다.
 *
 * 이에 따라 플로이드 워셜을 적용하여 모든 정점의 연결 여부를 확인한 후, 언제 일어났는지 확인하는 절차를 수행하면 풀이할 수 있습니다.
 *
 * */