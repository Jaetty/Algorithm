import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static Queue<int[]> candidates, temp;
    static int dx[] = {-1,1,0,0}, dy[]={0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        candidates = new ArrayDeque<>();
        temp = new ArrayDeque<>();

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j]!=0 && candidates.isEmpty()) candidates.add(new int[]{i,j});
            }
        }

        int year = 0;

        while(true){

            int count = 0;
            visited = new boolean[N][M];
            temp = new ArrayDeque<>();

            if(candidates.isEmpty()){
                System.out.println(0);
                return;
            }
            else{

                while(!candidates.isEmpty() && count < 2){
                    int[] rc = candidates.poll();
                    if(!visited[rc[0]][rc[1]]){
                        bfs(rc);
                        count++;
                    }
                }

                if(count>=2){
                    System.out.println(year);
                    return;
                }

                candidates = temp;
            }

            year++;

        }


    }

    static void bfs(int[] input){

        Queue<int[]> queue = new ArrayDeque<>();
        visited[input[0]][input[1]] = true;
        queue.add(input);

        while(!queue.isEmpty()){

            int[] rc = queue.poll();
            int count = 0;

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(map[nr][nc]<=0 && !visited[nr][nc]){
                    count++;
                }
                else if(map[nr][nc]>0 && !visited[nr][nc]){
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }

            map[rc[0]][rc[1]] -= count;
            if(map[rc[0]][rc[1]]>0) temp.add(rc);

        }
    }

}

/*
* 그래프 이론 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 각 행과 열의 끝에는 0이 주어진다. 즉 배열은 0 값으로 둘러쌓여있다.
* 2. 특정 노드에서 인접한 값이 0인 갯수 만큼 특정 노드의 값이 줄어든다. (4방향이 0이면 4가 줄어든다.)
* 3. 빙산이 분할되면 걸린 년도를 출력하고 분할되지 않고 모두 녹으면 0을 출력하고 종료한다.
* 4. N과 M은 최대 300이며 빙산 모두 합쳐 최대 10000개 주어지고 가장 큰 값은 10이다.
*
* 우선 1번 포인트로 절대 녹지 않는 경우는 없다는 것과 BFS 탐색 중 밖으로 배열 밖으로 탐색할 일이 없다는 것을 알 수 있습니다.
*
* 2번 포인트에서 단순히 탐색 뿐만 아닌 주위에 0값을 카운트하여 인접 노드 탐색 이후 0의 갯수만큼 현재 노드의 값을 차감시켜야한다는 점을 알 수 있습니다.
* 주의해야하는 점은 년도 수가 지나야 완전히 녹는다는 점 입니다. 즉 아직 1년 차에서 어떤 A 노드가 0이 되었는데 바로 인접했던 노드에서 다시 탐색 시 A가 0이라고 해서 카운트하게 해서는 안됩니다.
*
* 3번 포인트가 원하는 바는 BFS를 통해서 각 빙산 즉 0이 아닌 값을 가진 노드들이 모두 연결되어있는지를 묻고 있습니다.
* 만약 BFS가 두번 수행되게 된다면 그것은 한 덩어리가 아님을 알 수 있습니다.
*
* 4번 포인트에서 알 수 있는 점은 실제로 탐색하면 되는 구간은 최대 10000개라는 점 입니다. 즉 계속해서 300X300의 탐색을 하면서 아직 방문하지 않은 노드를 찾는 것 보단
* 0이 아닌 값들의 위치를 미리 기억해두고 그것들만 꺼내서 탐색하게 하면 시간을 절약할 수 있습니다.
*
* 위 4가지 포인트를 유념하면서 문제를 구현하게되면 쉽게 풀이할 수 있는 그래프 탐색 문제입니다.
*
*
* */