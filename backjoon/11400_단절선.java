import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int V, E, time;
    static int[] discovery;
    static List<Node> answer;
    static List<Integer>[] edges;

    static class Node{
        int x, y;

        Node(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        time = 1;

        discovery = new int[V+1];
        answer = new ArrayList<>();
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

        for (int i = 1; i <= V; i++) {

            if(discovery[i] == -1) {
                dfs(i, 0);
            }

        }

        StringBuilder sb = new StringBuilder();

        System.out.println(answer.size());

        if(!answer.isEmpty()) {
            Collections.sort(answer, (e1,e2)->{
                if(e1.x == e2.x){
                    return e1.y - e2.y;
                }
                return e1.x - e2.x;
            });
            for(int i=0; i<answer.size(); i++) {
                Node node = answer.get(i);
                sb.append(node.x +" " + node.y).append("\n");
            }
            System.out.print(sb.toString());
        }

    }

    static int dfs(int now, int parent) {

        discovery[now] = time++;
        int low = discovery[now];

        for(int next : edges[now]) {

            // 만약 방문했던 곳이라면?
            if(discovery[next] != -1) {
                // 직전 노드가 아닐 경우만 low 갱신
                if(next != parent){
                    low = Math.min(discovery[next], low);
                }
            }
            // 방문하지 않았다면
            else{
                int child_min = dfs(next, now);
                low = Math.min(child_min, low);

                // 방문하지 않았으며 자식 노드들이 반드시 나를 거쳐야만 갈 수 있는 곳이라면
                if(child_min > discovery[now]){

                    if(now < next){
                        answer.add(new Node(now, next));
                    }
                    else{
                        answer.add(new Node(next, now));
                    }
                }

            }
        }

        return low;

    }

}

/*

11400 단절선

단절점 문제

이 문제는 단절점 알고리즘의 응용 문제입니다.

단절점은 특정 노드를 제거했을 때 연결된 컴포넌트 개수가 증가하는 노드를 찾는 문제였습니다.
단절선은 이를 간선의 영역으로 가져와서 어떤 간선을 제거하면 연결된 컴포넌트가 증가하는 간선을 찾는 것 입니다.

이는 단절점의 아이디어를 잘 생각해보면 방법을 쉽게 응용할 수 있습니다.
단절점에서 단절점이 되는 2가지 조건은 다음과 같았습니다.
1. 루트 노드이면서 자식이 두 개 이상의 경우
2. 루트 노드가 아니면서, 정점 U가 그 자식 V가 있을 때, low[v] >= discovery[u] 일때.

하지만 단절선은 노드를 제거하는 것이 아니기 때문에 루트 노드 판별이 무의미해집니다.
그러니까 1번 조건은 아예 없어지게 되고, 2번 조건도 다음과 같이 바뀌게 됩니다.

2. 정점 U가 그 자식 V가 있을 때, low[v] >= discovery[u] 일때.
2번의 경우가 나타내는 점은 low[V] 값이 discovery[U] 이상이라면, U 노드는 V 노드 이하 노드에 가려면 반드시 거쳐야하는 유일한 통로라는 것입니다.

그렇다면 반대로 말하면 유일한 통로(간선)을 제거하게 되면? 연결된 컴포넌트가 증가하게 된다는 뜻이 됩니다.
때문에, 단절점 문제에서 1번 조건을 제거하고, 2번 조건을 모든 노드에 대해서 적용시키게 된다면 단절선을 판단할 수 있게 됩니다.

이를 바탕으로 코드를 작성하면 단절선을 찾는 것은 쉬울 것 입니다.
이제 문제에서 제시하듯, 노드의 숫자가 작은 것을 앞에 나오게 해서 기억하게 되면 트리의 구조상 중복된 간선은 저장되지 않습니다.
즉, 단절선이 판별되었을 때 (작은 노드, 큰 노드)를 List에 따로 모아주면 문제를 쉽게 해결할 수 있습니다.

*/