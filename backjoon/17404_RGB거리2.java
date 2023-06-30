import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N;
    static Integer[][][] dp;
    static int[][] arr;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        dp = new Integer[3][N+1][3];
        arr = new int[N+1][3];

        for(int i=0; i<3; i++){
            dp[i][0][0] = 0;
            dp[i][0][1] = 0;
            dp[i][0][2] = 0;
        }

        for(int i=1; i<=N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
            arr[i][2] = Integer.parseInt(st.nextToken());
        }

        for(int k=0; k<3; k++){
            dp[k][1][0] = arr[1][0];
            dp[k][1][1] = arr[1][1];
            dp[k][1][2] = arr[1][2];
        }

        int answer = Integer.MAX_VALUE;

        for(int k=0; k<3; k++){
            for(int i=2; i<=N; i++){
                if(i==2){
                    dp[k][i][k] = 100000000;
                    for(int j=k+1; j<k+3; j++){
                        dp[k][i][j%3] = arr[i][j%3] + arr[i-1][k];
                    }
                }
                else {
                    for(int j=0; j<3; j++){
                        dp[k][i][j] = Math.min(dp[k][i-1][(j+1)%3] + arr[i][j], dp[k][i-1][(j+2)%3] + arr[i][j]);
                    }
                }

                if(i==N){
                    for(int j=0; j<3; j++){
                        if(j==k) continue;
                        answer = Math.min(answer, dp[k][i][j]);
                    }
                }
            }
        }

        System.out.println(answer);

    }

}
/*
* DP를 이용한 문제
* 이번 풀이는 1149 RGB 거리 문제를 풀었다면 이해하기 쉬운 문제입니다. 만약 1149 RGB 거리를 풀지 않으셨다면 먼저 푸는 것을 추천드립니다.
* 본 문제는 1149 RGB 거리 문제에서 한 가지 조건이 추가된 문제입니다.
* 조건은 1번에서 고른 RGB값과 N번째 RGB값이 같으면 안된다는 점입니다.
*
* 먼저 단순히 조건 없이 문제를 풀기위한 코드는 다음과 같습니다.
*   for(int j=0; j<3; j++){
*        dp[i][j] = Math.min(dp[i-1][(j+1)%3] + arr[i][j], dp[i-1][(j+2)%3] + arr[i][j]);
*   }
*
* 이 코드는 순서에 상관없이 나의 바로 전과 똑같은 값이 아닌 다른 값들을 더해서 최소의 값을 DP배열에 저장하는 코드입니다.
* 하지만 본 문제는 1번에서 고른 RGB 값과 N번째 RGB 값이 같으면 안되기 때문에 순서가 개입되었습니다.
* 그렇다면 순서를 고려하여 이 문제를 풀어야하는데 경우가 3가지 있다고 느꼈습니다.
*
* 1. R에서 시작해서 다음 집은 G or B를 선택하여 마지막에도 G or B를 선택하는 경우
* 2. G에서 시작해서 다음 집은 R or B를 선택하여 마지막에도 R or B를 선택하는 경우
* 3. B에서 시작해서 다음 집은 R or G를 선택하여 마지막에도 R or G를 선택하는 경우
*
* 그리고 좀 더 생각해보면 다음과 같이 숫자가 더해지는것을 파악할 수 있습니다.
* 예를 들어 입력이 다음과 같고 처음 고른 순서 k=0이라면
* 3
* 26 40 83
* 49 60 57
* 13 89 99
*
* 1행의 R에 해당하는 26을 골랐기 때문에 2행에서는 26에서 60과 57에 더해주고 49는 선택받지 못합니다.
* 3행부터는 앞에 행에서 덧셈이 진행 된 60, 57만을 이용해서 3행의 값들을 dp배열에 저장해줘야합니다.
*
* 이렇게 정리가 되자 구현읗 하기 위해서는 3번의 반복이 필요하다.
* 2행까지는 내가 직접 값을 더해주는 과정을 가지자.
* 3번째 행에서는 2행의 k번째 숫자를 제외하고 dp 배열에 저장시키자. (이 방법은 2행의 k번째 값을 N보다 충분히 큰 값을 넣으면 dp과정에서 무시하게 만들었습니다.)
* 그 이후의 행은 원래 점화식으로 진행하면 된다.
* 최종적으로 가장 작은 값은 각 k번째의 열 값을 고려하지 않고 가장 작은 값을 비교하여 찾으면 된다.
* 이렇게 정리할 수 있었고 이를 토대로 구현하여 문제를 풀 수 있었습니다.
*
* */