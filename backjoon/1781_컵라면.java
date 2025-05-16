import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static List<int[]> list;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        list = new ArrayList<>();

        StringTokenizer st ;
        int time = 0;

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 가장 마지막 데드라인 기억하기
            time = Math.max(a, time);

            list.add(new int[] {a,b});
        }
        // 마지막 데드라인 값 추가해줌
        list.add(new int[] {0,0});

        // 입력된 값들을 모두 넣어줌
        Collections.sort(list, (e1,e2)->e2[0] -e1[0]);

        // 컵라면 갯수가 가장 많은 순으로 pq
        PriorityQueue<Integer> pq = new PriorityQueue<>((e1,e2)-> e2-e1);
        long answer = 0;

        for(int i = 0; i < list.size(); i++){

            int[] val = list.get(i);

            // 데드라인이 변했다면 그 차이만큼 pq에 있는 값들 뽑아서 합산
            while(time != val[0]){
                if(!pq.isEmpty()){
                    answer += pq.poll();
                }
                time--;

            }

            pq.add(val[1]);

        }

        System.out.print(answer);

    }

}

/*

1781 컵라면

그리디 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. 입력 값을 데드라인을 기준으로 정렬한다.
2. 데드라인이 내림차가 되도록 순회하면서 pq에 컵라면 갯수를 넣어준다.
3. 만약 데드라인 시간이 변했다면 pq에서 가장 많은 컵라면 갯수를 뽑아와 합산한다.

아무래도 문제에서 까다로운 부분은 우선순위를 어떻게 정해야할지 일 것입니다.

저는 다음과 같은 예시로 검증하면서 문제를 해결했습니다.

1	1	2	2	4	4	4   => 데드라인
5	10	9	9	15	12	50  => 컵라면 갯수

이 예시의 정답은 10 + 15 + 12 + 50 입니다.

이 예시로 알 수 있는 점은 반드시 데드라인이 가깝다고 선택해선 안되고,
전체 구간으로 값을 고려하되, 이미 지나온 데드라인은 고려하지 않는 것이 포인트라는 점을 알 수 있습니다.
저는 여기서 반대로 생각해서 데드라인 4부터 시작하여 기회를 따지면 되지 않을까 라는 생각이 들었습니다.

데드라인 4인 값들을 전부 pq에 넣고, 데드라인 2를 만나게 되면 이때까지 pq에 넣은 값 중 2개를 꺼낼 수 있다는 뜻입니다.
그리고 다시 2에 있는 값을 다 넣고 데드라인 1을 만나면 pq에서 1개를 뽑을 수 있습니다.

이렇게하면 지나온 데드라인은 pq에 들어있지 않으니 고려하지 않게 되고, 전체 구간에 대해서 가장 큰 값만을 고를 수 있게 됩니다.

위와 같은 로직을 코드로 구현하게 되면 해당 문제를 해결할 수 있습니다.

*/