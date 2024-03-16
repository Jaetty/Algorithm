import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static Node[] nodes;

    static class Node{

        Node parent;
        List<Node> path;
        int id, h;

        Node(int id){
            this.id = id;
            path = new ArrayList<>();
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        nodes = new Node[N+1];

        for(int i=1; i<N; i++){
            st = new StringTokenizer(br.readLine());

            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            if(nodes[left]==null){
                nodes[left] = new Node(left);
            }

            if(nodes[right]==null){
                nodes[right] = new Node(right);
            }

            nodes[left].path.add(nodes[right]);
            nodes[right].path.add(nodes[left]);
        }

        dfs(1, 1);

        M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());

            Node left = nodes[Integer.parseInt(st.nextToken())];
            Node right = nodes[Integer.parseInt(st.nextToken())];

            while(left.id != right.id){

                if(left.h < right.h){
                    right = right.parent;
                }
                else if(left.h > right.h){
                    left = left.parent;
                }
                else{
                    left = left.parent;
                    right = right.parent;
                }
            }
            sb.append(left.id+"\n");
        }

        System.out.print(sb.toString());

    }

    static void dfs(int curr, int h){

        nodes[curr].h = h;

        for(Node next : nodes[curr].path){
            if(next.h != 0) continue;
            next.parent = nodes[curr];
            dfs(next.id, h+1);
        }

    }

}

/*
* 트리 (최소 공통 조상) 문제
*
* 해당 문제의 경우 아래와 같은 로직으로 풀이가 가능합니다.
* 1. 입력을 트리의 형태로 구현한 후 DFS를 통해 트리에 "높이 값"을 부여한다.
* 2. 공통 조상을 찾을 노드 두개를 비교하여 한쪽이 높이 낮으면 같은 높이가 될 때까지 올려준다.
* 3. 서로 높이가 같음에도 서로가 다른 노드라면 둘의 부모 노드로 올려준다.
*
* 우선 1번을 수행하는 이유는 루트는 1로 정해져있지만 입력은 랜덤하게 부여됩니다.
* 그러므로 우선 트리를 구성한 후 DFS를 통해서 각각의 노드가 어느 높이에 있는지 식별해야합니다.
*
* 2번과 3번이 문제의 핵심 풀이 방법입니다.
* 가장 떠올리기 쉬운 방법은 두 노드에서부터 하나씩 위로 올라가서 서로 같아지는 구간의 값을 출력하면 된다는 점입니다.
* 그러나 여기서 중요한 점은 각 노드가 위치한 높이를 고려해야합니다.
*
* 예를들어 다음과 같은 트리가 구성되어있다면
*
*                   1           높이(h) : 1
*                 2             높이(h) : 2
*               4  5            높이(h) : 3
*             6                 높이(h) : 4
*
* 여기서 공통 조상을 묻는 노드로 6번과 5번이 들어왔다고 해보겠습니다. 정답은 2가 되어야합니다.
* 저희는 서로가 공통 노드에 도착할 때까지 올려주는 방법을 사용 중인데 6번과 5번이 서로 맞지 않으니 둘 다 올려주게 되면
* 5는 2로 갈 것이고 6은 4로 가게 됩니다. 그리고 다시 4와 2는 맞지 않으니 2는 1로 가고 4는 2로 갈 것입니다.
*
* 즉 서로의 높이를 맞춰주지 않으면 영원히 못 만나게될 수 있다는 점입니다.
* 그러므로 우선 서로의 높이를 맞춰주는 과정을 넣어준다면 6은 5보다 높이가 높으니 6만 4로 보낼 수 있습니다.
* 그리고 이후부터 4와 5는 높이가 같으나 서로 값이 다르니 둘 다 위로 올려주면 둘 다 2가 되어 같은 노드에 위치하게 됩니다.
*
* 이러한 방법으로 문제를 해결하였고 이를 코드로 구현하여 풀이할 수 있습니다.
*
* */