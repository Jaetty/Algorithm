import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static Node[] city;

    static class Node{
        int city, visible;
        List<int[]> edge;

        Node(int city, int visible){
            this.city = city;
            this.visible = visible;
            edge = new ArrayList<>();
        }
    }

    static long[] dist;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        city = new Node[N];
        dist = new long[N];
        st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            city[i] = new Node(i, Integer.parseInt(st.nextToken()));
            dist[i] = Long.MAX_VALUE;
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            city[s].edge.add(new int[]{e,w});
            city[e].edge.add(new int[]{s,w});
        }

        dijkstra(N);

        System.out.print( dist[N-1] == Long.MAX_VALUE ? -1 : dist[N-1] );

    }

    static void dijkstra(int N){

        boolean[] visited = new boolean[N];
        dist[0] = 0;

        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>((e1,e2)->Long.compare(e1[1],e2[1]));

        priorityQueue.add(new long[] {0,0});

        while(!priorityQueue.isEmpty()){

            long[] ec = priorityQueue.poll();

            if(visited[(int)ec[0]] || ec[1] > dist[(int)ec[0]]) continue;
            visited[0] = true;

            for(int[] edge : city[(int)ec[0]].edge){

                if(dist[edge[0]] > ec[1] + edge[1]){
                    dist[edge[0]] = ec[1] + edge[1];
                    if(city[edge[0]].visible != 1 && !visited[edge[0]]){
                        priorityQueue.add(new long[] {edge[0], dist[edge[0]]});
                    }
                }
            }
        }

    }

}

/*
 * 다익스트라 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 시야에 보이는 분기점으로는 진행이 불가하다. (유일하게 가능한 것은 마지막 N-1 분기인 넥서스 뿐이다.)
 * 2. N <= 100_000, cost <= 100,000
 * 3. 간선은 무방향이다.
 *
 * 문제의 핵심은 다익스트라로 풀이하되 다음 분기가 진행할 수 없는 분기라면 dist 값만 수정해주고 priorityQueue에 넣어주지 않으면 된다는 점 입니다.
 * 또한 2번 포인트로 알 수 있는 점은 거리의 최대 값이 int 범위를 벗어날 수 있다는 점입니다.
 *
 * 이 부분들에만 유의해서 기초적인 다익스트라 알고리즘을 적용하면 풀이가 가능합니다.
 *
 *
 * */