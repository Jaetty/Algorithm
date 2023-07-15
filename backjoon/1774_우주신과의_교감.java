import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static List<Point> points;
    static int[] parents;
    static class Point{
        int x, y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    static class Path{
        int a, b;
        double cost;

        Path(int a, int b, double cost){
            this.a = a;
            this.b = b;
            this.cost = cost;
        }
    }

    static PriorityQueue<Path> pq;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        points = new ArrayList<>();
        points.add(new Point(0,0));
        pq = new PriorityQueue<Path>(Comparator.comparingDouble(o->o.cost));

        parents = new int[N+1];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            points.add(new Point(a,b));
        }

        makeSet();

        double answer = 0;
        int count = 0;

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if(unionFind(a,b)){
                unionSet(a,b);
                count++;
            }
        }

        for(int i=1; i<=N; i++){
            for(int j=i+1; j<=N; j++){
                if(!unionFind(i,j)) continue;
                double cost = makePath(points.get(i),points.get(j));
                pq.add(new Path(i,j,cost));
            }
        }

        while(count<N-1){
            Path path = pq.poll();
            if(unionFind(path.a, path.b)){
                unionSet(path.a, path.b);
                answer += path.cost;
                count++;
            }
        }

        System.out.printf("%.2f",1d*answer);

    }

    static double makePath(Point a, Point b){
        return Math.sqrt(Math.pow((b.x-a.x),2) + Math.pow(b.y-a.y,2));

    }

    static void makeSet(){
        for(int i=1; i<=N; i++){
            parents[i] = i;
        }
    }

    static int findSet(int x){
        if(parents[x]==x) return x;
        return parents[x] = findSet(parents[x]);
    }

    static boolean unionFind(int x, int y){
        int nx = findSet(x);
        int ny = findSet(y);

        if(nx==ny) return false;
        return true;
    }

    static void unionSet(int x, int y){
        parents[findSet(x)] = findSet(y);
    }

}

/*
 * 최소 스패닝 트리(MST) 문제
 *
 * 해당 문제는 최소 스패닝 트리 문제로 크루스칼로 풀이하였습니다.
 * 문제의 포인트는 다음과 같습니다.
 * 1. 각 노드의 위치가 2차원 좌표로 나타나있다.
 * 2. 이미 연결된 통로가 있다. (이것은 비용으로 처리하지 않아도 된다)
 *
 * 일단 MST 알고리즘을 적용하려면 각 노드 간의 edge와 비용을 알아야합니다.
 * 여기서 1번 포인트를 통해 2차원 좌표끼리의 거리를 구하는 공식을 알면 비용을 구할 수 있다는 점을 알 수 있습니다.
 * 여기서 사용된 공식이 피타고라스 정리로 삼각형의 빗변을 구하면 그것이 거리임을 알 수 있습니다.
 * 즉 (x1,y1), (x2,y2)라는 두 좌표의 거리를 구하려면
 * (x2,y1)라는 좌표가 있다고 생각하면 삼각형이 되고 여기에 피타고라스 정리를 넣으면
 * 비용 = sqrt((x2-x1)^2 + (y2-y1)^2)
 * 이렇게 공식을 추출할 수 있습니다.
 * 여기서 결과가 double 형이라는 것을 알 수 있습니다. 그렇기 때문에 크루스칼로 진행할 때는 double형끼리 크기를 비교할 수 있게 만들어줘야합니다.
 *
 * 다음으로 2번 포인트를 통해서 비용에 상관없이 미리 통로들을 유니온파인드를 통해 연결시켜줍니다.
 * 그 후 서로 연결되지 않은 비용을 모두 구한 후 크루스칼을 통해 구현을 진행하면 문제를 해결할 수 있습니다.
 *
 * */