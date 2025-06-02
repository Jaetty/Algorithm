import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, sr, sc, gas;
    static int[][] map;
    static boolean[][] visited;
    static Node[][] destinations;

    static class Node {
        int row, col, cost;

        Node(int row, int col){
            this.row = row;
            this.col = col;
        }

        Node(int row, int col, int cost){
            this.row = row;
            this.col = col;
            this.cost = cost;
        }
    }

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        gas = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        destinations = new Node[N][N];

        for (int i = 0; i < N; i++) {

            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }

        }

        st = new StringTokenizer(br.readLine());
        sr = Integer.parseInt(st.nextToken())-1;
        sc = Integer.parseInt(st.nextToken())-1;

        for (int i = 0; i < M; i++) {

            st = new StringTokenizer(br.readLine());

            int pr = Integer.parseInt(st.nextToken())-1;
            int pc = Integer.parseInt(st.nextToken())-1;
            int tr = Integer.parseInt(st.nextToken())-1;
            int tc = Integer.parseInt(st.nextToken())-1;

            // 승객이 있는 칸은 맵에 -1로 따로 표기.
            map[pr][pc] = -1;

            // 그 승객의 도착지 기억하기.
            destinations[pr][pc] = new Node(tr, tc);

        }

        int tr = -1;
        int tc = -1;

        while(M-- > 0){

            // 손님 찾기
            Node passenger = bfs(sr, sc, -1, -1);

            // 손님이 없다면 리턴
            if(passenger == null){
                System.out.println(-1);
                return;
            }

            // 기름 값 수정
            gas -= passenger.cost;

            // 기름이 동나면 끝
            if(gas <= 0){
                System.out.println(-1);
                return;
            }

            // 손님의 위치에 해당하는 도착지 기억하기.
            tr = destinations[passenger.row][passenger.col].row;
            tc = destinations[passenger.row][passenger.col].col;

            // 손님 표식 없애기
            map[passenger.row][passenger.col] = 0;

            Node dest = bfs(passenger.row, passenger.col, tr, tc);
            if(dest == null){
                System.out.println(-1);
                return;
            }
            gas -= dest.cost;

            // 승객을 태운 이후 가스가 0인 상태로 도달했을 때는 실패가 아님.
            if(gas < 0){
                System.out.println(-1);
                return;
            }

            // 기름값과 시작과 갱신
            gas += dest.cost*2;
            sr = tr;
            sc = tc;

        }

        System.out.println(gas);

    }

    static boolean check(int nr, int nc){

        if(nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc] || map[nr][nc] == 1) {
            return true;
        }
        return false;
    }

    // tr의 값이 음수면, 손님을 찾고 있는 상태.
    // tr의 값이 양수면, 승객이 타고 있는 상태.
    static Node bfs(int sr, int sc, int tr, int tc){

        Queue<Node> queue = new ArrayDeque<>();
        // 리턴할 Node 값
        Node result = null;

        queue.add(new Node(sr, sc, 0));
        visited = new boolean[N][N];
        visited[sr][sc] = true;

        while(!queue.isEmpty()){

            Node cur = queue.poll();

            // 모든 에러를 잡는 가장 간단한 방법 !! 그냥 queue에서 뽑았을 때 현재 위치로 판단해주면 된다.
            // 현재 위치에 손님이 있다면?
            if(tr < 0 && map[cur.row][cur.col] == -1){
                // cost, row, col 순으로 가장 작은 위치의 손님 위치를 기억함.
                if(result == null){
                    result = cur;
                }
                else{

                    if(result.cost > cur.cost){
                        result = cur;
                    }
                    else if(result.cost == cur.cost){

                        if(result.row > cur.row){
                            result = cur;
                        }
                        else if(result.row == cur.row && result.col > cur.col){
                            result = cur;
                        }

                    }

                }
            }

            if(cur.row == tr && cur.col == tc){
                return cur;
            }

            for(int i = 0; i < 4; i++){

                int nr = cur.row + dr[i];
                int nc = cur.col + dc[i];

                if(check(nr, nc)){
                    continue;
                }

                visited[nr][nc] = true;
                queue.add(new Node(nr, nc, cur.cost+1));

            }
        }
        return result;
    }

}

/*

19238 스타트 택시

구현 문제

해당 문제의 경우, 문제 자체는 어렵지 않게 bfs로 간단히 풀이할 수 있는 문제입니다.

다만, 오류가 발생할 수 있는 몇 가지 유의애햐는 점들만 고려하면 됩니다.
1. 승객의 탑승 지점과 도착지점은 겹칠 수 있다. 예) (1,1)에 승객이 있는데 도착지가 (1,1)일 수 있음.
2. 승객을 태운 이후, 도착지점에 기름이 0이 되어 도착하는 건 인정된다. 즉, 승객을 태우고 나선 if(gas <= 0)을 적용해야함
3. 택시가 시작부터 승객이 있는 위치에 있을 수 있다.
4. 택시가 승객을 태우지 못하거나 도착지에 못 도달하는 경우가 있다.

위의 문제들을 말끔히 해결하는 간단한 방법은 bfs를 할 때,
Queue에서 꺼낸 현재의 위치 값으로 승객이 있는지, 목적지에 있는지를 판단하게 해주면 됩니다.
가령, queue에 넣을 때 판단하게 만들면 아무래도 불필요한 탐색을 안해도 되니까 그렇게 코드를 짤 분들이 많을 것 같은데
사실 N <= 20이고 M <= 400이기 때문에 충분히 모든 경우의 수를 다 돌아도 문제 없습니다. 그냥 queue에서 꺼냈을 때 판단하게 해주면 해결할 수 있습니다.


*/