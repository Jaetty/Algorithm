import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, H, index;
    static Integer[][] arr;
    static int[] answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        arr = new Integer[2][N/2];
        index = 0;

        for(int i=0; i<N; i++){
            if(i%2==0){
                arr[i%2][index] = Integer.parseInt(br.readLine());
            }
            else{
                arr[i%2][index++] = H+1 - Integer.parseInt(br.readLine());
            }
        }

        answer = new int[N+1];

        Arrays.sort(arr[0]);
        Arrays.sort(arr[1], (e1,e2)-> e2-e1);

        int min = index;

        for(int i=1; i<=H; i++){

            int hit = N - (binarySearch(0, i) + binarySearch(1, i));

            min = Math.min(min, hit);
            answer[hit]++;
        }

        System.out.println(min +" "+ answer[min]);

    }

    static int binarySearch(int row, int h){

        int lo = 0;
        int hi = index;

        while(lo<hi){
            int mid = (lo+hi)/2;

            if(row%2==0){
                if(arr[row][mid] < h){
                    lo = mid+1;
                }
                else{
                    hi = mid;
                }
            }
            else{
                if(arr[row][mid] <= h){
                    hi = mid;
                }
                else{
                    lo = mid+1;
                }
            }
        }
        return lo;
    }
}
/*
 * 이분탐색 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 처음은 반드시 석순이고, 석순과 종유석이 번갈아 나온다.
 * 2. 2<= H <= 500_000, 2<= N <= 200_000, N은 반드시 짝수
 *
 * 1번 포인트로 알 수 있는 점은 이 문제의 장애물 회피 판정입니다.
 * 처음 석순을 피하려면 특정 높이보다 더 높게 날아야하고, 종유석을 피하려면 특정 높이보다 낮게 날아야합니다.
 *
 * 2번 포인트로 알 수 있는 점은 단순 계산으로는 시간초과라는 점 입니다.
 * 모든 높이에 대해서 완전탐색 하게 되면 500000 * 200000 이 될 것입니다.
 * 이를 조금 줄여서 특정 조건의 경우 탐색을 멈춘다고 해도 그 값은 웬만하면 1억번의 연산을 넘게될 것입니다.
 *
 * 제가 여기서 생각한 방법은 누적합과 이분탐색이었는데 여기선 이분탐색이 평소 익숙해서 이분탐색으로 풀이했습니다.
 *
 * 결국 정렬한 배열에서 이분탐색으로 어떤 높이의 값이 몇개의 종유석과 석순을 피하는지 알면 된다는 것 입니다.
 * 그러면 log(N)만의 탐색으로 몇개의 장애물을 피했는지 알 수 있습니다.
 *
 * 500_000 * log(N)이면 대략 500_000 * 17로 충분할 것으로 생각했습니다.
 * 여기서 이분탐색을 위해 입력되는 석순과 종유석을 각각 따로 저장하고, 각각에 이분탐색으로 회피하는 횟수를 구해주었습니다.
 * 그러면 실제론 각각의 배열의 정렬 시간 (대략 320만) + 500_000 * (log(N/2) + log(N/2)) = (대략) 2천만 초반이므로 충분히 풀이가 가능합니다.
 *
 * 이와 같은 로직을 바탕으로 코드를 구현하면 풀이할 수 있습니다.
 *
 * */