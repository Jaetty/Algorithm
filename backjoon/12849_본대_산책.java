import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int D = Integer.parseInt(br.readLine());

        long[][] map = new long[D+1][8];
        long mod = 1_000_000_007;
        map[0][0] = 1;

        for(int i=0; i<D; i++){

            map[i+1][0] = (map[i][1] + map[i][2]) % mod;
            map[i+1][1] = (map[i][0] + map[i][2] + map[i][3]) % mod;
            map[i+1][2] = (map[i][0] + map[i][1] + map[i][3] + map[i][4]) % mod;
            map[i+1][3] = (map[i][1] + map[i][2] + map[i][4] + map[i][5]) % mod;
            map[i+1][4] = (map[i][2] + map[i][3] + map[i][5] + map[i][6]) % mod;
            map[i+1][5] = (map[i][3] + map[i][4] + map[i][7]) % mod;
            map[i+1][6] = (map[i][4] + map[i][7]) % mod;
            map[i+1][7] = (map[i][5] + map[i][6]) % mod;

        }

        System.out.println(map[D][0]);

    }

}

/*
 * 12849 본대 산책
 *
 * 다이나믹 프로그래밍 문제
 *
 * 해당 문제의 경우 다음과 같이 문제를 제시하는데,
 * 1. D <= 100,000
 * 2. D만큼 움직인다고 했을 때 모든 가능한 첫 위치로 돌아올 수 있는 경로의 수 구하기.
 *
 * BFS로는 모든 경우의 수를 따질 경우 시간초과, 공간 초과를 벗어날 수 없다는 점을 어림짐작 할 수 있습니다.
 * 애초에 그래프에서 벗어나서 단순히 배열로 움직임의 진행을 생각해봤을 때,
 *
 * 정보과학관 : 0, 전산관 : 1, 미래관 : 2, 신양관 : 3, 환경직기념관 : 4, 진리관 : 5, 형남공학관 : 6, 학생회관 : 7 라고 하면
 *
 * 0의 위치에서 1분의 시간을 주면 인접한 1,2 위치에 도착할 수 있습니다.
 * 이걸 식으로 나타내면 [1분일 때][1의 위치]와 [1분][2위치] = [0분][0위치]를 하고 있습니다.
 * 2분의 시간을 주면 0 -> {1, 2}, 1-> {0,2,3}, 2-> {0,1,3,4}의 위치로 이동할 수 있습니다.
 *
 *
 * 그렇다면 이를 활용하면 이미 1분이 지날 때, 0에서 1과 2로 움직였던 적이 있습니다.
 * 그러면 [2분][0위치] = [1분][1위치]의 값 + [1분][2위치]의 값.
 *
 * 즉 점화식은 map[시간][위치] = ( 인접한 위치의 바로 직전 시간에 도달한 경우의 수 총합 ) % 100..07이 됩니다.
 * 문제는 0으로 되돌아왔을 때의 숫자를 묻고 있기 때문에 [D분 후의][0위치]의 값을 리턴하면 풀이할 수 있게 됩니다.
 *
 * */