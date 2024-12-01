import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    // 상어 클래스로 상어가 특정 위치에 이동했을 때의 물고기와 이동 방향 상황등을 기억합니다.
    static class Shark{

        int r, c, sum;
        int[][] map;
        int[][] dir;

        Shark(int r, int c, int sum, int[][] map, int[][] dir){
            this.r = r;
            this.c = c;
            this.sum = sum;
            this.map = map;
            this.dir = dir;
        }

    }

    static Queue<Shark> queue;
    static int[] dx = {-1,-1,0,1,1,1,0,-1};
    static int[] dy = {0,-1,-1,-1,0,1,1,1};
    static int answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // map은 물고기의 번호 값을 dir은 이동방향을 기억해둡니다.
        int[][] map = new int[4][4];
        int[][] dir = new int[4][4];
        queue = new ArrayDeque<>();
        answer = 0;

        for(int i=0; i<4; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<4; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                dir[i][j] = Integer.parseInt(st.nextToken())-1;
            }
        }

        int sum = map[0][0];
        map[0][0] = -1;

        Shark start = new Shark(0,0, sum, map, dir);
        queue.add(start);

        // 모든 상어의 경우의 수가 더이상 움직일 수 없을 때 까지 반복.
        while(!queue.isEmpty()){

            Shark shark = queue.poll();
            // 물고기 먼저 움직이고 다음 상어를 움직입니다.
            fishMove(shark);
            sharkMove(shark);

        }

        System.out.print(answer);

    }

    static void fishMove(Shark shark){

        int[][] nMap = new int[4][4];
        int[][] nDir = new int[4][4];

        // 물고기를 번호 크기 순서대로 꺼내기 위해 PQ를 사용합니다.
        // 배열은 4의 크기로 {물고기.x, 물고기.y, 물고기.번호, '입력 시간'}
        // 여기서 입력 시간은 나중에 물고기들 끼리 위치를 바꿨다면 가장 늦게 입력 시간이 들어온 물고기의 위치가 '참'입니다.
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)->{
            if(e1[2]==e2[2]){
                return e2[3] - e1[3];
            }
            return e1[2] - e2[2];
        } );

        // 물고기 위치(x,y)와 그 물고기 방향,
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                nMap[i][j] = shark.map[i][j];
                nDir[i][j] = shark.dir[i][j];

                if(nMap[i][j]<=0) continue;
                pq.add(new int[]{i, j, nMap[i][j], 0});
            }
        }

        // next는 동일한 물고기가 pq에 중복되서 들어갈 수 있으므로 이미 움직였던 물고기보다 큰 값만 움직여야합니다.
        // 물고기는 번호가 작은 순서대로 한번만 움직이므로 다음으로 움직이는 물고기는 전의 번호보다 커야합니다. 이를 판별하는 변수.
        int next = 0;

        while(!pq.isEmpty()){

            int[] fish = pq.poll();

            // 만약 이미 움직였던 물고기보다 물고기 번호가 작다면 continue
            if(fish[2] <= next){
                continue;
            }

            int r = fish[0];
            int c = fish[1];
            int d = nDir[r][c];

            while(true){
                int nr = r + dx[d%8];
                int nc = c + dy[d%8];

                // 조건 불충족시 반시계방향 회전
                if(check(nr, nc, nMap, false)){
                    d++;
                }
                else{

                    // 다른 물고기와 자리 바꾸기 처리
                    if(nMap[nr][nc]!=0){
                        nMap[r][c] = nMap[nr][nc];
                        nDir[r][c] = nDir[nr][nc];
                    }
                    // 빈 공간으로 이동 시 처리
                    else{
                        nMap[r][c] = 0;
                        nDir[r][c] = 0;
                    }
                    nMap[nr][nc] = fish[2];
                    nDir[nr][nc] = d%8;

                    pq.add(new int[]{r,c,nMap[r][c],fish[2]});
                    next = fish[2];
                    break;

                }
            }
        }

        // 모든 물고기가 움직이고 난 후의 결과를 기억해주기
        shark.map = nMap;
        shark.dir = nDir;

    }

    static void sharkMove(Shark shark){

        int d = shark.dir[shark.r][shark.c];

        // 어차피 상어는 최대 3칸만 움직일 수 있으므로 3번만 반복
        // 여기서 i를 곱해서 1칸 2칸 3칸을 정함
        for(int i=1; i<4; i++){

            int nr = shark.r + dx[d]*i;
            int nc = shark.c + dy[d]*i;

            // 상어가 이동 조건을 충족 못할 시 나오는 처리
            if(check(nr, nc, shark.map, true)){
                answer = Math.max(answer, shark.sum);
                continue;
            }

            int[][] nMap = new int[4][4];
            int sum = shark.sum + shark.map[nr][nc];

            for(int j=0; j<4; j++){
                nMap[j] = shark.map[j].clone();
            }

            nMap[shark.r][shark.c] = 0;
            nMap[nr][nc] = -1;
            queue.add(new Shark(nr,nc, sum, nMap, shark.dir));

        }

    }
    static boolean check(int nr, int nc, int[][] map, boolean shark){

        if(nr < 0 || nc <0 || nr>=4 || nc >=4) return true;

        if(shark){
            if(map[nr][nc]<=0) return true;
        }
        else{
            if(map[nr][nc]==-1) return true;
        }
        return false;
    }

}

/*
 * 구현 문제
 *
 * 먼저 문제를 이해보면 상어의 움직임에 따라 다음 물고기의 움직임이 전혀 달라집니다.
 * 그렇다면 단순히 BFS로 푸는 문제가 아닌 각각의 경우마다 다른 결과가 있으므로 모든 경우의 수를 다 시도해야합니다.
 *
 * 문제의 풀이 방법은 문제에 나와있는 조건을 그대로 구현하면 됩니다.
 * 저의 풀이 방법은
 * 1. 상어가 움직이는데 성공한 경우는 queue에 저장. 아니면 넣지 않기.
 * 2. 각 상어가 움직인 경우에 맞춰서 물고기를 움직여주기
 * 3. 여기서 상어가 움직일 수 있는 경우를 확인해주고 맨 위의 1번 과정부터 반복.
 *
 * 자세한 코드 분석은 코드에 적힌 주석을 참고해주세요.
 *
 * */