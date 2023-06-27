import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N, start, end;
    static List<List<Vertex>> vertexes;

    static class Vertex{
        int v,c;
        // 1. 경로를 기억하는 ArrayList
        ArrayList<Integer> path;

        Vertex(int v, int c){
            this.v = v;
            this.c = c;
            this.path = new ArrayList<>();
        }

        // 2. 이 문제를 풀 때의 핵심 부분
        public void setPath(ArrayList<Integer> path){
            // 경로값들을 가져와 기록해준다.
            for(Integer val : path){
                this.path.add(val);
            }
            // 마지막으로 나를 기억하게 해준다.
            this.path.add(this.v);
        }

        // 시작 지점 세팅 용
        public void setFirstPath(){
            this.path.add(this.v);
        }

    }

    static int[] dist;
    static boolean[] visit;
    static final int INF = 1000000000;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int E = Integer.parseInt(br.readLine());

        vertexes = new ArrayList<>();
        vertexes.add(new ArrayList<>());

        visit = new boolean[N+1];
        dist = new int[N+1];
        Arrays.fill(dist,INF);

        for(int i=0; i<N; i++){
            vertexes.add(new ArrayList<>());
        }

        for(int i=0; i<E; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            vertexes.get(a).add(new Vertex(b,c));
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        dijkstra();

    }

    static void dijkstra(){

        PriorityQueue<Vertex> pq = new PriorityQueue<>((e1,e2)->e1.c-e2.c);
        dist[start] = 0;
        Vertex vertex = new Vertex(start,0);
        // 첫 시작 경로값을 세팅한다.
        vertex.setFirstPath();

        pq.add(vertex);

        while(!pq.isEmpty()){

            Vertex node = pq.poll();

            if(node.c > dist[node.v]) continue;

            if(visit[node.v]) continue;

            visit[node.v] = true;

            if(node.v == end){
                System.out.println(dist[node.v]);
                System.out.println(node.path.size());
                for(Integer val : node.path){
                    System.out.print(val + " ");
                }
                return;
            }

            for(Vertex edge: vertexes.get(node.v)){
                if(dist[node.v]!=INF && dist[edge.v] > dist[node.v] + edge.c){
                    dist[edge.v] = dist[node.v] + edge.c;
                    // 3. 경로값을 기억해준다.
                    Vertex temp = new Vertex(edge.v, dist[edge.v]);
                    temp.setPath(node.path);
                    pq.add(temp);
                }
            }

        }



    }

}
/*
* 다익스트라 문제
* 1916 최소비용 구하기를 풀었다면 조금만 응용하면 금방 풀 수 있는 문제이다.
* 이 문제에 추가된 점이 바로 어떤 경로에서 출발해서 도착하는 지를 출력해야한다는 점이다.
* 즉 경로를 저장해야하는 점이 추가된 것 뿐인데 이 방법은 조금만 생각하면 간단하다.
*
* 결국 다익스트라는 pq를 통해 가장 비용이 적게, 그리고 먼저 도착한 노드 순서로 탐색을 한다.
* 그렇다면 그 과정 하나하나를 기록해두고 도착 지점인 노드에 도달했을 때 그 과정을 다 출력하면 된다.
* 필자는 ArrayList를 이용하여 기록을 했고 그 방법은 주석 2번과 3번에서 나타나있다.
*
* 결국 마지막에 출력하면 되는 것은
* 1. 마지막 노드에 도달하는 최소 비용값과
* 2. ArrayList의 크기
* 3. ArrayList의 요소들
* 이렇게 출력해주면 문제가 해결된다.
*
*
* */