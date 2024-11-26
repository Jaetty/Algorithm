import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main{

    static int K, answer;
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        char[] input = st.nextToken().toCharArray();
        K = Integer.parseInt(st.nextToken());

        visited = new boolean[1000001][K];
        answer = -1;

        Queue<char[]> queue = new ArrayDeque<>();
        queue.add(input);

        for(int k=0; k<K; k++){

            int size = queue.size();

            while(size-- > 0){

                char[] ori = queue.poll();

                for(int i=0; i<input.length-1; i++){

                    for(int j=i+1; j< input.length; j++){

                        char[] value = ori.clone();

                        char tmp = value[i];
                        value[i] = value[j];
                        value[j] = tmp;

                        int var = getInt(value);

                        if( var <= ((int) Math.pow(10, input.length-1))-1 || visited[var][k]){
                            continue;
                        }

                        visited[var][k] = true;

                        if(k==K-1){
                            answer = Math.max(var, answer);
                            continue;
                        }
                        queue.add(value);

                    }
                }
            }

        }

        System.out.println(answer);

    }

    static int getInt(char[] arr){

        int result = 0;

        for(int i=0; i<arr.length; i++){

            if(i>0){
                result*=10;
            }
            result += arr[i]-'0';
        }

        return result;

    }

}

/*
 * 그래프 이론 + 백트래킹 문제
 *
 * 해당 문제의 경우 백트래킹, BFS를 이용하여 풀이가 가능하고 저는 BFS를 이용하였습니다.
 * @ 처음에는 그리디로 풀이를 해보려고 노력했는데 무언가 규칙성이 조금씩 안맞아서 멈췄습니다.
 *
 * 문제의 핵심은 모든 경우의 수를 만들 때 중복을 판단하여 가지치기 하는 것입니다.
 * 단순히 수많은 경우의 수를 전부 시도하게 되면 시간 초과를 벗어나지 못합니다.
 * 그러면 최대한 중복을 벗어난, 즉 가지치기를 할 수 있는 형태의 알고리즘 적용이 필요하게 되고 이때 사용한 방법이 BFS입니다.
 *
 * 총 K번 반복해서 자릿수 교환을 통해 모든 숫자의 경우의 수를 만드는 것은 동일하게 진행해줍니다.
 * 가장 핵심은 어떤 숫자를 몇 K번째에 만들었는지 기억하여 이미 만들었다면 다시 만들지 않게 하는 것 입니다.
 *
 * 예를 들어 다음과 같은 입력이 있을 때
 * 16375 2
 * 1번째 교환 과정에서는 겹치는 경우가 한번도 발생하지 않습니다.
 * 2번째 교환 과정에서 완성된 숫자가 겹치는 모든 경우가 64번입니다.
 *
 * 저희는 어떤 자릿 수가 어디로 옮겨졌는지 관심은 없고 겹치지 않고 완성된 숫자만 신경쓰면 됩니다.
 * 그러므로 어떤 자리수가 K번째에서 한번이라도 만들어졌는지 파악해서 앞으로의 중복 연산을 피할 수 있고 이를 통해 메모리와 시간을 아낄 수 있습니다.
 * 핵심인 중복 피하기만 지키면 BFS나 백트래킹 둘 중 어느 것으로 해도 상관없이 문제를 풀이할 수 있습니다.
 *
 * */