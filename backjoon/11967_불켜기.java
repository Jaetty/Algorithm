import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, answer;
    static boolean[][] visited, lights;
    static List<int[]> edges[][];
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        edges = new List[N+1][N+1];
        visited = new boolean[N+1][N+1];
        lights = new boolean[N+1][N+1];

        for (int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                edges[i][j] =  new ArrayList<>();
            }
        }

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            edges[x][y].add(new int[]{a, b});
        }

        answer = 1;
        bfs(1,1);

        System.out.print(answer);

    }

    static void bfs(int x, int y){

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{x, y});

        lights[1][1] = true;

        while(!q.isEmpty()){

            int[] cur = q.poll();
            if(visited[cur[0]][cur[1]]){ continue; }

            visited[cur[0]][cur[1]] = true;

            // 현재 위치에서 켤 수 있는 불켜기 작업
            for(int[] light : edges[cur[0]][cur[1]]){

                // 이미 들린 장소에 또 불켰으면 넘어가기
                if(visited[light[0]][light[1]]){ continue; }

                // 불이 최초로 켜졌으면 answer++
                if(!lights[light[0]][light[1]]){
                    answer++;
                }
                lights[light[0]][light[1]] = true;

                // 불을 켠 곳에서 4방향 탐색
                for(int i = 0; i < 4; i++){

                    int nx = light[0] + dx[i];
                    int ny = light[1] + dy[i];

                    if(check(nx,ny,true)){
                        // 4방향 중 인접한 곳에 (1,1)과 연결된 지점이 있다면 bfs 탐색용 queue에 넣어줌
                        q.add(new int[]{light[0], light[1]});
                        break;
                    }
                }

            }

            // 평범한 탐색 시작
            for(int i = 0; i < 4; i++){

                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(check(nx,ny,false)){
                    q.add(new int[]{nx, ny});
                }

            }
        }
    }

    static boolean check(int nx, int ny, boolean is_light){

        // 범위 초과거나 애초에 불이 안켜져있을 때
        if(nx < 1 || ny < 1 || nx > N || ny > N || !lights[nx][ny]){
            return false;
        }

        // 만약 불이 켜졌을 때의 탐색 중이라면
        if(is_light){
            // 인접한 지점이 (1,1)과 연결되어있는지 확인.
            if(!visited[nx][ny]) return false;
            return true;
        }
        // 평범한 bfs의 경우
        else{
            // 이미 들렸던 곳인지만 확인해서 가지치기.
            if(visited[nx][ny]) return false;
            return true;
        }

    }

}

/*

11967 불켜기

그래프 이론 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. 처음엔 (1,1) 위치에서 시작하여 bfs가 진행된다.
2-1. 먼저 (1,1)에서 불을 켜고 불은 킨 곳에서 상하좌우에 (1,1)과 이어진 지점인지 파악한다.
2-2. 만약, (1,1)과 연결된 지점이었다면 queue에 넣어서 다음 탐색 지점으로 사용한다.
2-3. 만약, (1,1)과 연결된 지점이 아니라면 그냥 불만 켜고 넘어간다.
3. 현재 위치에서 상하좌우로 탐색을 시작하고, 탐색 조건이 끝날 때 까지 2번부터 3번까지 반복.

해당 문제에서 가장 고민하게 되는 부분은 이미 bfs로 탐색이 종료된 지점과 한칸 차이나는 위치에 불이 켜졌을 때,
이 구간을 다시 탐색을 하도록 만드는 방법이 무엇인가 입니다.

워낙 m이 크고 n도 100*100이라 무작정 bfs를 다시 반복하면 시간 초과를 벗어날 수 없는 구조이기 때문입니다.

이에 대한 해답으로 떠올린 방법 핵심 로직의 2번 입니다.
불이 켜진 지점에서 4방향 탐색을 따로 하게 만들어준다면 자연스럽게 탐색 대상으로 추가할 수 있게 됩니다.

(이를 위해 불을 킨 위치를 기억하는 배열 lights와 (1,1)에서부터 출발하여 도달한 위치를 기억하는 배열 visited를 따로 만들었습니다.)

위의 핵심 로직을 바탕으로 코드를 구현하게 되면 문제를 해결할 수 있습니다.

*/