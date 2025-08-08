import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, maxColor;
    static int[][] dp;
    static List<Integer>[] edges;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine().trim());
        int h = 1;
        int cnt = 1;

        while(h < N){
            h = h<<1;
            cnt++;
        }

        maxColor = cnt; // 해설을 보기 전 이 변수에 처음에 3개를 넣었고 다음으로 15개를 넣는 등 주먹구구식으로 풀이해서 통과함.

        dp = new int[N+1][maxColor];
        edges = new List[N+1];
        visited = new boolean[N+1];

        for (int i = 0; i < N+1; i++) {
            edges[i] = new ArrayList<>();
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        for(int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine().trim());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            edges[a].add(b);
            edges[b].add(a);
        }

        int answer = Integer.MAX_VALUE;

        for(int c=1; c<maxColor; c++){
            if(N < c) continue;
            answer = Math.min(answer,dfs(1, c));
        }

        System.out.println(answer);

    }

    static int dfs(int node, int color){

        if(dp[node][color]!=Integer.MAX_VALUE) return dp[node][color];
        dp[node][color] = color;
        visited[node] = true;

        for(int next : edges[node]) {

            if(visited[next]) continue;

            int min = Integer.MAX_VALUE;

            for(int c=1; c<maxColor; c++){
                if(c == color || N < c) continue;
                min = Math.min(min,dfs(next, c));
            }
            dp[node][color] += min;

        }

        visited[node] = false;
        return dp[node][color];

    }
}
/*

1693 트리 색칠하기

트리 + 다이나믹 프로그래밍 문제

@@@@ 해당 문제는 풀이를 참고했습니다 @@@@

먼저 이 문제의 핵심은 굳이 N개의 색을 모두 고려할 필요 없이 딱 한정된 수량만 고려하면 된다는 점을 깨닫는 것 입니다.
저의 경우 이 점까지는 깨닫고 주먹구구식으로 여러 값을 대입해보며 통과는 했지만, 정확한 원리를 이해하지 못한 상태로 풀이한 반쪽짜리 정답입니다.
때문에, 이 점을 부끄럽게 여기고 제대로된 학습을 위해 풀이 자체보다는 해설을 통해 배웠던 내용을 중심으로 해설을 적었습니다.

# 제가 시도한 접근.
이 문제와 근본적인 풀이 방법은 [백준 1949 우수 마을]이라는 문제와 같습니다.
이미 해당 문제를 풀었기 때문에 dp를 이용한 트리 탐색을 하면 될거라고 생각했습니다.
다만, 이 문제의 경우 칠할 수 있는 색의 수가 N개 있는게 문제였습니다. 절대 N번 돌아서는 안된다는건 쉽게 파악했으나 몇번 돌아야할지 고민이었습니다.
이때, 트리는 평면 그래프일테니까, 나름대로 트리를 그려가며 생각해본 결과 대강 3개의 색 정도만 있으면 되지 않을까? 생각이 들었습니다.
이를 베이스로 구현하자 당연히 틀렸고, 애초에 이산 수학의 '4색 정리'조차 제대로 고려하지 못한 부끄러운 풀이였습니다.
다만, 색을 전부 고려할 필요는 없다는 확신은 있었어서 많아봤자 10개일 것 같다는 생각에 넉넉잡아 15개까지 고려하도록 구현했더니 통과했습니다.
이후 점점 숫자를 줄여가보니 12개의 색만있다면 통과한다는 점을 알게되었습니다.

그러나 왜 이게 성립하는지 전혀 모르는 체로 통과한, 시험이었으면 바로 탈락할 운만 좋은 정답이었습니다.
때문에 해설을 통해 몇 개를 고려해야하는지 어떻게 아는가를 알아보았습니다.

# koosaga님의 해설
T(N) = N개의 색깔을 사용했을 때 답이 나오는 트리의 최소 크기 라고 정의합니다.

1. 자명히 T(1) = 1입니다.
2. T(i) >= T(i-1) + T(i-2) ... T(1) + 1입니다. 최소의 트리 T(i) 를 생각해봅시다. 해당 트리에는 i번 색의 노드가 하나 존재할 것입니다.
이 트리에 인접한 노드들은 i-1, i-2, i-3 .... 1의 색깔을 가집니다. 서브트리들의 크기는 최소 T(i-1) 이상입니다.
이를 통해 T(N) >= 2^(N-1) 임을 증명할 수 있습니다.


처음에 봤을 땐 이해가 안됐는데 한참을 따져보니 역시 koosaga님 정말 대단하십니다...
T(N) = N개의 색깔을 사용했을 때 답이 나오는 트리의 최소 크기(노드의 개수)입니다.
예를 들어 2개의 색을 반드시 포함하는 트리가 가질 수 있는 최소 크기는 노드가 1하고 2만 있는 경우이므로 크기가 T(2) = 2입니다.
당연하게도 1개의 색만 쓴다면, 1의 색을 가진 1개의 노드만 가지면 되므로 T(1) = 1이 됩니다.

여기서 생각할 수 있는게, T(N)에서는 N번째 색의 노드가 하나 존재할 것이라는 점입니다.
즉, T(2)에선 2번 색이 하나였습니다. T(1)에선 1번 색이 하나였습니다. 만약 T(3)이면 3의 색상은 하나 존재한다는 것 입니다.
그렇다면 일단 식에 T(N) = (아직 모르는 식) + 1 이 성립합니다. 그야 어쨌건 N의 색을 가진 노드는 1개 존재할테니까.

이제 N이라는 색을 가진 옆에 있는 노드들은 N 색을 쓰지 못하니까 분명 인접한 노드들은 N-1, N-2, N-3... 1까지의 색을 쓸 것입니다.
만약 3의 노드 옆에 2의 색을 가진 노드가 와야한다면? 3 옆에 1이 오는게 베스트인데, 2를 두었다는 이유는 2밑에 1의 노드가 있다는 뜻입니다.
즉, 어떤 노드 옆에 K라는 인접 노드를 두려면 T(K) 크기를 가져야만 한다는 것입니다.

따라서 인접 노드들이 만드는 크기의 합은  T(k-1) + T(k-2) ... T(1)가 됩니다.
위에 썻던 식에 합쳐서 만들어본다면 T(N)의 크기는 적어도 T(N) >= T(N-1) + T(N-2) + T(N-3) ... + 1  와 같다는 것을 알 수 있습니다.

이 식은 한번 하나씩 대입해서보면 2배씩 증가하는 것을 알 수 있습니다.
T(1) = 1
T(2) >= T(1) + 1 = 2
T(3) >= T(2) + T(1) +1 = 4
T(4) >= T(3) + T(2) + T(1) +1 = 8
T(5) >= T(4) + T(3) + T(2) + T(1) +1 = 16
...

즉, T(N) >= 2^(N-1)이므로 색 N개를 꼭 써야하는 트리는 최소 2^(N-1)의 노드가 필요하다는 뜻입니다.
해당 문제의 입력으로 주어지는 노드의 최대 개수는 100_000 이었기 때문에
100_000 >= 2^(N-1) 이게 성립하려면 N은 17이어야합니다.

즉, 최소한의 값을 고려하기 위해서 필요한 최대 색상은 17개 이하라는 뜻이 됩니다.
이를 통해서 최대로 고려할 수 있는 색상의 수를 판단할 수 있게 되는 것 이었습니다.
이렇게 원리를 알고보니 정말로 제가 얼마나 부끄럽게 문제를 풀이했는지 알 수 있는 시간이었습니다...

*/