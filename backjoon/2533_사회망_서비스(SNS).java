import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][] dp;
    static List<List<Integer>> edgeList;
    static boolean visited[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        edgeList = new ArrayList<>();

        for (int i=0; i<=N; i++){
            edgeList.add(new ArrayList<>());
        }

        for(int i=0; i<N-1; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edgeList.get(a).add(b);
            edgeList.get(b).add(a);
        }

        dp = new int[N+1][2];
        visited = new boolean[N+1];

        recursive(1);

        System.out.println(Math.min(dp[1][0], dp[1][1]));

    }

    static void recursive(int node){
        visited[node] = true;
        dp[node][0] = 1;

        for(int nextNode : edgeList.get(node)){
            if(visited[nextNode]) continue;
            recursive(nextNode);
            dp[node][1] += dp[nextNode][0];
            dp[node][0] += Math.min(dp[nextNode][0], dp[nextNode][1]);
        }
    }

}

/*
 * 트리 + 다이나믹 프로그래밍 문제
 *
 * @@ 해당 문제는 풀이를 실패하여 다른 사람의 풀이를 참고하였습니다. @@
 *
 * 처음에 N의 크기를 보고 브루트포스로는 풀이가 힘들다는 걸 느끼고 문제를 bottom-up 방식의 그리디로 풀이했지만 통과하지 못했습니다.
 * 결국 다른 사람의 풀이를 참고하였고 알게 된 문제의 핵심은
 * 해당 노드가 얼리 어답터일때 아닐때의 경우의 수를 메모이제이션하여 활용하는 것 이었습니다.
 *
 * dp[node][0~1] 배열은 각각
 * 해당 node가 얼리 어답터 인 경우(dp[node][0])에 현재 노드를 루트로 하는 자식 노드들을 포함하여 얼리 어답터가 몇 명인지 기억하는 배열이고
 * 해당 node가 얼리 어답터가 아닌 경우 (dp[node][1]) 현재 노드를 루트로 하는 자식 노드들을 포함하여 얼리 어답터가 몇 명인지 기억하는 배열입니다.
 *
 * 그래서 해당 node가 얼리 어답터라면 자식 노드들의 경우 얼리 어답터 이거나 아니거나 둘 중 하나이기 때문에
 * dp[node][0] += Math.min(dp[nextNode][0], dp[nextNode][1]); 를 수행해주고
 *
 * 해당 node가 얼리 어답터가 아니라면 반드시 나와 관련된(root와 자식 포함하여) 모든 node들이 얼리 어답터 이기 때문에
 * dp[node][1] += dp[nextNode][0]; 를 수행해주는 방법으로
 *
 * 맨 위의 root인 1부터 시작하여 재귀적으로 top-down 방식으로 풀이가 가능한 문제였습니다.
 *
 *
 * */