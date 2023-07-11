import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

class Main{

    static int N,M;
    static int[] arr;
    static boolean[][] dp;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N];

        dp = new boolean[N][N];

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        set();
        M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++){
            if(i>0) sb.append("\n");

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken())-1;
            int b = Integer.parseInt(st.nextToken())-1;

            if(dp[a][b]) sb.append(1);
            else sb.append(0);

        }
        System.out.println(sb);

    }

    static void set(){

        for(int i=0; i<N; i++){
            dp[i][i] = true;
        }

        for(int i=0; i<N-1; i++){
            if(arr[i]==arr[i+1]) dp[i][i+1] = true;
        }

        for(int i=2; i<N; i++){
            for(int j=0; j<N-1; j++){
                if(arr[i]==arr[j] && dp[j+1][i-1] ){
                    dp[j][i] = true;
                }
            }
        }

    }

}

/*
* DP 문제
* 해당 문제는 힌트를 보았습니다.
*
* 이 문제의 아이디어는 팰린드롬이 성립하면 dp[][] 배열에 true값을 넣고 전의 dp배열을 이용하여 다음 팰린드롬을 구하자 입니다.
* 이를 해결하기 위한 포인트는 다음과 같았습니다.
* 1. 각자 길이가 1인 경우에는 true로 만들어준다.
* 2. 길이가 2일 경우 바로 다음 숫자와 같은지 확인되면 true 값을 넣는다.
* 3. 3개 이상의 길이는 맨 앞과 뒤가 같다면 맨 앞과 맨 뒤 사이에 있는 배열이 팰린드롬인지 확인합니다.
*
* 3번의 경우는 1과 2가 선 수행되어야 합니다.
* 만약 길이가 3인 배열의 팰린드롬 판단이 완료되면 4의 크기의 배열의 팰린드롬 판단을 3의 결과를 바탕으로 판단할 수 있습니다.
* 3번의 동작을 다음과 같은예제 입력을 통해 설명하겠습니다.
*
* 7
* 1 2 1 3 1 2 1
* 2
* 1 3
* 1 5
*
* 위와 같은 배열이 입력으로 주어졌을 때 1 3이 팰린드롬인지 확인해보겠습니다.
* 길이가 3이상이면 먼저 가장 앞의 숫자와 끝의 숫자가 같은지 확인합니다.
* 여기서 '1' 2 '1'이기 때문에 조건을 만족합니다.
* 그 다음 이 사이에 있는 dp 배열이 true값인지 확인해봅니다. (dp[1][1] 이 true인지 봅니다.)
* 길이가 1이면 팰린드롬이기 때문에 조건에 만족하여 1 2 1은 팰린드롬입니다.
*
* 1에서부터 5까지가 팰린드롬인지 확인해보면
* 먼저 맨 앞과 맨 뒤가 같기 때문에 첫번째 조건을 만족합니다.
* 다음 그 사이에 있는 배열이 팰린드롬인지 확인합니다. (dp[2][4]가 true인지 확인합니다.)
* dp[2][4]는 true가 아니므로 1 5 배열은 팰린드롬이 아닙니다.
*
* 이 아이디어를 구현하면 문제를 해결할 수 있습니다.
*
* */