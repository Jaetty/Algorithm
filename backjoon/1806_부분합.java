import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N,M, answer;
    static int arr[];
    static final int MAX = 1000000000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N];

        st = new StringTokenizer(br.readLine());

        int sum = 0;
        int end = 0;
        answer = MAX;

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
            sum += arr[i];

            while(sum>=M){
                if(sum-arr[end] < M){
                    answer = Math.min(answer, i+1-end);
                    break;
                }
                sum -= arr[end];
                end++;
            }

        }
        if(answer==MAX) System.out.println(0);
        else System.out.println(answer);
    }

}
/*
* 투 포인트 문제
* 해당 문제는 다음과 같이 풀이할 수 있습니다.
* 예시로
* 10 14
* 5 1 3 5 10 7 4 9 2 8
* 이라는 입력이 주어졌다고 했을 때 sum과 end라는 변수를 선언합니다.
* sum은 배열의 요소들을 계속 더해주는 변수입니다.
* end는 M보다 큰 수를 만들기 위해 최소한 이 요소는 포함하는 위치를 기억하는 변수입니다.
* end를 로직을 전개하며 설명하자면 다음과 같습니다.
*
*
* 1. 처음 요소는 5입니다. sum에 더해줍니다. sum = 5
* 2. 다음 요소는 1입니다. sum에 더해줍니다. sum = 6
* 3. sum+=3                            sum = 9
* 4. sum+=5 이때 M이 14이고 sum은 15이기 때문에 다음 과정이 진행됩니다.
* 4-1. 만약 sum이 M이상이면 가장 앞에 있는 요소를 뺏을때도 sum>=M 성립하는지 봅니다.
       가장 앞에 있는 5를 빼면 9가 되므로 sum>=M을 성립하지 않습니다.
       그렇다면 end값을 늘려주지 않습니다. sum>=M이 성립하려면 현재까지의 모든 요소들이 필요하다는 뜻이고
       이는 i+1 - end를 통해 요소가 몇개 들어있는지 확인할 수 있습니다.
* 5. 이제 10을 더해줍니다. sum += 10    sum = 24 M보다 크니까 위의 과정을 다시 진행합니다.
* 5-1. end 포인트에 있던 요소는 5이고 이를 빼더라도 sum = 19이고 14보다 큽니다.
       end++를 해서 다음 요소에 위치하게 해줍니다.
       다음 요소를 sum에서 빼도 18이므로 14보다 큽니다. end를 또 높여서 다음 요소를 가르키게 합니다.
       3을 빼더라도 15 이므로 다시 한번 위의 과정을 반복합니다.
       5를 빼면 10이 되고 더이상 14보다 클 수 없습니다. 그러면 end를 키우는걸 멈추고
       i+1 - end를 통해 요소가 2개 들어있음을 확인할 수 있고 sum 값은 15가 됩니다.
* 6. 이제 7을 더해주고 위의 과정들을 반복하면 end가 하나 더 키워지고 요소 값은 2개가 됩니다.
* 7. 위의 과정들을 N번째 순서까지 반복합니다.
*
* 이렇게 하면 최소 요소 개수 즉 부분합의 길이를 구할 수 있습니다.
* */