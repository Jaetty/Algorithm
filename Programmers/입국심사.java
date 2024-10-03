import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {
    public long solution(int n, int[] times) {

        Arrays.sort(times);

        long lo = 0;
        long hi = 1000000000L * 1000000000L;

        while(lo < hi){

            long mid = (lo+hi)/2;

            if(check(mid, times, n)){
                hi = mid;
            }
            else{
                lo = mid+1;
            }

        }

        return lo;
    }

    static boolean check(long mid, int[] times, int n){

        long sum = 0;

        for(int i=0; i<times.length; i++){

            if(sum >= n){
                break;
            }
            sum += mid/ ((long) times[i]);
        }

        return sum >= n;
    }


}

/*
 * 이분 탐색 문제
 *
 * 해당 문제의 경우 시간을 기준으로 이분 탐색을 수행하면 풀이할 수 있는 문제입니다.
 * 최대 시간은 기다리는 사람 1,000,000,000명과 걸리는 시간 1,000,000,000의 곱이 됩니다.
 * 이분 탐색을 수행하게 되면 log 1000000000000000000 = 대략 60 정도입니다.
 *
 * 그리고 check()함수를 통해서 각 수행원이 그 시간에 몇 번 수행하였는지 그 합산 값이 n을 넘는지 확인합니다.
 * 즉 특정 시간에 어떤 수행원의 수행 시간을 나눈 값을 누적한 값이 n 이상인지 확인해주면됩니다. -> sum += (mid/times[x])이 sum >= n이 되는지 확인.
 * 여기서 심사관이 최대 100,000명 이므로 앞에 구한 60을 곱하면 총 수행시간은 O(6,000,000)번임을 알 수 있습니다.
 * 그러면 600백만번의 반복이면 충분한 시간이므로 효율성에 문제가 없음을 알 수 있습니다.
 *
 * 이와 같이 문제를 풀이하였고 코드로 나타내면 해결할 수 있습니다.
 *
 */