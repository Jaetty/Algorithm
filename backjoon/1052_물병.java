import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int answer = 0;

        while(Integer.bitCount(N) > K){

            answer += N & -N; // 비트에서 가장 마지막 1을 빼오는 방법 => 비트에 2의 보수 값을 AND 하면 됨!
            N += N & -N;

        }

        System.out.println(answer);

    }

}

/*
 * 수학 + 비트 마스킹 문제
 *
 * @ 저는 이 문제를 반복을 통해서 풀었는데, 다른 분들의 풀이는 비트마스킹을 이용한 풀이가 많아서 공부해보았습니다.
 * @ 비트마스킹 풀이를 이해하니 확실히 더 효율적이고 응용할 부분도 많아서 이를 기준으로 주석을 적습니다.
 *
 * 해당 문제의 핵심은 물이 주어졌을 때 같은 크기의 물을 한 물통에 몰아줄 수 있는 것인데
 * 이를 통해 알 수 있는 점은 2의 거듭 제곱수 2^N 제곱인 친구들은 물을 서로 합했을 때 최종적으로 1개의 물통이 될 수 있습니다.
 * 그런데 2의 거듭제곱수는 비트로 표현이 됩니다.
 *
 * 예를 들어 입력으로 N=22 K=2가 주어졌고, 22을 2진수로 나타내면 10110 입니다.
 *
 * 10110은 2의 제곱에 해당하는 수가 3개 ('1bit'가 3개 있습니다) 있습니다. 즉 22을 어떤 조합을 해도 최소 3개의 물병이 남는다는 것을 알 수 있습니다.
 * 그렇다면 K가 현재 2인데 물병이 3개가 남는 상황이면 물병을 2개 이하로 줄여야합니다.
 * 저희는 물병을 구매할 수 있고 이는 bit에 더하기를 해도 된다는 뜻입니다.
 *
 * 10110 을 더하기로 1bit가 2개만 존재하게 하기 위해선 10110의 가장 오른쪽에 위치한 1인 00010을 더해주면 됩니다.
 * 10110 + 00010 = 11000이 되고 1이 K개 이하가 되는걸 확인할 수 있습니다.
 * 여기서 알 수 있는 점은 비트의 1의 갯수가 K개 이하가 될 때 까지 마지막 위치에 1을 더해주고 그 더한 값을 누적한 결과가 정답임을 알 수 있습니다.
 *
 * */