import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static String input;
    static int N;
    static Integer dp[];
    static Map<String, List<String>> words;

    static final int MAX = 1_000_000; // 모든 경우에도 절대 나올 수 없는 값

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        input = br.readLine();
        N = Integer.parseInt(br.readLine());
        dp = new Integer[input.length()+1];

        dp[0] = 0; // 모든 남은 문자가 없을 때는 결국 비용은 0

        words = new HashMap<>();

        for(int i=0; i<N; i++){

            String temp = br.readLine();
            String key = makingKey(temp);

            if(!words.containsKey(key)){
                words.put(key, new ArrayList<>());
            }
            words.get(key).add(temp);
        }

        dp(input.length());

        System.out.println(dp[input.length()] >= MAX ? -1 : dp[input.length()]);


    }

    static int dp(int size){

        if(dp[size] != null){
            return dp[size];
        }

        dp[size] = MAX;

        String key = "";
        String curr = "";
        int start = input.length()-size;

        for(int i=start; i<input.length(); i++){

            curr = input.substring(start, i+1);
            key = makingKey(curr);

            if(words.containsKey(key)){

                int diff = MAX;
                for(String var : words.get(key)){
                    diff = Math.min(diff, diff_calculate(curr, var));
                }

                dp[size] = Math.min(dp(input.length() - (i+1)) + diff, dp[size]);

            }

        }

        return dp[size];
    }

    static int diff_calculate(String a, String b){

        int result = 0;

        for(int i=0; i<a.length(); i++){
            if(a.charAt(i) != b.charAt(i)){
                result++;
            }
        }
        return result;
    }

    static String makingKey(String var){

        int[] arr = new int[26];

        for(int i=0; i<var.length(); i++){
            arr[var.charAt(i) - 'a']++;
        }

        return Arrays.toString(arr);
    }

}

