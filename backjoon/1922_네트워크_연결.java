import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Edge{
        int from, to, cost;
        Edge(int from, int to, int cost){
            this.from = from;
            this.to = to;
            this.cost = cost;
        }
    }

    static int[] parents;
    static int N, M;

    static Edge[] edges;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        parents = new int[N+1];
        edges = new Edge[M];

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            edges[i] = new Edge(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
        }

        Arrays.sort(edges, (e1,e2)->e1.cost-e2.cost);

        makeSet();

        int count = 0;
        int answer = 0;

        for(int i=0; i<M; i++){
            if(count==N) break;
            Edge edge = edges[i];
            if(unionFind(edge.from, edge.to)) continue;
            count++;
            answer += edge.cost;
        }

        System.out.println(answer);

    }

    static void makeSet(){
        for(int i=0; i<=N; i++){
            parents[i] = i;
        }
    }

    static int findSet(int x){
        if(parents[x]==x) return x;
        return parents[x] = findSet(parents[x]);
    }

    static boolean unionFind(int x, int y){

        int nx = findSet(x);
        int ny = findSet(y);

        if(nx==ny) return true;

        parents[nx] = ny;

        return false;

    }

}

/*
* 그래프 이론 (MST) 문제
*
* 해당 문제의 포인트는 컴퓨터들이 가장 최소의 비용으로 모두 연결해야한다는 점입니다.
* 즉 모든 vertext들이 edge로 연결되어야만 한다는 것이고 그렇다면 이 문제는 MST 문제라는 것을 알아낼 수 있습니다.
*
* MST의 대표적인 해결법은 프림 알고리즘과 크루스칼 알고리즘이 있고
* 해당 풀이는 전형적인 크루스칼 알고리즘을 적용하여 풀이하였습니다.
*
* ### 크루스칼 알고리즘은 탐욕적인 접근을 가지는 알고리즘입니다. 전체 edge에서 가장 비용이 적은 edge를 연결시켜주는 방법입니다.
* 여기서 주의해야하는 점은 그렇게 edge를 cost가 작은 순서로 뽑아내게 되면 이미 vertex끼리 연결되었을 경우가 있을 수 있습니다.
* 여기서 union find를 이용하여 노드들 끼리 서로 연결되었다면 무시하고 다음 edge를 고려하는 방법을 통해서 크루스칼 알고리즘을 구현할 수 있습니다. ####
*
*
* */