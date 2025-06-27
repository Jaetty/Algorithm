import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    // 위, 오른쪽, 오른쪽 아래, 아래, 왼쪽, 왼쪽 위
    static int[] dr = {-1, 0, 1, 1, 0, -1};
    static int[] dc = {0, 1, 1, 0, -1, -1};

    // moves는 얼마만큼 해당 방향으로 움직여야하는지 기억.
    static int[] moves = {1, 0, 1, 1, 1, 1};
    // count는 moves를 초기화하는데 사용. moves에서 0이 될때마다 +1 해준다.
    static int[] count = {1, 0, 1, 1, 1, 1};

    // 역추적 경로 확인 용
    static int[] path;

    static class Node {

        int id, r, c;
        Node[] next;

        public Node(int id, int r, int c) {
            this.id = id;
            this.r = r;
            this.c = c;
            this.next = new Node[6];
        }
    }

    static final int MAX = 1_000_000;
    static Node[] nodes;
    // 테스트해보니 최대 1500까지만 있으면 됐음.
    static Node[][] map = new Node[1500][1500];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        nodes = new Node[MAX+1];
        path = new int[MAX+1];
        setNode();
        setHive();
        bfs(start, end);
        dfs(end);

    }

    // 각 노드들의 초기화를 실행
    static void setNode(){

        // 초기 위치를 정중앙에서
        int r = 750, c = 750;
        // dr, dc의 방향을 나타냄.
        int dir = 0;

        for (int i = 1; i <= MAX; i++) {

            nodes[i] = new Node(i, r, c);
            map[r][c] = nodes[i];

            // 위, 오른쪽, 오른쪽 아래, 아래, 왼쪽, 왼쪽 위마다 정해진만큼만 움직여야함
            // 만약 정해진 만큼 다 움직였다면 count와 moves를 갱신해주고 다음 dir 값으로 변경.
            while(moves[dir] == 0){
                count[dir]++;
                moves[dir] = count[dir];
                dir = (dir+1)%6;
            }

            r = r + dr[dir];
            c = c + dc[dir];
            moves[dir]--;

        }
    }

    // 육각형으로 인접한 노드들을 서로 연결해주기
    static void setHive(){

        for(int idx=1; idx<=MAX; idx++){

            for(int i = 0; i < 6; i++){
                int nr = nodes[idx].r + dr[i];
                int nc = nodes[idx].c + dc[i];

                if(nr >= 1500 || nc >= 1500 || nr < 0 || nc < 0) continue;
                nodes[idx].next[i] = map[nr][nc];
            }
        }

    }

    // 그래프 탐색 수행
    static void bfs(int start, int end){

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(nodes[start]);

        path[start] = -1;

        while(!queue.isEmpty()){

            Node cur = queue.poll();

            for(int i = 0; i < 6; i++){
                Node next = cur.next[i];
                if(next != null && path[next.id] == 0){
                    path[next.id] = cur.id;
                    queue.add(next);
                    if(next.id == end){
                        return;
                    }
                }
            }
        }

    }

    // 역추적 수행
    static void dfs(int end){

        StringBuilder answer = new StringBuilder();

        int start = end;

        while(true){

            if(path[start] != -1){
                answer.insert(0, path[start]+" ");
                start = path[start];
                continue;
            }
            break;

        }
        answer.append(end);

        System.out.println(answer.toString());

    }

}

/*

1385 벌집

그래프 이론 + 구현 문제

해당 문제는 구현 문제이기 때문에 다양한 풀이가 존재할 것으로 예상됩니다.
그러므로 이 풀이에서는 저는 어떻게 이 문제를 풀이했는지로 로직을 설명하도록 하겠습니다.

먼저 저는 문제를 다음과 같이 접근하였습니다.
1. 적당히 큰 배열을 그려놓고 정중앙을 1로 했을 때 벌집의 이동경로를 직접 그려보았습니다.
2. 벌집은 위, 오른쪽, 오른쪽 아래, 아래, 왼쪽, 왼쪽 위 순서로 움직이며 각각 횟수에 맞게 이동하고 있었습니다.
예를 들어 1부터 시작하면 위로 1칸, 오른쪽으로 0칸, 오른쪽 아래 1칸, 아래 1칸, 왼쪽 1칸, 왼쪽 위 1칸.. 이게 반복되서
[1,0,1,1,1,1], [2,1,2,2,2,2], [3,2,3,3,3,3]로 규칙을 확인했고 이에 맞춰서 dr, dx, moves, count라는 변수를 만들었습니다.
3. 위 정보를 토대로 0,0 위치에서 시작해서 1,000,000번 반복하면 최대 {r, c}값이 얼마나 커지는지 테스트를 해보니 약 +-1500이 나왔습니다.

위와 같은 방법으로 풀이에 필요한 핵심 정보를 구한 이후, 다음과 같이 풀이를 수행했습니다.
1. id값, r위치, c위치 값을 가진 Node 클래스를 만든다.
2. Node[1500][1500]으로 (r,c)위치마다 node를 저장해둘 수 있는 배열을 만든다.
3. Node를 1부터 시작해서 1,000,000까지 반복하여 생성(id, r, c를 지정)해준다. O(1,000,000) = O(N)
4. Node를 1부터 시작해서 1,000,000까지 반복하여 인접 노드들과 서로 연결시켜준다. O(O(1,000,000) * 6) = O(6N)
5. start 위치부터 end 위치까지 BFS를 통해 탐색을 수행한다. 이때 역추적용 path 배열을 갱신하며 visited처럼 이용한다. O(N)
6. path배열을 통해 역추적으로 정답을 도출한다. 거의 O(N/2)이하로 예상함.

위와 같이 풀이하면 속도가 다음과 같습니다.
O(1,000,000) + O(1,000,000 * 6) + O(1,000,000) + O(대략 500,000이하) = O(N) 풀이 시간.
때문에, 크게 문제 없이 풀이가 될 것이라고 예측했습니다.

다만, 처음 문제를 풀이할 때, Node[1500][1500]이 아닌 HashMap<String, Node>로 진행했는데 시간초과와 메모리 초과를 겪었습니다.
그래서 Node[1500][1500]으로 바꾸니까 적절한 반응이 나와 제출했고, 이후 궁금해져서 HashMap<Integer, Node>와 Node[1500][1500]을 비교했습니다.
그러자 거의 시간과 공간에서 약 2배 정도의 차이를 겪었습니다.

해당 문제를 통해 Key, Value 없이 인덱스로만 가능하다면 최대한 배열을 이용하는 것이 더 빠르다는 점을 다시금 깨달을 수 있었습니다.

*/