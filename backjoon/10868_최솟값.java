import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long[] tree, input;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        tree = new long[4*N];
        input = new long[N+1];

        for(int i=1; i<=N; i++){
            input[i] = Integer.parseInt(br.readLine());
        }

        init(1, 1, N);

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            sb.append(min(1, 1, N, left, right)+"\n");
        }

        System.out.print(sb.toString());

    }

    static long init(int node, int start, int end){

        if(start==end){
            return tree[node] = input[start];
        }

        return tree[node] = Math.min(init(node*2, start, (start+end)/2), init(node*2+1, (start+end)/2+1, end) );
    }

    static long min(int node, int start, int end, int left, int right){

        if(end < left || right < start){
            return Integer.MAX_VALUE;
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        return Math.min(min(node*2, start, (start+end)/2, left, right), min(node*2+1, (start+end)/2+1, end, left, right));

    }


}

/*
 * 세그먼트 트리 문제
 *
 * 해당 문제는 세그먼트 트리 기초 문제입니다.
 * 세그먼트 트리의 구간 합을 해주는 부분을 최소 값을 구하는 방법으로 변경하면 해결할 수 있는 문제입니다.
 *
 *
 * */