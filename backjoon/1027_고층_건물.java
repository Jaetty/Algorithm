import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static int[] y;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        y = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            y[i] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;

        for (int x1 = 0; x1 < N; x1++) {
            answer = Math.max(answer, getCnt(x1));
        }

        System.out.print(answer);

    }

    private static int getCnt(int x1) {
        int cnt = 0;
        double standard = 0;

        for(int x2=x1-1; x2>=0; x2--) {
            double slope = ((double) y[x2] - (double) y[x1]) / ( (double)x2 - (double)x1);

            if(x2 == x1-1 || standard > slope) {
                cnt++;
                standard = slope;
            }
        }

        for(int x2=x1+1; x2<N; x2++) {
            double slope = ((double) y[x2] - (double) y[x1]) / ( (double)x2 - (double)x1);

            if( x2 == x1+1 || standard < slope) {
                cnt++;
                standard = slope;
            }
        }

        return cnt;
    }
}

/*

1027 고층 건물

기하학 + 브루트포스 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. 각 위치에서부터 좌 우의 건물들의 모든 기울기를 구해야한다.
1-2. 내 위치에서 왼쪽에 있는 건물들은 비교 중 나왔던 가장 작았던 기울기보다 더 작아야만 한다.
1-3. 내 위치에서 오른쪽에 있는 건물들은 비교 중 나왔던 가장 컸던 기울기보다 더 커야한다.
1-4. 위 1-2, 1-3 과정이 충족 될 때 마다 count 값을 증가시켜준다.
2. 1번 과정을 다 거치고 난 후 가장 높았던 count값을 출력한다.

해당 문제는 선분의 기울기의 비교를 할 줄 알면 풀이할 수 있는 문제입니다.
즉, 다음의 식을 이용해서
double slope = ((double) y[x2] - (double) y[x1]) / ( (double)x2 - (double)x1);으로
모든 위치에 대해 기울기를 따져가며 count를 한다면 쉽게 풀이할 수 있습니다.

*/