import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static String[] inputs;
    static int[] change;
    static boolean[] visited;
    static List<Integer> list;
    static int N;
    static long answer,sum;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        change = new int[26];
        inputs = new String[N];
        visited = new boolean[10];

        list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();

        for(int i=0; i<N; i++){
            inputs[i] = br.readLine();
            for(int j=0; j<inputs[i].length(); j++){
                set.add( (int) (inputs[i].charAt(j)-'A') );
            }
        }

        Iterator it = set.iterator();
        while(it.hasNext()){
            list.add((int)it.next());
        }

        recursive(list.size()-1);

        System.out.println(answer);

    }

    static void recursive(int index){

        if(index<0){

            sum = 0;
            StringBuilder sb;

            for(int i=0; i<N; i++){
                sb = new StringBuilder();
                for(int j=0; j<inputs[i].length(); j++){
                    sb.append( change[(inputs[i].charAt(j)-'A')] );
                }
                sum += Integer.parseInt(sb.toString());
            }
            answer = Math.max(sum, answer);

            return;
        }

        for(int i=0; i<10; i++){
            if(visited[i]) continue;
            visited[i] = true;
            change[list.get(index)] = i;
            recursive(index-1);
            visited[i] = false;
        }

    }


}


/*
 * 백트래킹 문제 (문제 풀고보니 그리디 알고리즘 문제였습니다. 해당 풀이는 따로 다시 올리도록 하겠습니다.)
 *
 * 해당 문제의 로직은 다음과 같습니다.
 * 1. 최대 10자리 조합을 이용해서 ABCDE 알파벳의 값을 정해줍니다.
 * 2. 대문자 알파벳을 나타낼 수 있는 26 크기의 배열을 만들고 이 배열안에 1번에서 구한 ABCD의 값을 기억해둡니다.
 * 3. 문자열을 하나씩 읽으면서 각각의 문자에 해당하는 숫자 값으로 문자열을 변환하여 모두 더하여 기존 정답 값보다 크면 변경해줍니다.
 * 4. 위 과정을 통해서 가장 큰 결과 값을 출력합니다.
 *
 * 사실 문제를 보고 백트래킹 로직을 쉽게 떠올릴 수 있기 때문에 로직의 설명보다는 java에서 어떻게 속도를 개선했는가로 설명하겠습니다.
 * 해당 문제를 보자마자 백트래킹으로 충분히 통과할 것이라고 생각했던 점은 바로 최대 10가지 조합이면 제한 시간인 2초안에는 출력이 가능하다고 판단됐기 때문입니다.
 * 다만 이를 위해서 로직의 1,2,3 과정에서 특정 작업을 통해 속도를 개선해주셔야합니다.
 *
 * 1. 입력된 알파벳을 정확히 기억함으로써 최대 10번의 조합만 가능하게 만들어줍니다. 즉 입력되지 않은 알파벳까지 고려해버려서 조합으로 경우의 수를 만들지 않도록 해야합니다.
 *   이를 위하여 한 방법은 set을 통해 정확히 입력된 알파벳(정확히는 A=0, B=1과 같이 0~25까지의 숫자로)만 추출 후 List에 넣어 기억하게 만들었습니다.
 *
 * 2. 알파벳의 값을 정했다면 이를 쉽게 찾을 수 있도록 26자리 배열을 만들어 정해진 값을 기억하게 만들었습니다.
 *   예로 조합 중에 ABG의 값이 각각 987로 정해졌다면 change 배열은 {9,8,0,0,0,0,7,0,0,0....,0} 이렇게 되고
 *   나중에 문자 하나하나 조회 할 때 change['해당 문자'-'A'] 를 통하여 그 문자에 해당하는 숫자 값을 빠르게 읽을 수 있습니다.
 *
 * 3. 기존 알파벳 문자열에서 문자를 하나하나 StringBuilder를 통해 String으로 변환 후 숫자로 변환해줍니다.
 *   이 과정을 하는 이유는 String은 불변성을 가지고 있기 때문입니다. String을 통해 String a = "string"; a = a+"anotherString" 의 경우 속도가 느립니다.
 *   String은 문자열을 합치는 기능이 자체적으로는 없습니다. 그래서 내부적으로는 새로운 문자열 객체를 만들고 StringBuilder가 문자열을 서로 합한 결과를 변수가 가르키게합니다.
 *   즉 내부에서 이러한 과정이 이루어지다보니 속도가 지체됩니다. 그러므로 StringBuilder를 이용하여 문자를 숫자로 바꿔줘야합니다.
 *
 * 이상 3가지 경우를 신경써주어 로직을 코드로 구현하면 해당 문제를 풀이할 수 있었습니다.
 *
 * */