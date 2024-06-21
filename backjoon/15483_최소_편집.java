import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));

        char[] left = br.readLine().toCharArray();
        char[] right = br.readLine().toCharArray();

        int[][] dp = new int[left.length+1][right.length+1];

        for(int i=0; i<=right.length; i++){
            dp[0][i] = i;
        }

        for(int i=0; i<=left.length; i++){
            dp[i][0] = i;
        }

        for(int i=1; i<=left.length; i++){
            for(int j=1; j<=right.length; j++){

                if(left[i-1]==right[j-1]){
                    dp[i][j] = dp[i-1][j-1];
                }
                else{
                    dp[i][j] = Math.min(Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1])+1;
                }
            }
        }

        System.out.println(dp[left.length][right.length]);

    }

}

/*
 * 다이나믹 프로그래밍 문제
 *
 * @@@ 해당 문제는 풀이에 실패하여 해답을 참고하였습니다! @@@
 * 해당 문제는 9521 LCS를 응용해야 풀이할 수 있습니다.
 *
 * 문제의 핵심은 다음과 같은 2차원 배열을 만든 후에
 *     a b c
 *   0 1 2 3
 * c 1 x x x
 * a 2 x x x
 *
 * 각각 한 입력으로 다른 입력의 부분 문자열을 만들 수 있을 경우를 계산해보는 것 입니다.
 * 예를 들어 ca와 abc를 사용하면
 * 먼저 c로 a를 만들 때 필요한 연산 수, ab를 만들 때 수, abc를 만들 때 필요한 수를 알아냅니다.
 * ca로 a를 만들 때, ab를 만들 때, abc를 만들 때를 계산해보면 아래와 같습니다.
 *
 *     a b c
 *   0 1 2 3
 * c 1 1 2 2
 * a 2 1 2 3
 *
 * 여기서 점화식을 알 수 있는 점은
 * 1. left의 마지막 문자(c,a)와 right의 마지막 문자가 같다면 dp[i-1][j-1]과 같다는 점
 * 2. 마지막 문자가 서로 다르다면 dp[i-1][j-1], dp[i-1][j], dp[i][j-1] 중 가장 작은 값 +1이라는 점입니다.
 *
 *
 * */