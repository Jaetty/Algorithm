import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, answer;

    static class Trie {
        Trie[] children;

        Trie(){
            this.children = new Trie[2];
        }

    }
    static String[] input;
    static Trie root;

    public static void main(String[] args) throws Exception {

        BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        input = new String[N];

        root = new Trie();
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < N; i++) {
            input[i] = String.format("%30s", Integer.toBinaryString(Integer.parseInt(st.nextToken()))).replace(' ', '0');
            Trie cur = root;

            for(int j = 0; j < input[i].length(); j++){
                if(input[i].charAt(j) == '0'){
                    if (cur.children[0] == null) {
                        cur.children[0] = new Trie();
                    }
                    cur = cur.children[0];
                }
                else{
                    if (cur.children[1] == null) {
                        cur.children[1] = new Trie();
                    }
                    cur = cur.children[1];
                }
            }
        }

        for(int i = 0; i < N; i++) {
            answer = Math.max(answer, dfs(input[i]));
        }

        System.out.println(answer);

    }

    static int dfs(String s){

        int sum = 0;
        Trie cur = root;

        for(int i = 0; i < 30; i++) {

            int opposite = s.charAt(i) == '0' ? 1 : 0;

            if(cur.children[opposite] != null){
                sum += (int) Math.pow(2, 29-i);
                cur = cur.children[opposite];
            }
            else{
                cur = cur.children[(opposite+1)%2];
            }
        }
        return sum;
    }

}

/*

13505 두 수 XOR

트라이 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. 입력으로 주어지는 숫자를 2진수로 바꾸되, 30 자릿수로 만들고 부족한 만큼 앞에 '0'을 붙여서 기억해준다.
2. 트라이로 2진수의 맨 앞에서 트리가 뻗어나가도록 만든다.
3. 입력으로 받은 문자열을 바탕으로 탐색한다. 즉, 앞에서부터 서로 안맞는 경우를 최대한 찾아가며 30번 반복한다.

우선 이 문제의 가장 큰 핵심은 트라이 구조를 떠올리는 것 입니다.
N = 100,000의 입력과 최대 입력 M = 1,000,000,000로 주어지기 때문에 절대 모든 경우의 수를 다 체크할 수는 없습니다.
때문에 최대 N log M 만에 서로를 다 비교하도록 만들어야만 하고, 이를 위해 트라이로 log M 크기의 트리를 만들어야 합니다.

1번의 30자릿수를 만드는 이유는 log2 1,000,000,000 = 29.89가 나옵니다. 이때 뒷자리를 버리고 +1을 한 값이 2진수의 최대 자릿수이기 때문입니다.

트라이로 비교하는 방법은 간단합니다. 지금 입력값을 모두 트리 형태로 만드는 작업이 끝났다고 가정하고,
000...001(줄여서 001로 하겠습니다.) 이라는 값을 기준으로 트라이를 탐색한다면
root에서부터 시작해서 지금 내 값과 다른 값의 child가 있다면 거기를 우선해서 탐색해가면 됩니다.

이렇게 하는 이유는 가장 먼저 만나는 XOR의 경우가 가장 크기 때문입니다.
예를 들어 10000이라는 값을 01111와 11111로 XOR을 한다면, 각각 10000, 01111이므로 '10000'이 더 큽니다.
때문에 앞에서부터 트라이를 탐색할 때, 가장 먼저 만나는 나와 다른 값이 있다면 그것을 먼저 우선시해서 따라가면 됩니다.
맨 마지막 리프노드까지 탐색을 지속하면서 서로 다른 path가 있는 경우마다 sum += 2^자릿수 연산을 수행해줍니다.

이렇게 트라이를 이용하면 N log M 의 반복으로 풀이가 가능합니다.

*/