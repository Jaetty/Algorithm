import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 */

public class Main {

    static List<Integer> list[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int tc=0; tc<T; tc++){
            String temp = br.readLine();
            int K = Integer.parseInt(br.readLine())-1;
            int max = 0, min = Integer.MAX_VALUE;

            list = new List[26];

            for(int i=0; i<26; i++){
                list[i] = new ArrayList<>();
            }

            for(int i=0; i<temp.length(); i++){
                int val = temp.charAt(i) - 'a';
                list[val].add(i);
            }

            boolean flag = true;

            for(int i=0; i<26; i++){
                if(list[i].size()>=K+1){
                    flag = false;
                    for(int j=0; j<list[i].size(); j++){
                        if(j+K < list[i].size()){
                            int tmp = (list[i].get(j+K)-list[i].get(j))+1;
                            max = Math.max(max, tmp);
                            min = Math.min(min, tmp);
                        }
                    }

                }
            }

            if(flag) sb.append(-1+"\n");
            else sb.append(min + " " + max+"\n");

        }
        System.out.print(sb);

    }
}

/*
 * 문자열 + 슬라이딩 윈도우 문제
 *
 * 해당 문제는 브루트포스로 풀면 시간 초과되는 입력 값이기 때문에 다른 방법으로 접근해야합니다.
 * 제가 접근한 방법은 다음과 같습니다.
 *
 * 각 문자열에서 각각의 문자가 어디에 있는지 index를 리스트 or 배열 형태로 기억합니다.
 * a~z까지 반복하면서 첫 index 위치에서 K번째 index 위치까지의 차가 곧 문자열 크기가 되기 때문에 그 값을 기준으로 min, max값을 도출합니다.
 *
 * 예를 들어 다음과 같은 입력값이 있다고 하겠습니다.
 * 1
 * abaaaba
 * 3
 *
 * 이제 a~z를 나타내는 배열을 가진 리스트, list[26]을 만들어줍니다.
 * 그리고 각각 문자마다 index를 저장합니다.
 * list[0(a)] = {0,2,3,4,6}
 * list[1(b)] = {1,5}
 * list[3(c)~25] = {0}
 *
 * 이후 K=3이기 K보다 크거나 같은 list가 있다면 K만큼 슬라이딩 윈도우를 해줍니다.
 * list[0]의 크기가 5이기 때문에 처음부터 해보면
 * 1. (3-0) + 1 = 4
 * 2. (4-2) + 1 = 3
 * 3. (6-3) + 1 = 4
 * 이렇게 진행되고 가장 작은 값 3과 4를 찾아낼 수 있습니다.
 *
 *
 */