import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static final int INF = 10000*55*55;
    static int[][] dist;
    static char[][] map;
    static Queue<int[]> gq;
    static int[][] edges;
    static int[] start, end;
    static int[] dr = {-1,0,1,0};
    static int[] dc = {0,1,0,-1};

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dist = new int[N][M];
        edges = new int[N][M];
        map = new char[N][M];
        start = new int[] {0,0};
        end = new int[] {0,0};
        gq = new ArrayDeque<>();

        for(int r=0; r<N; r++) {
            String input = br.readLine();

            for(int c=0; c<M; c++){
                dist[r][c] = INF;

                if(input.charAt(c) == 'S'){
                    start[0] = r;
                    start[1] = c;
                }
                else if(input.charAt(c) == 'F'){
                    end[0] = r;
                    end[1] = c;
                }
                else if(input.charAt(c) == 'g'){
                    gq.add(new int[]{r,c});
                }

                map[r][c] = input.charAt(c);

            }
        }

        while(!gq.isEmpty()){
            int [] rc = gq.poll();
            setCost(rc[0], rc[1], 10000);
        }

        dijkstra();

        System.out.println(dist[end[0]][end[1]]/10000 + " " + dist[end[0]][end[1]]%10000 );

    }

    static void setCost(int r, int c, int cost){

        edges[r][c] = cost;

        if(cost>1){
            for(int d=0; d<4; d++){
                int nr = r+dr[d];
                int nc = c+dc[d];

                if(nr < 0 || nr >= N || nc < 0 || nc >= M) continue;

                if(map[nr][nc] == '.'){
                    setCost(nr, nc, 1);
                }
            }
        }

    }

    static void dijkstra(){

        dist[start[0]][start[1]] = 0;
        // 현재 r 위치, c 위치, 현재 최소 도착 값
        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)-> e1[2] - e2[2]);
        pq.add(new int[]{start[0],start[1], 0});

        while(!pq.isEmpty()){

            int[] cur = pq.poll();
            if(cur[2] > dist[cur[0]][cur[1]]){
                continue;
            }

            int cost = cur[2] + edges[cur[0]][cur[1]];

            for(int d=0; d<4; d++){

                int nr = cur[0]+dr[d];
                int nc = cur[1]+dc[d];

                if(nr < 0 || nr >= N || nc < 0 || nc >= M || dist[nr][nc] <= cost) continue;
                dist[nr][nc] = cost;
                pq.add(new int[]{nr,nc, cost});

            }


        }


    }

}

/*

1445 일요일 아침의 데이트

다익스트라 문제

해당 문제의 핵심 포인트는 다음과 같습니다.
1. 최대한 쓰레기 칸을 '지나는 것을' 피해서 움직여야 한다.
2. 쓰레기 상하좌우 자리가 비어있는('.')칸도 '지나는 것을' 최대한 피한다.

먼저 문제를 풀이하기 전 헷갈릴 수 있는 부분들을 재대로 정의하고 가야합니다.

1. 어떤 칸을 '밟을 때(도착할 때)'가 아니라 어떤 칸을 '지나가는 것을' 피한다라는 점에 유념해야합니다.
즉, 어떤 장소에 도착하는 것 자체는 비용이 안나올 수 있지만 그 장소에서 다음 칸으로 넘어갈 때 비용이 발생한다는 것 입니다.

2. 쓰레기가 주변에 몇개 있는지를 세는 것이 아닙니다! '쓰레기가 주변에 있는 땅을 몇번 지났는지 아는 것'에 유념해야합니다.
예를 들어, g.g와 같이 양옆에 쓰레기가 있는 칸을 지난다면 비용은 1 증가입니다. (주변 쓰레기 개수가 아니라 쓰레기가 주변에 있는 땅에 집중)

문제를 풀이하게 된다면, 최소 비용으로 S에서 F에 도착해야하므로 다익스트라임을 금방 알 수 있습니다.
그렇다면, 비용을 어떻게 처리하느냐를 정의해야합니다.

저는 쓰레기가 있는 곳에서 다른 장소로 넘어갈 때 비용은 10000, 쓰레기가 주변에 있는 땅에서 다른 장소로 넘어갈 때 비용을 1로 설정했습니다.
(10000으로 설정하면 50*50땅의 비용이 1이라고 하고 이를 다 밟아도 도달할 수 없는 값이기 때문에 쓰레기 비용은 10000으로 했습니다.)

이후 다익스트라로 S부터 F까지 최단 경로로 도착하게 된다면 F 위치에 도착할 때 최단 비용을 가져옵니다.
이 비용을 /10000 해주면 쓰레기칸을 지난 횟수를 알 수 있고, %10000을 해주면 쓰레기 주변 칸을 지난 횟수를 알 수 있습니다.

이를 각각 출력해주면 문제를 풀이할 수 있습니다.

*/