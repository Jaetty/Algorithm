import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static int[] topology;
    static List<Integer> list[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        topology = new int[N + 1];
        list = new List[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int before = Integer.parseInt(st.nextToken());
            int after = Integer.parseInt(st.nextToken());

            if (list[before] == null) {
                list[before] = new ArrayList<>();
            }
            list[before].add(after);
            topology[after]++;
        }

        PriorityQueue<Integer> pqueue = new PriorityQueue<>();

        for (int i = 1; i <= N; i++) {
            if (topology[i]<=0) {
                pqueue.add(i);
            }
        }

        StringBuilder sb = new StringBuilder();

        while(!pqueue.isEmpty()){

            int index = pqueue.poll();
            sb.append(index+" ");

            if(list[index]!=null){
                for(int var : list[index]){
                    topology[var]--;
                    if(topology[var]<=0){
                        pqueue.add(var);
                    }
                }
            }
        }

        System.out.println(sb.toString());


    }
}

/*
* 위상 정렬 + 힙 문제
*
* 해당 문제는 위상 정렬과 힙을 이용하여 풀이 가능한 문제로 포인트는 다음과 같습니다.
* 1. 문제는 N까지 있고 (숫자는 난이도를 나타냅니다.) 1~N까지 모두 풀이해야한다.
* 2. 어떤 문제를 풀기 위해 먼저 풀어야하는 숫자가 주어지면 반드시 이 문제를 먼저 풀어야한다.
* 3. 현재 풀이 가능한 문제가 여러개 있다면 숫자가 낮은 것을 먼저 풀이하여야한다.
*
* 2번 포인트를 통해 알 수 있는 점은 위상 정렬을 이용하면 된다는 점을 알 수 있습니다.
* 다만 위상 정렬만 사용하게 되면 3번을 충족시키지 못하는 경우가 나올 수 있습니다.
* 그렇기 때문에 3번을 위해 우선 순위 큐로 오름차순으로 숫자를 뽑을 수 있도록 이용합니다.
*
* 예시 입력으로 다음이 주어지면
* 4 2
* 4 2
* 3 1
*
* 먼저 위상을 위한 topology[]라는 배열이 있고 크기는 0인덱스를 제외하기 위해 N+1의 크기를 갖습니다. topology[] = {0,0,0,0,0}
* 2번 문제를 풀기 위해서 4를 먼저 풀이해야하고, 1을 풀기 위해서는 3을 먼저 풀어야하기 때문에 topology[] = {0, 1, 1, 0, 0}이 됩니다.
* N번의 순회로 풀이를 위해 선수 조건이 없는(topolgy값이 0) 숫자를 PriorityQueue에 넣어줍니다. 그러면 pqueue에는 3,4가 들어가게 됩니다.
* 3을 꺼내서 풀이를 하면 3이 선수 조건이었던 1의 topology값이 하나 내려가서 topology[] = {0, 0, 1, 0, 0} 가 됩니다.
* 그러면 topology가 0이기 때문에 pqueue에 넣어주고 원래라면 4를 할 차례였지만 우선순위가 오름차순이기 때문에 1이 다음으로 나오게 됩니다.
* 그렇게 진행하게 되면 출력 결과는 3 1 4 2가 나오게 됩니다.
*
* 이렇듯 위상 정렬과 힙을 이용하면 해당 문제를 풀이할 수 있습니다.
*
* */