import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static int[][] map;
    static Integer[][] visited;

    static int[] dx = {0,0,-1,1};
    static int[] dy = {-1,1,0,0};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        visited = new Integer[N][N];

        for(int i=0; i<N; i++){
            String str = br.readLine();
            for(int j=0; j<N; j++){
                map[i][j] = str.charAt(j)-'0';
            }
        }

        bfs();

        System.out.println(visited[N-1][N-1]);


    }

    static void bfs(){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {0,0,0});
        visited[0][0] = 0;

        while (!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr < 0 || nc <0 || nr >=N || nc >= N) continue;

                if(map[nr][nc]==0){
                    if(visited[nr][nc] != null && visited[nr][nc] <= rc[2]+1) continue;
                    visited[nr][nc] = rc[2]+1;
                    queue.add(new int[] {nr, nc, rc[2]+1});
                }
                else{
                    if(visited[nr][nc]!=null && visited[nr][nc] <= rc[2]) continue;
                    visited[nr][nc] = rc[2];
                    queue.add(new int[] {nr, nc, rc[2]});
                }
            }
        }
    }
}

/*
* 그래프 이론 문제
*
* 해당 문제는 그래프 이론을 이용하여 풀이할 수 있는 문제로 문제의 포인트는 다음과 같습니다.
* 1. 입력으로 주어지는 배열의 값에서 1은 비용 없이 지나갈 수 있는 노드이고 0은 지나가기 위해 비용(1)이 필요합니다.
* 2. BFS는 가장 먼저 어떤 배열에 도착하는 그래프 탐색입니다.
* 3. 방문의 여부가 아닌 얼마만큼 비용을 적게 해당 노드에 도착했는지 기억합니다.
*
* 이 두 가지 포인트로 문제를 풀이하려고 하면 다음과 같습니다.
* 먼저 BFS를 이용하여 그래프 탐색을 수행합니다. 기초적인 BFS는 이미 탐색한 노드에 다시 들리지 않도록 java에선 boolean으로 도착한 노드를 구분합니다.
* 하지만 이 문제는 가장 비용을 적게 써서 도착지점에 도착하는 값을 원하고 있으니 boolean이 아닌 count를 이용하여 얼마만큼 비용을 사용하며 해당 노드에 도착했는지 기억하게 합니다.
* 그리고 노드를 탐색하는 과정에서 만약 이미 탐색한 노드의 위치까지 오는데 들었던 비용 값이 지금 탐색 중에 거쳐온 비용보다 낮을 때만 비용 값을 최소값으로 바꾸고 탐색을 지속하게 만들어줍니다.
*
* 다른 방법으로 풀이를 진행하지는 않았지만 다익스트라, 0-1 BFS로도 풀이가 가능하다고 생각합니다.
* BFS를 사용한다면 위와 같은 풀이 방법으로 풀이가 가능합니다.
*
*
* */