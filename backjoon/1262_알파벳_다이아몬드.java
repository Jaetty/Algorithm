import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, R1, C1, R2, C2, L;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        R1 = Integer.parseInt(st.nextToken());
        C1 = Integer.parseInt(st.nextToken());
        R2 = Integer.parseInt(st.nextToken());
        C2 = Integer.parseInt(st.nextToken());

        L = N*2-1;
        StringBuilder sb = new StringBuilder();

        for(int r=R1; r<=R2; r++){
            for(int c=C1; c<=C2; c++){

                int nr = r%L;
                int nc = c%L;
                int diff = Math.abs(N-1 - nr) + Math.abs(N-1 - nc);
                if (diff > N-1) {
                    sb.append('.');
                } else {
                    sb.append( (char) ('a' + diff%26));
                }

            }
            sb.append("\n");
        }
        System.out.print(sb.toString());

    }
}

/*

1262 알파벳 다이아몬드

구현 문제

해당 문제의 핵심은 규칙성을 찾아내어 구현하는 것 입니다.

이 문제의 최대 N은 20000만으로 미리 세팅한 값을 출력하게되면 메모리와 시간 모두 초과될 것 입니다.
그렇다면 단순히 R1 ~ R2까지 C1 ~ C2까지 반복문을 돌아가면서 규칙에 맞다면 출력하게 만들어야 합니다.

제가 규칙을 찾은 방법은 N=1, N=2, N=3의 경우를 두고
N=2일 때 배열의 총 크기와 [r][c]가 어떤 값일 때 무엇이 출력되는지 집중했습니다.
여기서 어렴풋이 어떤 처리를 한 r과 c를 더해야할 것 같다는 생각이 들었습니다.
또 N*2-1만큼 배열의 가로 세로 길이가 있고, N-1이 딱 중앙을 나타낸다는 것에 집중하여 규칙성을 찾아 풀이할 수 있었습니다.

문제가 규칙성을 찾는 것이라는 것은 금방 알았지만 규칙을 찾는 과정은 예상외로 꽤 시간이 걸렸던 문제였습니다.

*/