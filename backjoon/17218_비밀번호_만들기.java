import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][] dp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str1 = br.readLine();
        String str2 = br.readLine();

        dp = new int[str1.length()+1][str2.length()+1];
        int max = 0;

        for(int i=0; i<str1.length(); i++){
            for(int j=0; j<str2.length(); j++){
                if(str1.charAt(i)==str2.charAt(j)){
                    dp[i+1][j+1] = dp[i][j]+1;
                    max = Math.max(dp[i][j]+1, max);
                }
                else dp[i+1][j+1] = Math.max(dp[i][j+1], dp[i+1][j]);
            }
        }

        int row = str1.length();
        int col = str2.length();

        StringBuilder sb =new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for(int i=row; i>=0; i--){

            if(dp[i][col]>=max) continue;

            for(int j=col; j>=0; j--){
                if(max>dp[i+1][j]){
                    stack.add(str2.charAt(j));
                    max--;
                    col = j;
                    break;
                }

            }

        }

        while(!stack.isEmpty()){
            sb.append(stack.pop());
        }

        System.out.println(sb);

    }

}

/*
 * 다이나믹 프로그래밍 알고리즘 문제
 *
 * 이와 유사한 문제는 9251 LCS 문제가 있습니다.
 *
 * 이 문제는 2차원 배열의 메모이제이션을 이용하여 문제를 해결할 수 있습니다.
 * 예를들어 다음과 같은 입력이 주어진다면
 * ACAYKP
 * CAPCAK
 *
 * 저희는 이것을 2차원 첫 입력의 길이를 N, 두번째 입력의 길이를 M이라면 int[N+1][M+1] 크기의 배열로 만들어봅니다.
 *
 * \    \   C   A   P   C   A   K
 * \    0   0   0   0   0   0   0
 * A    0   0   0   0   0   0   0
 * C    0   0   0   0   0   0   0
 * A    0   0   0   0   0   0   0
 * Y    0   0   0   0   0   0   0
 * K    0   0   0   0   0   0   0
 * P    0   0   0   0   0   0   0
 *
 * 이제 ACAYKP의 A부터 시작해서 CAPCAK와 비교해줍니다.
 * 비교하는 과정에서 row = i, col = j 위치에서 자신과 같다면 dp[i-1][j-1] +1 값을 배열에 넣어주고
 * 만약 같지 않다면 dp[i-1][j] or dp[i][j-1] 값 중 큰 값을 자신에게 넣습니다.
 * 그 결과가 다음과 같습니다.
 *
 * \    \   C   A   P   C   A   K
 * \    0   0   0   0   0   0   0
 * A    0   0   1   1   1   1   1
 * C    0   0   0   0   0   0   0
 * A    0   0   0   0   0   0   0
 * Y    0   0   0   0   0   0   0
 * K    0   0   0   0   0   0   0
 * P    0   0   0   0   0   0   0
 *
 * 이것을 C까지 반복하면 다음과 같은 결과가 나옵니다.
 *
 * \    \   C   A   P   C   A   K
 * \    0   0   0   0   0   0   0
 * A    0   0   1   1   1   1   1
 * C    0   1   1   1   2   2   2
 * A    0   0   0   0   0   0   0
 * Y    0   0   0   0   0   0   0
 * K    0   0   0   0   0   0   0
 * P    0   0   0   0   0   0   0
 *
 * 즉 dp[i-1][j-1] + 1을 하는 이유는 앞에서 나랑 값이 같을 때 앞에서 먼저 부분수열을 이뤘던 문자열에 자신을 추가한다는 뜻입니다.
 * 첫 A를 통해 A가 하나 겹치는 경우들을 적어줬습니다.
 * 그다음 C를 통해 앞에 A로 부분수열이 만들어진 경우에 자신을 더해서 "AC"의 경우를 적어준겁니다.
 *
 * 이것을 P까지 반복한 결과가 다음과 같습니다.
 *
 * \    \   C   A   P   C   A   K
 * \    0   0   0   0   0   0   0
 * A    0   0   1   1   1   1   1
 * C    0   1   1   1   2   2   2
 * A    0   1   2   2   2   3   3
 * Y    0   1   2   2   2   3   3
 * K    0   1   2   2   2   3   4
 * P    0   1   2   3   3   3   4
 *
 * 여기서 가장 큰 숫자는 4입니다. 이 4를 기억해둡시다.
 * 부분 문자열을 뽑는 방법은 역순으로 생각하시면 됩니다.
 * 가장 많이 겹친 경우인 4라는 숫자는 앞에서 앞선 경우에 +1을 해서 입니다.
 * 즉 3에서 4가 되는 순간에 문자가 같았기 때문에 3+1이 된 것입니다.
 *
 * 그렇다면 역순으로 row와 col이 4에서 3이 되었을 때 문자가 변한다는 뜻이고 그 변하기 직전에 위치에 있는 문자를 출력해주면 K를 가져올 수 있습니다.
 * 그걸 반복하면 문자열은 KCAK가 나오고 이를 거꾸로하면
 * 정답인 ACAK가 나오게 됩니다.
 *
 * 이렇게 이차원 배열을 이용한 메모이제이션으로 해당 문제를 풀이할 수 있습니다.
 *
 * */