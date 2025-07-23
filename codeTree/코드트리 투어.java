import java.io.*;
import java.util.*;

public class Main {

    static int S, N, M, idxCnt;
    static int[][] dist;
    static final int INF = 30000*100;

    static class Travel_item{
        int id, revenue, destination;

        Travel_item(int id, int revenue, int destination){
            this.id = id;
            this.revenue = revenue;
            this.destination = destination;
        }

    }

    static Set<Integer> ids;
    static Travel_item[] items;
    static Map<Integer, Integer> itemIdx;
    static Set<Integer> connected[];
    static int[][] edges;
    static PriorityQueue<Travel_item> candidate = new PriorityQueue<>((e1, e2)->{

        if(e1.revenue == e2.revenue){
            return e1.id - e2.id;
        }
        return e2.revenue-e1.revenue;
    });

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        items = new Travel_item[30001];
        dist = new int[16][2000];
        itemIdx = new HashMap<>();
        connected = new HashSet[2000];
        ids = new HashSet<>();

        for(int i=0; i<16; i++){
            for(int j=0; j<2000; j++){
                dist[i][j] = INF;
            }
        }

        int Q = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        for(int q=0; q<Q; q++){

            StringTokenizer st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());

            if(cmd == 100){

                N = Integer.parseInt(st.nextToken());
                M = Integer.parseInt(st.nextToken());
                idxCnt = 0;

                edges = new int[N][N];

                for(int i=0; i<N; i++){
                    connected[i] = new HashSet<>();
                    for(int j=0; j<N; j++){
                        if(i==j) continue;
                        edges[i][j] = INF;
                    }
                }

                for(int i=0; i<M; i++){
                    int v = Integer.parseInt(st.nextToken());
                    int w = Integer.parseInt(st.nextToken());
                    int d = Integer.parseInt(st.nextToken());

                    edges[w][v] = edges[v][w] = Math.min(d, edges[v][w]);
                    connected[w].add(v);
                    connected[v].add(w);

                }

                dijkstra(0);

            } else if (cmd == 200) {
                int id = Integer.parseInt(st.nextToken());
                int revenue = Integer.parseInt(st.nextToken());
                int dest = Integer.parseInt(st.nextToken());

                items[id] = new Travel_item(id, revenue, dest);
                if(revenue - dist[S][dest] >= 0){
                    candidate.add(new Travel_item(id, revenue - dist[S][dest], dest));
                }
                ids.add(id);

            }
            else if(cmd == 300){
                int id = Integer.parseInt(st.nextToken());
                items[id] = null;
                ids.remove(id);
            }
            else if(cmd == 400){

                int answer = -1;
                while(!candidate.isEmpty()){

                    Travel_item t = candidate.poll();
                    if(!ids.contains(t.id)) {
                        continue;
                    }

                    answer = t.id;
                    break;

                }

                if(answer != -1){
                    sb.append(answer);
                    items[answer] = null;
                    ids.remove(answer);
                }
                else{
                    sb.append(-1);
                }
                sb.append('\n');

            }
            else{

                dijkstra(Integer.parseInt(st.nextToken()));
                setPq();

            }

        }

        System.out.print(sb.toString());

    }

    static void dijkstra(int start){

        if(itemIdx.containsKey(start)){
            S = itemIdx.get(start);
            return;
        }

        int sId = idxCnt++;
        itemIdx.put(start, sId);
        S = sId;

        PriorityQueue<int[]> pq = new PriorityQueue<>((e1,e2)->e1[1]-e2[1]);
        pq.add(new int[] {start, 0});

        while(!pq.isEmpty()){

            int[] cur = pq.poll();

            if(dist[sId][cur[0]] < cur[1]) continue;
            dist[sId][cur[0]] = cur[1];

            for(int next : connected[cur[0]]){

                int newDist = cur[1] + edges[cur[0]][next];

                if(dist[sId][next] > newDist){
                    pq.add(new int[] {next, newDist});
                }

            }
        }

    }

    static void setPq(){

        candidate.clear();

        for(int id : ids){
            Travel_item item = items[id];
            int benefit = item.revenue - dist[S][item.destination];
            if(benefit >= 0){
                candidate.add(new Travel_item(item.id, benefit, item.destination));
            }
        }

    }

}

