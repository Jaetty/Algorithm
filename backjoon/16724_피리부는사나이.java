import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static int N,M,answer;
    static char[][] map;
    static int[][] visit;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];
        visit = new int[N][M];
        answer = 0;

        for(int i=0; i<N; i++){
            String str = br.readLine();
            map[i] = str.toCharArray();
        }

        int index = 1;

        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){

                if(visit[i][j]>0) continue;
                search(i,j,index++);

            }
        }

        System.out.println(answer++);

    }

    static void search(int r, int c, int index){

        int nr = r;
        int nc = c;

        while(true){

            if(visit[nr][nc]==index){
                answer++;
                return;
            }

            if(visit[nr][nc]>0) {
                return;
            }

            visit[nr][nc] = index;

            switch (map[nr][nc]){
                case 'U' : nr--; break;
                case 'D' : nr++; break;
                case 'L' : nc--; break;
                case 'R' : nc++; break;
            }

        }

    }

}
/*
 * 그래프 탐색 문제
 * 이 문제의 포인트는 사이클이 새롭게 만들어질때만 정답 카운트를 1 올려주면 됩니다.
 * 그렇다면 한 번 탐색을 할 때 고유의 id값을 주어 탐색할 때마다 숫자를 부여하되 탐색해서 나온 숫자가 id값과 다르면 카운트를 올려주면 해결됩니다.
 *
 * 필자는 arr이라는 int 이차원 배열을 만들어서 탐색과 같이 index라는 고유의 id값을 각 탐색때마다 해당 배열 위치에 부여했고
 * 탐색 중 만난 숫자가 0 보다 크면 다른 값이 들어갔다는 것이고 이 값이 id값과 같으면 한 주기를 돌았다는 뜻이기 때문에 answer 변수의 값을 1 올려주었습니다.
 *
 */