import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int[] input, answer, count;

        int N = Integer.parseInt(br.readLine());

        input = new int[N];
        answer = new int[N];
        count = new int[1000001];

        Stack<Integer> stack = new Stack<>();
        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            input[i] = Integer.parseInt(st.nextToken());
            answer[i] = -1;
            count[input[i]]++;
        }

        stack.push(0);

        for(int i=1; i<N; i++){

            while(!stack.isEmpty() && count[input[stack.peek()]] < count[input[i]]){
                int idx = stack.pop();
                answer[idx] = input[i];
            }
            stack.push(i);
        }

        for(int i=0; i<N; i++){
            sb.append(answer[i]+" ");
        }

        System.out.print(sb.toString());

    }
}

/*
 * 스택 문제
 *
 * 해당 문제의 경우 포인트는 다음과 같습니다.
 * 1. N (1 ≤ N ≤ 1,000,000)
 * 2. 나보다 오른쪽에 있고 '가장 먼저' F(Ai) 값이 큰 숫자 찾기
 *
 * 문제를 보면 우선적으로 F(Ai) 값을 정해주기, 즉 count[]을 만들어서 어떤 Ai값이 몇번 나왔는지 기억하는 변수를 만들어줘야하는 점은 알 수 있습니다.
 * 또한, 입력의 크기가 100만입니다. 절대 N^2의 방식인 브루트포스로는 풀이가 불가능한 점을 쉽게 눈치챌 수 있습니다.
 * 그러면 적어도 O(N Log N) 혹은 O(N)에 해결할 방법을 찾아야 합니다.
 *
 * 여기서 주목해야하는 점이 2번 포인트, 오른쪽에서 가장 먼저 F(Ai) 값이 큰 숫자를 찾는 것 입니다.
 * 저는 이렇게 생각해봤는데, 어떤 x 값을 최초로 만나서 F(x)보다 큰 값을 찾고 있다고 해보겠습니다.
 * 그런데 다음으로 만난 y 값은 F(x)보다 작다고 하겠습니다. 그러면 당연히 F(x)보다 큰 값은 F(y)보다도 커야합니다.
 *
 * 즉 스택에 F(x)보다 작았던 값들들 계속 넣어줍니다. 그러다가 스택에 맨 위에 있는 값보다 큰 값이 나오면 스택에 들어있는 값이 클 때까지 반복해서 스택의 내용을 비워줍니다.
 * 계산에 N번의 반복만 필요하게 되고, 입력, 출력의 과정까지 합쳐 총 3N 즉 O(N)만큼의 시간으로 문제를 해결할 수 있습니다.
 *
 * @ 문제의 핵심을 쉽게 예시를 들면 다음과 같은 숫자 배열이 있을 때
 * 4 3 2 1 5
 * 4보다 최초로 큰 숫자를 오른쪽에서 찾고 있다면, 당연히 4보다 작은 수인 3,2,1보다 더 커야한다는 점을 생각해보면 쉽습니다.
 *
 * */