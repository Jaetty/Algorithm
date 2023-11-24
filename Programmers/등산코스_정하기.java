import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {

    static int[] cost, answer;
    static boolean[] summit, gate;
    static List<List<Node>> vertex;
    static Queue<Node> queue;
    static final int INF = Integer.MAX_VALUE;

    static class Node{
        int v, c;
        public Node(int v, int c){
            this.v = v;
            this.c = c;
        }
    }

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        answer = new int[2];
        answer[0] = INF;
        answer[1] = INF;
        cost = new int[n+1];

        queue = new ArrayDeque<>();
        vertex = new ArrayList<>();

        summit = new boolean[n+1];
        gate = new boolean[n+1];

        for(int i=0; i<=n; i++){
            vertex.add(new ArrayList<Node>());
            cost[i] = INF;
        }

        // node와 edge 설정해주기
        for(int i=0; i<paths.length; i++){
            vertex.get(paths[i][0]).add(new Node(paths[i][1], paths[i][2]));
            vertex.get(paths[i][1]).add(new Node(paths[i][0], paths[i][2]));
        }

        for(int ga : gates){
            queue.add( new Node(ga, 0));
            cost[ga] = 0;
            gate[ga] = true;
        }

        for(int su : summits){
            summit[su] = true;
        }

        dijkstra();

        return answer;
    }

    static void dijkstra(){

        while(!queue.isEmpty()){

            Node node = queue.poll();

            // 만약 queue에 먼저 들어왔지만 다른 node가 더 작은 가중치로 해당 node에 도착하게 됐다면 넘어가기
            if(node.c > cost[node.v]) continue;

            for(Node temp : vertex.get(node.v)){

                // 다음 가는 길이 출입구라면 넘어가기
                if(gate[temp.v]) continue;

                // 이때까지 지나온 edge의 가중치와 다음 지나갈 edge 가중치 중 가장 큰 값 넣기
                int max = Math.max(temp.c, node.c);

                // 다음 노드의 가장 작은 가중치 값이 지금의 내 가중치 보다 크다면 들어가기
                if(cost[temp.v] > max){
                    cost[temp.v] = max;

                    // 만약 도착하는 곳이 산봉우리라면 queue에 넣지 않고 answer 값을 비교하여 수정하기
                    if(summit[temp.v]){

                        // 가중치가 작거나, 가중치가 같으면서 산봉우리 번호가 더 작다면 정답 바꿔주기
                        if(answer[1]>max || (answer[1]==max && answer[0]>temp.v)){
                            answer[0] = temp.v;
                            answer[1] = max;
                        }
                    }
                    else{
                        queue.add(new Node(temp.v, max));
                    }
                }


            }


        }

    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제는 다익스트라를 조금 응용한 문제라고 할 수 있습니다.
 *
 * 문제의 포인트는 다음과 같습니다.
 * 1. 출입구와 산봉우리는 여러 개 주어지고 경로 중 출발한 출입구를 제외하고 다른 출입구가 있어서는 안된다.
 * 2. 한 node(산봉우리, 출입구, 휴식지 등등 각각의 지점)과 다른 node로의 이동하는 edge(경로) 중 가장 길었던 값을 기억한다.
 * 3. 출입구에서 산봉우리에 도착했을 때 가장 길었던 경로 값 중에서 가장 작은 값과 똑같다면 가장 산봉우리의 번호가 작은 값을 선택한다.
 *
 * 아이디어
 * 그래프로 생각해봤을 때 산봉우리나 게이트는 출발/도착 node라고 생각할 수 있을 것 입니다. 여기서 gate를 출발 노드로 정하겠습니다.
 * 다음으로 node에서 출발하면 인접한 node로 edge를 타고 가게 될텐데 기존의 다익스트라는 이때까지 지나온 edge의 가중치 + 다음 edge의 가중치를 기준으로 비교합니다.
 * 하지만 해당 문제는 지나온 길 중 가장 긴 edge 하나만 기억하면 되기 때문에 지나왔던 edge 중 가장 가중치가 높았던 값으로 갱신해주면 됩니다.
 * 그리고 다음 노드로 출발하기 전에 이때까지 가장 높았던 edge값을 기준으로 비교하여 진행하거나 멈추거나를 판단하면 됩니다.
 *
 * 이를 코드로 나타내면 풀이할 수 있는 문제입니다.
 *
 *
 *
 * */