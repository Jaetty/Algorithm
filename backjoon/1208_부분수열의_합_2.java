import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, S;
    static int[] inputs;
    static long answer;
    static List<Integer> left, right;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());


        inputs = new int[N];
        st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            inputs[i] = Integer.parseInt(st.nextToken());
        }

        left = new ArrayList<>();
        right = new ArrayList<>();

        recursive(0, 0, N/2,0, left);
        recursive(0, N/2, N,0, right);

        Collections.sort(left);
        Collections.sort(right);

        int lp = 0, rp = right.size()-1;

        while(lp < left.size() && rp >= 0){

            int leftValue = left.get(lp);
            int rightValue = right.get(rp);

            int sum = leftValue + rightValue;

            if(sum == S){

                long lc = 0;
                long rc = 0;

                while(lp < left.size() && leftValue == left.get(lp)){
                    lp++;
                    lc++;
                }

                while(rp>=0 && rightValue == right.get(rp)){
                    rp--;
                    rc++;
                }
                answer += lc*rc;
            }
            else if(sum < S){
                lp++;
            }
            else{
                rp--;
            }

        }


        System.out.println(answer);

    }

    static void recursive(int depth, int index, int max, int sum, List<Integer> list){

        if(depth>0){
            if(sum==S) answer++;
            list.add(sum);
        }

        for(int i=index; i<max; i++){
            recursive(depth+1, i+1, max, sum+inputs[i], list);
        }

    }

}

/*
* 중간에서 만나기 + (이분 탐색 OR 투 포인터) 문제
*
* 해당 문제는 중간에서 만나기 기법을 이용하여 풀이 가능한 문제입니다.
* 입력으로 들어오는 N의 최대 크기는 40입니다. 하지만 40^2으로는 시간 초과를 피할 수 없게 되기 때문에 문제를 좀 더 작게 나눠야 합니다.
*
* 문제의 포인트는 다음과 같습니다.
* 1. N을 반으로 나눠서 O((N/2)^2)의 시간으로 각각 만들 수 있는 모든 부분 수열을 만든다. 이렇게 나온 결과를 left와 right라고 하겠습니다.
* 2. 1에서 만든 left의 원소들을 하나씩 불러서 그 원소가 S가 되기 위해 더해야하는 값을 B에서 찾아서 그 갯수 만큼 카운트해줍니다.
* 3. 2를 수행하기 위해서 이분 탐색 혹은 투 포인터를 이용합니다.
*
* 예시 입력으로 다음과 같이 주어졌다면
* 3 6
* 1 2 3
*
* 배열을 반으로 나누면 1 / 2, 3 입니다.
* left와 right에 해당 배열들로 만들 수 있는 모든 부분 수열 합의 결과를 저장합니다.
* left = {1}
* right = {2, 3, 5(2+3)}
*
* 이후 left의 원소인 1을 꺼내서 1이 6이 되기 위해 더해야하는 값은 5이고 right에 5가 있으므로 count 값을 늘려줍니다.
*
* 이와 같은 방법을 코드로 표현하면 문제를 해결할 수 있습니다.
*
*
* 아래는 이분 탐색으로 풀이한 정답입니다.
*
*
*

import java.io.*;
import java.util.*;


public class Main {

    static int N, S;
    static int[] inputs;
    static long answer;
    static List<Integer> left, right;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());


        inputs = new int[N];
        st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            inputs[i] = Integer.parseInt(st.nextToken());
        }

        left = new ArrayList<>();
        right = new ArrayList<>();

        recursive(0, 0, N/2,0, left);
        recursive(0, N/2, N,0, right);

        Collections.sort(left);
        Collections.sort(right);

// ####################### 이 아래 코드가 다릅니다. #######################
        for(int val : left){
            answer += upperBound(S-val, right) - lowerBound(S-val, right);
        }

        System.out.println(answer);

    }

    static void recursive(int depth, int index, int max, int sum, List<Integer> list){

        if(depth>0){
            if(sum==S){
                answer++;
            }
            list.add(sum);
        }

        for(int i=index; i<max; i++){
            recursive(depth+1, i+1, max, sum+inputs[i], list);
        }

    }

// ####################### 여기가 다릅니다. #######################
    static int lowerBound(int key, List<Integer> list){

        int lo = 0;
        int hi = list.size();

        while(lo<hi){

            int mid = (lo + hi)/2;

            if(list.get(mid) >= key){
                hi = mid;
            }else{
                lo = mid+1;
            }

        }
        return lo;
    }

    static int upperBound(int key, List<Integer> list){

        int lo = 0;
        int hi = list.size();

        while(lo<hi){

            int mid = (lo + hi)/2;

            if(list.get(mid) > key){
                hi = mid;
            }else{
                lo = mid+1;
            }

        }
        return lo;
    }
}

*
* */