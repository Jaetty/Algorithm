import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static String[] arr;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
//        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(br.readLine());

        arr = new String[N];

        for(int i=0; i<N; i++){
            arr[i] = br.readLine();
        }

        boolean[] check = new boolean['Z'-'A'+1];

        for(int i=0; i<N; i++){

            int index = -1;
            String val = arr[i].toUpperCase();

            if(!check[val.charAt(0)-'A']){
                index = 0;
            }

            if(index < 0){
                String[] split = val.split(" ");
                int length = split[0].length()+1;

                for(int j=1; j<split.length; j++){

                    if(!check[split[j].charAt(0)-'A']){
                        index = length;
                        break;
                    }
                    else{
                        length += split[j].length()+1;
                    }
                }
            }

            if(index<0){

                for(int j=0; j<val.length(); j++){

                    if(val.charAt(j)==' ') continue;

                    if(!check[val.charAt(j)-'A']){
                        index = j;
                        break;
                    }
                }
            }

            if(index>=0){
                check[val.charAt(index)-'A'] = true;
            }

            StringBuilder make = new StringBuilder();
            if(i>0){
                make.append("\n");
            }
            for(int j=0; j<arr[i].length(); j++){
                if(j==index){
                    make.append("[");
                    make.append(arr[i].charAt(j));
                    make.append("]");
                }
                else make.append(arr[i].charAt(j));
            }
            sb.append(make.toString());

        }

        System.out.print(sb.toString());

    }

}
/*
 * 문자열 문제
 *
 * 해당 문제의 경우 문자열을 이용한 구현 문제로 실제 문제에서 제시하는 내용들만 구현해주면 됩니다.
 * 여러가지 구현 방법이 있겠지만 저는 다음과 같은 방식으로 구현을 했습니다.
 *
 * 먼저 어떤 문자가 등록되었는가는 boolean 배열로 대문자로 A~Z의 영문자의 인덱스 값이 true인지 확인하는 방식을 사용.
 * 이렇게 함으로써 대소문자 구분을 할 필요가 없어집니다. 또한 새 변수를 두어 대문자로 바뀐 문자열을 따로 기억해줍니다.
 * 그리고 다음은 그 대문자 문자열을 조건에 맞게 순회하며 등록되어있지 않은 문자의 위치를 기억합니다.
 * 1. 각 단어의 시작 문자가 등록되었는지 확인.
 * 2. 이후 문자열을 왼쪽부터 순회하며 등록되었는지 확인.
 *
 * 이 과정을 거친 후 index값을 알게되었다면, 원본 문자열을 순회하며 해당 index 차례가 오면 그 좌우에 대괄호를 씌워주고 이를 출력하면 됩니다.
 *
 * */