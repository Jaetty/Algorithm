import java.io.*;
import java.util.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)->{
            if(e1[1] == e2[1]){
                return e2[0] - e1[0];
            }
            return e1[1] - e2[1];
        });

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            pq.add(new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }

        int[] arr = pq.poll();
        int start = arr[1] - arr[0]; // 가장 일찍 일어나야 하는 시간 설정.
        int end = arr[1]; // 이 일이 끝났을 때의 시간 설정.
        int alpha = 0; // 추가로 얻게 되는 남는 시간

        while(!pq.isEmpty() && start >= 0){
            arr = pq.poll();

            alpha += arr[1] - end;
            alpha -= arr[0];

            if(alpha<0){
                start += alpha;
                alpha = 0;
            }
            end = arr[1];
        }

        System.out.println(start >= 0 ? start : -1);

    }

}
/*

1263 시간 관리

그리디 + 정렬 문제

문제의 풀이 방법은 다음과 같습니다.
1. 마감 시간(Si)이 빠른 순, 걸리는 시간(Ti)가 긴 순으로 입력을 정렬한다.
2. 시작 시간, 종료 시간, 남는 시간을 기억한다.
3. 남는 시간에서 먼저 Ti만큼 차감하고 만약 남는 시간을 초과한다면, 그 차이만큼 시작 시간에 빼준다.
4. 시작 시간을 출력한다. 시작 시간이 음수일 경우 -1을 출력

저는 문제를 이해하고 바로 다음 두 가지 예제를 만들어보고 문제를 해결했습니다.
1번 예제
3
1 3
1 3
1 3

2번 예제
3
1 3
2 4
2 5

먼저 1번 예제 2번 예제 구분 없이 처음 입력 1 3을 고려해보겠습니다.
3시까지 1시간 걸리는 일을 끝내야하는데 최대한 늦게 자는 법은 당연히 2시에 일어나서 시작하는 방법입니다.
즉, 아무리 늦어도 일어나야만 하는 최초의 시간은
"가장 일찍 끝내야하는 시간 - 걸리는 시간"이라는 점을 알 수 있습니다.

다음으로 1번 예제의 2번째 입력인 1 3을 고려해보면,
3시까지 1시간 걸리는 일을 끝내야하는데, 잠에서 깬 시간이 2시입니다. 저희는 1시간이 필요합니다.
그러면 2시보다 1시간 일찍 일어나면 해결됩니다. 즉 2-1 로 1시에 기상하면 됩니다.
이를 반복하면, 1번의 예제 3번도 동일하게 적용하여 정답이 0임을 알 수 있습니다.

하지만 2번 예제의 2번째 입력의 경우 2 4 입니다.
4시까지 2시간이 걸리는 작업을 끝내야합니다. 저희는 2시에 깻고, 3시까지 작업을 한 후, 4시까지 또 작업을 해야합니다.
그러면 3시부터 4시까지 저희는 1시간의 작업 시간을 우선 확보했습니다. 이를 플러스 알파 시간 즉, 남는 시간이라고 표현하면.

"남는 시간(alpha) = arr[1](4) - end(3) = 1"이라는 점을 알 수 있습니다.
하지만 남는 시간은 1이고 작업 시간은 2이므로 여전히 1시간이 부족합니다.
이를 시작 시간에서 차감하면 됩니다. 즉, 1시에 일어나서 1시간동안 작업한 후, 2시간동안 2번째 작업을 끝내면 됩니다.
여기까지를 똑같이 3번째 입력인 2 5에도 적용하면 정답은 0이 나옵니다.

여기서 볼 수 있듯이
시작 시간, 종료 시간, 남는 시간을 구한 후.
먼저 남는 시간을 누적해주고
작업 시간이 남는 시간을 초과한다면? 시작 시간에서 그 차이만큼 또 빼주면 해결할 수 있습니다.
그리고 종료 시간은 언제나 마지막 시간으로 최신화 시켜줍니다.

*/