import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int col, row, maxRoomSize, count, sumRoomSize;
    static int[][] map, sector;
    static int[] roomCount;
    static int[] dx = {0,-1,0,1};
    static int[] dy = {-1,0,1,0};
    static int[] dir = {1,2,4,8}; // 좌 상 우 하
    static List<Queue<int[]>> nearTheWall;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        col = Integer.parseInt(st.nextToken());
        row = Integer.parseInt(st.nextToken());
        maxRoomSize = 1;
        sumRoomSize = 1;
        count = 1;

        roomCount = new int[50*50+1];
        map = new int[row][col];
        sector = new int[row][col];

        nearTheWall = new ArrayList<>();
        nearTheWall.add(null);

        for(int i =0; i<row; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<col; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i =0; i<row; i++){
            for(int j=0; j<col; j++){
                if(sector[i][j]==0){
                    nearTheWall.add(new ArrayDeque<>());
                    sectorMaking(i, j, nearTheWall.get(count));
                    count++;
                }
            }
        }

        for(int i=1; i<count; i++){
            wallBreak(nearTheWall.get(i), i);
        }


        System.out.println(count-1);
        System.out.println(maxRoomSize);
        System.out.println(sumRoomSize);



    }

    static void sectorMaking(int r, int c, Queue<int[]> nearWall){

        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[row][col];
        sector[r][c] = count;
        queue.add(new int[] {r,c});
        int size = 1;

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){
                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(check(nr,nc) || sector[nr][nc]==count) continue;

                if( map[rc[0]][rc[1]]!=0 && (map[rc[0]][rc[1]] & dir[i]) == dir[i]){
                    if(visited[rc[0]][rc[1]]) continue;
                    nearWall.add(new int[]{rc[0],rc[1]});
                    visited[rc[0]][rc[1]] = true;
                }else{
                    sector[nr][nc] = count;
                    queue.add(new int[]{nr,nc});
                    size++;
                    maxRoomSize = Math.max(maxRoomSize, size);
                }
            }
        }
        roomCount[count] = size;

    }

    public static void wallBreak(Queue<int[]> queue, int index){

        boolean[] visited = new boolean[count];
        visited[index] = true;

        while(!queue.isEmpty()){
            int[] rc = queue.poll();

            for(int i=0; i<4; i++){
                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(check(nr,nc) || visited[sector[nr][nc]]) continue;

                sumRoomSize = Math.max(sumRoomSize, roomCount[index] + roomCount[sector[nr][nc]]);
                visited[sector[nr][nc]] = true;
            }
        }

    }

    static boolean check(int nr, int nc){

        if(nr < 0 || nc < 0 || nr >= row || nc>=col) return true;
        return false;

    }
}

/*
* 그래프 이론 + 비트 마스킹 문제
*
* 해당 문제는 BFS와 비트마스킹을 이용할 수 있는 문제입니다.
* 먼저 정답으로 제출해야하는 것은 3가지이고 각각 해결방법은 다음과 같다.
* 1. 각 방의 개수
* 이 부분은 bfs가 몇 번 돌았는지 세주면 된다.
*
* 2. 각 방 중에서 가장 넓은 방의 크기
* bfs를 돌 때 새로운 좌표가 큐에 들어갈 때마다 로컬 변수의 값을 1 올려주고 최대값과 비교하여 갱신해주면 된다.
*
* 3. 벽을 하나 허물었을 때 가장 넓어지는 크기
* 이 부분은 벽과 맞닿아 있는 부분의 위치를 큐에 넣어준다.
* 1, 2과정이 끝난다면 벽을 넘었을 때 위치에 얼마만큼의 넓이가 있는지 확인해서 그 값을 현재 넓이에 더해준다.
*
* 3번의 경우는 시도해보지는 않았지만 벽을 넘은 곳에서부터 bfs를 돈다거나 하는 방법도 생각할 수도 있겠지만
* 분명 시간이 많이 걸릴 것으로 예상됩니다. 그러므로 시간을 아끼려면 벽을 넘은 새 공간이 애초에 어느정도 넓이였는지
* 기억해두고 있으면 문제를 간단히 해결할 수 있습니다.
*
* 예시 입력은 다음과 같습니다.
* 7 4
* 11 6 11 6 3 10 6
* 7 9 6 13 5 15 5
* 1 10 12 7 13 7 5
* 13 11 10 8 10 12 13
*
* 이때 sector배열의 값은 모두가 0인 4*7의 배열입니다.
*
* 저의 경우 1부터 시작하는 count값을 가지고 있고 이를 bfs를 돌 때마다 sector라는 맵에다가 표시해줍니다.
* 이때 bfs 중에는 비트 마스킹을 이용하여 벽이 있는지 없는지를 검사하는 과정을 수행합니다.
* 벽이 있다면 벽과 마주한 현재 위치를 해당하는 count의 인덱스에 속하는 큐에 넣어줍니다.
* 벽이 없는 칸이면 size라는 로컬 변수로 방의 넓이를 갱신해줍니다.
* 그리고 bfs를 종료하기 모든 방의 사이즈를 기억하는 int 배열 roomCount에 count값을 이용하여 현재 방의 넓이를 저장해둡니다.
* 그리고 bfs가 종료되면 count 값을 올려줍니다.
* 이후 위 과정이 끝나면 sector는 다음과 같이 결과 나올 것 입니다.
*
* 1 1 2 2 3 3 3
* 1 1 1 2 3 4 3
* 1 1 1 5 3 5 3
* 1 5 5 5 5 5 3
*
* 이제 벽과 마주한 위치를 모은 큐에서 값을 꺼낸 후 벽을 넘었을 때 마주하게 되는 sector에 저장된 count값으로
* roomCount[벽을 뚫고 갔을 때 위치에 적힌 count 값] + roomCount[현재 count값]으로 가장 클 때를 갱신해주면 됩니다.
* 그리고 다른 위치 값에서 벽을 통과했을 때 이미 조회한 count값을 지녔을 수 있으니 이미 조회했다면 넘어가게 만들어줍니다.
* 해당 문제는 이런식으로 풀이하여 문제를 풀 수 있었습니다.
*
* */