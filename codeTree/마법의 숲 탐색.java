import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int R, C, K, answer;
    static int[] dr = {-1,0,1,0,0};
    static int[] dc = {0,1,0,-1,0};

    // 0이 아래, 1이 서쪽, 2가 동쪽
    static List<int[]>[] moveable;

    static class Giant{
        int id, r, c, exit;

        Giant(int id, int c, int exit){
            this.id = id;
            this.r = 1;
            this.c = c;
            this.exit = exit;
        }
    }

    static int[][] map;
    static boolean[][] visited;
    static Giant[] giants;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        clearMap();

        moveable = new ArrayList[3];

        for(int i=0; i<3; i++){
            moveable[i] = new ArrayList<>();
        }

        // 데이터 전처리
        moveable[0].add(new int[]{1,1});
        moveable[0].add(new int[]{2,0});
        moveable[0].add(new int[]{1,-1});

        moveable[1].add(new int[]{-1,-1});
        moveable[1].add(new int[]{0,-2});
        moveable[1].add(new int[]{1,-1});
        moveable[1].add(new int[]{1,-2});
        moveable[1].add(new int[]{2,-1});

        moveable[2].add(new int[]{-1,1});
        moveable[2].add(new int[]{0,2});
        moveable[2].add(new int[]{1,1});
        moveable[2].add(new int[]{1,2});
        moveable[2].add(new int[]{2,1});

        giants = new Giant[K+1];
        boolean flag = false;

        for (int id = 1; id <= K; id++) {

            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken())-1;
            int exit = Integer.parseInt(st.nextToken());

            giants[id] = new Giant(id, c, exit);
            flag = true;

            while(flag){

                flag = false;

                for(int i=0; i<3; i++){
                    flag = checkMoveable(i, id);
                    if(flag){
                        break;
                    }
                }

            }

            // 선 밖으로 나갔다면 클리어
            if(giants[id].r <= 3){
                clearMap();
                continue;
            }

            setMap(id);
            bfs(giants[id]);

        }

        System.out.print(answer);
    }

    static void setMap(int id){
        // 족적 남기기
        for(int i=0; i<5; i++){
            int nr = giants[id].r + dr[i];
            int nc = giants[id].c + dc[i];
            map[nr][nc] = id;
            if(giants[id].exit == i){
                map[nr][nc] = -1*id;
            }
        }

    }

    static void bfs(Giant giant) {

        Queue<int[]> queue = new ArrayDeque<>();
        visited = new boolean[R+3][C];
        queue.add(new int[] {giant.r, giant.c, giant.id});
        visited[giant.r][giant.c] = true;

        int bigR = giant.r;

        while(!queue.isEmpty()){

            int[] cur = queue.poll();
            bigR = Math.max(bigR, cur[0]);

            for(int i=0; i<4; i++){

                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                // 이미 들렸거나 갈 수 없는 곳이면 멈추기
                if(check(nr, nc) || visited[nr][nc] || map[nr][nc]==0) continue;

                // exit거나 같은 거인의 몸에서만 움직이기
                if(map[nr][nc] == cur[2] || map[nr][nc] == -1*cur[2]){
                    queue.add(new int[] {nr, nc, cur[2]});
                    visited[nr][nc] = true;
                }
                // 내 현 위치가 exit일 때 다른 거인으로 옮겨타기
                else if(map[nr][nc] != cur[2] && map[cur[0]][cur[1]] < 0){
                    queue.add(new int[] {nr, nc, Math.abs(map[nr][nc])});
                    visited[nr][nc] = true;
                }

            }
        }

        answer += bigR-2;

    }

    static boolean checkMoveable(int dir, int id) {

        // 옆에 체크
        for(int[] next : moveable[dir]) {

            int nr = giants[id].r + next[0];
            int nc = giants[id].c + next[1];

            if(check(nr,nc) || map[nr][nc] != 0){
                return false;
            }
        }

        giants[id].r += 1;
        if(dir == 1){
            giants[id].c -= 1;
            giants[id].exit = (giants[id].exit+3)%4;
        }
        else if(dir == 2){
            giants[id].c += 1;
            giants[id].exit = (giants[id].exit+1)%4;
        }
        return true;
    }

    static boolean check(int nr, int nc){

        if(nr < 0 || nc < 0 || nr >= R+3 || nc >= C){
            return true;
        }
        return false;
    }

    static void clearMap(){
        map = new int[R+3][C];
    }
}

