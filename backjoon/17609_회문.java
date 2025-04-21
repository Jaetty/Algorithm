import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<N; i++){
            sb.append(two_point(br.readLine())).append("\n");
        }
        System.out.print(sb.toString());

    }
    static int two_point(String val){

        int[] arr = {-1,-1};
        int count = 0;

        while(count<3){

            int left = 0, right = val.length()-1;
            if(count==1){
                left = arr[0]+1;
                right = arr[1];
            }
            if(count==2){
                left = arr[0];
                right = arr[1]-1;
            }
            boolean flag = true;

            while(left<right){

                if(val.charAt(left) != val.charAt(right)){
                    flag = false;
                    if(arr[0]==-1){
                        arr[0] = left;
                        arr[1] = right;
                        break;
                    }
                    break;
                }
                left++;
                right--;

            }

            if(flag) break;
            count++;
        }

        if(count==0){
            return 0;
        }

        return count >= 3 ? 2 : 1;

    }
}

/*
17609 회문

투 포인터 문제

문제의 풀이 방법은 다음과 같습니다.
1. 회문인지 확인한다.
2. 1번 과정 중 서로 일치하지 않는 왼쪽, 오른쪽의 포인터 부분을 기억해둔다.
3. 각각 2번에서 알게된 부분을 한번씩 스킵해보면서 다시 회문인지 확인해본다.

문제의 경우 딱 한 문자만 제외했을 때, 회문이 성립되는지 물어보고 있습니다.
그 말은 즉, 가장 최초에 서로 불일치하는 부분을 만나게 되면 둘 중 한 포인터를 스킵했을 때 일치하는지 확인하면 된다는 점을 알 수 있습니다.

로직의 진행을 다음과 같은 예시 입력으로 진행해보겠습니다.
입력이 다음처럼 주어지면 :
1
abbxa

우선, 회문인지 투포인터로 확인합니다.
abbxa
|   |
(a,a)로 일치
abbxa
 | |
(b,x)로 불일치

이때 충돌이 발생한 최초 지점인 인덱스 {1,3}을 기억해둡니다.
그렇다면, 1, 3까지는 회문에 문제가 없었다는 뜻이므로 최초 시작 포인터를 각각 left = 1, right = 3으로 만들어줍니다.

이후 left를 한번 스킵(left + 1)한 상태에서 회문인지 판단해봅니다.

abbxa
  ||
(b,x)로 불일치
이번에도 불일치이기 때문에 이번엔 left = 1, right = 3에서 right를 한번 스킵(right-1)해주고 회문인지 확인합니다.

abbxa
 ||
(b,b)로 일치

여기서 3을 스킵하면 회문인지 판단되기 때문에, 이 예시는 유사 회문이라는 점을 알 수 있습니다.

이처럼 충돌 없이 말끔한 회문이라면 0을 출력하고,
서로 충돌이 났다면 최초 충돌난 지점의 포인터를 각각 스킵했을 때 서로 일치할 수 있다면 유사 회문으로 1을 출력,
둘 다 스킵을 한번씩 해줬는데도 불구하고 불일치가 일어난다면 1번의 제외로는 회문이 되지 못하기 때문에 2를 출력해주면 됩니다.

  */