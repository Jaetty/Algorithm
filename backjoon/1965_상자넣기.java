import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[N];
        int[] dp = new int[N];

        for(int i=0; i<N; i++){;
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;

        for(int i=0; i<N; i++){

            for(int j=i+1; j<N; j++){
                if(arr[i] < arr[j]){
                    dp[j] = Math.max(dp[i]+1, dp[j]);
                    answer = Math.max(dp[j], answer);
                }
            }

        }

        System.out.print(answer+1);

    }

}

/*
 * 1965 상자넣기
 *
 * 다이나믹 프로그래밍 문제
 *
 * 문제의 핵심은 LIS입니다.
 * 최장 증가 부분 수열(Longest Increasing Subsequence) 통칭 LIS는 배열이 있을 때, 증가하는 형태로 가장 긴 부분 배열을 말합니다.
 *
 * 예를 들어 다음과 같이 배열이 있다면
 * 1 5 2 3 7
 * 증가하는 값으로 가장 긴 배열은 1, 2, 3, 7 입니다.
 *
 * LIS는 N log N과 N^2 방식으로 크게 2가지 풀이가 있습니다. 여기선 N이 충분히 작으니 N^2방식으로 풀이가 가능합니다.
 * N^2 방식 풀이 방법은 다음과 같이 dp 배열이 있다고 해보겠습니다. 여기서 dp배열은 나보다 작은 값이 얼마나 있는가를 뜻합니다.
 *
 * dp : [0, 0, 0, 0, 0]
 * arr : [1, 5, 2, 3, 7]
 *
 * 0번째 인덱스의 값 1을 시작으로 1보다 큰 값에 나의 dp값 +1을 해줍니다.
 * dp[0, 1, 1, 1, 1]
 *
 * 1번째 인덱스 값 5에서부터 나보다 큰 값에 dp[내 dp값]+1을 해줍니다.
 * dp[0,1,1,1,2]
 *
 * 2에서부터 반복하면 dp[0,1,1,2,2], 3은 dp[0,1,1,2,3] 이렇게 해서 7은 나보다 작은 값이 3개 있으므로 7을 포함해 LIS는 4가 됩니다.
 * 여기서 알 수 있듯 dp[나보다 큰 값] = max (dp[나보다 큰 값], dp[내 값]+1)을 수행해주면 쉽게 풀이할 수 있습니다.
 *
 *
 *
 *
  */