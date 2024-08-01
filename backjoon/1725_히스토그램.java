import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[] tree, arr;
    static int N;
    static long answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());

        tree = new int[4*N];
        arr = new int[N+1];
        answer = 0;

        arr[0] = Integer.MAX_VALUE;
        for(int i=1 ;i<=N; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        init(1, 1, N);
        recursive(1,N);

        System.out.print(answer);

    }

    static int init(int node, int start, int end){

        if(start==end){
            return tree[node] = start;
        }

        int left = init(node*2, start, (start+end)/2);
        int right = init(node*2+1, (start+end)/2+1, end);
        return tree[node] = arr[left] < arr[right] ? left : right;
    }

    static int getMin(int node, int start, int end, int left, int right){

        if(end < left || right < start){
            return 0;
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        int a = getMin(node*2, start, (start+end)/2, left, right);
        int b = getMin(node*2+1, (start+end)/2+1, end, left, right);

        return arr[a] < arr[b] ? a : b;
    }

    static void recursive(int left, int right){

        if(left > right){
            return;
        }

        int idx = getMin(1, 1, N, left, right);
        answer = Math.max(answer, arr[idx] * (right-(left-1)));

        recursive(left, idx-1);
        recursive(idx+1, right);

    }

}

/*
 * 세그먼트 트리 문제 (+ 스택으로 풀이 가능)
 *
 * @@@@@ 해당 문제는 풀이에 실패하여 해설을 참고하였습니다. @@@@@
 *
 * 세그먼트 트리를 공부하기 위해 해당 문제를 풀어보았지만 시간초과가 나왔습니다. 가장 큰 원인은 세그먼트 트리를 응용하지 못했던 부분이었습니다.
 * 이 문제의 핵심은 최소 값을 기억하는 것이 아닌 최솟값을 가진 인덱스 값을 기억하는 것 입니다.
 * 그리고 그 최솟값을 제외한 구간에서 다시 최솟값을 찾는 방식이 이 문제의 로직이었습니다.
 *
 * 예시 입력 N=7 {2 1 4 5 1 3 3} 을 기준으로 생각해보면
 * 1~N 중에서 최솟값을 가진 index는 2, 5 입니다.
 * 여기서 5를 출력했다고 하겠습니다. 그러면 1의 높이를 가진 arr로 만들 수 있는 넓이는 answer = N*arr[5] = 7 * 1입니다.
 * 이제 5 구간을 탐색에서 제외합니다.
 * 그러면 각각 1~4, 6~7 구간을 탐색합니다.
 *
 * 6~7부터 설명하면 각각 3이니까 2*3 = 6이 되고 다시 7~7구간을 검색해서 1*3 = 3의 결과를 얻습니다.
 * 1~4 구간은 2의 값이 1이므로 4*1을 도출 후 탐색 구간에서 제외되고 다시 1~1, 3~4 구간을 탐색합니다.
 * 1~1 구간은 2*1의 값이 도출되고, 3~4는 2*4의 값이 도출됩니다.
 * 이 중 가장 큰 값은 2*4=8이므로 8을 출력하면 되는 문제였습니다.
 *
 * */