import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int N;
    static List<int[]> distances[];
    static int[] parents;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        parents = new int[N+1];
        makeSet();

        distances = new List[3];
        distances[0] = new ArrayList<>();
        distances[1] = new ArrayList<>();
        distances[2] = new ArrayList<>();

        for(int i=0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            distances[0].add(new int[] {Integer.parseInt(st.nextToken()),i+1});
            distances[1].add(new int[] {Integer.parseInt(st.nextToken()),i+1});
            distances[2].add(new int[] {Integer.parseInt(st.nextToken()),i+1});
        }

        Collections.sort(distances[0], (e1,e2)->e1[0]-e2[0]);
        Collections.sort(distances[1], (e1,e2)->e1[0]-e2[0]);
        Collections.sort(distances[2], (e1,e2)->e1[0]-e2[0]);

        int count=0;
        long sum = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)->e1[0]-e2[0]);

        for(int k=0; k<3; k++){
            for(int i=0; i<N-1; i++){
                pq.add(new int[]{Math.abs(distances[k].get(i)[0] - distances[k].get(i+1)[0]), distances[k].get(i)[1], distances[k].get(i+1)[1]});
            }
        }


        while(count < N-1 && !pq.isEmpty()){

            int[] temp = pq.poll();

            if(union(temp[1], temp[2])) continue;

            sum += temp[0];

            count++;

        }

        System.out.println(sum);

    }

    static void makeSet(){
        for(int i=1; i<=N; i++){
            parents[i] = i;
        }
    }

    static int findSet(int x){
        if(parents[x]==x) return parents[x];
        return parents[x] = findSet(parents[x]);
    }

    static boolean union(int x, int y){

        int nx = findSet(x);
        int ny = findSet(y);

        if(nx==ny) return true;

        parents[nx] = ny;
        return false;
    }

}
/*
 * 최소 스패닝 트리 문제
 * 해당 문제는 최소 스패닝 트리 문제로 크루스칼 알고리즘으로 풀었습니다. 이 문제의 포인트는 다음과 같습니다.
 * 1. A(Xa, Ya, Za)와 B(Xb, Yb, Zb) 있을 때 서로의 vertex를 연결하는 edge의 최소 비용은 min(|Xa-Xb|, |Ya-Yb|, |Za-Zb|)
 *    즉 x y z 끼리 서로 비교해서 가장 차가 적은 값이 최소 비용이라는 뜻입니다.
 * 2. N-1개의 터널(간선)으로 모든 행성이 최소 비용으로 연결하게 한다.
 * 3. N은 최대 100,000
 *
 * 위의 핵심 포인트들을 고려해보면
 * 2번을 통해서 이 문제가 최소 스패닝 트리 문제라는 것을 파악할 수 있습니다.
 * 1번을 통해 3번의 비교가 필요하다는 것을 알 수 있습니다.
 * 3번을 통해 기존의 MST 알고리즘 방법으로는 시간, 혹은 메모리 초과가 날 수 있다는 것을 짐작할 수 있습니다.
 *
 *
 * 기존의 크루스칼로 하자면 너무나 많은 간선의 경우의 수로 메모리 초과가 발생할 것이고
 * 프림 알고리즘의 경우는 N^2이기 때문에 시간초과가 발생할 것 입니다.
 * 그렇다면 간선을 채택하는 방법에서 메모리가 초과되지 않도록 다른 방법을 생각해보면 크루스칼로 풀 수 있지 않을까 라고 생각하게 되었습니다.
 *
 * 이 문제에서 주목할 점이 바로 예제 입력에서 나옵니다.
 * 5
 * 11 -15 -15
 * 14 -5 -15
 * -1 -1 -5
 * 10 -4 -1
 * 19 -4 19
 * 위의 예제 입력을 보시면 Z의 경우 오름차순 정렬이 되어있습니다. 저는 여기서 힌트를 알 수 있었는데 Z의 경우 오름차순으로 정렬이 되어 있습니다.
 * -15와 -15 끼리 연결하면 비용이 0, -15와 -5로 연결하면 비용 10, -5와 -1과 연결하면 비용 4, -1과 19를 연결하면 비용 18
 * 이게 Z로만 연결한다고 했을 때 최소 비용임을 알게 되었습니다.
 * 그렇다면 최소비용끼리 연결하는 간선을 구하는 방법은 X, Y, Z를 오름차순 정렬하고 순차적으로 비용값을 구하면 그게 최소비용 간선이라는 점을 알게 되었습니다.
 *
 * 그러면 크루스칼 알고리즘을 위해 구해줘야하는 간선은 모든 경우의 수를 따지는 것이 아닌 정렬을 통해 해결할 수 있게 됩니다.
 * 오름차순 정렬 할 때 좌표값을 기준으로 정렬하고 어떤 노드의 좌표 인지만 기억해줍니다.
 * 그 이후 x, y, z 각각의 간선 비용과 해당하는 노드들을 비용을 기준으로 오름차순 정렬이 되는 우선순위 큐에 넣어줍니다.
 * 이후는 크루스칼 알고리즘과 같이 N-1의 간선이 생길 때 까지 큐에서 값을 뽑으면서 사이클이 생성되었는지 판단하면 해결할 수 있습니다.
 *
 */