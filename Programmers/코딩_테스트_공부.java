import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {

    static int dest_alp, dest_cop, answer;

    static int[][] visited;

    static class Study{
        int time;
        int alp;
        int cop;

        Study(int alp, int cop, int time){
            this.alp = alp;
            this.cop = cop;
            this.time = time;
        }

    }

    static PriorityQueue<Study> pqueue;
    static List<Integer> index;

    public int solution(int alp, int cop, int[][] problems) {
        answer = 0;
        dest_alp = 0;
        dest_cop = 0;

        pqueue = new PriorityQueue<>((e1,e2)-> e1.time - e2.time);
        index = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        Set<String> set = new HashSet<>();

        // 똑같이 반복되는 문제와, 의미가 없는 문제를 없애준다.
        for(int i=0; i<problems.length; i++){
            sb.setLength(0);
            dest_alp = Integer.max(problems[i][0], dest_alp);
            dest_cop = Integer.max(problems[i][1], dest_cop);

            if(problems[i][2] + problems[i][3] <= problems[i][4]) continue;

            sb.append(problems[i][0]).append(problems[i][1]).append(problems[i][2])
                    .append(problems[i][3]).append(problems[i][4]);

            if(set.contains(sb.toString())) continue;
            index.add(i);
        }

        visited = new int[2501][2501];

        for(int i=0; i<=2500; i++){
            for(int j=0; j<=2500; j++) visited[i][j] = Integer.MAX_VALUE;
        }

        pqueue.add(new Study(alp, cop, 0));
        bfs(problems);

        return answer;
    }

    static void bfs(int[][] problems){
        int alp = 0, cop = 0, time = 0;

        while(true){
            Study study = pqueue.poll();

            alp = study.alp;
            cop = study.cop;
            time = study.time;

            if(time > visited[alp][cop]) continue;

            if(alp>=dest_alp && cop >= dest_cop){
                answer = time;
                return;
            }

            for(int i=0; i<index.size(); i++){

                if(alp < problems[index.get(i)][0] || cop < problems[index.get(i)][1]) continue;
                if(visited[alp+problems[index.get(i)][2]][cop+problems[index.get(i)][3]] <=
                        time+problems[index.get(i)][4]) continue;
                visited[alp+problems[index.get(i)][2]][cop+problems[index.get(i)][3]] = time+problems[index.get(i)][4];
                pqueue.add(new Study(alp + problems[index.get(i)][2], cop+problems[index.get(i)][3],
                        time+problems[index.get(i)][4]));
            }

            if(visited[alp+1][cop] > time+1){
                visited[alp+1][cop] = time+1;
                pqueue.add(new Study(alp + 1, cop, time+1));
            }
            if(visited[alp][cop+1] > time+1){
                visited[alp][cop+1] = time+1;
                pqueue.add(new Study(alp, cop+1, time+1));
            }

        }
    }
}

