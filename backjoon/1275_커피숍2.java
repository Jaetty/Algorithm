import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long[] tree, arr;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        tree = new long[4*N];
        arr = new long[N+1];

        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        init(1, 1, N);

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if(y < x){
                int temp = y;
                y = x;
                x = temp;
            }

            sb.append(sum(1,1,N,x,y)+"\n");

            int target = Integer.parseInt(st.nextToken());
            long diff = Integer.parseInt(st.nextToken());
            update(1,1,N, target, diff - arr[target]);
            arr[target] = diff;


        }

        System.out.print(sb.toString());

    }

    static long init(int node, int start, int end){

        if(start==end){
            return tree[node] = arr[start];
        }

        return tree[node] = init(node*2, start, (start+end)/2) + init(node*2+1, (start+end)/2+1, end);
    }

    static void update(int node, int start, int end, int target, long diff){

        if( target < start || end < target){
            return;
        }

        tree[node] += diff;

        if(start!=end){
            update(node*2, start, (start+end)/2, target, diff);
            update(node*2+1, (start+end)/2+1, end, target, diff);
        }

    }

    static long sum(int node, int start, int end, int left, int right){

        if( right < start || end < left){
            return 0;
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        return sum(node*2, start, (start+end)/2, left, right) + sum(node*2+1, (start+end)/2+1, end, left, right);

    }

}

/*
 * 세그먼트 트리 문제
 *
 * 해당 문제는 세그먼트 트리 기초 문제입니다.
 * 이 문제의 주의점은 들어오는 x와 y의 경우 x>y인 경우가 있을 수 있다는 점 입니다. 이 점을 유의해서 수정하는 코드를 만들어야합니다.
 *
 *
 * */