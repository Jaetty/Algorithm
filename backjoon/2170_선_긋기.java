import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static PriorityQueue<int[]> priorityQueue;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        priorityQueue = new PriorityQueue<>((e1,e2)->{
            return e1[0] - e2[0];
        });

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            int[] input = {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
            priorityQueue.add(input);
        }

        int start = Integer.MAX_VALUE;
        int end = Integer.MIN_VALUE;

        long answer = 0;

        while(!priorityQueue.isEmpty()){

            int[] val = priorityQueue.poll();

            if(val[0] > end && end != Integer.MIN_VALUE){
                answer += end - start;
                start = Integer.MAX_VALUE;
                end = Integer.MIN_VALUE;
            }

            if (val[0] < start){
                start = val[0];
            }
            if (end < val[1]){
                end = val[1];
            }
        }

        answer += end - start;

        System.out.println(answer);

    }


}

/*
* 정렬 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 랜덤하게 시작점(x)과 끝점(y)이 주어진다. (중복도 있음)
* 2. 이미 선이 있는 위치에 겹쳐서 그릴 수도 있다.
* 3. N은 1_000_000만큼 주어진다.
*
* 문제를 매우 쉽게 생각해서 눈금 종이에 자와 연필을 대고 주어진 x에서 y까지 선을 긋는데 만들어진 선들의 길이의 총합을 물어보고 있습니다.
*
* 우선 1번과 2번을 통해 알 수 있는 점은 정렬이 필요하다는 점 입니다.
* 만약 랜덤하게 나오는 값을 그대로 선으로 그어버린다면 이미 선이 있는지 확인하는 방법은 매우매우 어려워질 것입니다.
* 그러므로 저희가 할 수 있는 방법은 정렬을 해야하는 점 입니다.
*
* 먼저 포인트 1번을 고려하여 정렬을 통해서 x, y를 받았을 때 x가 작은 순으로 정렬해주시면 됩니다.
* 그리고 x와 y를 기억하는 변수(start, end)를 둡니다.
* start에 가장 작은 x값을 end에 가장 큰 y값을 기억하게 하면 가장 긴 선을 기억할 수 있습니다.
*
* 그러면 이제 겹치지 않는 경우를 생각해봐야하는데 간단합니다. 1-----5 까지 이어졌는데 6-7의 선을 그으면 서로 선이 겹치는 일이 없습니다.
* 즉 입력 받은 x값이 이미 이어져온 선의 y 값보다 크면 선이 겹치지 않습니다.
* 이로써 1번과 2번 포인트로 알 수 있는 문제점은 해결되었습니다.
*
* 3번 포인트를 통해서 알 수 있는 점은 시간 초과의 우려가 있다는 점 입니다.
* 즉 입력을 모두 받은 후 정렬을 하는 것 보다 입력 받자마자 정렬시켜주는게 시간적으로 더 효율적이라고 느낄 수 있습니다.
* 그래서 저는 priorityQueue로 입력된 값들이 바로 정렬되게 만들어주었습니다.
*
* 이렇게 위의 포인트를 코드로 구현하게 되면 해당 문제를 풀 수 있습니다.
*
*
* */