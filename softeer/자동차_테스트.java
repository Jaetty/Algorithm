import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static Map<Integer, Long> map;
    static int N,q;
    static int[] input, arr;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        map = new HashMap<>();

        N = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        input = new int[N];
        arr = new int[3];

        st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++) input[i] = Integer.parseInt(st.nextToken());

        Arrays.sort(input);

        StringBuilder sb = new StringBuilder();

        for(int i=0; i<q; i++){
            int seek = Integer.parseInt(br.readLine());
            if(!map.containsKey(seek)){
                int index = lowerBound(seek);
                long answer = 0;

                for(int j=index; j<N; j++){
                    if(input[j]!=seek) break;
                    answer += j * ((N-1)-j);
                }
                map.put(seek, answer);
            }
            sb.append(map.get(seek)).append("\n");
        }

        System.out.print(sb.toString());

    }

    static int lowerBound(int seek){

        int hi = N, lo=0;

        while(lo<hi){
            int mid = (hi+lo) / 2;

            if(input[mid] < seek){
                lo = mid+1;
            }else{
                hi = mid;
            }

        }
        return lo;
    }

}


/*
* 이분 탐색 및 수학 문제
* 해당 문제의 경우 포인트는 어떤 x라는 값을 중앙값으로 갖게 만드는 경우의 수를 찾는 것 입니다.
* 단순히 모든 경우의 수를 조합을 사용하면 만들어준다면 시간 초과가 나올 입력 수 입니다.
* 그러므로 시간을 낭비하지 않도록 우리가 찾아야하는 값에만 경우의 수를 찾는 방법으로 진행하였습니다.
*
* 이제 문제에 집중해보면 만약 3 1 2이라는 입력 값이 있고 2가 중앙값인 경우의 수를 구하라고 한다면
* 단순히 생각해보면 그 결과는 [1, 2, 3]으로 1가지 경우가 있을 수 있습니다.
* 여기서 한번 잠시 생각해보면 [1,2,3]에서 '2'의 왼쪽과 오른쪽은 2가 중앙값이 되려면 왼쪽은 2이하, 오른쪽은 2이상이어야합니다.
* 즉 [2,2,2] [1,2,2] [2,2,3] 과 같이 왼쪽은 반드시 이하, 오른쪽은 반드시 이상입니다.
*
* 그럼 이제 3 2 1 2 5 이러한 입력이 주어지고 2가 중앙값인 경우를 찾아보라고 한다면
* 먼저 입력을 정렬하면 [1, 2, 2, 3, 5]가 됩니다.
* (index는 0부터 시작하여 각각 0 1 2 3 4 라고 하겠습니다.) index 값이 1인 2를 기준으로 생각해보면 왼쪽엔 [1] 하나가, 오른쪽엔 [2,3,5] 3가지가 있습니다.
* 그럼 이를 통해 만들 수 있는 경우의 수는 [1,2,2] [1,2,3] [1,2,4] 로 3가지이고 1 * 3과 같습니다.
* 똑같이 index가 2인 2값을 보자면 왼쪽에는 [1,2] 2개 오른쪽엔 [3,5] 2개 그러면 만들 수 있는 개수는 2*2입니다.
*
* 즉 이렇게 구하게 되면 결국 정렬을 한 후 가장 먼저 만나게되는 내가 찾는 값을 찾아서 내 왼쪽과 오른쪽에 값이 몇개 있는지 알아낸 후 곱한 값을 알아내면 됩니다.
* 여기서 오른쪽에 만약 나와 같은 값이 있다면 index를 옮겨서 또다시 왼쪽*오른쪽 개수 값을 누적해서 더한 결과가 정답임을 알 수 있습니다.
*
* 이를 코드로 구현하면 문제를 해결할 수 있습니다.
*
* */