import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[] tree, input;
    static int N, K;
    static final int MAX = 65536+1;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        tree = new int[MAX * 4];
        input = new int[N+1];
        long answer = 0;

        for(int i=1; i<=N; i++){
            input[i] = Integer.parseInt(br.readLine());
            update(1, 0, MAX, input[i], 1);

            if(i >= K){
                answer += find(1, 0, MAX, (K+1)/2);
                update(1, 0, MAX, input[i-K+1], -1);
            }
        }

        System.out.print(answer);

    }

    static void update(int node, int start, int end, int target, int val){

        if(target < start || target > end) return;
        tree[node] += val;

        if(start == end){
            return;
        }

        update(node*2, start, (start+end)/2, target, val);
        update(node*2+1, (start+end)/2+1, end, target, val);

    }

    static int find(int node, int start, int end, int k){

        if(start == end){
            return start;
        }

        if(tree[node*2] >= k) {
            return find(node*2, start, (start+end)/2, k );
        }
        return find(node*2+1, (start+end)/2+1, end, k - tree[node*2]);
    }

}

/*

1572 중앙값

세그먼트 트리 문제

@ 해당 문제는 풀이를 참고했습니다.
@ 저의 경우 세그먼트 트리로 풀이를 시도하였으나 핵심 로직을 찾는데 실패하여 다른 사람의 풀이를 참고했습니다.

풀이의 핵심은 다음과 같습니다.
1. 0 ~ 65536까지의 숫자가 몇개 있는지 기억하는 세그먼트 트리를 만든다.
2. 0~65536에서 갯수를 통해 (K+1)/2번째의 중앙값을 판단해줍니다.

2번을 쉽게 이해하기 위해 다음과 같은 예시가 있다고 해보겠습니다.
[1,2,3,4,5] 입력으로 이렇게 주어졌다면, 정답은 3이며, 중앙 값의 인덱스는 (5+1)/2 = 3이 됩니다.

이걸 세그먼트 트리 구조로 생각해보면 다음과 같습니다.

                [1,2,3,4,5] : 5개
                /         \
             [1,2,3] :3개  [4,5] : 2개
            /       \
         [1,2] :2개  [3] :1

여기서 각 배열의 총 갯수로만 주목해서 나타내보면 다음과 같은 형태가 됩니다.

                5
               / \
              3   2
             /\
            2  1

저희가 찾던 (K+1)/2 = 3이었습니다.
그럼 우선 맨 최초 꼭대기의 5에서부터 왼쪽으로 갈 때는 다음 총 갯수의 값이 (K+1)/2 = 3 이상이어야합니다.
왜냐면 앞에서부터 3번째에 해당하는 값을 찾고 있기 때문에, 반으로 갈랐을 때 반의 앞 부분의 총 갯수가 3보다 작으면 안되기 때문입니다.
이제 3에 도착했는데, 다시 반으로 쪼개고 보니 왼쪽의 총 갯수가 2 입니다. 3보다 작습니다.
그러면 오른쪽으로 들어가되, 왼쪽의 갯수만큼 빼줘야합니다. (3-2) = 1

이런 식으로 갯수를 중심으로 찾아가다가 start==end가 같은 경우에 도달하게 된 값이 중앙값이 됩니다.
이를 통해 update와 find 마다 log N만큼의 시간으로 문제를 풀이할 수 있게 됩니다.

*/