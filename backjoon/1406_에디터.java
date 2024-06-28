import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node{

        Character value;
        Node before;
        Node next;

    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        LinkedList<Character> list = new LinkedList<>();

        Node node = new Node();
        Node start = new Node();

        for(int i=0; i<input.length(); i++){
            node.value = input.charAt(i);
            node.next = new Node();
            node.next.before = node;
            node = node.next;
        }

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());

            char first = st.nextToken().charAt(0);

            switch(first){

                case 'L' :
                    if(node.before != null) node = node.before;
                    break;
                case 'D':
                    if(node.next != null) node = node.next;
                    break;
                case 'P' :
                    Node temp = new Node();
                    temp.value = st.nextToken().charAt(0);
                    temp.before = node.before;
                    temp.next = node;

                    if(node.before != null) node.before.next = temp;
                    node.before = temp;
                    break;
                case  'B':
                    if(node.before==null){
                        break;
                    }
                    node = node.before;

                    if(node.before==null){
                        node.next.before = null;
                    }
                    else{
                        node.before.next = node.next;
                        node.next.before = node.before;
                    }
                    node = node.next;
                    break;
            }

        }

        StringBuilder sb = new StringBuilder();

        while(node.before!=null){
            node = node.before;
        }

        while(node.next != null){
            sb.append(node.value);
            node = node.next;
        }
        System.out.println(sb.toString());

    }

}

/*
 * 링키드 리스트 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 처음 문자의 최대 길이(N)는 100,000이고, 문자 추가를 통해 최대 길이 600,000 문자열이 될 수 있다.
 * 2. 커서는 움직일 수 있고 커서의 위치에서 삭제, 추가가 가능해야한다.
 * 3. 자바 기준 시간 제한은 => Java 8 (OpenJDK): 2 초 이다.
 *
 * 포인트들을 통해 알 수 있는 점은 링키드(연결) 리스트를 사용해야 한다는 점입니다.
 * 길이 600000의 문자열을 단순 리스트, 배열로 관리하려면 매우 시간이 많이 들고 복잡한 작업이 필요하게 됩니다.
 * 그러므로 어느 특정 지점에서 삽입 삭제가 편리한 링키드 리스트를 선택하면 됩니다.
 *
 * 여기서 중요한 점은 링키드 리스트를 직접 구현해야하는 점 입니다.
 * 라이브러리에서 제공되는 링키드 리스트는 커서의 위치를 기억하지 못합니다.
 * 이 리스트는 내부적으로 index의 위치까지 처음부터 끝까지 리스트를 타서 도달 후 작업을 수행하므로 모든 명령에 대해 O(N)이 걸리게 됩니다.
 * 즉 최악의 경우 O(N*M)인 O(100,000 * 500,000)의 시간이 필요하게 됩니다.
 *
 * 그래서 현재 커서의 위치를 기억할 수 있도록 링키드 리스트를 직접 구현한 후 앞 뒤의 리스트들을 조건에 맞게 이어주는 코드를 구현하면 문제를 해결할 수 있습니다.
 *
 * */