import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int N;
    static long answer, trees[];
    static Map<Integer,Integer> index;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st1, st2;

        N = Integer.parseInt(br.readLine());
        trees = new long[N*4];
        answer = 0;
        index = new HashMap<>();

        st1 = new StringTokenizer(br.readLine());
        st2 = new StringTokenizer(br.readLine());
        int[] arr = new int[N];

        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st1.nextToken());
            index.put(Integer.parseInt(st2.nextToken()), i);
        }

        for(int i = 0; i < N; i++){
            int b_idx = index.get(arr[i]);

            update(1, 1, N, b_idx);
            // b_idx+1 이후의 구간 합이 곧 쌍의 갯수이므로 이를 합산해줌
            answer += getTotal(1,1,N, b_idx+1,N);
        }
        System.out.println(answer);


    }

    static long getTotal(int node, int start, int end, int left, int right){

        if(right < start || left > end){
            return 0;
        }
        if(left <= start && right >= end){
            return trees[node];
        }

        return getTotal(node*2, start, (start+end)/2, left, right) +
                getTotal(node*2+1, (start+end)/2+1, end, left, right);

    }

    static long update(int node, int start, int end, int find){

        if(find < start || find > end){
            return trees[node];
        }
        if(start == end){
            trees[node]++; // 연결되었음을 표시함.
            return trees[node];
        }

        // 구간 합의 수를 바꿔줌.
        return trees[node] = update(node*2, start, (start+end)/2, find) + update(node*2+1, (start+end)/2+1, end, find);

    }

}

/*

7578 공장

세그먼트 트리 문제

해당 문제의 경우 어떻게 하면 중복없이 교차가 되는 쌍을 구할 수 있는지를 먼저 알아야합니다.

가령 다음과 같은 입력이 들어왔다고 해보겠습니다.

1   2   3   4 [a 배열]
3   4   1   2 [b 배열]

먼저 a배열의 순서대로 차례를 진행합니다.
1. a(배열)의 1과 b(배열)의 1을 연결합니다.
2. a의 2와 b의 2를 연결합니다.
이때까지는 아무런 교차가 없습니다.
3. a의 3을 b의 3과 연결합니다.
이때 교차하는 수는 1번 선과 2번 선으로 2개입니다.
여기서 주목할 점은 b배열에서 1과 2는 3보다 뒤에 있다는 점입니다.
즉, b 배열에서 나보다 뒤의 배열에 선이 연결되어있는 수가 곧 교차되는 쌍의 수입니다.

이걸 그대로 4번에도 적용해보면
4. a의 4와 b의 4를 연결하면, 각각 1, 2와 교차를 하게 됩니다.
이건 b 배열의 4보다 뒤에 있는 배열에 연결된 선의 숫자와 같습니다.

즉, 문제의 풀이의 핵심은 다음과 같습니다.
1. a배열의 값을 순서로 하여 b배열과 차례대로 연결했다고 표시해주기.
2. 내가 연결된 b 배열의 인덱스 X에서 X+1 이후부터 연결되어있는 선을 찾기.
3. 만약 연결된 선이 있다면 그 총합이 곧 쌍의 갯수. 즉, answer += 총합.

여기서 핵심은 2번의 과정에 세그먼트 트리를 이용하면 구간 합을 log N 만에 구할 수 있게 됩니다.
이를 토대로, 연결 여부 및 구간합을 세그먼트 트리를 이용하여 구현하면 풀이할 수 있습니다.

*/