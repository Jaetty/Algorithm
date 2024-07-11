import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, total, answer;
    static int[][] map;
    static int[] sector;
    static int[] dx = {1,-1,0,0};
    static int[] dy = {0,0,1,-1};
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        answer = Integer.MAX_VALUE;

        for(int i=0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                total += map[i][j];
            }
        }

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                sectorCheck(i,j);
            }
        }

        System.out.println(answer);

    }

    static void sectorCheck(int x, int y){

        int[][] start = {{0,0}, {0,N-1}, {N-1,0}, {N-1,N-1}};

        // 섹터가 안만들어질 때 까지 계속 반복함.

        for(int d1=0; d1<=N; d1++){
            for(int d2=0; d2<=N; d2++){

                if(x+d1 < N && x+d2 <N && x+d1+d2 < N && y+d1 < N && y+d2 < N && y-d1 > 0 && y+d2 < N ){

                    visited = new boolean[N][N];
                    sector = new int[5];

                    setSector(x,y,d1,d2);
                    int sum = 0;
                    for(int i=0; i<4; i++){
                        bfs(i, start[i], x, y, d1, d2);
                        sum += sector[i];
                    }

                    sector[4] = total - sum;

                    Arrays.sort(sector);
                    answer = Math.min(answer, sector[4] - sector[0]);
                }
                else break;
            }
        }

    }

    static void setSector(int x, int y, int d1, int d2){

        for(int i=0; i<=d1; i++){
            visited[x+i][y-i] = true;
            visited[x+d2+i][y+d2-i] = true;
        }

        for(int i=0; i<=d2; i++){
            visited[x+i][y+i] = true;
            visited[x+d1+i][y-d1+i] = true;
        }

    }

    static void bfs(int number, int[] start, int x, int y, int d1, int d2){

        visited[start[0]][start[1]] = true;

        int sum = map[start[0]][start[1]];

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(start);

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(check(nr,nc, x, y, d1, d2, number)){
                    continue;
                }

                visited[nr][nc] = true;
                sum += map[nr][nc];
                queue.add(new int[] {nr,nc});

            }
        }

        sector[number] = sum;
    }

    static boolean check(int nr, int nc, int x, int y, int d1, int d2, int number){

        if(nr < 0 || nc <0 || nr >= N || nc >= N || visited[nr][nc]){
            return true;
        }

        switch(number){
            case 0:
                if(nr < 0 || nr >= x+d1 || nc < 0 || nc > y){
                    return true;
                }
                break;
            case 1:
                if(nr < 0 || nr > x+d2 || nc <= y || nc >= N){
                    return true;
                }
                break;
            case 2:
                if(nr < x+d1 || nr >= N || nc < 0 || nc >= y-d1+d2){
                    return true;
                }
                break;
            case 3:
                if(nr <= x+d2 || nr >= N || nc < y-d1+d2 || nc >= N){
                    return true;
                }
                break;
        }

        return false;
    }

}

/*
 * 그래프 이론 + 구현 + 브루트포스 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. N은 최대 20이다.
 * 2. 경계선과 경계선 안에 포함된 곳은 5번 선거구이다.
 * 3. 입력이 주어지는 것이 아닌 특정 기준을 정한 후 경계 길이 d1과 d2도 정해서 경계를 직접 만들어야한다.
 *
 * 1번과 3번을 통해 알 수 있는 점은 해당 문제는 브루트포스로 풀이해야한다는 점 입니다.
 * 2번 포인트로 알 수 있는점은 어차피 경계선은 침범할 일이 없으니 5번 구역의 값은 = 모든 인구 수 - 모든 경계선 밖 선거구 인구 수와 같습니다.
 *
 * 경계선의 경우 반복문을 이용하여 문제에 제시된 바와 같이 구현하면 됩니다.
 * 경계선을 세팅한 후 이 경계를 침범하지 않도록 조건문을 짜주고 bfs를 수행하여 각 선거구의 인구수를 종합합니다.
 * 모든 선거구의 총합 인구수를 구했다면 가장 큰 인구수 - 가장 작은 인구수를 구합니다.
 * 이것을 모든 경우의 수에서 구해서 그 중 가장 작은 값을 구하면 풀이할 수 있어서 해당 문제는 조건 만들기와 반복문을 잘 활용하여 코드를 짜면 풀이할 수 있습니다.
 *
 * */