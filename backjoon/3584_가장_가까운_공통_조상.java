import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{
        int level, id;
        Node root;
        List<Integer> child;

        Node(int level, int id){
            this.id = id;
            this.level = level;
            root = null;
            child = new ArrayList<>();
        }
    }

    static Node[] nodes;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for(int tc=0; tc<T; tc++){

            int N = Integer.parseInt(br.readLine());
            nodes = new Node[N+1];

            for(int i=1; i<=N; i++){
                nodes[i] = new Node(1, i);
            }

            for(int i=0; i<N-1; i++){
                st = new StringTokenizer(br.readLine());
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                nodes[parent].child.add(child);
                nodes[child].root = nodes[parent];
            }

            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            Node root = getRoot(nodes[left]);
            root.level = 1;

            setLevel(root);

            sb.append(getCommon(nodes[left], nodes[right])+"\n");


        }

        System.out.print(sb.toString());

    }

    static Node getRoot(Node start){

        while(start.root != null){
            start = start.root;
        }
        return  start;
    }

    static void setLevel(Node root){

        for(int child : root.child){
            nodes[child].level = root.level+1;
            setLevel(nodes[child]);
        }

    }

    static int getCommon(Node left, Node right){

        while(left.level > right.level){
            left = left.root;
        }

        while(right.level > left.level){
            right = right.root;
        }

        while(left.id != right.id){

            left = left.root;
            right = right.root;

        }

        return left.id;

    }


}

/*
 * 트리 문제
 *
 * 해당 문제는 트리 기초 문제입니다. 해당 문제의 포인트는 다음과 같습니다.
 * 1. N <= 1000
 * 2. 트리의 root가 주어지지 않고 랜덤하게 부모와 자식 관계가 입력된다.
 *
 * 위의 포인트로 알 수 있는 점은 root를 찾는 과정이 편향 트리라고 해도 O(1000)밖에 안걸린다는 점과, 트리의 root를 입력을 다 받고 찾아내야한다는 점 입니다.
 *
 * 해당 문제를 쉽게 생각해보려면 먼저 트리를 떠올려봐야합니다.
 * 맨 위의 노드인 root에서 자식들이 쭉 뻗어 있는 형태의 트리가 있습니다.
 * 여기서 랜덤하게 2개의 노드를 골랐을 때 이들의 최소 공통 조상을 알아내야 합니다.
 * 최소 공통 조상은 그 2개의 노드의 부모 노드를 찾아서 올라갔을 때 가장 최초로 같이 만나게 되는 같은 레벨(깊이)의 노드를 뜻합니다.
 * 그렇다면 공통 조상을 알기 위해서는
 * 1. 나의 직계 조상을 알아야하고,
 * 2. 현재 내가 어느 레벨의 노드인지를 알아야합니다.
 *
 * 그렇다면 쉽게 알 수 있는 점이 먼저 랜덤으로 받은 2 노드 left와 right의 level을 맞추고, 이들이 서로 같은 노드를 가르킬 때 까지 같이 부모를 찾으면 됩니다.
 *
 * 이를 코드로 나타내면 풀이할 수 있습니다.
 *
 *
 * */