import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String str = br.readLine();

        Set<Character> key = new HashSet<>();

        Stack<Character> operator = new Stack<>();

        key.add('+');
        key.add('-');
        key.add('*');
        key.add('/');
        key.add('(');
        key.add(')');

        for(int i=0; i<str.length(); i++){

            char temp = str.charAt(i);

            if(!operator.isEmpty() && key.contains(temp)){

                if(temp==')'){

                    while(true){
                        if(operator.peek()=='(') break;
                        sb.append(operator.pop());
                    }
                    operator.pop();

                }else{

                    if(temp == '+' || temp == '-'){
                        while (!operator.isEmpty() && (operator.peek() != '(')){
                            sb.append(operator.pop());
                        }
                    }
                    else if(temp=='*' || temp=='/'){
                        while (!operator.isEmpty() &&  (operator.peek() == '*' || operator.peek() == '/')){
                            sb.append(operator.pop());
                        }
                    }
                    operator.push(temp);
                }

            }
            else if(key.contains(temp)){
                operator.push(temp);
            }
            else{
                sb.append(str.charAt(i));
            }
        }

        while(!operator.isEmpty()){
            sb.append(operator.pop());
        }

        System.out.println(sb);

    }

}
/*
* 스택을 이용한 문제
* 문제를 풀기 위한 규칙성을 생각해보니 다음과 같았다.
* 괄호도 연산자로 취급하고 연산자가 아닌 문자가 나올 때는 그대로 출력을 해주고 연산자는 스택에 넣는다.
* 스택에 값이 들어간 상태에서 연산자 조회되면 우선순위를 따져주어 스택에 맨 위에 우선순위가 낮아질 때까지 출력해주는 방법을 사용하였다.
* 여는 괄호 '('를 만나면 무조건 스택에 넣어준다.
* 그리고 닫는 괄호 ')'이 나오면 '('이 나온 곳 까지 출력하게 해준다.
*
* 말로만 듣자면 어려우니 예시로 설명하겠다.
* A+B*(C-D)/E 라는 입력이 주어졌다고 해보자 반복문을 통해 이 문자열을 문자를 하나하나 탐색할 것이고 다음과 같이 진행된다.
*
* 1. 연산자가 아니기 때문에 'A'를 출력한다.                        A
* 2. 연산자인 '+'를 만났으니 '+'를 스택에 PUSH한다.                A
* 3. 'B'를 출력한다.                                           AB
* 4. '*'를 만났고 스택 안에 '+'와 우선순위를 비교한다. '+'가 더 낮으니 스택에서 POP하지 못하고 '*'를 스택에 PUSH준다.     AB
* 5. 연산자 여는 괄호 '('를 만났다. 이 경우엔 그냥 PUSH 한다.                  AB
* 6. C를 출력한다.                                                        ABC
* 7. '-'를 만났으나 스택 가장 위가 '(' 이므로 '-' 를 스택에 넣어준다.           ABC
* 8. D를 출력한다.                                                   ABCD
* 9. 닫는 괄호 ')'를 만났으니 '(' 가 POP 될 때 까지 POP 해준다.             ABCD-
* 10. '/'를 만났고 스택의 맨 위는 '*'이다 둘의 우선순위가 같으니 '*'을 출력한다.       ABCD-*
* 11. 이제 스택의 맨 위는 '+'이다. 우선순위가 낮으니 '/'를 스택에 PUSH한다.           ABCD-*
* 12. E를 출력한다.                                                            ABCD-*E
* 13. 이제 문자열 탐색이 끝났으니 스택에 남아 있는 값을 모두 POP 한다.                 ABCD-*E/+
*
* 프로그램은 위와 같이 진행되며 이를 구현하면 문제를 풀 수 있다.
*
* */