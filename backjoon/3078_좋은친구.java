import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, K;

    static class Value{
        int len, grade;

        Value(int len, int grade){
            this.len = len;
            this.grade = grade;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        List<Value> values = new ArrayList<>();

        for(int i=0; i<N; i++){
            String temp = br.readLine();
            values.add(new Value(temp.length(), i));
        }

        long answer = 0;

        Collections.sort(values,(e1,e2)->{
            if(e1.len==e2.len) return e1.grade-e2.grade;
            return e1.len-e2.len;
        });

        int high = 0;

        for(int i=0; i<N; i++){
            int myGrade = values.get(i).grade;
            int myLen = values.get(i).len;

            if(high<i) high = i;

            while(high<N && values.get(high).len == myLen && values.get(high).grade <= myGrade + K){
                high++;
            }

            answer += (high-1) - i;

        }
        System.out.println(answer);

    }

}

/*
 * 슬라이딩 윈도우 문제
 *
 * 해당 문제는 큐를 이용해서도 풀이할 수 있지만 필자는 슬라이딩 윈도우 공부를 위해 큐를 배척하고 풀이하려고 노력했습니다.
 *
 * 문제의 포인트는 다음과 같습니다.
 * 1. 나와 이름 길이가 같고 나와의 등수 + K 보다 크게 차이가 나지 않을 때 좋은 친구다.
 * 2. 입력으로 주어지는 N과 K의 최대 크기는 30만이다.
 *
 * 먼저 1번 포인트를 통해 저희는 입력값이 뭐가 주어지건 그냥 문자열의 길이, 등수 만 알면 된다는 점을 알 수 있습니다.
 * 2번을 통해 단순한 브루트포스 혹은 O(N^2)의 시간이 걸리는 함수는 시간초과가 발생한다는 점을 알 수 있습니다.
 *
 * 큐를 이용하면 큐를 20개의 배열로 만들어 조건에 맞으면 큐에 넣어주고 틀리면 queue에서 pop 한 후 size를 합산하는 방법으로도 풀 수 있습니다.
 * 슬라이딩 윈도우만을 이용한 저의 풀이는 다음과 같습니다.
 *
 * 먼저 문자열 길이와 등수를 고려하여 정렬해줍니다. 정렬이 끝나면 문자열 길이가 적은 순, 같다면 등수가 적은 순서로 정렬이 됩니다.
 * for문을 통해 0에서 부터 N까지 반복하고, high 변수를 통해 포인터를 하나 더 만들어줍니다.
 * high는 조건에 맞는 가장 먼 위치를 가르킵니다.
 *
 * 예를 들어 다음과 같은 입력이 있다면
 * 5 3
 * AB
 * CD
 * EF
 * HI
 * JK
 * i = 0인 AB에 존재해있고 조건에 맞을 때만 high 변수를 증가시키면 최종적으로 high는 3의 위치, 즉 HI를 가르킵니다.
 * 그러면 지금 친구의 수는 high-i => 3-0 = 3. 3명의 친구가 존재한다는 뜻 입니다.
 * 그리고 i=1이 되면 CD 차례인데 여기서 high를 올려주면 high = 4에 도달합니다.
 * 그리고 high-i = 3이므로 3명의 친구가 또 존재하는 것을 알 수 있습니다.
 *
 * 이처럼 조건에 맞을 때만 끝에 포인트만 움직여주면 N^2의 시간동안 수행할 필요가 없어집니다.
 * 그리고 저는 한 List에 서로 문자열 길이가 다르고, 등수가 다른 친구들을 모아놨기 때문에
 * 서로 등수가 너무 차이나거나, 포인터를 올리다 글자수가 달라졌을 때를 고려해야했습니다.
 *
 * 이 방법은 단순하게 if(high<i) high = i 조건으로 해결했습니다.
 * 즉 high는 등수 차가 심하거나 글자수가 차이나면 오르지 않습니다. 그러면 어느새 high보다 i가 커질텐데 그때 high = i로 만들면
 * 새로운 등수, 글자수에서 출발할 수 있게 됩니다.
 *
 * 위와 같은 로직을 구현하여 문제를 풀 수 있었습니다.
 *
 * */