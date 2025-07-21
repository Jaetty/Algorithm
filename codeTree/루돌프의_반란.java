import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, P, C, D, time, deadCount;
    static boolean[] dead;
    static int[] shocked;

    static class Santa{
        int id, r, c, score;
        public Santa(int id, int r, int c, int score){
            this.id = id;
            this.r = r;
            this.c = c;
            this.score = score;
        }
    }

    // 상 우 하 좌  대각선
    static int[] dr = {-1, 0, 1, 0, -1, 1, -1, 1};
    static int[] dc = {0, 1, 0, -1, -1, 1, 1, -1};

    // 위 dx, dy 반대 방향
    static int[] rdr = {1, 0, -1, 0,-1, 1, -1, 1};
    static int[] rdc = {0, -1, 0, 1, -1, 1, 1, -1};

    static Santa[] santas;
    static Santa[][] map;

    static class Rudolph{

        int r, c;
        public Rudolph(int r, int c){
            this.r = r;
            this.c = c;
        }

    }
    static Rudolph rudolph;
    static PriorityQueue<int[]> rudolph_pq;

    public static void main(String[] args) throws Exception {

        System.setIn(new FileInputStream("D://input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        time = 0;
        deadCount = 0;

        st = new StringTokenizer(br.readLine());

        dead = new boolean[31];
        shocked = new int[31];

        // 산타를 넣어주기만 할거임
        map = new Santa[N+1][N+1];

        // 거리 작은, r 큰, c 큰
        rudolph_pq = new PriorityQueue<>((e1,e2)->{
            if(e1[0]==e2[0]){

                if(e1[1]==e2[1]){
                    return e2[2]-e1[2];
                }
                return e2[1]-e1[1];

            }
            return e1[0]-e2[0];
        });

        rudolph = new Rudolph(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        santas = new Santa[31];

        // 산타의 ID는 입력되는 순서랑 다르게 랜덤하게 주어짐.
        for(int i = 1; i <= P; i++){
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            santas[id] = new Santa(id, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 0);
            map[santas[id].r][santas[id].c] = santas[id];
        }

        while(M-->0 && deadCount < P){
            rudolphMove();
            santaMove();
            time++;
        }

        for(int i=1; i <= P; i++){
            if(!dead[i]) santas[i].score += time;
            sb.append(santas[i].score).append(" ");
        }

        System.out.print(sb.toString());


    }
    // 루돌프 움직임

    static void rudolphMove(){

        rudolph_pq.clear();

        // 가장 가까운 산타 찾기
        for(int p=1; p<=P; p++){

            if(dead[p]) continue;

            int dist = getDist(rudolph.r, rudolph.c, santas[p].r, santas[p].c);
            rudolph_pq.add(new int[]{dist, santas[p].r, santas[p].c, p});

        }

        if(rudolph_pq.isEmpty()) return;

        int[] next = rudolph_pq.poll();
        rudolph_pq.clear();

        int min_dist = Integer.MAX_VALUE;
        int dir = 0;

        // 루돌프 8방향 찾기
        for(int i=0; i<8; i++){

            int nr = rudolph.r + dr[i];
            int nc = rudolph.c + dc[i];

            int dist = getDist(nr, nc, next[1], next[2]);
            if(dist < min_dist){
                min_dist = dist;
                dir = i;
            }
        }

        // 이동
        rudolph.r += dr[dir];
        rudolph.c += dc[dir];

        // 만약 산타가 있다면 튕겨져 나오기 실행
        if(map[rudolph.r][rudolph.c] != null){
            bounced(dir, C, map[rudolph.r][rudolph.c], true);
            map[rudolph.r][rudolph.c] = null;
        }

    }

    // 튕기기
    static void bounced(int dir, int power, Santa santa, boolean rCrash){

        map[santa.r][santa.c] = null;
        santa.score += power;

        if(!rCrash){
            for(int p=0; p<power; p++){
                santa.r += rdr[dir];
                santa.c += rdc[dir];
            }
        }
        else{
            for(int p=0; p<power; p++){
                santa.r += dr[dir];
                santa.c += dc[dir];
            }
        }

        shocked[santa.id] = time+2;

        if(deadCheck(santa)){
            return;
        }

        // 튕겨져 나가기 수행
        Santa cur = santa;
        Santa temp = null;

        while(map[cur.r][cur.c] != null){

            temp = map[cur.r][cur.c];
            map[cur.r][cur.c] = cur;
            cur = temp;

            if(!rCrash){
                cur.r += rdr[dir];
                cur.c += rdc[dir];
            }
            else{
                cur.r += dr[dir];
                cur.c += dc[dir];
            }

            if(deadCheck(cur)){
                return;
            }
        }

        map[cur.r][cur.c] = cur;
    }


    // 애매한점은 산타를 전부 다 움직이고 충돌 판정을 해야하는지..
    // 아니면 각자 수행하도록 만들어야하는지 애매함...
    static void santaMove(){

        for(int p=1; p<=P; p++){

            if(dead[p] || shocked[p] > time) continue;
            int minDist = getDist(rudolph.r, rudolph.c, santas[p].r, santas[p].c);
            int dir = -1;
            int ny = -1;
            int nx = -1;

            for(int i=0; i<4; i++){

                int nr = santas[p].r + dr[i];
                int nc = santas[p].c + dc[i];

                if(nr <= 0 || nr > N || nc <= 0 || nc > N || map[nr][nc] != null) continue;

                int dist = getDist(rudolph.r, rudolph.c, nr, nc);
                if(dist >= minDist){
                    continue;
                }
                minDist = dist;
                ny = nr;
                nx = nc;
                dir = i;
            }

            if(dir == -1) continue;

            map[santas[p].r][santas[p].c] = null;
            santas[p].r = ny;
            santas[p].c = nx;

            map[ny][nx] = santas[p];

            if(rudolph.r == ny && rudolph.c == nx){
                bounced(dir, D, santas[p], false);
            }

        }

    }

    static int getDist(int rr, int rc, int sr, int sc){
        return ((rr-sr) * (rr-sr)) + ((rc-sc) * (rc-sc));
    }

    static boolean deadCheck(Santa santa){
        if(santa.r <= 0 || santa.c <= 0 || santa.r>N || santa.c>N){
            dead[santa.id] = true;
            santa.score += time;
            deadCount++;
            return true;
        }
        return false;
    }

}