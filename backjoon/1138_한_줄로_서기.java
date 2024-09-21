import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[N];

        for(int i=0; i<N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        ArrayList<Integer> list = new ArrayList<>();

        for(int i=N-1; i>=0; i--){

            int count = 0;
            int index = 0;

            while (count < arr[i] && index < list.size()){

                if(list.get(index) > i){
                    count++;
                }
                index++;
            }

            list.add(index, i+1);
        }

        for(int val : list){
            sb.append(val +" ");
        }

        System.out.print(sb.toString());

    }

}
/*
 * 그리디 문제
 *
 * 해당 문제의 포인트는 자신에 왼쪽에 몇명이 더 있다가 아닌, 자신에 왼쪽에 나보다 키가 더 큰 사람이 몇명 있는지 기억하는 점 입니다.
 * 그러면 당연히 키가 가장 큰 사람은 아무리 왼쪽에 사람이 많아도 자기보다 큰 사람이 없으니 0이 됩니다.
 * 그리고 다음으로 키 큰 친구는 자기보다 키 큰 사람이 1명 밖에 없을 것이고
 * 그 다음으로 키큰 사람은 또 자기보다 키 큰 사람이 2명 밖에 없고... 이를 반복하게 될 것입니다.
 *
 * 그러면 이 가장 키가 큰 친구를 맨 처음에 세워두어 기준으로 사용할 수 있습니다.
 *
 * 결국 로직은 다음과 같습니다.
 * 1. 키가 큰 친구들부터 줄을 서기 시작한다.
 * 2. 친구들이 자기보다 더 큰 사람의 수를 채울 수 있을 만큼 뒤에 가서 줄을 선다.
 *
 * 예시 입력이 다음과 같다면
 * 4
 * 2 1 1 0
 *
 * 가장 키가 큰 4부터 줄을 세우는데 4보다 큰 사람이 없으므로 맨 앞에 줄을 세웁니다. 줄 : [4]
 * 그 이후 3을 줄을 세우는데 자기보다 왼쪽에 키큰 사람이 1명 있으므로 그만큼 뒤로 가서 줄을 섭니다. 줄 : ['4', '3']
 * 이후 2도 자기보다 왼쪽의 크기가 큰 사람은 1명이므로 1명을 충족할 때까지 뒤로 갑니다. ['4','2','3']
 * 1도 마찬가지로 처리해줍니다. ['4', '2', '1', '3']
 *
 * 이와 같이 해당 문제를 풀이할 수 있습니다.
 *
 *
 * */