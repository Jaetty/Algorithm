import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static char[][] map;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());
        map = new char[N+1][N*2+1];

        for(int i=0; i<N; i++){
            for(int j=0; j<2*N; j++) map[i][j] = ' ';
        }

        printStart(0,N-1,N/2);

        for(int i=0; i<N; i++){
            for(int j=0; j<2*N; j++) sb.append(map[i][j]);
            sb.append("\n");
        }

        System.out.println(sb);

    }

    static void printStart(int x, int y, int n){

        if(n==1){
            map[x][y] = '*';
            map[x+1][y-1] = '*';
            map[x+1][y+1] = '*';
            for(int i=0; i<5; i++){
                map[x+2][(y-2)+i] = '*';
            }
            return;
        }

        printStart(x,y,n/2);
        printStart(x+n,y + n,n/2);
        printStart(x+n,y - n,n/2);

    }

}
/*
    재귀를 이용한 문제로 규칙성을 찾으면 문제를 해결할 수 있다.

    N은 3*2^k로 입력이 된다. x를 세로, y를 가로로 둔다. '|'는 출력 이 시작되는 부분이라고 생각하면 된다.
         012345
        |  *    0
        | * *   1
        |*****  2
    N = 3을 입력하면 위와 같이 나오고 이 삼각형이 더이상 나눌 수 없는 가장 작은 단위이다.
    여기서 다음과 같은 사실들을 알 수 있다.
    1. 맨 위에 하나밖에 없는 꼭대기 별을 보자 꼭대기 별의 위치는 현재 x = 0이고 y = N-1인 위치에 존재한다.
    2. 가로의 길이는 N*2와 같다.
    3. 세로의 길이는 N과 같다.
    4. '* *'은 맨 위 꼭대기 별 부터 x+1 y-1, y+1 부분에 *을 입력해주면 된다.
    5. '*****'은 맨 위 꼭대기 별 부터 x+2 y-2~y+2까지 부분에 *을 입력해주면 된다.

    해당 규칙을 바탕으로 N = 6의 결과를 출력한다고 생각해보자
         0123456789AB
        |     *         0
        |    * *        1
        |   *****       2
        |  *     *      3
        | * *   * *     4
        |***** *****    5

    위와 같은 결과가 나오는데 규칙성은 이렇다.
    맨 위의 첫 삼각형의 꼭대기 별은 x=0 y=N-1의 위치에서 가장 작은 단위의 삼각형이 출력된다.

    다음 왼쪽 아래와 오른쪽 아래의 꼭대기 별의 위치는 각각
    (x = 3, y = 2), (x = 3, y = 8)에 위치해있다.

    맨 처음 꼭대기 별의 위치가 x=0, y=5 위치였는데 x는 "3"이 더해졌고, y는 +-"3"이 계산된 결과이다.
    여기서 3은 원래 N인 6에서 나누기 2를 한 값이다.

    즉 이를 토대로 식을 세우면
    왼쪽 아래는 x = x + (N/2) y = y - (N/2)
    오른쪽 아래는 x = x + (N/2) y = y + (N/2)
    라는 결과가 나온다.

    이제 이 식을 가지고 재귀를 통해 구현하면 문제를 해결할 수 있다.

* */