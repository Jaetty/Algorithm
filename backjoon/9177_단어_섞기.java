import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static char[] left, right, combine;
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int t=1; t<=N; t++){

            StringTokenizer st = new StringTokenizer(br.readLine());
            left = st.nextToken().toCharArray();
            right = st.nextToken().toCharArray();
            combine = st.nextToken().toCharArray();
            visited = new boolean[left.length+1][right.length+1];

            bfs();

            sb.append("Data set ").append(t).append(": ");
            if (visited[left.length][right.length]) sb.append("yes\n");
            else sb.append("no\n");

        }

        System.out.print(sb.toString());

    }

    static void bfs(){

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0,0});

        while (!queue.isEmpty()){

            int[] xy = queue.poll();
            int index = xy[0]+xy[1];

            if(xy[0]== left.length && xy[1]== right.length){
                return;
            }

            if(xy[0] < left.length && left[xy[0]] == combine[index]) {
                if(!visited[xy[0]+1][xy[1]]){
                    visited[xy[0]+1][xy[1]] = true;
                    queue.add(new int[]{xy[0]+1, xy[1]});
                }
            }

            if(xy[1] < right.length && right[xy[1]] == combine[index]) {
                if(!visited[xy[0]][xy[1]+1]){
                    visited[xy[0]][xy[1]+1] = true;
                    queue.add(new int[]{xy[0], xy[1]+1});
                }
            }
        }
    }
}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 3번째 입력 값의 길이는 1번째 입력과 2번째 입력의 길이의 합이다.
 * 2. 1번과 2번은 각각 원래의 순서를 유지해야한다.
 *
 * 문제의 풀이는 각각 1번과 2번의 index 값으로 BFS를 적용하면 풀이할 수 있습니다.
 *
 * 예시로 tea tae tteaea 라는 값이 있다고 하겠습니다.
 * 여기서 1번째 입력을 left, 2번째 입력을 right, 3번째 입력은 combine 이라고 하겠습니다.
 *
 * left의 index 포인터를 x, right의 index 포인터를 y라고 하겠습니다.
 * 최초에는 각각 x = 0, y = 0으로 각자 left[x] == t, right[y] == t 를 나타내고 있습니다.
 *
 * x와 y를 가지고 각각의 문자가 combine[x+y]의 위치 문자와 같은지 비교합니다.
 * left[x]와 같다면 x++한 값을 queue에 넣고, right[y]와 같다면 y++한 값을 queue에 넣어줍니다.
 *
 * bfs 결과 x와 y값이 각각 left의 크기와 right의 크기와 같아지면 끝까지 도달한 것이므로 yes 값을 아니면 y 값을 출력하면 문제를 해결할 수 있습니다.
 *
 * */