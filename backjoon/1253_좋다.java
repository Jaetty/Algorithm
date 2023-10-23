import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, answer;
    static int[] arr;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        answer = 0;

        st = new StringTokenizer(br.readLine());
        arr = new int[N];


        for(int i=0; i<N; i++){
            int val = Integer.parseInt(st.nextToken());
            arr[i] = val;
        }

        Arrays.sort(arr);

        for(int i=0; i<N; i++){

            int target = arr[i];
            int left = 0;
            int right = N-1;

            while(left<right){

                if(left==i){
                    left++;
                    continue;
                }
                else if(right==i){
                    right--;
                    continue;
                }

                long sum = arr[left]+arr[right];

                if( sum == target && left!=i && right!=i){
                    answer++;
                    break;
                }
                else if(sum>target){
                    right--;
                }
                else{
                    left++;
                }

            }

        }
        System.out.println(answer);
    }

}

/*
 * 투 포인터 문제
 *
 * 투 포인터를 이용하는 문제로 기본적인 로직은 다음과 같습니다.
 * 먼저 정렬을 한 후 양 끝쪽을 나타내는 left, right 포인터를 만들어줍니다.
 * 이제 N번 순회하면서 배열에 있는 값이 포인터 left right를 움직여서 더한 값과 같은지 확인하고 같다면 count를 하나 올려줍니다.
 * 즉 다음과 같은 입력이 들어오면
 * 5
 * 1 2 3 4 5
 * 순서대로 1,2,3,4,5가 양쪽 끝을 나타내는 left right 포인터가 가르키는 값을 더하여 더한 값이 크다면 right값을 낮추고 작다면 left값을 올립니다.
 * 만약 더한 값이 현재 값과 같다면 left와 right가 현재 값의 포인터가 아닌 것을 확인해주면 됩니다.
 *
 */