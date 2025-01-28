import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int R, C;
    static char[][] map;
    static int[][] dp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R+1][C];

        /*

        만약 입력이 다음과 같고,

        ....
        ...x
        xx..
        ....

        그러면 각 x 위치의 dp는 바로 내 위에 비어있는 위치를 가르키고, 각 맨 위의 .은 내 기준 가장 먼저 만나는 밑바닥을 가르킨다면??
        그리고, 각각 나머지 .들은 자기보다 맨 위 .의 위치를 나타내준다면?

        3번의 조회만으로 가장 밑바닥에서부터 가장 가까운 빈 공간을 찾아낼 수 있음.

        즉, dp[][] 배열을 만들어서 다음과 같이 나타나짐.

         2241
         000x
         xx04
         4403
        [xxxx] <= 가상의 맨밑바닥들


         2241
         0000
         0004
         4403
        [3302] <= 가상의 맨밑바닥들

        이러고나서 비었던 부분에 돌이 차면 그 밑바닥 부분의 인덱스의 값에 --해주기만 하면 됨

        */
        dp = new int[R+1][C];
        Stack<int[]> stack[] = new Stack[C];


        int[] first = new int[C];

        for(int c=0; c<C; c++){
            map[R][c] = 'X';
            stack[c] = new Stack<>();
            first[c] = R;
        }

        for(int i=0; i<=R; i++){

            if(i<R){
                map[i] = br.readLine().toCharArray();
            }

            for(int j=0; j<C; j++){

                if(i>0 && map[i][j]=='X' && map[i-1][j]=='.'){
                    dp[i][j] = i-1;
                    dp[first[j]][j] = i;
                    first[j] = R;
                }
                else{
                    first[j] = Integer.min(first[j], i);
                    dp[i][j] = first[j];
                }
            }


        }

        int N = Integer.parseInt(br.readLine());
        int nr = 0;

        while(N-->0){

            int fix = Integer.parseInt(br.readLine())-1;
            int[] rc = null;

            while(stack[fix].size()>0){
                rc = stack[fix].pop();
                if(map[rc[0]][rc[1]] == '.'){
                    break;
                }
                rc = null;
            }

            int row = dp[dp[0][fix]][fix]; // 가장 맨위가 가르키는 밑바닥에서부터 가장 가까운 빈 공간 찾기.
            int col = fix;

            if(rc != null){
                row = rc[0];
                col = rc[1];
            }

            while(true){

                if(row+1 < R && map[row+1][col] == 'O' ){

                    if(col-1 >= 0 && map[row][col-1] == '.' && map[row+1][col-1]=='.'){

                        stack[fix].push(new int[]{row, col});
                        col--;

                        nr = dp[row+1][col]; // 그 열에 가장 위에 비어 있는 노드
                        nr = dp[nr][col]; // 그 비어있는 노드에서 바닥의 위치
                        row = dp[nr][col]; // 바닥에서부터 가장 가까운 비어있는 곳

                        continue;
                    }

                    if(col+1 < C && map[row][col+1] == '.' && map[row+1][col+1]=='.'){

                        stack[fix].push(new int[]{row, col});
                        col++;

                        nr = dp[row+1][col]; // 그 열에 가장 위에 비어 있는 노드
                        nr = dp[nr][col]; // 그 비어있는 노드에서 바닥의 위치
                        row = dp[nr][col]; // 바닥에서부터 가장 가까운 비어있는 곳

                        continue;
                    }

                }

                map[row][col] = 'O';
                nr = dp[row][col];

                while(nr < row){
                    nr = dp[nr][col];
                }

                dp[nr][col]--;

                break;

            }
        }

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<R; i++){
            sb.append(new String(map[i])).append("\n");
        }

        System.out.print(sb.toString());


    }

}

