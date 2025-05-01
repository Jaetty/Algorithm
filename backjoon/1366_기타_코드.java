import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static final int MAX = 10000000;
    static int N, M, answer, guitar[], code[];
    static Map<String, Integer> c_to_int;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        init();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        guitar = new int[N];
        code = new int[M];

        st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            guitar[i] = c_to_int.get(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());

        for(int i=0; i<M; i++){
           code[i] = c_to_int.get(st.nextToken());
        }

        calculate(0,MAX, -1, new int[M]);
        System.out.print(answer);
    }

    static void init(){

        answer = MAX;
        String[] tmp = {"A","A#","B","C","C#","D","D#","E","F","F#","G","G#"};
        c_to_int = new HashMap<>();

        for(int i=0; i<12; i++){
            c_to_int.put(tmp[i], i);
        }
    }

    static void calculate(int depth, int min, int max, int[] check){

        if(depth>=N){

            for(int i=0; i<M; i++){
                if(check[i]<=0){
                    return;
                }
            }

            if(min == MAX){
                min = 0;
            }
            answer = Math.min(max-min+1, answer);
            return;
        }

        for(int j=0; j<M; j++){

            check[j]++;
            int diff = code[j]-guitar[depth];
            int over_diff = 12 - guitar[depth] +code[j];
            if(diff==0){
                // 코드 난이도에 영향을 주지 않는다.
                calculate(depth+1, min, max, check);
            }
            calculate(depth+1, min > diff ? diff : min, max < diff ? diff : max, check);
            calculate(depth+1, min > over_diff ? over_diff : min, max < over_diff ? over_diff : max, check);
            check[j]--;
        }
    }
}

/*
1366 기타 코드

구현 + 브루트포스 문제

문제 풀이 핵심은 제시된 문제를 구현하기만 하면 됩니다.

※ 이 문제가 브루트포스인지 확인한 방법은?
1. 경우의 수가 많지 않습니다.
2. 그리디로 하기엔 첫 결정이 다음 결정에 영향을 줍니다.
3. dp로 하기엔 규칙성이 모호하고 오히려 더 시간이 걸릴 것 같았습니다.

풀이 로직은 다음과 같습니다.
1. 우선 A, A# 과 같은 문자열로는 취급하기 까다로우니, Map을 이용하여 각각 숫자와 매칭시켜줍니다. A=0, A#=1, B=2.. G#=11.
2. 문제의 조건은 기타의 각 줄은 하나 이상의 코드 내에 음표를 쳐야하고, 코드에 있는 모든 음표는 한번 이상 소리가 나야합니다.
그럼 int check[]을 만들어서 각각 코드의 음표가 몇번 울렸었는지 기억하게 만들어서 모두 한번 이상은 소리가 났는지 확인해줍니다.
3. 이때, 기타로 소리를 내는 방법은 크게 3가지로, 음이 같을 때 플랫 없이 그냥 칠때, 플랫 차이만큼 올라가서 칠 때, 한바퀴 더 돌아서 칠때가 있습니다.
예를 들어, A는 A#을 1의 비용을 칠 수 있고, 13의 비용으로 칠 수 있습니다. A는 A를 플랫없이 칠 수도 있습니다.

다만, 여기서 중요한 점은 플랫 없이 치는 경우는 점수에 영향을 주지 않습니다. 즉, 과정을 그냥 스킵한다고 생각하면 되기 때문에
min, max 값에 영향을 주지 않고 넘어가게 만들어주는 것이 포인트입니다.

상술한 내용처럼 조건을 이해하고 구현하면 풀이할 수 있는 문제입니다.

*/