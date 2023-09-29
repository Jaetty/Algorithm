import java.util.*;
import java.io.*;

public class Main {

    static int t, M, a, N;
    static int[][] map;
    static Queue<Node> people;
    static Queue<Node> wating;
    static Queue<Node> arrive;

    static class Node{
        // start는 순서대로 0 1 2 3 => 하 우 좌 상
        int row, col, start;
        int destRow, destCol;

        Node(){}
        // row와 col은 현위치
        Node(int destRow, int destCol){
            this.destRow = destRow;
            this.destCol = destCol;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        wating = new ArrayDeque<>();
        people = new ArrayDeque<>();
        arrive = new ArrayDeque<>();

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            Node node = new Node(r, c);
            wating.add(node);
        }

        while(a<M) {
            t++;
            if (!people.isEmpty()) {
                int n = people.size();
                for (int i = 0; i < n; i++) {
                    Node node = people.poll();
                    move(node);
                    if(node.row==node.destRow && node.destCol == node.col){
                        arrive.add(node);
                    }
                    else{
                        people.add(node);
                    }
                }
            }
            if (!arrive.isEmpty()) {
                // map에서 2로 표현하여 더이상 갈 수 없는 길로 표시
                a += arrive.size();
                int n = arrive.size();
                for (int i = 0; i < n; i++) {
                    Node node = arrive.poll();
                    map[node.row][node.col] = 2;
                }
            }
            if (!wating.isEmpty()) {
                // 하나만 꺼내서 base를 맞춰준다.
                Node node = wating.poll();
                setBase(node);
            }

        }
        System.out.println(t);
    }
    static void setBase(Node node){
        int[] dr = new int[]{-1,0,0,1};
        int[] dc = new int[]{0,-1,1,0};

        boolean[][] visited = new boolean[N][N];

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{node.destRow, node.destCol});

        while(!queue.isEmpty()){
            int[] rc = queue.poll();

            for(int i=0; i<4; i++){
                int nr = rc[0] + dr[i];
                int nc = rc[1] + dc[i];

                if(nr >= N || nc >= N || nr<0 || nc<0 || visited[nr][nc]) continue;

                if(map[nr][nc] == 1){
                    map[nr][nc] = 0;
                    node.row = nr;
                    node.col = nc;
                    people.add(node);
                    return;
                }
                else if(map[nr][nc]==0){
                    queue.add(new int[] {nr,nc});
                }
                visited[nr][nc] = true;
            }
        }

    }

    static void move(Node node){
        int[] dr = new int[]{1,0,0,-1};
        int[] dc = new int[]{0,1,-1,0};

        boolean[][] visited = new boolean[N][N];

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{node.row, node.col, 0});

        boolean flag = true;

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){
                int nr = rc[0] + dr[i];
                int nc = rc[1] + dc[i];

                if(nr >= N || nc >= N || nr<0 || nc<0 || visited[nr][nc]) continue;

                if(node.destRow== nr && node.destCol == nc){
                    if(flag){
                        node.row = node.row + dr[i];
                        node.col = node.col + dc[i];
                    }else{
                        node.row = node.row + dr[rc[2]];
                        node.col = node.col + dc[rc[2]];
                    }
                    return;
                }

                visited[nr][nc] = true;

                if(flag){
                    queue.add(new int[]{nr, nc, i});
                }
                else{
                    queue.add(new int[]{nr,nc, rc[2]});
                }

            }

            flag = false;
        }
    }
}