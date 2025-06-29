import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long N, M;
    static boolean[] notPrime;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        notPrime = new boolean[10000001];
        primeCount();

        N = Long.parseLong(st.nextToken());
        M = Long.parseLong(st.nextToken());

        System.out.println(getAnswer());

    }

    static long getAnswer(){

        long answer = 0;

        for(int i = 2; i < 10000000; i++){

            if(!notPrime[i]){

                long val = (long) i * i;

                while(true){
                    if(N <= val){
                        answer++;
                    }
                    if(val > M/i) break;
                    val *= i;

                }
            }
        }
        return answer;
    }

    static void primeCount(){

        notPrime[0] = true;
        notPrime[1] = true;

        for(int i = 2; i <= 10000000; i++){

            if(notPrime[i]){
                continue;
            }

            for(int j = i + i; j <= 10000000; j += i){
                notPrime[j] = true;
            }

        }

    }

}

/*

1456 거의 소수

소수 판정 문제

해당 문제는 풀이 로직은 다음과 같습니다.
1. 에라토스테네스의 체를 이용하여 10^7까지의 소수를 구합니다.
2. 소수를 순회하면서 어떤 소수의 제곱(X)이 N<= X <= M 인 경우 count 값을 늘려서 출력합니다.

문제의 핵심은 10^14만큼 어떻게 반복하지 않을까 떠올리는 것 입니다.
단순 반복이라도 10^14의 반복은 시간초과를 벗어날 수 없습니다. 거기에 소수까지 판별하는 작업이 추가되면 브루트포스로는 불가능합니다.

단순히 생각해보면 모든 순회를 안하는 방법은 M보다 작은 소수를 제곱했을 때 이 값이 N 이상 M 이하인지만 확인하면 됩니다.
그렇다면, 먼저 루트 M 이하의 소수를 구한 후 각각의 소수를 제곱해보면서 N이상 M이하인지 확인해주면 시간을 확 줄일 수 있게됩니다.
(다만, 저의 경우는 for문이 int로 돌아가기를 바래서 처음부터 10^7(10000000)이하의 소수를 다 구했습니다.)

이 문제의 주의해야할 점은 오버플로우입니다.
만약 10^7에 매우 가까운 소수가 있다면, 그 소수를 2제곱하면 10^14 이하이지만, 3제곱하면 10^21제곱이고 이는 long형을 벗어납니다.
그러므로 if(val > M/i) break;로 제곱하기 전에 미리 오버플로우를 방지하는 준비가 필요합니다.

*/