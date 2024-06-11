import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */


public class Main {

    static int R, C, turn;
    static int[] dx = {0,1,-1,0};
    static int[] dy = {1,0,0,-1};
    static char[][] map;
    static Queue<int[]> queueForCluster;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        queueForCluster = new ArrayDeque<>();

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];

        for(int i=0; i<R; i++){
            String var = br.readLine();
            for(int j=0; j<C; j++){
                map[i][j] = var.charAt(j);
            }
        }

        br.readLine();
        st = new StringTokenizer(br.readLine());
        turn = 0;

        while(st.hasMoreTokens()){

            String temp = st.nextToken();
            int row = 0;
            if(temp.charAt(0)!='R'){
                row = R - Integer.parseInt(temp);
            }

            eliminate(row);
            if(!queueForCluster.isEmpty()) findCluster();

            turn++;
        }

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<R; i++){
            for(int j=0; j<C; j++){
                sb.append(map[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());

    }

    static void eliminate(int row){

        queueForCluster.clear();

        int col = turn%2==0 ? 0 : C-1;

        while(col >= 0 && col < C){

            if (map[row][col] != '.'){

                map[row][col] = '.';

                int count = 0;

                while(count<3){
                    queueForCluster.add(new int[]{ row + dx[ turn%2 + count], col + dy[turn%2 + count]});
                    count++;
                }
                return;
            }

            if(turn%2==0) col++;
            else col--;

        }
    }

    static void findCluster(){

        boolean[][] visited = new boolean[R][C];

        while(!queueForCluster.isEmpty()){

            int[] location = queueForCluster.poll();

            if(location[0] >= R || location[0] < 0 || location[1] >= C || location[1] < 0 || map[location[0]][location[1]] == '.' || visited[location[0]][location[1]]) continue;

            Queue<int[]> queue = new ArrayDeque();
            List<int[]> list = new ArrayList<>();

            list.add(location);
            visited[location[0]][location[1]] = true;

            boolean grounded = false;
            queue.add(location);

            while(!queue.isEmpty()){

                int[] rc = queue.poll();

                for(int i=0; i<4; i++){

                    int nr = rc[0] + dx[i];
                    int nc = rc[1] + dy[i];

                    if(nr < 0 || nr >= R || nc >= C || nc < 0 || visited[nr][nc] || map[nr][nc]=='.') continue;

                    if(nr == R-1) grounded = true;
                    visited[nr][nc] = true;

                    list.add(new int[]{nr,nc});
                    queue.add(new int[]{nr, nc});

                }

            }

            if(!grounded){
                takeGravity(list);
                return;
            }

        }

    }

    static void takeGravity(List<int[]> list){

        Collections.sort(list, (e1,e2)->{
            if(e1[0] == e2[0]){
                return e2[1] - e1[1];
            }
            else return e2[0] - e1[0];
        });

        int height = R-list.get(0)[0];

        for(int i=0; i< list.size(); i++){

            int rc[] = list.get(i);
            map[rc[0]][rc[1]] = '.';

            if(map[rc[0]+1][rc[1]]=='.' && heightCheck(rc[0], rc[1]) < height){
                height = heightCheck(rc[0], rc[1]);
            };
        }

        for(int[] var : list){
            map[var[0]+height][var[1]] =  'x';
        }

    }

    static int heightCheck(int r, int c){

        int result = 0;

        while (r < R){

            if (map[r][c]!='.'){
                break;
            }
            r++;
            result++;
        }

        return result-1;
    }

}

/*
 * 그래프 이론 + 구현 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 턴마다 특정 행 위치에서 미네랄을 캐는데 좌->우, 우->좌 순서로 번갈아가면서 미네랄을 캔다.
 * 2. 미네랄이 2개 이상 이어져 있으면 그것을 클러스터라고 한다.
 * 3. 미네랄은 혼자 공중에 떠있을 수 없다. 반드시 맨 밑 부분에서부터 미네랄끼리 이어져야 위에 올릴 수 있다.
 * 4. 클러스터가 분리되어 지지대 없이 혼자 공중에 떠있는 상태가 된다면 중력을 받아 클러스터의 모양 그대로 아래로 떨어진다.
 *     이때 다른 미네랄이나 맨 밑까지 가장 먼저 도착하는 경우가 반드시 있다.
 * 5. 클러스터가 분리될 때 2개 이상 바닥으로 떨어질 경우는 주어지지 않는다.
 *
 * 포인트에 맞춰서 우선 크게 3가지 단계로 문제 해결을 해야겠다고 생각했습니다.
 * 1. 미네랄 캐기 작업 (홀수 일 때 좌->우, 짝수일 때 우<-좌) -> 미네랄을 찾게 되면 분리 가능성이 있는 주변 미네랄 기억해서 2번 수행
 * 2. 클러스터 분리 확인 작업 -> 분리 되었고 공중에도 떠있다면 3번 작업 수행
 * 3. 클러스터를 바닥으로 내리는 작업
 *  3-1. 가장 클러스터가 먼저 바닥이나 다른 미네랄과 만나는 거리 찾기
 *
 * 1번의 경우 쉽게 고정된 행에서 열을 탐색하면서 미네랄이 있는지 확인해주면 됩니다.
 * 이 기능은 입력이 R과 C이고 100이 최대값이므로 최대 O(C) = 100번 반복의 시간이 소요됩니다.
 *
 * 여기서 클러스터가 분리될 가능성이 있는 미네랄들을 기억해두게 만들어줍니다.
 * 예를 들어 다음과 같은 값이 있다면
 * .xxx.
 * ..xx.
 * .xxx.
 *
 * 가운데 미네날이 하나만 사라지면 클러스터는 유지됩니다. 하지만 모두 사라지면 클러스터는 위의 하나, 아래의 하나로 분리됩니다.
 * 이걸 구분하는 방법으로 bfs를 이용하였습니다. 사라진 미네랄 위치에서 각각 상,하,(좌/우)에 위치한 미네랄 위치에서 각각 bfs를 수행하면 하나의 클러스터(모두 이어져있는지)인지 확인 가능합니다.
 *
 * 여기서 좌->우로 갈 때는 클러스터가 분리되면 각각 나의 위, 아래, 오른쪽에 미네랄이 있는지 확인합니다. (왼쪽은 이미 지나온 길이니 어차피 없으니까)
 * 반대로 우->좌로 갈 때는 위, 아래, 왼쪽에 미네날이 있는지 확인합니다.
 * 이 위치들을 각각 큐에 넣어주고 2번을 수행합니다.
 *
 * 2번의 경우
 * bfs를 수행시켜주는 부분입니다. bfs로 1번에서 받아온 큐의 값의 클러스터들이 각각 얼마나 이어져있고 공중에 떠있는지 확인해줍니다.
 * 공중에 떠있는지 확인하는 작업은 클러스터를 구성하는 미네랄의 위치가 하나라도 가장 맨 밑(R-1)의 위치에 있으면 됩니다.
 * 이 경우 시간은 최대 O(R*C) 만큼 수행되니 최악의 경우에도 10000번의 시간만 있으면 됩니다.
 *
 * 여기서 포인트는 list에 그래프 탐색 중 알아낸 클러스터의 위치를 모두 기억한 후 공중에 떠있다면 이걸 기준으로 3번을 수행하면 됩니다.
 *
 * 3번의 경우
 * 여기서 작업은 클러스터가 가장 먼저 안착하는 위치와의 높이 차이(height)를 찾아서 원래 클러스터 위치에는 .을 표기하고 수정된 위치 (row+height)에 x를 표시해줍니다.
 * 이때 높이 차이를 구하는 방법은 자신의 위치에서 row값을 계속해서 늘려서 결국 맨 밑이나 다른 미네랄이 있는지 탐색하면 됩니다. 여기는 최대 O(R) 만큼 시간이 걸립니다.
 *
 * 저는 먼저 기존 위치를 .으로 만들면서 높이 차이를 구한 후 위치를 수정하였습니다. 성능은 list의 크기가 N이라면 O(N*R+N) 입니다.
 * 저는 가장 낮은 row값을 지닌 순서로 먼저 정렬해주었습니다. 그 이유는 기존 클러스터의 위치 x값을 .으로 바꿀 때 아래에서부터 순서대로 없애야 문제가 발생하지 않기 때문입니다.
 * 이후 각각 위치에서 O(R)만큼 탐색하여 가장 작은 값을 찾아서 기억해준 후
 * 다시 O(N)만큼 반복하며 새로운 클러스터의 위치에 X를 표시해줍니다.
 *
 * 위의 방법대로 크게 3단계로 문제 해결방법을 떠올렸고 이것을 코드로 나타내어 문제를 해결할 수 있었습니다.
 *
 * */