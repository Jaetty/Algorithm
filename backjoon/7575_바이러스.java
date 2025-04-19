import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, K;
    static int[][] input;
    static int[][] table;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb;

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        input = new int[N][];

        for(int i=0; i<N; i++){

            int size = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            input[i] = new int[size];

            for(int j=0; j<size; j++){
                input[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int count = 0;
        List<Integer> list[] = new List[2];
        list[0] = new ArrayList<>();
        list[1] = new ArrayList<>();

        for(int i=0; i<input[0].length; i++){

            count = 0;
            list[0].add(input[0][i]);
            list[1].add(0,input[0][i]);

            if(i>=K-1){
                table = new int[2][K];
                failureFunction(0, list[0]);
                failureFunction(1, list[1]);

                for(int j=1; j<N; j++){
                    if(kmp(0, list[0], input[j])){
                        count++;
                        continue;
                    }
                    else if(kmp(1, list[1], input[j])){
                        count++;
                        continue;
                    }
                    break;
                }

                if(count == N-1){
                    System.out.println("YES");
                    return;
                }

                list[0].remove(0);
                list[1].remove(list[1].size()-1);
            }

        }

        System.out.println("NO");


    }
    static void failureFunction(int o, List<Integer> var){

        table[o][0] = 0;

        int idx = 0;

        for(int i=1; i<var.size(); i++){

            while(idx > 0 && var.get(i) != var.get(idx)){
                idx = table[o][idx-1];
                break;
            }

            if(var.get(i) == var.get(idx)){
                table[o][i] = ++idx;
            }
        }
    }

    static boolean kmp(int o, List<Integer> pattern, int[] origin){

        int o_idx = 0;
        int p_idx = 0;

        while(true){

            if(p_idx == pattern.size()){
                return true;
            }

            if(o_idx >= origin.length){
                break;
            }

            if(pattern.get(p_idx) == origin[o_idx]){
                p_idx++;
            }else{
                if(p_idx > 0 ){
                    p_idx = table[o][p_idx-1];
                    continue;
                }

            }

            o_idx++;

        }
        return false;
    }

}

/*
7575 바이러스

KMP 알고리즘 문제

문제의 핵심은 KMP를 이용한 알고리즘입니다.
KMP에 대한 설명은 다른 알고리즘 풀이인 1786 찾기 문제의 해설을 확인하시면 됩니다.

문제 풀이의 핵심은 다음과 같습니다.
1. 입력받은 문자열 중 하나를 골라서 거기서 K만큼의 부분 문자열을 뽑아낸다.
2-1. 부분 문자열을 기준으로 다른 문자열들과 KMP를 이용하여 일치 여부를 확인한다.
2-2. 만약 일치하지 않았다면, 부분 문자열을 거꾸로 만들어서 일치 여부를 확인한다.
3. 만약 모든 문자열에 부분 문자열이 존재한다면 바이러스 코드이다. 하나라도 일치하는게 없다면 바이러스 코드는 없다.

결국, 큰 로직은 위와 같고 이를 KMP 기반으로 구현해주기만 하면 문제를 풀이할 수 있습니다.

참고로, 저는 문제의 경우 문자열 보다는 숫자 비교와 비슷해 보였기 때문에 받은 값들을 int형태의 배열과 부분 문자열은 List로 해결했습니다.
이렇게하면, 부분 문자열 추가, 제거, 거꾸로돌리기 등이 간편해지는 장점이 있습니다.

  */