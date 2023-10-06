import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N,K, answer, bit;
    static boolean[] antatica;
    static int[] words;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        words = new int[N];
        bit = 0;

        antatica = new boolean[26];
        String antic = "antic";

        for(int i=0; i<5; i++){
            int var = antic.charAt(i)-'a';
            bit |= 1<<var;
            antatica[var] = true;
        }

        for(int i=0; i<N; i++){
            String str = br.readLine();
            for(int j=0; j<str.length(); j++){
                int temp = str.charAt(j) - 'a';
                if(!antatica[temp]) {
                    words[i] |= 1<<temp;
                }
            }
        }
        answer = 0;

        if(K<5){
            System.out.println(0);
            return;
        }

        backTrack(K-5, 25);

        System.out.println(answer);

    }

    public static void backTrack(int depth, int max){

        if(depth==0){
            int val = 0;

            for(int i=0; i<N; i++){
                if((bit & words[i]) == words[i]) val++;
            }
            answer = Math.max(answer, val);
            return;
        }

        for(int i=max; i>=0; i--){
            if( antatica[i] ) continue;
            bit ^= 1<<i;
            backTrack(depth-1, i-1);
            bit ^= 1<<i;
        }

    }
}

/*
* 백 트래킹 문제
*
* 해당 문제는 백트랙킹 문제로 필자는 비트마스킹을 활용하여 문제를 해결하였습니다.
* 문제의 포인트는 접두사와 접미사를 제외하고 K개의 글자에서 어떤 글자를 가르쳤을 때 가장 많은 단어를 읽는가 입니다.
*
* 단어는 다음과 같이 주어지게 됩니다.
* "anta[가운데 들어가는 문자열]tica"
* 모든 단어는 접두사에서 anta와 접미사에서 tica가 반드시 붙어야합니다.
* 그렇다면 필수적으로 접미사 접두사에 들어가는 글자는 배워야합니다.
* 여기서 antatica의 중복되는 부분을 제거하면 antic 이라는 5글자를 필수로 배워야하며 백트래킹 과정에서 빼줍니다.
* antic 5글자 외에 다른 글자를 백트래킹으로 모든 경우의 수를 따져보면 풀이할 수 있습니다.
*
* 여기에 bit마스킹을 더하게 되면 문제를 문자 비교 등 보다 더욱 간편히 코드를 작성할 수 있습니다.
*
* */