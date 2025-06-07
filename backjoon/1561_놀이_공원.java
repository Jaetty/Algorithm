import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long N;
    static int M;
    static int[] count, arr;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Long.parseLong(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // x분에 동작하는 기구가 몇개 있는지 기억합니다.
        count = new int[31];
        // 입력을 받습니다.
        arr = new int[M];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++){
            arr[i] = Integer.parseInt(st.nextToken());
            count[arr[i]]++;
        }

        // 처음에 모두 탈 수 있는 경우
        if(N <= M){
            System.out.println(N);
            return;
        }

        // 처음 시작 시간에 기구 M개는 모두 탈 수 있다.
        N -= M;

        // 모두가 다 타서 끝나는 시간 구하기.
        long time = getEndTime();

        // 딱 시간이 1분 부족할 때의 기구를 탄 아이들 수를 구한다.
        long child = getChild(time-1);

        // 첫 번째 기구부터 태워보며 N개를 만족시키는 때에 출력.
        for(int i = 0; i < M; i++){

            if(time % arr[i] == 0){
                child++;
            }
            if(child == N){
                // 정답 숫자는 1부터 시작해야하므로 +1 해준다.
                System.out.println(i+1);
                return;
            }

        }

    }

    // 어떤 시간을 넣으면, 그 시간에 몇명의 아이가 기구를 이용했는지 출력함.
    static long getChild(long time){

        long cnt = 0;

        for(int i=1; i<31; i++){
            if(count[i] > 0){
                // 시간 / 분 * 기구 갯수로 탔던 아이들의 수를 구할 수 있음
                long val = time/i;
                val *= count[i];
                cnt += val;
            }
        }
        return cnt;
    }

    // 정확히 끝나는 시간 구하기.
    static long getEndTime(){

        // 최악의 경우는 기구가 한명이고 N이 최대일 때 즉, 2,000,000,000명 * 30분.
        long left = 0, right = N * 30;

        // 이분 탐색으로 정확히 끝나는 시간을 구함
        while(left < right){

            long mid = (right + left) / 2;

            // 해당 시간에 몇명이 이용했는지로 이분 탐색 속행
            long child = getChild(mid);

            if(child >= N){
                right = mid;
            }
            else{
                left = mid+1;
            }

        }

        return left;

    }

}

/*

1561 놀이 공원

이분 탐색 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. 이분 탐색을 통해 모두가 다 타게 되는.. 즉, 끝나는 정확한 시간을 구합니다.
2. 그 시간에서 딱 1분 전에, 몇명이 탔었는지 알아냅니다.
3. 앞에서 구한 끝나는 시간을 기준으로 1번 기구부터 차례대로 탈 수 있다면 태웁니다. 마지막 인원까지 태우면 그 기구 번호를 출력합니다.

문제의 경우 N의 최대 값이 20억이나 됩니다. 20억이면 사실상 N번 순회만 하더라도 시간초과가 나기 쉬운 숫자입니다.
그렇기 때문에 N을 어떻게든 log N 이하로 만들어야하는 점을 쉽게 유추할 수 있습니다.

문제를 단순한 방법으로 생각하면 이렇게 됩니다.
time 값을 하나하나 올려가보면서, 만약 기구의 동작 시간이 끝나면 그때 새로운 아이를 차례대로 빈 기구에 태우는 걸 반복하여 끝날 때의 번호를 출력하는 것 입니다.
하지만, 이렇게 하면 당연히 시간 초과가 됩니다. 최악의 경우 2,000,000,000명 * 30분 만큼의 시간이 필요하게 됩니다.

그럼, 다르게 생각해보면 끝나는 시간을 알 수 있지 않을까? 라는 생각을 할 수 있습니다.
끝나는 시간을 구한 후, 그 끝나는 시간 직전에 몇명이 탔는지를 구한다음, 끝나는 시간에 차례대로 태워서 끝난 번호를 출력하면 된다는 아이디어를 냈습니다.

위의 아이디어를 바탕으로 핵심 로직을 구했고, 이를 코드로 구현하게 되면 문제를 풀이할 수 있습니다.

*/