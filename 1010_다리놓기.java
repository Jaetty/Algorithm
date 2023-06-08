import java.util.*;
import java.io.*;

public class Main {

    static int[][] arr;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        arr = new int[31][31];

        init();

        int T = Integer.parseInt(br.readLine());

        for(int i=0; i<T; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            sb.append(arr[N][M]+"\n");
        }

        System.out.println(sb);

    }

    static void init(){

        for(int i=1; i<=30; i++){
            arr[1][i] = i;
        }

        for(int i=2; i<=30; i++){
            for(int j=i; j<=30; j++){
                if(j==i) arr[i][j] = 1;
                else{
                    arr[i][j] = arr[i-1][j-1] + arr[i][j-1];
                }
            }
        }

    }

}

/*
* 백준 문제 번호는 1010 다리놓기
* 문제를 풀어보니
* M = 3, N = 4 라고 한다면 arr[3][4] = arr[2][3] + arr[3][3] 이런 식으로 규칙 발견
* 즉 arr[M][N] = arr[M-1][N-1] + arr[M][N-1]로 식을 짜서 해결
* */