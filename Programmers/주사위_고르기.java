import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {

    static int N, M, max_win, idx;
    static int[] answer;
    static int[][] g_dice;

    public int[] solution(int[][] dice) {

        N = dice.length;
        M = dice[0].length;
        g_dice = dice;

        combination(0, 0, new boolean[N]);

        for(int i=0; i<answer.length; i++){
            answer[i]++;
        }
        Arrays.sort(answer);


        return answer;
    }

    static void combination(int depth, int index, boolean[] comb){

        if(depth >= N/2){

            int[] left = new int[depth];
            int[] right = new int[depth];

            int l_count=0, r_count=0;

            for(int i=0; i<N; i++){
                if(comb[i]){
                    left[l_count++] = i;
                }
                else{
                    right[r_count++] = i;
                }
            }

            int[] l_sum = new int[(int)Math.pow(6,depth)];
            int[] r_sum = new int[(int)Math.pow(6,depth)];

            makeArr(0, left, 0, l_sum);
            makeArr(0, right, 0, r_sum);

            Arrays.sort(l_sum);
            Arrays.sort(r_sum);

            l_count=0;
            r_count=0;

            int before = 0, lo = 0, hi = 0;

            for(int i=0; i<l_sum.length; i++){

                if(before!=l_sum[i]){
                    lo = upper_bound(l_sum[i]-1, r_sum);
                    hi = upper_bound(l_sum[i], r_sum);
                    before = l_sum[i];
                }

                l_count += lo;
                r_count += l_sum.length - hi;

            }

            if(max_win < Math.max(l_count, r_count)){

                if(max_win< l_count){
                    max_win = l_count;
                    answer = left;
                }
                else{
                    max_win = r_count;
                    answer = right;
                }

            }

            return;

        }

        for(int i=index; i<N; i++){

            if(comb[i]) continue;
            comb[i] = true;
            combination(depth+1, i+1, comb);
            comb[i] = false;

        }

    }

    static void makeArr(int depth, int[] arr, int sum, int[] result){

        if(depth==0){
            idx = 0;
        }

        if(depth >= N/2){
            result[idx++] = sum;
            return;
        }

        for(int i=0; i<M; i++){
            makeArr(depth+1, arr, sum+g_dice[arr[depth]][i], result);
        }

    }

    static int upper_bound(int key, int[] right){

        int lo = 0;
        int hi = right.length;

        while(lo<hi){

            int mid = (lo+hi)/2;

            if(right[mid] > key){
                hi = mid;
            }
            else{
                lo = mid+1;
            }

        }

        return lo;

    }

}

/*
 * 조합 + 이분 탐색 문제
 *
 * 해당 문제를 간략하게 요약하면 다음과 같습니다.
 * 1. 주사위는 최대 10개, 각자 N/2개 씩 가져간다.
 * 2. 주사위 면은 6가지이고 최대 100까지의 자연수가 쓰여있다.
 * 3. 이 문제는 가장 승이 많은 조합을 가지고 가는 것이 목표이다.
 *
 * 먼저 1번을 통해 알 수 있는 점은 반드시 조합을 써야한다는 점입니다.
 * 그러니 먼저 주사위를 고르는 조합 기능을 계획에 추가합니다.
 *
 * 2번을 생각해보면 N/2개 가져온 주사위들의 모든 합을 구하는 기능이 필요하다는 점을 알 수 있습니다.
 * 그러므로 주사위를 통해 모든 합을 구하는 백트래킹 기능도 구현 목표롤 설정합니다.
 *
 * 3번의 경우 해결 방법은 사람마다 다르겠지만 저의 경우 다음과 같은 방법으로 해결법을 떠올렸습니다.
 * "각가 골랐던 주사위로 만들 수 있는 모든 합을 모아둔 배열 left와 right가 있을 때, left를 순회하면서 이 값이 right의 몇번째 값보다 더 큰지 확인하고
 * 무승부를 제외하고 right의 몇번째 값보다 작은지도 확인하면, left의 승리 수, right의 승리 수를 알 수 있다."
 * 이 경우 브루트포스를 수행하면 시간초과를 겪을 것이므로 이분 탐색 중 upper_bound를 이용하기로 하였습니다.
 * 현재 left 배열에 있는 x값이 어떤 값들보다 미만인지, x-1 값이 어떤 값들보다 초과인지 그 인덱스를 구하는 것입니다.
 *
 * 예를 들어 모든 주사위의 합을 구해놓은 후 left 배열과 right 배열이 있다고 해봅시다.
 * left[0]의 값이 right배열의 몇번째 인덱스 값 보다 작은지를 upper_bound로 구해보는겁니다.
 *
 * 만약 left가 [2, 2, 4, 4, 6, 6], right가 [1, 2, 3, 4, 5, 6] 이라면
 * left[0] = 2은 right의 3보다 작습니다. 그러면 upper_bound의 결과는 최초로 2보다 큰 값을 가진 3의 인덱스 값 2입니다.
 * 그리고 left[0]-1 = 1입니다. 1보다 최초로 큰 위치는 right[1] 위치입니다.
 * 그러면 즉 left[0]-1을 upper_bound한 값의 결과는 2가 승리한 횟수이고
 * left[0]을 upper_bound한 값 2는 right가 지거나 비긴 횟수입니다.
 *
 * 그러면 left[0]에 대해 left가 이긴 승리 수는 1개, right가 이긴 승리는 6-2 = 4 번입니다.
 * 이런 식으로 모든 주사위 경우의 수 left와 right에 대해 승리를 구해서 승리가 가장 큰 조합을 구하면 문제를 풀이할 수 있습니다.
 */