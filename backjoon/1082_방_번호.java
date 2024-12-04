import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] price = new int[N];
        int[] answer = new int[51];

        st = new StringTokenizer(br.readLine());

        for(int i=0; i<N; i++){
            price[i] = Integer.parseInt(st.nextToken());
        }

        int money = Integer.parseInt(br.readLine());
        int min=50, index=0;

        for(int i=N-1; i>=0; i--){
            if(price[i]<min){
                min = price[i];
                index = i;
            }
        }

        int cnt = 0;

        while(money - min>=0){
            money -= min;
            answer[cnt++] = index;
        }

        int start = 0;

        for(int i=0; i<cnt; i++){

            for(int j=N-1; j>0; j--){
                if(price[j] <= money + min){
                    answer[i] = j;
                    money = money + min - price[j];
                    break;
                }
            }

            // 맨 앞자리가 0이면 그냥 돈을 환불한다.
            if(answer[start]==0){
                money += price[0];
                start++;
            }

        }

        if(start==cnt){
            System.out.print(0);
        }
        else{
            for(int i=start; i<cnt; i++){
                System.out.print(answer[i]);
            }
        }

    }

}

/*
 * 그리디 문제
 *
 * 해당 문제의 조건을 고려하여 가장 큰 수를 만들 수 있는 방법을 생각해보면 알 수 있는 점은
 * 1. 가장 적은 가격으로 최대한 자릿수를 올리는게 이득이다.
 * 2. 자릿수가 동일할 때 앞 자리에 최대한 큰 수가 들어가는게 좋다.
 *
 * 다음 예시를 바탕으로 문제를 분석해보면
 * 3
 * 6 7 8
 * 21
 *
 * 먼저 최대한 가격이 작은 수로 최대한 자릿수가 긴 숫자를 만들어줍니다.
 * 6이 가장 저렴하므로 18가격으로 3자리를 맞추면 "000" 이 됩니다. 남은 가격은 21-18 = 3입니다.
 * 여기서 앞자리의 숫자들을 더 큰 숫자로 바꿀 수 있는지 확인해봅니다.
 *
 * 현재 남은 돈 3원에 6원을 환불했을 때 값 즉, 3+6 = 9원 보다 작거나 같은 가장 큰 값을 찾아봅니다.
 * 2의 경우 8원으로 9보다 작습니다. 그러면 맨 앞자리의 0을 수정합니다. "200"
 * 이를 다음 자리에서도 반복해줍니다. 그러면 남은 돈 1 + 6 = 7이므로 1의 경우 7보다 작거나 같으므로 "210"을 완성할 수 있습니다.
 *
 * 여기서 맨 앞자리가 0일 때를 주의해야합니다.
 * 자릿수가 아무리 길어도 맨 앞자리가 0이라면 성립하지 않습니다.
 * 그러므로 맨 앞자리가 0인지 확인하고 0이면 자릿수를 하나 줄이고 돈을 환불받습니다.
 *
 * 이렇듯 로직을 짤 수 있고, 이를 구현하면 풀이 할 수 있습니다.
 *
 * */