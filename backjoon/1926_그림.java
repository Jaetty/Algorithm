import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, total, biggest_size;
    static char[][] map;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];

        for(int i=0; i<N; i++){

            st = new StringTokenizer(br.readLine());

            for(int j=0; j<M; j++){
                map[i][j] = st.nextToken().charAt(0);
            }

        }

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(map[i][j]=='1'){
                    bfs(i,j);
                }
            }
        }

        System.out.println(total);
        System.out.println(biggest_size);


    }

    static void bfs(int r, int c){

        int count = 1;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r,c});

        map[r][c] = '0';

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr <0 || nc <0 || nr >= N || nc >= M || map[nr][nc] == '0'){
                    continue;
                }

                map[nr][nc] = '0';
                count++;
                queue.add(new int[]{nr, nc});

            }

        }

        biggest_size = Math.max(count, biggest_size);
        total++;

    }

}

/*
1926 그림

그래프 이론 문제

문제는 단순 그래프 이론 문제입니다.

BFS를 이용하여, 1이 연결된 조각이 얼마나 큰지와 몇개나 연결 점이 있는지 확인하면 문제를 풀이할 수 있습니다.


  */