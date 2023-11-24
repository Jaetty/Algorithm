/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {

    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;

        int d_last = 0, p_last = 0;
        int d_less = 0, p_less = 0;

        for(int i=n-1; i>=0; i--){
            if(deliveries[i]>0){
                if(d_last == 0)d_last = i;
                d_less += deliveries[i];
            }
            if(pickups[i]>0){
                if(p_last == 0) p_last = i;
                p_less += pickups[i];
            }
        }

        while(p_less > 0 || d_less>0){

            int max_length = Math.max(d_last+1, p_last+1);
            answer += max_length*2;

            int carry = cap;

            for(int d = d_last; d>=0; d--){

                d_last = d;
                if(deliveries[d]==0) continue;

                int val = deliveries[d] - carry;
                if(val>0){
                    d_less -= carry;
                    deliveries[d] = val;
                    break;
                }
                d_less -= deliveries[d];
                deliveries[d] = 0;
                carry = Math.abs(val);

            }
            carry = cap;

            for(int p = p_last; p>=0; p--){

                p_last = p;
                if(pickups[p]==0) continue;

                int val = pickups[p] - carry;
                if(val>0){
                    p_less -= carry;
                    pickups[p] = val;
                    break;
                }
                p_less -= pickups[p];
                pickups[p] = 0;
                carry = Math.abs(val);
            }

        }

        return answer;
    }
}

/*
* 그리디 문제
*
* 그리디로 보았을 때 해당 문제의 가장 중요한 포인트는 문제에서 볼드 처리한 단 한 부분 이었습니다.
* 1. 각 집에 배달 및 수거할 때, 원하는 개수만큼 택배를 배달 및 수거할 수 있습니다.
*
* 이 말은 즉 예시로 주어진 입력이 다음과 같다면
* cap	n	deliveries	         pickups	 result
*  4	5	[1, 0, 3, 1, 2]	 [0, 3, 0, 4, 0]   16
*
* 물건을 4개를 들고 1번집에서 놔두고 3번집에서 놔둘 수도 있고
* 3번집에서 놔두고 4번집에서 놔둘 수 있고, 1번집에 놔두고 5번 집에도 놔둘 수 있습니다.
* 이는 수거도 마찬가지로 적용됩니다.
*
* 그러면 "최대한 물건을 많이 가져간 다음 가장 먼 집부터 주면 되지 않을까?"를 해도 된다는 결론이 나오게됩니다.
*
* 예시의 경우 4개를 들고가서 5번 집에 도착하기 전에 3번집에서 1개를 주고 4번집에서 1개 그리고 5번집에서 2개를 줄 수 있습니다. 그리고 수거는 돌아오는 길에 4번집에서 하면 됩니다. 이러면 거리는 10이 됩니다.
* 그럼 남은 배달과 수거는 아래와 같게 되는데
* 배달 [1, 0, 2, 0, 0]
* 수거 [0, 3, 0, 0, 0]
*
* 배달과 수거 중 가장 먼 집은 3번 집으로 3번 집까지 가서 1번 집에서 1개 2번 집에서 2개를 놔두면 배달이 끝나고 돌아오는 길에 3개를 수거하면 됩니다. 거리는 3*2가 됩니다.
* 그리고 괜한 반복으로 시간이 아까울 수 있기 때문에
* 각각 수거가 남은 집, 배달이 남은 집의 마지막으로 멀었던 집을 기억하게 함으로써 해당 인덱스부터 순회하게 만들면 시간 낭비를 막을 수 있습니다.
*
* 이 아이디어를 코드로 작성하면 문제를 해결할 수 있습니다.
*
* */