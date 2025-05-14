import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int[] idx, trees;
    static int n, m;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {

            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());

            idx = new int[n+1];
            trees = new int[(n+m)*4];
            int index = n;

            for (int i = 1; i <= n; i++) {
                idx[i] = index--;
            }
            init(1,1,n+m);
            st = new StringTokenizer(br.readLine());

            index = n+1;

            for (int i = 0; i < m; i++) {
                int input = Integer.parseInt(st.nextToken());
                int left = idx[input];
                sb.append(get(1,1,n+m, left+1, n+m)+" ");

                update(1,1,n+m,left, left,-1);
                update(1,1,n+m,index, index,1);

                idx[input] = index++;
            }

            sb.append("\n");

        }

        System.out.print(sb.toString());

    }

    static int init(int node, int start, int end) {

        if(start > n){
            return 0;
        }
        if(start == end){
            trees[node] = 1;
            return 1;
        }

        return trees[node] = init(node*2, start, (start+end)/2) +
                init(node*2+1, (start+end)/2+1, end);

    }

    static void update(int node, int start, int end, int left, int right, int val) {

        if(start > right || end < left){
            return;
        }
        if(start == end){
            trees[node] += val;
            return;
        }

        trees[node] += val;
        update(node*2, start, (start+end)/2, left, right, val);
        update(node*2+1, (start+end)/2+1, end, left, right, val);
    }

    static int get(int node, int start, int end, int left, int right) {
        if(start > right || end < left){
            return 0;
        }
        if(left <= start && end <= right){
            return trees[node];
        }

        return get(node*2, start, (start+end)/2, left, right) +
                get(node*2+1, (start+end)/2+1, end, left, right);
    }
}

/*

3653 영화 수집

세그먼트 트리 문제

문제의 핵심 포인트:
1. 어떤 영화의 위치를 찾으면 그것보다 뒤에 있는 모든 영화의 갯수를 세면 된다.
2. 영화의 위치는 항상 맨 위에 쌓인다면, 충분한 크기의 배열을 만들기만 하면 된다 (n+m) 크기

2번 포인트의 경우 n+m으로 배열을 만드는 이유는 m번 맨 위로 옮기는 작업이 수행됩니다.
그렇기 때문에 기존의 n 배열에서 서로 자리를 바꿔주지 말고 n+1의 위치로 숫자를 옮겨주면 됩니다.

문제를 쉽게 숫자 배열로 예시를 들어보겠습니다.
만약 주어진 n = 5, m = 3인데 각각 해당 위치에 값이 있다를 1로 표시하면 다음과 같습니다.

 1 2 3 4 5 6 7 8 : idx
[1,1,1,1,1,0,0,0] : 배열 값 (8에 가까울수록 맨 위라는 뜻)

여기서 3번 인덱스 다음에 몇 개의 DVD 있나요? 그야 4와 5에 DVD가 있으니 2개가 존재합니다.
즉, 4부터 8까지 배열의 값을 모두 더한 값과 같습니다.
3번 DVD를 뽑았으니 이 DVD는 맨 위에 가야합니다. 3을 6번으로 옮겨줍시다.

 1 2 3 4 5 6 7 8 : idx
[1,1,0,1,1,1,0,0] : 배열 값

이때 2번 인덱스 위로 몇 개의 DVD가 있는지 보면 4,5,6에 있으므로 3개가 됩니다.
이후 또, 2는 7번에 옮겨주면 됩니다.

여기서 규칙성을 파악하셨으리라 생각합니다.
1. DVD의 위치란 그 DVD가 있는 인덱스 값 +1 부터 마지막 n+m까지의 합을 구하면 되고,
2. DVD를 맨 위로 옮기는 작업은 n+1 위치부터 시작하여 m의 크기가 될 때까지 옮겨주면 되는 것 입니다.

그렇다면 핵심은 1번 작업을 어떻게 빠르게 수행하는가 입니다.
여기서 떠올릴 수 있는 방법이 구간의 합을 log N에 구하는 세그먼트 트리입니다.

결국 세그먼트 트리를 이용하여 index +1 ~ n+m 까지의 합산 구하기와 위치 수정하기를 수행하면
m 3log(n+m)만에 작업을 완료할 수 있습니다. (update 2번, get 1번 수행하니까 3번의 log N을 수행)

위의 로직을 세그먼트 트리를 활용하여 그대로 구현하면 문제를 해결할 수 있습니다.

*/