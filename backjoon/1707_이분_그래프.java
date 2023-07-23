import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static ArrayList<ArrayList<Integer>> list;
    static Integer[] visited;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

         int K = Integer.parseInt(br.readLine());
         StringBuilder sb = new StringBuilder();

         for(int tc=0; tc<K; tc++){

             StringTokenizer st = new StringTokenizer(br.readLine());

             int V = Integer.parseInt(st.nextToken());
             int E = Integer.parseInt(st.nextToken());

             visited = new Integer[V+1];

             list = new ArrayList<>();
             list.add(new ArrayList<>());

             for(int i=0; i<V; i++) list.add(new ArrayList<>());

             for(int i=0; i<E; i++){
                 st = new StringTokenizer(br.readLine());
                 int a = Integer.parseInt(st.nextToken());
                 int b = Integer.parseInt(st.nextToken());
                 list.get(a).add(b);
                 list.get(b).add(a);
             }

             boolean flag = false;

             for(int i=1; i<=V; i++){
                 if(visited[i]==null && !list.get(i).isEmpty()) {
                     visited[i] = 0;
                     flag = dfs(i, 0);
                 }
                 if(flag) break;
             }


             if(flag) sb.append("NO\n");
             else sb.append("YES\n");

         }

         System.out.print(sb);

    }

    static boolean dfs(int curr, int order){

        boolean answer = false;

        for(int val : list.get(curr)){
            if(curr==val) continue;

            if(visited[val]==null){
                visited[val] = (order+1)%2;
                answer = dfs(val, (order+1)%2);
            }
            else{
                answer = visited[val]==order;
            }
            if(answer) break;
        }

        return answer;
    }

}

/*
 * 그래프 이론 문제
 *
 * 필자는 해당 문제를 DFS로 풀어서 DFS 기준으로 설명하겠습니다.
 * 그리고 이 문제를 이해하기 위해선 먼저 이분 그래프를 이해해야합니다.
 * 이분 그래프란 모든 꼭짓점(vetex)을 빨강과 파랑으로 색칠하되, 모든 변(edge)이 빨강과 파랑 꼭짓점을 포함하도록 색칠할 수 있는 그래프입니다.
 * 즉 한 vertex에서 edge를 통해 다른 vertex로 간다면 자신과 다른 색상이 나와야합니다.
 *
 * 문제의 포인트는 쉽게 말해서 빨강 순서가 나와야하는 꼭지점이 파랑을 띄고 있다면 이건 이분 그래프가 아니다 라는 점입니다.
 *
 * 문제를 단순히 생각해서 만약 1,2,3 꼭지점이 있고
 * edge가 1 2,  2 3, 3 1이라면
 * 1. 1번 꼭지점(vertex)에서 시작했다면 이 꼭지점은 색깔이 없으니 빨강색을 칠하자
 * 2. 1번에서 다음 꼭지점으로 도착하면 2번 꼭지점이고 아직 색이 안칠해져있으니 빨강 다음인 파랑을 칠하자
 * 3. 2번에서 3번 꼭지점에 도착하면 2번의 파랑의 다음 색인 빨강을 칠하자
 * 4. 3번에서 출발해서 1번 꼭지점으로 도착했는데 이미 색이 칠해져있으니 색을 비교한다.
 * 5. 3번과 1번은 서로 이어져있음에도 서로 빨강색이므로 이분 그래프가 아니다 NO를 출력한다.
 *
 * 이렇게 풀이가 가능합니다.
 * 여기서 이 문제에서 고려해야하는 점을 더 짚으면 다음과 같습니다.
 * 1. 비연결 그래프일 때
 * 2. 자기 순환 그래프일 때
 *
 * 위의 부분도 고려해서 구현을 하면 풀이가 가능하고 저는 DFS가 적합한 구현 방법이라고 생각하여 DFS로 풀이하였습니다.
 *
 * */