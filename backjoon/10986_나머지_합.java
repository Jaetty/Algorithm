import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static long answer;
    static int[] arr, sum;
    static int[] count;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N];
        sum = new int[N];

        count = new int[1000];
        st = new StringTokenizer(br.readLine());

        arr[0] = Integer.parseInt(st.nextToken())%M;
        sum[0] = (arr[0] + sum[0])%M;
        count[sum[0]]++;

        for(int i=1; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken())%M;
            sum[i] = (arr[i] + sum[i-1])%M;
            count[sum[i]]++;
        }
        answer = count[0];
        for(int i=0; i<M; i++){
            answer += combine(count[i]);
        }

        System.out.println(answer);

    }

    static long combine(long c){
        if(c<2){
            return 0;
        }
        else return c*(c-1) / 2;
    }

}

/*
 * 누적합 + 수학 문제
 *
 */