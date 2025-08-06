import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, size;
    static int[] dp;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        dp = new int[40000];
        N = Integer.parseInt(br.readLine());
        size = 0;

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i<N; i++){
            int val = Integer.parseInt(st.nextToken());
            int idx = binarySearch(val);

            if(idx == size){
                size++;
                dp[idx] = val;
            }
            else{
                dp[idx] = Math.min(val, dp[idx]);
            }
        }

        System.out.print(size);

    }

    static int binarySearch(int target){

        int left = 0, right = size, mid = 0;

        while(left < right){

            mid = (left+right)/2;

            if(dp[mid] >= target){
                right = mid;
            }
            else{
                left = mid +1;
            }

        }

        return left;
    }
}
/*

2352 반도체 설계

이분 탐색 + 가장 긴 증가하는 부분수열(LIS) 문제

해당 문제의 풀이 이분 탐색을 이용한 LIS 풀이 알고리즘을 안다면 쉽게 풀이할 수 있습니다.

반도체의 포트가 연결될 때 서로 교차하지 않는 선의 개수를 세려면
A 반도체의 x번 포트에서 B 반도체의 y번 포트로 연결될 때,
A 반도체의 x 초과의 포트가 B 반도체의 y번 초과의 포트와 연결되면 겹치는 일이 없습니다.

예를 들어서 A 반도체의 1번 포트가 B 반도체의 2번 포트와 연결되었다면
A번 반도체의 2 이상의 포트가 B 반도체의 3 이상의 포트와 연결만 된다면 교차하는 일이 없습니다.

그래서 A 반도체의 i번 포트가 B 반도체에 연결되는 포트 번호 y가 순서대로 주어지기 때문에,
결국, 가장 긴 증가하는 수열을 구하면 교차하지 않는 선의 최대 개수를 구할 수 있게 됩니다.

이분 탐색을 이용한 LIS는 N log N의 시간에 LIS를 구할 수 있기 때문에 해당 문제를 풀이할 때 가장 적절한 알고리즘입니다.

*/