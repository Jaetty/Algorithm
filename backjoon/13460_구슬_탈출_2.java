import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, id;
    static char[][] map;

    // 상 좌 하 우 +2 %2를 통해 반대 방향을 가르키도록
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};

    static int[] red;
    static int[] blue;

    static Queue<Node> queue = new ArrayDeque<>();

    static class Node{

        // row, col, 몇 번 움직였는지를 기억하는 count
        int r, c, count;
        // 무슨 색상인지 기억하는 color 변수
        char color;
        Node(int r, int c, int count, char color){
            this.r = r;
            this.c = c;
            this.count = count;
            this.color = color;
        }


    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        Node red = null, blue = null;

        id = 1;

        for(int i=0; i<N; i++){
            String temp = br.readLine();
            for(int j=0; j<M; j++){
                char var = temp.charAt(j);
                map[i][j] = var;
                if(var=='R' || var== 'B'){
                   map[i][j] = '.';
                    if(var=='R'){
                        red = new Node(i,j,0, 'R');
                    }
                    else{
                        blue = new Node(i,j,0, 'B');
                    }
                }
            }
        }

        queue.add(red);
        queue.add(blue);

        for(int i=1; i<=10; i++){

            if(bfs()){
                System.out.println(i);
                return;
            }

        }

        System.out.println(-1);



    }

    static boolean bfs(){

        int size = queue.size();

        while(size > 0){

            size -= 2;

            Node red = queue.poll();
            Node blue = queue.poll();

            for(int i=0; i<4; i++){

                Node nRed = new Node(red.r, red.c, 0, 'R');
                Node nBlue = new Node(blue.r, blue.c, 0, 'B');

                boolean redIn = false;
                boolean blueIn = false;

                while(check(nRed.r+dx[i], nRed.c+dy[i])){
                    nRed.r += dx[i];
                    nRed.c += dy[i];
                    nRed.count++;
                    if(map[nRed.r][nRed.c] == 'O'){
                        redIn = true;
                        break;
                    }
                }

                while(check(nBlue.r+dx[i], nBlue.c+dy[i])){
                    nBlue.r += dx[i];
                    nBlue.c += dy[i];
                    nBlue.count++;
                    if(map[nBlue.r][nBlue.c] == 'O'){
                        blueIn = true;
                        break;
                    }
                }

                // 한 번도 움직이지 않았다면 메모리 낭비와 시간 낭비를 방지하기 위해 queue에 넣지 말고 continue해준다.
                if(red.r == nRed.r && red.c == nRed.c && blue.r == nBlue.r && blue.c == nBlue.c) continue;
                // blue 공이 구멍에 들어가면 continue;
                if(blueIn) continue;
                // red공만 들어갔다면 return해준다.
                if(redIn) return true;

                // 만약 서로 도착한 위치가 같다면 count값으로 누가 먼저 도착했는지 확인하게 해준다.
                // 상 좌 하 우 이기 때문에 (i+2)%4를 통해서 반대 방향을 향하게 만들 수 있다.
                if(nRed.r==nBlue.r && nRed.c==nBlue.c){
                    if(nRed.count > nBlue.count){
                        nRed.r += dx[ (i+2)%4 ];
                        nRed.c += dy[ (i+2)%4 ];
                    }
                    else{
                        nBlue.r += dx[ (i+2)%4 ];
                        nBlue.c += dy[ (i+2)%4 ];
                    }
                }

                queue.add(new Node(nRed.r, nRed.c, 0, 'R'));
                queue.add(new Node(nBlue.r, nBlue.c, 0, 'B'));

            }


        }

        return false;
    }

    static boolean check(int r, int c){
        if(r>=N || c >=M || r<0 || c<0 || map[r][c]=='#') return false;
        return true;
    }

}

/*
* 그래프 이론 + 시뮬레이션 문제
*
* 해당 문제는 그래프 이론 + 시뮬레이션 문제로 포인트는 다음과 같습니다.
* 1. 한 번 기울이면 (하나의 방향으로 움직였다면) 구슬이 벽이나 다른 구슬에 막혀서 멈출 때까지 움직여야 한다.
* 2. 파란 공이 구멍에 들어가는 경우는 절대로 성공으로 카운트 하지 않는다.
* 3. N의 크기는 10 X 10 이 최대이다.
* 4. 10번 안에 들어갈 수 있는지 확인한다.
*
* 1번 포인트와 2번 포인트를 함께 생각해보면 빨간공이 먼저 구멍에 들어가고 파란공이 따라서 들어가는 경우도 있을 수 있습니다.
* 하지만 파란공은 반드시 구멍으로 들어가서는 안되기 때문에 그러한 경우는 성공으로 간주하지 않습니다.
* 그리고 3, 4번 포인트를 생각해보면 최대 크기가 10X10, 최대 횟수가 10이기 때문에 BFS를 이용하면 시간초과, 메모리 초과의 위험이 적다는 것을 알 수 있습니다.
*
* 이제 풀이 방법을 생각해보면 쉽게 Red공과 Blue공을 각각 특정 방향으로 끝까지 이동하는 BFS를 수행하면 되겠구나 하고 생각이 들게 됩니다.
* 다만 문제에서 고민할 수도 있는 부분은 만약 Red공이 오른쪽으로 갔는데 Blue공이 먼저 오른쪽 끝에 도달했을 때 어떻게 그걸 처리하지? 부분일 것 입니다.
*
* 저의 경우는 구슬의 위치를 나타내는 Node class에 count라는 변수를 만들어서 몇 번 이동했는지를 기억하게 만들었습니다.
* 그래서 서로 도착지가 같다면 어떤 구슬이 먼저 도착했는지를 count로 비교하여 count가 더 높은, 즉 나중에 도착한 구슬은 바로 한칸 뒤로 위치하게 만들어 해결하였습니다.
*
* 위의 로직을 BFS로 10번 동안 수행하게 만들고 그 안에 빨간공이 들어갔다면 몇 번째에 성공했는지를 출력하면 해결할 수 있는 문제입니다.
*
* */