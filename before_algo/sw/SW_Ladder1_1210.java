package sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SW_Ladder1_1210 {
    // 왼 - 오 - 위
    static int[] dy = { 0, 0, -1 };
    static int[] dx = {-1, 1,  0 };
    static int[][] ladder = new int[100][100];

    static int sy, sx, ans;
    public static void main(String[] args) throws Exception{
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int t = 1; t <= 10; t++) {
            // ladder 초기화 필요 X 매번 테케 처리시 입력으로 다시 초기화 자동으로
            // 첫 번째 테케 번호는 버린다.
            br.readLine();

            for (int i = 0; i < 100; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 100; j++) {
                    ladder[i][j] = Integer.parseInt(st.nextToken()); 
//                    ladder[i][j] = st.nextToken().charAt(0) - '0';
//                    if( ladder[i][j] == 2 ) {
//                        sy = i;
//                        sx = j;
//                    }
                }
            }

            // sy, sx 를 따로 ladder 에서 찾는 방법
            sy = 99;
            for (int i = 0; i < 100; i++) {
                if( ladder[99][i] == 2 ) sx = i;
            }

            // 탐색 시작
            // 시작 방향은 위
            int dir = 2;

            while(true) {
                // 위로 탐색 할 경우, 왼, 오 우선 -외, 오 이동할 수 없으면 계속 위
                if( dir == 2 ) {

                    for (int d = 0; d < 3; d++) {
                        int ny = sy + dy[d];
                        int nx = sx + dx[d];

                        if( ny >= 0 && nx >= 0 && nx < 100 && ladder[ny][nx] == 1 ) {
                            sy = ny;
                            sx = nx;
                            dir = d; // 방향 전환(그대로일 수도)
                            break; // 더 for 문 반복할 필요 X
                        }
                    }
                }else if( dir == 0 || dir == 1 ) { // 왼쪽 또는 오른쪽으로 탐색 할 경우, 위 우선, 위로 이동할 수 없으면 계속 왼, 오 방향으로 이동
                    // 위로 가는 좌표를 우선
                    int ny = sy + dy[2];
                    int nx = sx + dx[2];

                    if( ny >= 0 && ladder[ny][nx] == 1 ) { // 위로 갈 수 있으면
                        sy = ny;
                        sx = nx;
                        dir = 2;
                    }else {
                        sy = sy + dy[dir];
                        sx = sx + dx[dir];
                    }
                }

                if( sy == 0 ) {
                    ans = sx;
                    break;
                }
            } // while

            System.out.println("#" + t + " " + ans );
        }

}
}