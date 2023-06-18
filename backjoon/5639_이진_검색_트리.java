import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static List<Integer> list;
    static class Node{
        Node parent;
        Node left, right;
        Integer value;

        Node(Node node){
            this.parent = node;
        }

    }


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        list = new ArrayList<>();

        try {
            while(true){
                list.add(Integer.parseInt(br.readLine()));
            }
        }catch (Exception e){

            Node first = makeNode(null);
            first.value = list.get(0);

            for(int i=1; i<list.size(); i++){

                int val = list.get(i);
                Node node = first;

                while(true){

                    if(node.left == null && node.right == null){
                        node.left = new Node(node.parent);
                        node.right = new Node(node.parent);
                    }

                    if(node.value == null){
                        node.value = val;
                        break;
                    }

                    if(val > node.value) node = node.right;
                    else node = node.left;

                }
            }
            print(first);
        }

    }

    static Node makeNode(Node node){

        Node result = new Node(node);
        result.left = new Node(result);
        result.right = new Node(result);
        return result;
    }

    static void print(Node node){

        if(node.value==null) return;

        for(int i=0; i<2; i++){
            if(i%2==0) print(node.left);
            else print(node.right);
        }
        System.out.println(node.value);
    }

}
/*
* 이진 탐색 트리 기초 문제
* 이 문제의 특이한 점은 입력이 정해져서 들어오지 않기 때문에 try-catch를 통해 EOF가 발생 시 입력을 멈추게 해줘야한다.
* 이진 탐색 트리의 입력값이 전위 방식으로 오기 때문에 값은 [루트-왼쪽-오른쪽] 순으로 입력이 된다.
* 그렇다면 단순하게 현재 루트보다 크면 왼쪽 아니면 오른쪽으로 루트를 바꿔가며 탐색하게 만들어준다.
* 탐색 중 도달한 노드의 키 값이 null인 경우 그 안에 값을 넣어주기만 하면 된다.
* 출력의 경우 후위 순서로 [왼쪽-오른쪽-루트] 순서를 지켜가며 출력해주면 된다.
*
* */