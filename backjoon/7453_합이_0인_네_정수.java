import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static long[][] inputs;
    static long[] AB, CD;
    static long answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        inputs = new long[4][N];

        AB = new long[N*N];
        CD = new long[N*N];

        for(int i=0; i<N; i++){

            StringTokenizer st = new StringTokenizer(br.readLine());

            for(int j=0; j<4; j++){
                inputs[j][i] = Long.parseLong(st.nextToken());
            }
        }

        int index = 0;

        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                AB[index] = inputs[0][i] + inputs[1][j];
                CD[index++] = inputs[2][i] + inputs[3][j];
            }
        }

        answer = 0;

        Arrays.sort(AB);
        Arrays.sort(CD);

        for(int i=0; i<N*N; i++){

            int first = lowerBound(-AB[i]);

            if(first < N*N || first >= 0){

                int last = upperBound(-AB[i]);
                answer += last - first;

            }
        }

        System.out.println(answer);


    }

    static int lowerBound(long key){

        int lo = 0;
        int hi = N*N;

        while(lo<hi){

            int mid = (lo + hi)/2;

            if(CD[mid] >= key){
                hi = mid;
            }else{
                lo = mid+1;
            }

        }
        return lo;
    }

    static int upperBound(long key){

        int lo = 0;
        int hi = N*N;

        while(lo<hi){

            int mid = (lo + hi)/2;

            if(CD[mid] > key){
                hi = mid;
            }else{
                lo = mid+1;
            }

        }
        return lo;
    }

}

/*
* 중간에서 만나기 + (이분 탐색 OR 투 포인터) 문제
*
* 해당 문제는 1024MB, 12초 라는 자주 보기 힘든 넉넉한 메모리와 시간 제한을 제공하지만 문제가 브루트포스로 O(N^4)의 시간복잡도를 지닌 다는 사실을 알 수 있게 됩니다.
* 당연히 그 정도의 시간 복잡도는 허용되지 않기 때문에 좀 더 시간을 줄인 중간에서 만나기 (Meet in the middle) 이라는 기법을 사용해야하는 문제입니다.
*
* 중간에서 만나기는 분할 정복과 비슷한 개념입니다.
* 분할 정복이 커다란 문제를 나눌 수 없는 단위까지 작제 나눠서 각각을 풀이한 결과를 다시 합쳐서 문제를 해결하는 방식입니다.
* 이와 같이 중간에서 만나기도 커다란 문제를 나눠서 각각 나눈 것이 결과를 도출하고 이를 다시 이용하는 방식입니다.
*
* 이 기법을 이용하여 문제를 풀이하게 되면 다음과 같습니다.
* 1. ABCD의 모든 경우의 수를 기억하려면 O(N^4)이 필요하므로 이를 반으로 각각 나누어줍니다. 즉 A 배열과 B 배열의 합들을 기억하는 배열, C배열과 D배열의 합을 기억하는 배열을 만듭니다.(AB[], CD[])
*    이 경우 O(N^2)의 시간으로 AB, CD 배열을 완성할 수 있습니다.
* 2. 문제의 경우 합이 0이 되는 경우를 찾고 있습니다. 그렇다면 AB에서 값이 15라면 CD에서 -15가 몇 개 있는지만 파악해주면 됩니다.
*
* 여기서 2번이 방법을 수행하기 위해 떠올릴 수 있는 방법은 크게 3가지가 있습니다.
* 1. Map을 이용
* 2. 이분 탐색 이용
* 3. 투 포인터 이용 (풀이를 적는 중에 떠올랐습니다...)
*
* 언뜻 보면 Map이 가장 편해보입니다 그러나 Map으로 하면 시간초과가 발생합니다.
* 저도 처음에 Map의 get이 O(1)이기 때문에 Map이 가장 빠르고 편하겠다고 생각해서 코드를 짰는데 시간이 초과되었습니다. 알고보니 map의 get이 시간 복잡도가 O(1)이지만 속도가 느린 편이라고 합니다.
* 그러므로 2번과 3번을 이용해야하는데 2번의 경우 lower_bound와 upper_bound를 이용하여 찾던 값이 있는 첫 위치와 찾던 값이 끝난 위치의 차를 구해주면 됩니다.
* 3번의 경우 나중에 방법을 떠올렸는데 직접 짜보진 않았지만 이분 탐색으로 일일히 찾아주는 것 보다 전의 상태를 기억하기 때문에 속도가 더욱 빠를 것으로 예상됩니다.
*
* 이와 같이 해당 문제는 중간에서 만나기를 적용하는 부분, AB를 통해 필요한 CD의 값 만을 조회하는 부분 2가지를 고려하여 구현하면 풀이가 가능합니다.
*
* */