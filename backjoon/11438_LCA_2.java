import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static Node[] nodes;

    static class Node{

        Node parent;
        List<Node> edge;
        ArrayList<Integer> path;
        Integer id, h, branch;

        Node(int id){
            this.id = id;
            this.edge = new ArrayList<>();
            this.path = new ArrayList<>();
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        StringTokenizer st;

        nodes = new Node[N+1];

        for(int i=1; i<N; i++){
            st = new StringTokenizer(br.readLine());

            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            if(nodes[left]==null){
                nodes[left] = new Node(left);
            }

            if(nodes[right]==null){
                nodes[right] = new Node(right);
            }

            nodes[left].edge.add(nodes[right]);
            nodes[right].edge.add(nodes[left]);
        }

        ArrayList<Integer> path = new ArrayList<>();
        path.add(1);

        dfs(1, 0, 0, path);

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
                        answer = left.id;
                    }
                    else{
                        answer = right.id;
                    }

                }
                else{
                    // 같은 분파가 아니면 마지막 path 값이 공통 조상
                    answer = left.path.get(left.path.size()-1);
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
        int answer = 1;

        if(left.path.get(hi)==right.path.get(hi)){

            if(hi == left.path.size()-1){

                Node tmp = nodes[right.path.get(hi+1)];

                if(left.branch == tmp.branch){
                    if(left.h > tmp.h){
                        return tmp.id;
                    }
                    else return left.id;
                }

            }
            else {

                Node tmp = nodes[left.path.get(hi+1)];

                if(right.branch == tmp.branch){
                    if(right.h > tmp.h){
                        return tmp.id;
                    }
                    else return right.id;
                }
            }

            return left.path.get(hi);
        }

        while(lo<hi){

            int mid = (hi+lo)/2;

            if(left.path.get(mid) == right.path.get(mid)){
                answer = left.path.get(mid);
                lo = mid+1;
            }
            else {
                hi = mid;
            }

        }


        return answer;
    }

    static void dfs(int curr, int h, int branch, ArrayList<Integer> path){

        nodes[curr].h = h;
        nodes[curr].branch = branch;
        nodes[curr].path = (ArrayList<Integer>) path.clone();
        int count = 0;

        if(nodes[curr].edge.size()>2 && curr!=1){
            path.add(curr);
        }

        for(Node next : nodes[curr].edge){
            if(next.h != null) continue;
            next.parent = nodes[curr];
            dfs(next.id, h+1, branch + count++, path);
        }

        if(nodes[curr].edge.size()>2 && curr!=1) path.remove(path.size()-1);

    }

}

