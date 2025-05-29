import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, start, end;
    static List<int[]> edges[];
    static long[] dist;

    static final long MIN = Long.MIN_VALUE;
    static LinkedList<Integer> answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        edges = new List[N];
        dist = new long[N];
        answer = new LinkedList<>();
        Arrays.fill(dist, MIN);

        for (int i = 0; i < N; i++){
            edges[i] = new ArrayList<>();
        }

        for(int i=0; i<M; i++){

            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = -1 * Integer.parseInt(st.nextToken());

            edges[a].add(new int[]{b,c});
        }

        st = new StringTokenizer(br.readLine());
        // 각 지점마다의 점수를 저장함.
        int[] arr = new int[N+1];

        for (int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 입력 값 전처리
        for (int i = 0; i < N; i++){

            if(i==start){
                // 맨처음 시작 값을 설정함.
                dist[i] = arr[i];
            }

            for(int j=0; j<edges[i].size(); j++){
                int[] val = edges[i].get(j);
                // a에서 b로 갈 때 c 만큼 비용이 드는데, c 비용에 b지점에 도착하면 얻는 수익을 더해준다.
                edges[i].get(j)[1] += arr[val[0]];
            }
        }

        if(bellmanFord()){

            // 도달 불가능
            if(dist[end] == MIN){
                System.out.print("gg");
            }
            // 도달 가능
            else{
                System.out.print(dist[end]);
            }
            return;
        }
        else{
            // 무한하게 점수를 올릴 수 있는 경우
            System.out.print("Gee");
            return;
        }

    }

    static boolean bellmanFord(){

        List<Integer> check = new ArrayList<>();

        for (int i=0; i<N; i++){
            // 변화가 발생하는지 확인하는 변수
            boolean updated = false;

            for(int j=0; j<N; j++){
                for (int[] edge : edges[j]){

                    if( dist[j] != MIN && dist[edge[0]] < dist[j] + edge[1]){
                        dist[edge[0]] = dist[j] + edge[1];
                        updated = true;

                        // 사이클이 발생하는 노드들을 모으기
                        if(i==N-1){
                            check.add(edge[0]);
                        }
                    }

                }
            }
            // 변화가 없다면 굳이 N*N을 다 돌필요가 없음
            if(!updated){
                break;
            }
        }

        // 사이클이 발생한 노드에서부터 N까지 도달하는지 확인하여, 중요 길목에서 발생한 사이클인지 확인
        for(int val : check){

            if(canReach(val)) return false;

        }

        return true;
    }

    // 사이클이 발생한 노드에서 end로 갈 수 있는지 확인.
    static boolean canReach(int start){

        Queue<Integer> queue = new ArrayDeque<>();
        queue = new ArrayDeque<>();

        boolean[] visited = new boolean[N+1];
        visited[start] = true;

        queue.add(start);

        while(!queue.isEmpty()){

            int cur = queue.poll();
            if(visited[end]) return true;

            for(int [] edge : edges[cur]){

                if(!visited[edge[0]]){
                    visited[edge[0]] = true;
                    queue.add(edge[0]);
                }

            }

        }
        return false;
    }

}

/*

1219 오민식의 고민

밸만 포드 문제

해당 문제의 핵심 로직은 다음과 같습니다.
1. 밸만 포드로 가장 가중치 값이 크게 도착 도시에 도달하는 경로를 찾는다.
2. 전처리를 통해, 각 지점으로 갈 때의 edge 값을 수정해준다.
3. 시작 지점은 출발부터 점수를 얻고 시작한다.
4. 사이클이 발생했다면, 이 사이클이 end로 갈 수 있는 사이클인지 확인한다.

기초적인 형태의 밸만 포드 문제입니다.

굳이 따지면, 주의해야 하는 부분은 사이클 처리와 전처리 구간이라고 생각합니다.
먼저 어떤 지점에 도착했을 때 수익을 얻기 때문에, a에서 b로 갈 때 c 만큼의 비용이 드는 것이 아닌 c + b에서 수익이 곧 비용입니다.
이처럼 전처리를 해주면 밸만 포드 형태로 알고리즘을 수행하기 쉬워집니다.
또한, 첫 시작 위치에서도 수익을 얻고 시작합니다. 즉, 첫 시작 지점에서의 점수가 8이면 시작 점수는 dist[start] = 8입니다.

사이클 처리 부분은 양의 사이클이 발생했을 때, 이 사이클에서부터 end 지점에 도달할 수 있는지 확인하면 됩니다.
그러면 이론상 무한한 수익을 얻은 후 end에 도달할 수 있기 때문에 정답은 Gee가 됩니다.
그러나, 이 사이클이 end에 도달하지 못하는 변두리에 발생한 사이클이라면 고려 대상에서 제외하면 됩니다.

이처럼 밸만 포드를 이용하면 쉽게 풀이가 가능한 문제입니다.

*/