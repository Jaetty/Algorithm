import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int L, failure[];
    static char[] input;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while(true){

            input = br.readLine().toCharArray();

            if(input[0]=='.'){
                break;
            }

            L = input.length;
            failureFunction();

            if(failure[L-1]==0){
                sb.append(1);
            }
            else{
                int diff = L - failure[L-1];

                if(failure[L-1] % diff >0){
                    sb.append(1);
                }
                else{
                    sb.append( (failure[L-1] / diff)+1 );
                }
            }
            sb.append("\n");

        }

        System.out.print(sb.toString());


    }

    static void failureFunction(){

        failure = new int[L];
        int idx = 0;

        for(int i=1; i<L; i++){

            while(idx>0 && input[i]!=input[idx]){
                idx = failure[idx-1];
            }

            if(input[idx] == input[i]){
                failure[i] = ++idx;
            }

        }

    }

}

/*
 * 4354 문자열 제곱
 *
 * 문자열 문제
 *
 * 문제의 핵심은 kmp 알고리즘의 응용입니다.
 *
 * 입력으로 ababab 라는 문자열이 주어지면 보통은 보자마자 ab가 3번 나왔구나 라는 걸 확인할 수 있습니다.
 * 여기서 아이디어를 따와서 두 가지를 목표로 풀이를 진행할 수 있습니다. 즉,
 * 1. "ab"라는 접두사를 찾는다.
 * 2. 접두사가 몇 번 반복되는지 찾는다.
 *
 * 여기서 실패 함수를 이용하면 접두사 문자열과 반복 횟수를 알 수 있습니다. ababab에 실패 함수를 적용하면
 *
 * 입력 : ababab (문자열 길이 = 6)
 * 배열 : [0,0,1,2,3,4]
 *
 * 그러면 맨 마지막 실패 함수 배열의 숫자는 4이므로 6 - 4 = 2 로 처음 2개의 문자가 접두사임을 알 수 있습니다.
 *
 * ab가 접두사임을 알았으면, ab가 몇 번 나왔는지 알아봐야하는데
 * 이건 마지막으로 나온 실패 함수 숫자 4와 ab의 길이 2를 이용하면 쉽게 알 수 있습니다. 즉, (4 / 2) +1 = 3으로 3번 나온걸 확인할 수 있습니다.
 *
 * 만약 입력이 aaad인 경우에는?
 * 실패 함수 값이 [0,1,2,0] 이므로 접두사가 없다는 것을 알 수 있습니다. 그러므로 맨 마지막 숫자가 0이면 그 자체로 하나의 패턴 문자열이 됩니다.
 *
 * 입력이 abababa 인 경우는?
 * 실패 함수 값이 [0,0,1,2,3,4,5]가 됩니다. 'ab'가 접두사임을 알게 되었지만 마지막이 a로 끝나므로 ab가 반복되는 형태가 사실 아닙니다.
 * 이를 알 수 있는 방법은 mod를 이용하면 됩니다. (5%2) = 1 이므로 나누어 떨어지지 않습니다. 즉, 접두사가 제대로 반복되지 않으므로 이것도 하나의 패턴 문자열입니다.
 *
 * 이러한 조건을 충족하며, 실패 함수를 이용하면 문제를 해결할 수 있습니다.
 *
 * */