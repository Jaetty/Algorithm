import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, K;
    static PriorityQueue<int[]> jewelry, pqueue;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        jewelry = new PriorityQueue<>((e1, e2)->e1[0]-e2[0]);
        pqueue = new PriorityQueue<>((e1,e2)->{
            if(e1[1]==e2[1]){
                return e1[0] - e2[0];
            }
            return e2[1] - e1[1];
        });

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int[] arr = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
            jewelry.add(arr);
        }

        int[] bags = new int[K];

        for(int i=0; i<K; i++){
            int val = Integer.parseInt(br.readLine());
            bags[i] = val;
        }

        Arrays.sort(bags);

        long answer = 0;

        for(int i=0; i<K; i++){

            while(!jewelry.isEmpty()){
                if(jewelry.peek()[0] <= bags[i]){
                    pqueue.add(jewelry.poll());
                }
                else break;
            }
            if(!pqueue.isEmpty()) answer += pqueue.poll()[1];
        }

        System.out.println(answer);
    }


}

/*
* 그리디 + 힙 문제
*
* 해당 문제의 경우 입력으로 주어지는 값이 크기 때문에 시간 초과를 고려하여 풀이하여야 합니다.
* 저도 처음에는 PriorityQueue와 ArrayList에 lower bound를 이용하여 풀이를 시도하려고 했는데 remove의 동작이 O(N)이 걸리므로 시간 초과가 되었습니다.
* 해당 문제는 그리디 접근으로 풀이가 가능합니다. 풀이는 다음과 같습니다.
*
* (또한 입력되는 양과 가치 값도 크기 때문에 정답은 반드시 long형으로 해줘야합니다. )
*
* 1. 가방과 보석에 대해 주어지는 입력을 받아 둘 다 오름차순으로 정렬을 수행합니다. (저는 각각 bags, jewelry에 넣었습니다.)
* 2. PriorityQueue를 하나 만들고(pqueue) 정렬 기준은 보석의 가격이 높은 순, 무게가 낮은 순으로 정렬하도록 합니다.
* 3. 오름차순으로 정렬된 가방(bags)에서 요소(가방의 크기)를 하나씩 꺼냅니다.
* 4. 오름차순으로 정렬된 보석(jewelry)에서 현재 가방 크기보다 크기가 이하인 모든 보석을 2번에서 만든 pqueue에 넣어줍니다.
* 5. 4번 과정을 통해 pqueue에 값이 있다면 그 중 가장 앞에 있는 요소를 꺼내서 그 가치를 answer에 더해줍니다.
* 6. pqueue 안에 있는 값을 clear해줄 필요 없습니다. 왜냐하면 다음 가방 크기는 반드시 바로 전 가방 크기 이상이기 때문에 다음에 pqueue에 추가되는 것도 포함해서 가장 큰 값을 poll하면 됩니다.
*
* 이렇게 그리디하게 내가 가진 가방에 들어갈 수 있는 보석 중 가격이 가장 높은 값을 더해주는 방식으로 접근하면 문제를 풀이할 수 있습니다.
*
*
* */