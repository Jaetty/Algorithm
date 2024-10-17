import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        String[] arr = new String[N];

        for(int i=0; i<N; i++){
            arr[i] = br.readLine();
        }

        Map<String, Integer> map = new HashMap<>();

        int max=0, x=0, y=1;

        for(int i=0; i<N; i++){

            for(int j=1; j<=arr[i].length(); j++){

                String val = arr[i].substring(0, j);

                if(map.containsKey(val)){
                    if(max < j){
                        max = j;
                        x = map.get(val);
                        y = i;
                    }
                    else if(max==j && x > map.get(val)){
                        x = map.get(val);
                        y = i;
                    }
                }
                else{
                    map.put(val, i);
                }

            }
        }

        System.out.println(arr[x]);
        System.out.print(arr[y]);

    }
}
/*
 * 문자열 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. N <= 20_000, 문자 최대 길이는 100
 * 2. 가장 접두사가 많이 겹치는 문자열 2개를 출력한다.
 * 3. 만약 서로 가장 많이 겹치는 경우가 여러개라면, 문자열이 주어진 순서대로 빠른 순으로 S와 T를 정해야한다.
 *
 * 1번 포인트 때문에 시간초과를 염려하여 저는 이 문제를 HashMap을 이용하여 풀이하였습니다.
 * 2번과 3번 포인트를 만족하기 위해서 입력 받은 문자열을 문자열 배열에 순서대로 넣어줍니다.
 * 그리고 HashMap으로 맨 처음부터 뽑아와서 처음부터 그 문자의 최대 길이까지 부분 문자열을 생성해 Map에 부분 문자열을 key, 배열의 index를 value로 넣어줍니다.
 * 이후 다른 문자의 부분 문자열과 이미 map에 들어간 부분 문자열이 같다면 먼저 출려되는 문자열을 가르키는 index인 x에 value값과 y에 현재 index 값을 넣어주며 갱신합니다.
 * 이때, S가 가장 먼저 나와야한다는 3번 포인트에 주의하며 조건을 부여하면 풀이가 가능합니다.
 *
 * 여담으로 해당 문제는 저에게 혼란을 주었지만, 조금 더 유연하게 사고할 수 있는 계기가 된 것 같습니다.
 * 이 문제의 경우 N <= 20_000, 문자 최대 길이는 100입니다.
 *
 * 완전 탐색의 경우 시간복잡도는 O(20_000 * 20_000 * 100) = 40,000,000,000, O(N^2) 입니다.
 * 그럼 당연히 완전 탐색은 안되겠구나 하고 고민해서 풀이했는데... 다른 제출자 분들은 단순 완전 탐색으로 구현하여 통과하였습니다.
 *
 * 물론 시간 복잡도와과 수행 시간은 전혀 다른 개념입니다.
 * 예로 지금 시간 복잡도는 40,000,000,000이지만 반복 과정에서 얼마나 복잡한 작업인가, 컴퓨터의 성능 등 수많은 요소들이 작용하므로, 실제 수행 시간과 전혀 다릅니다.
 * 다만 그럼에도 크게 수행 시간과 틀릴지라도 대부분의 경우에서 '유용'하기 때문에 1억을 기준으로 1초로 예상하는 방법을 사용하는데..
 * 제가 문제를 풀면서 이 정도로 시간 복잡도와 수행 시간의 차이가 느껴지는 경우는 처음 보았습니다.
 *
 * 물론 저의 풀이가 완전 탐색 풀이보다 몇 배는 더 빠르기 때문에 괜히 어렵게 풀었다고 생각은 들지 않습니다.
 * 하지만 하나 깨달은 것은 앞으로는 시간복잡도 계산을 보다 빠르고, 널널히, 대략적으로 계산하는 습관이 필요하겠다는 생각이 들었습니다.
 * 어떤 구현 부분에 대해 대략 O(N^2)이 시간적으로 여유가 있는지 계산하고 안되면 바로 N log N의 해결 방법을 찾는 방향으로 풀이를 진행해야할 것 같습니다.
 *
 *
 * */