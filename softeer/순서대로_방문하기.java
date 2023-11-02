import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N,M, answer;
    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
    static int[][] map;

    static class Node{
        int r, c, next;
        Set<Integer> path;

        Node(){}

        Node(int r, int c, int next, int point){
            this.r = r;
            this.c = c;
            this.next = next;
            this.path = new HashSet<>();
            this.path.add(point);
        }


    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int curr = 1;
        map = new int[N+1][N+1];

        for(int i=1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=N; j++){
                map[i][j] = curr++;
                if(Integer.parseInt(st.nextToken())==1){
                    map[i][j] = 0;
                }
            }
        }

        Node start = new Node();

        for(int i=1; i<=M; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if(i==1) start = new Node(r,c,-2, -1);
            map[r][c] = -1 * i;
        }
      /*

      1 2 3
      4 5 6
      7 8 9

      1 -2 3
      4 5 -3
      -1 8 9

      다음할 일은 bfs로 모든 경우의 수를 찾아본다.

      */

        bfs(start);
        System.out.print(answer);
    }

    static void bfs(Node start){

        Queue<Node> que = new ArrayDeque<>();
        que.add(start);

        while(!que.isEmpty()){

            Node rc = que.poll();

            for(int i=0; i<4; i++){

                int nr = rc.r + dx[i];
                int nc = rc.c + dy[i];
                int next = rc.next;

                if(nr<=0 || nc <= 0 || nr>N || nc>N || map[nr][nc] == 0) continue;

                // 내가 방문한 적이 있는지
                if(rc.path.contains(map[nr][nc])) continue;

                // 내가 지금 방문하면 안되는 곳인지 확인
                if(map[nr][nc]<0){
                    // 만약 맞다면 먼저 종점인지 확인. 종점이면 answer값을 올리고 넘어간다.
                    if(map[nr][nc]==next){
                        if(Math.abs(next)==M){
                            answer++;
                            continue;
                        }
                        else{ // 종점이 아니라면 다음 순서를 적어주고 넘어간다.
                            next--;
                        }
                    }
                    else continue; // 만약 내가 지금 방문하면 안되는 장소면 그냥 넘어간다.
                }

                Node nNode = new Node(nr, nc, next, map[nr][nc]);
                Iterator it = rc.path.iterator();
                while(it.hasNext()){
                    nNode.path.add((int)it.next());
                }

                que.add(nNode);
            }

        }

    }

}

/*
* BFS 문제
* 이 문제의 포인트는 방문지점의 순서가 주어지고 이 순서대로 방문지점을 들려야한다는 점, 이미 들린 공간은 다시는 방문하지 말아야하는 점이다.
* 여러가지 방법이 있겠지만 필자가 떠올린 방법은
* 1. BFS를 실행하고 각각의 BFS 움직인 이들에게 방문했던 지점을 기록해준다.
* 2. 만약 특정 지점에 도착했다면 지금 내가 방문하는 시점이 맞는지 확인해준다.
*
* 이를 해결하기 위해 map은 우선 0 혹은 1이 아닌 다음과 같이 표시했는데
* 예제 입력이 다음과 같다면
* 3 3
* 0 0 0
* 0 0 0
* 0 0 1
* 3 1
* 1 2
* 2 3
*
* 이를 먼저 경로를 기억하기 쉽게 map에 값은 서로 겹치지 않는 id값으로 해주고 벽이 있는 구간은 0으로 표시합니다.
* map 값은 다음과 같이 만들어지게 됩니다.
* 1 2 3
* 4 5 6
* 7 8 0
*
* 여기서 지점을 나타내는 방법은 음수를 이용하여 몇 번째 지점인지 기억하게 하였습니다.
* 1 -2 3
* 4 5 -3
* -1 8 0
*
* 그럼 출발지점은 -1이 있는 곳부터 next값은 -2입니다. 즉 -1에서부터 출발하여 만약 -3에 먼저 도달한다면
* 그건 잘못된 접근으로 알고 bfs 중 큐에 넣지 않고 넘어가게 만들어줍니다.
* Node에는 set을 이용하여 지나온 경로를 기억하게 하였습니다.
* 이를 통해 해당 문제를 해결할 수 있습니다.
*
* */