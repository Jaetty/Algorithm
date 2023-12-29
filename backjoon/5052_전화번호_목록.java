import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{

        Node[] child;
        // 완성된 전화번호인지를 나타내는 boolean
        boolean complete;

        Node(){
            child = new Node[10];
        }


    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int t = Integer.parseInt(br.readLine());

        for(int tc=0; tc<t; tc++){

            int n = Integer.parseInt(br.readLine());
            boolean yesOrNo = false;

            Node tree = new Node();

            for(int i=0; i<n; i++){

                char[] input = br.readLine().toCharArray();
                if(yesOrNo) continue;

                Node node = tree;
                boolean flag = false;

                for(int j=0; j<input.length; j++){

                    int val = input[j]-'0';

                    if(node.complete){
                        flag = true;
                        break;
                    }

                    if(node.child[val]==null){
                        node.child[val] = new Node();
                    }
                    node = node.child[val];
                }

                if(flag){
                    yesOrNo = true;
                }
                else if(i==n-1){
                    for(int k=0; k<10; k++){
                         if(node.child[k]!=null){
                             yesOrNo = true;
                         }
                     }

                }

                node.complete = true;

            }

            sb.append( !yesOrNo ? "YES" : "NO" ).append("\n");

        }

        System.out.print(sb.toString());

    }

}

/*
* 트라이 문제
*
* 해당 문제의 경우 트리의 개념 혹은 트라이의 개념을 이해하면 풀이할 수 있는 문제입니다.
*
* 트라이란 트리의 일종으로 문자열을 검색, 삽입에 유용한 자료 구조입니다.
* 쉬운 설명으로는 루트에서부터 한 문자씩 노드를 만들어 분기하게 만들어줍니다.
* 예를 들어 해당 문제에서 911 이라는 문자열을 트라이로 나타내게 되면
* root 밑에 9가 있고 9 밑에 1이 있고 1밑에 1이 있어서 다음과 같은 형태입니다.
*
*               root
*                   \
*                    9
*                   /
*                  1
*                 /
*                1
*
* 그러면 이를 배열로 나타내면 다음과 같을 것입니다.
*
*  root    0[null] 1[null] 2[null] ... 9[new node]
*  9번 노드 0[null] 1[new node] 2[null]... 9[null]
*  1번 노드 0[null] 1[new node] 2[null]
*
* 이후 911을 포함하여 새로운 문자를 추가하고 싶다면 911까지 탐색 후 그 밑에 새로운 문자를 추가하면 되기 때문에 입력된 문자열의 검색과 문자 추가가 용이한 자료구조입니다.
*
* 구체적인 설명은 아래의 위키를 참조하시면 이미지와 함께 쉽게 이해하게 설명되어있습니다.
* https://en.wikipedia.org/wiki/Trie
*
* 해당 문제를 트라이로 풀이할 경우 생각해야하는 포인트는 2가지 입니다.
* 1. 전화번호의 끝을 인지해야한다.
* 2. 마지막 입력이 다른 전화번호의 접두사가 될 수 있다.
*
* 1번 포인트를 고려해야하는 이유는 누군가의 전화번호가 다른 누군가의 전화번호의 접두사가 되면 안되는 것을 정확히 해야하기 때문입니다.
* 예를 들어 다음 입력이 있다면
* 1
* 3
* 12340
* 12345
* 다음 입력 대기중
*
* 12340과 12345가 1234를 공유하더라도 서로가 마지막 0과 5로 번호가 다른것을 알 수 있습니다.
* 즉 다음에 오는 번호는 1234는 공유해도 되지만 12340이나 12345를 포함하면 안된다를 정확히 기억해야합니다.
*
* 이 부분을 해결하기 위해 트라이에서 마지막 입력이 이루어진 부분에 boolean 값으로 complete라는 변수를 두어 완성된 번호인지를 명확히 구분하게 만들어줍니다.
*
* 2번 포인트는 예시 입력이 다음과 같다면
* 1
* 3
* 12340
* 12345
* 1234
*
* 마지막에 모든 번호의 접두사가 되는 1234가 나올 수 있습니다. 그렇다고 모든 전화번호 경우 마다 모든 전화번호를 탐색해주면 시간이 낭비될 것입니다.
* 이를 해결하는 방법은 마지막 번호를 특별하게 처리해주는 방법이나(저는 이 방법을 택했습니다.), 미리 입력을 정렬을 통해 길이가 짧고 사전 순으로 문자를 정렬하여 맨 앞부터 차례대로 처리하면 됩니다.
*
* 이 두 가지 부분만 유의해서 트라이를 구현하면 문제를 해결할 수 있습니다.
*
* */