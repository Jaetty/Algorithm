import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static int[][][] map;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static int[][] answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 물을 1~9까지 각 칸마다 수위를 유지했다고 가정했을 때의 map 만들기
        map = new int[10][N+2][M+2];
        // 가장 높았던 물의 양 값들을 기억하게 할거임
        // +2가 있는 이유는 가장자리의 물이 빠지는 구간을 따로 두기 위함임.
        answer = new int[N+2][M+2];

        // 기본적으로 모든 칸들을 음수로 만들어줌.
        for(int i = 0; i < N+2; i++){
            for(int j = 0; j < M+2; j++){
                for (int k = 1; k <= 9; k++) {
                    map[k][i][j] = -1;
                }
            }
        }

        // 입력을 받을 때마다 각각 어떤 수위의 물을 채웠을 때의 물의 양을 기억하게 만듦
        for (int i = 1; i <= N; i++) {

            String input = br.readLine();

            for (int j = 1; j <= M; j++) {

                int val = input.charAt(j-1) - '0';

                for (int k = 1; k <= 9; k++) {
                    map[k][i][j] = val-k;
                }
            }
        }

        for(int waterLevel = 1; waterLevel <= 9; waterLevel++){
            setAnswer(waterLevel);
        }

        int sum = 0;

        for(int i = 1; i < N+2; i++){
            for(int j = 1; j < M+2; j++){
                sum += answer[i][j];
            }
        }

        System.out.print(sum);

    }

    // 벽과 땅으로 판명된 것을 제외하고 순수하게 갇혀있는 물들의 수위에서 가장 큰 값을 answer에 갱신해줌
    static void setAnswer(int waterLevel){

        boolean[][] wall = wallSetting(waterLevel);

        for(int i = 1; i < N+2; i++){
            for(int j = 1; j < M+2; j++){

                if(wall[i][j] || map[waterLevel][i][j] >= 0){
                    continue;
                }

                answer[i][j] = Math.max(answer[i][j], -1 * map[waterLevel][i][j]);

            }
        }

    }

    // 땅으로 물이 빠지거나 벽이 있는 즉, 수영장이 성립할 수 없는 구간을 구함.
    static boolean[][] wallSetting(int waterLevel){

        boolean[][] result = new boolean[N+2][M+2];

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0,0});
        result[0][0] = true;

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dr[i];
                int nc = rc[1] + dc[i];

                if (check(nr,nc) || map[waterLevel][nr][nc] >= 0 || result[nr][nc]) continue;
                result[nr][nc] = true;

                queue.add(new int[]{nr,nc});
            }

        }
        return result;
    }

    static boolean check(int nr, int nc){

        if(nr < 0 || nc < 0 || nr >= N+2 || nc >= M+2){
            return true;
        }
        return false;
    }

}

/*

1113 수영장 만들기

그래프 이론 + 구현 문제

해당 문제는 구현 문제이기 때문에 다양한 풀이가 존재할 것으로 예상됩니다.
그러므로 이 풀이에서는 저는 어떻게 이 문제를 풀이했는지로 로직을 설명하도록 하겠습니다.

먼저 저는 문제를 다음과 같이 접근하였습니다.
1. N * M의 구간의 바깥은 물이 닿으면 없어지는 땅이다.
2. 수영장이란 이러한 땅과의 접촉없이 완벽하게 직육면체의 벽으로 가둬진 곳을 뜻한다.
3. 그렇다면, 전체 물의 수위를 X로 설정했을 때 땅과 이어지는 칸을 모두 구하면 이를 제외한 구간은 다 '수영장'이 아닐까?
4. 그렇다면, 전체 수위를 1~9까지 설정해보면서 '수영장'의 깊이가 가장 깊었던 값들을 기억하면 되지 않을까?
(여기서 수위는 어떤 구간의 순수한 물의 높이를 뜻하며 직육면체 높이 + 부족한 물의 양으로 수위가 정해집니다.
가령, 직육면체 높이가 1이고 목표 수위가 3이면 필요한 물의 양은 2가 됩니다.)

위의 아이디어를 바탕으로 기본적인 로직을 짜보았고 이를 예제에 적용해보았습니다.
다음의 예제의 경우,
3 5
16661
61116
16661

위 입력에서 '땅'을 추가해서 만들면
0000000
0166610
0611160
0166610
0000000

그리고 전체 수위가 2라고 한다면 땅의 맨 왼쪽 위의 구간(0,0)에서 bfs를 적용하여 직육면체의 높이가 2 이하인 구간은 다 땅이 됩니다.
0000000
0066600
0611160
0066600
0000000

그리고 6과 같은 경우 땅은 아니지만 땅과 접촉했으니 수영장이 되진 못합니다.
그러니까 땅과의 접촉이 일절 없는, 자신보다 큰 벽으로 둘러쌓인 가운데 3곳이 수영장이 됩니다.
그리고 수위가 2이므로 이 경우의 수영장에 들어간 물의 양은 3이 됩니다.

이제 전체 수위를 7로 하여 (0,0)부터 bfs를 한다면??
bfs의 조건이 직육면체의 높이가 6이하면 통과가 되므로.. 6의 벽을 넘어서 모든 구간이 땅이 될 것입니다.
0000000
0000000
0000000
0000000
0000000

그렇다면 전체 수위를 6로 설정한다면?
아래와 같이 땅은 6의 벽을 넘지 못하게 되고 3곳의 수영장 형성됩니다. 수위가 6이므로 6-1 = 5, 5만큼의 물이 3개가 있게 되는 것 입니다.
0000000
0066600
0611160
0066600
0000000

그렇다면, 제가 여기서 추가해야할 것은 수위를 무엇으로 지정했을 때 어떤 칸에 가장 많은 물의 양이 있었는지를 기억하는 것이었습니다.
이를 위해 answer[N][M]을 만들었고, 수위를 1~9까지 바꿔보면서 가장 수영장의 물이 많은 경우를 기억하게 만들었습니다.

저는 위와 같은 아이디어를  모든 예제 입력에 적용되는지 확인하고 난 후, 이를 바탕으로 코드를 짜서 풀이하였습니다.

*/