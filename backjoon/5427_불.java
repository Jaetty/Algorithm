import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int N, H, W, answer;

    static int[] dx = {1,-1,0,0,};
    static int[] dy = {0,0,1,-1};

    static boolean[][] blocked;
    static boolean[][] visited;
    static Queue<int[]> fire;
    static Queue<int[]> person;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        for(int i=0; i<N; i++){

            st = new StringTokenizer(br.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            person = new ArrayDeque<>();
            fire = new ArrayDeque<>();
            answer = Integer.MAX_VALUE;

            blocked = new boolean[H][W];
            visited = new boolean[H][W];

            for(int h=0; h<H; h++){

                String input = br.readLine();

                for(int w=0; w<W; w++){

                    char ch = input.charAt(w);

                    if(ch!='.'){
                        blocked[h][w] = true;
                        if(ch=='*'){
                            fire.add(new int[]{h,w});
                        }
                        else if(ch=='@'){
                            if(h==0 || h == H-1 || w==0 || w==W-1){
                                answer = 1;
                            }
                            blocked[h][w] = false;
                            visited[h][w] = true;
                            person.add(new int[]{h,w,1});
                        }

                    }

                }
            }

            while(!person.isEmpty() && answer==Integer.MAX_VALUE ){
                fireMove();
                move();
            }

            sb.append(answer==Integer.MAX_VALUE ? "IMPOSSIBLE" : answer).append("\n");

        }

        System.out.print(sb.toString());
    }


    static void move(){

        int count = person.size();

        while(count-->0){

            int[] rc = person.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(check(nr,nc) || visited[nr][nc]){
                    continue;
                }

                if(nr==0 || nr == H-1 || nc == 0 || nc == W-1){
                    answer = rc[2]+1;
                    return;
                }
                visited[nr][nc] = true;
                person.add(new int[] {nr,nc,rc[2]+1});
            }

        }

    }

    static void fireMove(){

        int count = fire.size();

        while(count-->0){

            int[] rc = fire.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(check(nr,nc)){
                    continue;
                }
                blocked[nr][nc] = true;
                fire.add(new int[] {nr,nc});
            }
        }

    }

    static boolean check(int nr, int nc){
        if(nr <0 || nc <0 || nr >= H || nc >= W || blocked[nr][nc]) return true;
        return false;
    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제는 그래프이론 문제입니다.
 * 여러가지 풀이방법이 있겠지만 쉽게 떠올릴 수 있는 방법을 토대로 풀이하였고 로직은 다음과 같습니다.
 * 1. 문제 조건에 맞춰 불을 먼저 움직여준다, 불이 움직인 자리는 벽이 있는것과 마찬가지로 판정하게 해준다.
 * 2. 다음으로 상근이를 움직여준다. 상근이는 벽이 있는 곳으로 움직이지 못한다.
 * 3. 1,2를 반복해서 벽은 계속해서 늘어나고, 상근이는 계속해서 움직이게 만들었을 때 상근이가 외곽(탈출지역)에 최초로 도달했을 때 시간을 리턴한다.
 *
 * 로직은 위와 같이 단순합니다. 여기서 가장 먼저 상근이의 초기 위치가 외곽일 경우에는 굳이 BFS를 할 필요가 없으므로 수행시간을 조금 줄일 수 있습니다.
 * 위의 로직을 토대로 BFS 수행 코드를 작성하면 풀이할 수 있습니다.
 *
 *
 * */