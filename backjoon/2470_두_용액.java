import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int N;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[N];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        int[] answer = {1_000_000_000,1_000_000_000};

        for(int i=0; i<N; i++){


            int lo = i+1;
            int hi = N;

            while(lo < hi){

                int mid = (lo+hi)/2;

                if( Math.abs(arr[mid] + arr[i]) < Math.abs(answer[0] + answer[1])){
                    answer[0] = arr[i];
                    answer[1] = arr[mid];
                }

                if(arr[mid] + arr[i] < 0){
                    lo = mid+1;
                }
                else{
                    hi = mid;
                }

            }
        }

        System.out.print(answer[0] + " " + answer[1]);

    }
}

/*
 * 이분 탐색 문제
 *
 * @@@ 해당 문제의 경우 투포인터, 이분탐색의 풀이가 가능합니다.
 *
 * 문제의 포인트는 다음과 같습니다.
 * 1. 입력 범위는 -1_000_000_000 ~ 1_000_000_000
 * 2. 입력되는 배열의 갯수 N은 최대 10만
 * 3. 둘을 더했을 때 가장 0에 가까운 점수 찾기.
 *
 * 여기서 저희는 1번과 2번 포인트에 집중해야합니다.
 * 1번 포인트에서 나와있는 범위의 값이 랜덤하게 10만개 주어지게 됩니다.
 * 그렇다면 이를 브루트포스로 모두 비교하게 되면 N^2 반복이므로 시간초과를 예상할 수 있습니다. (절대 정확하게 맞지는 않지만 1억 연산을 1초로 생각하면 편합니다.)
 * 그러면 최대 N Log N의 방법을 생각해봐야합니다. 여기서 제가 LogN의 연산으로 선택한 것이 이분탐색입니다.
 *
 * 먼저 이분탐색이 가능하도록 정렬을 수행합니다.
 * 맨 처음 인덱스의 값은 가장 작은 값입니다. 이 값을 정답의 첫번째라고 했을 때, 이 값과 더했을 때 0에 가장 가까운 값을 찾습니다.
 * 여기서 이분 탐색의 기준은 나와 다른 값을 더해서, 그 값이 음수인지 양수인지로 판단하여 lo와 hi의 범위를 변경해줍니다.
 * 즉 더한 값이 음수면 현재 위치(mid)에서 뒤에 위치한 원소값 범위에서 찾으면 되고, 더한 값이 양수면 더 앞에 값을 찾게 합니다.
 * 이렇게 이분탐색으로 적절한 범위내에서 탐색하는 한편 그 과정에서 선택된 mid값과 현재 값을 더한 값이 0에 가까운지 확인하여 정답을 수정해줍니다.
 *
 * 이렇게 하면 N Log N의 연산으로 대략 1,700,000 반복이므로 시간초과 없이 문제를 풀이할 수 있습니다.
 *
 * */