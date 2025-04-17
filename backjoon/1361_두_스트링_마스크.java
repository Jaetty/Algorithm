import java.io.*;
import java.util.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static String answer = null, input1, input2, split1[], split2[];
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        input1 = br.readLine();
        input2 = br.readLine();

        split1 = new String[2];
        split2 = new String[2];

        StringBuilder[] sb = split(input1);
        split1[0] = sb[0].toString();
        split1[1] = sb[1].toString();

        sb = split(input2);
        split2[0] = sb[0].toString();
        split2[1] = sb[1].toString();

        input1 = input1.replace("*","");
        input2 = input2.replace("*","");

        for (int i = 0; i <= input1.length(); i++) {
            for (int j = i; j <= input1.length(); j++) {
                String comp1 = split2[0] + input1.substring(i, j) + split2[1];

                if(answer != null && answer.length() < comp1.length()){
                    break;
                }

                for (int k = 0; k <= input2.length(); k++) {
                    for (int l = k; l <= input2.length(); l++) {
                        String comp2 = split1[0] + input2.substring(k, l) + split1[1];

                        if(comp2.length() > comp1.length()) break;
                        if(comp1.length() != comp2.length()) continue;

                        if (comp1.equals(comp2)) {
                            answer = comp1;
                        }
                    }
                }
            }
        }

        System.out.println(answer != null ? answer : -1);

    }

    static StringBuilder[] split(String input){

        StringBuilder[] sb = new StringBuilder[2];
        sb[0] = new StringBuilder("");
        sb[1] = new StringBuilder("");
        int idx = 0;
        for(int i=0; i< input.length(); i++){
            if(input.charAt(i)=='*'){
                idx++;
                continue;
            }
            sb[idx].append(input.charAt(i));
        }
        return sb;
    }

}
/*

1361 두 스트링 마스크

브루트포스 문제

문제의 핵심 포인트는 다음과 같습니다.
1. 반드시 *은 한개만 포함해서 주어지고, *의 양옆에 혹은 왼쪽, 혹은 오른쪽에 대문자가 주어진다.
2. 모든 만들 수 있는 문자열의 조합을 다 만들어서 비교해본다.

결국, 브루트포스 문제이기 때문에 만들 수 있는 모든 문자열의 경우를 다 시도해보면 풀립니다.

풀이의 핵심은 만약 *이 중간에 있다면? *의 양 옆에 문자열이 있을 텐데 이들은 반드시 붙여줘야합니다.
그러면 *의 내용은 상대방의 문자열에서 내게 부족한 부분 문자열을 넣을 수 있을 겁니다.
그런데 내게 부족한 부분 문자열을 모르기 때문에 부분 문자열을 다 넣어서 만들어보고,
그렇게 만든 문자열 중 조건을 만족하면서, 가장 짧은 정답을 구하면 됩니다.

*/