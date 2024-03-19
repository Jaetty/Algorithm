import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static Node[] nodes;
    static List<int[]> list[];

    static class Node{

        Node parent;
        ArrayList<Integer> path;
        Integer id, h, branch, sum;

        Node(int id){
            this.id = id;
            this.path = new ArrayList<>();
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        nodes = new Node[N+1];
        list = new List[N+1];

        for(int i=1; i<N; i++){
            st = new StringTokenizer(br.readLine());

            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            if(nodes[left]==null){
                nodes[left] = new Node(left);
                list[left] = new ArrayList<>();
            }

            if(nodes[right]==null){
                nodes[right] = new Node(right);
                list[right] = new ArrayList<>();
            }

            list[left].add(new int[]{right, cost});
            list[right].add(new int[]{left, cost});

        }

        ArrayList<Integer> path = new ArrayList<>();
        path.add(1);

        dfs(1, 0, 0, path, 0);

        M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());

            Node left = nodes[Integer.parseInt(st.nextToken())];
            Node right = nodes[Integer.parseInt(st.nextToken())];

            int answer = 0;

            if(left.path.get(left.path.size()-1) == right.path.get(right.path.size()-1)){
                // 만약 부모 배열이 값은 같다면 그들이 같은 분파인지 확인

                if(left.branch == right.branch){

                    // 같은 분파면 둘 중 높이 값이 작은 것이 조상
                    if(left.h < right.h){
                        answer = right.sum - left.sum;
                    }
                    else{
                        answer = left.sum - right.sum;
                    }

                }
                else{
                    // 같은 분파가 아니면 마지막 path 값이 공통 조상
                    int lca = nodes[left.path.get(left.path.size()-1)].sum;
                    answer = (left.sum - lca) + (right.sum - lca);
                }

            }else{
                // 부모가 공통이지 않다면 서로 공통이 되는 마지막 부분을 찾는다.
                answer = lower_bound(left, right);

            }

            sb.append(answer+"\n");
        }

        System.out.print(sb.toString());

    }

    static int lower_bound(Node left, Node right){

        int hi = Math.min(left.path.size(), right.path.size())-1;
        int lo = 0;
        int lca = 1;

        if(left.path.get(hi)==right.path.get(hi)){

            if(hi == left.path.size()-1){

                Node tmp = nodes[right.path.get(hi+1)]; // left의 분기가 더 적을 때

                if(left.branch == tmp.branch){
                    if(left.h > tmp.h){
                        return (left.sum - tmp.sum) + (right.sum - tmp.sum);
                    }
                    else return right.sum - left.sum;
                }

            }
            else {

                Node tmp = nodes[left.path.get(hi+1)];

                if(right.branch == tmp.branch){
                    if(right.h > tmp.h){
                        return (right.sum - tmp.sum) + (left.sum - tmp.sum);
                    }
                    else return left.sum - right.sum;
                }
            }

            lca = nodes[left.path.get(hi)].sum;

            return (left.sum - lca) + (right.sum - lca);
        }

        while(lo<hi){

            int mid = (hi+lo)/2;

            if(left.path.get(mid) == right.path.get(mid)){
                lca = nodes[left.path.get(mid)].sum;
                lo = mid+1;
            }
            else {
                hi = mid;
            }

        }


        return (left.sum - lca )+(right.sum - lca);
    }

    static void dfs(int curr, int h, int branch, ArrayList<Integer> path, int sum){

        nodes[curr].h = h;
        nodes[curr].branch = branch;
        nodes[curr].sum = sum;
        nodes[curr].path = (ArrayList<Integer>) path.clone();
        int count = 0;

        if(list[curr].size()>2 && curr!=1){
            path.add(curr);
        }

        for(int[] next : list[curr]){
            if(nodes[next[0]].h != null) continue;
            nodes[next[0]].parent = nodes[curr];
            dfs(nodes[next[0]].id, h+1, branch + count++, path, sum + next[1]);
        }

        if(list[curr].size()>2 && curr!=1) path.remove(path.size()-1);

    }

}

/*
* 트리 (최소 공통 조상) 문제
*
* 해당 문제의 풀이는 11438 LCA2 풀이를 보고 오셔야 합니다.
* LCA2의 풀이를 베이스로 풀이된 문제입니다.
*
* 해당 문제의 포인트는 물어보는 A와 B의 노드의 LCA(최소 공통 조상)을 찾은 후 각각의 노드에서부터 LCA 까지의 거리를 구해서 더해주면 됩니다.
*
* 거리를 기억하게 만드는 방법은 다음과 같이 정했습니다.
* 어차피 1에서 부터 시작하니 1을 루트 노드로 정해서 1에서 부터 얼만큼 멀어졌는지를 계속해서 더해줍니다.
*
* 예를 들어 다음과 같은 입력이 있다면
* 5
* 1 2 1
* 2 3 2
* 3 4 3
* 1 5 4
*
* 1에서 1까지의 거리는 0
* 1에서 2까지는 1
* 1에서 3까지는 3
* 1에서 4까지는 6
* 1에서 5까지는 4
*
* 이렇게 1을 기준으로 얼마나 먼지를 각각의 노드에 기억하게 해줍니다.
* 이후 5에서 4까지 가는 방법을 물어보게 되면
* 5와 4는 LCA가 1이므로
* 6 + 4 = 10으로 10이 정답이 됩니다.
*
* 2와 4를 물어보면, LCA는 2이므로
* 4에서 2까지의 거리인 6 - 1 = 5 가 됩니다.
*
* 이렇게 LCA2 문제의 코드에서 간단히 거리를 계산해주는 처리를 해주면 해당 문제를 풀이할 수 있습니다.
*
*
* */