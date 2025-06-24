import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Point {
        int x1, y1, x2, y2;
    }

    static int N;
    static int[] size, parent;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        parent = new int[N+1];
        size = new int[N+1];
        Point[] points = new Point[N+1];

        for (int i = 1; i <= N; i++) {

            points[i] = new Point();
            parent[i] = i;
            size[i] = 1;

            st = new StringTokenizer(br.readLine());
            points[i].x1 = Integer.parseInt(st.nextToken());
            points[i].y1 = Integer.parseInt(st.nextToken());
            points[i].x2 = Integer.parseInt(st.nextToken());
            points[i].y2 = Integer.parseInt(st.nextToken());
        }


        for(int i=1; i<=N; i++){

            for(int j=i+1; j<=N; j++){

                int fx = find(i);
                int fy = find(j);

                if(fx == fy){
                    continue;
                }

                if(crossCheck(points[i], points[j])){
                    union(fx, fy);
                }

            }

        }

        Set<Integer> count = new HashSet<>();
        int max = 0;

        for(int i=1; i<=N; i++){
            count.add(find(i));
            max = Math.max(max, size[parent[i]]);
        }

        System.out.println(count.size());
        System.out.println(max);


    }

    static int find(int x) {
        if(parent[x] == x ) return x;
        return parent[x] = find(parent[x]);
    }

    static void union(int fx, int fy) {

        // Small to Large trick
        if(size[fx] >= size[fy]) {
            parent[fy] = fx;
            size[fx] += size[fy];
        }
        else{
            parent[fx] = fy;
            size[fy] += size[fx];
        }

    }

    static boolean crossCheck(Point a, Point b) {

        int ccw1 = ccw(a.x1, a.y1, a.x2, a.y2, b.x1, b.y1) * ccw(a.x1, a.y1, a.x2, a.y2, b.x2, b.y2);
        int ccw2 = ccw(b.x1, b.y1, b.x2, b.y2, a.x1, a.y1) * ccw(b.x1, b.y1, b.x2, b.y2, a.x2, a.y2);

        if(ccw1 <= 0 && ccw2 <= 0) {

            if(ccw1 == 0 && ccw2 == 0) {

                boolean flag1 = Math.min(a.x1, a.x2) <= Math.max(b.x1, b.x2);
                boolean flag2 = Math.min(b.x1, b.x2) <= Math.max(a.x1, a.x2);
                boolean flag3 = Math.min(a.y1, a.y2) <= Math.max(b.y1, b.y2);
                boolean flag4 = Math.min(b.y1, b.y2) <= Math.max(a.y1, a.y2);

                return flag1 && flag2 && flag3 && flag4;
            }

            return true;

        }

        return false;
    }

    static int ccw(long x1, long y1, long x2, long y2, long x3, long y3) {

        long result = ( (x1*y2) + (x2*y3) + (x3*y1) ) - ( (x2*y1) + (x3*y2) + (x1*y3) );

        if(result == 0){
            return 0;
        }
        else if(result > 0){
            return 1;
        }
        return -1;
    }

}

/*

2162 선분 그룹

기하학 + 분리 집합 문제

해당 문제의 경우 우선 ccw와 선분 교차 판정을 알아야합니다.
그래서 해당 문제 이전에 백준의 17386_선분 교차1, 17387_선분 교차2 문제를 풀면 쉽게 풀이할 수 있습니다.

문제의 핵심은 ccw를 통해서 선분의 교차를 확인한 후 이를 유니온파인드로 하나로 묶어주는 것 입니다.
여기서, 가장 큰 선분을 구하는 방법은 Small to Large 트릭을 이용하면 쉽게 구할 수 있습니다.

로직을 정리하자면
1. 이중 for문을 이용하여 나와 그룹이 다른 다른 모든 선분과 ccw로 교차하는지 체크한다.
2. 교차한다면 유니온 파인드를 이용하여 한 그룹으로 묶어준다.
3. 그룹에 묶을 때, small to Large 트릭을 이용하여 트리의 크기가 큰 root에 작은 root를 씌워준다.
4. set을 이용하여 그룹의 개수를 센다, 이때 경로 압축이 안되었을 수도 있기 때문에 find(i)로 세주고, size 값을 조회하여 max값을 갱신한다.

*/