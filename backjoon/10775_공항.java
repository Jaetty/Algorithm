import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[] parent;
    static int G,P;

    static final int MAX = 20000000;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        G = Integer.parseInt(br.readLine());
        P = Integer.parseInt(br.readLine());

        parent = new int[G+1];
        makeSet();

        int count = 0;
        boolean flag = false;

        for(int i=0; i<P; i++){

            int plane = Integer.parseInt(br.readLine());
            if(flag) continue;

            int gateNum = parent[plane];
            boolean check = true;

            if(gateNum==MAX){
                parent[plane] = plane;
                count++;
            }
            else{
                for(int j=gateNum-1; j>0; j--){
                    if(parent[j]==MAX){
                        parent[j] = j;
                        parent[findSet(j+1)] = findSet(j);
                        count++;
                        check = false;
                        break;
                    }
                    else{
                        parent[findSet(j+1)] = findSet(j);
                        j = findSet(j);
                    }
                }
                if(check) flag = check;
            }

        }

        System.out.println(count);
    }

    static void makeSet(){
        for(int i=1; i<=G; i++){
            parent[i] = MAX;
        }
    }

    static int findSet(int x){
        if(parent[x]==x) return x;
        else return parent[x] = findSet(parent[x]);
    }

}

/*
* 그리디 + 유니온 파인드 문제
*
* 저만 그런지 모르겠지만 저는 의외로 해당 문제를 이해하기까지 조금 시간이 걸렸는데 문제의 핵심 내용은 이렇습니다.
* 비행기가 착륙할 게이트 번호 이하의 값에만 착률할 수 있다.
*
* 예를 들어 게이트가 5개가 있고 비행기가 3게이트에 착륙한다고 하면 이 비행기는 3, 2, 1 중 한 곳에 착륙할 수 있습니다.
* 즉 4, 5에는 착륙하지 못한다는 뜻이고 3, 2, 1 중 한 곳에 우리는 가장 좋은 방법으로 착륙시켜서 가장 많이 착륙할 수 있는 경우를 구해야합니다.
*
* 이 문제 자체의 풀이 포인트는 그리디 입니다.
* 그리디하게 생각했을 때 착륙하려고 하는 게이트 값에서 비어있는 게이트 중 가장 큰 값에 내려주면 됩니다.
* 예를 들어 다음과 같은 입력이 주어진다면

4
6
2
2
3
3
4
4

* 백준에서는 위의 입력이 주어지면 [1][2][3][?] 형태로 도킹시킬 수 있다고 힌트를 주는데 이런 방법도 있겠지만 사실 이건 힌트가 아니라 덫입니다.
* 그리디하게 생각해보면 2 게이트에 내리겠다고 하면 1~2까지 남은 게이트 중 가장 큰 2에 내려주면 됩니다.
* 또다시 2에 내리겠다고하면 똑같이 1~2 게이트에서 비어있는 가장 큰 게이트인 1에 내려주면 됩니다.
* 즉 순서로 따지면 [2][1][3][?] 형태가 됩니다.
*
* 이렇게 풀이하면 문제 자체는 풀이가 가능합니다. 다만 입력이 크므로 시간초과를 고려해야합니다.
* 일일히 O(N^2)의 방법으로 비어있는 게이트를 확인해주는 것이 아닌 유니온 파인드를 응용하여 이미 착륙한 각 게이트에서 가장 작은 값을 기억하게 만들어주면 됩니다.
*
* 예를 들어 게이트가 4개가 있고 비어져있는 형태가 다음과 같다고 하겠습니다.
* [?][?][?][?]
* 그러면 입력으로 비행기가 4번에 내리겠다고 한다면
* [?][?][?][4] 이렇게 4값을 넣어주어 4번 게이트는 이미 누가 내렸고 4번이 가장 작은 값이야 라고 기억하게 해줍니다.
* 그리고 다음 입력으로 비행기가 2번에 내린다고 하면 다음 형태가 됩니다.
* [?][2][?][4]
*
* 이제 비행기가 4에 내린다고 한다면 4번 게이트에는 이미 4가 들었으므로 4보다 하나 작은 3번 게이트가 비어있는지 확인하고 내려줍니다.
* 그리고 내 위의 값들을 3이 이제부터 가장 작아 변환해줍니다. 즉 배열은 다음과 같이 변합니다.
* [?][2][3][3]
*
* 다시 또 비행기가 4번 게이트로 들어온다면 이제 3이 가장 작으니까 2부터 게이트가 비었는지 찾을 것입니다.
* 그런데 2가 이미 게이트에 들어있다면 게이트를 최신화해줍니다.
* [?][2][2][2]
* 그리고 2-1인 1번 게이트부터 탐색하고 1번이 비었으므로 최종적으로 배열의 형태는
* [1][1][1][1] 이 됩니다.
*
* 이렇게 하면 O(N^2)의 시간동안 탐색할 필요없이 가장 작은 값을 기준으로 다음 값을 탐색하기 때문에 시간을 대폭 줄일 수 있습니다.
*
* 위의 두 포인트를 코드로 구현하면 해당 문제를 풀이할 수 있습니다.
*
* */