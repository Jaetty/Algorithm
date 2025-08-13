import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, Q;

    static class TrieNode{

        TrieNode[] children;
        TrieNode fail;
        boolean isEnd;
        boolean isRoot;

        TrieNode(boolean isRoot) {
            children = new TrieNode[26];
            this.isRoot = isRoot;
        }

    }

    static TrieNode root;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        root = new TrieNode(true);

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            TrieNode curr = root;

            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (curr.children[c - 'a'] == null) {
                    curr.children[c - 'a'] = new TrieNode(false);
                }
                curr = curr.children[c - 'a'];
            }
            curr.isEnd = true;

        }

        failure();

        Q = Integer.parseInt(br.readLine());
        for (int i = 0; i < Q; i++) {
            if(kmp(br.readLine(), root)){
                sb.append("YES").append("\n");
            }
            else sb.append("NO").append("\n");
        }

        System.out.print(sb.toString());

    }

    static void failure(){

        Queue<TrieNode> queue = new ArrayDeque<>();
        root.fail = root;
        queue.add(root);

        // 아이디어는 BFS로 탐색하면서 내 failure가 조건을 만족할 때까지 뒤로 계속 돌아가는 것.
        // 반복 조건은 root가 아니어야하고, 그 노드의 자식노드에 나와 같은 char가 없어야만함.
        while (!queue.isEmpty()) {

            TrieNode curr = queue.poll();

            for(int i = 0; i < 26; i++) {

                TrieNode next = curr.children[i];

                if (next == null) {
                    continue;
                }

                if(curr.isRoot) {
                    next.fail = curr;
                } else{

                    TrieNode failure = curr.fail;

                    while (!failure.isRoot && failure.children[i] == null) {
                        failure = failure.fail;
                    }

                    if (failure.children[i] != null) {
                        failure = failure.children[i];
                    }

                    next.fail = failure;

                }

                if (next.fail.isEnd) {
                    next.isEnd = true;
                }

                queue.add(next);

            }
        }
    }

    static boolean kmp(String origin, TrieNode root) {
        TrieNode curr = root;

        boolean isFind = false;

        for (int i = 0; i < origin.length(); i++) {
            int idx = origin.charAt(i) - 'a';

            while (!curr.isRoot && curr.children[idx] == null) {
                curr = curr.fail;
            }

            if (curr.children[idx] != null) {
                curr = curr.children[idx];
            }

            if (curr.isEnd) {
                isFind = true;
            }
        }

        return isFind;
    }

}
/*

9250 문자열 집합 판별

아호 코라식(Aho–Corasick) 문제

해당 문제는 아호 코라식을 배울 수 있는 가장 기초적인 문제입니다.

아호 코라식의 핵심은 다수의 패턴을 효율적으로 한 번에 검색할 수 있게 만드는 것입니다.
KMP의 경우는 하나의 문자열안에서 다른 하나의 문자열 패턴을 찾는게 목표인 일대일의 문자열 찾기 방식입니다.
아호 코라식은 다수의 문자열에서 하나의 문자열을 패턴을 찾을 때 유용한 알고리즘입니다.

아호 코라식을 위해선 트라이가 있어야합니다.
Trie에 모든 문자열 패턴을 저장한 뒤, 각 노드마다 실패 링크(failure link)를 미리 계산해 둡니다.

실패 링크의 원리는 다음과 같습니다.
"현재 노드에서 매칭이 끊어졌을 때, 가장 긴 접두사가 되는 다른 경로로 이동한다."
예를 들어 abc라는 패턴이 있고, 현재 abx에서 매칭이 끊어졌다면, b부터 이어갈 수 있는 가장 긴 경로를 찾아서 탐색을 이어갑니다.

이 과정을 BFS로 모든 노드에 대해 수행하면, 패턴 검색 시 한 글자씩만 확인하면서도 다수의 패턴을 동시에 체크할 수 있게 됩니다.

이후 실제 문자열을 탐색할 때는 Trie를 따라가며 없으면 실패 링크로 돌아가고, 있으면 다음으로 이동합니다.
이때 어떤 노드가 isEnd면, 해당 위치까지의 문자열에 패턴이 포함된 것입니다.

*/