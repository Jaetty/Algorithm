import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long[] f, g;
    static final int MAX = 1_000_000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        f = new long[MAX+1];
        f[1] = 1;


        for(int i=2; i<=MAX; i++){
            f[i] = 1 + i;
        }

        setF();
        setG();

        int T = Integer.parseInt(br.readLine());

        for(int t=1; t<=T; t++){
            int val = Integer.parseInt(br.readLine());
            sb.append(g[val]).append("\n");
        }

        System.out.print(sb.toString());

    }

    static void setF(){

        for(int i=2; i<= MAX/2; i++){

            for(int j=i+i; j<=MAX; j+=i){
                f[j] += i;
            }

        }
    }

    static void setG(){

        g = new long[MAX+1];

        for(int i=1; i<=MAX; i++){

            g[i] = f[i] + g[i-1];

        }
    }
}

/*

17425 약수의 합

수학 + 누적합 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. f(1)부터 f(N)까지의 값을 에라토스테네스의 체를 응용한 방법으로 구합니다.
2. g(N)의 값은 f(N) + g(N-1)의 값이므로 이 공식을 이용하여 반복을 통해 미리 g(N)까지의 답을 기억합니다.
3. TestCase에서 질의하는 g(N)값을 출력합니다.

해당 문제는 각각의 TestCase마다 값을 구해서는 시간초과를 벗어나지 못합니다.
이럴 때 가장 단순한 해결 방법은 미리 g함수의 값을 구하면 됩니다.
또한, 약수라는 것은 곧 어떤 Y값을 x를 통해 x+x+x...x를 반복하면 만들 수 있다는 이야기입니다.
즉, 위의 setF()와 같은 방법으로 미리 모든 약수를 구할 수 있습니다.

*/