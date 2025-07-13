import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, answer;
    static char[] arr;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        arr = new char[N+1];

        String val = br.readLine();
        for (int i = 1; i <= N; i++) {
            arr[i] = val.charAt(i-1);
        }

        if(N==1){
            System.out.println(arr[1]-'0');
            return;
        }

        // 주어진 수식에서 넣을 수 있는 최대 괄호 수
        int p_cnt = ((N/2)+1)/2;
        char[] oper = new char[N];
        int[] nums = new int[N];
        answer = Integer.MIN_VALUE;
        arr[0] = '+';

        for(int max = p_cnt; max >= 0; max-- ) {
            dfs(1,max, oper, nums, 0,0);
        }

        System.out.println(answer);

    }

    static void dfs(int arr_idx, int p_count, char[] operator, int[] number, int operator_idx, int number_idx){

        if(arr_idx > N){
            getAnswer(operator, number, number_idx, operator_idx);
            return;
        }

        // 괄호를 칠 수 있는 조건이라면 괄호쳐주기
        // 바로 직전 값이 '연산자 이면서' && 괄호 최대 개수가 남았고 && 뒤에 2글자 더 있다면
        if(!('0' <= arr[arr_idx-1] && arr[arr_idx-1] <= '9') && p_count > 0 && arr_idx <= N-2){
            int temp = calculate(arr[arr_idx]-'0', arr[arr_idx+1], arr[arr_idx+2]-'0');
            number[operator_idx] = temp;

            dfs(arr_idx+3, p_count-1, operator, number, operator_idx, number_idx+1);
        }

        // 아래는 위의 괄호 로직을 이미 했거나 괄호 로직이 불가능하면

        // 현재 값이 숫자라면
        if(('0' <= arr[arr_idx] && arr[arr_idx] <= '9')){
            number[number_idx] = arr[arr_idx]-'0';
            dfs(arr_idx+1, p_count, operator, number, operator_idx, number_idx+1);
        }
        // 현재 값이 연산자라면
        else{
            operator[operator_idx] = arr[arr_idx];
            dfs(arr_idx+1, p_count, operator, number, operator_idx+1, number_idx);
        }

    }

    static int calculate(int a, char cal, int b){

        switch(cal){
            case '+':
                return a + b;
            case '-':
                return a - b;
            default:
                return a * b;
        }

    }

    static void getAnswer(char[] operator, int[] number, int number_idx, int operator_idx){

        if(operator_idx == 0){
            answer = Math.max(number[0],answer);
            return;
        }

        int sum = calculate(number[0], operator[0], number[1]);
        int op_idx = 1;

        for(int i = 2; i < number_idx; i++){
            sum = calculate(sum, operator[op_idx++], number[i]);
        }

        answer = Math.max(answer,sum);

    }

}

/*

16637 괄호 추가하기

브루트포스 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. 최대 괄호 개수 = (((N/2)+1)/2)를 구한 후 for문으로 괄호 개수를 줄여가면서 모든 곳에 괄호를 넣었을 때의 경우를 다 시도해본다.
2. 괄호를 친 곳을 미리 계산해준 후 최종적으로 나온 공식을 왼쪽부터 차례대로 계산하여 가장 큰 값을 기억한다.

우선 이 문제를 분석하기 위한 포인트는 다음과 같습니다.
1. 1 <= N <= 19 이며 N은 홀수.
2. 괄호는 딱 한 연산자만을 포함할 수 있다. (중첩 괄호는 성립하지 못한다)

먼저 1번을 고려해서 생각해보면 N이 홀수인 이유는 숫자 사이에 연산자가 있기 때문입니다.
즉, N=3이면 숫자 2개에 연산자가 1개라는 뜻이고, N=9이면 숫자 5개에 연산자가 4개가 됩니다.
이걸 다르게 나타내면 숫자의 개수는 = N/2+1이 되고, 연산자의 개수는 N/2가 된다는 것 입니다.

다음 2번 포인트를 생각해보면 괄호는 딱 숫자 2개만 포함할 수 있습니다.
이제 N= 9일때를 생각해보겠습니다. 만약 다음과 같은 입력이 있다면
1+1+1+1+1
최대로 괄호를 칠 수 있는 경우는 (1+1)+(1+1)+1 or 1+(1+1)+(1+1) 이 됩니다.
즉, 최대 괄호의 숫자는 = 숫자의 개수/2 = (N/2+1)/2 가 됩니다.

그렇다면 1번 2번을 모두 고려해서 생각해보면,
N<=19이므로 충분히 작은 입력이고, 괄호의 최대 개수도 (19/2+1)/2밖에 안되니 충분히 모든 경우의 수를 돌 수 있다는 점을 쉽게 알 수 있습니다.

그렇다면 결국 문제는 괄호를 최대 개수 ~ 0까지 처보면서 가장 합산 값이 높은 경우의 수를 찾으며 된다는 것 입니다.
로직은 이처럼 단순하게 세울 수 있고, 나머지는 구현의 영역이기 때문에 로직이 작동하게끔 풀이하기만 하면 문제를 해결할 수 있습니다.

문제의 주의점은 음수가 나올 수 있기 때문에 answer의 초기값은 꽤 낮은 음수 값으로 넣어줘야합니다.

*/