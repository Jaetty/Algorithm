import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int answer;

    static class Trie {
        Map<Character, Trie> children;
        // 여기 이하는 다 삭제해도 된다는걸 나타냄
        boolean erasable;
        // 파일 수를 count함
        int count;

        Trie() {
            children = new HashMap<>();
            erasable = true;
            count = 0;
        }
        void clear() {
            this.erasable = true;
            this.count = 0;
            this.children.clear();
        }
    }

    static Trie root;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        root = new Trie();
        int TC = Integer.parseInt(br.readLine());

        for(int tc = 0; tc < TC; tc++) {

            int N = Integer.parseInt(br.readLine());
            root.clear();
            answer = N;

            // 지워야 하는 파일로 트라이를 구성함
            for(int i = 0; i < N; i++) {
                add(br.readLine());
            }

            N = Integer.parseInt(br.readLine());

            // 삭제 하지 말아야 하는 파일 처리
            for(int i = 0; i < N; i++) {
                setNoErase(br.readLine());
            }

            checkErase(root);
            sb.append(answer).append("\n");

        }

        System.out.print(sb.toString());
    }

    static void add(String input) {
        Trie cur = root;
        cur.count++;

        for (int i = 0; i < input.length(); i++) {
            if(!cur.children.containsKey(input.charAt(i))) {
                cur.children.put(input.charAt(i), new Trie());
            }
            cur = cur.children.get(input.charAt(i));
            cur.count++;
        }
    }

    static void setNoErase(String input) {

        // 삭제해야하는 파일이 하나라도 있다면 절대로 rm * 을 할 수 없다.
        Trie cur = root;
        cur.erasable = false;

        // 삭제해야하지 말아야하는 파일이 있다면 삭제 하지 말아야한다고 표시
        // 만약 아예 그런 파일이 없다면 그냥 나오기.
        for (int i = 0; i < input.length(); i++) {

            if(cur.children.containsKey(input.charAt(i))) {
                cur = cur.children.get(input.charAt(i));
                cur.erasable = false;
            }
            else{
                break;
            }
        }
    }

    static void checkErase(Trie cur) {

        if(cur.erasable) {
            answer -= cur.count-1;
            return;
        }

        for(char next : cur.children.keySet()) {

            checkErase(cur.children.get(next));

        }
    }
}
/*

5446 용량 부족

트라이 문제

해당 문제의 풀이 로직은 다음과 같습니다.

1. 삭제해야 하는 파일들을 Trie에 저장합니다. 이때 각 노드마다 count 값을 기록하여 해당 노드를 거쳐 가는 삭제 파일의 총 개수를 기억합니다.
2. 삭제하면 안 되는 파일들을 Trie에 표시합니다.
3. Trie를 순회하며 현재 노드가 삭제 가능이라면, 해당 노드 이하의 파일들을 한 번에 삭제해주고, 그만큼 연산 횟수를 줄여줍니다.

문제의 경우, 입력으로 주어지는 파일의 개수 N은 최대 1000개이며 테스트 케이스는 최대 20개입니다.
단순히 문자열 비교로는 불필요한 중복 계산이 많아질 수 있으므로, Trie를 통해 공통 접두어를 공유하며 관리하는 것이 효율적입니다.

핵심 아이디어는 “삭제할 수 있는 가장 큰 상위 노드를 만나면 거기서 바로 삭제하고 더이상 탐색하지 않는 것"입니다.
이를 위해 erasable 이라는 boolean 변수로 "여기부터는 이하 모두 삭제해도 돼"라는 표식을 남겨두는 것 입니다.

또한, 1번 로직을 통해 count 값을 두어서 해당 노드 이하에 몇개의 파일이 있는지 기억해두었습니다.
그렇다면 만약 파일 이름이 'a'로 시작하는 파일이 30개 있다고 해보겠습니다.
그러면 'rm a*'만 해주면 30개를 한번에 삭제할 수 있습니다.

그렇다면 원래라면 30번의 삭제를 일일이 해주어야하는 반복이 -> 1번으로 줄어듭니다.
이는 (원래 해줘야하는 반복 수 - (a의 파일수-1))로 줄어드는 것 입니다.

이러한 로직을 바탕으로 트라이 기반 구현을 해주면 풀이할 수 있는 문제입니다.

*/