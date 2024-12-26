import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, arr[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N];

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int[] stack = new int[M];
        int lo = 0, hi = 1000000000;

        while(lo<hi){

            int mid = (lo+hi)/2;

            if(check(mid)){
                hi = mid;
            }
            else{
                lo = mid+1;
            }
        }

        System.out.println(lo);


    }

    static boolean check(int mid){

        int count = 0;
        int sum = 0;
        int idx = 0;

        while(idx<N){

            if(count>=M) break;

            if(sum+arr[idx] <= mid){
                sum += arr[idx++];
            }
            else{
                count++;
                sum = 0;
            }
        }

        return count<M;
    }

}

/*
 * 2343 기타 레슨
 *
 * 이분 탐색 문제
 *
 * 해당 문제의 경우 매개 변수 탐색 문제입니다.
 * 핵심 포인트는
 * 1. 이분 탐색으로 블루레이의 최대 값을 정한다.
 * 2. 최대 값을 기준으로 M개의 갯수 내로 블루레이에 수록할 수 있는지 확인해본다.
 *
 * 1번의 경우 최대 블루레이의 크기는 100,000 * 10,000 = 100,000,000 이므로 이분탐색 수행 시 대략 26번 수행.
 * 그러면 최대 26 * 100,000번 수행하므로 260만번의 반복이므로 시간상 충분합니다.
 *
 * 2번의 경우 문제에서 주어진 방식대로 더해보면서 최대값을 넣는지 확인하는 과정을 취해주면 됩니다.
 *
 * 주의점은 최대로 정한 값 보다 더 큰 값이 배열에 있을 수 있습니다.
 * 예를 들어 이분탐색을 통해 알아낸 가운데 값을 10으로 정했는데 주어진 배열이 다음과 같다면,
 * 1 2 3 11 12 13 14 ...
 * 그러므로 단순히 순차적으로만 반복하기 보단 while문으로 조건에 맞게 반복하도록 구현해야합니다.
 *
 *
 * */