import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, K;
    static int[] inputs, parents;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        inputs = new int[M];
        parents = new int[M+1];

        st = new StringTokenizer(br.readLine());

        for(int i=0; i<M; i++){
            inputs[i] = Integer.parseInt(st.nextToken());
            parents[i] = i;
        }

        parents[M] = M;

        Arrays.sort(inputs);

        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<K; i++){

            int val = Integer.parseInt(st.nextToken());
            int index = upper_bound(val);

            sb.append(inputs[findSet(index)]).append("\n");

            parents[findSet(index)] = parents[findSet(index)+1];
        }

        System.out.print(sb.toString());

    }

    static int upper_bound(int key){

        int left = 0;
        int right = M;

        while(left<right){

            int mid = (left + right) / 2;

            if(inputs[mid]<=key){
                left = mid+1;
            }
            else{
                right = mid;
            }
        }
        return left;
    }

    static int findSet(int x){
        if(parents[x]==x) return x;
        else return parents[x] = findSet(parents[x]);
    }

}

/*
* 분리 집합 + 이분 탐색 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 철수의 숫자는 반복될 수 있고 M개의 숫자 카드 중에 속하지 않을 수 있다.
* 2. 민수는 반드시 이길 수 있는 카드를 낼 수 있다. 왜냐하면 카드를 내지 못하는 경우는 없기 때문이다.
* 3. 민수는 철수가 낸 카드보다 큰 카드 중에서 가장 작은 카드를 낸다. 제출한 카드는 다시 사용하지 못한다.
* 4. M개의 최대 크기는 4,000,000이며 K는 최대 10,000 이다.
*
* 가장 먼저 예시 입력을 백준에서 제공하는 예제로 하겠습니다.
*
10 7 5
2 5 3 7 8 4 9
4 1 1 3 8
*
* 우선 1번과 2번의 포인트를 예제 입력에서 확인해보면 철수는 반복해서, 또한 M의 범위 밖의 숫자인 1을 두 번 낸 것을 확인할 수 있습니다.
* 즉 입력으로 1 1 1 1 1 같은 형태도 충분히 있을 수 있습니다. 다만 2번 포인트에 의해서 이기지 못하는 경우의 수는 주어지지 않습니다.
* 예를 들어 민수의 패의 최대 크기인 9이상의 카드는 입력으로 주어지지 않는다는 뜻입니다.
*
* 3번 포인트를 통해 알 수 있는 점은 이분 탐색을 이용하고 탐색 대상에서 지우면 된다는 점입니다.
* 여기서 생각할 수 있는 방법이 이분 탐색의 upper bound입니다. upper bound는 최초로 특정 값보다 더 큰 값의 위치를 찾을 수 있는 이분 탐색법입니다.
* 철수가 낸 카드로 4가 주어지면 4보다 큰 값을 처음 만나게 되는 index를 리턴하게 됩니다. 즉 index = 3, 값은 5임을 알 수 있습니다.
*
* 4번 포인트는 입력이 큰 것을 보여줌으로써 탐색 대상에서 지우는 방법에 새로운 방법을 강구하게 만들어줍니다.
* 3번 포인트까지 생각하면 list 등을 통해서 이미 찾은 index의 요소를 제거하거나 등의 방법을 가장 먼서 생각하게 될 것입니다.
* 다만 입력이 워낙 크니 그렇게 해서 시간 초과를 벗어날 수 없다는 것을 쉽게 짐작할 수 있습니다.
* 그러면 어떤 방법으로 탐색 범위에서 벗어나게 만들면 되는지를 새로 생각해야하는데 그 해결방법으로 저는 유니온 파인드를 응용하였습니다.
*
* 민수의 카드는 정렬해보면 다음과 같습니다.
* value : 2 3 4 5 7 8 9
* index : 0 1 2 3 4 5 6
*
* 예를 들어 철수가 1 1 1 1 1 이라는 입력을 수행했다고 해보겠습니다. 그러면 위의 배열에서 1보다 큰 수 중 가장 작은 수를 찾게 될 것입니다.
* 이는 당연히 0번째 index의 2입니다. 그러므로 이분 탐색으로 찾은 index 결과는 0이 됩니다.
*
* 그러면 다음으로 또 1의 입력을 이분 탐색으로 결과를 찾을 때 저희는 index 0번째를 무시하게 만들어야합니다.
* 이때 생각해볼 수 있는 방법이 그럼 2를 찾는다 하더라도 2의 다음 것을 가르키게하면 되지 않을까? 라는 방식의 접근이 생각납니다.
*
* 즉 배열이 다음과 같다면?
* value : 2 3 4 5 7 8 9
* index : 1 1 2 3 4 5 6
*
* 2를 찾았을 때 index 값은 1을 나타내고 1은 3이니까 민수는 다음 카드로 3을 제출하게 됩니다. 그 이후는 3과 2의 값의 인덱스를 2로 만들어준다면?
* value : 2 3 4 5 7 8 9
* index : 2 2 2 3 4 5 6
*
* 그러면 이분 탐색으로는 계속 값 2가 있는 위치를 찾지만 실질적으로 그 index 값은 제외되지 않은 2보다 큰 값을 가르키게 됩니다.
* 여기서 이 2를 일일히 입력해주면 당연히 시간 초과를 겪게 됩니다. 그러므로 이 때 사용할 수 있는 방법이 분리 집합입니다.
* 분리 집합을 통해서 일괄적으로 그 가르키는 값을 바꿔주게 되면 빠른 시간으로 가르키는 index의 값을 수정할 수 있게 됩니다.
*
* 위와 같은 포인트를 고려하여 코드를 나타내면 해당 문제를 풀이할 수 있습니다.
*
* */