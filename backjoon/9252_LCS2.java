import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

class Main{

    static int[][] dp;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str1 = br.readLine();
        String str2 = br.readLine();

        dp = new int[str1.length()+1][str2.length()+1];
        int max = 0;
        StringBuilder sb = new StringBuilder();

        for(int i=1; i<=str1.length(); i++){
            for(int j=1; j<=str2.length(); j++){

                if(str1.charAt(i-1)==str2.charAt(j-1)) dp[i][j] = dp[i-1][j-1] +1;
                else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                max = Math.max(max, dp[i][j]);
            }
        }

        sb.append(max+"\n");

        if(max==0){
            System.out.println(sb);
            return;
        }

        int count = max;
        int index = str2.length();
        char[] arr = new char[index+1];

        for(int i=str1.length(); i>0; i--){
            if(dp[i][index]==dp[i-1][index]) continue;
            for(int j=index; j>=0; j--){
                if(dp[i][j] < count){
                    arr[count] = str2.charAt(j);
                    index = j;
                    count = dp[i][j];
                    break;
                }
            }
            if(count<0) break;
        }

        for(int i=1; i<=max; i++){
            sb.append(arr[i]);
        }

        System.out.println(sb);

    }

}

/*
* DP 문제
* 해당 문제는 9521 LCS 문제를 풀면 더 쉽게 이해할 수 있습니다.
*
* 2차원 배열을 이용한 DP 문제입니다.
* 입력이 다음과 같다면
* ACAYKP
* CAPCAK
*
* dp 배열은 다음과 같이 됩니다.
*
*        C   A   P   C   A   K
*    0   0   0   0   0   0   0
* A  0   0   1   1   1   1   1
* C  0   1   1   1   2   2   2
* A  0   1   2   2   2   3   3
* Y  0   1   2   2   2   3   3
* K  0   1   2   2   2   3   4
* P  0   1   2   3   3   4   4
*
* 만약 str1의 X번째 문자와 str2의 Y번째 문자가 같다면
* dp[X][Y] = dp[X-1][Y-1] + 1이 됩니다.
* 즉 자신의 index값의 왼쪽 위의 값을 통해서 앞에 어떤 문자들이 몇개 맞았었다는 것을 가져올 수 있습니다.
*
* 그렇다면 문자를 뽑는 방법은 이것을 역순으로 풀어나가면 됩니다.
* */