import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int H, W, answer;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static char[][] map;
    static int[][] point;
    static int[][][] visited;
    static final int MAX = 10000000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        point = new int[2][2];
        map = new char[H][W];
        visited = new int[4][H][W];
        int c_count = 0;

        for(int i=0; i<H; i++){
            String temp = br.readLine();
            for (int j=0; j<W; j++){

                for(int k=0; k<4; k++) visited[k][i][j] = MAX;

                map[i][j] = temp.charAt(j);
                if(map[i][j]=='C'){
                    point[c_count++] = new int[] {i,j};
                }
            }
        }

        answer = MAX;
        bfs(point[0][0], point[0][1], point[1][0], point[1][1]);

        System.out.println(answer);

    }

    static void bfs(int start_x, int start_y, int end_x, int end_y){

        // 각각 저장하는 정보는 현재 위치(x,y), 원래 방향, 거울 개수
        Queue<int[]> queue = new ArrayDeque<>();

        for (int i=0; i<4; i++){

            visited[i][start_x][start_y] = 0;
            int nr = start_x + dx[i];
            int nc = start_y + dy[i];

            if(nr < 0 || nr >= H || nc < 0 || nc >= W || map[nr][nc] == '*' ) continue;

            queue.add(new int[] {nr, nc, i, 0});
            visited[i][nr][nc] = 0;
        }

        while (!queue.isEmpty()){

            int[] rc = queue.poll();

            for (int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr < 0 || nr >= H || nc < 0 || nc >= W ||  visited[i][nr][nc] <= rc[3] || map[nr][nc] == '*') continue;

                if(rc[2] != i){
                    visited[i][nr][nc] = rc[3]+1;
                }
                else{
                    visited[i][nr][nc] = rc[3];
                }
                if( nr==end_x && nc == end_y){
                    answer = Math.min(answer, visited[i][nr][nc]);
                    break;
                }
                queue.add(new int[] {nr, nc, i, visited[i][nr][nc]});
            }
        }
    }
}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 포인트는 어느 한 C의 위치에서 다른 C의 위치에 가장 거울을 적게 사용하여 도착하는 문제입니다.
 * 저는 BFS로 문제를 풀이했지만 문제를 최소의 노드 경유를 통해 도착지에 도착하게 만드는 방법으로 접근한다면 다익스트라로도 풀이가 가능할 것 같습니다.
 *
 * 이 문제는 쉽게 BFS를 실행시켜주되 방문 여부를 boolean이 아닌 int형으로 만들어서 거울을 최소한만 쓴 것을 기준으로 탐색을 진행하면 풀이가 가능합니다.
 * 다만 유념할 점은 똑같은 노드를 서로 다른 방향의 레이저가 지날 수 있습니다.
 * 그러므로 3차원 배열을 통해 방향을 고려한 visited 배열을 만들면 풀이가 가능합니다.
 *
 * */