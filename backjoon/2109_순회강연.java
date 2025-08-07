import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, answer;
    static int[][] input;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine().trim());
        answer = 0;
        input = new int[N][2];

        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine().trim());
            input[i][0] = Integer.parseInt(st.nextToken());
            input[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(input, (e1,e2)-> e1[1]-e2[1]);
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for(int[] val : input){

            if(pq.size() < val[1]){
                pq.add(val[0]);
            }
            else if(pq.peek() < val[0]){
                pq.poll();
                pq.add(val[0]);
            }
        }

        while(!pq.isEmpty()){
            answer += pq.poll();
        }

        System.out.print(answer);
    }
}
/*

2109 순회강연

그리디 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. 입력된 값(input)들을 날짜(d)를 기준으로 오름차순 정렬한다.
2. p값을 오름차순으로 저장할 pq를 생성한다.
3. input을 순회하면서 d값이 pq.size()보다 크다면 그냥 pq에 넣어준다.
4. input의 d값이 pq.size()와 같다면 p값이 pq에서 가장 작은 값보다 크다면 pq에서 가장 작은 값을 빼고 현재 p값을 넣어준다.
5. 이를 마지막 input까지 반복한다.

이 문제의 핵심 아이디어 부분은 pq의 size를 날짜로 인식하는 것 입니다.
즉, pq의 size는 하루하루를 의미합니다. pq size가 1이라면 1일차가 예정이 이미 정해졌다는 뜻입니다.

아래 예시 입력과 함께 해당 문제를 풀이해보겠습니다.
4
10 1
20 2
30 1
50 2

먼저 input을 날짜 순으로 정렬하면 순차적인 날짜로 강의를 고려할 수 있게 됩니다.
때문에 정렬을 하게 되면 아래와 같이 됩니다.

1일     1일     2일      2일
10      30      20      50

이제 오름차순 pq를 만들어서 정렬된 배열을 순회해봅니다.
첫 배열의 값으로 1일 이내 강연을 오면 10원을 받을 수 있는 강연 요청과 만났습니다.
1일에 딱히 예정된 스케쥴이 없었으므로 pq에 해당 강연 요청을 넣어줍니다.

pq = {10};

1일     2일      2일
30      20      50

다시 배열을 순회하며 1일 이내에 강연에 30원을 줄 수 있는 강연 요청과 만났습니다.
이때 이미 1일에 10원을 주는 강연이 예정되어있습니다. 그러나 10원보다 30원이 더 많습니다.
그러니 10원 강연을 취소하고 30원 강연을 수락합니다.

pq = {30}
2일      2일
20      50

다시 배열을 순회하며 2일 이내에 강연하면 20원을 줄 수 있는 강연 요청과 만났습니다.
저희 예정은 1일차 예정밖에 없으니 2일차엔 무리없이 참석이 가능합니다. 때문에 이 강연 요청을 수락합니다.

pq = {20, 30}
2일
50

다시 배열을 순회하며 2일 이내에 강연하면 50원을 줄 수 있는 강연 요청과 만났습니다.
이미 1일부터 2일까지 모든 강연 예정이 정해져있습니다. 그러나 20원보다 50원이 훨씬 이득입니다.
때문에 20원 강연을 취소하고 50원 강연을 수락합니다.

pq = {30, 50}

이렇게 해서 pq에는 30원과 50원을 받는 강연이 들어가게 됩니다.
이를 꺼내서 합산해주면 80원을 얻을 수 있다는 결론을 도출할 수 있습니다.

이처럼, pq와 그리디를 통해서 가장 높은 금액을 주는 강연을 선택해가면 문제를 해결할 수 있습니다.

*/