import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());
        int N = 0;
        int answer = 0, max = 0;
        int[] arr = new int[100001];

        for(int tc=0; tc<T; tc++){

            N = Integer.parseInt(br.readLine());

            for(int i=0; i<N; i++){

                st = new StringTokenizer(br.readLine());
                int idx = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                arr[idx] = value;

            }

            max = arr[1];
            answer = 1;

            for(int i=2; i<=N; i++){

                if(max > arr[i]){
                    answer++;
                    max = arr[i];
                }

            }

            sb.append(answer).append("\n");

        }

        System.out.print(sb.toString());


    }

}

/*
 * 1946 신입 사원
 *
 * 그리디 문제
 *
 * 문제의 포인트는 다음과 같습니다.
 * 1. T<=20, N<=100_000
 * 2. 서류와 면접 '성적 순위'가 주어진다. (1등부터 N등 까지)
 * 3. 한 지원자가 모든 지원자보다 서류, 면접의 점수 둘다 떨어진다면 불합격이다.
 *
 * 정말 단순하게 브루트포스로 생각해보면 한 지원자마다 다른 모든 지원자와 비교해주면 됩니다.
 * 하지만 N은 100_000이므로 N^2만으로도 이미 시간초과인데, T=20이므로 이를 또 20번 반복할 수 없습니다.
 *
 * 그렇다면 정렬을 해보면 어떨까요? 예시 입력을 기준으로 한번 보겠습니다.
 * 1
 * 7
 * 3 6
 * 7 3
 * 4 2
 * 1' 4'
 * 5 7
 * 2" 5"
 * 6 1
 *
 * 위를 각각 서류와 면접 순위로 정렬시키면
 *
 * 면접 : 1 2 3 4' 5" 6 7
 * 서류 : 1' 2" 3 4 5 6 7
 *
 * 여기서 서류 점수 1등인 친구는 이미 다른 모든 지원자를 서류로 이겼습니다. 그렇다면 둘 다 남들보다 떨어지는 경우는 없습니다.
 * 그리고 지금까지 나온 가장 높은 면접 순위는 4입니다.
 *
 * 이제 2, 5 성적의 지원자를 보시면
 * 서류가 2등이라 이미 앞에 있는 1등한테 졌습니다, 그러면 면접 순위로 이겼는지 확인해보면 됩니다. 그러나 면접 순위가 5로 4보다 낮습니다.
 * 그러므로 1, 4의 성적을 기록한 친구한테 서류, 면접에서 둘 다 졌으므로 탈락입니다.
 *
 * 3 5의 친구도 이미 3이니까 서류는 졌고, 5의 경우도 4를 못이기니 졌습니다.
 *
 * 4,2의 경우는 이미 서류 4순위니까 서류에선 졌습니다. 그러나 면접 순위가 4보다 높습니다.
 * 그러므로 둘 다 떨어지는 경우가 아니므로 4,2 친구는 합격입니다. 그리고 지금까지 나온 면접 순위 중 2가 가장 높게 됩니다.
 * 나머지도 같습니다. 결국 서류는 당연히 지고 이때까지 나온 면접 순위 중 가장 높은 친구를 이기는 경우가 있는지만 확인해보면 됩니다.
 *
 * 그러면 '로직을 짯으니 저렇게 동작하려면 먼저 정렬을 하고 누가 누구를 가르키는지 알아내는 과정이 필요하겠네?' 라고 떠오를텐데
 * 여기서 조금 생각해보면 굳이 정렬도 누가 누구를 가르키는지 파악하는 추가 과정도 필요가 없다는 것을 알 수 있습니다.
 * 정렬은 N log N의 시간을 요구합니다. N이 100_000이므로 대략 10만 * 16 정도인데 굳이 이 시간을 소비할 필요가 없습니다.
 *
 * 그냥 배열을 마치 이렇게 만드는 것입니다. 배열 arr[서류 순위] = 면접 순위
 * 그러면 1~N의 인덱스가 곧 서류 순위를 나타내고 배열에 서류 순위의 인덱스에 있는 면접 순위 값으로 비교하면 되니까 O(N)만에 비교가 가능합니다.
 *
 * */