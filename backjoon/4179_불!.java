import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int R, C;
    static Integer[][] j_visited, f_visited;
    static char[][] map;
    static int[] jihoon;
    static List<int[]> fire;
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1,0,0};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        j_visited = new Integer[R][C];
        f_visited = new Integer[R][C];

        map = new char[R][C];
        fire = new ArrayList<>();

        for(int i=0; i<R; i++){
            String val = br.readLine();
            for(int j=0; j<C; j++){

                map[i][j] = val.charAt(j);
                if(map[i][j]=='J'){
                    jihoon = new int[]{i,j};
                }
                if(map[i][j]=='F'){
                    fire.add(new int[] {i,j});
                }

            }
        }

        bfs(0, j_visited);
        bfs(1, f_visited);

        int answer = Integer.MAX_VALUE;

        for(int i=0; i<R; i++){

            if(j_visited[i][0] != null){

                if(f_visited[i][0] == null){
                    answer = Math.min(answer, j_visited[i][0]);
                }
                else if(f_visited[i][0] - j_visited[i][0] > 0){
                    answer = Math.min(answer, j_visited[i][0]);
                }
            }

            if(j_visited[i][C-1] != null){

                if(f_visited[i][C-1] == null){
                    answer = Math.min(answer, j_visited[i][C-1]);
                }
                else if(f_visited[i][C-1] - j_visited[i][C-1] > 0){
                    answer = Math.min(answer, j_visited[i][C-1]);
                }
            }

        }

        for(int i=0; i<C; i++){

            if(j_visited[0][i] != null){

                if(f_visited[0][i] == null){
                    answer = Math.min(answer, j_visited[0][i]);
                }
                else if(f_visited[0][i] - j_visited[0][i] > 0){
                    answer = Math.min(answer, j_visited[0][i]);
                }
            }

            if(j_visited[R-1][i] != null){

                if(f_visited[R-1][i] == null){
                    answer = Math.min(answer, j_visited[R-1][i]);
                }
                else if(f_visited[R-1][i] - j_visited[R-1][i] > 0){
                    answer = Math.min(answer, j_visited[R-1][i]);
                }
            }

        }

        System.out.println( answer==Integer.MAX_VALUE ? "IMPOSSIBLE" : answer );


    }

    static void bfs(int ctrl, Integer[][] visited){

        Queue<int[]> queue = new ArrayDeque<>();

        if(ctrl==0){
            queue.add( new int[] {jihoon[0], jihoon[1], 1});
            visited[jihoon[0]][jihoon[1]] = 1;
        }
        else{

            for(int[] var : fire){
                queue.add( new int[] {var[0], var[1], 1});
                visited[var[0]][var[1]] = 1;
            }

        }

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr < 0 || nc < 0 || nr >=R ||  nc >= C || map[nr][nc] == '#') continue;
                if(visited[nr][nc] != null && visited[nr][nc] <= rc[2]+1) continue;

                visited[nr][nc] = rc[2]+1;
                queue.add(new int[] {nr, nc, rc[2]+1});

            }
        }
    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 포인트는 지훈이와 불을 각각 BFS로 모든 노드에 최소로 도착 후 테두리에 위치한 노드에 도착 시간을 비교하는 것 입니다.
 * 주어진 예제의 정답이 3이기 때문에 각자 최초 시간을 1로 두고
 * J 위치에서 BFS 탐색으로 벽을 제외한 모든 노드에 최소의 이동으로 도달하는 시간을 구합니다. (j_visited[][])
 * 이후 F 위치에서 BFS 탐색으로 벽을 제외한 모든 노드에 최소의 이동으로 도달하는 시간을 구합니다. (f_visited[][])
 *
 * 이제 j_visited[][]의 테두리를 순회하면서 f_visited[][]의 값과 비교하면서 j_visited[x][y]가 f_visited[x][y]보다 작으면 지훈이가 불보다 먼저 테두리에 도착한 것입니다.
 * 그러면 그 중에서 가장 먼저 도착한 값을 기억하여 출력하면 되고, 만약 그런 값이 하나도 없다면 IMPOSSIBLE을 출력합니다.
 *
 * 위 로직대로 이 문제의 시간복잡도를 간단히 계산해보자면
 * 먼저 초기에 map을 세팅하는데 1000*1000 = 1_000_000 입니다.
 * BFS의 시간 복잡도는 최대 노드의 수(R*C) * 간선의 수(4) 이므로 => (1000*1000)*4 = 4_000_000 인데, BFS를 2번 수행하므로 4_000_000 * 2 = 8_000_000 입니다.
 * 여기에 마지막으로 테두리를 순회하므로 R+C = 1000+1000 = 2000 입니다.
 *
 * 이를 모두 더해주면 1_000_000 + 8_000_000 + 1000+1000 = 9_002_000.
 * 보통 O(1억)이 대략 1초 정도가 나오는데 Java의 시작 시간 120ms 정도를 제외하고 순수 로직 수행시간은 1번 당 0.09초 정도로 생각할 수 있습니다.
 *
 * */