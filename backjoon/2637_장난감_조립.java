import java.io.*;
import java.util.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {
    static class Node{
        boolean parent;
        int count;
        List<int[]> child;

        Node(){
            parent = false;
            count = 0;
            child = new ArrayList<>();
        }

    }
    static int N, M, counts[], left[];
    static Node[] nodes;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        nodes = new Node[N+1];
        counts = new int[N+1];
        left = new int[N+1];

        counts[N] = 1;

        for(int i=1; i<=N; i++){
            nodes[i] = new Node();
        }

        List<Integer> parents = new ArrayList<>();

        for(int i=0; i<M; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int parent = Integer.parseInt(st.nextToken());
            int child = Integer.parseInt(st.nextToken());
            int count = Integer.parseInt(st.nextToken());

            nodes[parent].child.add(new int[]{child, count});
            if(!nodes[parent].parent){
                nodes[parent].parent = true;
                parents.add(parent);
            }
            left[child]++;
        }

        // 처음엔 합성 부품인 애들 중에서 합성 부품이 얼마나 필요한지만 카운트를 세어줌
        // N부터 시작해서 더이상 부모가 없는 애들만 카운트 값을 상속시켜줌
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(N);

        while(!queue.isEmpty()){
            int p = queue.poll();

            for(int[] val : nodes[p].child){
                if(nodes[val[0]].parent){
                    counts[val[0]] += val[1] * counts[p];
                    left[val[0]]--;
                    if(left[val[0]]==0){
                        queue.add(val[0]);
                    }
                }
            }
        }

        for(int i=0; i<parents.size(); i++){
            int p = parents.get(i);

            for(int[] val : nodes[p].child){
                if(!nodes[val[0]].parent){
                    counts[val[0]] += val[1] * counts[p];
                }
            }
        }


        StringBuilder sb = new StringBuilder();

        for(int i=1; i<=N; i++){
            if(!nodes[i].parent){
                sb.append(i + " " + counts[i]).append("\n");
            }
        }

        System.out.print(sb.toString());

    }

}
/*

2637 장난감 조립

위상 정렬 + 다이나믹 프로그래밍 문제

문제의 핵심은 부품 X를 만들기 위해 필요한 다른 중간 부품의 제작 횟수를 상속 해주는 것 입니다.
만약, dfs로 모든 반복을 돌면서 필요한 횟수를 수정하게 된다면 시간초과가 발생합니다.

그런데 문제에서 주어지는 N과 M은 각각 최대 100으로 충분히 적은 숫자입니다.
그러므로 필요한 수량을 정해주기 위한 연산 횟수를 최악의 경우라도 M*M번 정도로만 만들어도 통과할 수 있습니다.

이렇게 하기 위해 적용한 방법은
1. 각각의 부품이 총 몇개 필요한지 기억하는 배열을 만든다. -> counts[]
2. 완성품인 N부터 시작하여 위상정렬을 활용하여 정확한 중간 부품의 필요 갯수를 기록한다.
3. 중간 부품들의 필요 갯수를 다 구했다면, 그 부품들의 갯수 * 필요한 기본 부품 수를 합산하여 기록한다.
4. 기본 부품들을 순회하며 구했던 필요 수를 출력한다.

다음과 같은 예시가 있다고 해보겠습니다.

7
8
4 5 1
5 1 2
5 2 2
6 5 2
6 3 3
6 4 4
7 5 2
7 6 3

위와 같은 입력의 경우 가장 위의 완성품의 값은 (7)이고, counts 배열의 초기는 다음과 같습니다.
counts = {0,0,0,0,0,0,1}

(7)을 부모 노드라고 지칭해보겠습니다. (7)을 만들려면 (5) 2개, (6) 2개가 필요합니다.

counts[5] += 2*1, counts[6] += 3*1로 수정됩니다.
counts = {0,0,0,0,2,3,1}

여기서 (6)의 경우는 (5)가 2개 필요합니다. (7)을 만드려면 (6)이 2개가 필요했으므로
(5)는 4개가 필요하게 됩니다.
그 말은, counts[5] += 2 * counts[6] 를 기록하면 됩니다.
또한, (6)은 (4)가 4개 필요하므로 counts[5] += 4 * counts[6] 를 기록하면 다음과 같습니다.
counts = {0,0,0,12,8,2,1}

여기서 (4)는 5로 만들어지기 때문에, (5)는 (4)라는 부모가 있는 것과 같습니다.
만약, 여기서 (5)의 값을 먼저 기록하게 되면 순서가 어긋나서 틀린 counts 값이 적용됩니다.
이때를 위해 위상정렬을 이용하여 부모의 순서가 다 끝난 (4)를 다음 순서로 counts를 수정합니다.

여기서, (4)는 5가 1개 필요하므로, counts[5] += 1 * counts[4]
counts = {0,0,0,8,20,2,1}

이후, (5)의 경우는 기본 부품만 자식이고 중간부품을 자식으로 두지 않기 때문에 넘어갑니다.

이런 식으로 먼저 중간 부품이 필요한 갯수를 구해놓은 후,
중간 부품들을 순회하면서 기본 부품의 갯수를 구해주면 됩니다.
예를 들어, (5)는 (1)과 (2)가 각각 2개씩 필요하므로
counts[1] += 2 * counts[5], counts[2] += 2 * counts[5]를 해주면 다음과 같고,
counts = {40,40,0,8,20,2,1}

여기에 (6)의 경우 (3)이 3개 필요하므로, counts[3] += 3 * counts[6]
counts = {40,40,9,8,20,2,1}

여기서 1,2,3만 기본 부품이므로 정답은 40 40 9가 되는 것입니다.

*/