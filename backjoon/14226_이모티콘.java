import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int S;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        S = Integer.parseInt(br.readLine());
        bfs();

    }

    static void bfs(){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{1,0,0}); // 시작점, 클립보드, 시간
        boolean[][][] visited = new boolean[1001][1001][3];

        while(!queue.isEmpty()){

            int[] node = queue.poll();

            if(node[0]==S){
                System.out.println(node[2]);
                return;
            }

            if(node[0]>0 && !visited[node[0]][node[0]-1][0]){
                visited[node[0]][node[0]-1][0] = true;
                queue.add(new int[]{node[0]-1, node[1], node[2]+1});
            }
            if(node[0] != node[1] && !visited[node[0]][node[0]][1]){
                visited[node[0]][node[0]][1] = true;
                queue.add(new int[]{node[0], node[0], node[2]+1});
            }
           if(node[0]>0 && !(node[0]>1000 || node[1]+node[0] > 1000) && !visited[node[0]][node[1]+node[0]][2]){

                visited[node[0]][node[1]+node[0]][2] = true;
                queue.add(new int[]{node[0] + node[1], node[1], node[2]+1});
            }
        }

    }
}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 경우 전형적인 BFS 문제입니다만 visited의 설정이 포인트 입니다.
 *
 * 우선 상태는 3가지가 있습니다.
 * 1. 현위치 -1
 * 2. 현위치까지의 길이를 클립보드에 복사하기
 * 3. 현위치 + 클립보드 붙여넣기
 *
 * 여기서 클립보드에 복사와 붙여넣기는 따로 작동합니다. 즉 어떤 y라는 길이의 내용을 클립보드에 복사한 후, 위치는 계속 움직일 수 있습니다.
 * 이럴 경우 하나의 visited만으로 정확한 값을 알지 못하고, 그렇다고 모두 허용하면 메모리적으로나 시간적으로 초과하게 됩니다.
 * 그러므로 각 상태마다 visited를 기억하게 만들어주는 것이 핵심입니다.
 * 즉 visited[현위치][다음위치][상태]를 통해서 혹시 어떤 x위치에서 다음 위치 y로 z라는 상태를 통해 갔던 적이 있는지 확인하는 방법을 통해 가지치기 및 풀이가 가능합니다.
 *
 * */