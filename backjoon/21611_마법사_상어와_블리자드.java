import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;

    static class Node{

        Node prev, next;
        int idx, biz;
        public Node(int idx, int biz){
            this.idx = idx;
            this.biz = biz;
        }

    }

    static Node[][] nodes;
    static int[][] numbers;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static long[] blow_count;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        nodes = new Node[N][N];
        numbers = new int[N][N];
        blow_count = new long[4];

        for(int i = 0; i < N; i++){

            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){

                nodes[i][j] = new Node(0,Integer.parseInt(st.nextToken()));

            }

        }

        setting();

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken())-1;
            int s = Integer.parseInt(st.nextToken());

            // 파괴해야 하는 목록 가져오기
            int[] del = delete(d,s);
            // 파괴하기
            connect(del);

            // 터트릴 수 있을 때까지 계속 터트리기.
            while (blow()){
                continue;
            }

            // 그룹핑하기
            Queue<Node> queue = grouping();
            // 새로운 환경 세팅
            changeBiz(queue);

        }

        long answer = 0;
        for(int i = 1; i < 4; i++){
            answer += blow_count[i] * i;
        }

        System.out.println(answer);

    }

    // 맨 처음 중심부터, 회오리 모양으로 돌면서 링키드 리스트 세팅하기.
    static void setting(){

        int[] local_dx = {0, 1, 0, -1};
        int[] local_dy = {-1, 0, 1, 0};
        int[] move_limits = {1,1,2,2};

        int r = N/2, c = N/2, dir=0, count = 1, limit = 1;
        Node before = nodes[r][c];

        while(true){

            r += local_dx[dir];
            c += local_dy[dir];

            if(r >= N || c >= N || r < 0 || c < 0){
                break;
            }

            if(nodes[r][c].biz != 0){
                Node cur = nodes[r][c];

                // 배열에 순서 값과 현재 노드에 idx 값 설정
                cur.idx = count;

                // 노드의 앞 뒤 연결 설정
                cur.prev = before;
                before.next = cur;

                before = cur;
            }

            numbers[r][c] = count++;
            limit--;

            if(limit == 0){
                move_limits[dir] += 2; // 다음에는 해당 방향으로 2칸 더 갈 수 있음
                dir = (dir+1)%4; // 방향 바꿔줌
                limit = move_limits[dir]; // 최대 이동 가능 횟수 설정
            }


        }

    }

    // 삭제하는 값 구하기.
    static int[] delete(int d, int s){

        int[] res = new int[s];
        int r = N/2, c = N/2, idx=0;

        while(idx<s){

            r += dx[d];
            c += dy[d];

            if(r >= N || c >= N || r < 0 || c < 0){
                break;
            }

            res[idx++] = numbers[r][c];
        }

        return res;
    }

    // 삭제 후 서로 연결하기
    static void connect(int[] delete){

        // delete의 idx를 기억
        int d_idx = 0;

        // 한번이라도 삭제가 되었다면 그때부터 뒤에랑 앞이랑 연결해줘야함.
        boolean check = false;

        Node before = nodes[N/2][N/2];
        Node cur = before;

        while(cur.next != null){

            cur = cur.next;
            if(d_idx < delete.length && delete[d_idx] == cur.idx){
                check = true;

                // 전의 값을 다음 값과 연결
                before.next = cur.next;

                // 다음 값을 전의 값과 연결
                if(cur.next != null){
                    cur.next.prev = before;
                }

                // 연결 해제 (메모리를 위해)
                cur.next = null;
                cur.prev = null;

                // 다음 삭제 값 지목
                d_idx++;

                cur = before;
            }
            else if(check){
                cur.idx = before.idx+1;
            }
            before = cur;
        }

    }

    // 4개 이상이면 터지기.
    static boolean blow(){

        // 한번이라도 삭제가 되었다면 그때부터 뒤에랑 앞이랑 연결해줘야함.
        boolean result = false;

        Node start = nodes[N/2][N/2].next;
        Node cur = start;

        // 비어 있다면
        if(start == null){
            return false;
        }

        int count = 1, t_biz = start.biz;

        while(cur.next != null){

            cur = cur.next;

            if(cur.biz == t_biz){
                count++;
                continue;
            }

            // 전의 구슬 값과 같지 않을 때

            // count가 4개 이상이면 폭발 작업
            if( count >= 4){
                result = true;

                // 시작 위치 전의 노드와 현재 노드를 연결
                start.prev.next = cur;

                cur.prev.next = null;
                cur.prev = start.prev;

                start.prev = null;

                blow_count[t_biz] += count;
            }

            // 한번이라도 폭발이 일어나면 순서가 변하므로, node의 순서 값 수정
            if(result){
                cur.idx = cur.prev.idx+1;
            }

            // 시작 위치, 구슬 값을 새로 세팅
            start = cur;
            t_biz = cur.biz;
            count = 1;

        }

        // 맨 마지막에 도달했을 때 count가 4 이상이라면
        if(count >= 4){
            result = true;
            start.prev.next = null;
            start.prev = null;

            blow_count[t_biz] += count;
        }

        return result;
    }

    // 순회하면서 그룹화 하기
    static Queue<Node> grouping(){

        Queue<Node> queue = new LinkedList<>();

        Node cur = nodes[N/2][N/2].next;

        // 아예 구슬들이 다 파괴되어 비어있을 때
        if(cur == null){
            return queue;
        }

        int count = 1, t_biz = cur.biz;

        while(cur.next != null){

            cur = cur.next;

            if(cur.biz == t_biz){
                count++;
                continue;
            }

            queue.add(new Node(0, count));
            queue.add(new Node(0, t_biz));

            t_biz = cur.biz;
            count = 1;

        }

        queue.add(new Node(0, count));
        queue.add(new Node(0, t_biz));

        return queue;
    }

    // 그룹을 변환하기
    static void changeBiz(Queue<Node> queue){

        int idx = 1;
        Node before = nodes[N/2][N/2];
        before.next = null;

        while(!queue.isEmpty() && idx < N*N){

            Node cur = queue.poll();

            before.next = cur;
            cur.prev = before;

            cur.idx = before.idx+1;
            idx++;

            before = cur;

        }

    }

}

