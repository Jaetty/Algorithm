import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int N=1, K=1;
        int[] parents = new int[1000001];
        int[] input = new int[1001];
        int answer;

        while(true){

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            if(N==0 && K==0) break;

            answer = 0;

            st = new StringTokenizer(br.readLine());

            for(int i=0; i<N; i++){
                input[i] = Integer.parseInt(st.nextToken());
            }

            parents[input[0]] = 0; // 루트 초기화

            int parentIdx = 0;
            int before = input[0];

            for(int i=1; i<N; i++){

                if(i>1 && before+1 < input[i]){
                    parentIdx++;
                }

                parents[input[i]] = input[parentIdx];
                before = input[i];

            }

            if(parents[K] == 0 || parents[parents[K]] == 0){
                sb.append(0).append("\n");
            }
            else{
                int find = parents[parents[K]];

                for(int i=0; i<N; i++){
                    int via = parents[input[i]];
                    if(via != parents[K] && parents[via] == find){
                        answer++;
                    }
                }
                sb.append(answer).append("\n");
            }

        }
        System.out.print(sb.toString());

    }

}

/*
 * 트리 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. N은 최대 1000
 * 2. 노드의 번호는 최대 1,000,000
 * 3. 숫자가 순서대로 입력되면 한 노드의 자식이 계속해서 입력되고, 순서가 끊어지면 자식이 없는 가장 작은 번호의 노드의 자식이다.
 * 4. 입력되는 수열은 항상 증가하는 순서로 들어온다.
 *
 * 처음 문제를 풀이할 때 Tree Class를 구현하여 정직하게 트리 문제를 풀듯 해결하려고 했는데 메모리 초과가 났습니다.
 * 메모리가 초과되지 않도록 조금씩 조금씩 불피요한 변수형 바꾸고, 줄이는 작업을 하다가 Tree의 ArrayList 부분을 없애니까 해결됐습니다.
 *
 * 이 문제는 애초부터 트리를 구현해서 풀이하는 것이 아니라 부모를 설정하는 방법을 구현하는 것이 핵심입니다.
 * 즉, 각 노드 번호의 부모를 기억하게 만들어줍니다. (루트는 부모가 없기 때문에 아무도 0을 부모로 선택합니다.)
 *
 * 어차피 노드 최대 수가 1000개니까 모든 노드를 돌면서 특정 x노드의 부모의 부모가 찾고있는 K의 부모의 부모와 같은지만 확인하고 count만 하면 됩니다.
 * 여기서 주의점은
 * 1. 부모가 루트이거나 K가 루트 번호일 때는 사촌이 없습니다.
 * 2. K의 형제들은 제외해야하므로 x값의 부모가 K의 부모와 같다면 count하지 않습니다.
 *
 * 이와 같이만 기능하도록 구현한다면 풀이할 수 있는 문제입니다.
 *
 * */