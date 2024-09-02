import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, max;
    static int[][] tree;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        max = 0;
        int[][] input = new int[N][2];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int idx = Integer.parseInt(st.nextToken());
            int val = Integer.parseInt(st.nextToken());
            input[i][0] = idx;
            input[i][1] = val;
            max = Math.max(max, idx);
        }

        tree = new int[max*4][3];
        init(1, 1, max);

        for(int i=0; i<N; i++){
            update(1, 1, max, input[i][0], input[i][1]);
        }

        int answer = 0;

        answer += (tree[1][2] - tree[1][1])*tree[1][0] + tree[1][0];

        int nl = tree[1][1]-1;
        int nr = tree[1][2]+1;

        while(nl>0){

            int[] var = getMaxWidth(1, 1, max, 1, nl);
            answer += (nl - var[1])*var[0] + var[0];
            nl = var[1]-1;

        }

        while(nr<=max){

            int[] var = getMaxWidth(1, 1, max, nr, max);
            answer += (var[2] - nr)*var[0] + var[0];
            nr = var[2]+1;

        }

        System.out.println(answer);

    }

    static int[] init(int node, int start, int end){

        if(start == end){
            tree[node][0] = 0;
            tree[node][1] = start;
            tree[node][2] = start;
            return tree[node];
        }

        int[] left = init(node*2, start, (start+end)/2);
        int[] right = init(node*2+1, (start+end)/2+1, end);

        return tree[node] = getArray(left, right);

    }

    static int[] update(int node, int start, int end, int idx, int val){

        if(end < idx || idx < start){
            return tree[node];
        }

        if(start == end){
            tree[node][0] = val;
            return tree[node];
        }

        int[] left = update(node*2, start, (start+end)/2, idx, val);
        int[] right = update(node*2+1, (start+end)/2+1, end, idx, val);

        return tree[node] = getArray(left, right);
    }

    static int[] getMaxWidth(int node, int start, int end, int left, int right){

        if(right < start || end < left){
            return new int[] {-1,-1,-1};
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        int[] l = getMaxWidth(node*2, start, (start+end)/2, left, right);
        int[] r = getMaxWidth(node*2+1, (start+end)/2+1, end, left, right);

        return getArray(l, r);
    }

    static int[] getArray(int[] left, int[] right){

        if(left[0] == right[0]){
            return new int[]{left[0], left[1], right[2]};
        }
        else if(left[0] > right[0]){
            return left;
        }
        else{
            return right;
        }

    }

}

/*
 * !!!!!!! 세그먼트 풀이 (해당 문제는 스택/브루트포스로 풀이가 더육 효율적입니다.)
 *
 * !!! 해당 문제의 경우 스택과 브루트포스로 풀이하는 것이 더 효율적입니다. 그럼에도 세그먼트 트리로 풀이하였습니다.
 * !!! 그 이유는 이 문제와 매우 비슷하지만 조건이 더욱 추가된 문제를 세그먼트 트리로 풀이한 경험이 있습니다. 그 문제가 생각나서 세그먼트 트리 복습을 위해 풀이해보았습니다.
 *
 * 이 세그먼트 트리 풀이의 핵심은 가장 높은 기둥 값과, 그 높이의 기둥 값을 가진 가장 왼쪽 위치와 오른쪽 위치를 기억하는 점 입니다.
 * 그러면 세그먼트 트리에서 전체 구간에서 가장 높은 기둥과 그 기둥의 좌, 우의 값을 가져옵니다.
 * 지붕의 값은 곧 (우-좌)만큼의 기둥 높이가 됩니다.
 *
 * 이후 왼쪽과 오른쪽으로 구간을 나눠서 왼쪽과 오른쪽이 각각 0과 마지막 인덱스를 나타낼 때 까지 해당 구간에서 가장 큰 기둥과 좌 우 값을 구해서 지붕 값을 구해주면 됩니다.
 *
 * */