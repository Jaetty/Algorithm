import java.io.*;
import java.util.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N,M;
    static int parent[];

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        parent = new int[N];
        makeSet();

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if(union(x,y)){
                System.out.println(i+1);
                return;
            }
        }
        System.out.println(0);
    }
    static void makeSet(){
        for(int i=0; i<N; i++){
            parent[i] = i;
        }
    }

    static int findSet(int x){
        if(parent[x]==x) return x;
        return parent[x] = findSet(parent[x]);
    }

    static boolean union(int x, int y){
        int nx = findSet(x);
        int ny = findSet(y);

        if(nx==ny) return true;

        parent[nx] = parent[ny];
        return false;

    }

}
/*
* 유니온 파인드 문제
* 매우 기초적인 유니온 파인드 문제입니다. 사이클이 존재하는지 파악하기만 하면 되는 문제입니다.
* 유니온파인드는 결국 서로 공통된 부모를 가지는지 확인하면 사이클이 존재한다고 판단하는 알고리즘입니다.
* 이를 구현한게 위의 코드와 같아서 유용하게 사용할 수 있습니다.
*
* */