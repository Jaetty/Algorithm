import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int N, K;
    static Integer[] visited;

    static class Node{

        int location, time;

        Node(int local, int time){
            this.location = local;
            this.time = time;
        }

    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        visited = new Integer[100001];

        bfs();
        findPath();

    }
    static void bfs(){

        Queue<Node> queue = new ArrayDeque<>();

        visited[N] = 0;
        Node start = new Node(N,0);
        queue.add(start);

        while(!queue.isEmpty()){

            Node node = queue.poll();

            if(node.location==K){
                return;
            }

            for(int i=0; i<3; i++){

                int nl = node.location;

                switch (i){
                    case 0 :
                        nl += 1;
                        break;
                    case 1 :
                        nl -= 1;
                        break;
                    case 2 :
                        nl *= 2;
                        break;
                }

                if(check(nl, node.time+1)){
                    visited[nl] = node.time+1;
                    Node next = new Node(nl, node.time+1);
                    queue.add(next);
                }
            }
        }

    }

    static void findPath(){

        int curr = K;
        Stack<Integer> path = new Stack<>();
        path.add(K);


        while(curr != N){

            if(curr-1 >= 0 && visited[curr-1] != null && visited[curr-1] == visited[curr]-1){
                path.push(curr-1);
                curr -= 1;
            }
            else if(curr +1 <= 100000 && visited[curr+1] != null && visited[curr+1] == visited[curr]-1){
                path.push(curr+1);
                curr += 1;
            }
            else if(curr/2 >= 0 && visited[curr/2] != null && visited[curr/2] == visited[curr]-1){
                path.push(curr/2);
                curr/=2;
            }
        }

        System.out.println(visited[K]);
        while(!path.isEmpty()){
            System.out.print(path.pop()+" ");
        }

    }

    static boolean check(int nl, int time){
        if(nl<0 || nl > 100000 || (visited[nl] != null && visited[nl] <= time )) return false;
        return true;
    }

}

/*
 * 그래프 이론 문제
 *
 * 해당 문제의 경우 전형적인 BFS 문제입니다. 다만, 이 문제 풀이의 핵심은 BFS를 수행하면서 경로를 찾는 것 입니다.
 *
 * 이 경우 여러가지 방법이 있겠지만 저는 단순히 visited를 int[]로 만든 후 특정 위치로 가는 최소 시간을 기억한 다음 그걸 뒤로 돌아가는 방법을 해결했습니다.
 * 예를 들어, N=5, K=17을 BFS로 탐색하면 visited는 다음과 같습니다.
 *
 * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20....
 * 4 3 2 1 0 1 2 2 2  1  2  2  3  3  4  3  4  3  3  2
 *
 * 그러면 17이 4번만에 도착했으므로 거꾸로 -1, +1, /2를 진행하면서 5위치에 도착할 때까지 다시 탐색을 수행하면됩니다.
 * 여기서 조건은 다음 위치가 현재 위치에서 정확히 -1 만큼의 시간이어야합니다. 즉 17/2 = 8이 나올텐데 8은 2이므로 4보다 2작으므로 탐색이 불가합니다.
 * 이와 같이 BFS로 최소 거리 탐색 -> 탐색을 반대로 진행하면서 경로 탐색하면 문제를 해결할 수 있습니다.
 * @@ 여기서 path는 따로 기록하고, visited를 boolean으로 하면 시간을 더욱 단축할 수 있다고 봅니다.
 *
 * */