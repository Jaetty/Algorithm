import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, erase, root, answer;
    static List<Integer> list[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        list = new List[N];

        for(int i=0; i<N; i++){
            list[i] = new ArrayList<>();
        }

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int index=0; index<N; index++){
            int val = Integer.parseInt(st.nextToken());
            if(val==-1){
                root = index;
                continue;
            }
            list[val].add(index);
        }

        erase = Integer.parseInt(br.readLine());
        answer = 0;

        dfs(root);

        System.out.println(answer);

    }

    static void dfs(int curr){

        boolean check = true;

        // 지워진 노드가 루트일 때 바로 리턴
        if(curr==erase) return;

        // 자식이 없는지 확인
        if(list[curr].isEmpty()){
            answer++;
            return;
        }

        // 자식들을 탐색하는데 지워진 노드가 있다면 들어가지 않음
        for(int node : list[curr]){
            if(node==erase) continue;
            dfs(node);
            check = false;
        }

        // 자식으로 지워진 노드 하나만 가지고 있었다면 answer++
        if(check) answer++;

    }


}

/*
* 트리 문제
*
* 해당 문제의 경우 트리의 기본 성질을 이해하면 풀이 가능한 문제입니다.
* 문제는 트리를 구성하던 어떤 노드가 사라졌을 때 리프 노드는 몇 개 인지 물어보고 있습니다.
*
* 사라진 노드를 erase라고 한다면 erase와 erase를 부모로 삼던 노드들은 모두 없어지게 됩니다.
* 그러면 erase만을 자식으로 삼던 노드의 경우 리프 노드가 될 수 있다는 점만 잘 고려해서 DFS를 활용하면 쉽게 문제를 해결할 수 있습니다.
*
* */