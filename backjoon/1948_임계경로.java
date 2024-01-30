import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static class Node{
        int to;
        int time;

        Node(int to, int time){
            this.to = to;
            this.time = time;
        }

    }
    static List<Node>[] forward, reverse;
    static long time[], topology[], count;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        forward = new List[N+1];
        reverse = new List[N+1];

        time = new long[N+1];
        topology = new long[N+1];

        for(int i=1; i<=N; i++){
            forward[i] = new ArrayList();
            reverse[i] = new ArrayList();
        }

        for(int i=0; i<M; i++){
             st = new StringTokenizer(br.readLine());
             int s = Integer.parseInt(st.nextToken());
             int e = Integer.parseInt(st.nextToken());
             int t = Integer.parseInt(st.nextToken());

             topology[e]++;

             Node node1 = new Node(e, t);
             Node node2 = new Node(s, t);
             forward[s].add(node1);
             reverse[e].add(node2);
        }

        st = new StringTokenizer(br.readLine());

        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        topologySet(start, end);
        bfs(end, start);

        System.out.println(time[end]);
        System.out.println(count);

    }

    static void topologySet(int start, int end){

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);

        while(!queue.isEmpty()){

            int from = queue.poll();

            for(Node node : forward[from]){

                time[node.to] = Math.max(time[from] + node.time, time[node.to]);
                if(--topology[node.to] == 0){
                    if(node.to != end) queue.add(node.to);
                }

            }
        }


    }

    static void bfs(int start, int end){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{start, 0});
        boolean[] visited = new boolean[N+1];
        visited[start] = visited[end] = true;

        while(!queue.isEmpty()){

            int[] from = queue.poll();

            for(Node node : reverse[from[0]]){

                if(time[from[0]] - node.time == time[node.to]){
                    count++;
                    if(!visited[node.to]){
                        visited[node.to] = true;
                        queue.add(new int[]{node.to, from[1]+1});
                    }
                }

            }
        }

    }
}

/*
* 방향 비순환 그래프 + 위상 정렬
*
* 해당 문제를 처음 읽었을 때 이해가 잘 안갔는데 문제를 쉽게 풀자면 다음과 같습니다.
* " a라는 노드에서 출발해서 b라는 노드에 도착할 때 가장 오래걸리는 시간과 그 시간에 도착하는 탐색들이 지나온 간선의 수를 중복없이 출력하라. "
*
* 이 문제의 경우 순환이 없고 출발 노드에서 나가는 방향의 간선만 있고 도착 노드는 들어오는 간선만 존재합니다.
*
* 문제의 포인트는 다음과 같습니다.
* 1. 그래프 탐색을 이용하여 도착 노드에 도착하는 최대 시간을 구하기
* 2. 탐색 중 지나온 경로 중 중복되는 경로를 없애기
*
* 1번의 포인트의 경우 사실 BFS, DFS로 쉽게 시간을 구할 수 있습니다.
* 저의 경우는 BFS를 이용하고 위상 정렬을 이용하여 쓸데없이 모든 경우의 수를 다 돌 필요 없게 하여 메모리와 시간이 낭비되지 않게 했습니다.
* 결국 포인트는 출발 노드의 비용 + 다음 노드로 가는데 비용 값 중 가장 큰 값을 다음 노드의 비용 값으로 만들어주면 됩니다.
* 다음과 같은 노드와 간선이 있다면
* 1 2 4
* 1 3 1
* 2 4 2
* 3 4 5
*
* [1] = 0, [2] = 4 + [1], [3] = [1] + 2, [4] = Max([2]+2, [3]+5);
*
* 위와 같이 구해주면 됩니다.
*
* 2번의 경우 가장 많이 고민한 파트인데
* (사실 이부분에 대해서는 머리를 잘 써서 풀었다기보다는 방향 비순환 그래프에는 역방향으로 진행하면 풀이가 가능한 경우가 종종 있다보니 일단 역방향 풀이를 고민해보다가 얻어걸린 느낌입니다;;)
* 역방향으로 가게 된다면 처음에 최대 시간을 정했던 것과는 반대로 최대 시간을 걸린 루트를 따라서만 그래프 탐색이 가능합니다.
*
* 백준 질문게시판에 있던 반례 중 하나를 예시로 들자면 다음과 같습니다.
5
7
1 2 1
1 3 3
2 3 2
2 4 1
2 5 3
3 5 1
4 5 1
1 5

* 각 노드의 도착 최대 시간 값은 [1] = 0, [2] = 1, [3] = 3, [4] = 2, [5] = 4 입니다.
*
* 그러면 이제 마지막 위치인 5에서 역방향으로 추적을 하게 된다면
* 5와 연결된 2,3,5 노드에 대해서 [5] 값에서 간선의 비용만큼 뺏을 때 [2], [3], [4] 값이 같은지 확인합니다.
* 2번 노드 같은 경우 5에서 2로 가기 위해서는 3이 필요합니다. 그럼 [5]-3 = 1이고 이는 [2]의 값과 같습니다.
* 그렇다면 [2]는 최대 시간이 걸리는 경로 중 하나이기 때문에 경로 count를 하나 올려주고 queue에 넣어줍니다.
* [4]의 경우는 [5]-1 = 3이고 [4] = 2이므로 서로 같지 않습니다. 그렇다면 최대 시간이 걸리는 경로가 아니기 때문에 count를 올리지 않고 queue도 올려주지 않습니다.
* [3]도 최대 경로입니다.
*
* 이제 queue에는 [2,3]이 있습니다.
* [2]의 경우 역방향으로는 1로 가는 길 밖에 없고 당연히 count를 하나 올려줍니다.
*
* [3]의 경우는 좀 다른데 3은 1로도 갈 수 있고 2로도 갈 수 있습니다. 그리고 이 둘 다 [3] - 간선 비용 == [다음 노드] 조건을 충족합니다.
* 하지만 여기서 주의할 점은 이미 2를 큐에 넣었었고 2로 이미 탐색했다는 점 입니다.
* 그렇다면 2를 큐에 다시 넣게 되면 2에 대해서 다시 추적해야하고 경로 값이 중복되어 count됩니다.
* 그러므로 이미 들린 노드는 표시를 해서 count값은 올려줘도 다시 queue에 오르지 못하게 막아줘야 합니다.
*
* 위와 같이 처리하게 되면 경로를 중복되지 않고 정확히 카운트하는 것이 가능하게 됩니다.
*
* 해당 포인트를 바탕으로 코딩을 하면 문제를 풀이할 수 있습니다.
*
*
* */