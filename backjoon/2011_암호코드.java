import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = " "+br.readLine();

        int[] dp = new int[input.length()];
        dp[0] = 1;

        int before = 10;
        int curr = 0;

        for(int i=1; i<dp.length; i++){

            curr = input.charAt(i)-'0';

            dp[i] = dp[i-1];

            if(curr==0){
                if(before > 2 || before == 0){
                    System.out.println(0);
                    return;
                }
                else{
                    dp[i] = dp[i-2];
                }
            }
            else if((before ==1 && curr <= 9) || (before == 2 && curr <=6) ){
                dp[i] += dp[i-2];
            }

            dp[i] %= 1000000;
            before = curr;

        }

        System.out.println(dp[input.length()-1]);

    }
}

/*
 * 2011 암호코드
 *
 * 다이나믹 프로그래밍 문제
 *
 * 핵심 포인트는 다음과 같습니다.
 * 1. 기본 점화식은 dp[x] = dp[x-1] + dp[x-2]
 * 2. 조건문을 통해서 앞과 뒤의 문자를 합쳐본 숫자가 10과 20을 제외한 26이하라면 점화식 적용
 * 3. 현재 숫자와 앞의 숫자를 합쳤을 때, 10이나 20이 나오면 dp[x] = dp[x-2]를 적용
 * 4. 숫자가 아예 성립되지 않는 숫자일 때 (ex. 30, 40, 00 등등) 오류로 인식하고 0을 출력하고 종료.
 * 5. 숫자가 매우 커지기 때문에 각각 %1000000 을 적용.
 *
 * 해당 문제의 경우 테스트 케이스를 만들고 이를 전개해보며 규칙성을 찾았습니다.
 *
 * 가령 입력이 251123의 경우 다음과 같이 경우가 늘어납니다.
 *
 * [0] = 최초엔 아무것도 없는 공집합
 * [B]
 * [BE, Y]
 * [BEA, YA]
 * [BEAA, YAA, BEK, YK]
 * [BEAAB, YAAB, BEKB, YKB, BEKB, YAN]
 * [BEAABC, YAABC, .....]
 * ....
 *
 * 결론적으로 숫자의 증가는
 * [0, 1, 2, 2, 4, 6, 10] 이었습니다.
 *
 *
 * 그러면 여기서 규칙을 볼 수 있는게, 만약 현재 가르키는 위치 x에서 앞의 숫자와 뒤에 문자를 합친 값이 26 이하라면, dp[x] = dp[x-1] + dp[x-2] 입니다.
 * 예를 들어 위의 6은 4+2로 만들 수 있고, 10은 6+4로 만들어졌습니다.
 * 그러면 의문이 들 수 있는게 맨 처음 2가 되는건 어떻게 구현하지 인데, 사실 간단합니다. dp[0] = 1로 초기화해주면 됩니다. 즉, [1, 1, 2, 2, 4, 6, 10] 형태로 만들어주면 됩니다.
 *
 * 이제 dp[x] = dp[x-1] + dp[x-2] 이라는 점화식 하나는 찾았습니다. 그럼 숫자 '10'과 '20'을 어떻게 처리할지 고민해봐야합니다.
 *
 * 결론적으로, dp[x] = dp[x-1] + dp[x-2]로 진행하다가,
 * 10과 20이 나오는 순간 dp[x] = dp[x-2]와 같아져야합니다.
 *
 * 예를 들어 210이라는 숫자가 주어지면 정답은 1입니다.
 * 왜냐하면 BJ가 유일한 암호 해석이기 때문입니다.
 *
 * 이를 전개해보면
 *
 * [0]
 * [B]
 * [BA, U] 까지 되는데 0을 만났으니
 * [BJ] 하나로 바뀌어야합니다. 즉, dp[x] = dp[x-1] + dp[x-2]의 결과를 dp[x-1]로 되돌려주면 됩니다.
 *
 *
 * */