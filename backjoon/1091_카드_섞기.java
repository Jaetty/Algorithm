import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, P[], S[], origin[], local[], cycle_check[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        P = new int[N];
        S = new int[N];
        cycle_check = new int[N];

        // local은 카드 뭉치의 특정 위치에 어떤 카드가 있는지 기억
        local = new int[N];
        // origin은 원래 [x] 위치에 있던 수가 현재 어디에 있는지 기억
        origin = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            origin[i] = i;
            local[i] = i;
            cycle_check[i] = i;
            P[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++){
            S[i] = Integer.parseInt(st.nextToken());
        }

        int answer = 0;

        while(true){

            if(answer > 0 && Arrays.equals(origin, cycle_check) ){
                answer = -1;
                break;
            }

            if(!check()){
                break;
            }

            answer++;
            shuffle();
        }

        System.out.println(answer);

    }

    static boolean check(){

        boolean answer = false;

        for(int i=0; i<N; i++){
            if(answer = origin[i]%3 !=P[i]){
                break;
            }
        }

        return answer;
    }
    static void shuffle(){

        /*
        *
        * 만약 입력이 다음과 같다면
        * P = 2 0 1
        * S = 1 2 0
        *
        * 섞기 전은 origin = [0,1,2], local = [0,1,2]
        *
        * origin은 최초에 [x]위치에 있던 녀석의 현재 위치
        * local은 카드 뭉치의 [x]위치에 있는 녀석이 누군지
        *
        * 섞으면 다음과 같이 local을 바꿔준다.
        * for(i = 0~2)
        * S[i] = local[i];
        *
        * 이걸 int[] tmp를 써서 해보면
        * tmp[S[i]] = local[i];
        * local = tmp;
        *
        * 즉,
        * local[0] = 2, local[1] = 0, local[2] = 1
        *
        * 그 후, origin[]의 값을 수정한다.
        * for(i = 0~2)
        * origin[local[0]==2] = 0; -> origin[2]는 0에 있다.
        * origin[local[1]==0] = 1; -> origin[0]은 1에 있다.
        * origin[local[2]==1] = 2; -> origin[1]은 2에 있다.
        *
        * */

        int[] tmp = new int[N];
        for(int i=0; i<N; i++){
            tmp[S[i]] = local[i];
        }

        local = tmp;

        for(int i=0; i<N; i++){
            origin[local[i]] = i;
        }
    }

}

/*
 * 1091 카드 섞기
 *
 * 구현 문제
 *
 * 해당 문제의 핵심 포인트는 다음과 같습니다.
 * 1. 원래 [x]위치에 있던 카드의 현재 위치 기억하기.
 * 2. 현재 카드 뭉치의 [x]위치에 있는 카드 기억하기.
 * 3. 순열의 특성상 고정된 섞기 방식을 수행할 경우 사이클이 발생하고 언젠가는 반드시 초기 상태로 되돌아옴.
 *
 * 문제의 관건은 위의 3가지 포인트를 어떻게 구현하여 풀이할 것인가 입니다.
 * 1,2를 사용한 방법은 중간의 shuffle 함수의 동작 방식을 참고해주시면 되겠습니다.
 *
 * 그리고 3번의 경우 어떠한 경우에도 특정 P를 만들 수 없다는 것을 알아낼 때 사용하므로,
 * 만약 열심히 카드를 섞었으나 초기 상태와 같아졌다면 더이상 만들 수 있는 경우는 없기 때문에 -1을 출력하면 됩니다.
 *
 * */