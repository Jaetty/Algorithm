import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int N, M, K, start, end;
    static final int INF = 1000*1000*10;
    static long[] answerList;
    static List<int[]>[] edges;

    static class Node{
        int cur, cost, cnt;

        Node(int cur, int cost, int cnt){
            this.cur = cur;
            this.cost = cost;
            this.cnt = cnt+1;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        answerList = new long[N+1];
        edges = new List[N+1];

        st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        for(int i = 0; i <= N; i++){
            edges[i] = new ArrayList<>();
        }

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            edges[a].add(new int[] {b, w});
            edges[b].add(new int[] {a, w});
        }
        dijkstra();
        sb.append(getAnswer(0)).append("\n");

        for(int i = 0; i < K; i++){
            int val = Integer.parseInt(br.readLine());
            sb.append(getAnswer(val)).append("\n");
        }
        System.out.print(sb.toString());
    }

    static void dijkstra(){

        int[][] dist = new int[N+1][N+1];

        for(int i = 0; i <= N; i++){
            for(int j = 0; j <= N; j++){
                if(j==start) continue;
                dist[i][j] = INF;
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>((e1,e2)-> e1.cost-e2.cost);
        Set<Integer> temp = new HashSet<>();
        temp.add(start);


        for(int[] val : edges[start]){
            dist[1][val[0]] = Math.min(val[1], dist[1][val[0]]);
            pq.offer(new Node(val[0], val[1], 0));
        }


        while(!pq.isEmpty()){

            Node node = pq.poll();

            if(dist[node.cnt][node.cur] < node.cost || node.cnt == N-1){
                continue;
            }

            for(int[] val : edges[node.cur]){

                int cost = node.cost + val[1];

                if(dist[node.cnt+1][val[0]] > cost){
                    dist[node.cnt+1][val[0]] = cost;
                    pq.offer(new Node(val[0], cost, node.cnt));
                }

            }

        }

        for(int i = 1; i < N; i++){
            answerList[i] = dist[i][end];
        }

    }

    static long getAnswer(int add){

        long min = Long.MAX_VALUE;

        for(int i = 1; i < N; i++){
            answerList[i] += (long)i * add;
            min = Math.min(min, answerList[i]);
        }

        return min;
    }

}
/*

13907 세금

다익스트라 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. 최초에 다익스트라를 한 번 돌려서 dist[지나온 경로 수][현재 위치]로 배열을 만들어서 최솟값을 기억합니다.
2. 위에서 나온 dist값에서 도착 위치에 도달한 모든 경우를 answerList[지나온 경로 수]에 넣어 기억합니다.
3. 세금을 올릴 때의 증가값은 answerList[경로 수] += 경로 수 * 증가값으로 갱신한 후 최솟값을 출력해줍니다.

문제의 경우, Edge의 수를 나타내는 M이 30,000, N의 1,000개 있습니다. 증세는 K번 (최대 30,000)일어납니다.
만약, 세금을 올릴 때마다 다익스트라를 해준다면 K * M Log N = 대략 90억의 연산 이므로 시간 초과를 벗어날 수 없습니다.

그렇다면 탐색을 최소화해야 하는데, 생각해보면 굳이 탐색을 다시 할 필요가 없습니다.

세금이 증가할 때 최솟값이 변하는 경우는 도착지에 몇 다리 건너서 도착했냐에 따라 달라집니다.
즉, min[지나온 edge 수] += edge수 * 세금 증가 값입니다.

그렇다면, 다익스트라를 최초에 시도할 때 경로의 수도 고려한 다익스트라를 돌려주어서 min[지나온 edge 수]를 기억할 수 있다면?
나중에는 K * N 번의 반복만으로 정답을 낼 수 있게 되는 것 입니다.

그러면 edge 최대 수가 M개 인데 몇개까지 고려하면 될까요?
정답은 N-1개 까지만 고려하면 됩니다. 모든 edge의 개수만큼 고려할 필요가 없습니다.

그냥 수평으로 쭉 하나의 간선으로 이어진 노드 그래프를 상상해보시고, 1번 노드에서 마지막 노드까지 도달하는데 가장 오래 걸리는 시간은
모든 N-1번 즉, 모든 노드를 한번씩 들리고 도착하는 방법일 것 입니다.
거기에 edge가 얼마나 많이 추가되서 N번 이상 간선을 들린다면 그건 어떤 경우에라도 최악의 경우이니 최솟값이 애초에 될 수 없는 후보입니다.

이렇듯, 위의 3 로직을 바탕으로 코드를 구현하면 풀이가 가능한 문제입니다.

*/