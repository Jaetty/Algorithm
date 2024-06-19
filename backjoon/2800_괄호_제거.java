import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static Set<String> check;
    static List<String> answer;
    static List<int[]> list;
    static String input;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        input = br.readLine();

        check = new HashSet<>();
        answer = new ArrayList<>();
        list = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        for(int i=0; i<input.length(); i++){

            if(input.charAt(i)=='('){
                stack.add(i);
            }
            else if(input.charAt(i)==')'){
                int val = stack.pop();
                list.add(new int[]{val, i});
            }
        }

        for(int i = 1; i <= list.size(); i++){
            recursive(0, i, new HashSet<>(), 0);
        }

        Collections.sort(answer);
        StringBuilder sb = new StringBuilder();

        for(int i=0; i< answer.size(); i++){
            sb.append(answer.get(i)+"\n");
        }
        System.out.println(sb.toString());

    }

    static void recursive(int depth, int max, Set<Integer> chosen, int index){

        if(depth >= max){
            
            StringBuilder temp = new StringBuilder();
            
            for(int i=0; i < input.length(); i++) {

                if(chosen.contains(i)) continue;
                temp.append(input.charAt(i));

            }
            String result = temp.toString();

            if(!check.contains(result)){
                check.add(result);
                answer.add(result);
            }
            return;
        }

        for(int i=index; i< list.size(); i++){
            chosen.add(list.get(i)[0]);
            chosen.add(list.get(i)[1]);
            recursive(depth+1, max, chosen, i+1);
            chosen.remove(list.get(i)[0]);
            chosen.remove(list.get(i)[1]);
        }

    }

}

/*
 * 문자열 + 브루트포스 문제
 *
 * 해당 문제는 근본적으로 괄호의 위치를 기억해두고 각각의 괄호를 제거하여 '괄호 한 쌍이 제거되는 모든 경우'의 수식을 찾으면 됩니다.
 * 다만 아래의 포인트를 고려해합니다.
 * 1. 입력은 올바르게 주어진다. (괄호가 짝이 안맞는 경우는 없다.)
 * 2. 괄호를 제거하여 서로 다른 식을 판별해야한다. (중복된 수식이 나오면 안된다.)
 * 3. 수식은 오름차순으로 출력한다.
 *
 * 포인트로 알 수 있는 점은 아래와 같습니다.
 * 1 -> 입력에 전처리를 할 필요가 없다.
 * 2 -> 문자열을 만들고 중복을 판단해야한다.
 * 3 -> 문자열 리스트를 마지막에 정렬하면 된다.
 *
 * 2번의 경우 여러가지 방법이 있겠지만 저는 set을 이용하여 다음과 같이 풀이하였습니다.
 * 1. 재귀 함수를 통해 수식을 만든다.
 * 2. set을 이용하여 중복을 판별한다.
 * 2-1. 중복되지 않았다면 list에 저장한다.
 * 3. list를 오름차순으로 정렬한다.
 *
 * 위와 같이 해결방법을 생각한 후 이를 코드로 나타내어 문제를 해결하였습니다.
 *
 *
 * */