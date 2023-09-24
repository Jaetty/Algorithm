import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M, K;
    static int time;
    static int[][] map;
    static int[][] freshAir; // 처음 에어컨 가동 후 완성되게 되는 합산이 된 map
    static boolean[][][] wall; // 0이면 왼쪽, 1이면 위쪽
    static List<int[]> aircon;
    static List<int[]> office;

    // 0 : 오른쪽, 1: 아래, 2: 왼쪽, 3: 위쪽
    static int[][] dx = {{0, -1, 1}, {1, 1, 1}, {0, -1, 1}, {-1, -1, -1}};
    static int[][] dy = {{1, 1, 1}, {0, -1, 1}, {-1, -1, -1}, {0, -1, 1}};

    /*
     * for문으로 i는 dx, dy의 크기 만큼인 3번 돌아가게 만든다.
     * i == 0 이면 정방향으로 간다는 뜻이고 1, 2는 대각 방향으로 진행된다는 뜻
     *
     *
     * 일단 가려는 곳이 true인지 먼저 체크해서 아니면 진행하고
     * => i를 확인하는 if문, 진행 방향이 상하 좌우를 나누는 if문,
     * i == 0 이면서 rc[2]%2 >= 2 오른쪽, 아래쪽은 다음 블록에 해당하는 wall[진행 방향%2][nr][nc] 값이 있는지 확인한다.
     * i == 0 이면서 rc[2]%2 < 2이면 왼쪽, 위쪽이라는 뜻이고 [rc[2]%2][rc[0]][rc[1]]의 값이 있는지 확인한다.
     *
     *
     * i == 1 이면 rc[2]%2==1이면 위쪽 아래쪽이라는 뜻이고 위쪽 아래쪽은 nc-=1이 되므로 wall[0][rc[0]][rc[1]] 값이 있는지 확인한다.
     * i == 1 이고 rc[2]%2==0이면 오른쪽 왼쪽 이라는 뜻이고 nr-=1이 되므로 wall[1][rc[0]][rc[1]] 값이 있는지 확인한다.
     * i == 2 이고 rc[2]%2==1이면 위쪽 아래쪽이라는 뜻이고 위쪽 아래쪽은 nc+=1이 되므로 wall[0][rc[0]][nc] 값이 있는지 확인한다.
     * i == 2 이고 rc[2]%2==0이면 nr+=1이 되므로 wall[1][nr][rc[0]] 값이 있는지 확인한다.
     *
     * */

    /* 순서는 왼 위 오른 아래*/

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        wall = new boolean[2][N+1][N+1]; // wall[0]은 왼쪽 wall[1]은 위쪽을 뜻한다.
        map = new int[N+1][N+1];
        freshAir = new int[N+1][N+1]; // 모든 에어컨의 작동 결과를 저장하는 배열
        aircon = new ArrayList<>();
        office = new ArrayList<>();
        time = 0;

        for(int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=N; j++) {
                int var = Integer.parseInt(st.nextToken());
                switch(var) {
                    case 1: office.add(new int[] {i,j});
                        break;
                    case 2: aircon.add(new int[] {i,j,2});
                        break;
                    case 3: aircon.add(new int[] {i,j,3});
                        break;
                    case 4: aircon.add(new int[] {i,j,0});
                        break;
                    case 5: aircon.add(new int[] {i,j,1});
                        break;
                }
            }
        }

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = (Integer.parseInt(st.nextToken()));
            int c = (Integer.parseInt(st.nextToken()));
            int dir = (Integer.parseInt(st.nextToken())+1) %2; // 왼쪽이 0이 되고 위쪽이 1이 되도록 바꿈
            wall[dir][r][c] = true;
        }

        int answer = -1;

        // 100번동안 반복한다.
        while(time<100) {

            if(check()) { // 충분 조건을 만족했는지 확인한다.
                answer = time;
                break;
            }

            if(time==0) {
                for(int[] val : aircon){ // 최초에 에어컨을 틀었을 때 공기가 퍼지게 되는 분포를 저장한다.
                    bfs(val);
                }
            }

            plus(); // 퍼져나간 차가운 바람들을 종합하여 map에 합산해준다.

            spread(); // 공기가 퍼져나가는 것을 계산해준다.

            time++;
        }

        System.out.println(answer);

    }

    public static void bfs(int[] dir){

        int nr = dir[0] + dx[dir[2]][0]; // [상하좌우 값][3번 반복]
        int nc = dir[1] + dy[dir[2]][0]; // 0 1 2 3 입력으로 들어오는 방향 값은 : 2 3 4 5 => %4 해주면 => 2 3 0 1

        // 왼쪽, 위쪽이면 현재 위치에서 wall 체크
        // 아니라면 다음 방향에서 wall을 체크해주면 된다.
        if(dir[2]<2){
            if ( wall[dir[2]%2][nr][nc]) return;
        }
        else{
            if (wall[dir[2]%2][dir[0]][dir[1]]) return;
        }

        freshAir[nr][nc] += 5;
        boolean[][] visited = new boolean[N+1][N+1];
        Queue<int[]> queue = new ArrayDeque<>();

        visited[nr][nc] = true;
        queue.add(new int[] {nr, nc, 5});

        while(!queue.isEmpty()){
            int[] rc = queue.poll();

            for(int i=0; i<3; i++){

                nr = rc[0] + dx[dir[2]][i];
                nc = rc[1] + dy[dir[2]][i];

                // 이미 들렸거나 범위를 벗어났으면 넘어감
                if(nr <= 0 || nc <= 0 || nr>N || nc>N || visited[nr][nc] || rc[2]==0) continue;

                // 벽이 있다면 넘어간다.
                // i가 1, 2일 때는 대각석으로 가기 위해 좌우 혹은 상하를 살피고 다음 상하와 좌우를 살펴야한다.
                if(i==0){ // 바로 앞으로 가는 방향
                    if(dir[2]%4<2){ // 오른쪽 아래쪽 인 경우는 nr, nc에 벽이 있는지 확인한다.
                        if ( wall[dir[2]%2][nr][nc]) continue;
                    }
                    else{ // 왼쪽과 위쪽의 경우 현재 위치 기준으로 벽이 있는지 확인한다.
                        if (wall[dir[2]%2][rc[0]][rc[1]]) continue;
                    }
                }
                else if(i==1){ // 대각선으로 가능 방향 1
                    if(dir[2]%2 == 0){
                        if(wall[1][rc[0]][rc[1]]) continue; // 좌우 일때 위 방향이 막혔다면 continue
                        // 위 방향에서부터 좌나 우로 갈 때 방향이 막혀있다면 continue;
                        if(dir[2]<2){
                            if ( wall[0][nr][nc]) continue;
                        }
                        else{
                            if (wall[0][nr][rc[1]]) continue;
                        }
                    }
                    else{
                        if(wall[0][rc[0]][rc[1]]) continue; // 상하 일때 왼쪽이 막혔다면 continue;
                        // 왼쪽 방향에서부터 상하로 갈 때 방향이 막혀있다면 continue;
                        if(dir[2]<2){
                            if ( wall[1][nr][nc]) continue;
                        }
                        else{
                            if (wall[1][rc[0]][nc]) continue;
                        }
                    }
                }
                else{ // 대각선으로 가는 방향 2

                    if(dir[2]%2 == 0){
                        if(wall[1][nr][rc[1]]) continue;
                        if(dir[2]<2){
                            if ( wall[0][nr][nc]) continue;
                        }
                        else{
                            if (wall[0][nr][rc[1]]) continue;
                        }
                    }
                    else{
                        if(wall[0][rc[0]][nc]) continue;

                        if(dir[2]<2){
                            if ( wall[1][nr][nc]) continue;
                        }
                        else{
                            if (wall[1][rc[0]][nc]) continue;
                        }
                    }

                }

                // 막는 벽도 없고 이미 방문하지도 않았다면 모든 결과 배열에 반영하고 큐에 넣어준다.

                visited[nr][nc] = true;
                queue.add(new int[]{nr,nc, rc[2]-1});
                freshAir[nr][nc] += rc[2]-1;
            }
        }

    }

    public static void plus() { // map에 새로운 차가운 공기를 더해준다.

        for(int i=1; i<=N; i++) {
            for(int j=1; j<=N; j++) {
                map[i][j] += freshAir[i][j];
            }
        }

    }


    public static void spread() { // 공기가 높은곳에서 낮은곳으로 퍼지는 기능

        int[][] temp = new int[N+1][N+1];

        for(int i=1; i<=N; i++){
            for(int j=1; j<=N; j++){
                compare(temp, i, j);
            }
        }

        for(int i=1; i<=N; i++){
            for(int j=1; j<=N; j++){
                map[i][j] += temp[i][j];
            }
        }

        // 외벽과 인접한 곳은 1 감소시킨다.
        dfs(1, 2, 0);

    }

    public static void dfs(int r, int c, int dir){

        if(map[r][c]>0) map[r][c] -= 1;
        if(r==1 && c==1) return;
        int nr = r+dx[dir][0];
        int nc = c+dy[dir][0];
        if(nr> N || nc >N || nr <= 0 || nc <=0){
            dir++;
            nr = r+dx[dir][0];
            nc = c+dy[dir][0];
        }
        dfs(nr, nc, dir);

    }

    public static void compare(int[][] temp, int r, int c){

        for(int i=0; i<4; i++){
            int nr = r + dx[i][0];
            int nc = c + dy[i][0];

            if(nr<=0 || nc<=0 || nr>N || nc>N) continue;

            if(i==0){ // 오
                if(wall[0][nr][nc]) continue;
            }
            else if(i==1){ //아
                if(wall[1][nr][nc]) continue;
            }
            else if(i==2) { //왼
                if(wall[0][r][c]) continue;
            }else{ // 위
                if(wall[1][r][c]) continue;
            }
            int diff = (map[r][c] - map[nr][nc])/4;
            if(diff <= 0) continue;
            temp[r][c] -= diff;
            temp[nr][nc] += diff;

        }
    }

    public static boolean check() {

        for(int[] val : office) {
            if (map[val[0]][val[1]] < K) {
                return false;
            }
        }

        return true;
    }

}