import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static long answer;
    static int[] arr, sum;
    static int[] count;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N];
        sum = new int[N];

        count = new int[1000];
        st = new StringTokenizer(br.readLine());

        arr[0] = Integer.parseInt(st.nextToken())%M;
        sum[0] = (arr[0] + sum[0])%M;
        count[sum[0]]++;

        for(int i=1; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken())%M;
            sum[i] = (arr[i] + sum[i-1])%M;
            count[sum[i]]++;
        }
        answer = count[0];
        for(int i=0; i<M; i++){
            answer += combine(count[i]);
        }

        System.out.println(answer);

    }

    static long combine(long c){
        if(c<2){
            return 0;
        }
        else return c*(c-1) / 2;
    }

}

/*
 * 누적합 + 수학 문제
 *
 * 해당 문제는 다음과 같이 풀이가 가능합니다.
 * 먼저 예제로 주어지는 입력을 보면
 * 5 3
 * 1 2 3 1 2
 *
 * 이고 정답이 7 입니다.
 *
 * 한번 누적합으로 이들을 다 더해주면 결과는
 * 1 3 6 7 9 입니다. 여기서 3으로 mod한 결과는
 * 1 0 0 1 0 이 됩니다.
 *
 * 0이 된 것은 나누어 떨어졌다는 뜻이고 저희는 결국 이 0이 총 몇개 만들어질 수 있는가를 확인해야합니다.
 *
 * 이제 7이 위치한, mod를 적용한 결과로 나타낸 값에서는 index 순서가 4번째인 1을 한번 봅시다.
 * 1이 0이 되려면 1을 빼줘야합니다. 그럼 뒤에서 1이 존재하는지 확인해보면 될 것입니다.
 * 1번째 순서의 값에 1이 있습니다. 즉 첫번째 순서의 값을 빼주면 0이 성립된다는 것을 알 수 있습니다.
 *
 * 그럼 입력이 다음과 같이 주어졌다면?
 * 6 3
 * 1 2 3 1 2 1
 * 정답은 9
 *
 * 이러면 누적합과 mod 결과는 각각
 * 1 3 6 7 9 10
 * 1 0 0 1 0 1
 * 이 됩니다.
 *
 * 10은 mod후 1이 되고 이 1이 0이 되려면 1을 빼줘야하는데 자신의 index 기준 뒤에 1이 존재하는 경우가 2번 있습니다.
 * 10을 3의 배수로 만드는 방법은 1을 빼주거나 7을 빼주면 가능합니다.
 * 즉 나를 기준으로 뒤에있는 1의 개수만큼 0으로 만들 수 있습니다.
 * 0에도 이것을 똑같이 적용하게 된다면 나를 기준으로 뒤에 0이 몇개 있는지 알아내면 된다는 것입니다.
 * 문제는 결국 누적합이 순수한 0의 개수 + 0을 포함하여 각각의 수가 총 몇 번 나왔는지를 더해주면 답을 알 수 있습니다.
 *
 * 이것을 나타내면 => 0은 순수하게 3개 있으니까 3
 * 그리고 0이 총 3개 있으니 3*2/2 = 3
 * 1이 3번 나왔으니 3*2/2 = 3
 *
 * 3 + 3 + 3 = 9로 정답을 확인할 수 있습니다.
 *
 */