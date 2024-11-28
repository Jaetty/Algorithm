import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int L, R, C, answer;
    static char map[][][];
    static boolean visited[][][];
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static int[] dl = {-1,1};
    static StringBuilder sb;
    static int[] start, end;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        sb = new StringBuilder();

        while(true){

            st = new StringTokenizer(br.readLine());
            L = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());

            if(L==0 && R==0 && C==0){
                break;
            }

            visited = new boolean[L][R][C];
            map = new char[L][R][C];
            answer = Integer.MAX_VALUE;

            for(int l=0; l<L; l++){
                for(int r=0; r<R; r++){
                    String input = br.readLine();
                    for(int c=0; c<C; c++){
                        map[l][r][c] = input.charAt(c);
                        if(map[l][r][c]=='S'){
                            start = new int[] {l,r,c};
                            visited[l][r][c] = true;
                        }
                        if(map[l][r][c]=='E'){
                            end = new int[] {l,r,c};
                        }
                    }
                }
                br.readLine();
            }
            bfs();
            setString(answer);
        }

        System.out.print(sb.toString());
    }

    static void bfs(){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{start[0],start[1],start[2],0});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            if(rc[0]==end[0] && rc[1]==end[1] && rc[2]==end[2]){
                answer = rc[3];
                return;
            }

            for(int i=0; i<2; i++){

                int nl = rc[0] + dl[i];
                if(check(nl, rc[1], rc[2])){
                    continue;
                }
                visited[nl][rc[1]][rc[2]] = true;
                queue.add(new int[]{nl,rc[1],rc[2],rc[3]+1});
            }

            for(int i=0; i<4; i++){

                int nr = rc[1] + dx[i];
                int nc = rc[2] + dy[i];

                if(check(rc[0], nr, nc)){
                    continue;
                }

                visited[rc[0]][nr][nc] = true;
                queue.add(new int[]{rc[0],nr,nc, rc[3]+1});

            }
        }
    }

    static void setString(int min){
        if(min==Integer.MAX_VALUE){
            sb.append("Trapped!\n");
            return;
        }
        sb.append("Escaped in ").append(min).append(" minute(s).\n");
    }

    static boolean check(int nl, int nr, int nc){
        if(nl <0 || nr < 0 || nc < 0 || nl >= L || nr >=R || nc>=C || visited[nl][nr][nc] || map[nl][nr][nc] == '#'){
            return true;
        }
        return false;
    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제는 전형적인 BFS 문제로 3차원 배열에서의 탐색 구현이 가능한지를 확인하는 문제입니다.
 * 3차원 배열 탐색을 위한 3차원 입력, vistied 및 이동 부분만 고려해서 구현하면 쉽게 풀이가 가능합니다.
 *
 *
 * */