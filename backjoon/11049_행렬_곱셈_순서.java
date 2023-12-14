import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static int[][] inputs;
    static int[][] answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        inputs = new int[N][2];
        answer = new int[N][N];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            inputs[i][0] = Integer.parseInt(st.nextToken());
            inputs[i][1] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<N-1; i++){
            answer[i][i+1] = inputs[i][0] * inputs[i][1] * inputs[i+1][1];
        }

        for(int next=2; next<N; next++){

            for(int i=0; i+next<N; i++){

                int j = next+i;
                answer[i][j] = Integer.MAX_VALUE;

                for(int k = i; k<j; k++){
                    answer[i][j] = Math.min(answer[i][j], answer[i][k] + answer[k+1][j] + (inputs[i][0] * inputs[k][1] * inputs[j][1]));
                }

            }
        }

        System.out.println(answer[0][N-1]);

    }

}

/*
* 다이나믹 프로그래밍 문제
*
* 해당 문제는 포인트는 다음과 같습니다.
* 1. 항상 순서대로 곱셈을 할 수 있는 크기만 주어진다.
* 2. 곱하는 순서가 다르면 곱셈의 연산 수가 달라진다.
*
* 생각해보면 입력으로 서로 곱해지면서 크기가 다른 행렬 A,B,C,D의 행렬이 들어왔다고 하겠습니다.
* 그러면 A를 D까지 곱하려면 1번 포인트에 따라서 A X (B X (C X D)) 와 같은 형태가 되거나
* (A X B) X (C X D) 가 되거나, ( (A X B) X C) X D... 등등의 방법으로 곱하기가 될 것입니다.
*
* 그렇다면 이 모든 경우의 수를 다 고려해주되 각각의 경우를 처음부터 일일히 다시 계산하는 것이 아닌
* 메모이제이션으로 최소값을 갱신하는 방법으로 수행하여 주면 해결할 수 있습니다.
*
*
*
* */