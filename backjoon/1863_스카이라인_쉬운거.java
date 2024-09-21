import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N+1];
        StringTokenizer st;

        arr[0] = 0;

        for(int i=1; i<=N; i++){
            st = new StringTokenizer(br.readLine());
            st.nextToken();
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int answer = 0;

        for(int i=N; i>=0; i--){

            if(arr[i]>stack.peek()){
                stack.push(arr[i]);
            }
            else{
                while(arr[i] < stack.peek()){
                    stack.pop();
                    answer++;
                }
                if(stack.peek()!=arr[i]){
                    stack.push(arr[i]);
                }
            }
        }

        System.out.println(answer);

    }

}
/*
 * 스택 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 건물의 가로를 나타내는 x <= 1,000,000, 높이를 나타내는 y <= 500,000
 * 2. 입력은 고도가 바뀌는 구간에서만 나타남 (첫 입력이 x = 1 y =1 이고 다음 입력이 x=5, y=2라면 x 1~4 구간은 1의 크기의 건물)
 * 3. 입력의 수 N <= 50,000
 *
 * 이 문제의 핵심은
 * '어떤 높이의 값이 중간에 끊어지지 않고 쭉 연결되어 있는 구간을 1개의 건물로 칠 때, 그렇게 해서 나오는 최소한의 건물 수가 몇 개인가?' 입니다.
 * 여기서 끊어졌는지 여부는 어떤 특정 높이보다 다음 높이가 더 작다면 그 건물은 더이상 이어져있지 않다는 뜻으로 끊어지게 된다면 거기서 특정 높이의 건물이 있다고 판단된다는 뜻입니다.
 * 반대로 어떤 높이보다 다음에 큰 높이가 나온다는건 여전히 이 건물은 이어져있다고 판단해도 된다는 뜻 입니다.
 *
 * 포인트들을 고려해보면 브루트포스로는 시간상 적절하지 못한 풀이임을 알 수 있습니다.
 * 그러므로 핵심을 잘 생각해보면 이러한 풀이가 가능하게 됩니다.
 * 1. 이때까지 나온 최소한의 높이를 기억해두고 있다가, 그것보다 큰 값이 나오면 최소값을 갱신해준다.
 * 2. 만약 내가 가진 마지막 최솟값보다 낮은 건물의 높이를 만난다면 이때까지 기억한 최솟값 중에 지금 만난 건물보다 큰 건물들은 모두 여기서 끊어졌으므로 그 수만큼 건물의 수를 카운팅합니다.
 *
 * 위의 로직대로라면 문제를 풀이할 수 있게 되고 이렇게 문제를 풀 때 가장 적절한 자료구조가 스택임을 짐작할 수 있습니다.
 * 스택을 이용하여 위 로직대로 동작하게끔 코드를 작성하면 문제를 풀이할 수 있습니다.
 *
 *
 * */