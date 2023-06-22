import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static long[][] arr;
    static int N;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        long B = Long.parseLong(st.nextToken());

        arr = new long[N][N];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        long[][] val = calc(arr, B);

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                sb.append(val[i][j]%1000 + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    static long[][] calc(long[][] val, long B){
        if(B==1){
            return arr;
        }

        long[][] result = calc(val,B/2);

        result = time(result, result);

        if(B%2==1) result = time(result, arr);

        return result;
    }

    static long[][] time(long[][] a, long[][] b){

        long[][] result = new long[N][N];

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                long sum = 0;
                for(int k=0; k<N; k++){
                    sum += (a[i][k]  * b[k][j])%1000;
                }
                result[i][j] = sum%1000;
            }
        }
        return result;
    }

}
/*
*
* 분할정복을 이용한 거듭제곱 응용 문제
* 행렬을 거듭제곱하는 방법이다. 이것보다 간단한 문제는 1629 곱셈 이라는 문제가 있는데 이 문제를 풀면 이해하기 쉽다.
*
* 간단하게 제곱의 성질을 행렬 A를 통해 알아보자
* A^6 = A^3 * A^3
* A^3 = A * A * A
*
* 이것을 N으로 나타내면
* (N % 2 == 0) 일 때
* A^N = A^N/2 * A^N/2
* (N % 2 == 1) 일 때
* A^N = A^N/2 * A^N/2 * A
*
* 그렇다면 재귀적으로 A^N/2 값을 구했으면 이를 제곱해주고 홀수라면 A를 곱해주면 된다.
*
* A^15 라면 다음과 같이 진행된다.
* A^15 = A^7 * A^7 * A
* A^7 = A^3 * A^3 * A
* A^3 = A^3/2 * A^3/2 * A
*
* 이때 3/2 = 1 즉 B가 1이므로 A를 리턴하고
* A * A를 수행 한다. B=3 홀수이므로 A를 다시 한번 곱해준다.
* A^3을 구했으므로 A^3을 곱한다. B=7 홀수이므로 A를 다시 곱해준다.
* A^7을 구했고 B=15 홀수 이므로 A^7 * A^7 * A 를 해주고 리턴한다.
*
*
* */