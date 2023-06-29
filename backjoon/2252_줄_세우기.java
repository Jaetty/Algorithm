import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N, M;
    static ArrayList<Integer> list[];
    static boolean printed[];

    static StringBuilder sb;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        list = new ArrayList[N+1];
        printed = new boolean[N+1];

        for(int i=1; i<=N; i++){
            list[i] = new ArrayList<>();
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int value = Integer.parseInt(st.nextToken());
            int lower = Integer.parseInt(st.nextToken());
            list[lower].add(value);
        }

        sb = new StringBuilder();

        for(int i=1; i<=N; i++){
            if(printed[i]) continue;
            print(i);
        }

        System.out.println(sb);

    }

    static void print(int val){
        if(list[val].isEmpty()){
            sb.append(val+" ");
            printed[val] = true;
            return;
        }

        for(Integer var : list[val]){
            if(printed[var]) continue;
            print(var);
        }

        sb.append(val+" ");
        printed[val] = true;

    }

}
/*
* 위상 정렬 문제
* !== 해당 문제는 대표적인 위상 정렬 문제인데 필자가 아직 위상정렬 풀이에 익숙하지 않아서 나름대로 구현하여 풀었습니다.
* !== 더 좋은 풀이를 보기 위해서 위상정렬의 개념을 공부하시면 위와 다른 풀이 방법이 가능할 것입니다.
*
* 필자는 이렇게 풀었습니다.
* 다음과 같은 입력이 주어졌다고 예시를 들겠습니다.
* 3 2
* 3 1
* 2 1
* 위와 같이 입력이 주어진다면
* ArrayList 배열 list와 boolean 타입 배열 printed의 두 가지 배열을 만듭니다.
* list의 역할은 해당 숫자보다 큰 숫자들을 기억해둡니다.
* 1보다 큰 숫자는 3과 2가 있습니다. 그렇다면 3,2를 list[1]에 넣어줍니다.
* printed는 단순하게 어떤 숫자의 출력 여부입니다.
*
* 그리고 나서 재귀 함수를 이용하여 1부터 N까지 나보다 큰 숫자가 없다면 숫자를 출력해주고 printed에 표시합니다.
* 위의 예시 같은 경우 1을 출력하려고 재귀 함수에 들어갔을 때 list[1]이 비어있지 않습니다. 그러면 for문으로 list를 순회합니다.
* list에 3이 먼저 입력되었기 때문에 3이 나옵니다. 그럼 3을 매개변수로 다시 재귀 함수를 수행합니다.
* 3의 경우 list가 비어있습니다. 그렇다면 출력해주고 3을 출력했다고 표시해줍니다.
* 다음은 2가 있습니다. 2의 경우도 list가 비었으니 출력해줍니다.
* 이제 1보다 큰 숫자들이 먼저 출력이 완료되었습니다. 그럼 이제 1을 출력해주고 마무리 됩니다.
*
* 이런식으로 재귀적으로 나보다 우선순위가 앞선 번호를 먼저 출력을 완료하여 해결하였습니다.
* 후에 위상 정렬을 이용하면 더 비슷한 상황에서 유용히 사용할 수 있는 코드가 있는 사실을 알게 되어 필자 코드보다 위상정렬 풀이 코드를 추천합니다.
*
* */