import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int answer = 0;

        int[] arr = new int[K];
        List<Integer> outlet = new ArrayList<>();

        st = new StringTokenizer(br.readLine());

        for(int i=0; i<K; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        outlet.add(arr[0]);

        for(int i=1; i<K; i++){

            if(outlet.contains(arr[i])){
                continue;
            }

            if(outlet.size() >= N){

                answer++;
                int[] erase = new int[N];
                boolean[] check = new boolean[101];
                erase[0] = outlet.get(0);
                int idx = 0;

                for(int j=i+1; j<K; j++){
                    if(!check[arr[j]] && outlet.contains(arr[j])){
                        erase[idx++] = outlet.indexOf(arr[j]);
                        check[arr[j]] = true;
                    }
                }

                if(idx==N){
                    outlet.remove(erase[idx-1]);
                }
                else{
                    for(int j=0; j<N; j++){
                        if(!check[outlet.get(j)]){
                            outlet.remove(j);
                            break;
                        }
                    }
                }
            }
            outlet.add(arr[i]);

        }
        System.out.println(answer);
    }

}

/*
 * 그리디 문제
 *
 * 이 문제의 저의 접근 방법은 다음과 같았습니다.
 * 1. 문제에서 고려할 만한 점은 미래에 어떤 기기가 '언제' 그리고 '몇 번' 이미 알고 있다는 사실.
 * 2. 그러면 '다음에 나오는 거리' '등장 횟수' 둘 중의 하나 혹은 둘 다 영향을 미치는지 몇가지 테스트케이스를 제작하여 테스트해봄.
 *
 * 2 7
 * 2 3 2 3 1 2 7
 * 위의 테스트 케이스는 2가 3보다 한 개 더 많아도 2와 3중 어느걸 빼도 결과가 같음.
 *
 * 2 10
 * 2 3 2 3 1 2 5 7 3 3
 * 위의 경우는 3이 2보다 1개 더 많아도 어느걸 빼도 결과가 같음.
 *
 * 2 14
 * 2 3 3 4 5 6 7 2 2 4 5 6 7 3
 * 이 경우 2와 3의 횟수가 같아도 결과가 다름.
 *
 * 위 3개의 경우를 통해 '등장 횟수'는 전혀 고려사항이 아닌 것 같다고 판단되었습니다.
 * 그러면 '언제'에 집중해서 3번째 테스트 케이스로 최적의 값을 구해보니까 다음과 같이 전개되었습니다.
 *
 * 1. [2,3] -> 3만남 넘어감
 * 2. [2,3] -> 4만나서 3을 뺌. answer = 1
 * 3. [2,4] -> 5만나서 4를 뺌. answer = 2
 * 4. [2,5] -> 6, 5. answer = 3
 * 5. [2,6] -> 7, 6. answer = 4
 * 6. [2,7] -> 2, 넘어감
 * 7. [2,7] -> 2, 넘어감
 * 8. [2,7] -> 4, 2를 뺌. answer = 5
 * 9. [4,7] -> 5, 4. answer = 6
 * 10. [5,7] -> 6, 5, answer = 7
 * 11. [6,7] -> 7, 넘어감
 * 12. [6,7] -> 3, 아무거나 빼도 됨. answer = 8
 *
 * 이 테스트케이스 풀이가 많이 도움이 되었습니다.
 * 풀이에서 플러그를 뺄 숫자를 정하는 방식은 [플러그에 꽃힌 숫자 중 다음번에 가장 늦게 나오는 숫자 / 앞으로 안나오는 숫자] 였습니다.
 *
 * 위와 같이 풀이 방식을 찾아냈고 이를 코드로 구현하여 풀이할 수 있었습니다.
 *
 *
 * */