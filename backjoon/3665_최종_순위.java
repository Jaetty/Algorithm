import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Node {

        int val, move;
        boolean[] change;

        Node(int val, int n) {
            this.val = val;
            this.move = 0;
            change = new boolean[n+1];
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int tc = Integer.parseInt(br.readLine());

        for(int T = 0; T < tc; T++) {

            int n = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            Node[] nodes = new Node[n];
            int[] idx = new int[n+1];

            for(int i = 0; i < n; i++) {
                int val = Integer.parseInt(st.nextToken());
                nodes[i] = new Node(val, n);
                idx[val] = i;
            }

            int m = Integer.parseInt(br.readLine());

            for(int i = 0; i < m; i++) {

                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                // 어떤 x값이 몇번 뒤로 가야하고, 몇번 숫자들과 자리를 바꿔야하는지 기록
                if(idx[a] < idx[b]) {
                    nodes[idx[a]].change[b] = true;
                    nodes[idx[a]].move++;
                }
                else{
                    nodes[idx[b]].change[a] = true;
                    nodes[idx[b]].move++;
                }

            }

            boolean flag = false;

            // 만약 자리 바꿈이 발생했다면?
            if(m>0){

                for(int i = n-1; i>=0; i--) {

                    // count는 몇번 자리를 바꾸는데 성공했는지 기억
                    int count = 0;
                    // 현재 x 값을 가져옴
                    Node origin = nodes[i];

                    for(int j = i+1; j<n; j++) {

                        if(!origin.change[nodes[j].val]) {
                            break;
                        }

                        nodes[j-1] = nodes[j];
                        nodes[j] = origin;
                        count++;
                    }

                    // 만약 원래 뒤로 가야하는 만큼 가지 못하거나 더 갔다면? 논리적으로 맞지 않다는 뜻!
                    if(count != origin.move) {
                        flag = true;
                        break;
                    }
                }
            }

            if(flag) {
                sb.append("IMPOSSIBLE");
            }
            else {

                for(int i = 0; i < n; i++) {
                    sb.append(nodes[i].val).append(" ");
                }

            }
            sb.append('\n');

        }
        System.out.print(sb.toString());

    }

}

/*

3665 최종 순위

정렬 문제

해당 문제는 문제를 잘 해석하고 매커니즘에 맞게만 구현하면 쉽게 풀이할 수 있습니다.

가령 다음과 같은 예시 입력이 있다고 해보겠습니다.
1
5
5 4 3 2 1
2
2 4
3 4

정답 : 5 3 2 4 1

문제를 해석하면 처음 주어지는 배열에서 특정 값들 끼리 위치의 변화가 있는 경우 m번 동안 그것을 나타낸다고 하고 있습니다.
위의 입력의 경우 (2,4), (3,4)가 변화가 있었습니다.

저는 먼저 만약 앞에서 두번째에 있는 '4'를 맨 마지막 '1'의 위치로 가게 만드려면 입력이 어떻게 주어질까? 를 생각해봤습니다.

그럴려면 단순히 (4,1)이나 (1,4)와 같은 입력으로는 논리적으로 맞지 않습니다.
이게 성립하려면 (4,1)(4,3),(4,2) 이런식으로 각각 '4'의 뒤에 있는 2,3,1들과도 변화가 발생했다고 표기해줘야합니다.

그렇다면? 이걸 로직으로서 생각해보면
"배열에서 어떤 x값이 현재 위치에서 뒤로 몇 칸 가려면, 그만큼의 상대적 변화가 발생했다는 입력이 필요하다." 라고 해석할 수 있었습니다.

예를 들면, 예시 입력의 '3'이 1의 자리(뒤로 2칸)로 가려면 (1,3), (3,2)와 같은 입력 값이 필요하고,
'5'가 '2'의 자리로 가려면 (5,4), (5,3), (5,2)와 같이 변화를 나타내는 입력이 필요합니다.
반대로 '5'가 '2'의 자리로 가려는데 (5,4), (5,2)와 같은 입력이 들어오면 3과는 자리를 바꾸지 않으면서 2의 자리에 간다는 모순이 발생합니다.

결국, 로직은 어떤 x값에 대해서 변화하는 입력이 갯수와, 각각 어떤 값들과 변화가 일어나는지 확인하여, 한칸씩 뒤로 보내주면 됩니다.
그 과정에서 논리적으로 맞지 않는 경우, (예, 5가 2의 자리로 가야하는데 3을 거치지 않고 간다는 등) "IMPOSSIBLE"을 출력하면 됩니다.

*/