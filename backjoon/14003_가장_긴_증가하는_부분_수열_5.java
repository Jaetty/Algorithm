import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int N;
    static int[] arr, list, index;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        arr = new int[N];
        list = new int[N+1];
        index = new int[N];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        list[0] = arr[0];
        index[0] = 0;
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
                index[i] = high;
            }else{
                index[i] = size;
                list[size++] = arr[i];
            }

        }

        StringBuilder sb = new StringBuilder();
        sb.append( size-- + "\n");
        Stack<Integer> stack = new Stack<>();

        for(int i=N-1; i>=0; i--){
            if(index[i]==size){
                stack.push(arr[i]);
                size--;
            }
        }

        while(!stack.isEmpty()){
            sb.append(stack.pop()+" ");
        }

        System.out.println(sb);
    }
}

/*
 * 이분 탐색 문제
 *
 * 이 문제는 12738 가장 긴 증가하는 부분 수열 3의 응용 문제입니다. 해당 문제를 먼저 풀면 이해가 쉽습니다.
 *
 * 문제의 핵심포인트는 O(N log N)의 탐색을 통해 LIS를 구한 후 여기서 구성하는 숫자를 출력하는 문제입니다.
 * 저희는 O(N log N)이 이분 탐색의 low_bound를 이용하여 구할 수 있다는 사실을 알고 있습니다.
 * 문제는 이 방식으로 구했을 때 크기만 알 수 있지 어떤 값으로 LIS가 구성되어있는지 정확히 알 수는 없습니다.
 *
 * 이 문제를 해결하기 위해서는 index 값을 기록하는 배열을 하나 더 두면 됩니다.
 * 저는 그 배열을 index 라고 선언했고 다음과 같이 입력이 주어지면
 * 6
 * 10 20 15 30 10 50
 *
 * 이것의 인덱스가 들어올 때마다 위치를 기억해주는 과정을 거치면
 * LIS = {10 20 30 50}
 * index = {0 1 1 2 0 3}
 *
 * 위와 같이 결과가 나옵니다. 그렇다면 index에서 뒤에서부터 현재 가장 큰 값이 3이므로 3부터 시작하여 0이 될때까지 가장 먼저 자신과 같은 값이 나오면
 * 그 인덱스의 값을 출력하면 다음과 같습니다.
 * index = {"0" 1 "1" "2" 0 "3"} => 50 30 15 10
 * 이것을 거꾸로 출력하기만 하면 10 15 30 50으로 정답을 도출할 수 있습니다.
 *
 */