import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static PriorityQueue<Integer> pq, mq;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        pq = new PriorityQueue<>((e1,e2)->e2-e1);
        mq = new PriorityQueue<>();

        N = Integer.parseInt(br.readLine());

        for(int i=0; i<N; i++){
            int var = Integer.parseInt(br.readLine());
            if(var >= 0){
                pq.add(var);
            }
            else{
                mq.add(var);
            }
        }

        int answer = 0;
        int index = N-1;

        while(pq.size()>1){

            int var = pq.poll();
            int time = var * pq.peek();
            int sum = var + pq.peek();

            if(time > sum){
                answer += time;
                pq.poll();
            }
            else{
                answer += var;
            }
        }

        while(mq.size()>1){

            int var = mq.poll();
            int time = var * mq.peek();
            int sum = var + mq.peek();

            if(time > sum){
                answer += time;
                mq.poll();
            }
            else{
                answer += var;
            }
        }

        if(!mq.isEmpty() && !pq.isEmpty()){
            int minus = mq.poll();
            int plus = pq.poll();

            if (minus * plus > minus + plus){
                answer += minus * plus;
            }
            else answer += minus + plus;

        }
        else if(!mq.isEmpty() && pq.isEmpty()){
            answer += mq.poll();
        }
        else if(mq.isEmpty() && !pq.isEmpty()){
            answer += pq.poll();
        }

        System.out.println(answer);



    }


}

/*
* 정렬 + 그리디 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 그리디 : 정렬을 통해서 값이 높은 것 끼리 곱해서 곱한 값이 더한 값 보다 높다면 그것을 채택한다.
* 2. 정렬 : 양수 음수는 정렬 기준이 서로 다르므로 따로 정렬시켜서 저장해둬야한다.
*
* 예를 들어 입력이 다음과 같다고 하겠습니다.
* 3
* -2
* -1
* -3
*
* 이럴 때 문제를 생각해보면 -3 * -2 를 해준 후 -1을 더해주면 가장 큰 값이 될 것입니다.
* 그러면 정렬을 해서 순차적으로 뽑아와야하는데 -3과 -2가 먼저 나오도록 오름차순으로 정렬해야 한다는 사실을 생각해 볼 수 있습니다.
*
* 그럼 다음 입력으로 예시를 들어보겠습니다.
* 3
* 1
* 2
* 3
*
* 이 경우는 3*2+1이 가장 값이 큽니다. 그렇다면 양수의 경우 내림차순으로 정렬해야함을 알 수 있습니다.
*
* 그럼 다음과 같은 입력이 주어진다면?
* 6
* -2
* -3
* -1
* 2
* 0
* 3
*
* 음수는 내림차순인데 양수는 오름차순이니 서로 따로 정렬이 필요하다는 점을 알 수 있습니다.
* 그렇게 정렬한 것이 음수는 mq = {-3,-2,-1}, 양수는 pq = {3,2,0} 입니다.
*
* 이제 그리디하게 생각하면 곱해준 값이 더해준 값보다 큰지만 확인해주면 됩니다.
* 3*2 = 6이고 이는 3+2보다 크므로 이것을 채택하여 3과 2를 poll()해줍니다. 0만 남습니다.
* 음수는 -3 * -2 = 6이고 -3-2 = -5 보다 크므로 곱해준 결과를 남깁니다. -1이 남습니다.
*
* 이제 -1과 0이 각각 남았는데 -1*0 =0 이  0-1 = -1 보다 크므로 곱해줘서 마무리합니다.
*
* 이렇게 해당 문제는 정렬과 그리디한 해결법을 생각하여 코드로 작성하면 해결할 수 있는 문제입니다.
*
*
* */