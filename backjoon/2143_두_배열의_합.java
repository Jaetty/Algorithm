import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        int N = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        // 누적합 변수
        int[] sum = new int[1001];
        // A의 부분 누적합을 모두 저장함
        List<Integer> AList = new ArrayList<>();

        for(int i = 1; i <= N; i++) {
            int val = Integer.parseInt(st.nextToken());
            // 1~i까지 총합을 나타냄.
            sum[i] = val + sum[i-1];

            // 1~i-1 까지 반복하며 각 인덱스의 누적합을 빼주면 부분 누적합을 모두 구함
            // N * (N+1) / 2 = O(N^2)이지만 N이 작으므로 충분히 가능
            for(int j = i-1; j >= 0; j--) {
                AList.add(sum[i] - sum[j]);
            }
        }

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        Map<Integer, Integer> BList = new HashMap<>();

        for(int i = 1; i <= M; i++) {
            int val = Integer.parseInt(st.nextToken());
            sum[i] = val + sum[i-1];

            for(int j = i-1; j >= 0; j--) {
                // B에서의 부분 누적합은 hashmap에 넣어줌. 거의 O(1)만에 탐색이 가능해짐
                BList.put(sum[i]-sum[j], BList.getOrDefault(sum[i]-sum[j], 0) + 1);
            }
        }

        long answer = 0;

        // A의 부분 누적합을 모두 돌아가면서, T와의 차이를 B에서 찾음
        for(int val : AList) {

            int diff = T - val;
            answer += BList.getOrDefault(diff, 0);

        }

        System.out.println(answer);

    }

}

/*

2143 두 배열의 합

누적합 문제

해당 문제의 핵심 풀이 로직은 다음과 같습니다.
1. N에 대해서 입력으로 주어지는 숫자를 누적합으로 기억한다.
2. 누적합을 이용하여 부분 누적합을 모두 구하여 따로 저장해둔다. (저는 List에 저장함)
3. M에 대해서 입력으로 주어지는 숫자를 누적합으로 기억한다.
4. 누적합을 이용하여 부분 누적합을 모두 MAP에 기억하게 한다. key = 합, value = 개수
5. 2번에서 구한 부분 누적합과 T와의 차이 값을 map에서 검색해서 나온 value 값만큼 계속 카운트해준다.

저의 경우 문제를 map으로 풀이했지만, map이 아닌 3번 방식을 만약 똑같이 list나 배열 형태로 저장했다면,
이분 탐색으로 upper_bound와 lower_bound로 범위 만큼 따로 빼는 방법도 가능할 것 같습니다.

주의할 점은 answer의 경우 int형을 벗어날 수 있으니 long 형으로 만들어야합니다.

*/