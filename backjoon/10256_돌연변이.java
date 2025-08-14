import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int T, N, M;
    static TrieNode root;
    static Set<String> patternSet;

    static class TrieNode {

        TrieNode[] children;
        TrieNode failure;
        boolean isEnd;
        boolean isRoot;

        TrieNode(boolean isRoot) {
            this.children = new TrieNode[26];
            this.isRoot = isRoot;
        }

        void clear(){
            this.children = new TrieNode[26];
            this.failure = null;
            this.isEnd = false;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        T = Integer.parseInt(br.readLine());
        root = new TrieNode(true);
        patternSet = new HashSet<>();

        for(int tc = 0; tc < T; tc++) {

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            root.clear();
            patternSet.clear();

            String origin = br.readLine();
            char[] patternOrigin = br.readLine().toCharArray();

            getPattern(patternOrigin);

            for(String pattern : patternSet) {

                TrieNode curr = root;

                for(int i=0; i<M; i++) {
                    if(curr.children[pattern.charAt(i)-'A'] == null) {
                        curr.children[pattern.charAt(i)-'A'] = new TrieNode(false);
                    }
                    curr = curr.children[pattern.charAt(i)-'A'];
                }

                curr.isEnd = true;

            }

            failure();

            sb.append(kmp(origin)).append("\n");

        }

        System.out.print(sb.toString());

    }

    // 문자열 전처리 구간
    static void getPattern(char[] input){

        StringBuilder temp = new StringBuilder();

        int start = 0;
        int mid = 0;

        while(start < M){

            temp.setLength(0);
            mid = M;

            for(int i=0; i<start; i++) {
                temp.append(input[i]);
            }

            while(start < mid){

                for(int i=mid-1; i>=start; i--) {
                    temp.append(input[i]);
                }

                for(int j=mid; j<M; j++) {
                    temp.append(input[j]);
                }

                patternSet.add(temp.toString());
                temp.setLength(start);

                mid--;
            }

            start++;
        }

    }

    // 실패 함수
    static void failure(){

        Queue<TrieNode> queue = new ArrayDeque<>();
        root.failure = root;
        queue.add(root);

        while(!queue.isEmpty()){

            TrieNode curr = queue.poll();

            for(int i = 0; i < 26; i++) {

                TrieNode next = curr.children[i];

                if(next == null){
                    continue;
                }


                if(curr.isRoot){
                    next.failure = curr;
                }
                else{

                    TrieNode failure = curr.failure;

                    while(!failure.isRoot && failure.children[i] == null){
                        failure = failure.failure;
                    }

                    if (failure.children[i] != null) {
                        failure = failure.children[i];
                    }

                    next.failure = failure;
                }

                if (next.failure.isEnd) {
                    next.isEnd = true;
                }

                queue.add(next);
            }
        }

    }

    // kmp
    static long kmp(String origin){

        TrieNode curr = root;
        long result = 0;

        for(int i = 0; i < N; i++) {

            char ch = origin.charAt(i);

            while(!curr.isRoot && curr.children[ch-'A'] == null){
                curr = curr.failure;
            }

            if(curr.children[ch-'A'] != null){
                curr = curr.children[ch-'A'];
            }

            if(curr.isEnd){
                result++;
            }

        }

        return result;
    }

}
/*

10256 돌연변이

아호 코라식(Aho–Corasick) 문제

해당 문제는 아호 코라식 베이스에 문자열 전처리가 추가된 문제입니다.

문자열을 찾아내는 부분은 전형적인 아호 코라식 문제이기 때문에 해설을 넘기겠습니다.

그래서 데이터 전처리만 해주면 되는데, Java의 경우 제한시간이 6초입니다.
이는 데이터 전처리가 오래걸릴 수 있다는 점을 감안한 시간입니다.

저의 경우는 char[] 을 만들어서 start와 mid라는 변수를 둬서 start 변수의 크기만큼 앞글자를 세팅하고,
start ~ mid 구간 만큼 뒤집어주고, mid~M 구간을 덧붙이는 방법으로 전처리를 수행했습니다.

이 부분의 구현은 String의 substring()을 사용할 수도 있겠지만 제가 알기론 그게 때어오는데 N번의 반복, 거꾸로 돌리는데 또 N번 반복하며
String 자체의 오버헤드도 있기 때문에 느릴 것으로 예상했습니다. 따라서 substring은 사용하지 않고 순수하게 직접 구현하였습니다.

이 외에는 kmp의 결과가 int형을 벗어날 수 있다는 점만 유의한다면 큰 문제 없이 아호 코라식으로 문제를 해결할 수 있습니다.

*/