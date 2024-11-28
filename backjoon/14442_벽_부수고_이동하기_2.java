import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, K, answer;
    static boolean map[][];
    static boolean visited[][][];
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken())+1;
        answer = Integer.MAX_VALUE;

        visited = new boolean[N][M][K];
        map = new boolean[N][M];

        for(int i=0; i<N; i++){
            String input = br.readLine();
            for(int j=0; j<M; j++){
                if(input.charAt(j)=='0'){
                    map[i][j] = true;
                }
            }
        }

        bfs();

        System.out.print(answer == Integer.MAX_VALUE ? -1 : answer);
    }

    static void bfs(){

        Queue<int[]> queue = new ArrayDeque<>();
        visited[0][0][0] = true;
        queue.add(new int[]{0,0,0,1});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            if(rc[0]==N-1 && rc[1]==M-1){
                answer = rc[3];
                return;
            }

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(check(nr, nc, rc[2])){
                    continue;
                }

                if(map[nr][nc]){
                    visited[nr][nc][rc[2]] = true;
                    queue.add(new int[]{nr,nc,rc[2], rc[3]+1});
                }
                else{
                    // 벽이랑 만났을 때 벽을 뚫을 수 있는지, 중복 방문인지 확인.
                    if(rc[2]+1<K && !visited[nr][nc][rc[2]+1]){
                        visited[nr][nc][rc[2]+1] = true;
                        queue.add(new int[]{nr,nc,rc[2]+1, rc[3]+1});
                    }
                }

            }
        }
    }

    static boolean check(int nr, int nc, int k){
        if(nr < 0 || nc < 0 || nr >=N || nc>=M || visited[nr][nc][k]){
            return true;
        }
        return false;
    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 시작점은 (1,1), 목적지는 (N,M)
 * 2. 최대 벽을 K번 뚫고 갈 수 있다.
 *
 * 가장 큰 포인트는 벽을 뚫고 갈 수 있는 것입니다. 이게 없다면 단순 BFS문제와 같기 때문입니다.
 * 그럼 고민점은 벽을 뚫은걸 어떻게 구현하면 될까 부분인데 이 부분은 visited를 3차원 배열로 만들어 해결할 수 있습니다.
 * visited[N][M][K]로 만들고 [K]부분은 k번 벽을 뚫었을 때 방문 처리입니다.
 *
 * 즉, 조건으로 벽이랑 만났을 때 벽을 뚫을 수 있는지, 중복 방문인지 확인하는 조건만 추가하면 크게 BFS 알고리즘에서 벗어나지 않아 쉽게 풀이가 가능합니다.
 *
 *
 * */