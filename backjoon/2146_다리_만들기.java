import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, count, answer;
    static int[][] map, parents, visited;
    static List<List<int[]>> list;

    static int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        list = new ArrayList<>();
        list.add(new ArrayList<>());

        map = new int[N][N];
        parents = new int[N][N];
        answer = Integer.MAX_VALUE;

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        count = 1;

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(map[i][j]==1 && parents[i][j]==0){
                    parents[i][j] = count;
                    list.add(new ArrayList<>());
                    setParent(i, j);
                    count++;
                }
            }
        }

        for(int i=1; i< list.size(); i++){

            visited = new int[N][N];

            for(int[] rc : list.get(i)){
                bfs(rc[0], rc[1], i);
            }

        }

        System.out.println(answer-1);



    }

    static void setParent(int r, int c){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r,c});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr<0 || nc < 0 || nr >=N || nc >= N || parents[nr][nc] == count) continue;

                if(map[nr][nc]==0){
                    list.get(count).add(new int[]{rc[0],rc[1]});
                }
                else{
                    parents[nr][nc] = count;
                    queue.add(new int[]{nr, nc});
                }

            }
        }
    }

    static void bfs(int r, int c, int check){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r,c,1});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(answer <= rc[2] || nr<0 || nc < 0 || nr >=N || nc >= N || parents[nr][nc] == check) continue;

                if(visited[nr][nc] != 0 && visited[nr][nc] <= rc[2]) continue;

                visited[nr][nc] = rc[2];

                if(parents[nr][nc] != 0){
                    answer = Math.min(rc[2], answer);
                    return;
                }
                else{
                    queue.add(new int[]{nr, nc, rc[2]+1});
                }

            }

        }


    }

}

/*
* 그래프 이론 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 육지를 구분하기 위해 BFS를 한번 수행해야합니다.
* 2. 육지와 육지를 가장 짧게 이어주려면 육지의 끝자락 위치를 기억해줍니다.
* 3. 시간초과를 막기 위해 가지치기를 하는 포인트는 현재 거리보다 이미 들린 노드가 시간이 더 낮을 때, 현재 가장 짧은 다리보다 내 거리가 더 높을 때 입니다.
*
* 1번 포인트
* 해당 문제는 어떤 한 육지에서 다른 한 육지에 가장 짧은 다리를 건설해야하므로 일단 각 육지를 구분시켜주는 작업이 필요합니다.
* BFS로 육지 어느 한 부분에서 부터 시작해서 모든 육지 부분에 구분할 수 있는 숫자나 문자를 세겨줍니다.
*
* 2번 포인트
* N<=100이므로 100*100의 모든 노드에서 굳이 BFS를 수행할 필요가 없습니다.
* 그러므로 1번 포인트에서 BFS를 수행하는 동안 바다와 인접한 육지의 위치를 기억해줍니다.
*
* 3번 포인트
* 이제 각각 육지와 바다와 인접한 부분들을 알았으니 BFS를 수행시켜줍니다.
* BFS 도중에는 visited[][] 라는 배열을 만들어 얼마만에 다른 노드로 움직였는지 기억하게 만들어줍시다.
* 이때 시간 초과를 막기 위해 특정 조건에는 BFS가 수행되지 않도록 가지치기를 해야하는데 BFS가 수행되지 않아도 되는 조건은 다음과 같습니다.
* 1. 이미 수행된 다리 건설 BFS에서 알아낸 가장 짧은 다리보다 거리가 길어지면 굳이 더이상 탐색할 필요가 없어집니다.
* 2. visited[][]를 통해서 이미 들린 곳이 있다면 가지 않게 하면 더욱 짧은 거리로 다른 육지에 도달하는 가능성이 배제될 수 있습니다.
*    그러므로 visited[][]에 현재 내가 거리를 움직여온 값이 visited에 들어간 값보다 크다면, 즉 더 늦게 어떠한 노드에 도착했다면 탐색할 필요가 없어집니다.
* 즉 위 두가지 부분을 고려해서 가지치기를 수행하면 시간초과를 벗어날 수 있습니다.
*
* 위의 3가지 포인트를 코드로 구현하게 된다면 문제를 해결할 수 있습니다.
*
*
* */