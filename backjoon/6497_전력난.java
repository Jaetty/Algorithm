import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[] parent;
    static int[] parent_size;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        while(true){

            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken()), E = Integer.parseInt(st.nextToken());

            if(N==0 && E==0) break;

            setParent(N);
            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((e1,e2)-> e1[2]-e2[2]);
            int sum = 0;

            for(int i=0; i<E; i++){
                st = new StringTokenizer(br.readLine());

                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());

                sum += cost;
                priorityQueue.add(new int[]{start, end, cost});
            }


            while(N > 0 && !priorityQueue.isEmpty()){

                int[] val = priorityQueue.poll();

                if(union(val[0], val[1])){
                    sum -= val[2];
                    N--;
                }

            }

            sb.append(sum+"\n");

        }

        System.out.print(sb.toString());
    }

    static void setParent(int N){
        parent = new int[N];
        parent_size = new int[N];
        for(int i=0; i<N; i++){
            parent[i] = i;
            parent_size[i]=1;
        }
    }

    static int find(int x){
        if(parent[x]==x) return x;
        return parent[x] = find(parent[x]);
    }

    static boolean union(int x, int y){

        int nx = find(x);
        int ny = find(y);

        if(nx==ny){
            return false;
        }

        if(parent_size[nx] <= parent_size[ny]){
            parent[nx] = ny;
            parent_size[ny] += parent_size[nx];
        }else{
            parent[ny] = nx;
            parent_size[nx] += parent_size[ny];
        }

        return true;
    }

}

/*
 * MST 문제
 *
 * 전형적인 MST 알고리즘 문제로 저는 크루스칼 알고리즘을 사용하였습니다.
 *
 * 포인트는 크루스칼을 통해 cost가 작은 순서로 Edge를 꺼내고 이 edge의 a와 b 노드가 아직 서로 연결되지 않은 상태라면
 * 그 둘을 연결해주고, cost 값 만큼 전체 cost에서 빼주면 됩니다.
 *
 * 여기서 노드가 연결되었는지 확인하는 방법은 유니온 파인드를 사용하였습니다.
 * 유니온 파인드에서 트리의 크기를 비교하는 과정이 있는데 이건 Small to Large Trick, 반드시 작은 트리를 큰 트리에 접목시킴으로 더 빠른 효과를 낼 수 있습니다.
 *
 *
 * */