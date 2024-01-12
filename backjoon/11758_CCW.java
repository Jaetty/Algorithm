import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int[][] point = new int[2][3];

        for(int i=0; i<3; i++){
            st = new StringTokenizer(br.readLine());
            point[0][i] = Integer.parseInt(st.nextToken());
            point[1][i] = Integer.parseInt(st.nextToken());
        }

        int answer = ccw(point);

        if(answer<0){
            System.out.println(-1);
        }
        else if(answer==0){
            System.out.println(0);
        }
        else{
            System.out.println(1);
        }

    }

    static int ccw(int[][] point){

        return ( (point[0][0]*point[1][1]) + (point[0][1] * point[1][2]) + (point[0][2] * point[1][0]) )
                - ( (point[1][0]*point[0][1]) + (point[1][1] * point[0][2]) + (point[1][2] * point[0][0]) );

    }
}

/*
* 기하학 문제
*
* 해당 문제는 CCW(Counter-ClockWise)라는 알고리즘 문제입니다.
* 이는 평면상의 3개의 점의 방향 관계를 구할 수 있는 기초 기하학 알고리즘입니다.
*
* 신발끈 공식을 이용하여 다음과 같이 풀이가 가능합니다.
*
* x1 x2 x3 x1
*   \  \  \
* y1 y2 y3 y1
*
* 위의 식으로 ( (x1 * y2) + (x2 * y3) + (x3 * y1) )을 얻고
*
* x1 x2 x3 x1
*   /  /  /
* y1 y2 y3 y1
*
* 위의 식으로 ( x2 * y1 + x3 * y2 + x1 * y3 )를 얻어서 위의 식에서 빼주시면 됩니다.
*
* 즉 ( x1*y2 + x2*y3 + x3*y1 ) -  ( x2*y1 + x3*y2 + x1*y3 ) 식이 됩니다.
*
* 이 공식을 이용하면 3가지 결과를 얻을 수 있는데
* CCW < 0 : 시계방향
* CCW = 0 : 일직선
* CCW > 0 : 반시계방향
* 이 결과에 맞추어 값을 출력하면 문제를 풀이할 수 있습니다.
*
* */