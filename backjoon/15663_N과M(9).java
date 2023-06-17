import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static int N,M;
    static int[] arr, answer;
    static boolean[] visited;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new int[N];
        answer = new int[M];
        visited = new boolean[N];

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<N; i++){
            arr[i] =  Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        backTrack(0);
        System.out.print(sb);

    }

    static void backTrack(int depth){

        if(depth==M){
            for(int i=0; i<M; i++){
                sb.append(answer[i]+" ");
            }
            sb.append("\n");
            return;
        }

        int before = -1;

        for(int i=0; i<N; i++){
            if(visited[i] || arr[i]==before) continue;
            before = arr[i];
            answer[depth] = arr[i];
            visited[i] = true;
            backTrack(depth+1);
            visited[i] = false;

        }

    }

}
/*
*
* 백트래킹 문제로 고려해야하는 점은 중복되서 나오지 않게 만드는 점이다. 이 점은 before 변수을 이용한다.
* 예시로 N = 3 M = 2이고 9 1 9 라는 값이 입력으로 주어진다면
* 정답은 (1 9) (9 9) 일 것이다. 하지만 visited 변수 하나로는 (1 9) (1 9) (9 9)로 결과가 나올 것이다.
* 배열을 정렬하면 1 9 9 가 나오고 before값에 전에 만났던 값을 넣어준다면 (1 9) 이후 before값은 9가 되고 반복문에서 9 이외의 값을 찾는다.
* 그렇게 해서 중복을 피할 수 있게된다.
*
*
* */