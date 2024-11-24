import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static String A, B;
    static int[][] dp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        A = br.readLine();
        B = br.readLine();

        int answer = 0;
        dp = new int[A.length()+1][B.length()+1];

        for(int i=0; i<A.length(); i++){

            for(int j=0; j<B.length(); j++){

                if(A.charAt(i)==B.charAt(j)){
                    dp[i+1][j+1] = dp[i][j]+1;
                    answer = Math.max(dp[i+1][j+1], answer);
                }

            }
        }
        System.out.print(answer);
    }
}

/*
 * 다이나믹 프로그래밍 문제
 *
 * 해당 문제는 단순히 비교만 하게 되면 최대 길이가 4000이므로 시간초과를 벗어날 수 없습니다.
 * 그러므로 DP를 이용하여 문제를 해결해야합니다.
 *
 * ABCD DBCA라는 문자열이 주어진다고 해보겠습니다.
 * 저희는 단순히 보면 BC가 겹친다는 것을 금방 알 수 있지만 이걸 브루트포스를 적용할 경우 A부터 시작해서 D가 될때 까지 모든 경우를 비교해야합니다.
 * 하지만 이를 한번 이차원 배열 형태로 바꿔서 비교해보도록 해봅시다. 다음과 같은 형태가 될 것입니다.
 *
 *      D   B   C   A
 * 0    0   0   0   0
 * A    0   0   0   1
 * B    0   1   0   0
 * C    0   0   2   0
 * D    1   0   0   0
 *
 * 여기서 문자가 서로 같을 경우 +1을 표시하게 만든 것을 확인할 수 있습니다.
 * 그런데 이때 C의 경우 왼쪽 대각선 위의 결과에 +1을 한 것과 같은 결과를 볼 수 있습니다.
 *
 * 결국 dp의 점화식 공식은 dp[i][j] = dp[i-1][j-1] + 1 과 같다는 점을 확인할 수 있습니다.
 * 풀이에서는 dp의 첫번째 비어있는 공간을 만들어야해서 dp[i+1][j+1] = dp[i][j] + 1로 만들어줬습니다.
 * 이 점화식만 찾으면 이에 맞춰서 수행만 하면 풀이할 수 있는 문제입니다.
 *
 * */