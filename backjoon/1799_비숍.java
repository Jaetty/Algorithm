import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static int[] answer;
    static int[][] map;
    static int[] dx = {-1,-1, 1, 1};
    static int[] dy = {-1,1,-1,1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        answer = new int[2];

        backTrack(0,0,0, 0);
        backTrack(0,1,0, 1);

        System.out.println(answer[0]+answer[1]);

    }

    static void backTrack(int row, int col, int count, int index){


        if(row>=N){
            answer[index] = Math.max(answer[index], count);
            return;
        }

        for(int c = col; c<N; c+=2){

            if(map[row][c]==1){
                map[row][c] = 2;
                if(check(row, c)) backTrack(row, c+2, count+1, index);
                map[row][c] = 1;
            }

        }

        backTrack(row+1, (index+(row+1))%2, count, index);

    }

    static boolean check(int r, int c){

        for(int i=1; i<N; i++){

            int count = 0;

            for(int j=0; j<4; j++){
                int nr = r + i * dx[j];
                int nc = c + i * dy[j];

                if(nr < 0 || nc < 0 || nr >=N || nc >= N){
                    count++;
                    continue;
                }

                if(map[nr][nc]==2){
                    return false;
                }
            }
            if(count==4) return true;
        }

        return true;

    }

}

/*
* 백트래킹 문제
*
* 해당 문제는 모든 경우를 수행하는 백트래킹 문제입니다. 해당 문제는 무려 시간제한이 10초로 매우 넉넉한 문제입니다.
* 하지만 문제를 정말 단순히 모든 경우를 수행하게 되면 최악의 입력으로 10 X 10 크기의 모든 곳에 비숍을 둘 수 있는 다음과 같은 입력이 주어지면
* 10
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* 1 1 1 1 1 1 1 1 1 1
* ANS : 18
* 아무리 10초의 넉넉한 시간제한이더라도 시간초과를 벗어나지 못할 것 입니다.
*
* 핵심적인 문제의 포인트는 바로 비숍의 이동 범위인 대각선에 있습니다.
* 5
* B W B W B
* W B W B W
* B W B W B
* W B W B W
*
* 위와 같이 5 X 5 크기의 배열이 있다고 생각해봅시다.
* 예를들어 (0,0) 위치에 비숍을 두게 되면 (1,1), (2,2), (3,3), (4,4)에는 더이상 비숍을 둘 수 없게 됩니다.
* 또한 알 수 있는 것은 어느 위치나 B의 위치에 비숍을 두게 되면 그 대각선에 위치한 B의 범위에만 비숍을 못 두게 됩니다.
* 즉 B와 W는 서로 영향을 주지 못한다는 사실을 알 수 있습니다.
* 그렇다면 W에만 놓는 것으로 가장 많이 비숍을 놓을 수 있는 경우의 수 + B에만 비숍을 가장 많이 놓을 수 있는 경우의 수를 구하려고 하면 연산 횟수를 매우 절감할 수 있습니다.
*
* 해당 포인트를 고려하여 코드를 작성하면 해당 문제를 해결할 수 있습니다.
*
* */