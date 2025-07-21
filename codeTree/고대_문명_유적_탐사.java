import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int K, M, answer;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};
    static int[] wall;

    static Queue<int[]> queue = new ArrayDeque<>();
    static StringBuilder sb = new StringBuilder();

    // 결과물은 1차 폭발 점수, 최종 합산 점수, 벽 유물 index, bfs 이후의 map
    static class Result{

        int first_blow, total_blow, wall_idx;
        int[][] map;

        Result(int blow, int wall_idx) {
            this.first_blow = blow;
            this.total_blow = 0;
            this.wall_idx = wall_idx;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        wall = new int[M];

        int[][] map = new int[5][5];


        for(int i = 0; i < 5; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 5; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            wall[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0,0, map);

    }

    static void dfs(int k,  int wall_idx, int[][] ori_map){

        if(k==K){
            return;
        }

        boolean flag = true;
        Result max = new Result(0,0);

        for(int limit_a=1; limit_a<4; limit_a++){

            // 열이 적은순이 먼저
            for (int j = 0; j <= 2; j++) {
                // 행이 적은 순 다음
                for (int i = 0; i <= 2; i++) {
                    int[][] rotated_map = cloneMap(ori_map);

                    // 회전 각도가 적은 순
                    for(int a=0; a<limit_a; a++){
                        rotated_map = rotate(i, j, rotated_map);
                    }

                    // 먼저 1차적 찾기만 실행하여 가장 점수가 높은걸 찾는다.
                    Result result = bfs(rotated_map, wall_idx, 0, true);

                    if(result.first_blow==0){
                        continue;
                    }
                    flag = false;

                    // 터진 값이 많은 순
                    if(max.first_blow < result.first_blow){
                        max = result;
                    }
                }
            }

        }

        // 1차에 아무것도 못찾았을 때 그냥 리턴한다.
        if(flag){
            return;
        }
        else{

            // 가장 점수가 높은 애를 연쇄 파괴해준다.
            max = bfs(max.map, max.wall_idx, max.total_blow,false);
            // 연쇄 파괴 후 점수를 출력하고, dfs를 실행한다.
            System.out.print(max.total_blow+" ");
            dfs(k+1, max.wall_idx, max.map);
            return;
        }

    }

    // Result를 리턴한다, boolean first가 있다면 딱 첫번째 폭발만 수행하고 빠져나온다.
    static Result bfs(int[][] map, int wall_idx, int total, boolean first){

        boolean flag = true;
        Result result = new Result(0,0);
        int cnt = total;

        while(flag){
            flag = false;
            queue.clear();

            boolean[][] visited = new boolean[5][5];
            List<int[]> erase = new ArrayList<>();

            for(int r=0; r<5; r++){
                for(int c=0; c<5; c++){

                    if(!visited[r][c]){
                        visited[r][c] = true;
                        queue.add(new int[]{r, c, map[r][c]});
                        List<int[]> path = new ArrayList<>();

                        while(!queue.isEmpty()){

                            int[] curr = queue.poll();
                            path.add(curr);

                            for(int d=0; d<4; d++){
                                int nr = curr[0] + dr[d];
                                int nc = curr[1] + dc[d];

                                if(nr < 0 || nc < 0 || nr >= 5 || nc >= 5 || visited[nr][nc] || map[nr][nc] != curr[2]){
                                    continue;
                                }

                                visited[nr][nc] = true;
                                queue.add(new int[]{nr, nc, curr[2]});

                            }
                        }

                        if(path.size() >= 3){
                            for(int[] val : path){
                                erase.add(val);
                            }
                        }
                    }

                }
            }

            if(!erase.isEmpty()){

                cnt += erase.size();

                flag = true;
                erase.sort((o1, o2) -> {
                    if(o1[1] == o2[1]){
                        return o2[0] - o1[0];
                    }
                    return o1[1] - o2[1];
                });

                for(int[] val : erase) {
                    map[val[0]][val[1]] = wall[wall_idx++];
                }

                if(first){
                    result.first_blow = cnt;
                    result.total_blow = cnt;
                    result.wall_idx = wall_idx;
                    result.map = cloneMap(map);
                    return result;
                }

            }

        }

        result.total_blow += cnt;
        result.wall_idx = wall_idx;
        result.map = cloneMap(map);

        return result;

    }

    static int[][] rotate(int sr, int sc, int[][] map){

        int[][] newMap = cloneMap(map);

        for(int r = 0; r <= 2; r++){
            for(int c= 0; c<=2; c++){
                newMap[sr+c][sc+(2-r)] = map[sr+r][sc+c];
            }
        }

        return newMap;
    }

    static int[][] cloneMap(int[][] map){
        int[][] clone = new int[5][5];
        for(int i=0; i<5; i++){
            clone[i] = map[i].clone();
        }
        return clone;
    }


}