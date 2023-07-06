import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int N,M;
    static int[][] map;
    static int[][] arr;
    static ArrayList<int[]> list;
    static int[] value;
    static int[] dr = {1,-1,0,0};
    static int[] dc = {0,0,-1,1};


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        arr = new int[N][M];
        list = new ArrayList<>();

        value = new int[1000001];

        for(int i=0; i<N; i++){
            String str = br.readLine();
            for(int j=0; j<M; j++){
                map[i][j] = (int)str.charAt(j)-'0';
                if(map[i][j]==1) list.add(new int[]{i,j});
            }
        }

        int index = 1;

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(map[i][j]==0 && arr[i][j]==0){
                    bfs(index++, i, j);
                }
            }
        }

        for(int[] var : list){

            Set<Integer> set = new HashSet<>();

            for(int i=0; i<4; i++){

                int nr = var[0] + dr[i];
                int nc = var[1] + dc[i];

                if(nr < 0 || nc <0 || nr>=N || nc>=M || map[nr][nc]>0 || set.contains(arr[nr][nc])) continue;

                map[var[0]][var[1]] += value[arr[nr][nc]];
                set.add(arr[nr][nc]);
            }

        }

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                sb.append(map[i][j]%10);
            }
            sb.append("\n");
        }

        System.out.println(sb);

    }

    static void bfs(int index, int r, int c){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r,c});
        int count = 1;
        arr[r][c] = index;

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dr[i];
                int nc = rc[1] + dc[i];

                if(nr < 0 || nc <0 || nr>=N || nc>=M || arr[nr][nc]>0 || map[nr][nc]==1) continue;

                arr[nr][nc] = index;
                count++;

                queue.add(new int[]{nr,nc});
            }

        }

        value[index] = count;

    }


}
/*
 * 그래프 탐색 문제
 * 이 문제의 포인트는 다음과 같습니다.
 * 1. 한 칸에서 다른 칸으로 이동하려면, 두 칸이 인접해야 한다. 두 칸이 변을 공유할 때, 인접하다고 한다.
 * 2. 벽을 부수고 이동할 수 있는 곳으로 만들고 부순 후 이동할 수 있는 칸의 개수 세기
 * 3. 맵 형태로 출력하되 10으로 나누고 남은 값으로 출력하기
 *
 * 1번을 통해서 알 수 있는것은 상하좌우로 움직이는 형태의 그래프 탐색을 사용하면 된다는 뜻이고
 * 2번을 통해서 알 수 있는 것은 자신의 상하좌우로 움직여서 갈 수 있는 0의 총 개수를 더해주면 된다는 뜻입니다.
 * 3번은 출력시 %10 을 해주면 된다는 점을 알 수 있습니다.
 *
 * 다음과 같은 입력 예시가 있다면
 * 2 5
 * 11001
 * 00111
 *
 * 정답은 다음과 같습니다.
 * 35003
 * 00531
 *
 * 1행의 1열같은 경우 상하좌우를 보았을 때 하단에 0이 2개 있으므로 1+=2를 해주므로 3이 나오고
 * 1행의 2열같은 경우 상하좌우를 둘러 보았을 때 오른쪽과 하단에 0이 2개씩 있으므로 1+=4가 됩니다.
 *
 * 만약 1에서부터 하나하나 상하좌우에 BFS를 적용하면 너무 비효율적이게 됩니다.
 * 그렇다면 주변에 0이 몇개 있는지 어떻게 알 수 있을까? 라고 생각이 들게 됩니다.
 * 저는 먼저 0인 애들만 BFS를 이용하여 0의 개수를 파악하여 미리 기억하게 해두자 라는 방법을 생각했습니다.
 *
 * 먼저 0이 있는곳에 BFS를 통해 0이 전체 몇개 있는지 개수를 세어줍니다.
 * 이 방법을 arr이라는 2차원 배열과 index라는 특유의 id값, value라는 id값을 가진 애들의 총 개수를 저장하는 배열을 통해 진행하였습니다.
 *
 * 위의 예시 같은 경우 0에서 BFS를 지나고 나면 위의 변수 내용이 다음과 같이 됩니다.
 *
 * arr의 값들
 * 00110
 * 22000
 *
 * value의 값 (value의 초기 크기는 최대 N과 M의 곱한값의 +1 인 => 1000*1000+1입니다.)
 * [0,2,2,0,0,0,0......0]
 *
 * 즉 arr 배열에서 0이 아닌 숫자를 value에서 조회하면 개수를 파악할 수 있습니다.
 *
 * 그리고 다음으로는 1이 있는 위치에서 상하좌우로 arr 배열의 값을 조회하여 map값에 더해주면 됩니다.
 * 여기서 주의해야할 점은 이미 조회한 값을 또 조회하지 못하게 해야합니다.
 * 예를 들면 다음과 같은 입력이 주어지면
 * 2 2
 * 00
 * 01
 *
 * arr은
 * 11
 * 10
 *
 * value는
 * [0,3,0....0]
 *
 * 정답은
 * 00
 * 04
 *
 * 하지만 조회를 중복되게 허락하면 위와 왼쪽이 같은 id값을 가지고 있는데 두번 더하여 1+3+3 으로
 * 00
 * 07
 * 과 같은 결과가 나올 수 있습니다.
 *
 * 여기서 set을 이용하여 이미 조회한 같은 index값을 만나면 무시하도록 하였습니다.
 * 여기까지가 가장 중요한 로직이고 이 로직들을 구현하기만 하면 해당 문제를 해결할 수 있습니다.
 *
 */