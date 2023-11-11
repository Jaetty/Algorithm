import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static String T,S;
    static boolean reverse;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        S = br.readLine();
        T = br.readLine();

        Deque<Character> deque = new ArrayDeque<>();

        for(int i=0; i<T.length(); i++){
            deque.add(T.charAt(i));
        }

        while(deque.size()>S.length()){
            char var;
            if(reverse) var = deque.poll();
            else var = deque.pollLast();

            if(var=='B'){
                reverse = !reverse;
            }
        }

        StringBuilder sb = new StringBuilder();
        while(!deque.isEmpty()){
            if(reverse) sb.append(deque.pollLast());
            else sb.append(deque.poll());
        }

        if(S.equals(sb.toString())) System.out.println(1);
        else System.out.println(0);


    }


}


/*
* 문자열 문제
*
* 해당 문제의 로직은 다음과 같습니다.
* 입력 S를 입력 T로 만들 수 있는가라는 문제는 반대로 생각하면 T가 반대로 진행하여 S가 되는지 확인하면 됩니다.
* 예제 입력으로
* S = B
* T = ABBA
* 와 같은 경우
* ABBA를 반대로 되돌리는 과정을 하나하나 밟아보면
* 1. ABBA (최초 상태)
* 2. ABB
* 3. BA
* 4. B
* 로 B가 되는 것을 확인할 수 있습니다.
*
* 이러한 로직을 저는 Deque을 이용하여 최초에 문자열의 맨 마지막을 가르키고 있는 상태에서 B를 만나면 앞 뒤의 순서를 바꿔주는 기능을 수행하여 코드로 구현하여 풀이하였습니다.
*
* */