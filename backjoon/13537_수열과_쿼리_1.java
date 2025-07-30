import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static List<Integer>[] mergeTree;
    static int[] arr;
    static int N, M;

    static List<Integer> init(int node, int start, int end){

        mergeTree[node] = new ArrayList<>();

        if(start == end){
            mergeTree[node].add(arr[start]);
            return mergeTree[node];
        }

        int mid = (start+end)/2;
        List<Integer> left = init(node*2, start, mid);
        List<Integer> right = init(node*2+1, mid+1, end);

        merge(left, right, mergeTree[node]);
        return mergeTree[node];

    }

    static void merge(List<Integer> left, List<Integer> right, List<Integer> node){

        int leftIdx = 0, rightIdx = 0;

        while(left.size() > leftIdx && right.size() > rightIdx){

            if(left.get(leftIdx) < right.get(rightIdx)){
                node.add(left.get(leftIdx++));
            }
            else{
                node.add(right.get(rightIdx++));
            }

        }

        while(left.size() > leftIdx){
            node.add(left.get(leftIdx++));
        }

        while(right.size() > rightIdx){
            node.add(right.get(rightIdx++));
        }

    }

    static int find(int node, int start, int end, int left, int right, int k){

        if(start > right || end < left){
            return 0;
        }

        if(left <= start && end <= right){
            return mergeTree[node].size() - upperBound(node, k);
        }

        int mid = (start+end)/2;
        int p1 = find(node*2, start, mid, left, right, k);
        int p2 = find(node*2+1, mid+1, end, left, right, k);

        return p1 + p2;
    }

    static int upperBound(int node, int find){

        int left = 0;
        int right = mergeTree[node].size();

        while(left < right){

            int mid = (left+right)/2;

            if(find < mergeTree[node].get(mid)){
                right = mid;
            }
            else{
                left = mid + 1;
            }

        }

        return left;

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        mergeTree = new List[N*4];
        // 0은 비어있음
        mergeTree[0] = new ArrayList<>();

        arr = new int[N+1];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=1; i<=N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        M = Integer.parseInt(br.readLine());
        init(1, 1, N);

        StringBuilder sb = new StringBuilder();

        for(int q=0; q<M; q++){

            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            sb.append(find(1, 1, N, left, right, k)).append("\n");

        }

        System.out.print(sb.toString());


    }

}

/*

13537 수열과 쿼리 1

머지 소트 트리 문제

해당 문제의 경우 먼저 머지 소트 트리의 개념을 이해해야합니다.

머지 소트 트리는 세그먼트 트리의 특수한 형태로 말그대로 머지 소트의 원리를 세그먼트 트리에 활용한 트리입니다.
세그먼트 트리는 구조를 보면 각 노드는 어떤 구간을 나타냅니다. 이 구간마다 정렬되어있는 배열이 있다고 생각하시면 됩니다.

문제를 보게 되면 배열이 주어지고 특정 구간에서 K보다 큰 수가 몇개인지 묻고 있습니다.
만약 단순히 구간 개념 없이 정렬된 배열에서 K보다 큰 수가 몇개인지 찾으려면 어떻게 해야할까요?
가장 좋은 방법은 이분탐색으로 K보다 큰 수가 시작되는 위치를 찾으면 (전체 길이 - 인덱스 위치)로 K보다 큰 수의 개수를 찾을 수 있습니다.

앞서 말했듯 머지 소트 트리는 세그먼트 트리 구조로 각각의 노드의 구간에 해당하는 값들이 정렬되어있다고 했습니다.
그렇다면 위의 개념을 그대로 적용하게 되면, 머지 소트 트리로 어떤 구간에 가서 K보다 큰 수가 몇개인지 알아내서 합산하면 된다는 점을 알 수 있습니다.

만약 입력된 배열이 [5, 4, 3, 2, 1] 이라고 했을 때
1~4구간에서 2보다 큰게 몇개? 라고 물어보게 된다면 아래와 같이 세그먼트 트리가 구성되어있다고 생각해보시면,

[5]     [4]     [3]     [2]     [1]

    [4,5]          [2,3]     [1]

       [2,3,4,5] <= tree[3]  [1] <= tree[2]

             [1,2,3,4,5] <= tree[1]

tree[3]에서 이분탐색으로 2보다 큰 값이 위치한 인덱스가 1이므로 전체 배열 길이 4 - 1 = 3으로 2보다 큰 숫자가 3개 있다는 것을 바로 알 수 있습니다.
이렇게 머지 소트 트리를 이용하면 세팅에 N Log N + 탐색에 Log^2 N 시간에 해당 문제를 풀이할 수 있게 됩니다.

*/