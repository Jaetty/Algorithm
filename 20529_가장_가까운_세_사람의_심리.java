import java.util.*;
import java.io.*;

/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

public class Main {

    static String[] arr;
    static int answer;
    static String firstKey;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int tc=0; tc<T; tc++){
            int N = Integer.parseInt(br.readLine());

            HashMap<String, Integer> hashMap = new HashMap<>();
            String original = br.readLine();

            if(N>=33){
                sb.append(0+"\n");
                continue;
            }

            StringTokenizer st = new StringTokenizer(original);

            boolean isItThree = false;
            int index = 0;
            arr = new String[N];

            for(int i=0; i<N; i++){
                String key = st.nextToken();
                arr[index++] = key;

                if(!hashMap.containsKey(key)){
                    hashMap.put(key, 1);
                }
                else{
                    hashMap.put(key, hashMap.get(key)+1);
                }

                if(hashMap.get(key)>=3){
                    isItThree = true;
                    break;
                }

            }

            if(isItThree){
                sb.append(0+"\n");
                continue;
            }

            if(N>=17){
                sb.append(2+"\n");
                continue;
            }

            answer = Integer.MAX_VALUE;
            for(int i=0; i<index; i++){
                firstKey = arr[i];
                recursive(1, i+1, 0, index, firstKey);
            }

            sb.append(answer+"\n");
        }

        System.out.println(sb);

    }

    static void recursive(int depth, int idx, int min, int length, String before){

        if(depth>=3){
            answer = Math.min(answer, min + differ(firstKey, before));
            return;
        }

        for(int i=idx; i<length; i++){
            recursive(depth+1, i+1, min+differ(arr[i], before), length, arr[i]);
        }

    }

    static int differ(String left, String right){
        int count = 0;
        for(int i=0; i<4; i++){
            if(left.charAt(i) != right.charAt(i)) count++;
        }
        return count;
    }
}

/*
 *
 * 문제의 경우 T가 1<=T<=50이고 N의 값이 3<=N<=100000 이기 때문에 단순한 브루트포스로 풀면 반드시 시간초과가 발생한다.
 * 문제를 이해해보면 공통적으로 3번이상 MBTI가 같은 값이 나오면 0을 출력해준다.
 * N>=33이라면 반드시 3번 겹친 MBTI값이 존재한다는 뜻이므로 33이상의 값이 들어오면 0을 출력하게 해준다.
 * 17<= N < 33의 경우는 반드시 2번 이상 겹친 MBTI가 하나가 존재한다는 뜻이고 모든 경우를 따져보면 2가 나온다는 것을 알 수 있다.
 * 이제 고려해야하는 건 16이하의 경우인데 N=16 정도면 3개씩 때가며 직접 경우의 수를 만들어주면 시간이 얼마 걸리지 않기 때문에 충분히 브루트포스를 적용할 수 있다.
 *
 * */