import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, MIN, MAX, answer;
    static int[][] map;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};
    static int[][] queue;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        MIN = Integer.MAX_VALUE;

        StringTokenizer st;

        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                MAX = Math.max(MAX,map[i][j]);
                MIN = Math.min(MIN,map[i][j]);
            }
        }
        queue = new int[N * N][2];
        answer = MAX;
        binary();

        System.out.println(answer);

    }

    static void binary() {
        int left = 0, right = MAX - MIN;
        int mid;

        answer = MAX - MIN;

        while (left <= right) {
            mid = (left + right) / 2;

            if (isPossible(mid)) {
                answer = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
    }

    static boolean isPossible(int diff) {
        for (int min = MIN; min + diff <= MAX; min++) {
            int max = min + diff;

            if (map[0][0] < min || map[0][0] > max) continue;

            if (bfs(min, max)) {
                return true;
            }
        }
        return false;
    }

    static boolean bfs(int minLimit, int maxLimit) {

        boolean[][] visited = new boolean[N][N];
        int poll = 0, add = 0;

        visited[0][0] = true;
        queue[add][0] = 0;
        queue[add++][1] = 0;

        while (poll < add) {
            int[] cur = queue[poll++];

            if (cur[0] == N - 1 && cur[1] == N - 1) {
                return true;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];

                if (nr < 0 || nc < 0 || nr >= N || nc >= N || visited[nr][nc]) {
                    continue;
                }

                if (map[nr][nc] < minLimit || map[nr][nc] > maxLimit) {
                    continue;
                }

                visited[nr][nc] = true;
                queue[add][0] = nr;
                queue[add++][1] = nc;
            }
        }

        return false;
    }
}
/*

1981 배열에서 이동

이분 탐색 + 그래프 탐색 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. 입력으로 주어진 최댓값과 최솟값의 차이(diff)를 구한다.
2. 이 차이를 이분 탐색으로 조정해가면서 차이가 가장 작으면서도 bfs 탐색을 통해 성립하는 경우를 모두 수행해본다.

먼저 문제를 쉽게 해석하면 BFS로 (1,1) 위치에서 (N,N)위치까지 가되, 특정 조건(최소와 최대 차이)안에서만 수행했을 때 최소 차이를 출력하라 입니다.

N이 최대 100이고 N*N이므로 10000의 정점이 있습니다.
그렇다면 BFS의 비용은 O(V+E) = O(N^2 + N^2*4) = O(50000)의 반복이 필요합니다.
또한, 최대가 200이고 최소가 0이므로 최대, 최소를 모든 경우의 수로 돌리게 될 경우
200 * 200 * 50000이므로 이는 TLE를 일으킬 수 밖에 없는 시간입니다.

그렇다면, 전체 BFS 반복 횟수를 줄여야만 하는데 이때 사용할 수 있는 것이 이분 탐색 방법입니다.
모든 경우가 아닌, 최소와 최대의 정해진 차이 만큼의 최소,최댓값을 기준으로 탐색하게끔 BFS를 제한시키면 됩니다.

이렇게 할 경우, 최악의 경우의 시간복잡도는
이분 탐색 시간 => Log 200 = 대략 8.
for (int min = MIN; min + diff <= MAX; min++){..} 구간은 최악의 경우 200번 반복
BFS는 50000번 반복
즉, 8 * 200 * 50000으로 O(80,000,000)으로 아슬아슬한 시간이지만
이는 정말 말도 안되는 최악을 가정한 시간이라서 사실 BFS도 50000까지 반복하지 않고, for문도 200번은 반복이 없는 경우가 훨씬 많기 때문에
실제로는 이것보다 훨씬 더 적을 것을 예상할 수 있습니다.

이처럼 diff를 베이스로한 이분 탐색과 BFS 탐색 수행으로 문제를 풀이할 수 있습니다.

*/