import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int tree[], input[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        String line;

        while((line = br.readLine()) != null) {

            st = new StringTokenizer(line);

            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            tree = new int[N*4];
            input = new int[N+1];

            st = new StringTokenizer(br.readLine());
            for(int i=1; i<=N; i++) {
                input[i] = value(Integer.parseInt(st.nextToken()));
            }
            init(1, 1, N);

            for(int i=0; i<K; i++){
                st = new StringTokenizer(br.readLine());
                char c = st.nextToken().charAt(0);
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                if(c=='P'){
                    int val = get(1, 1, N, a, b);
                    if(val == 0){
                        sb.append(0);
                    }
                    else{
                        sb.append(val < 0 ? "-" : "+");
                    }
                }
                else{
                    b = value(b);
                    update(1, 1, N, a, b);
                }

            }
            sb.append('\n');
        }

        System.out.println(sb.toString());

    }

    static int value(int val){

        if(val == 0){
            return 0;
        }
        return val < 0 ? -1 : 1;
    }

    static int init(int node, int start, int end){

        if(start == end){
            return tree[node] = input[start];
        }

        return tree[node] = init(node*2, start, (start+end)/2) * init(node*2+1, (start+end)/2+1, end);

    }

    static int update(int node, int start, int end, int idx, int val){

        if(idx < start || idx > end){
            return tree[node];
        }

        if(start == end){
            return tree[node] = val;
        }

        return tree[node] = update(node*2, start, (start+end)/2, idx, val) * update(node*2+1, (start+end)/2+1, end, idx, val);

    }

    static int get(int node, int start, int end, int left, int right){

        if(right < start || left > end){
            return 1;
        }

        if(left <= start && end <= right){
            return tree[node];
        }

        if(start == end){
            return tree[node];
        }

        return get(node*2, start, (start+end)/2, left, right) * get(node*2+1, (start+end)/2+1, end, left, right);

    }


}

/*

5676 음주 코딩

세그먼트 트리 문제

해당 문제는 세그먼트 트리를 이용하면 풀이가 가능한 단순한 문제입니다.
다만, 다음과 같은 이 문제의 주요 포인트를 잘 지켜야합니다.
1. EOF 처리하기
2. 숫자 범위는 long형을 뛰어넘음.

먼저 BufferedReader로 1번을 해결하려면 다음과 같은 코드를 작성하면 됩니다.
while((line = br.readLine()) != null)

이렇게하면 EOF를 감지할 수 있었습니다.

두 번째로 숫자 범위의 경우
어차피 문제는 +-0를 묻고 있습니다. 그러니까 굳이 들어오는 값을 기억할 필요 없이, 1, 0, -1만 기억해두면 됩니다.

여담으로 저의 경우는 처음에 입력 N만큼 update를 사용하여 입력 값을 세팅하려고 했는데 시간초과가 발생했습니다.
그래서 입력 값을 input 배열에 받고 한번의 init 만으로 해결하는 방법을 도입하니 문제가 해결되었습니다.

*/