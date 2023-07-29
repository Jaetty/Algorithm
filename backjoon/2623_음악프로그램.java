import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static List<List<Integer>> list;
    static boolean contains[][];
    static int left[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        list = new ArrayList<>();
        list.add(new ArrayList<>());

        contains = new boolean[N+1][N+1];
        left = new int[N+1];

        for(int i=0; i<N; i++){
            list.add(new ArrayList<>());
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            st.nextToken();
            int before = 0;
            while(st.hasMoreTokens()){
                int temp = Integer.parseInt(st.nextToken());
                if(before!=0 && !contains[temp][before]){
                    left[temp]++;
                    contains[temp][before] = true;
                    list.get(before).add(temp);
                }
                before = temp;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for(int i=1; i<N+1; i++) if(left[i]==0) queue.add(i);
        StringBuilder sb = new StringBuilder();
        int count = 0;

        while(!queue.isEmpty()){
            int node = queue.poll();
            sb.append(node+"\n");

            for(int next : list.get(node)){
                left[next]--;
                if(left[next]==0) queue.add(next);
            }

            count++;
        }

        if(count<N) System.out.println(0);
        else System.out.print(sb);

    }
}

/*
 * 그래프 이론 (위상정렬) 문제
 *
 * 해당 문제는 전형적인 위상정렬 문제로 다음과 같이 볼 수 있습니다.
 * 가수를 노드로 그리고 보조 PD들의 순서를 각각 간선과 선후관계라고 생각하면 됩니다.
 * 즉 예시로 주어지는 입력을 차례대로 해석하면
 * 6 3
 * 3 1 4 3 -> 3번 노드에 도달하려면 4번 노드를 먼저 거처야하고 4번 노드에 도착하려면 1번 노드를 먼저 거쳐야함
 * 4 6 2 5 4 -> 4번 노드에 도달하려면 5번 노들 먼저 거처야함, 5번은 2를 먼저, 2는 6을 먼저 거처야함
 * 2 2 3 -> 3번 노드에 도달하려면 2번 노드를 먼저 거처야한다.
 *
 * 위와 같이 됩니다. 위상 정렬로 이 문제를 해결할 수 있고 위상정렬의 기본 개념은 다음과 같습니다.
 * 1. 각 노드별로 진입 차수를 계산합니다.
 * 2. 진입 차수가 0인 노드를 큐에 넣어줍니다.
 * 3. 큐에서 노드를 뽑아내어 갈 수 있는 다른 노드들의 진입차수를 -1 해줍니다.
 * 4. 3번 과정을 통해 진입 차수가 0이 된 노드가 있다면 2번과 3번을 반복합니다.
 *
 * 위상 정렬의 개념은 위의 과정과 같고 저는 해당 사항들을 구현하여 문제를 풀었습니다.
 *
 */