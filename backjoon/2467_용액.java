import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N;
    static int arr[];
    static class Value{
        int left, right, sum;
        Value(int left, int right, int sum){
            this.left = left;
            this.right = right;
            this.sum = sum;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }


        if(arr[N-1] <0 && arr[N-2] <0){
            System.out.println(arr[N-2]+" "+arr[N-1]);
            return;
        }
        else if(arr[0] >= 0){
            System.out.println(arr[0]+" "+arr[1]);
            return;
        }

        int right = N/2;
        int left = right-1;
        int before = 0;
        Value answer = new Value(left, right, 1_220_000_000);

        while(left >= 0 && left < N && right < N && right >= 0){

            int sum = arr[left] + arr[right];

            if(sum==0){
                System.out.println(arr[left] + " " + arr[right]);
                return;
            }

            if (answer.sum > Math.abs(sum)){
                answer = new Value(left, right, Math.abs(sum));
            }

            if(before<0 && sum <0 && left<right-1){
                left++;
            }
            else if(before>0 && sum > 0 && left+1<right){
                right--;
            }
            else{
                if(sum>0) left--;
                else right++;
            }

            before = sum;

        }

        System.out.println(arr[answer.left] + " " + arr[answer.right]);

    }

}
/*
* 투 포인터 or 이분 탐색 문제
* 필자는 투 포인터를 이용하여 문제를 해결하였습니다.
*
* 이 문제의 포인트를 파악해보면
* 1. 시간 제한과 입력값을 보았을 때 절대 브루트포스로 풀지 못한다는 사실을 알 수 있습니다.
* 2. 입력 값은 오름차순으로 정렬되어있습니다.
* 3. 두 합이 0에 가장 가까운 값
* 위와 같이 세 가지라고 생각합니다.
*
* 생각해보면 만약 배열에 첫 인덱스 값이 0보다 크다면 arr[0]+arr[1]이 가장 0에 가깝다는 걸 알 수 있습니다.
* 반대로 생각하면 가장 큰 인덱스 값이 0 보다 작다면 arr[N-1] + arr[N-2]도 0에 가장 가까운 걸 알 수 있습니다.
*
* 그럼 위 두가지 경우에 속하지 않을 때는 투포인터를 활용하였습니다.
* 필자 같은 경우에는 각각 left, right라는 변수로 왼쪽 오른쪽을 나타내는 index를 만들고 N/2-1, N/2에서 부터 시작해서
* 두 합이 음수면 right를 키우고 양수면 left를 줄여서 찾게 만들었습니다.
* 물론 계속해서 음수가 나오거나 양수가 나오는 경우가 있을 수 있어서 그럴경우는 전에 값과 비교해서 right를 낮추거나 left를 올리게했습니다.
* 그렇게 해서 계속 절대값을 비교해가며 가장 0에 가까운 값을 갱신해주고 출력하게 해결했습니다.
*
* 다만 필자는 이 구현이 조금 아쉬운게 처음에 이분 탐색으로 해결하는 법을 생각하다가 투 포인터로 해야겠다고 생각했더니
* 의식의 흐름대로 left와 right의 시작점을 N/2를 기준으로 시작하게 코드를 구현해버렸습니다. (새벽 감성..ㅎㅎ;;;)
* 처음 left와 right를 각각 arr[0], arr[N-1]로 하여 맨끝에서 가운데로 점점 움직이는 방식으로 했으면 코드가 더 깔끔하고 구현이 쉬웠을거라고 예상합니다.
*
*
* */