import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

class Main{

    static long[][] arr;
    static int N;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        arr = new long[N][2];

        long sum = 0;
        long sum2 = 0;

        for(int i=0; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            arr[i][0] = Long.parseLong(st.nextToken());
            arr[i][1] = Long.parseLong(st.nextToken());
        }

        for(int i=0; i<N; i++){
            if(i==N-1){
                sum += arr[i][0] * arr[0][1];

                sum = sum + (sum2 - (arr[0][0] * arr[i][1]));
            }
            else{
                sum += arr[i][0] * arr[i+1][1];
                sum2 -= arr[i+1][0] * arr[i][1];
            }
        }

        double answer = Math.abs(sum);

        System.out.printf("%.1f", 1d*answer/2);

    }

}

/*
* 기하학 문제
*
* 이 문제는 신발끈 공식을 구현하기만 하면 풀 수 있는 문제입니다.
* 다만 주의할 점은 입력방식과 출력방식을 잘 맞춰주어야합니다.
*
* 신발끈 공식은 따로 참고 링크를 남깁니다.
* https://ko.wikipedia.org/wiki/%EC%8B%A0%EB%B0%9C%EB%81%88_%EA%B3%B5%EC%8B%9D
*
* */