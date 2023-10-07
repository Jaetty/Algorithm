import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N,M;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    static Deque<int[]> deque;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        deque = new ArrayDeque<>();
        map = new int[N][M];
        visited = new boolean[N][M];

        for(int i=0; i<N; i++){
            char[] arr = br.readLine().toCharArray();
            for(int j=0; j<M; j++){
                map[i][j] = arr[j]-'0';
            }
        }

        deque.add(new int[] {0,0,0});

        visited[0][0] = true;

        while(!deque.isEmpty()){
            int[] rc = deque.poll();

            if(rc[0]==N-1 && rc[1]==M-1){
                System.out.println(rc[2]);
                return;
            }

            for(int i=0; i<4; i++){
                int nr = rc[0]+dx[i];
                int nc = rc[1]+dy[i];

                if(nr<0 || nc <0 || nr>=N || nc>=M || visited[nr][nc]) continue;

                if(map[nr][nc]==0){
                    visited[nr][nc] = true;
                    deque.addFirst(new int[]{nr,nc,rc[2]});
                }else{
                    visited[nr][nc] = true;
                    deque.addLast(new int[]{nr,nc,rc[2]+1});
                }
            }
        }

    }

}

/*
 *
 * 그래프 이론 문제
 * 해당 문제는 다익스트라, BFS 응용 등을 통해 풀이할 수 있는 문제입니다.
 * 해당 문제의 경우 모든 노드들의 간선 비용이 0과 1밖에 없는 상황이기 때문에 0-1 BFS를 적용할 수 있습니다.
 * 보통 최단 거리를 찾을 때 사용하는 알고리즘은 다익스트라입니다.
 *
 * 다익스트라는 이진 힙을 이용하는 경우 O(ElogV) 시간에 수행이 가능합니다.
 * 0-1 BFS는 O(V+E)의 시간으로 문제 해결이 가능합니다.
 * Deque을 이용하여
 * 만약 다른 노드에 가는 비용이 0이라면 맨 앞에 넣어주고
 * 1이라면 덱의 맨 마지막에 넣어줍니다.
 * 이렇게하면 한 시작 노드에서 인접한 모든 0을 탐색할 것이고
 * 그 이후 1들을 탐색할 것이고 탐색한 1에서 0이 있다면 그 0부터 먼저 다 탐색하게 됩니다.
 * 이렇게 되면 방문한 노드를 다시 방문하는 경우는 없게 되므로
 * 최대 노드+간선 만큼의 탐색으로 문제가 해결됩니다.
 *
 *
 * */