import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static final int INF = 1000000000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[][][] dp = new int[100001][5][5];
        int index = 1;

        for(int i=0; i<100001; i++){
            for(int j=0; j<5; j++){
                for(int k=0; k<5; k++){
                    dp[i][j][k] = INF;
                }
            }
        }

        dp[0][0][0] = 0; // 최초에 양발은 0,0 위치에만 존재하므로 0,0의 점수를 0으로 만들어줍니다.

        while(st.hasMoreTokens()){
            int nextStep = Integer.parseInt(st.nextToken());
            if(nextStep==0) break;

            for(int i=0; i<5; i++){
                for(int j=0; j<5; j++){

                    if(j!=nextStep){
                        dp[index][nextStep][j] = Math.min(dp[index][nextStep][j], dp[index-1][i][j] + moving(i, nextStep));
                    }
                    if(i!=nextStep){
                        dp[index][i][nextStep] = Math.min(dp[index][i][nextStep], dp[index-1][i][j] + moving(j, nextStep));
                    }
                }
            }
            index++;

        }

        int answer = Integer.MAX_VALUE;
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                answer = Math.min(answer, dp[index-1][i][j]);
            }
        }

        System.out.println(answer);



    }

    static int moving(int cur, int dest){

        if(cur==0){
            return 2;
        }
        else if(cur==dest){
            return 1;
        }
        else if(Math.abs(dest - cur) == 2){
            return 4;
        }
        else return 3;


    }



}

/*
* 다이나믹 프로그래밍 문제
*
* 해당 문제의 경우 다이나믹 프로그래밍 문제로 3차원 배열을 이용하여 왼발과 오른발의 움직임을 메모이제이션 하였습니다.
*
* 배열 dp[100001][5][5]는 dp[최대 방향 지시 개수][왼발 위치][오른발 위치]를 나타내고 있습니다.
* 핵심 포인트는 "바로 직전에 위치해있던 오른발 왼발의 위치의 점수 + 왼발이나 오른발을 지시한 방향으로 움직일 때 얻는 점수"를 기억한다는 점 입니다.
*
* 예를 들어 다음과 같은 예시 입력이 있다면
* 1 2 0
*
* dp의 초기 내용은 dp[0][0][0]만 0 포인트를 가지고 있고 나머지는 INF값을 가지고 있습니다.
*
* 이중 for문으로 현재 왼발과 오른발이 각각 0,0에서부터 5,5까지 위치에 있을 때 왼발과 오른발을 1로 나아갈 수 있는 가장 작은 값을 기억하게 합니다.
* 그러면 맨 최초에는 둘 다 0,0에 위치해있기 때문에 dp[1][0][1] = 2, dp[1][1][0] = 2 라는 값을 가지고 나머지는 INF값을 가지게 됩니다.
*
* 다음 방향 지시값인 2를 받고 다시 현재 왼발과 오른발이 (0,0) ~ (5,5)까지 위치해있을 때 2로 나아갈 때 경우가 다음과 같이 4가지가 됩니다.
*
* 1. 현재 발 위치가 [0][1]때 왼발을 2로 움직이는 경우 => dp[2][2][1] = 4
* 2. 현재 발 위치가 [0][1]때 오른발을 2로 움직이는 경우 => dp[2][0][2] = 5
* 3. 현재 발 위치가 [1][0]때 왼발을 2로 움직이는 경우 => dp[2][2][0] = 4
* 4. 현재 발 위치가 [1][0]때 오른발을 2로 움직이는 경우 => dp[2][1][2] = 5
*
* 이렇게 현재 위치에서 다음으로 왼발이나 오른발을 움직일 때 양발이 서로 겹치지않고 해당 발이 위치에 도착하는 가장 작은 값들을 기억한 후
* 마지막에 for문을 통해 그 중 가장 작은 값을 발견하면 되는 문제였습니다.
*
* 사실 이번 풀이는 읽어도 이해하기 힘드실 수 있는데 그 이유는 제가 이 문제를 풀기 위해 많이 시도를 하다가 풀이했기 때문에 저도 잘 설명을 못하기 때문인 것 같습니다;;
* 다만 풀이 중 큰 도움이 된 부분은 직접 만든 예시로 1 1 0 (정답 4)과 1 2 3 2 0 (정답 8)가 어떻게 성립하는지 생각해보는 것과
* dp[최대 방향 지시 개수][왼발 위치][오른발 위치] 형태로 최소 움직임 기억하기를 떠올린 이후에 문제 풀이가 진전이 있었어서 혹시 도움될지 몰라 참고하셨으면 합니다.
*
*
* */