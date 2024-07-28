import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long[][] tree;
    static long[] arr;
    static int N;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());

        tree = new long[4*N][2];
        arr = new long[N+1];

        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        init(1, 1, N);

        int M = Integer.parseInt(br.readLine());

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if(command==2){
                sb.append(min(1,1,N,x,y)[1]+"\n");
            }
            else{
                update(1, 1, N, x, y);
            }

        }

        System.out.print(sb.toString());

    }

    static long[] init(int node, int start, int end){

        if(start == end){
            tree[node][0] = arr[start];
            tree[node][1] = start;
            return tree[node];
        }

        long[] left_result = init(node*2, start, (start+end)/2);
        long[] right_result = init(node*2+1, (start+end)/2+1, end);

        if(left_result[0] == right_result[0]){
            tree[node][0] = left_result[0];
            tree[node][1] = Math.min(left_result[1], right_result[1]);
            return tree[node];
        }
        else if(left_result[0] > right_result[0]){
            tree[node][0] = right_result[0];
            tree[node][1] = right_result[1];
            return right_result;
        }
        else{
            tree[node][0] = left_result[0];
            tree[node][1] = left_result[1];
            return left_result;
        }

    }

    static long[] min(int node, int start, int end, int left, int right){

        if(end < left || right < start){
            return new long[]{Long.MAX_VALUE, 4*N};
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        long[] left_result =min(node*2, start, (start+end)/2, left, right);
        long[] right_result = min(node*2+1, (start+end)/2+1, end, left, right);


        if(left_result[0] == right_result[0]){
            return new long[]{left_result[0], Math.min(left_result[1], right_result[1])};
        }
        else if(left_result[0] > right_result[0]){
            return right_result;
        }
        else{
            return left_result;
        }

    }

    static long[] update(int node, int start, int end, int idx, long val){

        if(idx < start || end < idx){
            return tree[node];
        }

        if(start == end){
            tree[node][0] = val;
            return tree[node];
        }

        long[] left_result = update(node*2, start, (start+end)/2, idx, val);
        long[] right_result = update(node*2+1, (start+end)/2+1, end, idx, val);

        if(left_result[0] == right_result[0]){
            tree[node][0] = left_result[0];
            tree[node][1] = Math.min(left_result[1], right_result[1]);
            return tree[node];
        }
        else if(left_result[0] > right_result[0]){
            tree[node][0] = right_result[0];
            tree[node][1] = right_result[1];
            return right_result;
        }
        else{
            tree[node][0] = left_result[0];
            tree[node][1] = left_result[1];
            return left_result;
        }


    }

}

/*
 * 세그먼트 트리 문제
 *
 * 해당 문제는 세그먼트 트리를 응용한 문제입니다.
 * 문제의 핵심은 작은 값을 출력하는 것이 아닌 작은 값의 인덱스 값(작은 값이 여러 개라면 가장 작은 인덱스 값)을 출력하는 것 입니다.
 * 제가 해결한 방법은 트리가 가장 작은 값과, 그 작은 값이 위치한 index 값을 같이 저장/수정하는 방법으로 해결하였습니다.
 * 배열의 [0]에는 가장 작은 값을, [1]에는 그 값이 존재하는 인덱스를 기억하게 만들고
 * 바텀 업 방식으로 누가 가장 작은 값과 가장 작은 인덱스 값을 가지고 있는지를 확인하여 이를 갱신 / 출력하는 방식으로 풀이가 가능합니다.
 *
 * */