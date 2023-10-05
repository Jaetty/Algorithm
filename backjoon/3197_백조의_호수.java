import java.util.*;
import java.io.*;
public class Main {

    static int N,M, count, se;
    static int[][] map;
    static boolean[][] visited;
    static char[][] str;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static int[] parent;
    static Queue<int[]> eraseQueue;
    static int[] startEnd;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        eraseQueue = new ArrayDeque<>();

        count = 1;
        parent = new int[1500*1500/2+1]; // +10은 그냥 넣어본것

        map = new int[N][M];
        str = new char[N][M];
        startEnd = new int[2];

        visited = new boolean[N][M];

        makeSet();

        for(int i=0; i<N; i++){
            str[i] = br.readLine().toCharArray();
        }

        se = 0;

        // O(N^2)
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(str[i][j]!='X' && map[i][j]==0){
                    bfs(i,j);
                    count++;
                }
            }
        }
        int time = 0;


        while(true){
            time++;
            melt();
            if(find(startEnd[0]) == find(startEnd[1])) break;
        }

        System.out.println(time);
        /**/

    }

    static int find(int x){
        if(parent[x]==x) return x;
        else return parent[x] = find(parent[x]);
    }

    static void union(int x, int y){

        int fx = find(x);
        int fy = find(y);

        if(fx==fy) return;

        parent[fx] = fy;

    }

    static void makeSet(){
        for(int i=1; i<=1500*1500/2; i++){
            parent[i] = i;
        }
    }

    static void bfs(int r, int c){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r,c});
        map[r][c] = count;

        if(str[r][c]=='L') startEnd[se++] = count;

        // O(1) + 4
        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr <0 || nc < 0 || nr >=N || nc >=M || map[nr][nc] == count) continue;
                if(str[nr][nc]!='X'){
                    if(str[nr][nc]=='L') startEnd[se++] = count;
                    map[nr][nc] = count;
                    queue.add(new int[]{nr,nc});
                }else{
                    // 이미 큐에 넣었다면 넘어가기
                    if(visited[rc[0]][rc[1]] || visited[nr][nc]) continue;
                    // 큐에 2개 이상 들어가지 못하도록 해준다.
                    eraseQueue.add(new int[]{rc[0],rc[1]});
                    visited[nr][nc] = true;
                    visited[rc[0]][rc[1]] = true;
                }
            }
        }
    }

    public static void melt(){
        int size = eraseQueue.size();

        for(int i=0; i<size; i++){
            int rc[] = eraseQueue.poll();

            for(int j=0; j<4; j++){

                int nr = rc[0] + dx[j];
                int nc = rc[1] + dy[j];

                if(nr <0 || nc < 0 || nr >=N || nc >=M || map[nr][nc] == map[rc[0]][rc[1]]) continue;

                // 벽을 만나면
                if(map[nr][nc]==0){
                    map[nr][nc] = map[rc[0]][rc[1]];
                    eraseQueue.add(new int[] {nr,nc});
                    // 녹인 자리에서 주변에 다른 값이 있는지 확인
                    for(int k=0; k<4; k++){
                        int nnr = nr + dx[k];
                        int nnc = nc + dy[k];

                        if(nnr <0 || nnc < 0 || nnr >=N || nnc >=M || map[nnr][nnc]==0 || map[nnr][nnc] == map[rc[0]][rc[1]]) continue;
                        union(map[nnr][nnc], map[rc[0]][rc[1]]);
                    }
                }

            }
        }

    }


}