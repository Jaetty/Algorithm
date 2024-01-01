import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][] inputs, indexArr;
    static Node[] nodes;
    static int N;
    static StringBuilder sb;

    static class Node{

        int id;
        Node parent, left, right;

        Node(int id){
            parent = null;
            left = null;
            right = null;
            this.id = id;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());

        inputs = new int[2][N+1];
        indexArr = new int[2][N+1];
        nodes = new Node[N+1];

        for(int i=0; i<2; i++){

            StringTokenizer st = new StringTokenizer(br.readLine());

            for(int j=1; j<=N; j++){
                inputs[i][j] = Integer.parseInt(st.nextToken());
                indexArr[i][inputs[i][j]] = j;
                if(i==0){
                    nodes[j] = new Node(j);
                }
            }
        }

        recursive(dc(1, N));

        System.out.println(sb.toString());


    }

    static void recursive(Node node){

        if(node == null){
            return;
        }

        sb.append(node.id).append(" ");

        recursive(node.left);
        recursive(node.right);

    }

    static Node dc(int left, int right){

        if(left>right || right <= 0){
            return null;
        }

        int val = inputs[1][right];

        Node rootNode = nodes[inputs[1][right]];

        int index = right-1;

        while(index>0){

            // post 입력의 값을 기준으로 계속해서 왼쪽으로 탐색한다.
            // 위에서 가져온 값이 위치한 inorder 입력의 인덱스 값이 root의 inorder 인덱스 값보다 작으면 break;
            if(indexArr[0][inputs[1][index]] < indexArr[0][inputs[1][right]]){
                break;
            }

            index--;
        }

        Node leftRoot = dc(left, index);
        Node rightRoot = dc(index+1, right-1);

        rootNode.left = leftRoot;
        rootNode.right = rightRoot;

        if(leftRoot != null){
            leftRoot.parent = rootNode;
        }
        if(rightRoot != null){
            rightRoot.parent = rootNode;
        }

        return rootNode;

    }



}

/*
* 트리 + 분할 정복 문제
*
* 해당 문제는 Inorder(중위 순회)와 Postorder(후위 순회)로 입력이 들어왔을 때 Preorder(전위 순회) 순서로 출력하는 문제입니다.
* 문제를 이해하기 위해 다음과 같은 트리가 있다고 생각해보겠습니다.
*
*                       1
*                     /   \
*                    2     5
*                   / \     \
*                  3   4     6
*
* 이 경우 입력으로 주어졌을 때는 다음과 같이 주어집니다.
* 6
* 3 2 4 1 5 6
* 3 4 2 6 5 1
*
* 입력을 통해 해당 규칙들을 기준으로 저희들이 알아낼 수 있는 포인트가 3가지 있습니다.
* 1. Inorder의 입력에서 1번 노드를 보면 1번의 왼쪽에 존재하는 노드(3,2,4)들은 1의 왼쪽에, 오른쪽에 존재하는 노드(5,6)들은 오른쪽에 위치합니다.
*    즉 InOrder 기준으로 어떤 한 노드의 왼쪽과 오른쪽의 노드들은 나보다 왼쪽에 있거나 오른쪽에 있다는 것을 나타냅니다.
* 2. PostOrder 입력을 보면 1이 맨 마지막에 위치한 것을 알 수 있습니다. 즉 부모는 맨 오른쪽에 위치한다는 것을 알 수 있습니다.
* 3. PostOrder의 입력을 보면 맨 마지막 노드를 부모로하여 자식 노드들의 왼쪽과 오른쪽의 경계가 존재합니다.
*    3 4 2 / 6 5 / 1 로 3,4,2는 1을 기준으로 왼쪽에 존재하는 노드들, 6,5는 오른쪽에 존재합니다.
*
* 그럼 해당 포인트들을 고려해서 풀이법을 생각해보면 다음과 같습니다.
* 1. 3번 포인트를 수행하기 위해 어떤 부모를 기준으로 Postorder에서 좌 우가 나뉘는 경계선을 찾습니다. 여기서 좌 우 경계를 찾기 위해 1번 포인트를 활용합니다.
* 2. 좌의 경계에 존재하는 노드들과 우의 경계에 위치한 노드들은 다시 그들 중 부모를 기준으로 좌 우의 경계를 찾습니다.
* 3. 2번의 과정 중 더 이상 나누어 떨어질 수 없다면 가장 위의 부모 노드를 리턴합니다.
* 4. 리턴 받은 노드들을 각각 그들의 부모의 좌 우에 붙여줍니다.
* 5. 이 과정을 맨 최초의 루트 노드에 도달할 때 까지 반복합니다.
*
* 즉 위의 입력 예시로 생각해보면 1은 맨 오른쪽에 있으므로 부모이고 좌 우의 경계를 나눠주면 [ 3 4 2 (좌) / 6 5 (우) / 1 (부모)] 이니까
* 3 4 2에 따라 2번 풀이를 적용하면 다시 [ 3 / 4 / 2 ]가 됩니다.
* 3, 4는 더이상 나뉠 수 없기 때문에 2의 좌 우에 3, 4가 붙습니다. 이제 2가 가장 부모이니 2를 리턴해줍니다.
*               1
*
*       2           5
*      / \
*     3   4             6
* 1의 기준 왼쪽이 완료되었으니 다음으로 오른쪽 부분인 5와 6을 생각해보면  [ ( 좌에 노드 없음 ) / 6 / 5 ] 이므로
* 5의 오른쪽에 6을 붙여줍니다.
*               1
*
*       2           5
*      / \           \
*     3   4           6
*
* 이렇게 왼쪽의 부모 노드로 리턴한 2와 오른쪽의 부모 노드인 5를 1의 왼쪽과 오른쪽에 붙여줍니다.
*
*                    1
*                  /   \
*                 2     5
*                / \     \
*               3   4     6
*
* 이후 1에서 부터 프리 오더로 순회하며 출력하면 됩니다.
*
* 이렇게 분할 정복의 아이디어를 코드로 구현하게 되면 문제를 해결할 수 있습니다.
*
* */