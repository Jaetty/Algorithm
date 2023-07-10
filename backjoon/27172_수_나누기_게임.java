import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

class Main{

    static int[] score;
    static boolean[] exist;
    static final int MAX = 1000000;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        score = new int[MAX+1];
        exist = new boolean[MAX+1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> list = new ArrayList<>();

        for(int i=0; i<N; i++){
            int val = Integer.parseInt(st.nextToken());
            list.add(val);
            exist[val] = true;
        }

        StringBuilder sb = new StringBuilder();

        for(int val : list){
            if(val == 1 ){
                score[val] = N;
                continue;
            }
            calculate(val);
        }

        for(int val : list){
            if(exist[1]){
                sb.append(score[val]-1+" ");
            }else{
                sb.append(score[val]+" ");
            }
        }

        System.out.println(sb);

    }

    static void calculate(int val){

        int sum = val+val;

        while(sum<=MAX){
            if(exist[sum]){
                score[val]++;
            }
            score[sum]--;
            sum+=val;
        }

    }

}

/*
* 브루트포스 문제
* 해당 문제는 다른 풀이 방법도 존재한다고 생각이 들지만 필자는 간단하게 브루트포스로 해결하였습니다.
* 누구나 생각할 수 있는 매우 간단한 방법으로 생각해보면 다음과 같은 포인트가 있습니다.
* 1. 딱 떨어진다는 것은 배수라는 뜻
* 2. 큰수가 작은수를 나누었을 때 딱 떨어질 수 없다.
* 3. 1의 결과는 무조건 N-1이다.
*
* 그렇다면 다음과 같은 입력이 주어졌다면
* 6
* 2 4 6 8 10 1
*
* 2부터 시작해서 자신에게 2+=2를 1000000이 될때까지 반복합니다.
* 자신의 배수인 값들에게 모두 -1씩 점수를 낮춰주고
* 만약 입력값 중에 자신의 배수(여기선 4,6,8,10)를 만나면 자신의 점수를 1 올려줍니다.
*
* 1의 경우는 점수를 N으로 넣어주고 출력 때 입력값에 1이 있을 경우 모든 점수에 -1을 해주면 됩니다.
*
* */