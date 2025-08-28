import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int[][] dist;
    static int[] cnt;
    static int N;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        dist = new int[N+1][N+1];
        cnt = new int[10001];

        for(int i = 1; i <= N; i++){
            Arrays.fill(dist[i], 10000);
        }

        while(true){

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if(a == -1 && b == -1){
                break;
            }

            dist[a][b] = 1;
            dist[b][a] = 1;

        }

        for(int k=1; k<=N; k++){
            for(int i = 1; i <= N; i++){
                for(int j = 1; j <= N; j++){
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)-> e1[1] == e2[1] ? e1[0] - e2[0] : e1[1] - e2[1]);

        int min = 10000;

        for(int i = 1; i <= N; i++){

            int max = 0;

            for(int j = 1; j <= N; j++){
                if(i==j){
                    continue;
                }
                max = Math.max(dist[i][j], max);
            }

            cnt[max]++;
            min = Math.min(min, max);
            pq.offer(new int[]{i, max});
        }

        sb.append(min + " " + cnt[min]).append("\n");

        while(!pq.isEmpty()){

            int[] p = pq.poll();

            if(p[1] > min){
                break;
            }

            sb.append(p[0] + " ");
        }

        System.out.print(sb.toString());

    }

}
/*

2660 회장뽑기

플로이드 워셜 문제

해당 문제는 플로이드 워셜을 이용하면 쉽게 풀이할 수 있는 문제입니다.

문제를 해석하면 다음과 같습니다.
1. 모든 노드가 이어진 그래프가 주어지는데, 각 노드들이 다른 모든 노드에 갈 때의 최단거리를 구하라.
2. 각각의 노드들이 다른 모든 노드에 가는 최단거리가 가장 큰 값이 점수이다.
3. 가장 낮은 점수와 점수가 가장 작은 친구가 몇명 있고 누구인지 출력하라.

해당 문제는 경로를 묻고 있지 않고, 단순히 얼마나 걸리는지에 대해 묻고 있으며, N은 최대 50이기 때문에 플로이드 워셜이 적절합니다.
결국 플로이드 워셜로 모든 노드간의 최단 거리를 구한 후 반복을 통해 가장 작은 점수와 학생을 기억하여 출력하면 풀이할 수 있습니다.

*/