import java.io.*;
import java.util.*;

/*
 * 해당 문제는 완전 구현 문제로 따로 해설이 없습니다.
 * */

public class Main {

    static class RC {
        int r, c;
        public RC(RC other) {
           this.r = other.r;
           this.c = other.c;
        }
        RC() {}
    }

    // 문제에서의 기사 = Block 왜냐면 말이 기사지 블록으로 이해하면 더 편함.
    static class Block{
        int id, hp;
        RC leftUp, rightUp, leftDown, rightDown;

        Block(int id, int r, int c, int h, int w, int hp){
            this.id = id;
            this.hp = hp;
            leftUp = new RC();
            rightUp = new RC();
            leftDown = new RC();
            rightDown = new RC();

            leftUp.r = r;
            leftUp.c = c;

            h-=1;
            w-=1;

            rightUp.r = r;
            rightUp.c = c+w;

            leftDown.r = r+h;
            leftDown.c = c;

            rightDown.r = r+h;
            rightDown.c = c+w;

        }

        void move(int dir){

            this.leftUp.r = this.leftUp.r + dr[dir];
            this.leftUp.c = this.leftUp.c + dc[dir];

            this.rightUp.r = this.rightUp.r + dr[dir];
            this.rightUp.c = this.rightUp.c + dc[dir];

            this.leftDown.r = this.leftDown.r + dr[dir];
            this.leftDown.c = this.leftDown.c + dc[dir];

            this.rightDown.r = this.rightDown.r + dr[dir];
            this.rightDown.c = this.rightDown.c + dc[dir];

        }
    }

    // 위쪽, 오른쪽, 아래쪽, 왼쪽
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};

    static int[][] map;
    static int L,N,Q, answer;
    static boolean[] moved, dead;
    static Block[] blocks;

    static int[] origin_hp;
    static Queue<Block> candidate;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new  BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        L = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        map = new int[L+2][L+2];

        // 처음 전부 다 벽으로 만듦.
        for (int i = 0; i < L+2; i++){
            for (int j = 0; j < L+2; j++){
                map[i][j] = 2;
            }
        }

        // map을 초기화 해줌
        for (int i = 1; i <= L; i++){
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= L; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 기사들에 대한 정보들 기억하기.
        dead = new boolean[N+1];
        blocks = new Block[N+1];
        origin_hp = new int[N+1];

        for (int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            blocks[i] = new Block(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            origin_hp[i] = blocks[i].hp;
        }

        candidate = new ArrayDeque<>();
        answer = 0;

        for (int i = 0; i < Q; i++){
            candidate.clear();
            st = new StringTokenizer(br.readLine());
            int id = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            candidate.add(blocks[id]);
            if(!dead[id] && moving_simulate(dir)){
                actual_move(id, dir);
            }
        }

        // 정답을 구하는 방법은 살아있는 기사의 원래 HP - 현재 HP의 총합.
        for (int i = 1; i <= N; i++){
            if(dead[i]){
                continue;
            }
            answer += origin_hp[i] - blocks[i].hp;
        }

        System.out.println(answer);

    }

    // 블록을 시뮬레이션으로 움직일 수 있는지 테스트한다. (만약 움직임이 성공한다면, 성공했다고 리턴한다.)
    static boolean moving_simulate(int dir){

        moved = new boolean[N+1];
        // 움직일 수 없는 경우는? 쭉.. 밀려가다가 무언가 하나라도 벽에 닿게 된다면.

        while (!candidate.isEmpty()){

            Block block = candidate.poll();
            moved[block.id] = true;

            // front에서 구한 범위로 다른 back 부분의 범위에 들어가는가 확인
            RC[] front = get_dir_front_rc(block, dir);
            front[0].r = front[0].r + dr[dir];
            front[0].c = front[0].c + dc[dir];
            front[1].r = front[1].r + dr[dir];
            front[1].c = front[1].c + dc[dir];

            // 벽돌 먼저 체크 만약 한번이라도 만나면 전부 안됨.
            for(int i=front[0].r; i<=front[1].r; i++){
                for(int j=front[0].c; j<=front[1].c; j++){
                    if(map[i][j]==2){
                        return false;
                    }
                }
            }

            // 벽돌체크 끝나면 다른 기사들이 옆에 있는지 확인
            for(int i = 1; i <= N; i++){
                if(dead[i] || moved[i]) continue;
                // 만약 왼쪽으로 한칸 움직인다고 하면, 다른 블록의 오른쪽을 가져옴
                RC[] back = get_dir_front_rc(blocks[i], (dir+2)%4);

                // 위아래로 움직일 때
                if(dir%2==0){
                    // 다른 블록이 바로 내 위/d에 없을 때.
                    if(back[0].r != front[0].r){
                        continue;
                    }
                    // 내 위에 있다고 해도 col 위치가 다를 때 = 다른 애의 오른쪽 아래 c가 지금 올라온 애의 왼쪽 위의 c보다 작을때
                    // 다른 왼쪽 아래의 c가 지금 올라온 애의 오른쪽 위의 c보다 클 때
                    if(back[1].c < front[0].c || back[0].c > front[1].c){
                        continue;
                    }
                    moved[i] = true;
                    candidate.add(blocks[i]);
                }
                // 좌우로 움직일 때
                else {
                    // 다른 블록이 바로 오른쪽에 없을 때.
                    if(back[0].c != front[0].c){
                        continue;
                    }
                    // 내 옆에 있다고 해도 row 위치가 다를 때 = 다른 애의 오른쪽/왼쪽 아래 r이 지금 올라온 애의 왼쪽/오른쪽 위r보다 작을때
                    // 다른 애의 오른쪽/왼쪽 위의 r이 지금 올라온 애의 왼쪽/오른쪽 아래쪽 r보다 클때
                    if(back[1].r < front[0].r || back[0].r > front[1].r){
                        continue;
                    }
                    moved[i] = true;
                    candidate.add(blocks[i]);
                }
            }

        }
        return true;
    }

    // 블록 움직이기에 성공했다면 진짜로 움직여준다.
    static void actual_move(int first_id, int dir){

        for(int i = 1; i <= N; i++){

            if(!moved[i]){
                continue;
            }

            blocks[i].move(dir);
            if(i!=first_id){
                get_damage(i);
            }
        }

    }

    // 움직임이 완료된 블록에서 데미지 입히기
    static void get_damage(int block_id){

        int sr = blocks[block_id].leftUp.r;
        int sc = blocks[block_id].leftUp.c;

        int er = blocks[block_id].rightDown.r;
        int ec = blocks[block_id].rightDown.c;

        int cnt = 0;

        for(int i = sr; i <= er; i++){

            for(int j = sc; j <= ec; j++){

                if(map[i][j] == 1){
                    cnt++;
                }
            }

        }

        blocks[block_id].hp -= cnt;
        if(blocks[block_id].hp <= 0){
            dead[block_id] = true;
        }
    }

    static RC[] get_dir_front_rc(Block block, int dir){

        RC[] dir_rc = new RC[2];

        if(dir == 0){
            dir_rc[0] = new RC(block.leftUp);
            dir_rc[1] = new RC(block.rightUp);
        }
        else if(dir == 1){
            dir_rc[0] = new RC(block.rightUp);
            dir_rc[1] = new RC(block.rightDown);
        }
        else if(dir == 2){
            dir_rc[0] = new RC(block.leftDown);
            dir_rc[1] = new RC(block.rightDown);
        }
        else{
            dir_rc[0] = new RC(block.leftUp);
            dir_rc[1] = new RC(block.leftDown);
        }

        return dir_rc;
    }

}