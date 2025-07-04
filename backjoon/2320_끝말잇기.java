import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static Word[][] dp;
    static String[] input;
    static int answer;

    static class Word{
        char first;
        int length;

        public Word(char first, int length){
            this.first = first;
            this.length = length;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        input = new String[N];
        dp = new Word[N][1<<N];

        for (int i = 0; i < N; i++) {
            input[i] = br.readLine();
            dp[0][1<<i] = new Word(input[i].charAt(0), input[i].length());
            answer = Math.max(answer, input[i].length());
        }

        // 선택한 갯수
        for(int i = 1; i <= N; i++) {

            // bit 값 부분
            for(int j = 0; j < 1<<N; j++) {

                if(dp[i-1][j] == null) continue;

                // 다음 input[고를 값 = k]
                for(int k = 0; k < N; k++) {

                    // 이미 선택한 값이거나 || 선택한 문자열의 '마지막 문자'와 기존에 성립한 문자열의 '최초의 문자'가 틀리면 넘어가기
                    if( (j & 1<<k) > 0 || input[k].charAt(input[k].length()-1) != dp[i-1][j].first) continue;

                    // 선택한 문자열을 앞에 두는 거임 현재 선택한 input[k] = A이고 DP[i-1][j]과 AEE라면 AAEE가 됨
                    Word word = new Word(input[k].charAt(0), dp[i-1][j].length + input[k].length());

                    if(dp[i][j | (1<<k)] == null) {
                        dp[i][j | (1<<k)] = word;
                    }
                    else {
                        dp[i][j | (1<<k)] = dp[i][j | (1<<k)].length < word.length ? word : dp[i][j | (1<<k)];
                    }
                    answer = Math.max(answer, word.length);

                }
            }
        }

        System.out.println(answer);

    }

}

/*

2320 끝말잇기

비트마스킹 + 다이나믹 프로그래밍 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. 문제의 핵심은 N개의 단어를 어떻게 조합했을 때 가장 큰 값이 나오냐를 묻기 때문에 모든 경우의 수를 돌면 되지만 시간초과.
    그렇다면, 모든 경우의 수를 돌지 말고 dp[X개를 골랐을 때][어떤 값들을 선택했을 때]의 가장 긴 경우를 기억하는 dp를 사용해야함.
2. 문자열을 모두 기억할 필요가 없다고 판단. class로 길이만 기억하고, 맨 앞(혹은 뒤) char만 기억해서 pick한 문자열을 앞(혹은 뒤)에 붙이는 방식 사용
3. 모든 문자열을 다 쓴 조합이 불가능할 수 있음. 그러므로 중간에 성립할 때마다 가장 큰 값을 기억하게 함. (answer = max(..) 부분)

먼저 이 문제는 조합 중 가장 큰 경우를 묻고 있기 때문에, 비트 필드를 이용한 DP 문제임을 알 수 있습니다.
우선 규칙을 정해야했는데 저의 규칙은 어떤 문자열이 성립했다면 그 문자열의 앞에 다른 문자열을 붙였을 때 성립하는 문자열을 기억하는 방식을 썼습니다.

그렇게 어떤 값들을 조합했을 때 dp[2][(101 = 1번째와 3번째를 골랐을 때)] = 최고로 긴 값을 기억하게 만들어 나가면서, 최대한 끝까지 조합이 가능한 경우를 찾아주면 됩니다.
여기서 조합이 반드시 성립할 수 없기 때문에 중간 중간 answer 값을 갱신하며 가장 큰 값을 기억하게 해주어 풀이하였습니다.


*/