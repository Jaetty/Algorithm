import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static List<Integer> primeList;
    static boolean[] prime;
    static final int MAX = 4000000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        prime = new boolean[MAX+1];
        primeList = new ArrayList<>();

        primeSet();

        N = Integer.parseInt(br.readLine());

        long[] sum = new long[primeList.size()+1];

        sum[0] = 0;

        for(int i=0; i<primeList.size(); i++){
            sum[i+1] += primeList.get(i) + sum[i];
        }

        int low = 0;
        int high = 1;

        int answer = 0;

        while(low<high && high <= primeList.size()){

            long val = sum[high] - sum[low];

            if(val == N){
                answer++;
                high++;
            }
            else if(val < N){
                high++;
            }
            else{
                low++;
            }
        }

        System.out.println(answer);
    }

    static void primeSet(){

        for(int i=2; i<=MAX; i++){
            if(!prime[i]){
                primeList.add(i);
                for(int j=i*i; j<=MAX; j+=i){
                    if(j<0) break;
                    prime[j] = true;
                }
            }
        }

    }

}

/*
* 투 포인터 + 누적합 + 소수 판정 문제
*
* 해당 문제에서 소수를 언급한 이상 4000000 이하에서 소수를 빠르게 판단하는 방법이 필요하다는 점을 알 수 있습니다.
* 제가 사용한 방법은 에라토스테네스의 체를 사용하였습니다.
* 에라토스테네스의 체는 일일히 어떤 수가 소수인지 파악하는것이 아닌 소수를 가지고 그 소수의 배수들은 소수가 아니라고 표시하는 방법입니다.
* 이 방법을 통해 소수와 소수가 아닌 값을 빠르게 구분할 수 있어서 알고리즘 문제 해결에서 자주 사용됩니다.
*
* 저는 에라토스테네스의 체로 소수인 값들을 알아내어 소수들만 ArrayList에 넣었습니다.
* 그리고 list안에 담긴 소수들의 누적합을 구해주고 이 누적합의 배열에 투 포인터를 이용하여 문제를 해결하였습니다.
* 투 포인터의 핵심은 최초에 left는 0을 가리키고 right는 1을 가르키는 상황에서 right - left 값이 입력된 N과 같은지 판단해줍니다.
* N과 같으면 count를 하나 올려주고 N보다 작으면 left 포인터를 올려주고 N이 더 크면 right 포인터를 올려줍니다.
* 이를 left가 right보다 값이 작을 동안 반복을 하는 것 입니다.
*
* 만약 입력 값이 4라고 한다면 누적합 배열은 {0, 2, 5, 10 ...} 으로 구성되어 있을 것 입니다.
* 1. 포인터는 각각 left = 0, right =2 상태이고 그 차는 r-l = 2가 나오게 됩니다. 이는 4보다 낮은 수이기 때문에 right 포인터를 올려줍니다.
* 2. left = 0, right = 5일 때 차는 5이고 이는 4보다 높기 때문에 left 포인터를 올려줍니다.
* 3. left = 2, right = 5일 때 차는 3으로 4보다 낮기 때문에 right를 올리게 됩니다.
* 4. left = 2, right = 10일 때 차는 8이므로 left를 올려줍니다.
* 5. left = 5, right = 10일 때 차는 5이며 left를 올려줍니다.
* 6. left =10, right = 10이 되고 이는 left가 right보다 값이 작아야한다는 전제를 만족하지 못하기 때문에 반복문에서 break하게 됩니다.
*
* 이와 같은 방법으로 입력된 N과 left와 right의 차가 몇 번 같았는지를 출력하게 되면 문제를 해결할 수 있습니다.
*
*
* */