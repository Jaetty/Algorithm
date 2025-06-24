import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Point {
        long x, y;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Point[] points = new Point[4];

        for (int i = 0; i < 4; i++) {
            points[i] = new Point();
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 2; i++) {
            points[i].x = Long.parseLong(st.nextToken());
            points[i].y = Long.parseLong(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 2; i < 4; i++) {
            points[i].x = Long.parseLong(st.nextToken());
            points[i].y = Long.parseLong(st.nextToken());
        }

        int abc = ccw(points[0], points[1], points[2]);
        int abd = ccw(points[0], points[1], points[3]);
        int cda = ccw(points[2], points[3], points[0]);
        int cdb = ccw(points[2], points[3], points[1]);

        if( abc * abd < 0 && cda * cdb < 0 ) {
            System.out.println(1);
        }
        else{
            System.out.println(0);
        }

    }

    static int ccw(Point a, Point b, Point c) {
        return (a.x * b.y) + (b.x * c.y) + (c.x * a.y) - (c.x * b.y) - (b.x * a.y) - (a.x * c.y) < 0 ? -1 : 1;
    }
}

/*

17386 선분 교차1

기하학 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. ccw 알고리즘을 통해 abc, abd, cda, cdb 각각 외적이 시계 방향인지 반시계 방향인지를 구한다.
2. abc * abd가 각각 시계, 반시계 방향이며, cda, cdb 또한 각각 시계, 반시계라면 두 선분은 교차한다.
3. 오버플로우를 주의해야하며 해당 문제의 경우, 일직선 상에서 겹치는 경우는 없다고 하니까 각각 0인 경우는 없다.

*/