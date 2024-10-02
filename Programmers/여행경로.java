/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

import java.util.*;

class Solution {

    static Map<String, TreeSet<String>> map;
    static Map<String, Integer> visited;
    static int max;
    static boolean flag;

    public String[] solution(String[][] tickets) {

        map = new HashMap<>();
        visited = new HashMap<>();
        max = tickets.length;
        flag = true;

        for(int i=0; i<max; i++){

            if(!map.containsKey(tickets[i][0])){
                map.put(tickets[i][0], new TreeSet<>());
            }
            if(!map.containsKey(tickets[i][1])){
                map.put(tickets[i][1], new TreeSet<>());
            }

            TreeSet a = map.get(tickets[i][0]);
            visited.put(tickets[i][0]+tickets[i][1], visited.getOrDefault(tickets[i][0]+tickets[i][1],0)+1);
            a.add(tickets[i][1]);

        }

        List<String> answer = new ArrayList<>();
        dfs(0, "ICN", answer);

        return answer.toArray(new String[0]);
    }

    static void dfs(int depth, String start, List<String> answer){

        answer.add(start);

        if(depth>=max){
            flag = false;
            return;
        }

        Iterator<String> it = map.get(start).iterator();

        while(flag && it.hasNext()){

            String next = it.next();
            if(visited.get(start+next)<=0) continue;

            visited.put(start+next, visited.get(start+next)-1);
            dfs(depth+1, next, answer);
            visited.put(start+next, visited.get(start+next)+1);
        }

        if(flag) answer.remove(depth);

    }

}

/*
 * DFS 문제
 *
 * 해당 문제의 경우 포인트는 다음과 같습니다.
 * 1. 주어진 항공권을 모두 사용해야만 한다!! (탐색만 잘 하면 모든 항공권은 반드시 다 쓸 수 있다.)
 * 2. 만일 경로가 2개 이상일 경우 알파벳 순서가 앞서는 경로를 return한다.
 *
 * 가장 중요한 포인트는 1번입니다.
 * 2번의 경우 예제 설명에서도 쉽게 볼 수 있지만 1번의 경우는 보여주지 않고 있습니다.
 * 우선 자바의 경우 이들을 기억해야하기 때문에 Map과 2번의 요건을 만족하기 위해 TreeSet을 이용해 기억하게 만들었습니다.
 * 그래서 Iterator로 불러와도 반드시 정렬된 상태로 불러오게 됩니다.
 *
 * 포인트 1번의 경우 다음과 같은 반례가 있습니다.
 * 입력 : [["ICN", "BBB"], ["BBB", "ICN"], ["ICN", "AAA"]]
 * 정답 : ["ICN", "BBB", "ICN", "AAA"]
 *
 * 보시면 ICN에서 출발해서 단순히 빠른 순으로 출발하면 AAA로 가게 되고, AAA에서 출발하는 항공권은 없습니다.
 * 그러므로 AAA가 아닌 먼저 BBB로 간 후 BBB에서 ICN으로 돌아와서 마지막으로 ICN에서 AAA로 출발해야합니다.
 *
 * 요점은 visted 처리를 잘 해줘야하는데 저는 여기에 map<String, Integer>을 이용하였습니다.
 * "ICN"+"BBB"와 같이 a->b 경로를 key 값으로 주고 value에는 항공권이 총 몇 개 있는지 count 하는겁니다.
 * 그리고 한번 사용할 때 마다 해당 항공권의 갯수를 줄여나가, 항공권을 다 쓴 경우에는 더이상 탐색하지 않도록 만들어주면 됩니다.
 *
 * 위의 로직을 기반으로 재귀함수를 짜게 되면 문제를 해결할 수 있습니다. 문제에서는 DFS/BFS 문제라고 명시되었지만 백트래킹에 더 가깝게 느껴졌습니다.
 *
 * */