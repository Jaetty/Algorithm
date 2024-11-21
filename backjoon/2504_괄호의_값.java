import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{


    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        char[] stack = new char[31];
        int[] tmp = new int[31];
        char val = 'A';

        int answer = 0;
        int idx = 0;

        for(int i=0; i<input.length(); i++){

            char ch = input.charAt(i);

            if(ch==')' || ch==']'){

                if(val != 'A'){
                    if(ch==')' && val=='('){
                        tmp[idx] += 2;
                    }
                    else if (ch==']' && val == '['){
                        tmp[idx] += 3;
                    }
                    else{
                        System.out.print(0);
                        return;
                    }
                    val = 'A';

                    if(idx==0){
                        answer += tmp[idx];
                        tmp[idx] = 0;
                    }
                    continue;
                }

                if(idx == 0 || (stack[idx-1] == '(' && ch == ']') || (stack[idx-1] == '[' && ch == ')') ){
                    System.out.println(0);
                    return;
                }

                idx--;

                if(ch==')'){
                    tmp[idx] += 2 * tmp[idx+1];
                }
                else{
                    tmp[idx] += 3 * tmp[idx+1];
                }

                tmp[idx+1] = 0;

                if(idx==0){
                    answer += tmp[idx];
                    tmp[idx] = 0;
                }

            }
            else{
                if(val != 'A'){
                    stack[idx++] = val;
                }
                val = ch;
            }
        }

        if(idx>0) System.out.print(0);
        else System.out.print(answer);

    }
}

/*
 * 스택 문제
 *
 * 해당 문제는 괄호만 보면 전형적인 스택 문제입니다.
 * 다만 이 문제에서 고려해야할 부분은 더하기와 곱하기 부분의 구현입니다.
 *
 * 괄호의 여닫이는 여타 스택문제가 그렇듯 맨 마지막 스택의 열림과 들어온 닫힘이 다르면 틀렸다로 구현하면 됩니다.
 *
 * 점수를 계산 하는 방법은 재귀로 푸는 방법도 있겠지만 저는 그냥 직접 구현했습니다.
 * 방법은 다음과 같습니다.
 * 먼저 이렇게 생각해보겠습니다. 괄호가 ((())) 처럼 있을 때 저는 맨 왼쪽에 열린 괄호를 0레벨, 다음 괄호를 1레벨 그 다음을 2레벨로 봤습니다.
 * 0( 1 ( 2( )2 ) 1 )0 즉 이렇게 본겁니다. 그러면 같은 레벨에서 이루어진 여닫이는 더하기가 이루어져야하고, 나보다 높은 레벨의 여닫이에는 지금까지 구한 합에다가 곱을 해야합니다.
 * 예를 들어 2 레벨에서 수 많은 더하기가 이루어지고 나서, 1레벨로 나왔다면 2레벨에서 더해진 모든 값에 *2 or *3을 해줘야합니다.
 * 저는 이 방식을 위해 val 이라는 변수를 만들어서 이 val 값은 현재 위치한 가장 높은 레벨에서의 문자를 나타내도록 했습니다.
 * 또한 어떤 레벨에서의 총 합은 tmp[] 배열로 만들어서 각 레벨마다 총합을 구하도록 했습니다.
 *
 * 즉,
 * 1. val에 가장 높은 레벨 값에 들어온 문자를 저장한다.
 * 2. val만으로 여닫이가 해결된다면 tmp[가장 높은 레벨]에 합을 누적한다.
 * 3. 만약 val에 문자가 없는데 닫힘 문자가 들어오면 tmp[레벨-1] += tmp[레벨] * '2 or 3'를 해주고 tmp[레벨] = 0 으로 초기화한다.
 * 4. 이를 반복해서 tmp의 레벨이 0에 도달했다면 answer 변수에 더해준다.
 *
 * 설명이 조금 어렵습니다만.. 이런 구조로 아이디어를 냈고 이를 바탕으로 구현하여 풀었습니다.
 *
 * */