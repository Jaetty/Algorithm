import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][] dist;
    static final int MAX = 100_000_000;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        List<int[]>[] list;
        List<Integer> target;

        int T = Integer.parseInt(br.readLine());

        for(int tc=1;tc<=T;tc++){

            st = new StringTokenizer(br.readLine());


            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());

            st = new StringTokenizer(br.readLine());

            int s = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());

            int far = 0;

            list = new List[n+1];
            target = new ArrayList<>();
            dist = new int[2][n+1];

            for(int i=1;i<=n;i++){
                list[i] = new ArrayList<>();
                dist[0][i] = MAX;
                dist[1][i] = MAX;
            }

            for(int i=0;i<m;i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());

                if( (a==g && b==h) || (b==g && a==h) ){
                    far = d;
                }

                list[a].add(new int[]{b,d});
                list[b].add(new int[]{a,d});

            }

            for(int i=0;i<t;i++){
                target.add(Integer.parseInt(br.readLine()));
            }

            Collections.sort(target);

            dijkstra(s, list);
            dijkstra2(g, h, far, list);

            for(int i=0;i<t;i++){
                int diff = dist[1][s] + dist[1][target.get(i)] - far;

                if(dist[0][target.get(i)] == MAX || diff == MAX){
                    continue;
                }
                if(dist[0][target.get(i)] == diff){
                    sb.append(target.get(i)+" ");
                }
            }

            sb.append('\n');

        }

        System.out.print(sb.toString());

    }

    static void dijkstra(int start, List<int[]>[] list){

        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)-> e1[1]-e2[1]);
        dist[0][start] = 0;
        pq.add(new int[]{start,0});

        while(!pq.isEmpty()){

            int[] cur = pq.poll();
            if(dist[0][cur[0]]<cur[1]) continue;

            for(int[] val: list[cur[0]]){

                if(dist[0][val[0]] > val[1]+cur[1]){
                    pq.add(new int[]{val[0],val[1]+cur[1]});
                    dist[0][val[0]] = cur[1]+val[1];
                }

            }
        }

    }

    static void dijkstra2(int g, int h, int far, List<int[]>[] list){

        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)-> e1[1]-e2[1]);
        dist[1][g] = far;
        dist[1][h] = far;

        // 먼저 g와 h를 마치 diff 만큼의 거리 값을 가진 하나의 노드마냥 만들어준다.
        for(int[] val: list[g]){

            if(dist[1][val[0]] > val[1]+far){
                pq.add(new int[]{val[0],val[1]+far});
                dist[1][val[0]] = far+val[1];
            }

        }

        for(int[] val: list[h]){

            if(dist[1][val[0]] > val[1]+far){
                pq.add(new int[]{val[0],val[1]+far});
                dist[1][val[0]] = far+val[1];
            }

        }

        while(!pq.isEmpty()){

            int[] cur = pq.poll();
            if(dist[1][cur[0]]<cur[1]) continue;

            for(int[] val: list[cur[0]]){

                if(dist[1][val[0]] > val[1]+cur[1]){
                    pq.add(new int[]{val[0],val[1]+cur[1]});
                    dist[1][val[0]] = cur[1]+val[1];
                }

            }
        }

    }

}

/*

9370 미확인 도착지

다익스트라 문제

해당 문제는 s에서 시작해서 t1,t2,t3...,tn 까지 최단 경로로 도착할 때의 값과
s가 반드시 g-h 도로를 이용했을 때, t1,t2,..tn까지 도달하는 최단 경로의 값이 동일한지 묻고 있습니다.

문제의 풀이 로직은 다음과 같습니다.
1. 먼저 s에서부터 다익스트라로 모든 노드에 대한 최단 경로를 구한다.
2. g-h를 마치 하나의 노드(X)라고 가정하고 (X에서 s와 t에 가는 거리 합 - 중복값) 이 1번에서 구한 최단경로와 같은지 확인한다.

핵심은 결국 2번 로직입니다.
이 문제는 반드시 g-h 도로를 이용하라고 제시하고 있습니다. 도로를 함께 생각하면 어렵게 느껴질 수 있습니다.
그러니까 g-h를 그냥 하나의 노드 X라고 가정해봅시다.

X를 기준으로 다익스트라를 돌리면 당연하게도 반드시 X를 거친 t와 s까지의 최단 거리를 구합니다.
그렇다면 s에서 t로 가는 최단 거리는 X to s 거리 + X to t 거리 값과 같게됩니다.


이렇게 하면 s가 X를 반드시 거치면서 t로 가는 최단거리를 구할 수 있게 되고, 이를 1번 로직에서 구한 값과 비교할 수 있게 됩니다.

그렇다면 이와 같은 로직을 구현하기 위해서는 여러가지 방법이 있겠지만 저는 dijkstra2 메소드와 같이 구현했습니다.
처음 다익스트라의 초기 기준 값을 0을 주는 것을 다음과 같이 dist[g] = dist[h] = far 로 초기화한 후,
밑에 g와 h와 가까운 edge를 먼저 pq에 넣어주어 마치 하나의 노드처럼 전처리를 해주어 문제를 해결했습니다.

이렇게 1번 2번 로직을 바탕으로 구현하면 풀이할 수 있는 문제입니다.

(참고로 질문게시판을 보니 16%에서 틀리는 분들이 많아보였는데, 꿀팁을 알려드리자면
연산과 관련된 MAX 값에는 Integer.MAX_VALUE가 필요할 때 외에는 하지말고 테스트케이스에서 안나올 충분히 큰 값 정도로 넣어주는게 좋습니다.
MAX_VALUE는 +1만 해도 오버플로우를 일으켜서 음수값이 되기 때문에 코드 동작이 꼬일 가능성이 높습니다.)




*/