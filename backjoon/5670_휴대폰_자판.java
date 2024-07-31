import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{

        boolean end;
        Map<Character, Node> child;

        Node(){
            this.end = false;
            this.child = new HashMap<>();
        }
    }

    static List<String> list;
    static Node root;
    static double answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String input = br.readLine();

        while(input != null && !input.equals("")){

            int N = Integer.parseInt(input);
            list = new ArrayList<>();
            root = new Node();
            root.end = true;
            answer = 0;

            for(int i=0; i<N; i++){

                input = br.readLine();
                list.add(input);

                Node node = root;
                Node next;

                for(int j=0; j<input.length(); j++){

                    if(!node.child.containsKey(input.charAt(j))){
                        next = new Node();
                        node.child.put(input.charAt(j), next);
                    }
                    else{
                        next = node.child.get(input.charAt(j));
                    }
                    node = next;
                }
                node.end = true;
            }

            for(int i=0; i<N; i++){
                input = list.get(i);
                recursive(0, 0, input, root);
            }
            sb.append(String.format("%.2f",answer/N)+"\n");
            input = br.readLine();

        }

        System.out.print(sb.toString());

    }

    static void recursive(int depth, int count, String str, Node node){

        if(depth >= str.length()){
            answer += count;
            return;
        }

        if(node.child.size()==1 && !node.end){
            recursive(depth+1, count, str, node.child.get(str.charAt(depth)));
        }
        else{
            recursive(depth+1, count+1, str, node.child.get(str.charAt(depth)));
        }

    }

}

/*
 * 트라이 문제
 *
 * 해당 문제의 포인트는 입력된 문자열을 기준으로 탐색하면서 끝 단어거나, 자식 노드가 하나가 아닌 노드의 갯수를 모두 구해서 평균을 구하는 것 입니다.
 * 처음 들어오는 hello, hell, heaven 부분을 트라이로 나타내면 다음과 같습니다.
 *                                       root
 *                                      /
 *                                     h
 *                                    /
 *                                  e
 *                                 / \
 *                                l   a
 *                               /     \
 *                             l        v
 *                            /          \
 *                           o            e
 *                                         \
 *                                          n
 *
 * 여기서 누군가가 자판으로 h를 썻다면 h의 자식은 e 밖에 없습니다. 그러면 e는 자동완성 되어 he까지 써지게 되는겁니다.
 * 그리고 e에서 분기가 생기면 입력이 필요하다는 뜻입니다. 그러므로 찾고자 하는 단어를 추가 입력합니다. 여기서는 l을 입력했다고 하겠습니다.
 * l을 입력하면 l의 자식은 l밖에 없습니다. 또한, l의 자식은 o 밖에 없습니다.
 * 하지만 hell 이라는 단어에서 이미 하나의 단어가 완성되기 때문에 o까지 가면 안됩니다. 그러므로 바로 아래의 l까지만 자동완성 시켜줍니다.
 * 이후 o를 써야만이 hello라는 단어를 쓸 수 있고 총 3번의 입력이 필요합니다.
 *
 * 위와 같이 hell의 l과 같이 단어의 마지막 문자이거나, he의 e와 같이 자식이 여러명이라면 입력 횟수가 증가하게 되는겁니다. (또한, 최초에는 반드시 입력을 해야하기 때문에 count는 1부터 시작한다고 보시면 됩니다.)
 * 즉, root부터 자식노드로 탐색을 시작하되 위의 조건에 맞게 count 값을 증가시킨 후 마지막에 count/N을 수행해주면 해결할 수 있습니다.
 *
 * 주의점 :
 * 1. 반드시 소숫점 둘 째 자리 까지 출력해야합니다.
 * 2. 입력에 테스트케이스 갯수가 주어지지 않습니다. 그러므로 다음 입력을 받은 후, 이 값이 공란("")이거나 null일 경우 반복을 멈추게 처리해야합니다.
 *
 *
 *
 * */