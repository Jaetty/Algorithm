import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int price[], N, M;
    static long answer[];
    static List<Node> edges[];
    static final long INF = Long.MAX_VALUE;

    static class Node{

        int idx, low_price, dist;
        long total;

        Node(int idx, int dist){
            this.idx = idx;
            this.dist = dist;
        }

        Node(int idx, long total){
            this.idx = idx;
            this.total = total;
        }

        Node(int idx, long total, int low_price){
            this.idx = idx;
            this.total = total;
            this.low_price = low_price;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        price = new int[N+1];
        answer = new long[N+1];
        edges = new List[N+1];

        Arrays.fill(answer, INF);

        answer[1] = 0;

        st = new StringTokenizer(br.readLine());

        for(int i = 1; i <= N; i++){

            int val = Integer.parseInt(st.nextToken());
            price[i] = val;
            edges[i] = new ArrayList<>();

        }

        for(int i = 1; i <= M; i++){

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            edges[a].add(new Node(b,d));
            edges[b].add(new Node(a,d));

        }

        dijkstra();

        System.out.println(answer[N]);


    }

    static void dijkstra(){

        Queue<Node> queue = new ArrayDeque<>();
        // 시작 위치, 시작 비용
        queue.add(new Node(1, 0L));
        // { 다음 위치, 비용, 가장 작은 주유 비용 }
        PriorityQueue<Node> pq = new PriorityQueue<>((e1,e2)-> Long.compare(e1.total, e2.total));

        while(!queue.isEmpty()){

            Node start = queue.poll();

            if(start.total > answer[start.idx]){
                continue;
            }

            long[] dist = new long[N+1];
            Arrays.fill(dist, INF);
            dist[start.idx] = start.total;

            for(Node edge : edges[start.idx]){
                // 다음 장소, 다음 장소 갈 때 필요한 비용(거리 * 주유비), 가장 싼 주유비
                pq.add(new Node(edge.idx, (long) edge.dist * price[start.idx] + start.total, price[start.idx]));
            }

            while(!pq.isEmpty()){

                Node poll = pq.poll();

                if(dist[poll.idx] <= poll.total){
                    continue;
                }

                dist[poll.idx] = poll.total;;
                int n_price = poll.low_price;

                // 만약 현재 장소의 주유비가 기존의 값보다 싸다면?
                if(price[poll.idx] < poll.low_price){
                    // 가격 바꿔줌
                    n_price = price[poll.idx];

                    // 이때까지 반복했던 탐색 중 가장 값싸게 도착했다면? 다음 탐색 후보로 만들어줌
                    if(answer[poll.idx] > dist[poll.idx]){
                        queue.add(new Node (poll.idx, dist[poll.idx]));
                    }
                }

                // 다익스트라
                for(Node edge : edges[poll.idx]){

                    long cost = (long) n_price * edge.dist + poll.total;

                    if(dist[edge.idx] > cost ){
                        pq.add(new Node(edge.idx, cost, n_price));
                    }

                }
            }

            // dist 값 중 최솟값으로 갱신
            for(int i=1; i<=N; i++){
                answer[i] = Math.min(answer[i], dist[i]);
            }

        }


    }
}

/*

13308 주유소

다익스트라 + 다이나믹 프로그래밍 문제

아무래도 다양한 풀이 방법이 있을 것 같지만, 저의 풀이 로직의 핵심은
"직전에 출발한 위치의 주유비보다 다음 위치의 주유비가 낮으며 이때까지와 비교해도 가장 값싸게 그 위치에 도착했다면, 탐색 순서에 추가"

이러한 아이디어를 떠올리게 된 이유는 문제에서 제공하는 예시 입력을 바탕으로 떠올렸습니다.
4 4
5 2 4 1
3 1 3
1 2 2
4 3 4
2 4 15

위의 예시 입력의 경우, 정답은 1번 -> 2번 -> 1,3,4번으로 이동합니다.
2번 도시에 가니, 주유비가 2원이고, 2번 도시에서부터 1,3,4번 도시에 들릴 때 굳이 거기서 주유하지 않습니다.
왜냐하면, 1,3,4번 도시의 주유비가 2번 도시보다 비싸기 때문입니다. 때문에, 직전의 도시보다 주유비가 비싸면 그냥 넘어가면 된다는 점을 알 수 있습니다.

그런데, 한번의 다익스트라로는 이를 갱신하기가 어렵습니다. 왜냐하면 다익스트라는 기본적으로 이미 들린 곳에서 다시 간선을 선택하지 않습니다.
제가 생각한 해결법은 다익스트라를 여러 번 돌려주면 되겠다는 것 이었습니다.

그럼, 어떨 때를 기준으로 다익스트라를 여러번 돌려주면 될까?에 대한 저의 대답은 앞서 말했듯 주유비가 낮은 곳에 가장 값싸게 도착했을 때 입니다.

예를 들어, 1번에서 다익스트라를 진행하면 dist 값은 각각 다음과 같이 됩니다.
dist[1번 시작] = { 0, 10, 15, 31 }

여기서 주유비가 낮아지는 도시는 각각, 2번과 3번입니다. 그럼 2번과 3번에서 다시 다익스트라를 해주는 것 입니다.
중요한 점은 시작 점수를 0이 아닌 그 위치에 도달한 값으로 해줍니다. 그럼 다음과 같이 됩니다.
dist[2번 시작] = {14, '10', 20, 28}
dist[3번 시작] = {27, 35, '15', 31}


이렇게 여러번 다익스트라를 하는 이유는 직전 도시보다 주유비가 낮은 도시에서 출발하면 더욱 싸게 N번 도시에 도착할 가능성이 있기 때문입니다.
만약 2번 도시의 주유비가 6이었다고 해보겠습니다. 굳이 2번에서 다시 다익스트라를 할 필요가 있을까요? 그냥 1번에서 주유를 더 많이 해서 가는게 이득입니다.
만약 2번 도시에 도달할 수 있는 경우가 매우 많다면, 그걸 다 신경써야할까요? 아닙니다. 딱, 가장 값싸게 2번에 도달한 경우만 고려하면 됩니다.

이러한 이유로 가장 값싸게 직전의 주유비보다 싼 위치에 도달한 경우
그 위치에서 다익스트라를 시도하여 N까지 가장 값싸게 도달하는 경우를 모두 구해주면 됩니다.

사실 저보다 더 좋은 성능으로 통과한 사람들이 많기 때문에 최적의 답이 아니겠지만, 저는 이러한 방식으로 해당 문제를 풀이했습니다.

*/