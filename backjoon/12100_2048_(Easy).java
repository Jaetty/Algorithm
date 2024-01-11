import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, answer;
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};

    static class Block{
        int r, c, size;

        Block(int r, int c, int size){
            this.r = r;
            this.c = c;
            this.size =size;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        int[][] map = new int[N][N];
        List<Block> blocks = new ArrayList<>();

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j]>0){
                    blocks.add(new Block(i, j, map[i][j]));
                }
            }
        }

        answer = 0;

        for(int i=0; i<4; i++){
            recursive(0, i, blocks);
        }

        System.out.println(answer);

    }

    static void recursive(int depth, int dir, List<Block> blocks){

        if(depth>=5){
            Collections.sort(blocks, (e1,e2) ->{
                return e2.size - e1.size;
            });
            answer = Math.max(answer, blocks.get(0).size);
            return;
        }


        // dir은 각각 상 하 좌 우 순으로 진행
        switch (dir){
            case 0 :
                Collections.sort(blocks, (e1,e2) ->{
                    if(e1.r==e2.r){
                        return e1.c-e2.c;
                    }
                    return e1.r - e2.r;
                });
                break;
            case 1 :
                Collections.sort(blocks, (e1,e2) ->{
                    if(e1.r==e2.r){
                        return e1.c-e2.c;
                    }
                    return e2.r - e1.r;
                });
                break;
            case 2 :
                Collections.sort(blocks, (e1,e2) ->{
                    if(e1.c==e2.c){
                        return e1.r-e2.r;
                    }
                    return e1.c - e2.c;
                });
                break;
            case 3 :
                Collections.sort(blocks, (e1,e2) ->{
                    if(e1.c==e2.c){
                        return e1.r-e2.r;
                    }
                    return e2.c - e1.c;
                });
                break;
        }

        int[][] map = new int[N][N];
        // 한 번 합쳐진 블록은 다시 합쳐질 수 없기 때문에 합쳐진 위치 기억하기
        boolean[][] check = new boolean[N][N];
        List<int[]> location = new ArrayList<>();

        for(Block poll : blocks){
            search(map, check, dir, poll, location);
        }

        List<Block> list = new ArrayList<>();

        for(int[] rc : location){
            list.add(new Block(rc[0], rc[1], map[rc[0]][rc[1]]));
        }

        for(int i=0; i<4; i++){
            recursive(depth+1, i, list);
        }

    }

    static void search(int[][] map, boolean[][] check, int dir, Block block, List<int[]> nBlocks){

        // 만약 dir = 0 이면 위쪽으로 이동이므로 현재 위치에서 계속해서 위로 올리고
        // 맨 마지막에 도달 OR 무언가 다른 블록이 막고 있을 때 까지 이동
        // 이때 만난 블록이 이미 합쳐진 블록이면 길이 막힌 것으로 간주
        // n = 10이고 들어온 r, c값이 {5,5}라면 현재 위치에서 막힐 때 까지 계속해서 위로 움직여줌 {4,5}, {3,5}, {2,5}...

        int nr = block.r;
        int nc = block.c;

        while(nr >= 0 && nc >=0 && nr<N && nc<N){

            if(check[nr][nc]){
                break;
            }

            if(map[nr][nc]==0 || map[nr][nc] == block.size){
                nr += dr[dir];
                nc += dc[dir];
            }
            else{
                break;
            }
        }

        nr -= dr[dir];
        nc -= dc[dir];

        if(map[nr][nc] != 0){
            map[nr][nc] += block.size;
            check[nr][nc] = true;
        }
        else{
            map[nr][nc] = block.size;
            nBlocks.add(new int[]{nr,nc});
        }

    }

}

/*
* 브루트포스 + 시뮬레이션 문제
*
* 2048게임이란 한쪽으로 움직이게 되면 모든 블록이 그 방향으로 움직이게 됩니다.
* 이 게임은 다음과 같은 방법으로 진행됩니다.
* 1. 상 하 좌 우로 블록들을 움직일 수 있습니다. 그러면 블록들은 그 방향으로 다른 블록이나 마지막 칸에 도달할 때 까지 움직입니다.
* 2. 만약 움직이다가 나와 같은 값의 블록과 만나면 서로 합쳐지게 됩니다.
* 3. 한번의 움직임에서는 딱 한번 합치는게 가능합니다. 예를 들어 2 2 4 라는 블록이 있습니다. 왼쪽으로 움직여 2와 2가 만나 4가 되지만 이 4는 이번 움직임에서 다른 4와 합쳐지지 않습니다.
* 4. 블록은 움직임이 끝나고 랜덤한 위치에 랜덤한 값의 블록 하나가 더 생성됩니다.
*
* 게임을 이해했다면 이번 문제의 포인트는 다음과 같습니다.
* 1. 최대 5번 움직여서 만들 수 있는 가장 큰 블록의 크기를 출력해야합니다.
* 2. 블록은 최초에 1개 이상 주어지고 게임 중 추가로 생성되지 않습니다.
*
* 게임의 특성과 문제를 생각해보면 백트래킹으로 모든 경우의 수를 수행해야한다는 점을 유추할 수 있을 것 입니다.
*
* 저의 풀이는 다음과 같이 진행했습니다.
* 1. 백트래킹으로 상 하 좌 우로 이동을 수행하게 만들어줍니다. (5번까지 수행합니다.)
* 2. 어떤 블록이 어디에 있는지 List를 이용하여 기억합니다.
* 3. 상 하 좌 우 에 맞춰 list에서 먼저 꺼내야하는 순서가 있으므로 그 순서에 맞게 정령해줍니다.
* 4. list에서 블록을 꺼내서 상 하 좌 우 방향으로 멈추는 조건이 발생될 때 까지 이동 & 병합 시켜줍니다. 멈추면 그 위치를 기억합니다.
* 5. 모든 이동이 끝난 후 블록들이 위치했던 곳의 값을 불러와 다시 block을 담은 list를 만들어서 1번부터 반복합니다.
*
* 저는 3~5까지의 과정을 수행한 이유가 list를 통해 블록이 있는 위치만 기억하게 만들면 굳이 O(N^2)으로 일일히 블록의 위치를 찾아주는 과정이 없어져서
* 메모리는 더 필요하더라도 속도는 더욱 빨라질 것이라고 생각했는데 오히려 O(N^2)으로 찾는 경우가 더욱 빠르고 메모리도 적게 차지하는것을 확인했습니다...
*
* 아무래도 새롭게 객체를 만드는 과정, Collcetions.sort를 수행하는 시간, list에서 값을 뽑아오고 하는 시간등이 차이를 만들었다고 생각합니다.
*
* 그래서 해당 문제는 위와 같이 풀이하는 것 보다 배열만을 이용하여 O(N^2)으로 조건에 맞게 탐색을 수행하는 것이 더욱 빠른 풀이입니다.
*
* 저는 이번 문제를 계기로 다시 한번 시간 효율에 대해 더욱 고려하는 코딩을 수행해야할 것 같습니다.
*
* */