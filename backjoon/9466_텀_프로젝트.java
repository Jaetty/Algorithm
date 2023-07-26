import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, end, answer;
    static int[] arr;
    static boolean[] visit;
    static Set<Integer> set;
    static boolean flag;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int tc=0; tc<T; tc++){
            N = Integer.parseInt(br.readLine());

            arr = new int[100001];
            visit = new boolean[100001];
            StringTokenizer st = new StringTokenizer(br.readLine());
            answer = 0;
            set = new HashSet<>();

            for(int i=1; i<=N; i++) arr[i] = Integer.parseInt(st.nextToken());

            for(int i=1; i<=N; i++){
                end = 0;
                flag = false;

                if(!visit[i]){
                    set.clear();
                    dfs(i);
                }
            }

            sb.append(answer+"\n");

        }

        System.out.print(sb);

    }

    static void dfs(int index){

        if(visit[arr[index]]){
            answer++;
            visit[index] = true;
            return;
        }

        if(set.contains(arr[index])){
            end = arr[index];
            visit[index] = true;
            flag = true;
            return;
        }

        set.add(index);

        dfs(arr[index]);

        if(flag){
            if(end==index) flag = false;
        }else{
            answer++;
        }

        visit[index] = true;

    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제는 그래프적으로 쉽게 생각해서 싸이클이 생기지 않는 노드가 몇개인지 확인하는게 목표입니다.
 * 시간제한이 3초이지만 N이 최대 100000이기 때문에 단순히 방문을 반복하면 반드시 시간초과가 발생합니다.
 * 이러한 점들을 고려하여 문제를 생각해보면 떠올릴 수 있는 포인트는 다음과 같습니다.
 * 1. 탐색을 지속하다보면 결국 마지막에는 싸이클이 생성된다.
 * 2. 시간 초과가 이루어지지 않으려면 저번에 방문한 노드, 사이클은 다시 방문하지 않도록 한다.
 *
 * 만약에 입력이 다음과 같이 주어졌다고 해보겠습니다.
 * 1
 * 4
 * 2 3 4 4
 * 위의 경우는 정답이 3입니다. 그 이유는
 * 1->2, 2->3, 3->4, 4->4(자기 순환)
 * 즉 사이클이 4가 자기 순환으로 발생하고 나머지는 사이클이 없기 때문입니다.
 *
 * 저희는 1부터 시작해서 이 탐색을 DFS로 진행한다고 했을 때, 1부터 시작해서 4까지 이미 탐색을 완료하였습니다.
 * 그렇다면 2, 3, 4는 더이상 탐색하지 않아도 될 것입니다.
 * 그것을 저는 visit[] 이라는 배열로 이미 탐색이 '완료'되었는지 확인하는 배열을 만들고
 * '탐색 중에 들린' 노드들을 기억하는 HashSet을 만들었습니다.
 *
 * 그리고 사이클이 만들어진 노드가 있을 것입니다.
 * 다음과 같은 입력이 주어진다면
 * 1
 * 4
 * 2 3 4 2
 *
 * 이 경우는 1->2, 2->3, 3->4, 4->2로
 * 마지막으로 사이클을 형성하게 되는 노드가 2입니다.
 * 그렇다면 순서대로 1, 2, 3, 4, 2로 탐색을 했다고 하면
 * 2를 사이클이 생성되는 마지막 노드라고 end라는 변수에 넣어주고 사이클을 나타내는 flag = true로 해줍니다.
 * 그리고 재귀를 벗어나면서 2가 나올때까지 나오는 값들(4,3)은 사이클에 들어간 노드들 이므로 visit에만 true값을 줍니다.
 * 결국 2의 순서가 오면 그때부터 이 이후는 사이클이 아닙니다를 뜻하도록 flag = false로 설정합니다.
 * 그리고 1이 나오게 되며 1은 사이클에 속하지 못했으므로 answer 값을 하나 늘려줍니다.
 *
 * 이러한 식으로 로직을 세우면 O(N)만큼의 시간복잡도를 가지게 될 것이고 이를 구현하여 풀 수 있습니다.
 *
 * */