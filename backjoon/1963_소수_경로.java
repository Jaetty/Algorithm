import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static boolean[] prime;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        prime = new boolean[10000];
        Arrays.fill(prime, true);

        primeSet();

        int T = Integer.parseInt(br.readLine());

        for(int i=0; i<T; i++) {
            st = new StringTokenizer(br.readLine());

            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            int answer = bfs(start, end);
            if(answer >= 0){
                sb.append(answer).append("\n");
            }
            else{
                sb.append("Impossible").append("\n");
            }
        }

        System.out.print(sb.toString());

    }

    static int bfs(int start, int end) {

        Queue<int[]> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        visited.add(start);
        queue.add(new int[] {start, 0});

        while(!queue.isEmpty()) {

            int[] curr = queue.poll();

            if(curr[0] == end) {
                return curr[1];
            }

            for(int i=0; i<4; i++) {
                for(int c=0; c<10; c++){

                    int next = change(curr[0], i, c);

                    if(next >999 && prime[next] && !visited.contains(next)) {
                        visited.add(next);
                        queue.add(new int[] {next, curr[1]+1});
                    }

                }
            }

        }
        return -1;
    }

    static int change(int start, int idx, int c){
        StringBuilder sb = new StringBuilder(start+"");
        sb.setCharAt(idx, (char) (c+'0'));
        return Integer.parseInt(sb.toString());
    }

    static void primeSet() {

        prime[0] = prime[1] = false;

        for(int i=2; i<Math.sqrt(10000); i++){
            if(prime[i]){
                for(int j=i*i; j<10000; j+=i){
                    prime[j] = false;
                }
            }
        }

    }

}

/*

1963 소수 경로

소수 판정 + 그래프 이론 문제

해당 문제의 풀이 로직은 다음과 같습니다.
1. 에라토스테네스의 체를 이용하여 1만 이하의 소수를 미리 구해둔다.
2. 입력 받은 첫 시작 소수부터 BFS로 목표 소수에 도달할 수 있는지 확인한다.

문제의 경우 입력 받은 첫 소수에서 항상 자릿수 중 단 하나만 숫자를 바꿀 수 있고, 바꾼 값 또한 소수여야합니다.
그렇다면 먼저 소수를 미리 구한 후 바뀐 숫자 값이 이미 판정된 소수인지 확인만 해주면 시간을 아낄 수 있습니다.
이를 위해 에라토스테네스의 체를 통해 소수를 미리 판별해두면 쉽습니다.

다음으로 모든 숫자를 하나씩 바꿔보고 소수일 때마다 다시 재귀적인 백트래킹을 수행하게 되면 매우 비효율적입니다.
해당 문제에 BFS를 적용하게 되면 항상 한 자리의 숫자를 변환해서 도달한 소수의 가장 최솟값을 구할 수 있습니다.
때문에 BFS를 통해 소수라는 노드를 탐색하는 방법으로 문제를 해결할 수 있습니다.

*/