/*
* 그래프 이론 풀이
* 해당 문제를 봤을 때 DP 혹은 BFS로 풀이가 가능하다고 느꼈습니다.
* 각각 DP와 BFS로 풀이하여 통과가 되었지만 문제가 있는게 DP 방식으로 했을 때 스스로 반례를 만들어봤는데 틀렸음에도 통과하는 경우가 발생하였다는 점이었습니다.
* 그러므로 DP는 틀렸다고 생각하기 때문에 주석 맨 아래에 참고로만 남겨놓겠습니다.
*
* 이제 그래프 이론으로의 풀이에 대해 저는 BFS 적인 방법으로 풀이를 진행하였습니다. 아마도 DFS, 다익스트라 등등의 방법으로도 풀이가 가능할 것으로 보입니다.
* 제가 그래프 이론으로 풀이하기 위해 고려한 포인트는 다음과 같습니다.
*
* 1. problems에서 만약 [10,10,1,1,3]과 같이 문제를 풀어서 얻는 알고력 + 코딩력의 합이 시간보다 낮은 경우는 차라리 단일로 알고력 코딩력을 올리는 편이 낫다.
*    그러므로 해당 problems는 사전에 고려대상에서 제외시켜서 시간초과를 방지한다.
*
* 2. problems의 내용이 똑같은 값이 반복적으로 나온다면 BFS에 몇 번이고 반복하여 큐에 넣을 필요가 없다. 중복을 제거하자.
*
* 3. PriorityQueue를 통해 걸렸던 시간 순으로 정렬하게 만들어서 BFS에서 먼저 인접한 노드에 도착한 순서를 정해준다.
*
* 4. BFS 과정 중에서 중복되거나 시간이 더 걸리면서 이미 들린 노드에 들어가는 경우를 없애서 불필요한 메모리와 시간 낭비를 방지한다.
*    예를 들어 [0,0]에서 시작했다면 [0,1] [1,0]으로 파생이 될 것입니다. 그리고 다음 BFS 도중 [0,1]은 [1,1]이 되고 [1,0]도 [1,1]이 될 수 있게 됩니다.
*    그렇다면 앞에서 이미 [1,1]을 했기 때문에 시간 상 더 빨리 수행할 수 있지 않다면 [1,0]에서 [1,1]로 탐색하는 것은 막아서 중복된 노드 탐색을 제거합니다.
*
* 위의 3가지 포인트가 그래프 이론에서 시간초과를 막기 위한 포인트 였습니다.
*
* 다음은 DP, 그래프 공통적으로 고려해야하는 포인트는 다음과 같습니다.
* 여기 예시에서 사용하는 모든 alp = 0, cop = 0이라고 생각해주세요
* 1. 내가 이미 조건을 충족하는 경우가 있을 수 있다. (alp = 0, cop = 0) problems[[0,0,0,0,1]]인 경우 정답은 0입니다.
* 2. 한 쪽만 조건을 충족한 경우가 있다. problems[[0,40,0,0,1]]인 경우 정답은 40입니다.
* 3. problems를 통해 알고력과 코딩력이 상승했는데 목표 지점을 통과할 수 있다. alp = 0, cop = 0 [[0,0,10,10,1],[9,9,0,0,1]] 인 경우 정답은 1입니다.
* 4. 탐색의 범위가 커질 수 있다. problems에 [[0,0,30,2,1] [150,150,0,0,1]]이 있다고 생각해보겠습니다. 정답은 75입니다.
*    여기서 알고력이 30*75 = 2250이 되므로 계산, 탐색할 범위를 크게 잡아줘야합니다.
*
* 바로 위의 4가지 포인트는 DP, BFS로 풀이할 때 공통적으로 고려해야하는 부분입니다.
*
* 그래프 이론으로 풀이하게 되자 공통적으로 고려해야하는 포인트는 1~3번까지는 자연스럽게 해결되었습니다. (DP 풀이에선 공통 포인트를 기초로 점화식을 세워야 했습니다.)
* 4번 같은 경우엔 visited[][] 배열을 만들고 이를 충분히 큰 [2501][2501] 크기로 만들어서 4번 케이스를 해결하였습니다.
*
* 그래프 이론 포인트를 해결하기 위해서는
* 1. 쓸모 있는 problems의 인덱스를 list를 통하여 기억하게 만들었습니다.
* 2. Set과 String을 이용하여 중복된 problems 내용을 기억하지 않게 만들었습니다.
* 3. Study라는 클래스를 이용하여 time 값을 기준으로 오름차 정렬하게 만들어줬습니다.
* 4. int visited[][] 배열을 통해서 가장 작은 time값을 가지게 만들어서 가지치기가 이루어지도록 만들었습니다.
*
* 이처럼 각각의 포인트에 유의하여 코드를 구현하여 문제를 풀이하였습니다.
*
* 아래는 통과는 했지만 정답 여부가 의심스러운 DP 풀이입니다.

class Solution {

    static int answer;
    static int[][] dp;

    public int solution(int alp, int cop, int[][] problems) {
        answer = 0;

        int dx=0, dy=0;

        for(int i=0; i<problems.length; i++){
            dx = Math.max(problems[i][0], dx);
            dy = Math.max(problems[i][1], dy);
        }

        dp = new int[501][501];

        for(int i=0; i<=500; i++){
            for(int j=0; j<=500; j++){
                if(i<=alp && j<=cop) continue;
                int min = 9000;
                for(int k=0; k<problems.length; k++){
                    if(problems[k][2]+problems[k][3] <= problems[k][4]) continue;
                    if(i-problems[k][2] >= problems[k][0] && j-problems[k][3] >= problems[k][1]){
                        min = Math.min(min, dp[i-problems[k][2]][j-problems[k][3]] + problems[k][4]);
                    }
                }


                if(i<=alp){
                    dp[i][j] = Math.min(dp[i][j-1] + 1, min);
                }
                else{
                    dp[i][j] = Math.min(dp[i-1][j] + 1, min);
                }

            }
        }

        int answer = 9000;

        for(int i=dx; i<=500; i++){
            for(int j=dy; j<=500; j++){
                answer = Math.min(dp[i][j],answer);
            }
        }


        return answer;
    }

}
* */