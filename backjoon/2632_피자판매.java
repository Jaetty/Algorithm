import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int answer;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int order = Integer.parseInt(br.readLine());
        answer = 0;

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int sum1 = 0;
        int sum2 = 0;

        List<Integer> first = new ArrayList<>(), second = new ArrayList<>();

        int[][] a = new int[N+1][N];
        int[][] b = new int[M+1][M];

        for(int i=0; i<N; i++){
            a[1][i] = Integer.parseInt(br.readLine());
            sum1 += a[1][i];

            if(a[1][i]<order){
                first.add(a[1][i]);
            }
            else if(order == a[1][i]) answer++;
        }

        for(int i=0; i<M; i++){
            b[1][i] = Integer.parseInt(br.readLine());
            sum2 += b[1][i];

            if(b[1][i]<order){
                second.add(b[1][i]);
            }
            else if(order == b[1][i]) answer++;
        }

        if(sum1<order){
            first.add(sum1);
        }
        else if(order == sum1) answer++;

        if(sum2<order){
            second.add(sum2);
        }
        else if(order == sum2) answer++;

        setArray(a, first, N, order);
        setArray(b, second, M, order);

        Collections.sort(first);
        Collections.sort(second);

        int value = 0;
        int count = 0;


        if(first.isEmpty() || second.isEmpty()){
            System.out.println(answer);
            return;
        }

        for(int i=0; i<first.size(); i++){

            if(value==first.get(i)){
                answer += count;
                continue;
            }

            value = first.get(i);
            count = 0;

            int left = lower_bound(second, order - value);
            if(left==-1) continue;

            int right = upper_bound(second, order - value);

            count = right - left;
            answer += count;

        }

        System.out.println(answer);

    }

    static void setArray(int[][] array, List<Integer> list, int N, int order){

        for(int i=2; i<N; i++){
            for(int j=0; j<N; j++){
                array[i][j] = array[1][j] + array[i-1][(j+1)%N];
                if(array[i][j]<order){
                    list.add(array[i][j]);
                }
                else if(order == array[i][j]) answer++;
            }
        }
    }

    static int lower_bound(List<Integer> list, int key){

        int lo = 0;
        int hi = list.size();

        while(lo < hi){

            int mid = (lo+hi) / 2;

            if(list.get(mid) >= key){
                hi = mid;
            }else{
                lo = mid+1;
            }
        }

        return lo;
    }

    static int upper_bound(List<Integer> list, int key){

        int lo = 0;
        int hi = list.size();

        while(lo < hi){

            int mid = (lo+hi) / 2;

            if(list.get(mid) > key){
                hi = mid;
            }else{
                lo = mid+1;
            }

        }

        return lo;
    }

}

/*
 * 누적합 + 이분 탐색 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 한 종류의 피자를 2 조각 이상 판매할 때는 반드시 연속된 조각들을 잘라서 판매한다.
 * 2. 피자조각은 모두 A종류이거나, 모두 B종류이거나, 또는 A와 B 종류가 혼합될 수 있다.
 *
 * 여기서 1번 포인트를 통해 알 수 있는 점은 연속된 조각을 누적합으로 구할 수 있다는 점입니다.
 * 만약 최대 1000개의 조각을 그때그때마다 누적하여 계산하면 시간 초과를 벗어나지 못하므로 처음부터 구해놓으면 됩니다.
 *
 * 2번째 포인트로 알 수 있는 점은 A와 B에서 각각 조각 값이 주문한 값과 동일하면 판매가 가능하다는 점을 거꾸로 생각하면
 * A에서 판매할 수 없어서 남은 조각과 B에서 남은 조각을 합친 값이 주문한 값과 같은 갯수를 찾으면 된다는 점 입니다.
 *
 * 여기서 중복된 누적 값들이 여러개 나올 수 있다는 점을 유추할 수 있습니다. 그러면 A의 누적합 값에서 B에 누적합을 하나하나 반복을 통해 탐색하는 것 보다
 * 이분 탐색을 이용하여 찾는 값과 같은 index(left)와 그 값보다 하나 더 큰 index(right)를 찾아서 right - left를 수행하면 조합이 가능한 갯수를 알 수 있습니다.
 *
 * 위의 로직에 맞춰서 먼저 각각 A와 B의 1~N개를 이용한 누적합을 만들고 거기서 주문한 값과 같은 경우 정답 갯수 +1을 해주고
 * 그 후 first, second라는 리스트에 주문한 값보다 작은 값들을 모아놓았습니다.
 * 이분 탐색을 적용하기 위해 first와 second를 정렬해주고, first를 순회하면서 first의 조합 값 + second의 조합 값이 == 주문 값인 갯수를 이분탐색으로 찾아주었습니다.
 * 저는 이렇게 로직을 짜고 코드를 구현하였고 이를 통해 문제를 해결하였습니다.
 *
 * */