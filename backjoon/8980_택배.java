import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int answer = 0;
        int N = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        List<int[]>[] list = new List[N+1];
        for(int i = 1; i <= N; i++){
            list[i] = new ArrayList<>();
        }

        int M = Integer.parseInt(br.readLine());

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            list[a].add(new int[]{b,c});
        }

        for(int i = 1; i <= N; i++){
            Collections.sort(list[i], (e1,e2)-> e2[0] - e1[0]);
        }

        TreeMap<Integer, Integer> map = new TreeMap<>((e1,e2)-> e1 - e2);
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)-> e1[0] - e2[0]);
        int capacity = C;

        for(int number = 1; number <= N; number++){

            pq.clear();

            // 만약 택배안에 도착지용 수화물이 있다면?
            if(map.containsKey(number)){
                capacity += map.get(number);
                answer += map.get(number);
                map.remove(number);
            }

            // val[0]은 다음 장소, val[1]은 택배 수량
            for(int[] val : list[number]){

                // 현재 위치에서 다음 목표 장소가 지금 트럭에 실린 마지막 목적지보다 더 가깝다면? 택배에서 가장 먼 곳에 도착해야할 택배들을 뺀다.
                while(!map.isEmpty() && val[0] < map.lastKey()){

                    if(val[0] < map.lastKey()){

                        int count = map.get(map.lastKey());
                        capacity += count;
                        pq.offer(new int[]{map.lastKey(), count});
                        map.remove(map.lastKey());

                    }

                }

                if(capacity > 0){
                    pq.offer(new int[]{val[0], val[1]});
                }

            }

            while(!pq.isEmpty()){

                if(capacity == 0){
                    break;
                }

                int[] val = pq.poll();
                int diff = getMin(capacity, val[1]);
                capacity -= diff;

                map.put(val[0], map.getOrDefault(val[0], 0) + diff);

            }
        }

        System.out.print(answer);

    }

    static int getMin(int a, int b){
        return Math.min(a, b);
    }

}

/*

8980 택배

그리디 문제

@ 사실 제 풀이는 시간이 좀 걸려서, 다른 사람의 풀이가 더 좋습니다.
@ 그래도 저는 어떻게 풀이했는지 정리하여 기술하고자 합니다.

저의 풀이의 핵심은
"각 장소마다 최대한 많은 택배를 싣되, 가장 가까운 도착지를 우선해서 싣는다. 만약, 이미 있는 택배들이 방해되면 그 수만큼 버린다."
이러한 풀이를 했던 이유는 그리디하게 최대한 많이 택배를 배송하는 방법은 최대한 앞 마을에서 많이 배송하는 것 이었습니다.
즉, 가장 거리가 가까운 마을에 최대한 많이 택배를 주는 방식이 정답이라고 깨달았습니다.

위의 로직을 적용하여 다음 예시 입력으로 풀이해보겠습니다.
6 60
5
1 2 30
1 6 40
2 5 70
3 4 40
5 6 60

최초로 1번 마을에서 {2번 마을행, 30개}, {6번 마을행, 30개}로 60칸을 꽉꽉 채워 싣습니다.

2번 마을에서 내려서 {2, 30}을 내립니다. 그럼 정답 += 30을 해줍니다.
2번 마을의 택배는 {5, 70}인데 이미 실린 {6,30}보다 도착지가 가깝습니다.
우선순위에 따라 {5도착행, 60개}를 실어야 합니다. 그러니까 {6,30}을 다 버립니다. 즉, 트럭에는 {5, 60}이 실려있습니다.

3번 마을에 도착합니다. 3번 마을에는 내릴 것이 없습니다. answer += 0 입니다.
3번 마을에 택배는 {4, 40}입니다. 트럭은 이미 꽉 차있습니다. 그러므로 {5, 60}을 일단 내립니다.
우선수위에 따라 {4, 40}을 먼저 싣고, 남은 20의 공간 만큼 {5, 20}을 싣습니다.

4번 마을에서 {4, 40}을 내립니다. answer += 40이 수행됩니다.
트럭에는 {5, 20}이 남아있습니다.

5번 마을에 {5, 20}을 내립니다. answer += 20.
5번 마을의 택배는 {6, 60}입니다. 트럭은 텅 비어있습니다.
{6, 60}을 싣고 6으로 출발합니다.

6에서 60을 내립니다. answer += 60
결과, answer = 150이 나옵니다.


저는 이렇게 문제를 풀이했고 이를 코드로 나타내어 풀이했습니다. 다만, 싣고 버리고 작업에 시간이 걸리므로 최적화면에서 아쉽게 생각하고 있습니다.
저보다 더 좋은 다른 분들의 풀이를 올릴까 했지만, 저는 이렇게 풀이했다는 점을 남기기 위해 올립니다.

*/