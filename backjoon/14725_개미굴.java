import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{

        String food;
        List<Node> leafs;

        Node(String food){
            this.food = food;
            this.leafs = new ArrayList<>();
        }

    }

    static int N;
    static StringBuilder sb;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        Node tree = new Node("root");

        for(int i=0; i<N; i++){

            st = new StringTokenizer(br.readLine());
            st.nextToken(); // K 값 제거
            Node node = tree;

            while(st.hasMoreTokens()){

                int size = node.leafs.size();
                String food = st.nextToken();
                Node leaf = new Node(food);

                if(size == 0){
                    node.leafs.add(leaf);
                }
                else{

                    boolean flag = false;

                    for(int j=0; j<size; j++){
                        if (node.leafs.get(j).food.equals(food)){
                            node = node.leafs.get(j);
                            flag = true;
                            break;
                        }
                    }

                    if(flag) continue;
                    node.leafs.add(leaf);

                }

                node = leaf;

            }
        }

        search(0, tree);
        System.out.println(sb.toString());


    }

    static void search(int depth, Node node){

        if(!node.leafs.isEmpty()){
            Collections.sort(node.leafs, (e1,e2)->{
                return e1.food.compareTo(e2.food);
            });
        }

        for(int i=0; i<node.leafs.size(); i++){
            stringSet(depth);
            Node next = node.leafs.get(i);
            sb.append(next.food).append("\n");
            search(depth+1, next);
        }

    }

    static void stringSet(int depth){

        for(int i=0; i<depth; i++){
            sb.append("--");
        }

    }

}

/*
 * 트리 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. N의 크기는 최대 1000, K의 크기는 최대 15이다.
 * 2. 최상위 굴을 포함하여 하나의 굴에서 개미굴이 여러개로 나뉠 때 먹이는 종류별로 최대 한 번만 나올 수 있다.
 * 3. 개미 로봇은 위에서부터 탐색을 진행한다. 다만 반드시 트리를 문자열 순서대로 탐색한다고는 명시되어있지 않다.
 *
 * 핵심 포인트는 트리를 구성할 수 있는가? && 트리를 탐색할 수 있는가를 구현하는 문제입니다.
 * 1번 포인트로 알 수 있는 점은 가장 깊은 트리의 높이는 15이고, N이 최대 1000이므로 최대 같은 층에 1000개의 다른 굴이 있을 수 있다는 점을 알 수 있습니다.
 * 2번 포인트는 어떤 한 굴에서부터 여러개로 나뉘는 굴들은 절대 같은 먹이 종류를 가질 수 없다는 점을 나타냅니다.
 *
 * 즉, 다음의 1번 그림은 성립할 수 없으나, 2번 그림은 성립할 수 있습니다.
 *
 * 1번 그림 -> root에서 분파되는 굴에 A가 두개 있으므로 안됨
 *            root
 *           /   \
 *          A     A
 *
 * 2번 그림 -> root의 분파가 서로 다르며 각각의 A,B의 분파되는 굴은 A를 가지고 있을 수 있음
 *            root
 *            /  \
 *           A    B
 *          /      \
 *         A        A
 * 3번 포인트는 개미 로봇은 맨 위에서부터 차례대로 아래로 내려가고 있음을 나타내지만 A,B,C,D와 같이 1층에 여러개의 구멍이 있을 때 반드시 순서대로 진행되지는 않습니다.
 * 그렇다면 A,B,C,D의 어느 것이 랜덤하게 나왔을 시 이미 그 굴을 가지고 있는지 파악하는 로직이 필요하다는 것을 알 수 있습니다.
 * 1번 포인트와 같이 생각해보면 굴에 대한 리스트에서 값을 가져와 이미 존재하는지 반복을 통해 파악하더라도 시간이 넉넉하다는 점을 예상할 수 있습니다.
 *
 * 위와 같이 포인트를 고려해서 생각해보면
 * 트리에 값을 넣어주고, 트리를 문자열 기준으로 정렬한 후 탐색해주면 문제를 풀이할 수 있습니다.
 *
 *
 * */