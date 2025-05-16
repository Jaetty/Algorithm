import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        PriorityQueue<String> pq = new PriorityQueue<>((e1, e2) -> {
            String o1 = e1 + e2;
            String o2 = e2 + e1;
            return o2.compareTo(o1);
        });

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            String val = st.nextToken();
            pq.add(val);
        }

        while(!pq.isEmpty()){
            sb.append(pq.poll());
        }

        // 맨 앞이 0인 경우는 결국 0밖에 없다는 뜻이므로 0만 출력하게끔 수정
        if(sb.charAt(0) == '0'){
            sb.setLength(1);
        }

        System.out.print(sb.toString());

    }

}

/*

16496 큰 수 만들기

그리디 문제

이 문제의 고려 포인트는
1. n <= 1000
2. 각각의 숫자는 0 ~ 1,000,000,000입니다.
3. 정답이 0인 경우엔 0 하나만 출력함.

해당 문제의 경우 문제를 본 순간 대부분은 똑같은 생각을 할 것 같습니다.
"반드시 앞자리 숫자가 큰 경우 먼저 앞에 붙여서 숫자를 만들면 된다."

그러나 문제가 되는 점은 주어지는 숫자가 너무 크다는 점입니다. long이나 int형으로 다루기엔 버거운 부분이 있습니다.
결국, 이 문제는 숫자로써 비교하지말고 문자열로 비교하는 것이 핵심입니다.

그리고 문자열로 서로 비교하기 가장 쉬운 방법은 두 문자열을 붙인 뒤 어느게 더 큰지 비교하는 방법입니다.

가령, 입력으로 [9, 31]이 주어졌다고 해보겠습니다.
그러면 "9" + "31" 과 "31" + "9"를 해서 931과 319를 비교하여 더 큰 숫자를 앞에 두는 PriorityQueue를 만들면 해결할 수 있었습니다.

핵심은 1000개의 문자열을 서로 붙여서 큰 순서로 정렬(저는 pq를 이용)하면 해결할 수 있는 문제입니다.

*/