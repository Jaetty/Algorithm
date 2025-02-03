import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{

        char c;

        Node prev, next;

        Node(char c){
            this.c = c;
            this.next = null;
            this.prev = null;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for(int tc=0; tc<T; tc++){

            String str = br.readLine();

            Node first = new Node('@');

            Node curr = first;
            char val;

            for(int i=0; i<str.length(); i++){

                val = str.charAt(i);

                if(val == '<'){

                    if(curr.prev != null){
                        curr = curr.prev;
                    }

                }
                else if(val == '>'){
                    if(curr.next != null){
                        curr = curr.next;
                    }
                }
                else if(val == '-'){

                    if(curr.c == '@'){
                        continue;
                    }

                    if(curr.next != null){
                        curr.next.prev = curr.prev;
                    }

                    curr.prev.next = curr.next;
                    curr = curr.prev;

                }
                else{
                    Node node = new Node(val);

                    node.prev = curr;
                    node.next = curr.next;

                    if(curr.next != null){
                        curr.next.prev = node;
                    }

                    curr.next = node;
                    curr = curr.next;
                }
            }

            curr = first.next;


            while(curr != null){
                sb.append(curr.c);
                curr = curr.next;
            }

            sb.append("\n");

        }

        System.out.print(sb.toString());

    }

}

/*
 * 5397 키로거
 *
 * 링키드 리스트 문제
 *
 * 이 문제는 포인트는 다음과 같습니다.
 * 1. 입력되는 문자열의 길이는 L <= 1000000
 * 2. < > 로 커서가 움직이고 -로 지우기가 가능하다. 마지막 문자가 아니라면 한칸씩 땡겨와야한다.
 *
 * 이 문제의 핵심은 링키드 리스트를 구현할 수 있는가 입니다.
 * 만약 배열 등으로 풀이하자면 땡겨오는 과정에서 이때까지 입력 받은 길이 size를 최악의 경우 size-1만큼 반복해야합니다.
 *
 * 그러면 만약 저희가 입력 받은 값이 500만인데 첫번째 문자로 커서를 움직여서 하나씩 지우게 되면 O(499만 + 498만 + .. 1)을 반복하게 되므로 비효율적입니다.
 * 이때 가장 좋은 방법이 링키드 리스트입니다. 연결리스트의 삭제는 뒤와 앞을 연결해주므로 자연스럽게 한칸씩 땡겨오게 만들 수 있습니다.
 * 또한, 현재의 위치도 기억해두므로써 << >> 등의 커서 움직임도
 *
  */