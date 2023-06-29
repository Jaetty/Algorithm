import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N, E;

    static Edge[] edges;
    static int[] parent;

    static class Edge implements Comparable {
        int v1,v2, c;

        Edge(int v1, int v2, int c){
            this.v1 = v1;
            this.v2 = v2;
            this.c = c;
        }

        @Override
        public int compareTo(Object o) {
            Edge e2 = (Edge) o;
            return this.c-e2.c;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        edges = new Edge[E];
        parent = new int[N+1];

        for(int i=0; i<E; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            edges[i] = new Edge(a,b,c);
        }

        Arrays.sort(edges);
        makeSet();

        int cnt = 0;
        int max = 0;
        int answer = 0;

        // 크루스칼 알고리즘 -> 그래프의 노드를 잇는 전체 간선(Edge)에서 가장 비용이 작은 것을 차례로 골라서 사이클이 발생하지 않으면 이어준다.
        for(int i=0; i<E; i++){
            Edge edge = edges[i];
            if(union(edge.v1, edge.v2)){
                cnt++;
                max = Math.max(max, edge.c);
                answer += edge.c;
            }
            if(cnt==N-1) break;
        }

        System.out.println(answer-max);
    }

    // 유니온 파인드를 위한 초기 설정
    static void makeSet(){
        for(int i=1; i<=N; i++){
            parent[i] = i;
        }
    }

    // 유니온 파인드에서 서로 같은 부모를 가지는지 확인하는 기능
    static int findSet(int x){
        if(parent[x]==x) return x;
        else return parent[x] = findSet(parent[x]);
    }

    static boolean union(int x, int y){
        int px = findSet(x);
        int py = findSet(y);

        if(px==py) return false;
        parent[py] = px;

        return true;
    }

}
/*
* MST 문제
* 해당 문제의 포인트는 마을을 두개로 분리할 것이라는 점, 하지만 각 마을에 특정한 집들은 모두 연결되어 있어야하고 마을은 하나 이상의 집을 가진다는 점이다.
* 그렇다면 쉽게 생각할 수 있는 방법이 일단 마을의 모든 집을 가장 최저 비용으로 연결하게 만든다.
* 그 다음 모두가 연결이 되었다면 가장 비용을 줄이면서 두 마을로 나누는 방법은 가장 비용이 높은 길 하나를 제거하는 것이다.
* 그렇게 하면 위의 조건을 만족하는 두 마을을 만들 수 있다.
*
* 이 문제는 최소 스패닝 트리 문제이고 보통 MST 풀이용 알고리즘은 프림과 크루스칼 알고리즘이 있다. 필자는 이 문제를 푸는데 크루스칼을 이용하였다.
* 크루스칼 알고리즘의 핵심은 가장 그래프의 모든 간선 중 가장 비용이 적은 간선을 고르고 이 간선을 골랐을 때 사이클이 발생하지 않는지만 확인해주면 된다.
* 사이클의 발생은 유니온 파인드를 이용하여 알 수 있으므로 유니온 파인드를 통해 모두 공통의 루트를 지니면 사이클이 발생했다는 뜻이다.
* 즉 사이클이 발생하지 않고 최소 비용의 간선들을 고른 후 가장 간선의 비용 값이 높았던 값을 빼주면 해당 문제를 해결할 수 있다.
*
* */