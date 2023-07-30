import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, K;
    static int[][] group, dp;
    static List<List<Integer>> path;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Math.min(Integer.parseInt(st.nextToken()),3000); // 배낭 문제의 무게에 해당

        group = new int[N+1][2]; // [N][0] = 친구 수 [N][1] = 사탕 수
        dp = new int[N+1][K];

        path = new ArrayList<>();
        path.add(new ArrayList<>());

        for(int i=0; i<N; i++){
            path.add(new ArrayList<>());
        }

        st = new StringTokenizer(br.readLine());

        for(int i=1; i<=N; i++){
            group[i][0] = 1;
            group[i][1] = Integer.parseInt(st.nextToken());;
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            path.get(a).add(b);
            path.get(b).add(a);
        }

        for(int i=1; i<=N; i++){
            if(group[i][0] < 3000 && !path.get(i).isEmpty()){
                grouping(i);
            }
        }

        for(int i=1; i<=N; i++){
            for(int j=1; j<K; j++){
                if(j>=group[i][0]){
                    dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-group[i][0]] + group[i][1] );
                }
                else dp[i][j] = dp[i-1][j];
            }
        }

        System.out.println(dp[N][K-1]);

    }

    static void grouping(int index){

        boolean[] grouped = new boolean[N+1];
        Queue<Integer> queue = new ArrayDeque<>();

        grouped[index] = true;
        queue.add(index);

        while(!queue.isEmpty()){

            int temp = queue.poll();

            for (int val : path.get(temp)){
                if(grouped[val]) continue;
                grouped[val] = true;
                queue.add(val);
                group[index][1] += group[val][1];
                group[index][0]++;
                group[val][0] = 3001;
            }
        }
    }

}

/*
 * 그래프 이론 + 다이나믹 프로그래밍 문제
 *
 * 이 문제는 그래프 이론과 다이나믹 프로그래밍을 같이 이용해야하는 문제입니다.
 * 문제를 풀기 위한 프로세스는 크게 두 가지 입니다.
 * 1. 친구(노드)들이 서로 연결된 것 끼리는 하나의 그룹으로 만들어준다.
 * 2. 그룹에 대해서 배낭문제를 이용하여 가장 사탕(가치)이 많은 값을 적어준다.
 *
 * 1번 설명
 * 노드가 서로 연결된 것 끼리 하나의 그룹으로 만들어주면 됩니다.
 * 가장 대표적으로 이러한 문제를 해결하는 방법은 유니온 파인드를 이용하는 방법이 있습니다.
 * 저의 경우는 배낭문제가 아직 익숙하지 않아서 메모리와 시간을 차지하더라도 2차원 배열 형태로 그룹화 하였습니다.
 *
 * group이라는 2차원 배열을 이용하여 [node][0] = "그 노드와 연결된 모든 노드 수의 합" [node][1] = "그 노드와 연결된 모든 노드가 가진 가치 합"을 나타냈습니다.
 * 그리고 연결된 모드 노드를 찾는 과정에서 다른 노드들은 이미 들렸다는 것을 나타내기 위해서 [node][0] = K의 최대값인 3000+1을 두어 배낭문제 풀이 과정에서 제외되도록 하였습니다.
 * 이렇게 하여 만약 입력이 다음과 같다면
 * 5 2 4
 * 1 2 3 4 5
 * 1 2
 * 2 5
 *
 * group[1][0] = 3 group[1][1] = 8
 * group[2][0] = 3001
 * group[3][0] = 1 group[3][1] = 3
 * group[4][0] = 1 group[4][1] = 4
 * group[5][0] = 3001
 *
 * 위와 같이 group배열이 완성됩니다.
 *
 * 2번 설명
 * 앞의 과정을 통해서 그룹화를 완료하였고 배낭문제를 통하여 문제를 해결하면 됩니다.
 * 여기서 구한 group 배열은 다음과 같은 의미입니다.
 *
 * group[node][0] = 배낭문제의 무게
 * group[node][1] = 배낭문제의 가치
 *
 * 배낭문제에서 필요한 무게와 가치를 구했기 때문에 배낭문제에ㅔ서 group 배열을 이용하면 됩니다.
 * 무게는 K가 되면 안되기 때문에 무게의 최대값을 K-1 만큼으로 하여 배낭문제를 풀이하면 해당 문제를 풀 수 있습니다.
 *
 *
 */