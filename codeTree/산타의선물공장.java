import java.util.*;
import java.io.*;

public class Main {

    static class Node{
        public Node front;
        public Node back;
        int weight;
        int id;
        int belt;
    }

    static Map<Integer, Node> map;
    static Map<Integer, Integer> contain;

    // 처음과 끝을 만들고 처음과 끝은 처음에 연결시켜준다.
    // 그리고 다음에 들어오는 애들은 모두 끝의 앞에 있는 Node 뒤에 두고 끝은 언제나 끝이 차지함
    // [시작 노드] -  [마지막 노드] <-1 => [시작] - 1(새로 들어온 노드) - [마지막] 이런식으로
    static Node[] beltFirst;
    static Node[] beltLast;
    static StringTokenizer st;
    static StringBuilder sb;
    static int N, M;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        map = new HashMap<>();
        sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for(int tc = 0; tc<T; tc++){
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());

            switch(cmd){
                case 100 : process1(); break;
                case 200 : process2(Integer.parseInt(st.nextToken())); break;
                case 300 : process3(Integer.parseInt(st.nextToken())); break;
                case 400 : process4(Integer.parseInt(st.nextToken())); break;
                case 500 : process5(Integer.parseInt(st.nextToken())-1); break;
            }
        }
        System.out.print(sb);

    }

    public static void process1(){

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        beltFirst = new Node[M];
        beltLast = new Node[M];
        contain = new HashMap<>();

        for(int i=0; i<M; i++){
            Node head = new Node();
            Node tail = new Node();
            head.belt = i+1;
            tail.belt = i+1;
            beltFirst[i] = head;
            beltLast[i] = tail;
            tail.front = head;
            head.back = tail;
        }

        Node[] input = new Node[N];

        for(int i=0; i<N; i++){
            input[i] = new Node();
            int val = Integer.parseInt(st.nextToken());
            input[i].id = val;
            map.put(val, input[i]);
        }

        for(int i=0; i<N; i++){
            int val = Integer.parseInt(st.nextToken());
            input[i].weight = val;
        }

        for(int i=0; i<M; i++){
            int val = N/M*i;
            for(int j=val; j<val+N/M; j++){
                addPresent(i, input[j]);
            }
        }


    }

    public static void process2(int w_max){
        int result = 0;
        for(int i=0; i<M; i++){
            if(beltFirst[i]==null || beltFirst[i].back.belt != 0) continue;
            Node tmp = beltFirst[i].back;

            if(tmp.weight<=w_max){
                result += tmp.weight;
                remove(tmp);
                map.remove(tmp.id);
                contain.remove(tmp.id);
            }else{
                remove(tmp);
                addPresent(i,tmp);
            }
        }
        sb.append(result+"\n");

    }

    public static void process3(int id){
        Node tmp = map.get(id);
        if(tmp==null){
            sb.append("-1\n");
        }
        else{
            sb.append(id+"\n");
            remove(tmp);
            map.remove(tmp.id);
            contain.remove(tmp.id);
        }
    }

    public static void process4(int id){

        Node tmp = map.get(id);
        if(tmp==null){
            sb.append("-1\n");
        }
        else{
            int belt = contain.get(id);
            if(beltFirst[belt].back!=beltLast[belt].front){
                Node head = tmp.front;
                Node tail = beltLast[belt].front;

                beltLast[belt].front = head;
                head.back = beltLast[belt];
                beltFirst[belt].back.front = tail;
                tail.back = beltFirst[belt].back;
                beltFirst[belt].back = tmp;
                tmp.front = beltFirst[belt];
            }
            sb.append((belt+1)+"\n");
        }

    }

    public static void process5(int belt){

        if(beltFirst[belt]==null){
            sb.append("-1\n");
        }
        else{

            Node node = beltFirst[belt].back;
            int nbelt = 0;

            for(int i=belt+1; i<M+belt; i++){
                if(beltFirst[i%M] != null){
                    nbelt = i%M;
                    break;
                }
            }

            while(node.belt==0){
                contain.put(node.id, nbelt);
                node = node.back;
            }

            node = beltFirst[belt].back;

            node.front = beltLast[nbelt].front;
            node.front.back = node;
            beltLast[nbelt].front = beltLast[belt].front;
            beltLast[belt].front.back = beltLast[nbelt];


            beltFirst[belt] = null;
            beltLast[belt] = null;
            sb.append((belt+1)+"\n");
        }

    }

    public static void addPresent(int m, Node n){

        n.front = beltLast[m].front;
        n.front.back = n;
        n.back = beltLast[m];
        beltLast[m].front = n;
        contain.put(n.id, m);

    }

    public static void remove(Node n){
        n.front.back = n.back;
        n.back.front = n.front;
    }

}