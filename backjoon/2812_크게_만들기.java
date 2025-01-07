import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        char[] input = br.readLine().toCharArray();
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < N; ++i) {

            int val = input[i] - '0';

            while(!stack.isEmpty() && stack.peekLast() < val && K>0){
                stack.pollLast();
                K--;
            }

            stack.addLast(val);
        }

        // 남은 K 숫자 만큼 스택 내용 제거하기
        while(K-->0){
            stack.pollLast();
        }

        StringBuilder sb = new StringBuilder();

        while(!stack.isEmpty()){
            sb.append(stack.pollFirst());
        }

        System.out.print(sb.toString());

    }

}

/*
 * 2812 크게 만들기
 *
 * 스택 + 그리디 문제
 *
 * 문제의 핵심 로직은 다음과 같습니다.
 * 1. 숫자를 스택에 넣는다.
 * 2. 다음 숫자 x가 현재 스택의 peek 값보다 크면 스택의 peek 값이 x 이상의 값이 나올 때 까지 스택을 pop하고 숫자를 넣어준다.
 * 3. 2번의 과정은 최대 K번 까지만 수행할 수 있다.
 *
 * 위의 로직대로 풀이하면 그리디하게 가장 큰 숫자로만 구성된 스택이 완성됩니다.
 * 그후, 스택의 원소들을 앞에서부터 뽑아와야하는데, 저는 Deque을 사용하여 Queue와 같이 앞의 요소들을 순서대로 뽑았습니다.
 *
 * 문제에서 주의점은 K번 반드시 빼야한다는 것 입니다.
 *
 * 만일 입력이 다음과 같다면
 * 5 3
 * 98765
 *
 * 정답은 98765가 아니라 98이어야합니다.
 * 핵심 로직만 수행할 경우, 98765가 나오게됩니다. 이는 2번 로직 때문에 자기보다 이하의 숫자는 스택에 반드시 넣기 때문입니다.
 *
 * 하지만 반대로 생각해보면, 스택의 m의 위치에 들어있는 숫자보다 뒤에 있는 숫자들은 m보다 작거나 같다는 뜻입니다.
 * 즉, K가 만약 남아있다면 스택의 뒷부분의 숫자들을 남은 K의 횟수만큼 제거해주면 됩니다.
 *
 *
 * */