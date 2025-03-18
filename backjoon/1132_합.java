import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{
        char c;
        long score;
        boolean not_zero;

        Node(char c, long score){
            this.c = c;
            this.score = score;
            this.not_zero = false;
        }
    }

    static Map<Character, Integer> c_to_num;
    static Map<Integer, Node> num_to_c;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        c_to_num = new HashMap<>();
        num_to_c = new HashMap<>();
        List<Node> nodes = new ArrayList<>();
        String[] inputs = new String[N];

        for(int i=0; i<=9; i++){
            nodes.add(new Node( (char) ('A'+i), 0));
        }

        for(int i=0; i<N; i++){
            inputs[i] = br.readLine();
            int base = inputs[i].length();
            for(int j=0; j<inputs[i].length(); j++){
                nodes.get(inputs[i].charAt(j) - 'A').score += Math.pow(10, base-j);
                if(j==0){
                    nodes.get(inputs[i].charAt(j) - 'A').not_zero = true;
                }
            }
        }

        Collections.sort(nodes, (e1,e2)-> Long.compare(e2.score, e1.score));

        for(int i=0; i<=9; i++){
            Node node = nodes.get(i);
            c_to_num.put(node.c, 9-i);
            num_to_c.put(9-i, node);
        }

        if(num_to_c.containsKey(0) && num_to_c.get(0).not_zero){
            reorder(0, num_to_c.get(0));
        }

        long answer = 0;

        for(String input : inputs){

            int base = input.length()-1;
            for(int j=0; j<input.length(); j++){
                answer += (long) Math.pow(10, base-j) * c_to_num.get(input.charAt(j));
            }

        }

        System.out.print(answer);

    }

    static void reorder(int rank, Node node){

        if( num_to_c.get(rank+1).not_zero ){
            reorder(rank+1, num_to_c.get(rank+1));
        }

        c_to_num.put(num_to_c.get(rank+1).c, rank);
        c_to_num.put(node.c, rank+1);
        num_to_c.put(rank, num_to_c.get(rank+1));
        num_to_c.put(rank+1, node);

    }
}

/*
 * 1132 합
 *
 * 그리디 문제
 *
 * 문제의 핵심 포인트는 다음과 같습니다.
 * 1. 각 자릿수에 해당하는 가중치를 주고 이를 이용하여 9점부터 1점까지 부여한다.
 * 2. 만약 0에 해당하는 값이 0이 될 수 없다면, 0이 될 수 있는 값을 찾을 때까지 한칸씩 점수를 올려준다.
 *
 * 해당 문제의 경우 모든 경우의 수를 다 시도하려고 한다면,
 * 문자 10개 순서 재배치 경우의 수(10!) * 입력으로 받은 값들로 합산하는 경우의 수(50*9) = 시간 초과라는 점을 짐작할 수 있습니다.
 *
 * 사실, 문제를 보자마자 단순히 생각해보면 이렇게 아이디어가 금방 떠오를 겁니다.
 * "그냥 가장 앞에 있는 값을 9부터 8,7,6... 순으로 만들면 되지 않나?" <= 이게 풀이의 핵심입니다.
 *
 * 이렇게 문제를 접근하면, 그럼 "어떻게 해야 정확한 기준을 세우지?" 라는 생각이 들텐데 간단합니다.
 * 핵심은 각각 위치마다 가중치를 주고, 이 가중치의 합산이 가장 높은 순으로 9,8,7...1까지의 순서를 갖게 됩니다.
 *
 * 저의 경우는 10^(현재 자릿수 위치) 점수를 줬습니다.
 * 이때, 10의 제곱으로 한 이유는 자릿수 간에 서로 완전히 다른 점수를 주기 위함입니다. 만약 같은 1의 자릿수 점수를 주면 점수가 겹쳐버립니다.
 * 서로 아예 겹치지 않도록 10배 이상의 점수차가 나도록 점수를 주고,
 * 만약 AB, CB, CB, CB, .... CB(11번째)와 같은 입력에 대해서는 A가 B보다 먼저 나온적이 있음에도 가중치 합이 B가 더 높으므로 B가 2등으로 8이 됩니다.
 *
 * 그 다음, 고려할 부분이 0이 될 수 없는 경우입니다.
 * 문제에서 0으로 시작하는 수는 없고, 어떠한 경우라도 단 하나의 문자는 0이 될 수 있다고 표기해놨습니다.
 *
 * 그러면 다음과 같은 예시로 보자면, 여기선 0을 4라고 생각하면 됩니다.
 * 5
 * ABCDEF
 * F
 * E
 * D
 * C
 *
 * F,E,D,C는 0이 될 수 없지만, 가중치 점수를 보면 그들 중 하나는 0이 됩니다. 그러면 여기선 ABCDEF가 번호 순서(987654)라고 한다면.
 * F는 0이 될 수 없으므로 바로 앞의 값과 교환해줍니다. 그런데 바로 앞에 값이 E이고 E는 0이 될 수 없으므로 다음 값과 자리를 바꾸고,
 * D, C에도 똑같이 적용하게되면, B와 C가 자리를 바꿀 수 있게 됩니다.
 * 그러면 ACBDEF -> ACDBEF -> ACDEBF -> ACDEFB 이렇게 B를 0의 자리까지 내려주면 해결됩니다.
 *
 * 이렇게 2가지 포인트를 유념해서 구현해주면 문제를 풀이할 수 있습니다.
 *
 *
 *
  */