import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int[][] map;
    static List<int[]> zero;
    static int rightBit;
    static boolean ok;


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        map = new int[9][9];
        zero = new ArrayList<>();

        rightBit = 0;

        ok = false;

        for (int i=0; i<9; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j=0; j<9; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j]==0) zero.add(new int[]{i,j});
            }
            rightBit ^= (int)Math.pow(2,i+1);
        }

        backTrack(0);

        if(ok){
            for (int i=0; i<9; i++){
                for (int j=0; j<9; j++){
                    sb.append(map[i][j]+" ");
                }
                sb.append("\n");
            }

            System.out.println(sb);
        }


    }

    static void backTrack(int depth){

        if(depth== zero.size()){
            ok = true;
            return;
        }

        int[] point = zero.get(depth);

        boolean[] possibleNumbers = new boolean[10];

        for(int j=0; j<9; j++){
            possibleNumbers[map[point[0]][j]] = true;
            possibleNumbers[map[j][point[1]]] = true;
        }

        int row = point[0] - point[0]%3;
        int col = point[1] - point[1]%3;

        for(int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                possibleNumbers[map[row+r][col+c]] = true;
            }
        }

        for(int i=1; i<=9; i++){
            if(possibleNumbers[i]) continue;
            map[point[0]][point[1]] = i;
            backTrack(depth+1);
            if(ok) return;
            map[point[0]][point[1]] = 0;
        }

    }


}
/*
*
* 백트래킹 문제
* 스도쿠의 규칙은 빈칸이 속한 가로줄에 겹치는 숫자가 없고 세로줄에 겹치는 숫자가 없고
* 3*3크기의 격자에 겹치는 숫자가 없어야한다.
*
* 단순하게 생각해보면 모든 숫자를 다 넣어서 마지막에 체크를 해줄 수 있지만 이는 시간이 너무 오래 걸린다.
* 그래서 빈칸에 숫자를 넣기 전에 규칙을 모두 충족한 숫자만 넣게 해주면 모든 경우의 수를 돌 필요가 없어진다.
* 가로와 세로를 판단하는 방법은 쉽지만 좌표가 주어졌을 때 그 좌표가 속한 3*3 격자의 숫자를 가져오는 방법은 규칙성을 찾아야한다.
*
* 이 방법에 대해서 가장 왼쪽 위 부분에서부터 3*3격자를 조회하는 방법을 사용한다.
* 만약 빈 값이 있는 위치가 (7,6) 이라고 한다면 (6,6) 값을 찾아내기만 하면 된다.
* 방법은 %3을 이용하여 (7,6)의 경우 7%3 = 1, 6%3=0이고 여기서 (7-1, 6-0) 이므로 (6,6) 을 찾을 수 있다.
*
* 이제 위의 방법으로 규칙성이 올바른 숫자만을 대입해보며 진행하게 된다.
* 이 문제의 전제는 "스도쿠가 완성되지 못하는 입력값이 주어지지 않는다" 이므로
* 재귀의 마지막 깊이에 도달하면 스도쿠가 완성되었다는 뜻이므로 출력하고 종료해준다.
*
* */