import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, size;
    static int[] LIS, history;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        N = Integer.parseInt(br.readLine().trim());
        LIS = new int[N];
        history = new int[N];
        size = 0;
        List<int[]> list = new ArrayList<>();

        for(int i = 0; i<N; i++){
            st = new StringTokenizer(br.readLine().trim());
            list.add(new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }
        Collections.sort(list, (e1,e2)-> e1[0] - e2[0]);

        for(int i=0; i<N; i++){

            int L_idx = binarySearch(list.get(i)[1]);

            if(L_idx == size){
                LIS[L_idx] = list.get(i)[1];
                history[i] = size++;
            }
            else{
                LIS[L_idx] = list.get(i)[1];
                history[i] = L_idx;
            }
        }

        System.out.println(N-size);

        int[] ans = new int[N-size];
        int match = size-1;
        int idx = ans.length-1;

        for(int i=N-1; i>=0; i--){
            if(history[i] == match){
                match--;
                continue;
            }
            ans[idx--] = list.get(i)[0];
        }

        for(int i=0; i< ans.length; i++){
            sb.append(ans[i]).append("\n");
        }

        System.out.print(sb.toString());
    }

    static int binarySearch(int target){

        int left = 0, right = size, mid = 0;

        while(left < right){

            mid = (left+right)/2;

            if(LIS[mid] >= target){
                right = mid;
            }
            else{
                left = mid +1;
            }

        }

        return left;
    }
}
/*

2568 전깃줄 - 2

이분 탐색 + 가장 긴 증가하는 부분수열(LIS) 문제

@@ 이분 탐색을 이용한 LIS를 알고 있다는 전제하에 쓰였습니다.

해당 문제의 핵심은 이분 탐색 LIS에 선택된 인덱스를 역추적하는 방법을 추가하는 것 입니다.

주어지는 최대 N이 10만이기 때문에 dp를 이용한 O(N^2)의 방식은 적절하지 않습니다. 때문에 N Log N 방식의 이분 탐색 LIS가 필요합니다.
전깃줄 문제의 경우 대표적으로 LIS를 이용하면 교차하지 않는 최대 전깃줄 개수를 구할 수 있는 문제입니다.

다만, 이 문제는 여기에 더해서 끊어야하는 전깃줄 목록들을 출력하게 되어있습니다.
즉, 이분 탐색 LIS는 순서와 관계없이 정렬된 숫자를 바꾸기 때문에 단일로는 역추적이 어렵습니다.
이를 위해서 어떤 숫자가 LIS 배열에서 몇번 째에 위치한 값인지 기억하는 history 배열이 필요합니다.

이 방법은 다음과 같은 예시 입력과 함께 해설하겠습니다.
10
281 492
283 375
370 463
385 279
397 84
433 483
450 52
461 179
467 487
494 325

위와 같은 입력이 주어진다면 완성된 LIS 배열은 {52, 179, 325, 487}이 됩니다.
이를 A 전봇대의 시작점으로 바꿔보면 {450, 461, 494, 467}이 됩니다.
만약, 52, 179, 325, 487 남기고 자르면 정답일까요? 아닙니다 성립하지 않습니다. 325는 훨씬 뒤에 있기 때문입니다.
오히려 325에 해당하는 494번 줄은 잘라야만 하는 전깃줄 입니다.

이처럼 이분 탐색을 이용한 LIS는 순서에 상관없이 크기만 고려하여 배열이 구성되는 특성이 있습니다.
역추적을 위해서는 이런 문제를 극복하기 위해 "history 배열을 사용하여 각 숫자가 LIS 배열에서 몇번째 위치였는지 기억"하게 만들어주면됩니다.

즉, LIS 배열은 다음과 같이 변화합니다.
{492} -> {375} -> {375, 463} -> {279, 463} -> {84, 463} -> {84, 463, 483}
-> {52, 463, 483} -> {52, 179, 483} -> {52, 179, 483, 487} -> {52, 179, 325, 487}

history 배열에 각각의 값이 LIS 배열에서 몇번째 위치에 있었는지 기억하게 만들면 다음과 같습니다.
A:  492     375     463     279     84     483     52     179     487     325
[     0       0"      1"      0      0       2"     0       1       3"      2     ]

이 배열을 뒤에서부터 순회하면서 순서대로 3,2,1,0으로 가장 먼저 만나는 값들만 모으면 교차 없이 가장 길게 전깃줄이 이어진 경우가 됩니다.

해당 문제는 교차를 발생시키는 다른 전깃줄을 모두 잘라야하므로 순서에 안맞는 값들을 전부 출력해주면 문제를 풀이할 수 있습니다.

*/