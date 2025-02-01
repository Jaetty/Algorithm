import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static char[][] map;
    static int[][] dp;
    static int N, M, answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        dp = new int[N][M];

        for(int i=0; i<N; i++){

            map[i] = br.readLine().toCharArray();

        }

        dfs(1,0, 0, new boolean[N][M]);

        System.out.println(answer == -1 ? -1 : answer);

    }

    static void dfs(int depth, int r, int c, boolean[][] visited){

        if(answer==-1){
            return;
        }

        answer = Math.max(answer, depth);


        if(!visited[r][c]){
            visited[r][c] = true;
        }
        else{
            answer = -1;
            return;
        }

        int pr = r + (map[r][c]-'0');
        int pc = c + (map[r][c]-'0');
        int mr = r - (map[r][c]-'0');
        int mc = c - (map[r][c]-'0');

        if(pr < N && answer >= 0 && map[pr][c] != 'H' && dp[pr][c] < depth+1){
            dp[pr][c] = depth+1;
            dfs(depth+1, pr, c, visited);
        }

        if(pc < M && answer >= 0 && map[r][pc] != 'H' && dp[r][pc] < depth+1){
            dp[r][pc] = depth+1;
            dfs(depth+1, r, pc, visited);
        }

        if(mr >= 0 && answer >= 0 && map[mr][c] != 'H' && dp[mr][c] < depth+1){
            dp[mr][c] = depth+1;
            dfs(depth+1, mr, c, visited);
        }

        if(mc >= 0 && answer >= 0 && map[r][mc] != 'H' && dp[r][mc] < depth+1){
            dp[r][mc] = depth+1;
            dfs(depth+1, r, mc, visited);
        }

        visited[r][c] = false;

    }

}

/*
 * 1103 게임
 *
 * 그래프 이론 + 다이나믹 프로그래밍 문제
 *
 * 문제의 경우 읽었을 때 금방 그래프 이론 문제로 풀이가 가능하다고 생각됩니다.
 * 그러나 조금 생각해보면 2가지 문제점이 떠오를 것 입니다.
 * 1. 어떻게 무한 루프를 파악하는가?
 * 2. 어떻게 반복적으로 특정 위치에 들렸을 때, 그 중 최선의 정답을 낼 수 있는가?
 *
 * 1번의 경우는 어떤 방향으로 출발하던 한번 들렸던 곳에 다시 방문하게 되는 순간 무한 루프를 돈다는 점을 알 수 있습니다.
 * 그러면 visited를 만들어서 확인만 해주면 금방 파악할 수 있습니다.
 *
 * 2번의 경우는 예를 들어 (x,y)위치에 3번만에 도착한 경우가 있고, 6번만에 도착한 경우가 있다고 하겠습니다.
 * 만약 무한 루프를 돌지 않는다고 한다면 결국, (x,y)에 도착하면 이후 어떻게 움직일지는 정해져있습니다.
 * 그렇다면 3번만에 (x,y)에 도착한것과 6번만에 도착한 것 중 더 많이 움직일 수 있는 경우는 당연히 6번입니다.
 *
 * 이점에 주목해서 생각해보면 특정 (x,y)에 지점에 만약 더 높은 이동 횟수를 가진 채 도달했다면 이전의 결과를 덮어씌우면 되겠구나를 알 수 있습니다.
 * 그러므로 dp[x][y]배열을 만들어서 얼마만에 도달했는지 기룍해주면 가지치기를 더욱 수월히 하여 풀이가 가능합니다.
 *
 *
 * */