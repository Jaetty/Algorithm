import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine())+1;

        System.out.println(binaryChange(N));


    }

    static String binaryChange(int val){

        StringBuilder sb = new StringBuilder();

        while(val > 0){

            if(val%2!=0){
                sb.append(7);
            }
            else{
                sb.append(4);
            }
            val /= 2;

        }

        return sb.reverse().substring(1, sb.length()).toString();

    }

}

/*
 * 2877 4와 7
 *
 * 수학 + 구현 문제
 *
 * 문제의 핵심은 K번째 이진수 값을 물어보고 있습니다. 즉, 4는 1을, 7은 0을 나타내고 있습니다.
 * 하지만, 주의해야할 점이 3을 물어보게 되면 77이 나옵니다.
 * 저도 계속 틀렸던 부분인데 이건 입력 값에 +1 을 한 후, 맨 앞자리를 제외시키는 방법으로 해결할 수 있습니다.
 *
 * 즉, 100 을 만든 후, 1을 제거하고, 00이 44가 되므로 정답이 44가 나오게 됩니다.
 *
  */