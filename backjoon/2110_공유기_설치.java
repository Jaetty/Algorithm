import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, C;
    static int[] arr;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        arr = new int[N];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(arr);

        int lo = 1;
        int hi = 1_000_000_001;

        while(lo<hi){

            int mid = (lo+hi) / 2;

            if(check(mid)){
                hi = mid;
            }
            else{
                lo = mid+1;
            }
        }

        System.out.println(lo-1);

    }

    static boolean check(int mid){

        int count = 1;
        int before = arr[0];

        for(int i=1; i<N; i++){

            if(count>C){
                break;
            }

            if(arr[i]>=mid+before){
                before = arr[i];
                count++;
            }

        }

        return count < C;
    }

}
/*
 * 이분 탐색 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 집의 개수 N은 2<= N <= 200,000.
 * 2. 집의 좌표는 범위는 1<= xi <= 1_000_000_000.
 * 3. '가능한 공유기 간의 거리를 최대한 크게 만들 때' 그 중 가장 거리가 짧은 값 도출하기
 *
 * 우선 1번 2번 포인트를 보면 완전탐색으로는 시간이 부족하다는 점을 알 수 있고, 그렇다면 적어도 NlogN 단위로 만들어야 함을 알 수 있습니다.
 * 여기서 logN을 만든다고 하면 200,000 보다 10억의 숫자에 log를 씌우는게 훨씬 더 빠르다는 점을 알 수 있습니다.
 *
 * 이제 3번 포인트를 고려해보면 가장 거리를 띄우되, 그 중에서 가장 짧은 거리를 찾는다고 한다면
 * 이분탐색을 통해 그 값을 도출할 수 있다는 점을 알 수 있습니다.
 * 즉, 10억번을 모두 돌 필요가 없고 이분 탐색으로 '이 이상으로는 집들 간의 거리를 벌릴 수 없는 값'을 찾아내는게 중요합니다.
 *
 * 그러면 이분 탐색으로 집들 간의 성립되는 거리의 최소 값을 찾아낸 후 이를 출력하면 문제를 해결할 수 있습니다.
 *
 * */