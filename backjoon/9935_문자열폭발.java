import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static String A,B;
    static char[] arr;
    static int last;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        A = br.readLine();
        B = br.readLine();

        last = B.length()-1;

        arr = new char[1000000];
        int index=0;

        for(int i=0; i<A.length(); i++){
            arr[index] = A.charAt(i);

            if(arr[index]==B.charAt(last) && index >= last && arr[index-(last)] == B.charAt(0)){
                boolean flag = true;
                int val = 0;
                for(int j=index - last; j<=index; j++){
                    if(arr[j] != B.charAt(val++)){
                        flag = false;
                        break;
                    }
                }

                if(flag) index = index - last;
                else index++;
            }
            else index++;
        }

        for(int i=0; i<index; i++){
            sb.append(arr[i]);
        }

        if(index == 0) System.out.println("FRULA");
        else System.out.println(sb);


    }

}
/*
* 분할 정복 응용 문제
* !!! 해당 문제는 풀지 못해 풀이를 참고하였습니다. !!!
* 결국 규칙성에 대한 풀이를 보았기 때문에 과정에 대해서는 스스로 설명드릴 수 없지만
* 결론은 {{1,1},{1,0}}의 행렬을 n번 제곱하면 {{F[n+1], F[N]},{F[N], F[N-1]}} 의 결과가 나온다고 요약 할 수 있습니다.
*
* 만약 행렬을 제곱하는 방법에 대해서 잘 모르시면 10830 행렬제곱 문제를 풀어보시면 쉽게 이 문제를 구현할 수 있습니다.
*
* */