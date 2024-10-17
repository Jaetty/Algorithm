import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {

    static int answer;
    static int[] node;
    static int[][] path;

    public int solution(int[] info, int[][] edges) {

        answer = 0;

        node = info;
        path = edges;
        dfs(0, new boolean[info.length], 0, 0);

        return answer;
    }

    static void dfs(int idx, boolean[] visited, int sheep, int wolf){

        visited[idx] = true;

        if(node[idx]==0){
            sheep++;
        }else{
            wolf++;
        }

        if(wolf >= sheep) return;

        answer = Math.max(answer, sheep);

        for(int[] edge : path){

            if(visited[edge[0]] && !visited[edge[1]]){

                boolean[] next = visited.clone();
                dfs(edge[1], next, sheep, wolf);

            }
        }
    }

}

/*
 * DFS 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 트리는 이진 트리 형태이고, 노드를 나타내는 info의 최대 길이는 17.
 * 2. 양을 데리고 반드시 루트 노드로 돌아와야 한다.
 *
 * 해당 문제는 읽어보면 트리를 탐색하는 DFS가 적절하다는 점을 쉽게 유추할 수 있습니다.
 * 2번 포인트로 알 수 있는 점은 루트 노드에서 탐색을 시작하면 된다는 점입니다.
 * 또한, 1번 포인트로 알 수 있는 점은 최대 노드의 갯수가 17이므로 이진트리의 경우 edge는 node-1개 만큼 존재합니다.
 * 그렇다면 이는 완전 탐색을 진행해도 전혀 문제 없는 시간이라는 점을 알 수 있습니다.
 *
 * 이 문제의 유의점은 DFS로 어떻게 탐색을 하냐 입니다.
 * 단순히 DFS로 탐색을 진행하면 일방향이 되기 때문에 왔던 곳 기준으로 다시 돌아가는 형태의 진행이 불가합니다.
 * 그러므로 취할 수 있는 방법은 모든 edge를 탐색하면서 자식 노드로 가기 전 그 자식 노드의 부모 노드를 방문 했으며 진행하게 만들어줍니다.
 * 이러면 모든 경우의 수를 찾아낼 수 있게 구현할 수 있습니다.
 *
 */