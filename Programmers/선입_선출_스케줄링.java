/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {

    public int solution(int n, int[] cores) {

        int time = 0;
        int work = 0;
        int lo = 0;
        int hi = 10000*n;

        while(lo<hi){

            int mid = (lo+hi)/2;
            int cal = check(mid, cores);

            if(cal >= n){
                hi = mid;
                time = mid;
                work = cal;
            }
            else{
                lo = mid+1;
            }
        }

        int answer = 0;

        work -= n;

        for(int i=cores.length-1; i>=0; i--){

            if(time % cores[i]==0){
                if(work==0){
                    answer = i+1;
                    break;
                }
                work--;
            }
        }
        return answer;
    }

    static int check(int mid, int[] cores){

        int count = cores.length;

        for(int i = 0; i< cores.length; i++){
            count += mid / cores[i];
        }

        return count;
    }

}

/*
 * @@@@@@@@ 해당문제는 풀이에 실패하여 정답을 보았습니다. @@@@@@@@
 *
 * 이분 탐색 문제
 *
 * @@@ 처음 이 문제를 우선순위 큐로 풀이했을 때 3개의 효율성 검사를 통과하지 못했습니다.
 * @@@ 이후 이분 탐색으로 풀이를 시도했는데 거의 대부분의 테스트케이스에서 '틀렸습니다.'를 받아서 결국 풀이를 참고했습니다.
 *
 * 문제의 포인트는 이런 방식이었습니다.
 * 처음 시간이 0일 때 cores의 갯수만큼 작업을 넣을 수 있습니다. 예시 입력처럼 cores가 [1,2,3]이라면 작업양은 3입니다.
 * 그리고 이걸 계속 전개해보면
 * time work
 * 0    3
 * 1    4
 * 2    6
 * 3    8
 * 4    10
 * 5    11
 * 6    14
 * 7    15
 *
 * 이런식으로 전개될 것 입니다. 표에 나와있다 싶이 특정 시간의 총 작업량을 알 수 있다는 점입니다.
 * 그렇다면 저희가 처리해야하는 작업량 n 이상인 작업량 중에서 가장 작은 값(work)을 이분탐색으로 찾아냅니다.
 * 그 후 work-=n을 하면 n보다 초과된 작업량을 알아내게 됩니다.
 * 그 work값이 0이 될 때 까지 즉, 정확하게 n번째 작업을 수행한 코어를 가르킬 때 까지 cores 뒤에서부터 찾아주면 됩니다.
 *
 *
 * */