import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int[] parent, edge;
    static int N, Q;
    static int[][] query;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        query = new int[N-1+Q][3];
        init();

        for (int i = 2; i <= N; i++) {
            edge[i] = Integer.parseInt(br.readLine());
        }

        for(int j=0; j<Q+N-1; j++){

            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            query[j][0] = a;
            query[j][1] = b;

            if(a==1){
                query[j][2] = Integer.parseInt(st.nextToken());
            }
        }

        String[] answer = new String[Q];
        int idx = Q-1;

        for(int i=(N-1+Q)-1; i>=0; i--){

            int cmd = query[i][0];

            if(cmd==0){
                union(query[i][1]);
            }
            else{

                if(find(query[i][1]) == find(query[i][2])){
                    answer[idx--] = "YES";
                }
                else{
                    answer[idx--] = "NO";
                }

            }

        }

        for(String a : answer){
            sb.append(a).append("\n");
        }

        System.out.print(sb.toString());
    }

    static void union(int x){

        int nx = find(x);

        parent[nx] = find(edge[x]);

    }

    static int find(int x){
        if(parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    static void init(){

        edge = new int[N+1];
        parent = new int[N+1];
        for(int i = 0; i < N+1; i++) {
            parent[i] = i;
        }

    }

}
/*

13306 트리

유니온 파인드 + 오프라인 쿼리 문제

해당 문제의 풀이 로직은 다음과 같습니다.

1. 입력으로 들어오는 쿼리를 모두 기억합니다.
2. 입력이 끝나면 쿼리를 거꾸로 수행합니다. (엣지 삭제면 엣지를 연결해줌)
3. 출력도 따로 모아서 거꾸로 출력시킵니다.

해당 문제는 오프라인 쿼리를 배울 수 있는 전형적인 문제입니다.
*오프라인 쿼리란? 쿼리를 실시간으로 처리하는 것이 아닌 쿼리가 끝난 후에 따로 처리하는 것을 뜻합니다.

해당 문제를 만약 직접 구현하게 된다면? 트리를 구현과 O(N) 엣지를 끊는 작업은 O(1)이라서 빠르게 처리가능하지만..
서로 연결되어있는지 확인하는 작업은 희소 배열을 쓰지 않고 공통 조상 찾기를 해야하기 때문에 각 쿼리마다 O(N)이 걸릴 것 입니다.
즉, N이 200,000, Q가 200,000이기 때문에 1개의 쿼리마다 N을 반복하니 시간은 O(N^2)이 될 것입니다.

이제 문제를 조건을 이렇게 바꿔보면 어떨까요?
최초에 서로 전혀 연결이 되지 않은 노드들이 있는데, 이 노드들울 연결하는 작업을 수행하면서 간간히 x노드와 y노드가 서로 이어져있는지 확인하라.
이러한 유형의 문제는 유니온 파인드를 활용하면 쉽게 구현할 수 있습니다.

이 문제도 읽어보면 마찬가지 입니다.
이 문제의 입력 조건을 읽어보면 "최후에는 모든 연결이 끊깁니다."
그렇다면, 쿼리를 다 받고 나서 쿼리를 거꾸로 수행하면서 끊는 작업에서 연결시켜주고 그 후 서로 연결되어있는지 확인한다면?
이렇게하면 해당 문제는 평범한 유니온 파인드 문제가 되는 것 입니다.

이처럼 해당 문제는 오프라인 쿼리를 이용한다면 쉽게 풀 수 있는 문제입니다.

*/