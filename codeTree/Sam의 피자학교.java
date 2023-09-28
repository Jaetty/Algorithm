import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int index, N, K, height, half;
    static int time = 0;
    static int[] dx = {-1,0};
    static int[] dy = {0,-1};
    static Integer[][] map;
    static int[][] tmp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new Integer[N+1][N+1];

        if(N==1){
            System.out.println(1);
            return;
        }

        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=N; i++){
            map[N][i] = Integer.parseInt(st.nextToken());
        }

        while(check()){
            min();
            height = 1;
            index = 2;

            while(true){
                if( (height-1) + index > N ) break;
                roll();
            }
            bfs(0);
            unfold();

            half = N;
            index = 0;
            for(int i=0; i<2; i++){
                half/=2;
                fold();
            }
            bfs(1);
            unfold();
            time++;
        }

        System.out.println(time);

    }

    public static void bfs(int y){

        tmp = new int[N+1][N+1];

        for(int i=N; i>0; i--){
            for(int j=N; j>0; j--){
                if(map[i][j]==null) continue;

                for(int k=0; k<2; k++){

                    int nr = i + dx[k];
                    int nc = j + dy[k];

                    if(nr > N || nc >N || nc <= 0 || nr <= 0 || map[nr][nc] == null ) continue;
                    int val = Math.abs(map[i][j] - map[nr][nc])/5;

                    if(map[i][j] > map[nr][nc]){
                        tmp[i][j] -= val;
                        tmp[nr][nc] += val;
                    }else{
                        tmp[i][j] += val;
                        tmp[nr][nc] -= val;
                    }

                }
            }
        }

        for(int i=N; i>0; i--){
            for(int j=N; j>0; j--){
                if(map[i][j]==null) continue;
                map[i][j] += tmp[i][j];
            }
        }

    }

    public static void fold(){
        index = index + half;
        for(int i = index; i>0; i--){
            if(map[N][i]==null) break;
            int r = N;
            Stack<Integer> stack = new Stack<>();
            while(r!=0 && map[r][i]!=null){
                stack.push(map[r][i]);
                map[r--][i] = null;
            }

            while(!stack.isEmpty()){
                map[r--][index+ 1 + (index- i)] = stack.pop();
            }
        }
    }

    public static void unfold(){
        Queue<Integer> que = new ArrayDeque<>();
        for(int i=1; i<=N; i++){
            if(map[N][i]!=null){
                int r = N;
                while(map[r][i] != null ){
                    que.add(map[r][i]);
                    map[r--][i] = null;
                }
            }
        }

        for(int i=1; i<=N; i++){
            map[N][i] = que.poll();
        }

    }
    public static void roll(){

        int r = N;
        int c = index;
        while(r==0 || map[r][index-1]!=null){
            int nr = N-1;
            for(int i=index-1; i>=0; i--){
                if(map[r][i]==null) break;
                map[nr--][c] = map[r][i];
                map[r][i] = null;
                height = Math.max(height, (N - nr) );
            }
            r--;
            c++;
        }
        index = index + (N-r);
    }

    public static void min(){

        int min = Integer.MAX_VALUE;
        int max = 1;
        List<int[]> list = new ArrayList<>();

        for(int i=1; i<=N; i++){
            max = Math.max(max, map[N][i]);
            if(min==map[N][i]){
                list.add(new int[]{N,i});
            }
            else if(min>map[N][i]){
                list.clear();
                list.add(new int[]{N,i});
                min = map[N][i];
            }
        }

        for(int[] rc : list){
            map[rc[0]][rc[1]] += 1;
        }
    }

    public static boolean check(){

        int min = Integer.MAX_VALUE;
        int max = 1;

        for(int i=1; i<=N; i++){
            max = Math.max(max, map[N][i]);
            min = Math.min(min, map[N][i]);
        }
        if(max - min <= K) return false;
        return true;
    }

}