import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        char[] stack = new char[input.length()];

        int index = 0;

        for(int i=0; i<input.length(); i++){

            stack[index] = input.charAt(i);

            if(index>=3){
                StringBuilder sb = new StringBuilder();
                for(int j=index-3; j<=index; j++){
                    sb.append(stack[j]);
                }
                if(sb.toString().equals("PPAP")){
                    index -= 2;
                    continue;
                }
            }
            index++;
        }

        if(stack[0]=='P' && index==1){
            System.out.println("PPAP");
        }
        else System.out.println("NP");

    }

}

/*
 * 문자열 + 스택 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. PPAP = P로 나타낼 수 있다.
 *
 * 포인트를 고려해서 문제를 생각해보면 결국 모든 문자가 PPAP로 구성된 문자열은 계속 P로 축약시키면 마지막에 "P" 하나로 변환이 가능하다는 것을 알 수 있습니다.
 * 즉 문자열 입력이 어떻게 주어지던, PPAP를 구성하는 부분 문자열을 P로 변환하면 됩니다.
 *
 * 그러면 생각해볼 수 있는 점은 입력으로 들어온 값들을 처음부터 순회하면서 PPAP를 마주쳤을 때 그 부분을 P로 바꾸면 된다는 결론이 나옵니다.
 * 여기서 PPAP가 구성되었는지 확인하는 방법으로 스택을 사용합니다.
 * 스택에 차곡히 쌓인 문자가 뒤에서부터 4개를 뽑았을 때 PPAP가 되는지 확인하고 PPAP가 맞다면 그만큼 pop (여기서는 포인터 이동) 해주면 됩니다.
 *
 * 이렇게 문제를 해결한 후 이를 코드로 나타내면 문제를 풀이할 수 있습니다.
 *
 * */