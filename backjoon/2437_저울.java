import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());
        long[] arr = new long[N+1];

        long sum = 0, answer=Long.MAX_VALUE;
        long[] target = new long[N+1];

        // 배열이 0부터 시작한다고 가정하고 누적합 +1 을 1로 설정해줌.
        // 이렇게 하면 2 이상의 값부터 시작하는 배열에서 정답을 1로 출력이 가능.
        target[0] = 1;

        st = new StringTokenizer(br.readLine());

        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        for (int i = 1; i <= N; i++) {
            sum += arr[i];
            target[i] = sum+1;
        }
        answer = target[N];

        for (int i = 0; i < N; i++) {
            if(target[i] < arr[i+1]){
                answer = target[i];
                break;
            }
        }

        System.out.print(answer);

    }

}

/*

2437 저울

그리디 문제

문제의 핵심 포인트는 다음과 같습니다.
"처음부터 확인해보며, 바로 다음의 숫자가 현재의 누적합+1 보다 크다면 현재의 누적합+1은 만들지 못한다"

저는 예시 입력을 바탕으로 풀이 방법을 떠올렸습니다.
7
3 1 6 2 7 30 1

가장 먼저 떠올린 방법은 전부다 더한 값에 +1을 한 값은 절대 만들 수 없다는 점이었습니다.
위의 예시의 값들을 다 더하면 50이 나오는데 그렇다면 51은 절대로 못만드는 값입니다.

그래서 한번 정렬을 한 후, 각각 만들 수 있는 누적합 값을 기록해보기로 했습니다.

예시 입력을 정렬하고, 밑에 누적합을 나타내면 다음과 같아집니다.
1   1   2   3   6   7   30  => 배열 값
1   2   4   7   13  20  50  => 누적합 값
2   3   5   8   14  21  51  => 누적합+1 값

그럼 각각의 누적합에 대해서 +1을 한 값은 못만들 수 있는 가능성이 있지 않을까? 의심이 들었습니다.
그리고 찾아낸 규칙성이 만약 다음 숫자가 누적합 +1 보다 크다면 그 값은 만들지 못한다는 점 이었습니다.

가령, 입력에서 주어진 1~7까지의 누적 합은 20이고, 21은 만들지 못합니다.
21을 만드려면 다음 숫자의 범위는 7<=x<=21 이어야만 했습니다.

즉, "바로 다음의 숫자가 현재의 누적합+1 보다 크다면 현재의 누적합+1은 만들지 못한다"는 점을 알 수 있습니다.

또한, 이 규칙은 가장 맨 앞에서만 반드시 적용됩니다. 뒤에 가선 꼭 그렇지 않은 경우도 있으나
적어도 맨 첫번째 경우에 대해선 반드시 이 규칙이 성립한다는 점을 테스트를 해보며 확인했습니다.
이 문제는 결국, "최초로 다음 숫자가 현재의 누적합+1 보다 큰 경우, 현재의 누적합+1 출력하기" 문제가 됩니다.

*/