import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static String input;
    static boolean[][] pal;
    static int[] dp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        input = br.readLine();
        dp = new int[input.length()];
        pal = new boolean[input.length()][input.length()];
        setPal();

        System.out.print(recursive(0));

    }

    static void setPal(){

        for (int len = 1; len <= input.length(); len++) {
            for (int start = 0; start + len - 1 < input.length(); start++) {
                int end = start + len - 1;
                if (input.charAt(start) == input.charAt(end)) {
                    if (len <= 2) pal[start][end] = true;
                    else pal[start][end] = pal[start+1][end-1];
                }
            }
        }

    }

    static int recursive(int curIdx){

        if(curIdx == input.length()){
            return 0;
        }

        if(dp[curIdx] != 0) return dp[curIdx];
        dp[curIdx] = Integer.MAX_VALUE;

        for(int l_idx = input.length()-1; l_idx>= curIdx; l_idx--){

            if(pal[curIdx][l_idx]){
                dp[curIdx] = Math.min(recursive(l_idx+1)+1, dp[curIdx]);
            }

        }

        return dp[curIdx];
    }
}
/*

1509 팰린드롬 분할

다이나믹 프로그래밍 문제

해당 문제는 DP 문제로 먼저 어떤 요소를 기억하게 만들면 나중에 다시 계산하지 않게 만들까를 찾아내는 것이 핵심입니다.

DP는 로직에서 2구간에 적용할 수 있습니다.
1. 팰린드롬 여부 확인하는 전처리 작업 (setPal) <= 이 작업은 있으면 확 시간이 줄어들지만 없어도 통과합니다.
2. x번 index에서 최소한의 팰린드롬 분할 횟수를 구하는 작업 (recursive) <= 이 부분이 핵심으로 여기만 제대로 구현해도 통과합니다.

1번째 DP의 원리는 다음과 같습니다.
"글자수가 3 이상이고 첫글자와 마지막 글자가 같을 때 그 사이에 있는 문자열이 팰린드롬이면 이 문자열도 팰린드롬이다."
예를 들어, A는 팰린드롬입니다. 이 A가 B와 B 사이에 있다고 해보겠습니다. BAB는 팰린드롬입니다.
그럼 BAB가 C와 C 사이에 있다면? CBABC가 되고 이는 팰린드롬입니다.
즉, 내부의 문자열에 대해 팰린드롬인지 파악했다면 또 계산할 필요가 없다는 점에 착안해서 DP로 구현할 수 있습니다.


2번째 DP의 원리는 다음과 같습니다.

먼저 최소를 구하기 위해선 모든 경우를 다 돌아야합니다. 즉, i 위치에서 여러 길이의 팰린드롬이 존재한다면,
어떤 길이의 팰린드롬을 선택했을 때가 전체적으로 최소한의 분할이 가능할까를 알아야합니다.

입력 문자열이 AACB 라고 해보겠습니다.
마지막 C와 B에만 집중해보시면 이 둘은 아무리 노력해도 길이 1의 팰린드롬입니다. 더 늘릴 수 없습니다.
그렇다면 C 이후부터는 암만 노력해도 최소한의 팰린드롬의 분할 수가 2개(C,B)입니다.
A의 경우는 선택이 가능합니다. {A, A}로 선택하거나 {AA}로 선택하여 뒤의 팰린드롬 경우의 수를 탐색할 수 있습니다.
그러나 어떤 경우에도 C이후는 무조건 2입니다. 그러면 굳이 C에 도달한 후 최소한의 팰린드롬인지 확인하는 절차는 필요없습니다.
이점에 착안해서 DP를 적용하는 것 입니다.

dp[i]는 i번째 인덱스에서 시작해 문자열 끝까지 최소로 잘라낼 팰린드롬 조각의 개수를 저장하게 만들어줍니다.
만약 i에서 시작해 j까지가 팰린드롬이라면, i~j 구간을 하나로 자르고,
j+1부터 끝까지의 최소 분할 개수(dp[j+1])에 +1을 한 값이 하나의 후보가 됩니다.

이렇게 하면 앞서 말했듯 AACB라는 문자열을 A, A, C, B로 탐색, AA, C, B로 탐색도 수행할 수 있으면서
또 이미 탐색 중 'C'이후의 최솟값 dp[2] = 2를 알고 있기 때문에 두번 탐색하지 않게 만들 수 있습니다.

이처럼 2가지의 DP(정확히는 2번째 DP만 써도 합격합니다.)를 떠올리는 것이 문제 풀이의 핵심입니다.

*/