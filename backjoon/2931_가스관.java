import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static char[] candidate = {'|','-','+','1','2','3','4'};
    static int[] dr = {-1,1,0,0};
    static int[] dc = {0,0,-1,1};
    static int direction = 0;
    static char[][] map;
    static boolean[][] visited;
    static int N,M;
    static int[] rc;
    static HashSet<Character> set[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        visited = new boolean[N][M];
        rc = new int[2];

        setSetting();

        for(int i=0; i<N; i++){
            String temp = br.readLine();
            for(int j=0; j<M; j++){
                map[i][j] = temp.charAt(j);
                if(map[i][j]=='M'){
                    rc[0] = i;
                    rc[1] = j;
                }
            }
        }

        for(int i=0; i<4; i++) {
            int nr = rc[0] + dr[i];
            int nc = rc[1] + dc[i];

            if (nr < 0 || nc < 0 || nr >= N || nc >= M || map[nr][nc] == '.' || map[nr][nc]=='Z') continue;

            direction = i;
            break;
        }

        while(map[rc[0]][rc[1]]!='Z'){

            int nr = rc[0]+dr[direction];
            int nc = rc[1]+dc[direction];

            if(map[nr][nc]=='.'){

                int count = 0;

                for(int i=0; i<4; i++) {
                    int r = nr + dr[i];
                    int c = nc + dc[i];

                    if (r < 0 || c < 0 || r >= N || c >= M || map[r][c] == '.') continue;

                    switch (i){
                        case 0:{
                            // . 위에 이어지는 가스관이 있으려면? 블록 1, 블록 4, 블록 +, 블록 | 밖에 없다
                            if(!set[0].contains(map[r][c])){
                                candidate[0] = '.';
                                break;
                            }
                            candidate[1] = '.';
                            for(int j=3; j<candidate.length; j++){
                                    if(!set[0].contains(candidate[j])) continue;
                                    candidate[j] = '.';
                            }
                            count++;
                        }
                        break;
                        case 1:{
                            // 아래로 이어지는 가스관의 경우 2, 3, | + 밖에 없다.
                            if(!set[1].contains(map[r][c])){
                                candidate[0] = '.';
                                break;
                            }
                            candidate[1] = '.';
                            for(int j=3; j<candidate.length; j++){
                                if(!set[1].contains(candidate[j])) continue;
                                candidate[j] = '.';
                            }
                            count++;
                        }
                        break;
                        case 2:{
                            // 왼쪽으로 이어지는 가스관의 경우 1, 2, +, -
                            if(!set[2].contains(map[r][c])){
                                candidate[1] = '.';
                                break;
                            }
                            candidate[0] = '.';
                            for(int j=3; j<candidate.length; j++){
                                if(!set[2].contains(candidate[j])) continue;
                                candidate[j] = '.';
                            }
                            count++;
                        }
                        break;
                        case 3:{
                            if(!set[3].contains(map[r][c])){
                                candidate[1] = '.';
                                break;
                            }
                            candidate[0] = '.';
                            for(int j=3; j<candidate.length; j++){
                                if(!set[3].contains(candidate[j])) continue;
                                candidate[j] = '.';
                            }
                            count++;
                        }
                        break;
                    }

                }

                for(int i=0; i< candidate.length; i++){
                    if(candidate[i]!='.'){
                        if(candidate[i]=='+' && count<4) continue;
                        System.out.println( (nr+1) + " " + (nc+1) + " "+candidate[i]);
                        return;
                    }
                }


            }

            switch (map[nr][nc]){
                case '1' :{
                    if(direction==0) direction = 3;
                    else direction = 1;
                } break;
                case '2' :{
                    if(direction==1) direction = 3;
                    else direction = 0;
                } break;
                case '3' :{
                    if(direction==1) direction = 2;
                    else direction = 0;
                } break;
                case '4' :{
                    if(direction==0) direction = 2;
                    else direction = 1;
                } break;
            }
            rc[0] = nr;
            rc[1] = nc;

        }



    }

    static void setSetting(){

        set = new HashSet[4];

        set[0] = new HashSet<>();
        set[1] = new HashSet<>();
        set[2] = new HashSet<>();
        set[3] = new HashSet<>();

        set[0].add('|');
        set[0].add('+');
        set[0].add('1');
        set[0].add('4');

        set[1].add('|');
        set[1].add('+');
        set[1].add('2');
        set[1].add('3');

        set[2].add('-');
        set[2].add('+');
        set[2].add('1');
        set[2].add('2');

        set[3].add('-');
        set[3].add('+');
        set[3].add('3');
        set[3].add('4');
    }

}

/*
 * 구현, 시뮬레이션 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. M과 Z는 하나의 '블록'과 인접해있는 입력만 주어진다.
 * 2. 항상 답이 존재한다.
 * 3. 불필요한 블록이 존재하지 않는다.
 *
 * 1번 포인트에서 알 수 있는건 M이나 Z 주변엔 단 하나의 블록만 존재합니다.
 * 즉 저희들이 DFS혹은 탐색을 할 때 방향이 여러곳인 경우는 존재하지 않는다는 점입니다.
 * 다만 주의해야할 점은 하나의 '블록'과 인접한 입력이 주어진다는 뜻은 M과 Z가 서로 붙어있는 입력이 주어질 수 있다는 점입니다.
 *
 * 2번 포인트로 정답이 없는 경우는 없다는 점을 알 수 있습니다.
 *
 * 3번 포인트로 모든 블록들은 서로 연결되어야만 한다는 점을 알 수 있습니다.
 *
 * 저의 풀이는 이렇습니다.
 * 1. M 혹은 Z에서 출발해서 가스관을 따라서 계속해서 이동한다. (저는 M에서 시작했습니다.)
 * 2. 탐색을 지속하면 반드시 길이 끊어진 부분에 도달하게 된다.
 * 3. 길이 끊어진 부분에서 상하좌우 4방향에 어떤 블록들이 놓아져있는지 확인한다.
 * 4. 블록들의 모양에 맞게 정답이 될 수 있는 후보를 추려내면 단 하나의 정답이 나온다.
 *
 * 3번의 경우 좀더 자세히 말하자면 만약 제가 지금 길이 끊어진 부분에 도달을 했고 그 위치가 (2, 2) 일 때
 * 먼저 (1,2), (3,2), (2,1), (2,3)에 가스관이 있는지 확인합니다.
 * 그리고 각 위치마다 가스관이 연결될 수 있는 가스관인지 확인합니다.
 * 예를들어 (1,2) 위치에 가스관이 있다면 나와 연결되려면 {+, |, 1, 4} 블록들만 가능합니다.
 * 만약 이 블록들이 없다면 위로는 가지 말아야하기에 {|, +}는 정답 후보가 되지 못합니다.
 * 만약 (1,2)가 {+, |, 1, 4} 블록 중 하나라면 위로 연결이 되야만합니다.
 * 그렇다면 {-, 1, 4} 는 정답의 후보가 되지 못하므로 소거합니다.
 *
 * 이와 같이 상하좌우로 후보가 되지 못하는 블록들을 소거합니다. 그러면 정답만이 남게 되고 이를 구현하여 문제를 해결하였습니다.
 *
 * */