import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{
        int next;
        int cost;

        Node(int next, int cost){
            this.next = next;
            this.cost = cost;
        }
    }
    static List<Node> edge[];
    static boolean flag;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        edge = new List[N+1];

        for(int i=0; i<=N; i++){
            edge[i] = new ArrayList<>();
        }

        int left = 0;
        int right = 1;

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edge[a].add(new Node(b,c));
            edge[b].add(new Node(a,c));

            right = Math.max(right, c);
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());


        while(left<=right){

            int mid = (left+right) / 2;
            flag = false;

            boolean[] check = new boolean[N+1];

            dfs(start, mid, check, end);

            if(flag){
                left = mid+1;
            }
            else{
                right = mid-1;
            }
        }

        System.out.print(right);

    }

    static void dfs(int curr, int weight, boolean[] paths, int end){

        if(curr==end){
            flag = true;
            return;
        }

        paths[curr] = true;

        for(Node node : edge[curr]){

            if(flag) return;

            if(!paths[node.next] && node.cost >= weight){
                dfs(node.next, weight, paths, end);
            }
        }

    }

}

/*
 * 그래프 이론 + 이분 탐색 문제
 *
 * @@@@@@@ 해당 문제는 풀이를 참고하였습니다. @@@@@@@@
 * 이 문제를 처음에 BFS, DFS로만 풀이하려고 하니까 메모리 초과를 겪었습니다.
 * 이를 해결하기 위해 Map, PriorityQueue 등을 활용해보았지만 문제 해결에 실패하였습니다. 제가 알아본 해법은 이분 탐색을 응용한 방법이었습니다.
 *
 * 문제의 포인트는 다음과 같았습니다.
 * 1. 노드는 2~10000개가 주어진다.
 * 2. 간선은 양방향으로 100000개가 주어지며 중복된 값의 간선이 주어질 수 있다.
 *
 * 해당 문제는 대표적인 그래프 탐색 문제로 풀이를 위해서 BFS, DFS를 이용할 수 있었습니다.
 * 노드의 최대 크기와 간선의 수를 고려했을 때 탐색 중 가지치기 / 입력 데이터의 전처리가 중요하다고 생각하고 풀이하였으나 실패하였습니다.
 *
 * 그래서 제가 찾은 방법은 이분 탐색을 응용한 가지치기 방법이었습니다.
 *
 * 해결 방법은
 * 1. 간선에서 주어지는 최대 중량 값을 기억합니다.
 * 2. 최대 중량 값을 이분 탐색의 오른쪽 포인트로 이용하여 (0 + 최대 중량) / 2 = 중간 값의 중량으로 시작 노드에서 마지막 노드에 도달 가능한지 확인합니다.
 *    만약 도달한다면 (중간 값 + 최대 중량) / 2의 값으로도 시작 노드에서 마지막 노드에 도달 가능한지, 안된다면 (0 + mid) / 2 로 도달 가능한지 봅니다.
 *
 * 위의 방법을 통해서 정해진 중량 값 이하면 아예 탐색을 지속하지 않도록 만들 수 있으므로 가지치기가 매우 수월해집니다.
 *
 *
 *
 * */