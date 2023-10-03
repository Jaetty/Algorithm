import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N,M,K, walkCount;
    static int map[][], person[][];
    static int[] exit;
    static int[] startRC, endRC;
    static int[] dx = {0,-1,1,0,0};
    static int[] dy = {0,0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N+1][N+1];
        person = new int[N+1][N+1];
        walkCount = 0;

        for(int i=1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            person[r][c]++;
        }

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        exit = new int[]{r,c};
        person[r][c] = -1;

        while(K-- > 0){

            if(moving()) break;
            makeSquare();
            while(startRC[0]<=endRC[0] && startRC[1] <= endRC[1]){
                rotate();
                startRC[0]++;
                startRC[1]++;
                endRC[0]--;
                endRC[1]--;
            }
        }

        System.out.println(walkCount);
        System.out.println(exit[0]+" "+exit[1]);


    }

    static boolean moving(){

        boolean check = true;
        int[][] temp = new int[N+1][N+1];

        // int[] = { (dx, dy 적용한 i 값), (맨해튼 거리 값) }
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)->{
            if(e1[1]==e2[1]){
                return e1[0] - e2[0];
            }
            else return e1[1] - e2[1];
        });

        for(int i=1; i<=N; i++){
            for(int j=1; j<=N; j++){

                if(person[i][j]>=1){
                    pq.clear();

                    int nr = 0;
                    int nc = 0;

                    for(int k=0; k<5; k++){

                        nr = i+dx[k];
                        nc = j+dy[k];

                        if(nr <= 0 || nc <=0 || nr>N || nc>N || map[nr][nc]>0) continue;
                        int length = Math.abs(exit[0]-nr) + Math.abs(exit[1]-nc);
                        pq.add(new int[]{k, length});
                    }

                    int[] location = pq.poll();
                    nr = i+dx[location[0]];
                    nc = j+dy[location[0]];

                    if(location[0]!=0) walkCount += 1*person[i][j];

                    if(location[1]!=0){
                        temp[nr][nc] += person[i][j];
                    }
                    person[i][j] = 0;

                }

            }
        }

        for(int i=1; i<=N; i++){
            for(int j=1; j<=N; j++){

                person[i][j] += temp[i][j];
                if(person[i][j]>=1) check = false;

            }
        }
        return check;
    }

    static void makeSquare(){
        startRC = new int[]{1,1};
        endRC = new int[]{2,2};

        int next = 3;

        while(true){

            boolean isExit = false;
            boolean isPerson = false;

            for(int i=startRC[0]; i<=endRC[0]; i++){
                for(int j=startRC[1]; j<=endRC[1]; j++){
                    if(person[i][j]==-1) isExit = true;
                    if(person[i][j]>=1) isPerson = true;
                }
            }

            if(isPerson && isExit) break;

            startRC[1]++;
            endRC[1]++;

            if(endRC[1]>N || startRC[1]>N){
                startRC[1] = 1;
                endRC[1] = next-1;
                startRC[0]++;
                endRC[0]++;
            }

            if(endRC[0]>N || startRC[0]>N){
                startRC[0] = 1;
                startRC[1] = 1;
                endRC[0] = next;
                endRC[1] = next++;
            }
        }

    }

    static void rotate(){

        int r = startRC[0];
        int c = startRC[1];
        int count  = 0;

        // 우 하 좌 상 시계방향
        int[] rdx = {0,1,0,-1};
        int[] rdy = {1,0,-1,0};

        if(r==endRC[0] && c==endRC[1]){
            map[r][c]--;
            return;
        }

        Queue<Integer> wallQueue = new ArrayDeque<>();
        Queue<Integer> personQueue = new ArrayDeque<>();

        while(count < 4){

            int nr = r + rdx[count];
            int nc = c + rdy[count];

            if(nr < startRC[0] || nc < startRC[1] || nr > endRC[0] || nc > endRC[1]){
                count++;
                continue;
            }
            int wall = map[nr][nc]-1;
            wallQueue.add(wall);
            personQueue.add(person[nr][nc]);
            r = nr;
            c = nc;
        }

        r = startRC[0];
        c = endRC[1];
        count = 5;

        while(count < 9){

            int nr = r + rdx[count%4];
            int nc = c + rdy[count%4];

            if(nr < startRC[0] || nc < startRC[1] || nr > endRC[0] || nc > endRC[1]){
                count++;
                continue;
            }

            map[nr][nc] = wallQueue.poll();
            person[nr][nc] = personQueue.poll();

            if(person[nr][nc]<0){
                exit[0] = nr;
                exit[1] = nc;
            }
            r = nr;
            c = nc;
        }

    }

}