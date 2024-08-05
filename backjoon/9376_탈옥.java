import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static Integer[][] sum;
    static char[][] map;
    static int N, M;
    static List<int[]> start;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        start = new ArrayList<>();

        for(int tc = 0; tc<T; tc++){

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            start = new ArrayList<>();
            start.add(new int[] {0,0});

            map = new char[N+2][M+2];
            sum = new Integer[N+2][M+2];

            for(int i=1; i<=N; i++){
                String temp = br.readLine();
                for(int j=1; j<=M; j++){
                    map[i][j] = temp.charAt(j-1);
                    if(map[i][j]=='$'){
                        start.add(new int[] {i,j});
                    }
                }
            }

            for(int i=0; i<3; i++){
                bfs(start.get(i));
            }

            sb.append(getMin()+"\n");

        }


        System.out.print(sb.toString());

    }

    static void bfs(int[] start){

        Integer[][] visited = new Integer[N+2][M+2];
        visited[start[0]][start[1]] = 0;

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {start[0], start[1], 0});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr < 0 || nc <0 || nr>=N+2 || nc>= M+2 || map[nr][nc]=='*') continue;

                int cost = rc[2];

                if(map[nr][nc]=='#') cost++;


                if(visited[nr][nc] != null && visited[nr][nc] <= cost ) continue;

                visited[nr][nc] = cost;
                queue.add(new int[] {nr,nc,cost});

            }
        }

        setSum(visited);


    }

    static void setSum(Integer[][] visited){

        for(int i=0; i<N+2; i++){
            for(int j=0; j<M+2; j++){
                if(visited[i][j]==null) continue;
                if(sum[i][j]==null) sum[i][j] = 0;
                sum[i][j] += visited[i][j];
            }
        }
    }

    static int getMin(){

        int min = Integer.MAX_VALUE;

        for(int i=0; i<N+2; i++){
            for(int j=0; j<M+2; j++){
                if(map[i][j]=='*' || sum[i][j]==null) continue;
                else if(map[i][j]=='#'){
                    min = Math.min(sum[i][j]-2, min);
                }
                else min = Math.min(sum[i][j], min);
            }
        }
        return min;
    }

}

/*
 * 그래프 이론 문제
 *
 * @@@@@ 해당 문제는 풀이에 실패하여 풀이를 참고하였습니다. @@@@@
 *
 * 해당 문제를 BFS로 풀이하는데 가장 중요한 포인트는 바로 총 3번의 BFS를 통해 이미 열린 문의 처리를 계산해야한다는 점이었습니다.
 *
 * 핵심은 각 참가자마다 bfs를 수행하여 문을 만날 때 마다 문을 연 횟수를 기준으로 visited 배열에 저장합니다.
 * 그렇게 1번 참가자 2번 참자가 만든 visited의 값을 한 배열에 누적하여 더합니다.
 * 마지막으로 울타리 밖에서 위의 과정을 똑같이 진행시켜줍니다.
 * 그러면 1번, 2번, 울타리 밖에서 각각 문을 만나고 진행했던 값이 기록됩니다.
 * 이제 배열을 순회하면서 가장 작은 값을 찾는데 여기서 문이 있는 위치의 값에는 -2를 해줍니다.
 * 이유는 -2를 해주면 중복된 2번의 문 열기를 빼줌으로써 한번만 열었을 때의 값을 알 수 있습니다.
 *
 * 제가 해당 문제를 풀이하지 못한 가장 큰 이유는 BFS를 3번 수행한다는 생각을 못했기 때문입니다.
 * 막연히 '이때까지 문제의 경험으로 BFS를 2번 이상 수행할 필요는 없을 것 같다'는 닫힌 사고로 문제를 풀려고 했던 것이 문제였습니다.
 * 다음에 비슷한 상황이 발생하지 않도록 열린 사고로 풀이를 진행하고, 이에 관해 시간적으로 타당한지 검토하는 습관을 들여야겠다고 배웠습니다.
 *
 * */