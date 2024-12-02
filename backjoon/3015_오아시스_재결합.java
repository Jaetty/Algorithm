import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][] stack;
    static int idx;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        long answer = 0;
        idx = 0;
        // [idx][0]은 원소의 값, [idx][1]은 어떤 원소값이 최초로 들어왔던 위치
        // 예를 들어 원소 5,5,5를 만났다면 스택에는 [[5,0],[5,0],[5,0]] 과 같이 5가 최초로 스택에 들어온 위치를 그대로 기억함.
        stack = new int[N][2];

        for(int i=0; i<N; i++){

            int val = Integer.parseInt(br.readLine());

            while(idx > 0 && stack[idx-1][0]<val){
                answer+= idx - stack[idx-1][1];
                idx = stack[idx-1][1];
            }

            int before = idx;

            if(idx>0){
                answer += idx - searchBigger(val);

                if(stack[idx-1][0] == val){
                    before = stack[idx-1][1];
                }

            }
            stack[idx][0] = val;
            stack[idx][1] = before;
            idx++;
        }

        System.out.println(answer);
    }

    // 스택에서 만나는 나보다 큰 값 위치 리턴.
    static int searchBigger(int curr){

        int answer = idx-1;

        while(answer > 0 && stack[answer][0] <= curr){
            answer = stack[answer][1]-1;
        }

        return answer<=0 ? 0 : answer;

    }
}

/*
 * 스택 문제
 *
 * @ 오아시스 팬으로서 늦었지만 재결합 기념으로 풀이해보았습니다. ㅎㅎ
 *
 * 이 문제는 서로 마주보는 쌍이 몇개 인가를 물어보고 있습니다.
 * 여기서 서로가 마주보기 위한 조건은 A와 B 사이에 A나 B 둘 중의 키보다 더 큰 사람이 없어야 합니다.
 *
 * 그러면 몇가지 예시와 함께 문제를 이해해보겠습니다.
 * 2 4 4 1 2 2 5 1 이 주어졌을 때
 * 맨 처음의 2의 경우 2-4는 서로 볼 수 있지만 4 이후의 값은 4에 크기에 막혀서 서로 마주보지 못합니다. 즉, 2는 4 이후로는 더이상 고려할 필요가 없어집니다.
 * 그렇다면 어떤 작은 값이 큰 값을 만난 이후로는 더이상 고려 대상이 되지 못하는 점을 알 수 있습니다.
 * 이를 스택으로 해보면
 * 1. [] -> 2를 만남 스택에 2를 push
 * 2. [2] -> 4를 만남 [2]보다 큼 -> pop & 4를 push
 * 3. [4] -> 4을 만남 4와 같음 -> 4 push
 * 3. [4,4] -> 1을 만남 4보다 작음 -> 1 push
 * 4. [4,4,1] -> 2를 만남 [1]보다 큼 -> pop -> 4보다 작음 2 push
 * 5. [4,4,2] -> 2를 만남 [2]와 같음 -> 2 push
 * 6. [4,4,2,2] -> 5를 만남 [2]보다 큼 -> " 2번 pop " -> 4보다 큼 pop -> 5 push
 * 7. [5] -> 1를 만남 [5]보다 작음 -> [5,1]
 *
 * 이와 같이 문제 풀이를 진행할 수 있다는 점이 떠오릅니다.
 * 여기서 쌍이 되는 과정을 확인해보면
 * 1. 스택의 마지막 값 X보다 큰 값 Y를 만났을 때는 스택에 Y보다 작은 값이 들어간 만큼 쌍이 이루어집니다. (예를 들면 위의 2번는 한 쌍 6번의 경우 네 쌍이 만들어집니다.)
 * 2. 스택의 마지막 값 X가 Y보다 작거나 같다면 스택 안에서 가장 처음으로 만나는 나보다 큰 값의 거리만큼 쌍이 이루어집니다.
 * 예를 들면 [4,4] -> 2을 만나면, 2는 바로 자신의 옆에 있는 4와 마주칠 수 있지 그 뒤에 있는 4랑은 쌍이 되지 못합니다. 즉 거리 1만큼 쌍이 됩니다.
 * [4,4,2]-> 2와 만나면, 2는 내 옆에 2와 그 다음 4까지만 쌍이 되고 최초의 4와는 쌍이 되지 못합니다.
 *
 * 문제의 핵심은 어떻게 2번 포인트를 빠르게 처리하냐 입니다. 일일히 다 비교하면 시간 초과가 됩니다.
 * 저는 이 방법을 최초로 어떤 숫자가 들어온 위치를 같이 기억하게 했습니다. (지금 생각하면 스택에 다 넣지 않고 카운트를 올려도 됐던 것 같습니다만...)
 *
 * [4,4,'2',2,2,2]->2를 만남, 이런 상황이 와도 2의 최초 위치인 3번에서 -1을 해준 값은 반드시 2보다 클 것입니다.
 * 그러니까 [스택 크기 값 - (나와 같은 크기의 최초 위치 -1)]만큼 쌍이 생깁니다.
 *
 * 이와 같은 로직을 짠 후 코드로 구현하자 문제를 풀이할 수 있었습니다.
 *
 *
 * */