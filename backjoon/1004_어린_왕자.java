import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int T;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        T = Integer.parseInt(br.readLine());

        int point[] = new int[4];

        StringBuilder sb = new StringBuilder();

        for(int tc = 0; tc<T; tc++){

            st = new StringTokenizer(br.readLine());

            for(int i=0; i<4; i++){
                point[i] = Integer.parseInt(st.nextToken());
            }

            int circles = Integer.parseInt(br.readLine());
            int count = 0;

            for(int i=0; i<circles; i++){
                st = new StringTokenizer(br.readLine());

                int x0 = Integer.parseInt(st.nextToken());
                int y0 = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());

                boolean startIn = check(x0, y0, r, point[0], point[1]), endIn = check(x0, y0, r, point[2], point[3]);

                if( (startIn && endIn) || (!startIn && !endIn) ){
                    continue;
                }
                else{
                    count++;
                }
            }

            sb.append(count).append("\n");

        }
        System.out.print(sb.toString());

    }

    static boolean check(int x0, int y0, int r, int x, int y){

        return Math.sqrt(Math.pow((x0-x),2) + Math.pow((y0-y),2)) < r;

    }
}

/*
* 기하학 문제
*
* 해당 문제의 경우 가장 중요한 포인트는
* "시작 점과 도착 점 중 하나만 원 안에 들어있다면 거쳐야하는 원의 경계 갯수를 증가한다." 입니다.
*
* 생각해보면 원 끼리 서로 접점이 없어서 원이 가로막는 상황이 아니면 이동 경로에서 원의 경계를 건드릴 일이 없습니다.
* 그리고 아주 커다란 원이 있어서 이 원 안에 시작점과 도착점 둘이 같이 들어있는 경우는 원의 경계를 지날 일이 없습니다.
* 다만 시작점이 A라는 원 안에 들어있고 끝점이 그 밖에 있거나 반대로 끝점이 B라는 원 안에 있고 시작점이 B라는 원 밖에 있는 상황
* 즉 시작점, 끝점 둘 중 하나만 원 안에 속해있는 상황에는 원의 경계를 하나 통과해야하게 되는 것 입니다.
*
*
* 즉 x0, y0을 원의 원점 r = 반지름,  x, y를 원점이라고 한다면
* root((x0-x)^2 + (y0-y)^2) < r으로 x,y에 각각 시작점과 끝점을 넣어 둘 중 하나만 속해있는지 확인하면 풀이할 수 있는 문제입니다.
*
* */