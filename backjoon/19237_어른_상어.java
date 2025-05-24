import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, K, time;
    // 상어가 살아있는지 확인용
    static boolean[] alive;

    // 위, 아래, 왼쪽, 오른쪽
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0,-1, 1};

    // 각 격자 칸
    static class Base{

        int time, who;
        // 이 격자에 들어온 상어들을 보관함
        List<Shark> sharks;

        Base(){
            this.time = 0;
            this.who = -1;
            this.sharks = new ArrayList<>();
        }

        // 상어가 두마리 이상 들어왔을 때 한 마리를 삭제해줌.
        void eliminate(int time){

            if(!sharks.isEmpty()){

                int min = Integer.MAX_VALUE;

                for(Shark shark : sharks){
                    alive[shark.number] = false;
                    min = Math.min(min, shark.number);
                }

                alive[min] = true;
                who = min;
                this.time = time + K;

            }
            sharks.clear();
        }

    }

    static class Shark{

        // dir 순서는 위, 아래, 왼쪽, 오른쪽
        int number, r, c, dir;
        int[][] priority;

        Shark(int number, int r, int c){

            this.number = number;
            this.dir = 0;
            this.r = r;
            this.c = c;
            this.priority = new int[4][4];
        }

        void setDir(int dir){
            this.dir = dir;
        }

        void setPriority(int idx, int[] move){
            priority[idx] = move;
        }

    }

    static List<Shark> sharks;
    static Base[][] map;
    static List<Base> arrived;


    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new Base[N][N];
        alive = new boolean[M+1];
        arrived = new ArrayList<>();

        sharks = new ArrayList<>();

        for(int i=0; i<N; i++){

            st = new StringTokenizer(br.readLine());

            for(int j=0; j<N; j++){
                int val = Integer.parseInt(st.nextToken());
                map[i][j] = new Base();

                if(val!=0){
                    alive[val] = true;
                    Shark shark = new Shark(val,i,j);
                    sharks.add(shark);

                    map[i][j].who = shark.number;
                    map[i][j].time = K;
                }
            }
        }

        st = new StringTokenizer(br.readLine());
        Collections.sort(sharks, (e1,e2)-> e1.number - e2.number);

        for(int i=0; i<M; i++){
            sharks.get(i).setDir(Integer.parseInt(st.nextToken())-1);
        }


        // 어떤 방향일 때, 다음 방향 우선 순위 정해주기
        for(int i=0; i<M*4; i++){

            st = new StringTokenizer(br.readLine());
            int[] val = new int[4];

            for(int j=0; j<4; j++){
                val[j] = Integer.parseInt(st.nextToken())-1;
            }

            sharks.get(i/4).setPriority(i%4, val);

        }

        while(sharks.size()>1 && time < 1001){

            time++;

            sharkMove();
            eliminateSharks();

        }

        System.out.println(time == 1001 ? -1 : time);


    }

    static void sharkMove(){

        for(int s=0; s<sharks.size(); s++){

            Shark shark = sharks.get(s);
            List<int[]> blanks = new ArrayList<>();
            List<int[]> myPath = new ArrayList<>();

            for(int d=0; d<4; d++){

                int nr = shark.r + dx[d];
                int nc = shark.c + dy[d];

                if(nr < 0 || nc < 0 || nr >= N || nc >= N ){
                    continue;
                }

                // 내가 아닌 상어가 들렸으면서, 아직 향기가 남아있다면
                if(map[nr][nc].who != shark.number && map[nr][nc].time >= time){
                    continue;
                }

                // 어떤 상어가 있었던간에 향이 나지 않는다면
                if(map[nr][nc].time < time){
                    blanks.add(new int[]{nr, nc});
                }
                // 향은 있지만, 내 냄새라면
                else if(map[nr][nc].who == shark.number){
                    myPath.add(new int[]{nr, nc});
                }

            }

            // 상어가 빈 칸에 갈 수 있다면 무엇보다 우선해야함
            if(!blanks.isEmpty()){
                chooseNext(shark, blanks);
            }
            // 상어가 빈 칸에 갈 수 없는 경우, 자신의 향기 구역 중 하나로 가야함, 이것도 우선순위 맞춰서
            else{
                chooseNext(shark, myPath);
            }

        }

    }

    static void chooseNext(Shark shark, List<int[]> next){

        // 위치가 맞는지 확인
        boolean found = false;

        // 먼저 상어에서 가장 우선순위 높은 새 위치를 뽑아본다.
        for(int d=0; d<4; d++){

            if(found) break;

            int dir = shark.priority[shark.dir][d];

            int nr = shark.r + dx[dir];
            int nc = shark.c + dy[dir];

            if(nr < 0 || nc < 0 || nr >= N || nc >= N ){
                continue;
            }

            for(int[] val : next){

                // 만약 우선순위 순으로 했을 때 위치가 맞다면 방향, 향기, 등을 세팅
                if(val[0] == nr && val[1] == nc ){
                    found = true;

                    map[nr][nc].sharks.add(shark);
                    shark.dir = dir;
                    shark.r = nr;
                    shark.c = nc;

                    // 나중에 여기에 중첩해서 들어간 상어들을 지워줄 예정
                    arrived.add(map[nr][nc]);
                    break;
                };

            }
        }

    }

    static void eliminateSharks(){

        for(Base b : arrived){
            b.eliminate(time);
        }
        arrived.clear();

        for(int s=sharks.size()-1; s>=0; s--){
            if(!alive[sharks.get(s).number]){
                sharks.remove(s);
            }
        }
    }

}

/*

19237 어른 상어

구현 문제

해당 문제는 구현이기 때문에 문제에 나온대로 작동하게끔 만들기만 하면 됩니다.
때문에, 문제 자체의 해설보다는 어떻게 구현했는가에 대해 설명하겠습니다.

저의 풀이 방법의 핵심은 다음과 같습니다.
1. 각 격자에 어떤 상어가 들어왔고, 어떤 상어가 몇 초간의 향기를 남겼는지 기억하게 만든다.
2. 움직임은 먼저 인접한 칸에 비어있는 칸의 위치들과 나의 향기가 남은 칸의 위치를 기억한다.
3. 비어있는 칸이 하나라도 있다면 그걸 우선해서 움직이고, 없다면 향기가 남은 칸으로 움직인다.
4. 모든 상어가 다 움직이고 나면, 그 격자에서 하나의 상어만 남기고 다 없앤다.

저는 상어가 위치하는 격자를 Base라는 클래스로 나타냈습니다.
Base는 냄새 지속시간(time), 어떤 상어가 그 냄새를 남겼는지(who), 들어온 모든 상어 모음(sharks)가 있습니다.
핵심은 상어를 모든 상어가 다 움직인 이후에 이 값들을 수정해주는 것 입니다.

저는 이를 상어가 움직일 수 있는 칸을 찾게되면 그 위치의 Base class의 sharks 리스트에 넣어주는 것으로 해결했습니다.
그리고 모든 움직임이 끝나면, eliminate()를 실행시켜 가장 작은 값만 남기고 나머지를 제외시키는 방법을 적용했습니다.

또한, 움직임은 일단 bfs로 4군데를 가보고 정하는게 아니라, 4군데를 살펴서 거기서 빈칸과 내 향기가 남은 칸들을 모두 기억한 후
빈칸이 하나라도 없다면 그때, 향기가 남는 부분에서 탐색을 수행하게 만드는게 핵심입니다.

위의 조건만 조금 유의하면 특별히 어려운 구간은 없기 때문에, 해당 문제는 구현 과정 중 실수만 없다면 금방 구현할 수 있는 문제입니다.

*/