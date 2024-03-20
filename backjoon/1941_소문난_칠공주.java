import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static char[][] input;
    static boolean[][] visited;
    static int answer = 0;
    static int[] dr = {1,-1,0,0};
    static int[] dc = {0,0,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        input = new char[5][5];
        visited = new boolean[5][5];

        for(int i=0; i<5; i++){
            input[i] = br.readLine().toCharArray();
        }

        backTrack(0,-1,0);

        System.out.println(answer);

    }

    static void backTrack(int depth, int rc, int count){

        if(depth==7){
            if(count>=4 && bfs(rc/5,rc%5)) answer++;
            return;
        }

        while (rc < 24){

            rc++;
            visited[rc/5][rc%5] = true;
            if(input[rc/5][rc%5]=='S') backTrack(depth+1,rc,count+1);
            else backTrack(depth+1,rc,count);
            visited[rc/5][rc%5] = false;

        }

    }

    static boolean bfs(int r, int c){

        boolean[][] path = new boolean[5][5];
        path[r][c] = true;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r, c});
        int count = 1;

        while(!queue.isEmpty()){

            int[]rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dr[i];
                int nc = rc[1] + dc[i];

                if(nr < 0 || nc < 0 || nr >= 5 || nc >= 5 || !visited[nr][nc] || path[nr][nc]) continue;

                path[nr][nc] = true;
                count++;
                queue.add(new int[]{nr, nc});

            }

        }

        if(count==7) return true;
        else return false;
    }

}

/*
* 백트래킹 + 그래프 이론 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 백트래킹(혹은 브루트포스)로 5X5격자에서 7개를 선택하는 경우를 만듭니다.
* 2. 7개가 선택되었다면 그 안에 S가 4개 이상 존재하는지 확인합니다.
* 3. 2번까지 충족되면 BFS로 모든 칸들이 이어져있는지 확인해줍니다.
*
* 먼저 저는 백트래킹에 들어온 위치에서부터 24까지 카운트를 올리며 재귀를 실행하게 했습니다.
* (포인트는 c의 크기만 키워서 c가 5이상이면 c를 0으로 만들고 r의 크기를 하나 키우게 만들면 됩니다.)
*
* 이후 7가지가 선택되면 S가 4개 이상인지 확인해주고
* 위의 모든것이 만족되면 마지막 위치에서 BFS를 수행해줍니다.
*
* 어느 위치에서건 BFS를 실행했을 때 7개의 선택된 노드를 모두 방문할 수 있어야 합니다.
* 그래서 BFS로 다른 노드를 들릴 때 마다 count를 하나씩 올려줘서 7개의 방문이 되었는지 확인합니다.
* 7개의 방문이 완전하다면 정답 count를 하나 올려주면 해결할 수 있습니다.
*
*
* */