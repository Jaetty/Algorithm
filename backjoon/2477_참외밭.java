import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        int widthMax = 0;
        int heightMax = 0;

        int[] size = new int[8];

        for(int i=1; i<=6; i++){

            st = new StringTokenizer(br.readLine());
            int direction = Integer.parseInt(st.nextToken());
            size[i] = Integer.parseInt(st.nextToken());

            if(direction<3){
                widthMax = Math.max(widthMax, size[i]);
            }
            else{
                heightMax = Math.max(heightMax, size[i]);
            }
        }
        size[0] = size[6];
        size[7] = size[1];

        int emptySize = 1;

        for(int i=1; i<=6; i++){
            if(size[i]==heightMax || size[i] == widthMax){
                emptySize *= Math.abs(size[i-1] - size[i+1]);
            }
        }

        System.out.println( (widthMax * heightMax - emptySize) * N);

    }
}

/*
* 기하학 + 수학 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 가장 긴 가로와 세로를 구합니다.
* 2. 가장 긴 가로 양 옆의 세로 값의 차가 빼야하는 크기의 사각형의 세로 값이 됩니다.
* 3. 가장 긴 세로 양 옆의 가로 값의 차는 빼야하는 크기의 사각형의 가로 값이 됩니다.
* 4. (가장 긴 가로 * 가장 긴 세로 - 2와 3에서 구한 값을 곱한 값) * N 을 해주면 됩니다.
*
* 예시 입력으로 다음과 같은 입력이 주어진다면
1
2 10
3 30
1 100
4 100
2 90
3 70
*
* 직접 그리면 쉽게 알 수 있지만 실질적인 참외밭 넓이는 100 * 100 - 70 * 10(부족한 크기)을 해주면 됩니다.
* 결국 포인트에 나와있는 것은 이 부족한 크기를 구하는 것 입니다.
*
* 가로 100의 양옆에는 30과 100의 세로 값이 있고 |30-100|인 70을 구할 수 있고
* 세로 100의 양옆에는 100과 90이 있고 |100-90|인 10을 구할 수 있습니다.
* 이렇게 100 * 100에 - 70 * 10 을 수행할 수 있게 되고 여기에 N을 곱해주면 문제를 해결할 수 있습니다.
*
*
* */