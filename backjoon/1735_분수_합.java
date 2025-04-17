import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int inputs[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        inputs = new int[4];

        for(int i=0; i<4; i++){
            if(i==2) st = new StringTokenizer(br.readLine());
            inputs[i] = Integer.parseInt(st.nextToken());
        }

        int a = (inputs[1]*inputs[2]) + (inputs[0]*inputs[3]);
        int b = inputs[1]*inputs[3];

        int r = gcd(a,b);

        System.out.println( (a/r) + " " + (b/r));

    }

    // 유클리드 호제법을 이용한 gcd 구하기
    static int gcd(int a, int b){

        while(b!=0){

            int temp = b;
            b = a%b;
            a = temp;

        }
        return a;
    }

}

/*
1735 분수 합

유클리드 호제법 문제

문제의 핵심은 최대 공약수(GCD)를 빠르게 구하기 즉, 유클리드 호제법을 구현할 수 있는지 입니다.
결국 분모끼리 곱해준 뒤, 분자는 상대의 분모와 곱하고 더하는 것 까지는 누구나 만들 수 있을건데, 어떻게 기약분수 형태로 나타낼지 고민하게 될 것입니다.
여기서 GCD를 구해야하는데, 브루트포스는 너무 시간이 걸립니다. 그래서 유클리드 호제법 알고리즘을 이용합니다.

유클리드 호제법이란?
기원전 300년경, 유클리드가 만든 최대 공약수 구하기 방식으로, 결국 쉽게 말해 a와 b의 최대 공약수를 구하는 방법입니다.
이 방법은 "큰 수를 작은 수로 나눈 나머지를 가지고 다시 반복한다"는 원리를 이용합니다.

기본 공식은 다음과 같습니다.
gcd(a, b) = gcd(b, a % b)

if a % b == 0이면, b는 GCD
else a = b, b = a % b로 바꾸면서 반복.

예시
gcd(48, 18) => 48 % 18 = 12
gcd(18, 12) => 18 % 12 = 6
gcd(12, 6) => 12 % 6 = 0, (a % b == 0임!)
그러므로, 6이 최대공약수

이 알고리즘은 log 수준의 속도를 가지므로 가장 널리 사용되는 gcd 알고리즘입니다.


  */