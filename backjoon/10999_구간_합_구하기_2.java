import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long[] tree;
    static long[] lazy;
    static long[] arr;

    static void propagate(int node, int start, int end) {

        // 만약 전파해야할 값이 애초에 없다면 그냥 나오기
        if(lazy[node] == 0){
            return;
        }

        // 총 합은 전체 길이 * 변화해야하는 값
        tree[node] += (end-start + 1L) * lazy[node];

        if(start != end){
            // lazy의 왼쪽 오른쪽 자식 노드에게 값을 전파함
            lazy[node*2] += lazy[node];
            lazy[node*2+1] += lazy[node];
        }
        // 전파가 끝나면 0으로 만들어서 전파할 값이 없다는 것을 명시함
        lazy[node] = 0;
    }

    static long init(int node, int start, int end){

        if(start == end){
            return tree[node] = arr[start];
        }

        int mid = (start+end)/2;
        return tree[node] = init(node*2, start, mid) + init(node*2+1,mid+1, end);

    }

    static void update(int node, int start, int end, int left, int right, long val){

        // update가 재귀적으로 진행되면서 해당 구간에 도착했다면 전파(갱신)이 필요함. 그래서 전파(갱신)해줌
        propagate(node, start, end);

        if(start > right || end < left){
            return;
        }

        if(left <= start && end <= right){
            // 실제 변화가 일어나는 구간에 도착했다면 여기서는 lazy 값을 증가시킨 후 전파(갱신)해줌
            lazy[node] += val;
            propagate(node, start, end);
            return;
        }

        int mid = (start+end)/2;
        update(node*2, start, mid, left, right, val);
        update(node*2+1, mid+1, end, left, right, val);
        tree[node] =  tree[node*2] + tree[node*2+1];

    }

    static long query(int node, int start, int end, int left, int right){

        // query로 이 구간에 도착했으니까 갱신이 필요함. 그래서 갱신해줌
        propagate(node, start, end);

        if(start > right || end < left){
            return 0;
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        int mid = (start+end)/2;
        return query(node*2, start, mid, left, right) + query(node*2+1, mid+1, end, left, right);

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        arr = new long[N+1];
        tree = new long[N*4];
        lazy = new long[N*4];


        for(int i=1; i<=N; i++){
            arr[i] = Long.parseLong(br.readLine());
        }

        init(1, 1, N);

        int cmd=0, a=0, b=0;
        long c=0;

        for(int i=0; i<M+K; i++){
            st = new StringTokenizer(br.readLine());

            cmd = Integer.parseInt(st.nextToken());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            if(cmd == 1){
                c = Long.parseLong(st.nextToken());
                update(1, 1, N, a, b, c);
            }
            else{
                sb.append(query(1, 1, N, a, b)).append("\n");
            }

        }

        System.out.print(sb.toString());

    }

}

/*

10999 구간 합 구하기2

lazy propagation 세그먼트 트리 문제

해당 문제의 경우 먼저 lazy propagation(느리게 전파? 갱신?되는) 세그먼트 트리의 개념을 이해해야합니다.

보통의 세그먼트 트리는 단일 부분에 대해서 수정이 이루어지게 됩니다. 그렇게 되면 log N의 시간으로 최대, 최소, 총합 등의 결과를 쉽게 얻습니다.
그런데 해당 문제와 같이 단일 부분이 아닌 특정 구간에 동시에 변화가 구간의 길이가 M이면 M log N 만큼 반복이 필요합니다.
만약, 최대 입력 구간이 N이고 update를 10만번 시키게 된다면?? 10만 * N Log N 번의 연산이 필요하게 되니까 매우 긴 시간이 필요하게 됩니다.

이때 필요한 것이 lazy propagation 세그먼트 트리 입니다.
느리게 전파 or 갱신 된다는 의미의 lazy propagation이라는 말에서 알 수 있듯, 최대한 끝에 노드까지 갱신하지 않게 만드는 것이 특징입니다.
즉, 반드시 업데이트가 필요한 구간에서 최소한의 깊이에서 변화를 주기만 하는 것이 목표입니다.

다음과 같은 배열이 있다고 했을 때 예시를 들어보겠습니다.
arr = [1,2,3,4,5]
입력1 : 1~5구간 까지 +3을 해라.
입력2 : 1~5구간 까지 총합을 출력해라

원래 세그먼트 트리라면 1~5까지 반복하면서 하나하나 수정을 한 후 총합을 출력할 것입니다.
하지만, lazy의 경우는 맨 최초의 tree[1]만 수정하는 것 입니다. tree[1] += 5*3 이러면 결과는 똑같습니다.
여기서 자식들 노드는 전혀 수정이 되지 않은 상태라는 것 입니다. 최대한 수정을 미루어서 덜 깊이 들어가는 것이 목표입니다.

즉, 모든 자식 노드를 방문하지 않고 최대한 갱신을 미뤄서, 실제로 필요한 시점에만 갱신하는 것이 lazy 세그먼트 트리입니다.

해당 문제는 결국, lazy propagation 세그먼트 트리 구현할 수 있는지 묻는 문제입니다.
그렇기 때문에 총합을 리턴해주는 lazy propagation 세그먼트 트리를 구현하기만 하면 쉽게 풀이할 수 있습니다.

*/