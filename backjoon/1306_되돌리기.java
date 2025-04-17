import java.io.*;
import java.util.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {
    static int N;

    static class Node{
        int time, undo;
        String input;
        boolean flag;

        Node(){
            this.flag = true;
            this.undo = -1;
        }

    }
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        Node[] nodes = new Node[N];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            String type = st.nextToken();
            String input = st.nextToken();
            int time = Integer.parseInt(st.nextToken());

            nodes[i] = new Node();
            nodes[i].time = time;

            if(type.equals("type")){
                nodes[i].input = input;
            }
            else{
                nodes[i].undo = Integer.parseInt(input);
            }
        }

        // 전처리 작업
        for(int i=N-1; i>=0; i--){
            if(nodes[i].undo>=0 && nodes[i].flag){
                int limit = nodes[i].time - nodes[i].undo;
                for(int j=i-1; j>=0; j--){

                    if(nodes[j].time < limit){
                        break;
                    }
                    nodes[j].flag = false;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<N; i++){

            if(nodes[i].undo<0 && nodes[i].flag){
                sb.append(nodes[i].input);
            }

        }

        System.out.print(sb.toString());

    }

}
/*

1306 되돌리기

구현 문제

문제의 핵심은 어떤 undo가 작동하는가를 파악하는 것 입니다.

예를 들어 다음과 같은 입력이 있다면,

4
type a 1
type b 2
undo 2 3
undo 2 4

처음 undo는 ab를 없애버립니다.
그러나 다음 undo는 앞의 undo를 없던걸로 하니까, ab가 있는거고, ab에서 b를 없는걸로 해버립니다.

그러면 앞에 undo 2 3 은 없다고 생각해도 무방합니다.
이를 없는 것과 동일하게 만들어주는건 뒤에 undo입니다.

즉, 뒤에 나오는 undo는 앞에 나오는 undo를 애초에 없던 것과 같이 만들 수 있다는 겁니다.
그러면 이 문제는 다음 입력과 같아집니다.
3
type a 1
type b 2
undo 2 4

그러면 이 점에 착안해서 입력을 받은 후, 뒤에서부터 순회면서 진짜로 작동하는 undo에 대해서만 작동하게끔 만들면,
O(N)의 시간만에 풀이가 가능해집니다.

*/