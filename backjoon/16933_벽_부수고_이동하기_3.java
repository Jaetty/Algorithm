import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, K;
    static char[][] map;
    static Integer[][][] visited;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        visited = new Integer[K+1][N][M];

        for (int i = 0; i < N; i++) {

            String line = br.readLine();

            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j);
            }

        }

        for(int i = 0; i <= K; i++) {
            visited[i][0][0] = 1;
        }

        bfs();

        int answer = Integer.MAX_VALUE;
        for(int i=K; i>=0; i--) {

            if(visited[i][N-1][M-1]==null) {
                continue;
            }
            answer = Math.min(answer, visited[i][N-1][M-1]);

        }

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);

    }

    static boolean check(int nr, int nc){

        if(nr < 0 || nc < 0 || nr >= N || nc >= M) {
            return true;
        }
        return false;
    }

    static void bfs(){

        Queue<int[]> queue = new ArrayDeque<>();

        // row, col, 이동횟수, 벽 부순 횟수, 낮(짝수)과 밤(홀수)을 뜻함
        queue.add(new int[] {0,0,1,0,0});

        while(!queue.isEmpty()){

            int[] cur = queue.poll();

            for(int i = 0; i < 4; i++){

                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if(check(nr, nc)){
                    continue;
                }

                // 만약 다음 위치가 벽이고 벽부수기 횟수가 남았다면
                if(map[nr][nc] == '1' && cur[3] < K ){

                    // 벽을 만났을 때, 낮인지 밤인지로 걸리는 시간 계산
                    int cost = cur[4] % 2 == 0 ? cur[2]+1 : cur[2]+2;

                    // 벽 부수고 난 후의 위치에 가장 최소로 도달했다면
                    if(visited[cur[3]+1][nr][nc] == null || visited[cur[3]+1][nr][nc] > cost){

                        visited[cur[3]+1][nr][nc] = cost;
                        // 벽부수고 다음은 무조건 밤.
                        queue.add(new int[] {nr, nc, cost, cur[3]+1, 1});

                    }
                }
                // 다음 위치가 벽이 아니라면
                else if(map[nr][nc] == '0'){

                    // 단순 비교해서 queue 추가
                    if(visited[cur[3]][nr][nc] == null || visited[cur[3]][nr][nc] > cur[2]+1){
                        visited[cur[3]][nr][nc] = cur[2]+1;
                        queue.add(new int[]{nr, nc, cur[2]+1, cur[3], cur[4]+1});
                    }

                }
            }
        }
    }

}

/*

16933 벽 부수고 이동하기 3

그래프 이론 문제

해당 문제는 벽 부수고 이동하기 2를 풀이했다고 가정하고 설명하겠습니다.
혹시, 벽 부수고 이동하기 2를 풀이하지 못했다면, 먼저 풀어보길 권장하며 벽 부수고 이동하기 2의 해설도 같은 레포지토리에서 확인 가능합니다.

문제의 핵심은 결국 벽 부수고 이동하기 2에서 낮과 밤의 개념을 추가하는 것 입니다.
문제만 보면 어떻게 머물게 만들지? 고민할 수도 있는데 굳이 머물겠다는 판단을 할 필요가 없습니다. 이는 매우 간단히 해결 가능합니다.
벽을 만났을 때, 낮이면 걸린 시간 +1, 밤이면 걸린 시간 +2 를 해주면 됩니다.

즉, 벽 부수고 이동하기 2 풀이에서 낮과 밤을 표현하는 변수를 추가하여 이 값에 따라 시간에 +2를 할지 +1을 할지 정하면 해결할 수 있습니다.

그나마 주의할 점은 다음과 같이 출발하자마자 도착할 수 있는 경우가 있습니다.
1 1 0
0

이때 정답은 1이 나와야합니다. 그러므로 첫 초기화 때 처음 위치의 값은 1로 설정해줘야합니다.

*/