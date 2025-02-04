import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{

        char c;
        boolean end;
        Node[] child;

        Node(){
            this.end = false;
            child = new Node[26];
        }

    }

    static int C, N, Q;
    static Set<String> set;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder answer = new StringBuilder();

        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        set = new HashSet<>();

        Node color_root = new Node();
        Node c_node;

        nodeSet(color_root, C, br);

        for(int i=0; i<N; i++){
            set.add(br.readLine());
        }


        Q = Integer.parseInt(br.readLine());

        for(int i=0; i<Q; i++){

            String str = br.readLine();
            boolean flag = false;
            c_node = color_root;

            for(int j=0; j<str.length()-1; j++){

                c_node = c_node.child[str.charAt(j)-'a'];

                if(c_node == null){
                    break;
                }

                if(c_node.end && set.contains(str.substring(j+1))){
                    flag = true;
                    break;
                }

            }


            if(flag){
                answer.append("Yes\n");
            }
            else{
                answer.append("No\n");
            }
        }
        System.out.print(answer.toString());

    }

    static void nodeSet(Node root, int last, BufferedReader br) throws Exception{

        Node node;

        for(int i=0; i<last; i++){

            String var = br.readLine();
            char c = ' ';
            node = root;

            for(int j=0; j<var.length(); j++){

                int idx = var.charAt(j)-'a';

                if(node.child[idx] == null){
                    Node child = new Node();
                    child.c = c;
                    node.child[idx] = child;
                }
                node = node.child[idx];
            }

            node.end = true;

        }

    }

}

/*
 * 19585 전설
 *
 * 트라이 문제
 *
 * 문제의 핵심 풀이 로직은 트라이로 색상이 있는지 확인하고, subString으로 닉네임을 가져와서 비교하는 것 입니다.
 * 저의 경우 처음에 트라이를 만들 때, HashMap으로 자식들을 기억했는데 이게 조회할 때 시간이 단순 배열보다 더 걸립니다.
 * 그런데 아무래도 트라이를 할 때, 불필요한 메모리를 줄이기 위해 map을 사용하는 습관이 있다보니 로직은 이미 풀고서 시간초과가 나는 경우를 계속 겪었습니다.
 *
 * 저는 문제 풀이에 꽤 많은 시간과 시도가 있었는데 다음과 같은 과정을 시도했습니다.
 *
 * 1. 설마 모든 성립하는 경우의 문자열을 hashset에 다 저장할 리 없다. 애초에 그러면 메모리 초과가날 것이다.
 *    그러면 각각 따로 hashset에 저장한 후, StringBuilder로 하나씩 늘려가며 색상을 먼저 조회하고 색상이 맞다면, 닉네임을 subString으로 조회해보자.
 *     -> 시간 초과.
 *
 * 2. 1번에 트라이를 도입해보자. 그러면 굳이 StringBuilder를 toString()하는데 내부적으로 똥작하는 O(N)의 동작을 줄일 수 있다.
 *    -> 시간 초과.
 *
 * 3. 트라이를 2개 써보자 대신 여기서 닉네임은 거꾸로 조회해서 닉네임이 각각 위치했던 idx의 -1 만큼 색상을 조회한 후, 색상, 닉네임이 성립하는지 확인.
 *     -> 시간 초과.
 *
 * 4. 도무지 이해가 안간다, 2번 / 3번 로직은 될 줄 알았는데 왜 안되는거지? 해서 자잘한 수정을 몇 시간 거듭하다, 질문에 Map이 의심된다는 글을 찾았습니다.
 *    child의 Map 방식에서 배열 방식으로 코드를 수정.
 *    -> 3번 로직 시간 초과. 2번 로직이 성공.
 *
 * 여기서 다른 사람들의 해답을 봤는데 인터넷 글에 따르면 3번 로직은 자바 외의 다른 언어에서는 성공한다고 합니다.
 * 메모리가 넉넉한 만큼 Map을 진작에 포기했다면 금방 풀이 했을텐데 문제 조건과 풀이에 필요한 메모리 판단을 조금 더 신경써야겠구나 생각했습니다.
 *
  */