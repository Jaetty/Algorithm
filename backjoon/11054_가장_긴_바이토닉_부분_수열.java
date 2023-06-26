import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N;
    static int[] value, dp_plus, dp_minus;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        value = new int[N];
        dp_plus = new int[N];
        dp_minus = new int[N];

        for(int i=0; i<N; i++){
            value[i] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;

        for(int i=0; i<N; i++){
            for(int j=i+1; j<N; j++){
                if(value[j]>value[i]){
                    dp_plus[j] = Math.max(dp_plus[j], dp_plus[i] + 1);
                }
            }
        }

        for(int i=N-1; i>=0; i--){
            for(int j=i-1; j>=0; j--){
                if(value[j]>value[i]){
                    dp_minus[j] = Math.max(dp_minus[j], dp_minus[i] + 1);
                }
            }
        }

        for(int i=0; i<N; i++){
            answer = Math.max(answer,dp_minus[i]+dp_plus[i]);
        }

        System.out.println(answer+1);

    }

}
/*
* dp를 이용한 문제
* 해당 문제를 풀기 위해 도움이 되는 관련 문제로는
* 11053번. 가장 긴 증가하는 부분 수열
* 11722번. 가장 긴 감소하는 부분 수열
* 두가지가 있다.
*
* 먼저 가장 긴 증가하는 부분 수열을 푸는 방법은 예시로 설명하면 다음과 같다.
* N = 4
* 10 30 20 25
* 위와 같이 입력이 주어진다면
* 위의 값들을 기억할 배열과 dp에 쓰일 배열 이렇게 두개의 배열을 만든다. (dp 배열 값들은 0으로 초기화 되어 있다.)
* index = 0 인 10부터 시작해서 10보다 크면 dp에 쓰이는 배열에 저번 dp에 쓰인 배열의 값에 +1을 해준다.
* 즉 진행이 된다면 다음과 같다.
* 먼저 index=0인 10부터 시작해서 index=3의 30까지 진행한다.
* 1. 10보다 30 이 크니 dp[1] = dp[0] + 1
* 2. 10보다 20 이 크니 dp[2] = dp[0] + 1
* 3. 10보다 25 이 크니 dp[3] = dp[0] + 1
* 4. index = 1 이고 30을 기준으로 한다. 30이 20보다 크니 넘어간다.
* 5. 30이 25보다 크니 넘어간다.
* 6. index = 2 이고 20을 기준으로 25가 20보다 크니 dp[3] = dp[2] + 1이 된다.
* 7. index=3이 되지만 뒤가 없으니 종료된다.
* 가장 dp배열에 값이 높은건 dp[3]이다. 여기에 +1을 해준다. (부분 수열은 하나만 있어도 성립하기 때문에 나중에 +1 해주면 된다.)
*
* 위와 같은 과정으로 가장 긴 증가하는 부분 수열을 얻을 수 있다. 그렇다면 감소하는 부분 수열은 위와 같이 0에서 시작할 수도 있지만
* 그렇게 되면 해당 문제인 바이토닉 문제를 풀 때 고려해야할 사항이 늘어난다.1
* 대신 반대로 생각하면 N번째 요소에서 시작해서 거꾸로 내려가면서 증가하는 부분 수열을 찾으면 된다.
* 즉 위의 과정을 그저 N에서부터 0까지 수행하며 실행시켜주면, 바이토닉 수열은 그 둘의 합이 가장 큰 값을 찾아주기만 하면 된다.
*
* 10
* 1 5 2 1 4 3 4 5 2 1
* 백준에서 제공하는 테스트케이스인 위의 입력값이 dp 과정을 거치면 다음과 같은 배열이 나온다.
*
* dp_plus  = {0,4,1,0,3,2,2,2,1,0}
* dp_minus = {0,1,1,0,2,2,3,4,1,0}
* 두 개를 더했을 때 가장 큰 값은 6 이고 여기에 +1 해주면 7이 나오고 이렇게 정답을 도출 할 수 있다.
*
*
* */