import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Main{

    static long[] arr, answer;
    static int N;
    static long temp;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new long[N];
        answer = new long[3];
        temp = Long.MAX_VALUE;

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        for(int i=0; i<N-2; i++){
            twoPoint(i);
        }

        Arrays.sort(answer);

        System.out.println(answer[0]+ " " + answer[1] + " " + answer[2]);

    }

    static void twoPoint(int index){

        int low = index+1, high = arr.length-1;


        while(low<high){

            long sum = arr[low] + arr[index] + arr[high];
            long tmp = Math.abs(arr[low] + arr[index] + arr[high]);

            if(sum==0 || temp > tmp){
                answer[0] = arr[low];
                answer[1] = arr[index];
                answer[2] = arr[high];
                temp = tmp;
            }
            if(sum < 0){
                low++;
            }
            else{
                high--;
            }
        }

    }
}
/*
 * 투포인터 활용 문제
 * 2467 용액 문제를 풀었다면 조금만 응용하면 풀 수 있는 문제입니다.
 *
 * 투포인터로 중간값을 하나 정하고 그 중간값을 기준으로 low값을 지정해줍니다.
 * 그래서 가능한 경우들을 더해주며 후보를 찾으면 문제를 해결할 수 있습니다.
 *
 */