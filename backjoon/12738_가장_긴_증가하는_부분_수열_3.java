import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static int[] arr, list;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[N];
        list = new int[N+1];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        list[0] = arr[0];
        int size = 1;

        for(int i=1; i<N; i++){

            if(list[size-1]>=arr[i]){

                int lower = 0;
                int high = size;

                while(lower < high){

                    int mid = (high+lower)/2;

                    if(list[mid]>=arr[i]){
                        high = mid;
                    }else{
                        lower = mid+1;
                    }

                }
                list[high] = arr[i];
            }else{
                list[size++] = arr[i];
            }

        }

        System.out.println(size);
    }
}

/*
 * 이분 탐색 문제
 *
 * LIS 문제는 대표적으로 DP로 해결하는 방법이 있습니다. 하지만 DP의 경우는 O(N^2)의 시간이 걸리게 됩니다.
 * 즉 N이 얼마나 크냐에 따라 시간이 결정되게 됩니다. 이 문제의 경우 N의 범위는 1 ≤ N ≤ 1,000,000 이므로 DP로는 해결할 수 없습니다.
 *
 * 그렇다면 이 문제를 해결하기 위해서는 N^2이 아닌 다른 방법이 필요하게 됩니다.
 * 여기서는 이분 탐색의 lower_bound를 응용하여 문제를 해결하는 방법이 있습니다.
 *
 * 입력이 다음과 같다면
 * 6
 * 10 20 15 30 20 40
 *
 * 저희는 문제의 정답이 {10, 20, 30, 40}으로 4라는 것을 알 수 있습니다.
 * 여기서 정답이 4라는 부분을 고려하면 이 집합이 어떻게 구성되어있건 간에 4개라는 것만 알면 됩니다.
 *
 * 포인트는 N번의 반복을 돌면서 숫자를 순서대로 찾으며 LIS 수열을 만들어줍니다.
 * 여기서 LIS 수열에 마지막 숫자보다 현재 탐색해서 찾은 숫자가 크면 수열에 추가하고 작으면 값을 바꿔주면 됩니다.
 * 그렇게 하면 원래 탐색 시간 O(N)과 이분 탐색 시간 O(Log N)으로 총 시간은 O(N Log N)안에 값을 도출 할 수 있게 됩니다.
 *
 * 순서대로 진행하며 나타내보겠습니다.
 * 6
 * 10 20 15 30 20 40
 *
 * 0. 처음에 LIS 수열은 비어있습니다. 수열을 이렇게 표현하겠습니다 => {}
 * 1. 10은 수열이 비어있기 때문에 10을 추가합니다. {10}
 * 2. 20은 수열 마지막 값인 10보다 크기 때문에 수열에 추가합니다. {10, 20}
 * 3. 15는 20보다 작습니다. 여기서 이분탐색으로 수열 내에 15가 위치할 수 있는 위치를 찾습니다.
 *    {10, 20} 에서 15가 20보다 작기 때문에 위치할 수 있는 곳은 1번째 부분입니다. 즉 10을 15로 바꿔서 {15, 20}으로 수열이 구성됩니다.
 * 4. 30은 20보다 크기 때문에 수열을 늘립니다. {15, 20, 30}
 * 5. 20은 30보다 작기 때문에 이분탐색으로 20의 위치를 찾습니다. {15, 20, 30} 중 20은 똑같이 20 위치에 있을 수 있기 때문에 20이 있는 자리에 들어갑니다.
 * 6. 40은 30보다 크기 때문에 수열을 늘립니다. {15, 20(인덱스 값은 4), 30, 40}
 *
 * 이렇게 진행되면 수열을 구성하는 값은 다르지만, 수열의 크기인 정답 4는 알아낼 수 있게 됩니다.
 * 위의 로직을 구현하게 되면 문제를 해결할 수 있습니다.
 *
 *
 */