/*

21611 마법사 상어와 블리자드

구현 문제

해당 문제는 구현이기 때문에 문제에 나온대로 작동하게끔 만들기만 하면 됩니다.
때문에, 문제 자체의 해설보다는 어떻게 구현했는가에 대해 설명하겠습니다.

저의 풀이 방법의 핵심은 다음과 같습니다.
1. 파괴된 값을 서로 이어주는 부분이 있기 때문에 그래프 형식보단 링키드 리스트를 적용함.
2. 중앙부터 시작한 회오리 모양의 순서를 링키드 리스트 값의 인덱스처럼 활용.

해당 문제는 크게 다음의 4가지를 구현하라고 지시하고 있습니다.
1. 입력 값 세팅하기
2. 특정 방향(d)과 갯수(s)의 구슬을 파괴하고 남은 구슬은 한칸씩 당겨주기
3. 같은 종류의 구슬이 4개 이상 이어지면 터트리기 (터지지 않는 경우까지 반복)
4. 남은 구슬들을 갯수와 종류를 바탕으로 2개의 구슬로 바꾸는 과정으로 배열을 초기화하기

아마 위의 구현 목록만 보면 이해가 어렵지는 않을 것 입니다.
특히, 1번과 4번의 경우 어떤 자료구조를 써서 구현하더라도 쉬운 구현이기 때문에 설명을 생략하겠습니다.
다만, 제가 생각하기에 가장 어려움을 느낄 것 같은 부분은 2번과 3번이라고 생각합니다.

왜냐하면 2번과 3번을 비효율적으로 수행한다면 시간초과를 겪을 수 있습니다.
이는 그래프로도 충분히 구현할 수 있겠지만, 저는 어차피 N칸을 땡기는 작업을 수행한다면 링키드 리스트가 편하다고 느껴졌습니다.

그래서 한 방법은, 구슬 파괴의 경우는 파괴할 구슬의 인덱스들만 기억하게 만들어줍니다.
그리고 링키드 리스트를 순회하면서 파괴해야하는 인덱스의 노드를 삭제하고 서로 이어주는 작업을 해주었습니다.

이와 비슷하게, 구슬 폭발 현상도 링키드 리스트를 순회하면서 같은 종류의 구슬이 4개 이상 이어지는지 확인합니다.
조건을 만족하면 시작 지점과 끝 지점을 리스트에서 삭제만 해주면 쉽게 당기는 작업을 완수할 수 있었습니다.

*/