/*
 * 3025 돌 던지기
 *
 * 구현 + 스택 문제
 *
 * 문제의 핵심은 다음과 같습니다.
 * 1. 돌을 굳이 다 굴려서 끝까지 내리는 것이 아닌 돌이 끝까지 내려갈 장소를 미리 기억해둔다.
 * 2. 돌이 왼쪽, 오른쪽으로 굴러 내려갈 수 있다면, 굴러가기 전 위치를 기억해둔다, 그 위치는 비어있다는 뜻이고 다음에 다시 꺼내서 바로 쓰면 된다.
 * 3. 만약 다른 위치에서 굴어온 돌이 2번에서 기억해둔 위치를 막았다면 가장 마지막으로 가능했던 위치를 꺼내주면 됨.
 * 4. 결국 O(R)만큼 걸리는 돌이 굴러가는 시간을 최대한 O(1)로 바꾸는게 핵심.
 *
 *
 * 해당 문제는 꼬박 하루가 걸렸는데... 풀기 위해서 했던 로직은 다음과 같았습니다.
 *
 * 1. 먼저 문제에 나온 그대로 구현했을 시 걸리는 시간을 예상해보았습니다.
 *
 *      돌이 내려갈 때 마다 최대 O(R)만큼 반복하므로 O(R*N)만큼의 반복이 발생하므로 매우 비효율적...
 *      그렇다면 O(R)의 과정에서 병목현상이 발생하므로 이 부분에 집중해서 최적화를 이뤄야겠구나 계획함.
 *
 * 2. 그러면 돌을 내리는 일일히 내리는 과정 O(R)을 없애버리면?
 *
 *      그렇다면 시간은 O(1*N)으로 매우 효율적 하지만 어떻게 이걸 구현해야할까?
 *      일일히 내리지 않으려면? -> 어떤 col의 맨 위에서 돌을 떨어트릴 때, 그 열에서 가장 밑바닥의 위치를 미리 기억하고 불러오기만 하면 됨.
 *      이거면 크게 문제가 없겠다 싶어서 이걸 바탕으로 우선 구현했습니다.
 *
 *      3번에 걸쳐서 시도했는데, Treeset으로 1번(메모리 초과), 배열로 2번 (시간 초과)이었습니다.
 *      여기서 TreeSet에 'X'바로 위의 '.'의 위치들만 기억하게 만들었는데 많아봐야 최대 15000 * 30개니까 괜찮을거라 생각했더니..
 *      TreeSet은 알고보니 Red-Black을 유지하기 위해 꽤나 메모리를 필요로 한다는걸 배웠습니다...
 *
 * 3. 그런데.. 막상 2번의 로직대로 구현했더니 실제론 O(1*N)이 아니라 경우에 따라 더 많은 반복이 발생하는걸 알았습니다.
 *
 *      문제에 보면, 만약 내가 내려가는 곳에 돌이 있다면 왼쪽 아래로, 오른쪽 아래로 혹은 그 돌 위에 두는 3가지 경우가 있습니다.
 *      그렇다면 만일 R과 C가 최대 값에, X의 위치가 돌이 굴러가기 편한 완전 지그재그 형태로 있다고 해보겠습니다.
 *      그러면 실제로는 돌을 맨 밑으로 내리는데 걸리는 시간은 돌이 한편으로 굴러갈 때 마다 O(1)이 됩니다. 이걸 15000번 반복했다고 하면, O(15000*N).
 *
 *      문제를 파악하자 어떻게 하면 최대한 내가 가장 멀리 갔던 곳을 기억할까? 로 좁혀졌습니다.
 *      즉, 처음 돌은 O(15000)을 통해 특정 위치에 도달했다고 합시다, 그러면 다음번에 또 O(15000)만큼 반복하지 말고, 그냥 O(1)만에 O(14999)번째 위치를 찾으면??
 *      이걸 위해 맨 처음 col 위치에서 시작한 돌이 갔던 경로를 기억하는 스택을 만들어두고, 스택에 맨 마지막을 꺼내서, 이게 유효하면 여기에 두고 아니면 pop()하면 되겠구나.
 *
 *
 * 사실 이 문제는 그림을 바탕으로 이해하는게 더 좋습니다..
 * 그래서 돌의 경로 부분을 비슷하게 해설한 사이트 주소들을 남깁니다...
 *
 * https://hillier.tistory.com/118
 * https://littlesam95.tistory.com/entry/%EB%B0%B1%EC%A4%80BOJ-3025-%EB%8F%8C-%EB%8D%98%EC%A7%80%EA%B8%B0Platinum-5
 *
 *
 * */