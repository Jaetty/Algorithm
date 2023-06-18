import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N,answer1,answer2;
    static int[][] arr;
    static Integer[][] max, min;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        arr = new int[N][3];
        min = new Integer[N+1][3];
        max = new Integer[N+1][3];

        for(int i=0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
            arr[i][2] = Integer.parseInt(st.nextToken());

            max[i+1][0] = max[i+1][1] = max[i+1][2] = 0;

            min[i+1][0] = min[i+1][1] = min[i+1][2] = Integer.MAX_VALUE;

        }

        min[0][0] = max[0][0] = 0;
        min[0][1] = max[0][1] = 0;
        min[0][2] = max[0][2] = 0;

        findMax();
        findMin();

        System.out.println(answer1 + " " + answer2);

    }

    static void findMax(){

        for(int i=0; i<N; i++){
            for(int j=0; j<3; j++){

                if(j==0){
                    max[i+1][0] = Math.max(max[i+1][0], max[i][j]+arr[i][0]);
                    max[i+1][1] = Math.max(max[i+1][1], max[i][j]+arr[i][1]);
                }
                else if(j==1){
                    max[i+1][0] = Math.max(max[i+1][0], max[i][j]+arr[i][0]);
                    max[i+1][1] = Math.max(max[i+1][1], max[i][j]+arr[i][1]);
                    max[i+1][2] = Math.max(max[i+1][2], max[i][j]+arr[i][2]);
                }
                else{
                    max[i+1][1] = Math.max(max[i+1][1], max[i][j]+arr[i][1]);
                    max[i+1][2] = Math.max(max[i+1][2], max[i][j]+arr[i][2]);
                }
            }
        }
        answer1 = Math.max(max[N][0], Math.max(max[N][1], max[N][2]));
    }

    static void findMin(){

        for(int i=0; i<N; i++){
            for(int j=0; j<3; j++){

                if(j==0){
                    min[i+1][0] = Math.min(min[i+1][0], min[i][j]+arr[i][0]);
                    min[i+1][1] = Math.min(min[i+1][1], min[i][j]+arr[i][1]);
                }
                else if(j==1){
                    min[i+1][0] = Math.min(min[i+1][0], min[i][j]+arr[i][0]);
                    min[i+1][1] = Math.min(min[i+1][1], min[i][j]+arr[i][1]);
                    min[i+1][2] = Math.min(min[i+1][2], min[i][j]+arr[i][2]);
                }
                else{
                    min[i+1][1] = Math.min(min[i+1][1], min[i][j]+arr[i][1]);
                    min[i+1][2] = Math.min(min[i+1][2], min[i][j]+arr[i][2]);
                }
            }
        }
        answer2 = Math.min(min[N][0], Math.min(min[N][1], min[N][2]));
    }

}
/*
*
* DP 문제로 이것보다 약간 쉬운 문제로는 실버 1의 [1149 RGB거리] 문제가 있다.
* 문제를 DFS로 풀려고할 시 반드시 시간초과가 발생한다. 이 문제는 DP를 통해 해결할 수 있다.
* max값을 넣을 2차원 배열 하나와 min값을 넣을 2차원 배열을 N+1크기로 만든다.
*
* 점화식을 다음과 같이 세우고 구현해준다.
* dp[i][0 or 1 or 2] = min or max( (dp[i][0 or 1 or 2]), (dp[i-1][0 or 1 or 2] + arr[i-1][0 or 1 or 2]) );
*
* 이 점화식을 토대로 구현한 부분이 findMax()와 findMin()이다.
*
* 결국 dp용 배열의 마지막 구간에서 가장 큰 값(answer1)과 가장 작은 값(answer2)을 찾아서 출력해주면된다.
*
* */