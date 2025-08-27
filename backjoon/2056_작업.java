import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int N;
    static int[] left, time;
    static List<Integer>[] edges;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        edges = new List[N];
        left = new int[N];
        time = new int[N];

        for (int i = 0; i < N; i++) {
            edges[i] = new ArrayList<>();
        }

        for(int n = 0; n < N; n++) {

            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            left[n] = b;
            time[n] = t;

            for(int i = 0; i < b; i++) {
                int v = Integer.parseInt(st.nextToken())-1;
                edges[v].add(n);
            }

        }

        Queue<int[]> q = new ArrayDeque<>();

        int[] timeToReach = new int[N];
        int answer = 0;
        Arrays.fill(timeToReach, -1);

        for(int i = 0; i < N; i++) {
            if(left[i] == 0){
                timeToReach[i] = time[i];
                answer = Math.max(answer, time[i]);
                q.add(new int[] {i, time[i]});
            }
        }

        while(!q.isEmpty()) {

            int[] poll = q.poll();

            for(int next : edges[poll[0]]) {

                left[next]--;
                timeToReach[next] = Math.max(timeToReach[next], poll[1]+time[next]);
                answer = Math.max(answer, timeToReach[next]);
                if(left[next] == 0) q.add(new int[] {next, timeToReach[next]});

            }

        }


        System.out.print(answer);

    }

}
/*

2056 작업

위상 정렬 문제

해당 문제는 핵심 풀이 로직은 다음과 같습니다.
1. 위상 정렬로 먼저 수행할 수 있는 작업들을 수행해준다.
2. 다음 작업이 끝나는 시간은 = 내 앞의 모든 선수 작업이 끝난 시간 중 가장 오래 걸린 시간 + 내 작업 시간

문제의 경우 어떤 작업을 수행하기 위해 먼저 수행되어야 하는 작업들이 있습니다.
이러한 문제는 위상 정렬을 이용하면 선수 작업이 수행되면 다음으로 넘어가는 방식이 풀이가 가능합니다.

다음으로, 모든 작업이 끝나기까지 최소 시간을 묻고 있습니다.
조금만 생각해보면, 이런 예시를 떠올릴 수 있습니다.

3번 작업을 수행하려면 1번, 2번 작업이 완료되어야 한다고 해보겠습니다.
1번과 2번은 병렬로 수행이 가능합니다, 그런데 1번 작업은 5초가 걸리고, 2번 작업은 10초가 걸린다면 최소 시간은 얼마일까요?
정답은 10초일 것입니다. 그야 아무리 1번 작업이 먼저 완료되도 2번이 10초 걸리니 아무리해도 최소 시간은 10초입니다.

즉, 최소 시간이란 어떤 작업이 완료 될 때 다음 작업에 도달할 때 최대 시간을 기억해두면 되는 것 입니다.
또한, 모든 작업을 다 수행한 경우보다 오히려 하나의 작업이 더 오래 걸릴 경우도 있을겁니다.
그렇기 때문에 정답을 단순히 끝에 도달했을 때의 시간만 고려하는 것이 아닌 모든 경우에서 가장 오래 걸린 시간이 정답입니다.

*/