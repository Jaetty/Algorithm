import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] cnt = new int[1001];
        int[] answer = new int[N+1];
        // 중복없는 입력값을 모음
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            int val = Integer.parseInt(st.nextToken());
            if(cnt[val] == 0) {
                list.add(val);
            }
            cnt[val]++;
        }

        // 절대 겹칠 리 없는 값을 넣어주자
        answer[0] = 1001;
        int insert_idx = 1;
        // 중복 없는 입력값을 오름차순 sort
        Collections.sort(list);

        while(insert_idx <= N) {

            int candidate = -1;

            for(int val : list) {

                if(cnt[val] == 0) {
                    continue;
                }

                // 후보가 초기값(-1)이면 그냥 후보로 해줌
                if(candidate == -1){
                    candidate = val;
                }
                // 후보가 -1이 아니면, 조건을 불충족할때만 바꿔줌
                else if(candidate == answer[insert_idx-1]+1) {
                    candidate = val;
                    break;
                }
            }

            answer[insert_idx] = candidate;
            cnt[candidate]--;

            // 해당 위치에 후보 값을 넣으려고 하는데 만약 조건(A[i]+1 != A[i])을 충족 못시켰다면?
            // 조건을 만족할때까지 후보값을 앞으로 보내줌
            int curr = insert_idx;
            while(answer[curr-1]+1 == answer[curr]) {
                int temp = answer[curr];
                answer[curr] = answer[curr-1];
                answer[curr-1] = temp;
                curr--;
            }

            insert_idx++;
        }

        StringBuilder sb = new StringBuilder();

        for(int i=1; i<=N; i++) {
            sb.append(answer[i]).append(" ");
        }
        System.out.print(sb.toString());

    }

}

/*

1071 소트

그리디 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
"i 위치에 값이 가장 작으면서도 가능하다면 조건(A[i-1]+1 != A[i])을 만족하는 값을 둔다.
만약, i 위치에 두는 값이 조건을 만족하지 못하면 만족할 때까지 앞으로 보낸다."

먼저 문제의 경우 N의 최대 크기는 50입니다. 이는 매우 작은 수로 O(N^2)은 가뿐히 실행할 수 있는 범위입니다.
그렇기 때문에 풀이 과정 중 O(N^2) 만큼의 반복이 필요하게 되더라도 주저하지 않고 넓게 가능성을 가지고 풀이가 가능합니다.

다음과 2가지 예시 입력으로 한번 위의 핵심 풀이 로직이 올바른지 확인해보겠습니다.

4
1 1 2 2
먼저 N크기의 배열을 만듭니다.
index :     1 2 3 4
answer[] = {X,X,X,X}

1번 인덱스의 넣을 값을 1 1 2 2 중에서 찾습니다. (최소이면서 가능하면 조건을 만족하는 값)
1이 가장 최솟값이며 조건(맨 첫 입력은 조건 판단이 필요없음)을 만족하기 때문에 1을 넣어줍니다.
answer[] = {1,X,X,X}

2번 인덱스의 넣을 값을 1 2 2 에서 찾아보면 1이 가장 최솟값이니까 1을 선택합니다.
answer[] = {1,1,X,X}

다음으로 후보를 2 2 에서 찾아야하는데 2가 최소이므로 2를 고릅니다.
answer[] = {1,1,2,X}
그런데 2는 조건(A[i-1]+1 != A[i])을 위배합니다. 그러니까 2를 조건을 만족할 때까지 앞으로 보내줍니다.
answer[] = {2,1,1,X}

마지막 2도 이를 반복하게 되면
answer[] = {2,1,1,2} => {2,2,1,1}
즉, 정답은 {2,2,1,1}이 되는 것 입니다.

다음 예시의 경우는
3
1 2 3

먼저 N크기의 배열을 만듭니다.
index :     1 2 3
answer[] = {X,X,X}

1번째 인덱스에 값을 넣으려면 1, 2, 3 중 가장 작으면서 가능하면 조건을 만족하는 값을 넣으면 됩니다.
여기선 '1'이 가장 작으며 조건(맨 첫 입력은 조건이 필요없음)을 만족하니까 '1'을 꺼내서 넣어줍니다.
answer[] = {1,X,X}

2번째 인덱스에 값을 넣기 위해 남은 2, 3중에 최소이며 가능하면 조건을 만족하는 값을 찾아봅니다.
2의 경우 최소이긴 합니다만, 조건을 충족하지 못합니다. 그러니까 3을 선택합니다.
answer[] = {1,3,X}

다음으로 2만 남았으니 정답은 answer[] = {1,3,2}가 됩니다.

이처럼 해당 문제는 단순한 그리디 문제이기 때문에 그리디 조건을 잘 파악하기만 하면 금방 풀 수 있는 문제입니다.


*/