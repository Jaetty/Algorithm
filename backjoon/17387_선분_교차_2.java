import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static long[][] point;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        point = new long[2][4];

        for(int i=0; i<2; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<4; j++){
                point[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ccw1 = ccw(point[0][0], point[0][1], point[0][2], point[0][3], point[1][0], point[1][1]) * ccw(point[0][0], point[0][1], point[0][2], point[0][3], point[1][2], point[1][3]);
        int ccw2 = ccw(point[1][0], point[1][1], point[1][2], point[1][3], point[0][0], point[0][1]) * ccw(point[1][0], point[1][1], point[1][2], point[1][3], point[0][2], point[0][3]);

        int answer = 0;

        if(ccw1 <=0 && ccw2 <= 0){
            if(ccw1==0 && ccw2==0){

                boolean compare1 = Math.min(point[0][0], point[0][2]) <= Math.max(point[1][0],point[1][2]);
                boolean compare2 = Math.min(point[1][0], point[1][2]) <= Math.max(point[0][0],point[0][2]);
                boolean compare3 = Math.min(point[0][1], point[0][3]) <= Math.max(point[1][1],point[1][3]);
                boolean compare4 = Math.min(point[1][1], point[1][3]) <= Math.max(point[0][1],point[0][3]);

                if(compare1 && compare2 && compare3 && compare4) answer = 1;

            }
            else answer = 1;
        }

        System.out.println(answer);


    }

    static int ccw(long x1, long y1, long x2, long y2, long x3, long y3){

        long result = ( (x1*y2) + (x2*y3) + (x3*y1) ) - ( (x2*y1) + (x3*y2) + (x1*y3) );

        if(result<0) return -1; // 시계
        else if(result==0) return 0; // 직선
        else return 1; // 반시계

    }
}

/*
* 기하학 문제
*
* @@@ 해당 문제는 풀이를 참고하였습니다! @@@
*
* 해당 문제의 경우 아래의 블로그의 풀이를 참고하였습니다.
* https://hyeo-noo.tistory.com/108
* https://velog.io/@jini_eun/%EB%B0%B1%EC%A4%80-17387%EB%B2%88-%EC%84%A0%EB%B6%84-%EA%B5%90%EC%B0%A8-2-Java-Python
*
* 해당 문제의 경우 두 가지 부분을 생각해내면 되는데 편의상 입력으로 들어온 점을 각각 p1, p2, p3, p4 (p1<p2, p3<p4)라고 하겠습니다.
* 1. p1과 p2에서 p3와 p4로 ccw를 통해 알아낸 가는 방향이 다르면 두 선분은 교차한다.
* 2. p1과 p2에서 p3와 p4로 가는 방향이 같으면 선분은 교차하지 않는다.
* 3. 두 직선이 일직선 상에 있을 때는 p1 < p4 && p3 < p2 면 교차한다.
*
* 위의 부분들을 코드로 나타내면 풀이가 가능한 문제였습니다.
*
*
* */