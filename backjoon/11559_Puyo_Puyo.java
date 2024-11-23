import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int answer;
    static char[][] map = new char[12][6];
    static boolean[][] visited;
    static boolean flag;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        answer = 0;

        for(int i=0; i<12; i++){
            map[i] = br.readLine().toCharArray();
        }

        flag = true;

        while(flag){

            flag = false;

            for(int i=11; i>=0; i--){
                for(int j=0; j<6; j++){
                    if(map[i][j]!='.'){
                        erase(i,j);
                    }
                }
            }

            if(flag){
                answer++;
                down();
            }

        }

        System.out.print(answer);
    }

    static void erase(int r, int c){

        char find = map[r][c];
        visited = new boolean[12][6];
        visited[r][c] = true;
        int count = 1;

        Queue<int[]> queue = new ArrayDeque<>();
        List<int[]> list = new ArrayList<>();
        list.add(new int[]{r,c});
        queue.add(new int[]{r,c});

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){

                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(check(nr,nc) || map[nr][nc]!=find){
                    continue;
                }

                visited[nr][nc] = true;
                queue.add(new int[]{nr,nc});
                list.add(new int[]{nr,nc});
                count++;

            }

        }

        if(count>=4){
            flag = true;
            for(int[] rc : list){
                map[rc[0]][rc[1]] = '.';
            }
        }


    }

    static void down(){

        for(int c=0; c<6; c++){

            int diff = 0;

            for(int i=11; i>=0; i--){

                if(map[i][c]=='.') diff++;
                else if(diff>0){
                    map[i+diff][c] = map[i][c];
                    map[i][c] = '.';
                }
            }

        }

    }


    static boolean check(int nr, int nc){
        if(nr <0 || nc <0 || nr >= 12 || nc >= 6 || visited[nr][nc]) return true;
        return false;
    }

}

/*
 * 그래프 이론 + 구현 문제
 *
 * 해당 문제는 주요 로직은 다음과 같습니다.
 * 1. BFS를 통해 같은 색깔의 뿌요가 4개 이상 인접하고 있는지 확인한다.
 * 2. 만약 뿌요가 소멸했을 시 소멸한 위치보다 위의 뿌요들은 중력에 의해 밑으로 내려오게 만든다.
 *
 * 문제의 경우 크기가 Row와 Col의 크기가 크다면 어려움을 겪겠지만 고정적으로 12와 6으로 정해져있습니다.
 * 이 경우 시간 초과의 염려를 전혀 고려하지 않아도 되는 정도의 크기이므로 구현에만 집중하면 됩니다.
 *
 * 다만 주의할 점은 정답은 연쇄가 몇번 일어나는지 묻고 있고, 또 초기에 주어진 형태가 게임에서는 있을 수 없는 형태일 수 있습니다.
 * 즉 중간에 진작에 없어져야할 뿌요들의 집합이 있음에도 없어지지 않았을 경우가 있습니다.
 * 그러니까 BFS를 할 때 아래 부분이나 특정 부분만 탐색을 진행하는게 아니라 모든 위치마다 BFS를 수행해줘야합니다.
 * 그리고 연쇄는 결국 이런 BFS를 다 수행하고 나서 카운트해줘야합니다. 뿌요 소멸마다 카운트를 높여서는 안됩니다.
 *
 * 이 점만 주의해서 BFS와 중력 부분을 구현하면 풀이할 수 있습니다.
 *
 *
 *
 * */