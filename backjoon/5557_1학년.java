import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long[][] dp = new long[N][21];

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] input = new int[N];

        for(int i=0; i<N; i++){
            input[i] = Integer.parseInt(st.nextToken());
        }

        dp[0][input[0]] = 1;

        for(int i=1; i<N-1; i++){

            for(int j=0; j<=20; j++){

                if(dp[i-1][j]>0){

                    int minus = j-input[i];
                    int plus = j+input[i];

                    if(minus >= 0){
                        dp[i][minus] += dp[i-1][j];
                    }
                    if(plus <= 20){
                        dp[i][plus] += dp[i-1][j];
                    }
                }
            }
        }

        System.out.print(dp[N-2][input[N-1]]);

    }

}

/*
 * 5557 1학년
 *
 * 다이나믹 프로그래밍 문제
 *
 * 문제의 핵심 포인트는 다음과 같습니다.
 * 1. + - 연산만이 있다.
 * 2. 음수의 개념과 20 초과의 값을 모른다.
 * 3. 최대 long형의 MAX_VALUE보다 작다.
 *
 * 입력으로 8 2 6이 주어졌다고 해보겠습니다.
 *
 * 8에서 파생될 수 있는 경우는 8-2, 8+2입니다. 즉 8에서 +-2는 각각 10, 6이 될 수 있고 현재까지 하나의 경우의 수가 있습니다.
 * 를 배열 형태로 나타내면 다음과 같습니다.
 *
 *  0 1 2 3 4 5 6 7 8 9 ... 20
 * [0,0,0,0,0,0,0,0,1,0,.....0]
 *                 / \
 *               /    \
 * [0,0,0,0,0,0,1,0,0,0,1....0]
 *
 * 이 배열을 응용해서 [N][21]까지의 배열을 제작하여 20이 넘지 않고, 0미만이 아닌 경우 계속 파생된 값을 누적해주면서 기억해주면
 * 모든 경우의 수를 따지지 않아도 메모이제이션을 활용한 최대 경우의 수를 구할 수 있게 됩니다.
 *
 *
  */