import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int[][] map;
    static Queue<int[]> queue;
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};

    static int N, M, count;

    static final int INF = 99999999;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        queue = new ArrayDeque<>();

        map = new int[N][M];
        count = 0;

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j]==1) count++;
            }
        }

        melting();

    }

    static void melting(){

        int answer = 0;

        while(count>0){

            queue.add(new int[] {0,0});
            int[][] visited = new int[N][M];
            visited[0][0] = INF;
            Queue<int[]> erase = new ArrayDeque<>();

            while(!queue.isEmpty()){

                int[] rc = queue.poll();

                for (int i=0; i<4; i++){
                    int nr = rc[0] + dr[i];
                    int nc = rc[1] + dc[i];

                    if(nr<0||nc<0||nr>=N||nc>=M||visited[nr][nc]==INF) continue;

                    if(map[nr][nc]==1){
                        visited[nr][nc]++;
                        if(visited[nr][nc]==2) erase.add(new int[]{nr,nc});
                    }
                    else{
                        visited[nr][nc] = INF;
                        queue.add(new int[]{nr,nc});
                    }
                }

            }

            while(!erase.isEmpty()){
                int[] rc = erase.poll();
                map[rc[0]][rc[1]] = 0;
                count--;
            }

            answer++;
        }
        System.out.println(answer);
    }


}
/*
* bfs를 사용하는 알고리즘 문제이다.
* 문제에서 주목해야하는 포인트는 다음과 같다.
* 1. N과 M 크기의 배열이 주어지고 그 안에서 각 가장자리에는 치즈가 놓이지 못한다.
* 2. 치즈가 외부와 2개 이상의 변이 마주쳐야 녹는다.
* 3. 치즈가 둘러싸고있는 부분에는 공기가 접촉하지 않은 것이다.
*
* 1번의 경우 r을 행 c를 열이라고 하면 치즈의 위치는 0<r<N-1, 0<c<M-1 에 있다는 뜻이다.
* 2번과 3번의 경우는 외부에 2개이상 체크하되 치즈 내부의 공기는 고려하지 말아야한다는 뜻이다.
*
* 그렇다면 이를 조합해서 생각해보면...
* 3번 포인트의 치즈 내부의 공기를 고려하지 않는 방법은 치즈 외부에서부터 bfs 탐색을 시작하여
* 치즈 외부에 막히면 더이상 그 방향으로 진행을 하지 않도록 만들면 된다.
* 2번의 경우 탐색을 할 때 치즈에 막혔다면 그 치즈가 있던 위치에 카운트를 만들어서 2번 카운팅이 되었다면 치즈의 변이 외부에 2개 닿았다는 뜻이다.
* 그리고 반드시 외부에서 탐색을 시작하는 방법은
* 1번 포인트에 의해 가장자리가 무조건 0이기 때문에 가장자리의 아무 위치에서 탐색이 시작되면 된다.
*
* 이것을 bfs에 적용하여 구현하면 쉽게 해결된다.
*
* */