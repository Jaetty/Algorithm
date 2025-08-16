import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int N, M;

    static List<Integer>[] mergeTree;
    static int[] arr;

    static void init(int node, int start, int end){

        if(start == end){
            mergeTree[node].add(arr[start-1]);
            return;
        }

        int mid = (start+end)/2;

        init(node*2, start, mid);
        init(node*2+1, mid+1, end);
        merge(node);
    }

    static void merge(int node){

        List<Integer> left = mergeTree[node*2];
        List<Integer> right = mergeTree[node*2+1];

        int left_idx = 0;
        int right_idx = 0;

        while(left_idx < left.size() && right_idx < right.size()){

            if(left.get(left_idx) < right.get(right_idx)){
                mergeTree[node].add(left.get(left_idx++));
            }
            else{
                mergeTree[node].add(right.get(right_idx++));
            }
        }

        while(left_idx < left.size()){
            mergeTree[node].add(left.get(left_idx++));
        }

        while(right_idx < right.size()){
            mergeTree[node].add(right.get(right_idx++));
        }
    }

    static int query(int node, int start, int end, int left, int right, int find){

        if(left > end || right < start){
            return 0;
        }

        if(left <= start && end <= right){
            return lower_bound(node, find);
        }

        int mid = (start+end)/2;
        int q1 = query(node*2, start, mid, left, right, find);
        int q2 = query(node*2+1, mid+1, end, left, right, find);

        return q1 + q2;

    }

    static int lower_bound(int node, int find){

        int left = 0;
        int right = mergeTree[node].size();

        while(left < right){

            int mid = (left+right)/2;
            if(find <= mergeTree[node].get(mid)){
                right = mid;
            }
            else{
                left = mid + 1;
            }
        }

        return left;

    }

    static int binary_search(int left, int right, int idx){

        int l = -0;
        int r = 10;

        while(l <= r){

            int mid = (l+r)/2;
            int result = query(1, 1, N, left, right, mid);

            if(result >= idx){
                r = mid - 1;
            }
            else{
                l = mid + 1;
            }

        }

        return r;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int size = 1;

        while(size < N){
            size = size <<1;
        }

        size *= 2;

        mergeTree = new List[size*2];
        arr = new int[N];

        for(int i = 0; i < size*2; i++){
            mergeTree[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        init(1,1, N);

        for(int i = 0; i < M; i++){

            st = new StringTokenizer(br.readLine());

            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            int idx = Integer.parseInt(st.nextToken());

            sb.append(binary_search(left, right, idx)).append("\n");

        }

        System.out.print(sb.toString());

    }
}
/*

7469 K번째 수

머지 소트 트리 + 이분 탐색 문제

@ 해당 문제는 풀이를 참고 했습니다.

문제의 핵심은 머지 소트 트리에서 특정 숫자 X보다 작은 값을 찾는 방법을 응용하여, 정렬된 구간에서 K 인덱스에 위치한 값을 출력하는 것입니다.
문제 자체는 머지 소트 트리 기반 문제이기 때문에, 머지 소트 트리 개념을 정리한 저의 블로그 주소를 남기겠습니다.
https://jaetty.tistory.com/39

머지 소트 트리에서 lower_bound로 X 이하 값의 총 개수를 구했을 때, 그 값이 K와 같다면
X 값이 정렬된 배열에서 K 위치에 있는 값이라는 뜻이 됩니다.

다만, 저의 경우 이 X값을 어떻게 찾아야 하는지 감을 못잡고 헤매다가 결국 정답을 참고하였습니다.
그 결과 이분 탐색을 이용하면 된다는 점을 알 수 있었습니다.

*/