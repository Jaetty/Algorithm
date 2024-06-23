import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static char[][] map;
    static int[][] visited;
    static List<int[]> door;
    static int[] dx = {1,0,-1,0};
    static int[] dy = {0,1,0,-1};
    static final int MAX = 100000000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        visited = new int[N][N];
        map = new char[N][N];
        door = new ArrayList<>();

        for(int i=0; i<N; i++){
            String var = br.readLine();

            for(int j=0; j<N; j++){
                map[i][j] = var.charAt(j);
                visited[i][j] = MAX;

                if(map[i][j]=='#'){
                    door.add(new int[] {i,j});
                }
            }
        }

        bfs(door.get(0));

        int[] end = door.get(1);

        System.out.println(visited[end[0]][end[1]]);


    }

    static void bfs(int[] start){

        Queue<int[]> queue = new ArrayDeque<>();
        visited[start[0]][start[1]] = 0;

        for(int i=0; i<4; i++){

            int nr = start[0] + dx[i];
            int nc = start[1] + dy[i];

            if(nr <0 || nc <0 || nr>=N || nc>=N){
                continue;
            }
            if(map[nr][nc] != '*'){
                queue.add(new int[] {start[0], start[1], 0, i});
            }

        }

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            int nr = rc[0] + dx[rc[3]];
            int nc = rc[1] + dy[rc[3]];

            while(nr >= 0 && nc >= 0 && nr < N && nc < N && visited[nr][nc] > rc[2] && map[nr][nc]!='*'){

                if(map[nr][nc]=='#'){
                    visited[nr][nc] = rc[2];
                }
                else if(map[nr][nc] == '!' && visited[nr][nc] > rc[2]+1){
                    visited[nr][nc] = rc[2]+1;
                    queue.add(new int[] {nr, nc, visited[nr][nc], (rc[3]+1)%4 });
                    queue.add(new int[] {nr, nc, visited[nr][nc], (rc[3]+3)%4 });
                }

                nr += dx[rc[3]];
                nc += dy[rc[3]];

            }
        }


    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 반드시 문이 서로 거울로 마주할 수 있는 경우만 주어진다.
 * 2. 거울은 45도 각도로만 둘 수 있다.
 * 3. ! 위치에만 거울을 둘 수 있다.
 *
 * 2번 포인트로 알 수 있는 점은 BFS를 실행하기 전에 먼저 전에 어떤 방향이었는지를 알고 그 후 그 방향에서 45도 돌린 방향으로 움직여야 한다는 점입니다.
 * 문에서부터 아래나 위의 방향으로 탐색을 하다가 거울을 만나게 되면 좌, 우 방향으로만 가야하기 때문에 전 방향이 수직방향이었는지 수평 방향이었는지 기억해야합니다.
 *
 * 3번 포인트로 알 수 있는 점은 !를 만날 때만 그 위치에서 방향을 틀어서 BFS를 수행해주면 된다는 점을 알 수 있습니다.
 * 또한 계속해서 반복하지 못하도록 가장 최소의 거울 수로 방문했던 횟수를 기억하게 하므로 그래프 순환을 막을 수 있습니다.
 *
 * 위의 로직을 고려하여 코드로 구현하면 해당 문제를 풀이할 수 있습니다.
 *
 * */