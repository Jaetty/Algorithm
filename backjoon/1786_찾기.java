import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static String T,P;
    static int[] failure;
    static List<Integer> answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        T = br.readLine();
        P = br.readLine();
        answer = new ArrayList<>();

        failureFunction();
        kmp();

        sb.append(answer.size()).append("\n");

        for(int val : answer){
            sb.append(val+" ");
        }

        System.out.print(sb.toString());

    }

    static void failureFunction(){

        int idx = 1;
        int correct = 0;
        failure = new int[P.length()];

        while(idx < P.length()){

            if(P.charAt(idx)==P.charAt(correct)){
                correct++;
                failure[idx++] = correct;
            }
            // 설정을 안해줬다가 틀렸던 부분
            else if(correct>0){
                correct = failure[correct-1];
            }
            else{
                correct = 0;
                idx++;
            }

        }

    }

    static void kmp(){

        int t_idx = 0;
        int p_idx = 0;

        while(true){

            if(p_idx==P.length()){
                answer.add(t_idx-p_idx+1);
                // 시간 초과 피하는 핵심
                p_idx = failure[p_idx-1];
            }

            if(t_idx>=T.length()){
                break;
            }

            if(T.charAt(t_idx)==P.charAt(p_idx)){
                p_idx++;
            }
            else{
                if(p_idx>0){
                    p_idx = failure[p_idx-1];
                    continue;
                }
            }

            t_idx++;

        }
    }
}

/*
 * 문자열 + kmp 문제
 *
 * 이 문제는 애초에 문제 자체에서 kmp의 원리에 대해 설명하고 있는 특이한 문제입니다.
 * 그러므로 원리를 이해하고 원리에 맞게 코드만 구현하면 해결이 가능합니다.
 * 그래서 이 문제는 해설 방법보다는 KMP 알고리즘 이해 문제와 같으므로 따로 설명을 생략합니다.
 *
 * @ 저는 KMP를 이 문제를 통해 배웠는데 반례에 막혀 틀렸습니다를 많이 받고 몇번의 시도 끝에 제 코드의 문제점을 발견했습니다.
 * @ 아무래도 이 문제는 저와 같은 시행착오를 통해 KMP 원리를 이해시키는 것이 목표인 문제같습니다.
 *
 * */