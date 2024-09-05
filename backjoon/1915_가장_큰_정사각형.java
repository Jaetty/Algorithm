import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static int[][] map, dp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N+1][M+1];
        dp = new int[N+1][M+1];

        for(int i=1; i<=N; i++){
            String str = br.readLine();
            for(int j=1; j<=M; j++){
                map[i][j] = str.charAt(j-1)-'0';
            }
        }

        int answer = 0;

        for(int i=1; i<=N; i++){
            for(int j=1; j<=M; j++){
                if(map[i][j]!=0) dp[i][j] = Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]))+1;
                answer = Math.max(answer, dp[i][j]);
            }
        }

        System.out.println(answer*answer);

    }

}
/*
 * 다이나믹 프로그래밍 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 배열의 행과 열의 크기 n,m의 크기는 (1 <= n,m <= 1000)
 * 2. 2차원 배열 중 1이 표기된 것으로 만들 수 있는 가장 큰 정사각형을 찾기
 *
 * 1번 포인트를 통해 단순한 구현을 통해서는 문제를 풀이할 수 없다는 점을 파악할 수 있습니다.
 * 이때는 정사각형이 성립하는 규칙을 찾아야합니다.
 *
 * 저는 다음과 같이 그림을 그려 규칙을 찾았습니다.
 *
 * 배열 중 1이 표기된 것은 정사각형의 가장 작은 1*1 크기의 정사각형입니다.
 * 이게 다음과 같은 형태로 모여야 2 * 2 형태의 정사각형이 될 것입니다.
 * 0 0 0
 * 0 1 1
 * 0 1 1
 *
 * 맨 아래, 오른쪽의 1을 보면 왼쪽, 왼쪽 위, 위 방향이 1로 둘러져있습니다.
 * 이를 dp 배열로 나타내면 다음과 같습니다.
 * 0 0 0
 * 0 1 1
 * 0 1 2
 *
 * 이제 하나를 더 그려서 3*3 배열을 그려보면 아래와 같습니다.
 * 0 0 0 0
 * 0 1 1 1
 * 0 1 1 1
 * 0 1 1 1
 *
 * 이를 dp 배열로 나타내면 다음과 같습니다.
 * 0 0 0 0
 * 0 1 1 1
 * 0 1 2 2
 * 0 1 2 3
 *
 * 그러면 보이는 규칙이 1이 있을 경우 나의 좌상, 좌, 상의 위치에서 가장 작은 값을 가져와서 +1을 하면 가장 큰 정사각형의 변의 길이를 알 수 있습니다.
 * 즉 점화식은 map[i][j]==1 일 때, dp[i][j] = min(dp[i-1][j-1], dp[i][j-1], dp[i-1][j]) +1 이라는 점을 알 수 있습니다.
 * 이를 통해 가장 큰 정사각형의 넓이를 구할 수 있으며 코드로 구현하면 풀이할 수 있습니다.
 *
 *
 * */