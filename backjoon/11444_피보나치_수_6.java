import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {
    static final long[][] A = {{1,1},{1,0}};
    static long N;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Long.parseLong(br.readLine());

        long[][] val = calc(N);

        System.out.println(val[0][1]);
    }

    static long[][] calc(long B){
        if(B==1){
            return A;
        }

        long[][] result = calc(B/2);

        result = time(result, result);

        if(B%2==1) result = time(result, A);

        return result;
    }

    static long[][] time(long[][] a, long[][] b){

        long[][] result = new long[2][2];

        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                long sum = 0;
                for(int k=0; k<2; k++){
                    sum += (a[i][k]  * b[k][j])%1_000_000_007;
                }
                result[i][j] = sum%1_000_000_007;
            }
        }
        return result;
    }

}
/*
* 분할 정복 응용 문제
* !!! 해당 문제는 풀지 못해 풀이를 참고하였습니다. !!!
* 결국 규칙성에 대한 풀이를 보았기 때문에 과정에 대해서는 스스로 설명드릴 수 없지만
* 결론은 {{1,1},{1,0}}의 행렬을 n번 제곱하면 {{F[n+1], F[N]},{F[N], F[N-1]}} 의 결과가 나온다고 요약 할 수 있습니다.
*
* 만약 행렬을 제곱하는 방법에 대해서 잘 모르시면 10830 행렬제곱 문제를 풀어보시면 쉽게 이 문제를 구현할 수 있습니다.
*
* */