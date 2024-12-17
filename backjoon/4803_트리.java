import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M, answer;
    static int[] parent, size;
    static boolean[] isGraph;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int tc = 1;

        while(true){

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            answer =0;

            if(N==0 && M==0){
                break;
            }

            parent = new int[N+1];
            size = new int[N+1];
            isGraph = new boolean[N+1];

            setParent();

            for(int i=0; i<M; i++){

                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                union(a,b);

            }

            sb.append("Case "+tc+": ");

            for(int i=1; i<=N; i++){
                int nx = find(i);
                if(!isGraph[nx]){
                    answer++;
                    isGraph[nx] = true;
                }
            }

            if(answer==0){
                sb.append("No trees.\n");
            }
            else if(answer==1){
                sb.append("There is one tree.\n");
            }
            else{
                sb.append("A forest of " + answer + " trees.\n");
            }

            tc++;
        }

        System.out.print(sb.toString());


    }

    static void setParent(){

        for(int i=1; i<=N; i++){
            parent[i] = i;
            size[i] = 1;
        }

    }

    static int find(int x){

        if(parent[x]==x) return x;
        return parent[x] = find(parent[x]);

    }

    static void union(int x, int y){

        int nx = find(x);
        int ny = find(y);

        if(nx==ny){
            isGraph[nx] = true;
            return;
        }

        if(size[nx] < size[ny]){
            size[ny] += size[nx];
            parent[nx] = ny;
            isGraph[ny] = (isGraph[ny] || isGraph[nx]);
        }
        else{
            size[nx] += size[ny];
            parent[ny] = nx;
            isGraph[nx] = (isGraph[ny] || isGraph[nx]);
        }

    }

}

/*
 * 유니온 파인드 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 트리의 정의를 아는가?
 * 2. 그래프를 탐색할 수 있는가?
 *
 * 문제의 경우 결국 이 두 가지를 할 줄 아는지 묻고 있습니다.
 * 즉, 주어지는 정점과 간선을 통해 주어진 간선을 이었을 때 사이클이 발생하지 않는 것이 몇 개인지 알아내는 것이 핵심입니다.
 *
 * 여기서 그래프 탐색을 이용한 방법도 물론 가능하지만 저는 유니온 파인드를 이용하기로 하였습니다.
 * 유니온 파인드를 통해 간선의 값이 주어질 때 이들을 하나의 트리로 만들어줍니다. (유니온 파인드의 원리는 트리입니다.)
 * 만약 간선으로 이어진 두 정점 값을 가져왔을 때 이미 한 트리에 소속해있다면 사이클이 만들어졌으므로 더이상 트리가 아닙니다.
 * 이를 통해 유니온 파인드로 트리를 계속해서 만들어주면서 트리가 아닌지도 확인하며 최종적으로 트리가 몇 개인지 확인할 수 있게 됩니다.
 *
 * 이걸 나타내는 방법으로 저는 isGraph라는 배열을 만들고, 만약 find(x)와 find(y)의 값이 같다면 isGraph[nx]를 true로 만들어주는 식으로 구현했습니다.
 *
 * 위의 로직을 바탕으로 구현을 하게 되면 문제를 풀이할 수 있습니다.
 *
 * */