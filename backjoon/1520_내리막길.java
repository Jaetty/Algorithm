import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][] map;
    static Integer[][] dp;
    static int N, M;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        // 최초에 모든 값은 Null로 초기화하여 아예 방문하지 않았음을 표시함.
        dp = new Integer[N][M];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(dfs(0,0));

    }

    static int dfs(int r, int c){

        // N,M위치에 도달하면 1을 리턴
        if(r==N-1 && c==M-1) return 1;

        // 만약 방문한 적이 있다면 도달 가능한 경우의 수 리턴
        if(dp[r][c] != null){
            return dp[r][c];
        }

        // 최초로 방문했을 때를 표시하면서, N,M에 도달한 적 없을 것이기 때문에 0으로 값을 변경
        dp[r][c] = 0;

        for(int i=0; i<4; i++){
            int nr = r + dx[i];
            int nc = c + dy[i];

            if(nr <0 || nc < 0 || nr >= N || nc >= M || map[nr][nc] >= map[r][c]) continue;

            // 경로를 탐색하며 경우의 수를 더해준다.
            dp[r][c] += dfs(nr, nc);

        }

        return dp[r][c];

    }

}

/*
* 그래프 이론 + 다이나믹 프로그래밍 문제
*
* !! 해당 문제는 직접 풀이하는데 실패하여 해설을 참조하였습니다 !!
*
* 해당 문제를 봤을 때 머릿속에 떠오른 방법은 DFS로 찾아가면서 (1,1) 위치에서 (N,M) 위치로 도달 가능한 경로에 도착하면 더이상 탐색하지 않고 count값만 올리자는 로직을 세웠습니다.
* 그래서 제가 짯던 풀이 로직 다음과 같았습니다.
* 1. boolean[][]을 만들었습니다. 이 배열은 (1,1)에서 출발하여 (N,M)위치로 갈 수 있는 경로일 경우 true값으로 바꿔줍니다. 최초엔 모두 false입니다.
* 2. DFS로 1,1 위치에서 N,M 위치까지 탐색합니다. 탐색 조건은 boolean[][]이 true인지, 다음 노드가 나보다 값이 작은지 확인합니다.
* 3. 탐색을 통해 N,M 위치까지 도달하면 count값을 하나 늘리고 이때까지의 경로를 저장한 List의에서 값을 꺼내 지나온 경로를 true로 만들어줍니다.
* 4. 다음 탐색부터는 이 boolean[][] 값이 true면 더이상 탐색하지 않고 지나온 경로를 저장해주고 정답 count를 하나 늘려줍니다.
*
* 제출 후 결과는 틀렸습니다 로 나왔습니다. 생각해보니 해당 풀이는 여러 개의 경우의 수를 더하지 못한다는 문제점이 있었습니다.
* 그래서 DP 문제라고 생각이 되었고 경우의 수를 어떤 형태의 메모이제이션을 통해 해결할 수 있을지 고민하였으나... 결국 풀이를 참고하게 되었습니다. 풀이는 다음과 같습니다.
*
* 먼저 2차원 배열 dp를 만들어줍니다.
* 이 배열은 탐색한 적이 있는지, 또 했다면 이 위치에 도달하면 얼마만큼의 경우의 수로 N,M에 도착할 수 있는지를 기억하는 배열입니다.
* 탐색 중 최초로 도착한 위치에 도달하면 그 위치의 배열 값을 0으로 초기화합니다. 그러므로 dp의 초기값은 음수 값이나, null값 등으로 초기화 되어 있어야합니다.
*
* 1,1 부터 시작하여 어떠한 경로로 N,M에 도착하면 이 경로들에 +1씩 하게 해줍니다.
* 그렇게 하면 다시 또다른 탐색으로 N,M으로 도달하면 관련된 모든 경로에 +1이 됩니다.
* 예를 들어 (2,3)위치에서 (3,3)로 도달했을 때 (3,3) dfs와 dp를 통해 (3,3)위치가 7개의 다른 경로로 N,M에 도달할 수 있다는 것을 알게 되었다면
* (3,3) 값에 7이 들어가있을 것입니다. 그러면 나중에 (3,2)에서 (3,3)으로 도달한다면 (3,2) = 7 + (3,2)에서 N,M도달 가능한 경우의 수를 가지게 될 것입니다.
*
* 검색 결과를 바탕으로 코드로 짠 부분이 dfs 메소드 부분입니다.
*
*
* */