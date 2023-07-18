import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        PriorityQueue<Integer> minus = new PriorityQueue<>((e1,e2)->e2-e1);
        PriorityQueue<Integer> plus = new PriorityQueue<>();

        int max = 0;
        boolean isPlusBig = true;

        for(int i=0; i<N; i++){
            int temp = Integer.parseInt(st.nextToken());
            max = Math.max(Math.abs(temp),max);

            if(temp<0){
                minus.add(temp);
                if(max==Math.abs(temp)) isPlusBig = false;
            }else{
                plus.add(temp);
                if(max==Math.abs(temp)) isPlusBig = true;
            }
        }

        int answer = 0;

        while(!minus.isEmpty()){
            int sum = 0;
            int size = minus.size();
            if(size>M){

                if(size%M==0){
                    for(int i=0; i<M; i++){
                        sum = minus.poll();
                    }
                }
                else{
                    for(int i=0; i<size%M; i++){
                        sum = minus.poll();
                    }
                }
                answer += Math.abs(sum)*2;
            }else{
                while(!minus.isEmpty()){
                    sum = minus.poll();
                }
                if(!isPlusBig) answer += Math.abs(sum);
                else answer += Math.abs(sum)*2;
            }
        }

        while(!plus.isEmpty()){
            int sum = 0;
            int size = plus.size();
            if(size>M){
                if(size%M==0){
                    for(int i=0; i<M; i++){
                        sum = plus.poll();
                    }
                }
                else{
                    for(int i=0; i<size%M; i++){
                        sum = plus.poll();
                    }
                }
                answer += Math.abs(sum)*2;
            }else{
                while(!plus.isEmpty()){
                    sum = plus.poll();
                }
                if(isPlusBig) answer += Math.abs(sum);
                else answer += Math.abs(sum)*2;
            }
        }

        System.out.println(answer);

    }

}

/*
 * 그리디 알고리즘 문제
 *
 * 해당 문제는 정렬과 그리디 방식을 이용하여 풀 수 있습니다.
 * 문제의 포인트는 다음과 같습니다.
 * 1. 모든 반납된 책은 0에 있으므로 0에서부터 시작한다.
 * 2. 한 번에 최대 M개를 가질 수 있다.
 * 3. 책들을 모두 반납했다면 0까지 돌아올 필요없다.
 *
 * 먼저 1번과 3번 포인트에 집중해보겠습니다.
 * 1번을 통해 N까지 책을 들고 갔다가 아직 반납한 책이 많다면 0까지 돌아와야 하므로 왔다갔다한 거리(비용)이 N*2가 필요하다는 것을 알 수 있습니다.
 * 여기서 3번 포인트를 탐욕적으로 생각해보면 맨 마지막에 꽂으러 가는 책이 가장 멀리 있다면 돌아올 필요가 없습니다.
 * 즉 가장 먼 거리가 100이라면 100*2가 아닌 100의 비용으로 끝낼 수 있다는 것입니다.
 * 그렇기 때문에 가장 먼 곳을 책 반납 중 맨 마지막에 갈 수 있도록 어디인지 알아둬야 합니다.
 * 또한 이러한 이유로 먼저 정렬이 되어야한다는 점을 알 수 있습니다. 그래야 어느게 더 작고 큰지를 쉽게 비교할 수 있기 때문입니다.
 *
 * 이제 가장 중요한 포인트인 2번을 생각해보겠습니다.
 * M개를 가져갈 수 있는데 어떻게하면 가장 효율적으로 가져갈 수 있을지 고려해야합니다.
 * 가령 입력으로
 * 3 2
 * 1 2 3
 * 이라는 입력이 주어지면 저희는 조금만 생각해보면
 * 1을 먼저 다녀오고 3까지 가면 총 비용이 (1*2)+3 = 5 이니까 가장 작다는걸 알 수 있습니다.
 * 이제 그 수를 조금만 늘려서
 * 5 3
 * 1 2 3 4 5
 * 라는 입력이 주어졌다면
 * 경우의 수를 따져보면 가장 효율적인 방법은
 * 처음에 2까지 다녀오고 다음에 5까지 가는 방법이 (2*2)+5 = 9로 가장 비용이 적습니다.
 *
 * 여기서 알 수 있는 공통점은 N % M != 0이면 N % M 만큼 처음에 가고 다음엔 계속 M만큼 가게 만들었다는 점입니다.
 *
 * 2번을 통해 알 수 있는 이 문제의 핵심은
 * N % M != 0이면 N % M 만큼 가고 다음엔 계속 M만큼 가게하는 것 입니다.
 *
 * 그렇다면 해결 방법은 다음과 같게 됩니다.
 * 1. 음수와 양수 각각 따로 배열(혹은 리스트)을 만든다.
 * 2. 이 둘을 각각 정렬한다.
 * 3. 음수와 양수 중 절대값이 가장 큰 값이 어딘지 기억해둔다.
 * 4. 각자 핵심포인트를 구한 방법으로 비용을 산출한다.
 * 5. 가장 절대값이 큰 값은 마지막에 방문할 것이기 때문에 비용은 N*2가 아닌 N이다.
 *
 * 이 부분들을 고려하여 구현해주면 문제를 해결할 수 있습니다.
 * */