/*
* 트리 (최소 공통 조상) 문제
*
* 해당 문제의 풀이는 같은 깃에 올려져있는데 11437 LCA의 풀이를 먼저 보셔야합니다.
* 그 이유는 풀이 내용이 11473 LCA의 풀이를 바탕으로 나온 풀이기 때문입니다.
*
* 해당 문제는 11437 LCA와 근본적으로는 동일한 문제입니다.
* 다만 입력의 값이 N이 100,000으로 2배가 되었고 M은 100,000으로 10배가 되었습니다.
* 풀이 방식이 저번과 같다면 시간 초과를 벗어나지 못 한다는 사실을 금방 짐작하실 수 있습니다.
*
* 처음에 저는 각 노드들에게 모든 경로를 기억하게 만드는 배열(path)을 만들어서
* 그 배열에서 이분 탐색으로 가장 마지막까지 서로 겹치는 노드를 찾아내는 방법을 떠올렸습니다.
*
* 예를 들어 6이라는 노드의 경로가 path = {1, 2, 4, 6} 이고, 8이라는 노드의 path = {1,2,4,5,7,8} 이라면
* 둘이 가장 마지막까지 겹치는 노드는 4입니다. 이를 이분탐색으로 찾아내는 방식으로 풀이하였습니다.
*
* 다만 시간적으로는 문제가 없지만 너무 많은 노드에 너무나 많은 path 값 때문에 메모리 초과가 되었습니다.
*
* 그래서 위에 기술한 방법에서 조금 수정하여 모든 노드를 기억하는 것이 아닌 분기가 발생한 곳만 기억하자는 방법으로 접근했습니다.
*
* 예를 들면 아래와 트리가 있다고 해보겠습니다.
*
*                   1               h : 0
*                  / \
*                 2   3             h : 1
*                /
*               4                   h : 2
*              / \
*             5   6                 h : 3
*            /   / \
*           7   8   9               h : 4
*          /
*         10                        h : 5
*
* 원래 방식은 모든 경로를 기억해둬야하지만 분기가 발생할 때만 기억하는 방식을 취해줍니다. 여기서 분기가 발생하는 노드는 1, 4, 6번 입니다.
* 위의 트리의 경우 1~4까지의 경로는 path = {1} 이고 5, 6, 7, 10의 path = {1, 4}, 8~9 path = {1, 4, 6} 라는 path를 가지게 됩니다.
* 이렇게 구현하여 먼저 메모리를 절약할 수 있었습니다.
*
* 이제 이 형태를 취한 상태에서 최소 공통 조상을 찾는 방법은 다음과 같습니다.
*
* 먼저 크게 같은 경로를 가지고 있는지 같은 경로를 갖고 있지 않은지로 나뉘어집니다.
*
* 1 같은 경로를 가지고 있을 때 (마지막 경로 값이 같은지만 확인하면 됩니다.)
* -Q 그 둘이 같은 분기 값(변수 명 : branch)을 가지고 있는지 확인하기
* -A 분기가 같지 않다면 이분 탐색으로 경로에서 공통 조상을 찾아내기
* -B 분기 값이 같다면 둘 중 높이 값이 낮은 값을 찾아내기
*
*
* 2 경로가 다를 때 (마지막 경로 값이 다를 때)
* -Q 경로의 크기가 작은 쪽의 마지막 인덱스에 해당하는 값과 경로가 많은 쪽의 같은 인덱스의 값이 같은지 확인하기
* -A 만약 같다면 인덱스 크기를 한 칸 올려서 경로가 많은 쪽 값의 노드를 조회하기
* -A 그 조회한 노드의 분기 값이 경로가 적은 값과 같은지 확인하기
* -A 분기 값이 같다면 h값이 작은 쪽이 정답
* -A 분기가 같지 않다면 작은쪽의 마지막 노드 값이 정답
* -B 만약 Q에 해당하지 않는다면 이분 탐색으로 경로에서 공통 조상 찾아내기
*
* 위와 같이 로직을 세웠고 이를 코드로 구현하였습니다.
* 아래의 트리로 다시 예시를 들어보겠습니다.
*
*                   1               h : 0   (1의 분기 값(branch)은 0)
*                  / \
*                 2   3             h : 1   (2의 branch 0, 3의 branch 1)
*                /
*               4                   h : 2   (4의 branch 0)
*              / \
*             5   6                 h : 3   (5의 branch 0, 6의 branch 1)
*            /   / \
*           7   8   9               h : 4   (7의 branch 0, 8의 branch 1, 9의 branch 2)
*          /
*         10                        h : 5   (10의 branch 0)
*
* (branch 값을 보면 서로 경로가 달라도 branch 값이 같을 때가 있지만 상관없습니다. 애초에 경로 값이 달라서 브랜치로 고려하는 로직의 대상이 되지 않습니다.)
*
* Q. 8과 9를 통해 1번 로직을 예시로 적용해보겠습니다.
* A. 8과 9는 동일하게 path = {1,4,6} 을 갖습니다. 하지만 서로는 같은 분기에 속하고 있지 않습니다.
* A. 이를 구분하기 위해서 분기 값(branch)이라는 것을 주어서 서로 다른 분기에 위치해있다는 것을 구분합니다.
* A. 8과 9의 브랜치가 각각 1, 2로 서로 다르기 때문에 서로는 공통 조상을 6까지만 같고 그 이후는 분파된 것을 알 수 있으므로 6이 최소 공통 조상입니다.
*
* B. 5와 10으로 예시를 들면 둘 다 경로 값은 path = {1,4}입니다. 그리고 브랜치 값이 0으로 같습니다.
* B. 즉 4에서부터 분파해서 그대로 이어져왔다는 것을 알 수 있습니다. 그 중 5가 높이가 낮으므로 5가 최소 공통 조상입니다.
*
* Q. 10번과 8번으로 2번 로직의 예시를 들어보겠습니다.
* A. 서로의 path값은 각각 10의 path = {1,4}, 8의 path={1,4,6}으로 서로 경로가 다릅니다.
* A. 그러면 둘 중 경로가 적은 10의 경로의 인덱스인 1을 가져옵니다.
* A. 8의 path값에서 인덱스 1번에 해당하는 값이 10과 같은지 확인합니다. 즉 10의 path[1] == 8의 path[1] 이 같은지 확인합니다.
* A. 둘 다 같은 4를 지니고 있기 때문에 6번 노드로 가서 6번 노드와 10번 노드의 branch가 같은지 확인합니다.
* A. 서로의 branch 값이 다르니 6과 10은 각각 4에서부터 시작했지만 분파가 다르다는 점을 알게 되었으니 최소 공통 조상은 4가 됩니다.
*
* B. 만약 서로 경로가 아예 다른 경우는 경로 값에서 이분 탐색으로 최소 공통 조상을 찾으면 됩니다.
*
* 저는 위와 같은 방법으로 경로를 기억하는 방식을 수정하였고 이에 맞춰서 최소 공통 조상을 찾는 로직을 세웠습니다.
* 이 로직을 세운 후 이를 코드로 구현하여 문제를 해결할 수 있었습니다.
*
*
*
*
* */