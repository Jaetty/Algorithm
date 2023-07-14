import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, zero_cnt, answer;
    static int[][] original;
    static Integer[][] map;
    static Queue<int[]> queue;
    static List<int[]> list;
    static int[] dr = {1,-1,0,0};
    static int[] dc = {0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        list = new ArrayList<>();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        original = new int[N][N];

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                int tmp = Integer.parseInt(st.nextToken());
                if(tmp==0) zero_cnt++;
                if(tmp==2) list.add(new int[] {i,j});
                original[i][j] = tmp;
            }
        }

        answer = Integer.MAX_VALUE;
        backTrack(0, 0, new ArrayList());

        if(answer==Integer.MAX_VALUE) System.out.println(-1);
        else System.out.println(answer);

    }

    static void backTrack(int depth, int index, ArrayList<int[]> arr) {

        if(depth==M) {
            map = new Integer[N][N];
            queue = new ArrayDeque<>();

            for(int[] rc : arr){
                map[rc[0]][rc[1]] = 0;
                queue.add(rc);
            }

            process();
            return;
        }

        if(index>=list.size()) return;

        arr.add(list.get(index));
        backTrack(depth+1, index+1, arr);
        arr.remove(arr.size()-1);
        backTrack(depth, index+1, arr);


    }

    static void process() {

        int zero = zero_cnt;
        int max = 0;


        while(!queue.isEmpty()) {

            int[] rc = queue.poll();

            if(map[rc[0]][rc[1]]+1>=answer) continue;

            for(int i=0; i<4; i++) {
                int nr = rc[0] + dr[i];
                int nc = rc[1] + dc[i];

                if(nr <0 || nc <0|| nr>=N || nc>=N || original[nr][nc]==1) continue;

                if(map[nr][nc]!=null) continue;

                if(map[nr][nc]==null && original[nr][nc]==0) zero--;

                map[nr][nc] = map[rc[0]][rc[1]]+1;
                if(original[nr][nc]==0) max = Math.max(map[nr][nc],max);
                queue.add(new int[] {nr, nc});

            }
        }
        if(zero==0) answer = Math.min(answer, max);
    }

}

/*
 * 그래프 이론 문제
 *
 * BFS와 브루트포스를 이용하여 풀 수 있는 문제입니다.
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 상하좌우 4방향으로 바이러스가 퍼지는데 같은 시간으로 퍼진다.
 * 2. 다른 바이러스와 만나면 바이러스가 활성화된다.
 * 3. M의 갯수만큼 바이러스가 초기 활성화 된다.
 * 4. 모든 0에 바이러스가 퍼지는 가장 짧은 시간을 출력한다.
 *
 * 1번 포인트를 통해서 BFS를 이용하면 된다는 사실을 알 수 있습니다.
 * 2번 4번 포인트로 탐색 중 비활성 바이러스와 만나 활성화 되는 것은 걸린 시간으로 고려하지 않아도 된다는 사실을 알 수 있습니다.
 *
 * 즉 아래의 입력값의 경우
 * 5 1
 * 0 2 2 2 2
 * 0 1 2 2 2
 * 0 1 2 2 2
 * 0 1 2 2 2
 * 0 1 2 2 2
 * 결과는 5가 나오게 됩니다.
 *
 * 3번을 통해 모든 경우의 수 만큼 BFS를 실행해줘야하는 것을 알 수 있습니다.
 *
 * 저같은 경우 backTrack이라는 재귀 함수를 이용하여 M개 만큼의 모든 경우의 수를 돌 수 있도록 하였습니다.
 * 그리고 각 경우의 수에서 BFS를 사용하여 BFS 결과로 가장 오래 걸린 시간 즉 마지막 0에 도달한 시간과 정답으로 넣어놓은 변수의 시간 중 작은 값으로 갱신해줍니다.
 * 이를 통해 문제를 해결할 수 있습니다.
 *
 * */