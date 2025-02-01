import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][] map;
    static int[][][] dp;
    static int N, answer, allMark;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        dp = new int[N][1<<N][10];

        allMark = (1<<N)-1;

        for(int i=0; i<N; i++){

            String str = br.readLine();

            for(int j=0; j<N; j++){
                map[i][j] = str.charAt(j)-'0';
            }

        }

        System.out.print(tsp(1, 0,1,0));

    }

    static int tsp(int depth, int x, int mark, int price){

        if(mark ==  allMark){
            return depth;
        }

        if(dp[x][mark][price] != 0){
            return dp[x][mark][price];
        }

        dp[x][mark][price] = depth;

        for(int i=0; i<N; i++){

            if( map[x][i] < price || (mark & 1<<i) != 0 ) continue;

            dp[x][mark][price] = Math.max(dp[x][mark][price], tsp(depth+1, i, (mark | 1<<i), map[x][i]));

        }

        return dp[x][mark][price];
    }

}

/*
 * 1029 그림 교환
 *
 * 외판원 순회 문제
 *
 * @ 이 문제는 외판원 순회 응용 문제입니다. tsp 알고리즘을 모르신다면 먼저 2098 외판원 순회 문제를 풀이하셔야합니다.
 *
 * 문제는 전형적인 외판원 순회인데 약간의 응용이 있습니다. 차이점은 다음과 같습니다.
 * 1. 외판원 순회의 경우 가장 작은 비용을 원하지만 이 문제의 경우 가장 많이 소유된 횟수를 묻고 있습니다.
 * 2. 외판원 순회의 경우 최소의 값이 단 하나만 존재합니다. 하지만 이 문제는 어떤 경로를 택했는지에 따라 최소값이 달라질 수 있습니다.
 *
 * 1번의 경우 dp에 저장하는 값을 재귀로 얼마나 깊게 들어갔는가를 기억해두면 해결할 수 있다는 점을 알 수 있습니다.
 * 2번의 경우는 저도 늦게 깨달았는데, 아래의 반례를 보시면 알 수 있습니다.
 *
 * 5
 * 01199
 * 00530
 * 02060
 * 00004
 * 00000
 *
 * 정답 : 5
 *
 * 이 경우 dp 배열을 2차원 배열로만 풀이를 하게 된다면 정답이 4가 나올 것 입니다.
 * 왜냐하면 1->2->3->4로 이동하게 되면 최대 가격은 6이 됩니다.
 * 4에서부터 6이상으로 갈 수 있는 곳이 없으므로 dp[4][mark]의 값은 4로 고정되어버립니다.
 *
 * 하지만 정답은 1->3->2->4->5 입니다.
 * 이 경우는 4위치에 도달했을 때 최대 가격이 4이므로 5로 갈 수 있게 됩니다.
 *
 * 그렇다면 이를 해결하려면 어떤 가격으로 그 위치에 도달했는지 기억하게 하면 됩니다.
 * 최대 가격 4원인 상태에서 4에 도착했다면 최대 1칸 더 갈 수 있다. 이런식으로 기억하게 하면 문제를 해결할 수 있게 됩니다.
 *
 * */