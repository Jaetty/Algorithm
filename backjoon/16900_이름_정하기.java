import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static String S;
    static int K, N;
    static int[] table;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        S = st.nextToken();
        K = Integer.parseInt(st.nextToken());
        N = S.length();

        table = new int[N];
        tableSet();

        long answer = N;
        answer += (K-1L) * (N - table[N-1]);

        System.out.println(answer);

    }

    static void tableSet(){

        int before = 0;

        for(int cur = 1; cur < N; cur++){

            while(before>0 && S.charAt(before) != S.charAt(cur)){
                before = table[before-1];
            }
            if(S.charAt(before) == S.charAt(cur)){
                table[cur] = ++before;
            }

        }
    }
}

/*

16900 이름 정하기

문자열 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. KMP 알고리즘을 통해 LPS 배열(Prefix function, Failure function 이라고도 부름)을 구한다.
2. 문자열 길이를 N이라고 한다면, 정답은 N + ((K-1) * (N - table[N-1])) 이다.

이 문제의 경우 최소의 문자열로 부분 문자열 K개 포함한 문자열을 만들어야합니다.
가장 쉬운 방법은 부분문자열로만 전체문자열을 만들면 됩니다. 여기서 핵심은 중복을 이용하는 것 입니다.
즉, 접미사와 접두사가 같은 구간부터 다시 세주면 최소한으로 만들 수 있습니다.

예를 들어 ada 3 이라는 입력이 있다면 이를 해결하는 최소한의 길이의 문자열은 "adadada" 입니다.
이는 KMP를 이용하면 쉽게 구할 수 있고, 결국 정답은 문자열 길이 N + ((K-1) * (N-table[N-1]))이 됩니다.

*/