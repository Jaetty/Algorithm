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
    static int N, answer;

    public static void main(String[] args)throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        map = new int[N][N];

        for(int i=0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) map[i][j] = Integer.parseInt(st.nextToken());
        }

        for(int k=0; k<=100; k++){
            visited = new boolean[N][N];
            int count = 0;

            for(int i=0; i<N; i++){
                for(int j=0; j<N; j++){
                    if(map[i][j]>k && !visited[i][j]){
                        bfs(i,j,k);
                        count++;
                    }
                }
            }

            answer = Math.max(answer, count);
        }

        System.out.println(answer);

    }

    static void bfs(int x, int y, int k){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{x,y});
        visited[x][y] = true;

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){
                int nr = rc[0]+dr[i];
                int nc = rc[1]+dc[i];

                if(nr<0 || nc<0 || nr>=N || nc>=N || visited[nr][nc] || map[nr][nc]<=k) continue;

                visited[nr][nc] = true;
                queue.add(new int[]{nr,nc});
            }

        }


    }

}

/*
 * 브루트포스 + 그래프 이론 문제
 *
 * 해당 문제의 포인트는 가장 많이 침수되지 않는 지점의 개수를 구하는 문제입니다.
 * 즉 비가 0~100까지 모든 경우를 모두 bfs를 통해 지점을 구해주면 됩니다.
 *
 * 저는 입력으로 주어지는 높이는 map[][]으로 저장해두었고 visited[][]라는 boolean 배열을 이용하여 bfs를 적용하였습니다.
 * 결국 내리는 비의 양을 k라고 하면 k보다 값이 클때 bfs를 통해 지점을 만들어주고 지점이 완성되지 않은 k보다 큰 블록을 발견하면 bfs를 또 돌리는 방법으로 문제를 해결할 수 있습니다.
 *
 */