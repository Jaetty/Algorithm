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

        StringBuilder sb = new StringBuilder();
        String key = "";
        String curr = "";

        for(int i=input.length()-size; i<input.length(); i++){

            sb.append(input.charAt(i));
            curr = sb.toString();
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

        // 어째서인지 에러가 나서 추가한 구간, 아무래도 입력으로 들어오는 값 중 공백문자 등이 있는 것 같음
        if(a.length() != b.length()){
            return MAX;
        }

        for(int i=0; i<a.length(); i++){
            if(a.charAt(i) != b.charAt(i)){
                result++;
            }
        }
        return result;
    }

    static String makingKey(String var){

        StringBuilder sb = new StringBuilder();
        int[] arr = new int[26];

        for(int i=0; i<var.length(); i++){
            arr[var.charAt(i) - 'a']++;
        }

        for(int i=0; i<26; i++){
            sb.append(arr[i]);
        }

        return sb.toString();
    }

}

/*
 * 1099
 *
 * 다이나믹 프로그래밍 문제
 *
  */