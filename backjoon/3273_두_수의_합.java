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

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int x = Integer.parseInt(br.readLine());
        Arrays.sort(arr);

        int left = 0, right = N-1, answer=0;

        while(left<right){

            int sum = arr[left] + arr[right];

            if(sum == x){
                answer++;
            }

            if(sum > x){
                right--;
            }
            else{
                left++;
            }

        }

        System.out.print(answer);


    }


}

/*
 * 3273 두 수의 합
 *
 * 투 포인터 문제
 *
 * 우선 문제의 N이 최대 100,000이므로 브루트포스로 풀이하면 N^2 (100,000^2) 이므로 시간초과가 될 수 밖에 없음을 짐작할 수 있습니다.
 * 이때 가장 쉽게 떠올릴 수 있는 방법이 정렬 후 투 포인터를 적용하는 방법일 것입니다.
 *
 * 왼쪽 포인터는 가장 작은 값, 오른쪽은 가장 큰 값을 가르키기도록 하고 둘을 더합니다.
 * 더한 값 sum이 x와 같다면 count를 올려주면 됩니다.
 * 또한, sum이 x보다 작거나 같으면 left를 올려주고, x보다 크면 right를 내려줍니다.
 *
 * 이렇게 하면 정렬에 드는 시간 O(N Log N) + 투포인터의 시간 O(N) 이므로 O(N Log N)으로 결과를 도출해낼 수 있습니다.
 *
 * */