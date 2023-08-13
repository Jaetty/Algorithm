import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Main{

    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {1,-1,0,0};
    static int[] dc = {0,0,-1,1};
    static int N, M, answer;

    public static void main(String[] args)throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        answer = 0;

        map = new int[N][M];

        for(int i=0; i<N; i++){
            String str = br.readLine();
            for(int j=0; j<M; j++) map[i][j] = str.charAt(j);
        }


        for(int i=0; i<N; i++){

            for(int j=0; j<M; j++){
                if(map[i][j]=='L'){
                    visited = new boolean[N][M];
                    bfs(i,j);
                }
            }
        }

        System.out.println(answer);

    }

    static void bfs(int x, int y){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{x,y,0});
        visited[x][y] = true;

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){
                int nr = rc[0]+dr[i];
                int nc = rc[1]+dc[i];

                if(nr<0 || nc<0 || nr>=N || nc>=M || visited[nr][nc] || map[nr][nc]=='W') continue;

                visited[nr][nc] = true;
                answer = Math.max(answer, rc[2]+1);
                queue.add(new int[]{nr,nc, rc[2]+1});
            }

        }


    }

}

/*
 * 브루트포스 + 그래프 이론 문제
 *
 * 해당 문제의 포인트는 육지를 나타내는 'L'로 이어진 구간에서 가장 끝에서 끝 거리를 나타내는 것 입니다.
 * 브루트포스로 각 'L' 지점마다 BFS를 통하여 거리를 구해주면 해결할 수 있는 문제입니다.
 *
 */