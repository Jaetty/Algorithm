import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static char[][] map;
    static int turn;
    static int[] dx = {-1,-1,-1,0,0,0,1,1,1};
    static int[] dy = {-1,0,1,-1,0,1,-1,0,1};
    static boolean flag;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        map = new char[8][8];
        flag = false;

        for(int i=0; i<8; i++){
            map[i] = br.readLine().toCharArray();
        }

        bfs();

        if(flag) System.out.println(1);
        else System.out.println(0);

    }

    static void bfs(){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {7,0, turn});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            if(rc[2]>turn && turn < 8){
                turn++;
                mapChange();
            }

            for(int i=0; i<9; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr < 0 || nc < 0 || nr >=8 || nc >=8 || map[nr][nc]=='#') continue;

                if(nr > 0 && map[nr-1][nc] == '#'){
                    continue;
                }

                if(nr == 0 && nc == 7){
                    flag = true;
                    return;
                }

                queue.add(new int[] {nr, nc, rc[2]+1});
            }
        }
    }

    static void mapChange(){

        char[] first = {'.','.','.','.','.','.','.','.'};

        for(int i=7; i>0; i--){
            map[i] = map[i-1].clone();
        }

        map[0] = first.clone();

    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 벽('#')은 욱제가 움직이고 나서 한 칸 아래로 내려온다.
 * 2. 욱제는 9방향으로 움직일 수 있다.
 * 3. 맵의 크기는 8로 고정이다.
 *
 * 1번 포인트로 알 수 있는 점은 벽이 내려오는게 아닌 아예 한 행의 배열 값이 아래로 이동한다는 점입니다.
 * 그러면 1번 포인트를 수행할 수 있는 함수를 하나 따로 만들어야하고 이 함수를 1턴이 끝날 때 마다 수행해주면 된다는 점을 알 수 있습니다.
 *
 * 2번 포인트로 알 수 있는 점은 욱제를 1턴에 9개의 경우로 이동해주면 된다는 점 입니다.
 * 욱제의 이동이 끝난 후 1번 포인트를 수행시켜주면 됩니다. 저는 이것을 bfs의 queue에 다음 턴 값을 기억해두어서 턴의 증가를 구분하게 하였습니다.
 *
 * 3번 포인트로 알 수 있는 점은 사실 해당 문제는 방문 여부를 체크하기 어려운 조건입니다. 만약 최대 크기가 8이 아닌 100 정도만 되도 메모리 부족을 먼저 겪을 수 있습니다.
 * 하지만 중복이 계속해서 쌓이더라도 크게 문제 없다는 점을 알 수 있습니다.
 *
 * 위의 3가지 포인트를 고려하여 BFS로 9개의 방향으로 움직이되 벽을 만나지 않아야하고 턴 종료시 벽이 내려오는 코드를 구현하면 문제를 풀이할 수 있습니다.
 *
 * */