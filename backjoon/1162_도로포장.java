import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, K;

    static List<int[]> edges[];
    static long[][] dist;

    static final long INF = 50_000L * 1_000_001L;

    static class Node {

        int num, k;
        long dist;

        Node(int num, int k, long dist) {
            this.num = num;
            this.k = k;
            this.dist = dist;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dist = new long[K+1][N];
        edges = new ArrayList[N];

        for (int i = 0; i < N; i++) {
            edges[i] = new ArrayList<>();
        }

        for(int i = 0; i <= K; i++){
            Arrays.fill(dist[i], INF);
        }

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken());

            edges[a].add(new int[]{b, d});
            edges[b].add(new int[]{a, d});

        }

        dijkstra();

        long answer = INF;

        for(int k=0; k<=K; k++){
            answer = Math.min(dist[k][N-1], answer);
        }

        System.out.print(answer);

    }

    static void dijkstra(){

        PriorityQueue<Node> pq = new PriorityQueue<>((e1,e2)-> Long.compare(e1.dist, e2.dist));
        // 각각 도착 위치, 누적 거리, 포장 횟수
        pq.add(new Node(0,0,0));

        while(!pq.isEmpty()){

            Node node = pq.poll();
            if(dist[node.k][node.num] <= node.dist){
                continue;
            }

            dist[node.k][node.num] = node.dist;

            for(int[] edge : edges[node.num]){

                // 평범한 다익스트라 부분.
                if(dist[node.k][edge[0]] > node.dist + edge[1]){
                    pq.add(new Node(edge[0], node.k, node.dist + edge[1]));
                }
                // 만약 현재 나의 도로 포장 횟수가 K번 밑이고,
                // 도로 포장을 한번 더 했을 때의 최저 비용 값이 현재까지의 비용보다 낮다면 pq에 추가한다.
                if(node.k < K && dist[node.k+1][edge[0]] > node.dist){
                    pq.add(new Node(edge[0], node.k+1, node.dist));
                }

            }

        }
    }

}

/*

1162 도로포장

다익스트라 + 다이나믹 프로그래밍 문제

해당 문제를 쉽게 요약하면.
M개의 간선에서 K개 이하만큼의 간선을 선택하여 비용을 0으로 만들 수 있다면, 이를 이용하여 1부터 N까지 도달하는 최저 비용을 찾아라 입니다.

저는 문제를 보고선 다익스트라가 떠올랐지만 뭔가 고민하다가 차라리 BFS가 낫지 않나? 고민하게 되었습니다.
BFS 문제 중 "벽 부수고 이동하기 4"라는 문제가 있는데 이 문제와 조금 유사하다고 느꼈기 때문입니다.

그러다가 "벽 부수고 이동하기 4"의 핵심 개념을 그대로 다익스트라에 적용하면 어떨까 라는 생각이 들게 되었습니다.

즉, 핵심 로직을 정리하자면 다음과 같습니다.
1. 다익스트라의 최저 비용 방문을 나타내는 변수 dist[도로를 포장한 횟수][도달 위치] 형태의 2차원 배열로 만든다.
2. 다익스트라를 수행하되, pq에 edge를 넣어주는 과정에서 만약 현재 도로를 포장한 횟수가 K 미만이라면 다음 위치를 0의 비용으로 도착한다.
3. 이를 고려하여 다익스트라를 전부 수행한 후, dist[0][N] 부터 dist[K][N]까지의 값 중 가장 최솟값을 출력한다.

이렇게하면, 20 * 50,000 Log 10,000 정도가 나오지 않을까? 그러면 대략 1000천만 + @ 연산 정도로 가정한다면 괜찮은 시간이다고 판단했습니다.

즉, 그래프 탐색에서 쓸 수 있는 다이나믹 프로그래밍 개념을 다익스트라에 적용하면 풀이 가능한 문제였습니다.


*/