/*
 * 1099 알 수 없는 문장
 *
 * 다이나믹 프로그래밍 문제
 *
 * 문제의 핵심 포인트는 다음과 같습니다.
 * 1. 특정 전체 문자에서 특정 구간이 주어진 N개의 단어 중 하나인지 확인하기
 * 2. 문자가 서로 얼마나 차이나는지 확인하기
 * 3. dp를 이용하여 남은 문자열로 가장 최솟값으로 만들 수 있는 경우를 가져오기
 *
 * @ 1번과 2번의 경우 여러가지 해결법이 있겠지만, 저는 Map을 이용하여 N개의 문자를 미리 어떤 문자가 몇개 들어있는지를 key 값으로 구분했습니다.
 *
 * 1번의 핵심은 문자의 수를 세서 비교하는 것 입니다.
 * 예를 들어, cba와 abc가 같은 문자로 구성되어있는지 확인하려면 두 문자열을 구성하는 문자의 갯수를 세보면 됩니다.
 * 각각 a: 1개, b: 1개, c: 1개이므로 두 문자열은 문자의 위치만 다를 뿐 같은 문자로 구성됨을 확인할 수 있습니다.
 * 저는 이를 makingKey 라는 메소드로 만들었습니다. 문자의 숫자를 세서 그 문자의 숫자 갯수로 구성한 문자열을 리턴합니다.
 * abc의 경우 "[1,1,1,0,0,...0,0]" 이라는 문자열을 리턴하고 이는 a와 b와 c가 각각 1개씩 있다는 뜻입니다.
 * 저는 이걸 비교를 위한 key로 만들었습니다.
 *
 * 2번의 경우는 두 문자열을 비교해주면 됩니다.
 * N개의 문자열에 cba, acb가 있고 비교 문자열로 acb가 주어진다면 맨 앞자리부터 일치하지 않는 갯수를 세면 됩니다.
 * 그래서 그 중 점수가 가장 낮은 값을 찾아주면 됩니다.
 *
 * 3번의 경우는 1번과 2번을 활용하고 또한, dp를 활용하여 풀이하여야합니다.
 *
 * 기본적으로는 주어진 문자열을 앞에서부터 하나씩 나눠가면서 주어진 단어에 속하는 부분문자열이 되는지 확인합니다.
 * 즉, neotowheret라는 문자열이 있다면 n, ne, neo, neot, neoto,.... neotowhere, neotowheret 처럼 앞에서부터 잘라낸 부분 문자열 중 key가 같은 단어가 있는지 확인합니다.
 * 그리고 key가 같은 단어가 있다면 얼마나 차이가 나는지 diff를 계산해주어 최소의 diff 값을 구해가면 됩니다.
 * 하지만 이를 브루트포스로만 해결하려고 하면 시간초과를 벗어날 수 없습니다. 경우의 수가 너무 많기 때문입니다.
 *
 * 저는 이걸 재귀적인 형태의 그림을 그린 후 dp라고 판단하고 해결했습니다.
 * 입력으로 neoneoneo라는 문자열과 N개의 문자 one, noe 가 주어졌다고 해보겠습니다.
 * (구분하기 쉽게 "neo neo neo"로 표기하겠습니다.)
 *
 * "neo neo neo"를 어떻게 가장 최솟값으로 만들 수 있을까를 고민하다가 이를 다음과 같이 그림으로 나타내보았습니다.
 * (이는 피보나치 수열과 비슷한 형태를 나타내고 있습니다.)
 *
 *                                  "neo neo neo"
 *                          noe     /         \   one
 *                            "neo neo"      "neo neo"
 *                             /    \        /    \
 *                         "neo"   "neo"  "neo"   "neo"
 *                         /  \    /  \   /  \    /  \
 *                        2    3  2    3 2   3   2    3 <= neo를 noe 혹은 one으로 만들 때 드는 비용은 각각 2와 3, 2가 더 최소
 *
 * 보시면 "neo neo noe"를 만들려면 "neo neo neo"에서 "neo"를 하나 빼주고, 다음으로 "neo neo" 중 neo를 하나 빼주고 마지막 neo를 빼주는 반복적인 동작이 일어납니다.
 *
 * 자, "neo neo noe"는 9글자의 문자열입니다.
 * 마지막 neo 부분에 집중해봅시다. neo를 만들 때, 드는 최소 비용은 2입니다(주어진 단어 noe와 2개만 차이남).
 * 그리고 마지막 neo는 이것보다 더 최소의 값으로 만드는 경우가 없습니다.
 * 이때의 문자열의 길이는 neo이므로 3입니다. 즉, dp[3] = 2, 이건 확정적입니다. 남은 문자열이 3개가 있을 때, 최소로 만들 수 있는 값은 2밖에 없습니다.
 *
 * 다음 neo neo의 경우인데 앞의 neo와 겹칩니다. 그러면 앞의 neo를 최소로 만드는 경우는 또 2입니다.
 * 그러면 6의 길이를 가진 문자열 중 앞에 3개의 문자열은 2의 값으로 만들 수 있었습니다. 뒤의 3개를 또 때서 굳이 비교할 필요가 있을까요?
 * 이미 앞에서 dp[3] 즉 남은 문자열이 3개밖에 없을 때, 최소의 값은 2였으므로, dp[6] = 현재 최솟값 2 + dp[3] 이 됩니다.
 * neo neo neo도 마찬가지입니다. 맨 앞에 neo 부분은 최소 2로 만들 수 있으니, dp[9] = neo 최솟값 2 + dp[6]이 됩니다.
 *
 * 즉, 남은 문자열을 최소로 만들 수 있는 값을 기억해두면, 굳이 그 뒤에 있던 모든 경우의 수를 다시 돌 필요가 없습니다.
 *
 * 이를 토대로, dp[남은 문자열 값]을 기억하는 방법으로 반복적인 연산을 최소화 하면 문제를 해결할 수 있습니다.
 *
 *
 */