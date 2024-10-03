/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

import java.util.*;

class Solution {

    static List<int[]> edges[];
    static int[] s_dist;
    static int answer;
    static final int INF = 100_000_000;

    public int solution(int n, int s, int a, int b, int[][] fares) throws Exception{

        answer = INF;
        s_dist = new int[n+1];
        edges = new List[n+1];

        for(int i=0; i<=n; i++){
            edges[i] = new ArrayList<>();
        }

        for(int i=0; i<fares.length; i++){

            int node1 = fares[i][0];
            int node2 = fares[i][1];
            int cost = fares[i][2];

            edges[node1].add(new int[] {node2, cost});
            edges[node2].add(new int[] {node1, cost});

        }

        dijkstra(n,s,a,b,s);

        for(int start=1; start<=n; start++){

            if(start==s) continue;
            dijkstra(n,s,a,b,start);

        }


        return answer;
    }


    static void dijkstra(int n, int s, int a, int b, int start){

        if(s_dist[start] >= answer) return;

        int[] dist = new int[n+1];

        for(int i=0; i<=n; i++){
            dist[i] = INF;
        }

        dist[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)-> e1[1]-e2[1]);
        pq.add(new int[] {start, 0});

        while(!pq.isEmpty()){

            int[] node = pq.poll();

            if(dist[node[0]] < node[1]){
                continue;
            }

            for(int[] nc : edges[node[0]]){
                if(dist[nc[0]] > nc[1] + node[1]){
                    dist[nc[0]] = nc[1] + node[1];
                    pq.add(new int[] {nc[0], dist[nc[0]]});
                }
            }
        }

        if(s==start){
            s_dist = dist;
        }

        answer = Math.min(answer, s_dist[start] + dist[a] + dist[b]);

    }


}

/*
 * 다익스트라 문제
 *
 * 해당 문제의 경우 포인트는 다음과 같습니다.
 * 1. 합승은 이득이 될 때만 하면 되고 반드시 할 필요는 없다.
 * 2. 최대 n 즉 Vertex은 200, 최대 Edge = n*(n-1)/2
 * 3. 요금의 최대 값은 100,000이다.
 *
 * 문제를 읽어보면 다익스트라로 풀이해야하는 걸 알 수 있습니다. 왜냐하면 어떤 X지점에서 시작해서 Y지점까지의 최소 경로를 묻고 있기 때문입니다.
 * 다만 1번 포인트의 합승의 개념 때문에 응용을 해야합니다. 합승의 개념을 이해해보자면 s에서 부터 다익스트라로 시작하되,
 * s에서부터 한 번 다른 노드로 옮겨서 다익스트라를 수행해도 된다는 뜻입니다.
 *
 * 즉 s에서 다익스트라로 갈 수 있는 모든 구간의 비용 값을 기억해두고 (s_dist[])
 * 어떤 노드 X 위치에서 다익스트라를 수행하여 모든 노드에 가는 비용을 기억한 후 (로컬 변수 dist[])
 * X에서 a와 b로 가는 비용의 합(dist[a] + dist[b]) + s에서 X로 가는 비용(s_dist[X])이라는 뜻 입니다.
 *
 * 그렇다면 모든 노드에서 다익스트라를 수행해줘야한다는 걸 알 수 있습니다.
 * 이 문제는 효율성을 따지는 문제이기 때문에 pq룰 이용한 다익스트라를 200번 수행했을 때 수행 시간적으로 괜찮은지 빅-오 계산을 해보면 대략적으로 판단할 수 있습니다.
 * 최대 V = 200, 최대 E = 19,900 이므로 pq를 이용한 다익스트라의 경우 E log V = 19,900 * 대략 8 = 159200 입니다.
 * 이를 200번 반복하면 200 * 159,200 = 31,840,000 입니다. 대략 1억번의 연산을 1초로 가정했을 때, 최대 0.3초 정도라고 생각하면 됩니다.
 * 보통 알고리즘 사이트에서 효율성 문제의 경우 0.6억 연산이면 간당간당, 0.7억이면 매우 간당간당한 수준으로 통과합니다. (경험상 이야기일 뿐 확실한 이야기는 아닙니다;;)
 * 그래서 저는 효울성 문제를 따질 때 빅-오 연산으로 5천만 이하의 연산 수이면 통과를 확신합니다.
 *
 * 그러므로 위의 로직을 바탕으로 코드를 구현하여 문제를 해결할 수 있었습니다.
 *
 * */