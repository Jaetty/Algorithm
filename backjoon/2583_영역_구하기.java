import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int M, N, K;
    static boolean[][] map;
    static int dx[] = {-1,1,0,0}, dy[]={0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new boolean[N][M];

        for(int i=0; i<K; i++){

            st = new StringTokenizer(br.readLine());

            int sr = Integer.parseInt(st.nextToken());
            int sc = Integer.parseInt(st.nextToken());
            int er = Integer.parseInt(st.nextToken());
            int ec = Integer.parseInt(st.nextToken());

            for(int row=sr; row<er; row++){
                for(int col=sc; col<ec; col++){
                    map[row][col] = true;
                }
            }
        }

        List<Integer> list = new ArrayList<>();

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(!map[i][j]){
                    map[i][j] = true;
                    int count = bfs(i,j);
                    list.add(count);
                }
            }
        }

        Collections.sort(list);

        StringBuilder sb = new StringBuilder();
        sb.append(list.size()).append("\n");

        for(int count : list){
            sb.append(count).append(" ");
        }

        System.out.print(sb.toString());

    }

    static int bfs(int r, int c){

        int count = 1;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r, c});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr < 0 || nc <0 || nr >=N || nc >= M || map[nr][nc]) continue;

                map[nr][nc] = true;
                count++;

                queue.add(new int[]{nr, nc});
            }

        }

        return count;
    }
}

/*
* 그래프 이론 문제
*
* 이 문제의 핵심은 그래프 탐색을 통해 몇 번 탐색했는지와 탐색 했을 때 몇 개의 노드를 들렸는지를 오름차순으로 보여주는 것 입니다.
*
* 해당 문제는 BFS, DFS로 풀이가 가능한 그래프 이론 문제로 포인트는 다음과 같습니다.
* 1. 모눈 종이를 시계방향으로 한번 돌렸다고 생각해봅시다.
* 2. K만큼 입력받는 범위를 이미 들린 곳으로 표시하여 그래프 탐색이 이루어지지 않도록 만들어줍니다.
*
*
* 1번 포인트의 경우 왼쪽 아래의 꼭지점을 (0,0)으로 보기 때문에 처음 이런 문제를 접했을 때 당황할 수도 있지만
* 조금만 생각해보면 시계방향으로 한바퀴 돌리면 그게 곧 row와 col이 됨을 알 수 있습니다.
* 즉 arr[][] = new int[M][N] 크기로 만들어주시면 됩니다.
*
* 2번 포인트는 이후 K 만큼 입력되는 범위를 탐색하지 않도록 이미 탐색이 되었다는 표시를 해줍니다.
*
* 이후 그래프를 처음부터 탐색하면서 탐색이 되지 않은 노드를 찾게되면 거기서 그래프 탐색(DFS, BFS)을 통해 넓이를 기억해줍니다.
* 모든 탐색이 끝나면 그래프 탐색이 이루어진 횟수와 정렬을 통해 오름차순이 된 넓이를 출력하면 풀이할 수 있습니다.
*
*
* */