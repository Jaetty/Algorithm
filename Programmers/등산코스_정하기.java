import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Solution {

    public String solution(String number, int k) {

        char[] stack = new char[number.length() - k];
        int index = 0;
        stack[0] = number.charAt(0);

        for(int i=1; i<number.length(); i++){

            char val = number.charAt(i);

            if(k>0){
                while(index>=0 && k > 0){
                    if(stack[index]>=val) break;
                    else{
                        index--;
                        k--;
                    }
                }
                if(index +1 < number.length() - k){
                    stack[++index] = val;
                }
                if(k==0) index++;

            }
            else{
                stack[index++] = val;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<stack.length; i++){
            sb.append(stack[i]);
        }

        return sb.toString();
    }

}

/*
* 그리디 알고리즘 문제
* 해당 문제는 스택과 그리디를 사용한 문제로 풀이가 가능합니다.
*
* 그리디한 포인트는 어떤 값을 찾아서 줄일까로 접근하는 것이 아니라 스택에 가장 큰 값을 만들어보자로 접근하는 것 입니다.
* 로직을 설명하면 number값을 탐색하며 스택에 값을 넣어주는데 만약 k값이 0 보다 크고 스택에 맨 위에 들어있는 값이 나보다 작으면 그 값들을 빼면서 k값을 줄여주는 것 입니다.
* 그래서 스택의 맨 위 값이 현재 탐색한 number 값 이상이라면 더이상 빼지 말고 현재 number 값을 넣어줍니다.
* 만약 k가 0 이하라면 그 이후로는 스택에 그대로 값을 넣어주는 것 입니다.
*
* 다음 예시로 설명해보겠습니다..
* number = 1231234 k = 3
*
* 그렇다면 스택에 넣어지는 순서는 다음과 같습니다.
* 맨 처음 k = 3이고 스택이 비어있습니다. [비었음]
* 그러므로 스택에 number의 첫 값인 1을 넣어줍니다.
* k = 3, [1]
* number의 두 번째 값인 2가 들어왔습니다. 2는 1보다 크므로 스택에 맨 위의 값인 1을 빼주게 되고 그 밑은 없기 때문에 값이 2로 바뀌고 k = 2가 됩니다.
* k = 2, [2]
* number의 값으로 3이 들어옵니다. 3은 2보다 크므로 2를 빼주고 k값을 하나 낮춥니다. 이후 스택이 비었으므로 3을 넣어줍니다.
* k = 1, [3]
* 다음 값으로 1이 들어옵니다. 1은 3보다 작으므로 그대로 스택에 넣어줍니다.
* k = 1, [3,1]
* 다음으로 2가 들어옵니다.
* 2는 1보다 크므로 스택에서 1을 빼주고 k에도 1을 빼줍니다.
* k = 0, [3] 현재 값은 2
* k가 0이므로 이후에는 그대로 넣어줍니다. [3, 2, 3, 4]
*
* 이와 같이 3234가 가장 만들 수 있는 큰 값으로 이를 return하면 문제를 풀 수 있습니다.
* */