import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static String[] inputs;
    static int[] change;
    static boolean[] visited;
    static int N;
    static long answer;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        change = new int[26];
        inputs = new String[N];
        visited = new boolean[10];

        for(int i=0; i<N; i++){
            String str = br.readLine();
            inputs[i] = str;
            for(int j=0; j<str.length(); j++){
                change[str.charAt(j)-'A'] += (int) Math.pow(10, (str.length()-1)-j);
            }
        }

        Arrays.sort(change);
        int val = 9;
        for(int i=25; i>=0; i--){
            if(change[i]==0) break;
            answer += change[i]*val--;
        }

        System.out.println(answer);

    }


}


/*
* 그리디 알고리즘 문제
*
* 해당 문제의 로직은 다음과 같습니다.
* 그리디하게 생각했을 때 풀이법은 가장 자릿수나 값의 크기가 큰 값부터 차례대로 9 8 7 6 5 4 3 2 1 0 값을 부여해주면 될 것입니다.
* 그렇다면 자릿수를 판단하는 방법은 입력된 문자열을 각각 그 자릿수에 몇개 있는지라고 생각하면 됩니다.
* 예를 들어 다음과 같은 입력이 있다면
* 2
* GCF
* ACDEB
*
* 먼저 G의 경우 100자리에 하나가 있고, C는 10자리에 하나 , F는 1자리에 하나 있습니다.
* ACDEB의 경우 A는 10000자리에 하나, C는 1000자리에 하나 D는 100, E는 10 B는 1입니다.
* 이제 위 아래를 더해주면
* A = 10000
* B = 1
* C = 1010
* D = 100
* E = 10
* F = 1
* G = 100
* 이렇게 됩니다. 그러면 이것을 큰 순서로하면 A, C, D, G, E, F, B 와 같이 될 것입니다.
* 그러면 이제 A의 값인 10000부터 1의 값인 B까지 9,8,7...3를 순서대로 곱해주고 모든 값들을 더해주면 문제를 풀이할 수 있습니다.
*
*
* */