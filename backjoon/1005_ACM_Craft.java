import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static ArrayList<ArrayList<Integer>> vertexes;
    static int N,M,end;
    static ArrayList<Set<Integer>> possible;
    static int[] dist;
    static StringBuilder sb;

    static class Vertex{
        int start, end, t;

        Vertex(int start, int end, int t){
            this.start = start;
            this.end = end;
            this.t = t;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        sb = new StringBuilder();

        for(int tc=0; tc<T; tc++){

            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());
            dist = new int[N+1];

            vertexes = new ArrayList<>();
            vertexes.add(new ArrayList<>());

            possible = new ArrayList<>();
            possible.add(new HashSet<>());

            for(int i=1; i<=N; i++){
                dist[i] = Integer.parseInt(st.nextToken());
                vertexes.add(new ArrayList<>());
                possible.add(new HashSet<>());
            }

            for(int i=1; i<=M; i++){
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                vertexes.get(a).add(b);
                possible.get(b).add(a);

            }

            end = Integer.parseInt(br.readLine());

            search();

        }

        System.out.println(sb);

    }

    static void search(){

        int localDist[] = new int[N+1];

        Queue<Vertex> queue = new ArrayDeque<>();

        for(int i=1; i<=N; i++){
            if(possible.get(i).isEmpty()){
                if(i==end){
                    sb.append(dist[i]+localDist[i]+"\n");
                    return;
                }
                for(Integer val : vertexes.get(i)){
                    queue.add(new Vertex(i, val, dist[i]));
                }
            }
        }

        while(!queue.isEmpty()){

            Vertex vertex = queue.poll();

            possible.get(vertex.end).remove(vertex.start);
            localDist[vertex.end] = Math.max(localDist[vertex.end], vertex.t);

            if(possible.get(vertex.end).isEmpty()){

                if(vertex.end==end){
                    sb.append(dist[vertex.end]+localDist[vertex.end]+"\n");
                    return;
                }

                for(Integer val : vertexes.get(vertex.end)){
                    queue.add(new Vertex(vertex.end, val, dist[vertex.end]+localDist[vertex.end]));
                }
            }

        }

    }
}
/*
* dp, 그래프 이론, 위상 정렬 등으로 풀 수 있는 문제 (필자는 그래프의 특성으로 풀었다.)
*
* 이 문제는 여러 방법으로 풀 수 있는데 최초로 풀었을 때 성공한 그래프 관점에서의 풀이를 기초로 설명하겠습니다.
* 먼저 그래프 탐색을 응용하면 문제를 풀 수 있겠다 라고 최초로 생각이 들었고 주목해야하는 포인트는 다음과 같았습니다.
*
* 1. 앞에 한 특정한 노드 N을 지으려면 N이 지어지기 전에 먼저 건설되야 하는 건물들이 건설되야 한다. (마치 스타크래프트 빌드처럼)
* 2. 건물은 각각 지어지는데 필요한 시간이 있다.
*
* 그렇다면 위의 포인트에서 구현하기 위해 필요한 기능들이 무엇이 있을까 생각을 했습니다.
* Q1. 어떤 건물들이 지어질 수 있는가를 판단하게 만들어줘야겠다.
* Q2. 시간은 결국 지으려는 건물에 도착한 시간 중 가장 늦은 시간을 더해주면 되겠다.
*
* 이 둘을 아래와 같이 구현했습니다.
* A1. HashSet을 이용하여 이 set에 앞에서 먼저 지어져야하는 건물들을 담아두자.
*     만약 set이 비어있다면 그건 앞에서 지어져야하는 건물들이 다 지어졌다는 뜻이다.
*     ** HashSet으로 고른 이유는 add, remove, contains 등의 속도가 O(1) 이기 때문입니다.
*
* A2. local_dist라는 배열을 만들어서 이 배열에 다음으로 짓는 건물에 대해 가장 시간이 오래 걸리는 값을 저장해두자
*     문제에서 제공하는 예시로 예를 들면 1은 10초가 걸리고 2는 1초, 3은 100초, 4는 10초 걸린다면
*     1이 지어지면 10초가 걸립니다. local_dist[2] = local_dist[3] = 10 이 됩니다.
*     1이 지어졌기 때문에 이제 2와 3을 지을 수 있는데 이때 local_dist의 값을 이용하여 dist[2] += local_dist[2], dist[3] += local_dist[3] 으로 해줍니다.
*     이제 2와 3을 지었으니 4를 지을 수 있는데 dist[2]는 11초 dist[3]은 110초 이기 때문에 둘 중 큰 dist[3]을 골라서 local_dist[4] = dist[3]으로 해줍니다.
*     그러므로 4는 dist[4] += local_dist[4]이기 때문에 120초가 됩니다.
*     이렇게 하면 특정 건물 N이 지어지기 위해 필요한 가장 오래 걸린 시간을 기억 할 수 있게 됩니다.
*
* 이렇게 두가지 조건들을 만족시키고 queue를 통해 트리를 탐색시키는 부분을 구현하여 문제를 해결할 수 있습니다.
* **** dp or 위상정렬을 이용하면 더욱 성능이 높힐 수 있습니다.
*
* */