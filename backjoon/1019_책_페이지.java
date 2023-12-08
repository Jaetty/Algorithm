import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static long[] count;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        count = new long[10];
        int start = 1;
        int point = 1;


        while(start <= N){

            while(start%10!=0 && start <= N){
                plus(start, point);
                start++;
            }

            while( N%10 != 9 && start <= N){
                plus(N, point);
                N--;
            }

            if(start > N) break;

            start /= 10;
            N /= 10;

            for(int i=0; i<10; i++){
                count[i] += (N-start + 1) * point;
            }

            point *= 10;

        }

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<10; i++){
            sb.append(count[i]).append(" ");
        }

        System.out.print(sb.toString());

    }

    static void plus(int val, int plus){

        while(val > 0){
            count[val%10]+= plus;
            val /= 10;
        }

    }

}

/*
* 수학 문제
*
* 해당 문제의 경우 풀이에 실패하여 아래 사이트의 풀이를 참고하였습니다.
* https://www.slideshare.net/Baekjoon/baekjoon-online-judge-1019
*
* 문제의 입력으로 주어지는 N의 크기를 보면 브루트포스로는 제한 시간 내에 풀이가 불가능함을 짐작할 수 있습니다.
*
* 참고 사이트를 통해 알게 된 해당 문제의 가장 큰 포인트는
* 1. A라는 숫자가 있고 이 숫자의 마지막 자리는 0이어야한다.
* 2. 목표인 B라는 숫자는 마지막 자리의 숫자가 9이어야한다.
* 3. A가 B에 도달할 때 까지 나오는 1의 자리 숫자는  ( (A/10) - (B/10) +1 ) * 원래 숫자의 자릿수
* A = 10
* B = 39
* 라고 한다면
*
* 10 11 12 13 14 15 16 17 18 19
* 20 21 22 23 24 25 26 27 28 29
* 30 31 32 33 34 35 36 37 38 39
*
* 각각 0~9는 3번씩 나오고 이는 (1 - 3 + 1) * 1 과 같다.
*
* 이를 알고리즘에 적용하여
* N = 6789 라면
* 첫 시작 페이지를 start라는 변수라 두면
* start = 1 이므로
* 1의 자리가 0이 되도록 올려주면서 기존 자릿수 만큼 평범히 카운트를 진행해줍니다.
* [0 1 1 1 1 1 1 1 1 1]
* 그리고 0에서부터 9가 될때까지 값인 (8 - 1 + 1) * 1 8을 더해줍니다.
* [8 9 9 9 9 9 9 9 9 9]
*
* 그러면 이제 start/=10 N/=10 으로 start = 1, N = 678이 되도록 하고 point *= 10으로, 10의 자릿수를 고려해봅니다.
*
* start 를 다시 9로 올려주면서 현재는 10의 자리를 고려하므로 10을 더해주어서
* [8 19 19 19 19 19 19 19 19 19]
* 그리고 N을 678을 669로 만들어주면서 카운트하고
* [18 29 29 29 29 29 29 29 29 19]
* 다음으로 (1 - 6 + 1 ) * 10 = 60 만큼 모든 배열에 더해줍니다.
* [78 89 89 89 89 89 89 89 89 79]
*
* 위와 같은 과정을 반복하면 반복 횟수를 대폭 감소시킬 수 있어서 문제를 해결할 수 있게 됩니다.
*
*
* */