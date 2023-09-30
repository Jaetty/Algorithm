import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M, K;
    static int[][] map, fired; // 전체 맵, 가장 최근에 쏜 값을 저장
    static boolean[][] path; // 레이저나 포탄의 피폭 경로들을 저장함
    static int[] dx = {0,1,0,-1}; //우 하 좌 상
    static int[] dy = {1,0,-1,0};

    static PriorityQueue<int[]> attack, defence;

    static class Road{
        int r,c;
        List<int[]> path;

        Road(int r, int c){
            this.r = r;
            this.c = c;
            path = new ArrayList<>();
        }

    }
    static Road road;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        attack = new PriorityQueue<>((e1,e2)->{
            if(map[e1[0]][e1[1]]==map[e2[0]][e2[1]]){
                if(fired[e1[0]][e1[1]]==fired[e2[0]][e2[1]]){
                    if(e1[0]+e1[1] == e2[0]+e2[1]){
                        return e2[1] - e1[1];
                    }
                    else return (e2[0]+e2[1]) - (e1[0]+e1[1]);
                }
                else return fired[e2[0]][e2[1]] - fired[e1[0]][e1[1]];
            }
            else return map[e1[0]][e1[1]] - map[e2[0]][e2[1]];
        });

        defence = new PriorityQueue<>((e1,e2)->{
            if(map[e1[0]][e1[1]]==map[e2[0]][e2[1]]){
                if(fired[e1[0]][e1[1]]==fired[e2[0]][e2[1]]){
                    if(e1[0]+e1[1] == e2[0]+e2[1]){
                        return e1[1] - e2[1];
                    }
                    else return (e1[0]+e1[1]) - (e2[0]+e2[1]);
                }
                else return fired[e1[0]][e1[1]] - fired[e2[0]][e2[1]];
            }
            else return map[e2[0]][e2[1]] - map[e1[0]][e1[1]];
        });

        map = new int[N][M];
        fired = new int[N][M];
        int time = 1;

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        while(K>0){

            findAttackAndDefence();
            if(attack.size()==1 || defence.size()==1) break;
            int[] start = attack.peek();
            map[start[0]][start[1]] += N+M;

            road = null;
            bfs(attack.peek(), defence.peek());

            path = new boolean[N][M];
            int point = map[start[0]][start[1]];
            path[start[0]][start[1]] = true;

            int[] end = defence.peek();
            map[end[0]][end[1]] -= point;
            path[end[0]][end[1]] = true;

            fired[start[0]][start[1]] = time;

            if(road==null) bomb(point);
            else laser(point);

            repair();

            time++;
            K--;
        }

        int answer = 0;
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(map[i][j]<=0) continue;
                answer = Math.max(answer, map[i][j]);
            }
        }

        System.out.println(answer);

    }

    // 1번 공격이 가능한지 최단 경로 찾기
    static void bfs(int[] start, int[] end){

        Queue<Road> queue = new ArrayDeque<>();
        queue.add(new Road(start[0], start[1]));
        boolean[][] visited = new boolean[N][M];
        visited[start[0]][start[1]] = true;

        while(!queue.isEmpty()){

            Road rc = queue.poll();

            for(int i=0; i<4; i++){
                int nr = rc.r + dx[i];
                int nc = rc.c + dy[i];

                if(nr >= N){
                    nr = 0;
                }
                else if(nr <0){
                    nr = N-1;
                }

                if(nc >= M){
                    nc = 0;
                }
                else if(nc<0){
                    nc = M-1;
                }

                if(visited[nr][nc]) continue;

                if(map[nr][nc]<=0) continue;

                visited[nr][nc] = true;
                Road nRoad = new Road(nr, nc);

                for(int[] val : rc.path){
                    nRoad.path.add(val);
                }

                if(nr==end[0] && nc==end[1]){
                    road = nRoad;
                    return;
                }

                nRoad.path.add(new int[]{nr, nc});

                queue.add(nRoad);

            }

        }

    }

    // 1번 공격
    static void laser(int point){

        for(int i=0; i<road.path.size(); i++){
            int[] rc = road.path.get(i);
            map[rc[0]][rc[1]] -= (point/2);
            path[rc[0]][rc[1]] = true;
        }

    }

    // 2번 공격
    static void bomb(int point){

        int[] dx = {-1,-1,-1, 0, 0, 1, 1, 1};
        int[] dy = {-1,0,1, -1, 1, -1, 0, 1};

        int[] rc = defence.peek();
        int[] start = attack.peek();

        for(int i=0; i<8; i++){

            int nr = rc[0] + dx[i];
            int nc = rc[1] + dy[i];

            if(nr >= N){
                nr = 0;
            }
            else if(nr <0){
                nr = N-1;
            }

            if(nc >= M){
                nc = 0;
            }
            else if(nc<0){
                nc = M-1;
            }

            if(map[nr][nc]<=0 || (nr==start[0] && nc==start[1])) continue;

            map[nr][nc] -= point/2;
            path[nr][nc] = true;

        }

    }

    // 정비
    static void repair(){
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(map[i][j]<=0) continue;
                if(path[i][j]) continue;
                map[i][j]++;
            }
        }
    }

    // 공격, 수비 찾기
    static void findAttackAndDefence(){
        attack.clear();
        defence.clear();
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                if(map[i][j]<=0) continue;
                attack.add(new int[]{i,j});
                defence.add(new int[]{i,j});
            }
        }
    }

}