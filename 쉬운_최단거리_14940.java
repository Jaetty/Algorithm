import java.util.*;
import java.io.*;

public class Main {

    static Integer[][] visited;

    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int sx=0, sy=0;

        visited = new Integer[N][M];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j=0; j<M; j++){
                int tmp = Integer.parseInt(st.nextToken());

                visited[i][j] = -1;

                if(tmp==2){
                    sx = i;
                    sy = j;
                }

                if( tmp==2 || tmp==0 ){
                    visited[i][j] = 0;
                }
            }
        }

        bfs(sx, sy, N, M);

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                sb.append(visited[i][j]+" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static void bfs(int sx, int sy, int N, int M){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{sx,sy});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dr[i];
                int nc = rc[1] + dc[i];

                if(nr < 0 || nc <0 || nr >= N || nc >= M || visited[nr][nc] >= 0) continue;

                visited[nr][nc] = visited[rc[0]][rc[1]] + 1;
                queue.add(new int[] {nr, nc});

            }

        }


    }


}