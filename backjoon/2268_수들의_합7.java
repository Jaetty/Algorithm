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

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if(command==0){
                if(y < x){
                    int temp = y;
                    y = x;
                    x = temp;
                }
                sb.append(sum(1,1,N,x,y)+"\n");
            }
            else{
                update(1, 1, N, x, y-arr[x]);
                arr[x] = y;
            }

        }

        System.out.print(sb.toString());

    }

    static long sum(int node, int start, int end, int left, int right){

        if(end < left || right < start){
            return 0;
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        return sum(node*2, start, (start+end)/2, left, right) + sum(node*2+1, (start+end)/2+1, end, left, right);

    }

    static void update(int node, int start, int end, int idx, long diff){

        if(idx < start || end < idx){
            return;
        }

        tree[node] += diff;

        if(start!=end){
            update(node*2, start, (start+end)/2, idx, diff);
            update(node*2+1, (start+end)/2+1, end, idx, diff);
        }


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