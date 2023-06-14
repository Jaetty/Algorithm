import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static long A,B;
    static long answer;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        answer = -1;

        bfs();
        System.out.println(answer);

    }

    static void bfs(){

        Queue<Value> queue = new ArrayDeque<>();

        queue.add(new Value(A,1));

        while(!queue.isEmpty()){

            Value value = queue.poll();

            long plusOne = value.num*10 +1;
            long timeTwo = value.num*2;

            if(plusOne==B){
                answer = value.time+1;
                return;
            }

            if(timeTwo==B){
                answer = value.time+1;
                return;
            }

            if(plusOne<B){
                queue.add(new Value(plusOne, value.time+1));
            }

            if(timeTwo<B){
                queue.add(new Value(timeTwo, value.time+1));
            }

        }


    }

    static class Value{
        long num;
        long time;

        Value(long num, long time){
            this.num = num;
            this.time = time;
        }

    }

}
/*
*
* */