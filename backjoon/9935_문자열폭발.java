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
* 스택 응용 문제
* 문자를 하나하나 배열에 넣으면서 만난 문자가 문자열 폭발 마지막 문자와 같다면 문자열 폭발이 이루어지는지 index를 뒤로 가면서 따져본다.
* 필자는 뒤로 찾기 보다 앞으로 찾는 걸 먼저 생각해서 스택의 index 값이 폭발 문자열 크기보다 크고 맨앞문자도 같다면 수행하도록 만들어줬다.
*
* 즉 ABABCC라는 문자열이 있고
* ABC가 폭발 문자열이라고 하자
* ABABCC를 한문자씩 때오면서 다음과 같이 진행된다.
* 1. 스택 : [] 가져온 문자 : A -> 폭발 문자열 마지막 문자가 C이므로 체크하지 않고 넣어준다.
* 2. [A] B -> C가 아니므로 그대로 넣어준다.
* 3. [AB] A -> 넣어준다.
* 4. [ABA] B -> 넣어준다.
* 5. [ABAB] C -> C가 맞으므로 index값을 뒤로 가면서 맞는지 확인한다. (필자는 index-2부터 올려가며 찾아본다.)
* 5-2. 폭발문자열에 해당하기 때문에 index값을 -2 해준다.
* 6. [AB] C -> 5의 상황과 마찬가지로 폭발문자열과 일치하므로 index 값을 -2 해준다.
* 7. index 값의 크기 만큼 출력을 해준다. index가 0이면 비어있다는 뜻으로 FRULA를 출력한다.
* */