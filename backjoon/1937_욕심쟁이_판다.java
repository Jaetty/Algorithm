import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, answer;
    static int[][] map, dp;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        dp = new int[N][N];
        answer = 1;

        StringTokenizer st;

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){

                if(dp[i][j]>0) continue;

                dp[i][j] = dfs(i,j);
            }
        }

        System.out.println(answer);

    }

    static int dfs(int r, int c){

        dp[r][c] = 1;

        for(int i=0; i<4; i++){
            int nr = r + dx[i];
            int nc = c + dy[i];

            if(nr < 0 || nc <0 || nr >=N || nc >=N ){
                continue;
            }

            if(map[nr][nc] > map[r][c]){

                if(dp[nr][nc]>1){
                    dp[r][c] = Math.max(dp[r][c], dp[nr][nc]+1);
                }else{
                    dp[r][c] = Math.max(dp[r][c], dfs(nr,nc)+1);
                }
            }
        }

        answer = Math.max(answer, dp[r][c]);
        return dp[r][c];
    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 판다는 원래 위치보다 더 많은 대나무 숲으로만 이동한다.
 * 2. N = 500이고 상,하,좌,우 한 곳으로 이동한다.
 *
 * 2번 포인트로 단순히 모든 경우를 따지는 방법으로는 시간초과를 벗어나지 못한다는 점을 알 수 있습니다.
 * 시간적으로 V = N*N, E = V*4 이므로 풀어쓰면 O((N*N) * (V+E)) 이므로 시간복잡도는 O(312,500,000,000)가 되기 때문입니다.
 * 1번은 그래프 탐색의 조건으로 사용할 수 있는 점을 알 수 있습니다.
 *
 * 1번 포인트를 통해 생각할 수 있는것은 이미 탐색이 끝난 곳이면 더이상 탐색할 필요가 없게 만들면 된다는 점을 알 수 있습니다.
 * 즉 이미 탐색이 끝난 결과에 +1을 했을 때 그 결과가 가장 큰 값을 기억해두면 됩니다.
 *
 * 예를 들어 다음과 그래프가 있다고 해보겠습니다.
 * 1 2 3 5 6
 * 1 1 4 5 7
 * 거꾸로 맨 첫줄에 6에서부터 탐색을 시작했다고 해보겠습니다. 6은 더이상 갈 곳이 없으니 1입니다.
 * 5는 6로 갈 수 있는데 6의 탐색은 이미 끝났고 6은 1의 값을 가지고 있으므로 굳이 다시 6에서 탐색할 필요가 없습니다.
 * 즉, 5는 (6의 탐색 결과) + 1 = 1+1로 2의 값을 가집니다.
 * 3은 오른쪽의 5로 갈 필요가 없습니다. (5의 탐색 결과) + 1 = 3은 5로 탐색을 했을 시의 결과입니다.
 *
 * 4는 탐색이 되지 않았으니 4로부터 탐색을 시작합니다.
 * 4->5->7로 탐색이 수행됩니다. 7은 더이상 갈 수 없으므로 1을 가집니다.
 * 다시 5는 1+1, 4는 2 +1 의 결과를 가집니다.
 * 그러면 4의 결과 값은 = 3이고 3+1을 한 경우가 3에서 오른쪽으로 갔을 때보다 결과가 크므로 3의 결과는 4가 됩니다.
 *
 * 이런식으로 수행하면 시간 복잡도는 O((N*N) + (V +E)) 이므로 O(1,500,000)만 수행하면 해결할 수 있습니다.
 *
 * 위의 문제 풀이방식을 코드로 옮기면 문제를 해결할 수 있습니다.
 *
 * */