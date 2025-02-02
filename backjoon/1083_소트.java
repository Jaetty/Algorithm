import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[N];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int S = Integer.parseInt(br.readLine());
        int curr = 0;

        while(S>0 && curr < N){

            int[] change = {arr[curr], curr}; // 값, 위치, 차이

            for(int i=curr; i<N; i++){

                if(S<i-curr){
                    break;
                }

                if(change[0] < arr[i]){
                    change[0] = arr[i];
                    change[1] = i;
                }

            }

            for(int c = change[1]; c>curr; c--){

                int tmp = arr[c];
                arr[c] = arr[c-1];
                arr[c-1] = tmp;
                S--;
            }

            curr++;

        }

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<N; i++){
            sb.append(arr[i]+" ");
        }

        System.out.print(sb.toString());

    }

}

/*
 * 1083 소트
 *
 * 그리디 문제
 *
 * 이 문제의 핵심은 최대한 앞자리를 크게 만든 것 입니다.
 *
 * 예를 들어, 1 2 3 4가 주어졌을 때, 아무리 둘째, 셋째, 넷쩨 자리 숫자를 바꿔서 크게 하는것보다
 * 2가 맨앞에 나오는게 더 숫자가 크다는 것을 알 수 있습니다.
 *
 * 예) 1 4 3 2, 1 4 2 3, 1 3 2 4... 등이 있더라도 결국 2 1 3 4 보다 작음.
 *
 * 그러면 쉽게 생각해서 가장 앞에 있는 숫자부터 최대한 크게 만들어주면 됩니다.
 *
 * 예를 들어 1 2 3 4를 기준으로 현재 가르키는 위치를 1, S가 2라고 해보면
 *
 * 1' 2 3 4
 *
 * 에서 최초 1을 최대값으로 둔 후 S만큼 뒤로 순회하며 최대값을 계속 비교합니다.
 *
 * 1 2' 3 4 -> 1보다 크므로 최대값 2로 갱신
 * 1 2 3' 4 -> 2보다 크므로 최대값 3로 갱신
 * 1 2 3 4' -> S보다 더 움직였으므로 성립할 수 없음.
 *
 * 그러면 3이 가장 큰 값이므로 이를 앞으로 가져오는 작업을 수행해줍니다. 연속된 것만 교환이 되므로
 *
 * (초기) 1 2 3' 4 -> 1 3' 2 4 -> 3' 1 2 4
 *
 * 이런식으로 옮겨주면 됩니다. 만약 S가 충분히 큰 값이라고 해보겠습니다. 그러면 모두 순회한 후 최대의 값이 1의 자리로 옮겨질 것 입니다.
 * 여기선 4가 앞으로 와서 4 1 2 3 이 될 겁니다. 그러면 4보다 큰 경우는 없으므로 1의 자리는 고정해둡니다.
 * 2의 자리부터 다음으로 큰 값을 찾으면 됩니다. 또한, 문제의 경우 S만큼 해야하는게 아니라 최대 S까지만 움직일 수 있다고 했으므로 S를 다 쓸 필요가 없습니다.
 *
 *
  */