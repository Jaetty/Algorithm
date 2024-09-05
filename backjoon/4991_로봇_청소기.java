import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static char[][] map;
    static int cr, cc, count;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1,0,0};
    static List<int[]> dirty;
    static int[][] move;
    static int[] min;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        while (true){

            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());

            if(N==0 && M==0) break;

            map = new char[N][M];
            dirty = new ArrayList<>();

            count = 0;

            for(int i=0; i<N; i++){
                map[i] = br.readLine().toCharArray();
                for(int j=0; j<M; j++){
                    if(map[i][j]=='*'){
                        dirty.add(new int[]{i,j});
                        count++;
                    }
                    if(map[i][j]=='o'){
                        cr = i;
                        cc = j;
                    }
                }
            }

            move = new int[count+1][count];
            min = new int[count];
            boolean flag = false;

            for(int i=0; i<=count; i++){
                if(i==0){
                    flag = setting(cr,cc,i);
                    if(flag) break;
                }
                else{
                    int[] rc = dirty.get(i-1);
                    flag = setting(rc[0],rc[1],i);
                }
            }

            if(flag){
                sb.append(-1).append("\n");
                continue;
            }

            int answer = Integer.MAX_VALUE;

            for (int i=0; i<count; i++){
                min[i] = Integer.MAX_VALUE;
                recur(1, i, i, move[0][i], (1<<i));
                answer = Math.min(answer, min[i]);
            }

            sb.append(answer).append("\n");

        }
        System.out.print(sb.toString());
    }

    static void recur(int depth, int origin, int start, int sum, int bit){

        if(depth>=count){
            min[origin] = Math.min(min[origin], sum);
            return;
        }

        for(int i=0; i<count; i++){

            if( (bit & (1<<i) ) >= 1) continue;

            recur(depth+1, origin, i, sum + move[start+1][i], (bit | (1<<i)) );
        }

    }

    static boolean setting(int sr, int sc, int s){

        Integer[][] visit = bfs(sr, sc);

        for(int i=0; i<dirty.size(); i++){
            int[] rc = dirty.get(i);

            if(visit[rc[0]][rc[1]] == null){
                return true;
            }
            move[s][i] = visit[rc[0]][rc[1]];
        }

        return false;
    }

    static Integer[][] bfs(int sr, int sc){

        Integer[][] visited = new Integer[N][M];
        visited[sr][sc] = 0;

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{sr, sc, 0});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr <0 || nc < 0 || nr >=N || nc >= M || map[nr][nc] == 'x' || visited[nr][nc]!=null) continue;

                visited[nr][nc] = rc[2]+1;
                queue.add(new int[] {nr, nc, rc[2]+1});
            }
        }

        return visited;
    }

}

/*
* 그래프 이론 + 비트 마스킹 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 더러운 칸은 최대 10곳이다.
* 2. N, M은 각각 최대 20이다.
*
* 이 두 가지 포인트에 유념하고 로직을 세워보면 다음과 같이 전개할 수 있습니다.
* 문제는 로봇청소기가 출발하여 모든 더러운 지점을 최소한의 이동으로 청소해야만 합니다.
* 더러운 지점이 A, B, C가 있고 모든 지점은 벽으로 막혀있지 않다고 생각해보겠습니다.
* 그러면 로봇 청소기 위치를 o라고 한다면 o에서부터 갈 수 있는 경우의 수는
* o->A->B->C, o->A->C->B, o->B->A->C, o->B->C->A, o->C->A->B, o->C->B->A
* 이렇게 6가지 일 것입니다.
* 그러면 이때 문제를 해결하는 방법은 6가지의 경우 중 가장 이동 거리가 짧을 것을 고르는 것 입니다.
*
* 위의 포인트에서 더러운 곳은 최대 10곳, 배열의 최대 크기도 20*20이었습니다.
* 그러면 bfs의 시간은 O(N*M*4) = O(1600)이 될 것입니다.
* 여기서 알 수 있는 건 만약 경우의 수를 만드는 과정마다 bfs를 돌게하면 시간 초과가 날 수 밖에 없습니다.
*
* 하지만 가구를 피해야하기 때문에 어떤 지점에서 다른 지점까지 도달하는 거리를 알려면 bfs를 반드시 수행해야합니다.
* 이때 반대로 생각해서 로봇의 시작위치를 포함해서 각 더러운 지점마다 bfs를 통해 다른 더러운 지점에 도착하는 거리를 기억하게 만들어보면
* 더러운 곳은 최대 10곳, 로봇은 1곳이므로 O(1600)을 11번 반복하므로 O(17600)번으로 충분한 시간입니다.
* 즉 최대 11번만 bfs를 수행하여 각각의 위치에서 다른 위치로 가는 거리를 구해놓습니다.
*
* 이후 백트래킹을 통해서 경우의 수에 따라 현재 위치에서 아직 들리지 않은 위치로 가는 거리의 총합의 최소를 찾으면 됩니다.
* 이때 방문 여부는 비트마스킹을 이용하면 편리합니다. 어차피 더러운 곳이 최대 10곳이므로 비트로 충분히 나타낼 수 있습니다.
*
* 즉 정리하자면 bfs로 각 위치에서 다른 위치로 가는 거리를 구하고, 백트래킹으로 경우의 수마다 걸리는 총 움직임의 최소를 구해주면 됩니다.
*
*
* */