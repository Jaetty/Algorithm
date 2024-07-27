import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, index;
    static Map<Integer, Integer> parent, count;
    static Map<String, Integer> map;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine());

        for(int tc=0; tc<T; tc++){

            N = Integer.parseInt(br.readLine());

            parent = new HashMap<>();
            count = new HashMap<>();
            map = new HashMap<>();
            index = 0;

            for(int i=0; i<N; i++){
                st = new StringTokenizer(br.readLine());

                int left = getIndex(st.nextToken());
                int right = getIndex(st.nextToken());

                sb.append(union(left, right)+"\n");

            }

        }

        System.out.print(sb.toString());

    }

    static int getIndex(String name){

        if(map.containsKey(name)){
            return map.get(name);
        }
        else{
            map.put(name, index);
            parent.put(index, index);
            count.put(index, 1);
            return index++;
        }

    }

    static int find(int x){
        if(parent.get(x)==x) return x;
        else{
            parent.put(x, find(parent.get(x)));
            return parent.get(x);
        }
    }

    static int union(int x, int y){

        int nx = find(x);
        int ny = find(y);

        if(nx==ny){
            return count.get(nx);
        }

        count.put(ny, count.get(ny) + count.get(nx));
        parent.put(nx, ny);

        return count.get(ny);

    }


}

/*
 * 유니온 파인드 문제
 *
 * 해당 문제는 유니온 파인드 응용으로 포인트는 다음과 같습니다.
 * 1. 어떤 트리에 노드의 개수를 저장하는 배열을 만든다.(count 배열)
 * 2. Map을 통해 들어오는 String 값을 숫자로 기억하게 해준다.
 * 3. 유니온 파인드를 수행할 때 노드 개수의 값을 갱신해준다.
 *
 * 이 문제의 경우 기본 유니온 파인드와 다르게 입력되는 값이 String입니다.
 * String으로는 관리가 어려우므로 String에게 각각 id를 부여해주고 이를 기억하면 됩니다. 이때 유용한 것이 HashMap입니다.
 * 그리고 N이 최대 100_000개 이므로 최악의 경우 존재할 수 있는 모든 사람은 20만명 입니다.
 * 이에 맞춰서 배열을 만들어도 되고, 저처럼 모두 Map을 이용하여 해결해도 괜찮습니다.
 *
 * 결국 문제의 핵심은 유니온으로 트리가 합쳐졌을 때 합쳐진 트리의 노드의 총 갯수는 얼마인지 물어보고 있습니다.
 * 그렇다면 노드의 갯수를 따로 저장해두고 합쳐질 때 한쪽의 트리의 노드 수를 합쳐지는 트리에 더해주면 됩니다.
 *
 * 이를 코드로 나타내면 쉽게 풀이할 수 있습니다.
 *
 * */