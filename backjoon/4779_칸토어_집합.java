import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static StringBuilder sb;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        while(true){

            String input = br.readLine();
            if(input == null || input.equals("") || input.equals("\0")){
                break;
            }

            int N = Integer.parseInt(input);
            recursive((int)Math.pow(3, N));
            sb.append("\n");

        }

        System.out.print(sb.toString());

    }

    static void recursive(int curr){

        if(curr==1){
            sb.append("-");
            return;
        }

        int divide = curr/3;

        recursive(divide);
        for(int i=0; i<divide; i++){
            sb.append(" ");
        }
        recursive(divide);
    }


}

/*
 * 분할 정복 문제
 *
 * 문제의 핵심은 3등분해서 좌와 우만 "-"를 표시하게 만드는 것 입니다.
 * 매커니즘을 이해하기 쉬운 예로 입력 N = 0, N = 1을 보면 대략의 방법을 떠올릴 수 있습니다.
 *
 * N = 0의 경우 3^0 = 1입니다. 이 경우 "-"를 출력합니다.
 * 여기서 1을 만나면 "-"를 출력하면 된다는 점을 짐작할 수 있습니다.
 *
 * N = 1의 경우 3^1 = 3입니다. 이 경우 "- -"의 결과를 출력합니다.
 * 즉 정확히 3등분 되어 '가운데는 비어져'있고 좌우는 표시되어 있습니다.
 *
 * 그렇다면 재귀를 짤 때, 값을 3등분하고 나누어진 몫으로 다시 재귀를 수행시켜주되 1을 만나면 "-"를 수행하게 해줍니다.
 * 가운데가 비어있다고 했으니, 재귀는 두번만 수행해주고 가운데에는 공백을 몫만큼 출력해주면 됩니다.
 *
 *
 * */