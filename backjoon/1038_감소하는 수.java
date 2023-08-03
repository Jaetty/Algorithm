import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Main{

    static long[] answer;
    static int index;

    public static void main(String[] args)throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        answer = new long[1023];
        Arrays.fill(answer, -1);
        answer[0] = 0;

        for(int i=1; i<10; i++){
            answer[i] = i;
        }

        index = 10;
        int depth = 1;

        while(depth < 11){
            for(int i=1; i<10; i++) backtrack(depth-1, i-1, (long) Math.pow(10,depth) * i);
            depth++;
        }

        if(n>1022) System.out.println(-1);
        else System.out.println(answer[n]);


    }

    static void backtrack(int depth, int start, long val){
        if(depth<=0){
            for(int i=0; i<=start; i++) answer[index++] = val+i;
            return;
        }
        else for(int i=0; i<=start; i++) backtrack(depth-1, i-1, val + (long) Math.pow(10,depth)*i);
    }

}

/*
 * 브루트포스 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 브루트포스로 모든 경우를 다 저장해준다.
 * 2. 맨 마지막 숫자는 9876543210이고 이는 long 형태이다. 여기까지 반복하면 된다.
 *
 * 위의 포인트를 종합해서 생각해보면 브루트포스로 앞의 숫자를 올려주면 그것보다 적은 숫자만 다음 숫자를 선정하면 된다는 점을 알 수 있습니다.
 * 예를들어 세 자리 숫자로 첫 숫자가 3이면 10의 자리 숫자는 3보다 적은 0 1 2가 올 수 잇고 그 다음 1의 자리 숫자는 앞의 숫자에 따라 결정됩니다.
 *
 * 이를 재귀적으로 나타내서  depth는 총 숫자의 길이고, start는 저번 숫자보다 1 작은 값, val는 총 합을 구해서
 * answer 배열에 넣어주면 풀이가 가능합니다.
 *
 */