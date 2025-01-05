import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int L, failure[];
    static char[] input;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        L = Integer.parseInt(br.readLine());
        input = br.readLine().toCharArray();

        failureFunction();

        System.out.println(L - failure[L-1]);


    }

    static void failureFunction(){

        failure = new int[L];
        int idx = 0;

        for(int i=1; i<L; i++){

            while(idx>0 && input[i]!=input[idx]){
                idx = failure[idx-1];
            }

            if(input[idx] == input[i]){
                failure[i] = ++idx;
            }

        }

    }

}

/*
 * 1305 광고
 *
 * @ 해당 문제의 경우 뭔가 이렇게 하면 될 것 같아서 풀었더니 통과됐기 때문에 명확한 원리를 파악하지 못했습니다. 그래서 다른 사람들의 설명을 통해 원리를 이해했습니다.
 *
 * 문제의 핵심은 접두사와 접미사 찾기 였습니다.
 * 저 같은 경우, kmp 알고리즘을 백준의 '1786 찾기' 문제의 내용을 구현하며 배우다보니 실패 함수의 접두사 접미사의 개념을 파악하지 못했습니다.
 * 그래서 실패 함수는 단순히 가장 가까운 실패 구간의 인덱스를 기억하는 배열 정도로만 생각했던 것 이었습니다. 하지만 실패 함수의 핵심은 재활용 할 수 있는 접두사와 접미사 기록이었습니다.
 *
 * 예를 들어 ABAB 라는 문자열이 있다면 각각의 접두사(맨 마지막 문자를 제외한 부분 문자열)와 접미사(맨 앞 문자를 제외한 부분 문자열)가 될 수 있는 경우는
 * 접두사 : [A, AB, ABA]
 * 접미사 : [B", AB", BAB"]
 *
 * 실패 함수의 핵심은 현재까지 일치한 부분 문자열의 최장 접두사이면서 접미사인 부분의 길이를 기록하는 것 이었습니다.
 *
 * 문제의 조건에서 광고판의 길이는 L이고 문자열의 길이 N은  N<=L 임을 알 수 있습니다.
 * 또한, 길이가 N인 광고를 무한히 붙여서 광고한다는 조건이 있기 때문에, 만약 끝에 접미사 접두사와 같을 경우 이게 이어진다고 볼 수 있게 됩니다.
 *
 * 예를 들어 aaba가 원문이라고 하더라도 L이 만약 4 혹은 5라면 각각 'aaba', 'aabaa'가 됩니다.
 * 여기서 가장 줄일 수 있는 문자열의 크기는 'aab'로 3이 됩니다.
 *
 * 즉 마지막 문자까지가 접두사와 얼마나 겹치는지 전체 길이 L에서 빼주면 정답을 구할 수 있게 됩니다.
 *
 * */