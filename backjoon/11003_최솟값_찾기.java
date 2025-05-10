import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        // 기본적으로 맨 앞 값이 가장 작은 값이고 그 이후는 그것보다 큰 값
        // 1 5 2가 입력이면 {1}, {1,5}, {1,2}로 유지되도록 설정
        Deque<int[]> deque = new ArrayDeque<>();

        st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){

            int val = Integer.parseInt(st.nextToken());
            int[] input = new int[]{val, i};

            while(!deque.isEmpty()){
                // 가장 먼저 들어온 값이 범위를 벗어났는지 파악.
                int[] first= deque.getFirst();
                if(i - deque.peekFirst()[1] >= L){
                    deque.pollFirst();
                    continue;
                }

                // 만약 맨 처음 값이 이번 입력 값보다 크거나 같다면 비우기.
                if(first[0] >= val){
                    deque.clear();
                }
                break;
            }

            // 맨 마지막 값에서 나보다 큰 값들 다 없애기
            while(!deque.isEmpty()){

                int[] last= deque.getLast();
                if(val <= last[0]){
                    deque.pollLast();
                    continue;
                }
                break;

            }
            deque.addLast(input);

            sb.append(deque.peekFirst()[0]).append(" ");
        }
        System.out.print(sb.toString());

    }
}

/*

11003 최솟값 찾기

덱 문제

해당 문제는 다음 두 가지 조건 때문에 시간 초과를 일으키게 됩니다.
1. 입력 크기가 크다. (1 ≤ L ≤ N ≤ 5,000,000)
2. 배열을 이용하여 카운트 하기 어려운 입력 값이 주어진다. (-10^9 ≤ Ai ≤ 10^9)

@ 저는 처음에 HashMap을 활용하여 중복을 제외한 우선 순위 큐로 풀이했습니다.
예상하기로는 N Log N 만에 되지 않을까 싶었는데, 시간 초과가 나와서 다음과 같은 풀이 방법을 다시 생각해냈습니다.

제가 생각해낸 문제의 해결 핵심은 Deque<int[]>을 이용하는 것 입니다.
( int[]는 각각 {값, 순서}를 나타내고 있습니다. )
그리고 Deque은 오름차순으로 정렬 시켜 놓습니다. 즉, 맨 처음 값이 최솟값 입니다.

최솟값을 체크할 때 먼저 최솟값이 L 구간을 벗어나게 된다면 deque.pollFirst()로 지워줍니다.
그리고 L 구간에 합류하는 값이 X라면 deque에서 X보다 작은 값 뒤에 두고, 그 과정에서 X보다 큰 값들은 버려주게 만들어줍니다.

예시로 다음과 같은 입력이 주어진다면
7 4
1 2 4 8 3 16 1

(L = 4이므로 덱은 다음과 같이 변한다.)
[{1,0}]
[{1, 0}, {2, 1}]
[{1, 0}, {2, 1}, {4, 2}]
[{1, 0}, {2, 1}, {4, 2}, {8, 3}]
[{2, 1}, {3, 4}]
[{3, 4}, {16, 5}]
[{1, 6}]

이렇게 하는 이유는 마지막으로 들어온 값이 어차피 가장 오래 생존해있습니다.
나보다 먼저 들어온 나보다 크거나 같은 값들을 deque에 굳이 보유할 필요가 없는 것입니다.
이렇게 하면 최솟값은 항상 deque의 맨 처음이니까 찾기가 편하고, 불필요한 값들을 금방 갱신되게 만들 수 있습니다.

이 방법으로 최솟값을 찾는 반복의 최악의 경우는 다음과 같습니다.

deque이 [1,2,3,4,5,6] 이렇게 구성되어 있을 때, 그 다음 입력값이 '2'라면
2를 둘 장소가 맨 최초의 값 '1' 바로 뒤에 있으므로 deque.size()-1 만큼 반복하게 됩니다. (2,3,4,5,6 을 버리는 만큼 수행해야함)

하지만 그 이후로는 deque은 [1,2] 가 되므로 deque의 크기가 확 줄어들어 반복 횟수는 줄어들게 됩니다.
즉, N = L = 5,000,000 이라도 최악의 경우 O(N + N-1) = O(N)에 해결될 수 있다고 판단하였습니다.

위와 같은 로직을 바탕으로 코드를 구현하면 문제를 해결할 수 있습니다.

*/