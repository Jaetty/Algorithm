import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N,K;
    static Integer[] goal;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        /*

        동생은 계속해서 이동하는 중임.
        형은 N에서 시작함. 동생이 더 앞에 있을 수도, 더 뒤에 있을 수도 있음.

        가정) 이런 경우를 생각해보자.
        1. BFS를 하게 되면 항상 그 위치에 최대한 빨리 도착하는 경우가 됨.
        그럼 동생을 따라잡아야하는 경우도 있지만, 동생을 기다려야하는 경우도 있을거임.
        동생이 3초 대에 x위치에 도착하는데, 형이 x위치에 만약 1초에 도착했다면?? 2초를 그 주변에서 어슬렁 거리기만 하면 됨.
        반대로 동생이 2초에 도착하는데 형이 1초에 도착한다면? 그 주변에서 시간을 때울 수 없음. 그렇다면 서로 못만남.
        즉 홀짝임. 같은 홀수나 같은 짝수면 서로 만날 수 있음. 그러나 서로 다르면 못만남.

        2. 동생이 이미 지나간 곳에 뒤늦게 형이 도착했다면? 음.. 고려할 가치가 없음.
           그러면 동생의 시간 >= 형의 시간 이 성립해야함.

        */

        int time = 0;
        int start = K;
        goal = new Integer[500000+1];

        while(start <= 500000){

            goal[start] = time;
            time++;
            start+=time;

        }

        int answer = bfs();

        System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);

    }

    static int bfs() {

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{N,0});
        // 홀짝, 5000001로 설정 홀짝을 설정하는 이유는 홀짝마다 최소 도착시간이 다를 수 있음
        boolean[][] visited = new boolean[2][500001];
        visited[0][N] = true;

        int min = Integer.MAX_VALUE;

        while(!queue.isEmpty()){

            int[] cur = queue.poll();
            int isOdd = cur[1]%2;

            if(goal[cur[0]] != null){
                // 서로 홀짝이 같다면?
                if(goal[cur[0]]%2 == cur[1]%2){

                    if(goal[cur[0]] >= cur[1]){
                        min = Math.min(min,goal[cur[0]]);
                    }

                }
            }

            // 뒤로 갈 때
            if(cur[0] >= 1 && !visited[isOdd][cur[0]-1]){
                visited[isOdd][cur[0]-1] = true;
                queue.add(new int[]{cur[0]-1,cur[1]+1});
            }
            // 앞으로 갈 때
            if(cur[0] < 500000 && !visited[isOdd][cur[0]+1]){
                visited[isOdd][cur[0]+1] = true;
                queue.add(new int[]{cur[0]+1,cur[1]+1});
            }
            // 나누기 2
            if(cur[0]*2 <= 500000 && !visited[isOdd][cur[0]*2]){
                visited[isOdd][cur[0]*2] = true;
                queue.add(new int[]{cur[0]*2,cur[1]+1});
            }

        }

        return min;

    }

}

/*

17071 숨바꼭질 5

그래프 탐색 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. 동생을 먼저 움직여서 동생이 X위치에 몇초에 도달하는지 기록한다.
2. 형의 경우 N위치에서 BFS로 탐색한다.
3. 형의 방문 처리는 visited[홀/짝 시간][0~500000]으로 처리한다.
4. 만약 형이 동생이 도달했던 위치에 도착했을 때, 동생의 도착 시간이 내 도착 시간 이상이며,
    홀짝의 여부를 확인하여 가장 최솟 값을 갱신해준다.

문제의 핵심 포인트는 결국 홀짝 비교입니다.
만약 동생이 X라는 위치에 5초만에, 형이 X라는 위치에 3초만에 도착했다고 가정해보겠습니다.
형은 두 가지 선택이 있습니다. 더 앞쪽(X-1)으로 가보거나 그 자리를 유지하거나.
그 자리를 유지하는 방법은 단순합니다. 양옆 중 한곳을 왔다갔다 반복하면 됩니다.

하지만 왔다갔다 하는 동안 동생이랑 엇갈릴 수도 있습니다. 이를 확인하는 방법이 홀짝 비교입니다.
현재 형이 3초에 X위치에 도착했다면, X+1(혹은 X-1) 위치에 갔다가 다시 X위치로 돌아가면 5초에 X위치에 도착할 수 있습니다.
그런데 만약 동생이 X라는 위치에 4초에 도착한다면? 형이 X+-1위치로 가는 순간 4초가 되고 서로 엇갈리게 됩니다.
즉, 서로의 도착 시간의 홀짝이 같다면, 둘은 만날 수 있게 됩니다.

그렇다면, BFS를 통해 형을 움직이며 어떤 위치 Y에 홀짝 시간마다 가장 최소 도착 시간을 기록합니다.
만약 Y위치에 동생이 도착했거나 도착할 예정이라면 그 시간과 홀짝이 맞다면, 동생의 도착 시간이 곧 동생을 잡을 수 있는 시간입니다.
동생을 잡는 시간 중 가장 최솟값을 찾아내게 되면 이 문제를 해결할 수 있습니다.


*/