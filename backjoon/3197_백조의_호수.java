import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

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

/*
 * 그래프 이론 + 유니온 파인드 문제
 * 해당 문제는 유니온 파인드와 BFS를 활용하여 문제를 해결하였습니다.
 * 문제의 경우 입력 되는 최대 행과 열의 크기가 1500으로 단순히 얼음을 녹이고 L끼리 BFS를 해주면 시간초과가 발생합니다.
 * 그렇다면 문제를 해결하기 위해서는 결국 백조의 위치를 각각 L1, L2라고 한다면
 * L1이 속해있던 물과 L2가 속해있던 물이 이어지게 되는지만 확인하면 됩니다.
 *
 * 제가 풀이한 방법은 최초 BFS를 통해 각자 유니온 파인드의 번호를 매겨줍니다.
 * 예를들어 다음과 같은 입력이라면
 * 3 6
 * LX.X.X
 * X.X.X.
 * .X.XLX
 *
 * 여기서 물이 있는 위치를 숫자(백조는 물 위에 있습니다.)로 나타내면
 * 1X2X3X
 * X4X5X6
 * 7X8X9X
 *
 * parent 배열은 {1,2,3,4,5,6,7,8,9} 가 되게 됩니다.
 * 이제 얼음과 접한 물이 있는 위치를 Queue에 넣어줍니다.(어떤 얼음과 접한 물이 여러개라면 가장 첫번째 물만 큐에 넣어줍니다.)
 *
 * -저는 얼음이 아닌 물을 기준으로 큐에 넣었는데 이유는 어떤 물이 4면에 얼음이 접해있다면
 *  얼음은 4개를 큐에 넣어줘야하고 물은 하나만 넣어주면 되기 메모리적으로 더 나을 것 같기 때문에 물을 큐에 넣기로 정하였습니다. -
 *
 * 큐에서 값을 하나 꺼내서 4방향으로 얼음이 있다면 물로 바꿔주고
 * 물로 바꾼 후 다른 곳과 접했는지 확인해줍니다.
 * 얼음과 접하였다면 현재 위치를 큐에 넣어주고
 * 다른 물과 접했다면 유니온 파인드로 서로를 이어줍니다.
 *
 * 1X2X3X
 * X4X5X6
 * 7X8X9X
 *
 * 위의 입력으로 다시 예를 들면
 * 얼음과 접한 큐에 들어가는 값은 각각 1, 2, 3, 4, 5, 6만 들어갑니다.
 * 1, 2, 3은 오른쪽과 아래에 접하고, 4 5 6은 아래와 접해서 들어가게 되고
 * 7 8 9 는 접해있는 얼음과 맞닿은 물이 이미 큐에 있으니 넣지 않습니다.
 *
 *
 * 1을 큐에 넣으면 1의 4방향을 확인합니다.
 * 상하좌우를 찾을 때 아래쪽과 오른쪽이 얼음입니다.
 * 순서대로 아래쪽을 물로 만들고
 * 1x2X3X
 * 14X5X6
 * 7X8X9X
 * 그 위치에서 다른 번호와 접하는지 확인합니다.
 * 1은 이제 7하고 4하고 접했기에 유니온 파인드로 1 4 7이 이어졌다고 수정해줍니다.
 * 또 오른쪽으로 가면 오른쪽 얼음이 1이 되고 2와 1이 이어졌다고 수정해줍니다.
 * 112X3X
 * 14X5X6
 * 7X8X9X
 *
 * 이렇게 진행하게 되면 언젠가 L1의 숫자와 L2의 숫자가 합해지게 될 것입니다.
 * 분리 집합을 통해 서로가 이어지게 되면 걸린 시간을 출력하면 됩니다.
 *
 * */