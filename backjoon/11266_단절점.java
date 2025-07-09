import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int V, E, cnt;
    static int[] discovery;
    static boolean[] answer;
    static List<Integer>[] edges;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        cnt = 1;

        discovery = new int[V+1];
        answer = new boolean[V+1];
        edges = new ArrayList[V+1];

        for (int i = 0; i < V+1; i++) {
            edges[i] = new ArrayList<>();
            discovery[i] = -1;
        }

        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges[a].add(b);
            edges[b].add(a);
        }

        for(int i = 1; i <= V; i++) {
            if(discovery[i] == -1) {
                dfs(i, 0);
            }
        }

        StringBuilder sb = new StringBuilder();
        int count = 0;

        for (int i = 1; i <= V; i++) {
            if(answer[i]){
                count++;
                sb.append(i).append(" ");
            }
        }

        System.out.println(count);
        if(count > 0){
            System.out.print(sb.toString());
        }

    }

    static int dfs(int now, int parent) {

        discovery[now] = cnt++;
        int low = discovery[now];

        int child = 0;
        for(int next : edges[now]) {

            // 만약 방문했던 곳이면 넘어가기 (부모라도 넘어가기)
            if(discovery[next] != -1) {
                if(parent != next){
                    low = Math.min(discovery[next], low);
                }
            }
            // 방문하지 않았다면
            else{
                child++;
                int child_low = dfs(next, now);
                low = Math.min(child_low, low);

                // 루트 노드가 아니면서, 자식의 low 값이 내 discovery 이상이면.
                if(parent != 0 && child_low >= discovery[now]){
                    answer[now] = true;
                }

            }
        }

        // 루트 노드가 단절점이 되려면 child가 2개 이상이어야함
        if(parent == 0 && child>1){
            answer[now] = true;
        }

        return low;

    }

}

/*

11266 단절점

단절점 문제

이 문제는 단절점 알고리즘을 학습하는 대표적인 문제이므로 풀이보다는 단절점 알고리즘에 대해 적겠습니다.

단절점이란, 무향 그래프에서 특정 노드를 제거했을 때 연결된 컴포넌트(Connected Component)의 갯수가 증가하는 노드를 말합니다.
즉, 모든 노드가 연결된 그래프가 있을 때, 어떤 노드를 제거하면 그래프의 묶음이 2개 이상으로 증가하는 점을 나타냅니다.

이를 발견하는 가장 효율적인 방법은 DFS를 활용하는 방법입니다.
핵심 개념은 2가지 입니다.
1. discovery[u] = 현재 노드를 발견한 시간(depth가 아닙니다!)입니다. 이를 통해 특정 노드에 어느 시간에 최초로 방문했는지 기억합니다.
2. low[u] = 자손 정점들이 방문할 수 있는 가장 작은 '시간'을 뜻합니다.

2번째 개념을 쉽게 말하기 위해 예시로 A-B, B-C, C-A 로 사이클이 발생해있는 그래프에서 A를 root로 dfs를 실행했다고 해보겠습니다.
참고로 바로 직전의 부모 노드로 가는 길은 무시하겠습니다.
2-1.discovery[A] = 1이 되고 유일한 경로인 B로 이동합니다.
2-2 discovery[B] = 2가 되고 유일한 경로인 C로 이동합니다.
2-3 discovery[C] = 3이 됩니다. C는 A로 이동할 수 있습니다. 즉, C가 방문 가능한 가장 작은 시간 => low[C] = discovery[A]가 됩니다.

이제 위와 같은 두 가지의 개념을 활용하면 단절점 판별 조건을 구할 수 있습니다.
1. 루트 노드이면서 자식이 두 개 이상의 경우 단절점이 됩니다.
2. 루트 노드가 아니면서, 정점 U가 그 자식 V가 있을 때, low[v] >= discovery[u] 일때.

2번의 경우 왜 이게 성립하냐면, discovery[U] 값이 low[V] 값보다 작다는 것은 내가 사라져도 자식들은 조상 노드들과 잘 연결된다는 뜻입니다.
즉, U 노드는 자식과 부모를 잇는 유일한 통로가 되지 못한다는 뜻입니다.
반대로 말하면 low[V] 값이 discovery[U] 이상이라면? U 노드는 V 노드 이하 노드에 가려면 반드시 거쳐야하는 유일한 통로라는 것입니다.

때문에 위의 조건을 바탕으로 코드를 구현하기만 하면 해당 문제를 풀이할 수 있고, 단절점의 개념을 잡을 수 있을 것 입니다.

*/