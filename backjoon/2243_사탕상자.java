import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static final int MAX = 1_000_000;

    public static void main(String[] args) throws Exception {

        SegmentTree segmentTree = new SegmentTree(MAX);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int tc=0; tc<N; tc++){

            StringTokenizer st = new StringTokenizer(br.readLine());

            if(Integer.parseInt(st.nextToken())==1){ // 빼낼 때
                sb.append(segmentTree.search(1,1,MAX,Integer.parseInt(st.nextToken()),1)+"\n");
            }
            else{ // 사탕을 넣을 때
                int idx = Integer.parseInt(st.nextToken());
                int diff = Integer.parseInt(st.nextToken());
                segmentTree.update(1, 1, MAX, idx, diff);

            }
        }

        System.out.print(sb.toString());

    }

    static class SegmentTree{
        long[] tree;
        int treeSize;

        public SegmentTree(int arrSize){
            int h = (int) Math.ceil(Math.log(arrSize)/ Math.log(2));

            this.treeSize = (int) Math.pow(2,h+1);
            tree = new long[treeSize];
        }

        public void update(int node, int start, int end, int idx, long diff){
            if(idx < start || end < idx) return;

            tree[node] += diff;

            if(start != end){
                update(node*2, start, (start+end)/2, idx, diff);
                update(node*2+1, (start+end)/2+1, end, idx, diff);
            }
        }

        public int search(int node, int start, int end, long rank, int idx){

            tree[node]--;

            if(start==end){
                return idx;
            }

            if(rank <= tree[node*2]){
                return search(node*2, start, (start+end)/2, rank, idx);
            }else{
                return search(node*2+1, ((start+end)/2+1), end, rank-tree[node*2], (start+end)/2+1);
            }
        }


    }

}

/*
* 세그먼트 트리 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 사탕의 맛의 좋고 나쁨이 1부터 1,000,000까지의 정수로 구분된다.
* 2. 사탕을 꺼낼 때는 꺼낼 순위로 주워진다.
* 3. 사탕을 넣는 경우 사탕의 맛과 몇 개를 넣는지(음수도 있음) 주어진다.
* 4. 없는 사탕을 꺼내는 경우와 같은 잘못된 입력은 주어지지 않는다.
*
* 해당 문제의 경우 입력이 최대 100,000번 주어지므로 맛의 범위를 함께 고려하면 단순 배열에서 찾는 걸로는 시간 초과를 벗어날 수 없다는 점을 알 수 있습니다.
* 저의 경우 이 풀이를 세그먼트 트리를 이용하였고 그 이유는 순위 맞는 값을 찾는 시간을 O(logN)으로 하면 풀이할 수 있다고 생각했기 때문입니다.
*
* 세그먼트 트리를 이용하여 사탕이 넣어졌을 경우
* 사탕의 위치에 해당하는 tree 배열의 값을 들어온 값만큼 더해주는 작업을 수행하고 (위의 update)
*
* 사탕을 꺼내는 것은 rank 변수를 이용하여 이 rank의 값을 수정하면서 마지막 리프 노드의 위치를 출력하는 방법을 사용했습니다.
*
* 제가 문제를 풀이할 때 생각했던 방법을 그대로 써보자면
* 먼저 쉽게 사탕의 맛이 최대 4개 있다고 가정하겠습니다. 입력으로 다음과 같은 입력이 주어졌다고 해보겠습니다.
* 2 1 1
* 2 3 2
* 2 4 1
* 그러면 세그먼트 트리의 루트에는 모든 사탕 갯수의 합이 들어있고 그 아래에 반으로 나눠서 각각의 구간에서 사탕의 맛이 나타날 것입니다.
* 이를 그림으로 나타내면 아래와 같을 것 입니다.
*
*                           [트리의 루트 노드 : 4]
*                            /                 \
*               [1~2 구간 노드 : 1]         [3~4 구간 노드 : 3]
*                  /          \               /           \
*             [1 : 1]        [2 : 0]      [3 : 2]       [4 : 1]
*
* 여기서 어떤 순위의 사탕을 빼낸다고 한다면 루트 노드 부터 아래의 과정을 수행합니다.
* 1. 루트 노드의 값을 하나 낮추고 왼쪽 자식과 아래쪽 자식의 노드 값을 가져옵니다.
* 2. 왼쪽 자식 값이 rank의 값보다 크거나 같은지 확인합니다. 만약 해당한다면 왼쪽 노드로 들어가서 1번부터 다시 진행합니다.
* 3. 만약 왼쪽 노드보다 rank값이 크다면 왼쪽 노드의 값 만큼 rank의 값을 감소하고 오른쪽 노드로 들어가서 1번부터 다시 진행합니다.
* 4. 위의 과정을 반복해서 더이상 자식이 없는 리프 노드에 도달하면 그 위치를 리턴합니다.
*
* 이제 예시의 그림에서 rank = 4인 값을 꺼내보는 과정을 생각해보겠습니다.
* 먼저 루트 노드의 값을 하나 낮추고 자식 노드들에서 왼쪽으로 갈지 오른쪽으로 갈지 정합니다.
* 여기서 rank 값이 4이므로 1~2구간 노드의 값은 1이므로 rank값이 더 크니까 오른쪽을 기준으로 다시 탐색을 진행합니다.
* 이때 rank -= 1 (tree의 왼쪽 자식 값)를 해줍니다.
* 그리고 위치를 나타내는 idx 값은 3~4 구간의 시작 값인 3으로 수정합니다.
*
*                   [root : 2] <= 현위치, rank = 4, idx = 1
*                   /        \
*               [1~2 : 1]    [3~4 : 3] <= 다음 위치, rank = 3, idx = 3
*
* 3~4 구간 노드에서 값을 하나 빼고 자식 노드들과 비교합니다. rank가 3이므로 왼쪽의 자식 2보다 큽니다. 그러면 오른쪽으로 탐색을 이어갑니다.
* 이때 rank의 값에 - 2를 해주고 idx 값은 4로 수정되게 됩니다.
*
*                   [3~4 : 2] <= 현위치, rank = 3, idx = 3
*                   /        \
*              [3 : 2]       [4 : 1] <= 다음 위치, rank = 1, idx = 4
*
* 4의 위치의 값을 하나 빼주고 탐색을 진행합니다. 하지만 더이상 자식이 없기 때문에 해당하는 idx값을 리턴하고 탐색을 모두 종료합니다.
*
*                       [4 : 0] <= 더이상 자식이 없음 리턴, rank=1, idx=4
*
* 이 과정을 통해서 값을 순위를 기준으로 빼내오는 것과 입력하는 것이 둘 다 logN 만에 수행할 수 있게 됩니다.
* 그러면 시간 상 문제가 없으므로 문제가 풀이가 가능해집니다.
*
* 저는 위의 가정을 내놓고 이를 바탕으로 코드를 제작하여 문제를 풀이할 수 있었습니다.
*
* */