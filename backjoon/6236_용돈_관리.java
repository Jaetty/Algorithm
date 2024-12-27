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

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        int lo = 1, hi = 10000 * 100_000, answer = hi;

        while(lo<hi){

            int mid = (lo+hi)/2;
            int key = count(mid);

            if(key <= M){
                answer = Math.min(answer, mid);
                hi = mid;
            }
            else {
                lo = mid + 1;
            }

        }

        System.out.println(answer);

    }

    static int count(int mid){

        int idx = 0;
        int money = mid;
        int count = 1;

        while(idx < N){

            if(count > M){
                break;
            }

            if(money - arr[idx] < 0){
                money = mid;
                count++;
            }
            else{
                money -= arr[idx++];
            }
        }
        return count;
    }

}

/*
 * 6236 용돈 관리
 *
 * 이분 탐색 문제
 *
 * 해당 문제의 경우 매개 변수 탐색 문제입니다.
 * 핵심 포인트는
 * 1. 이분 탐색으로 인출해야할 최소 K 값을 정한다.
 * 2. K가 정해졌을 때 M번 이하의 횟수로 인출이 가능한지 확인한다.
 *
 * 문제의 경우 M이라는 숫자를 반드시 맞춰야한다고 적혀있는데, 바로 그 다음 문장에 원한다면 돈이 충분하더라도 재인출이 가능하다고 적혀있습니다.
 * 그렇다면 K값을 정한 후 M 횟수 이하로만 인출이 가능하면 원할 때 그 횟수를 늘려서 M번을 맞출 수 있습니다.
 * 즉, 인출 횟수가 M 이하이면 된다는 얘기입니다. 이에 맞춰서 매개 변수 탐색으로 풀이하면 풀이할 수 있습니다.
 *
 *
 * */