package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_구간합구하기5_11660_2 {
	
	static int N, M;
    static int[][] arr;
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N+1][N+1];
        for(int i=1; i<=N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken())
                        + arr[i-1][j] + arr[i][j-1] - arr[i-1][j-1];
            }
        }
        
        StringBuilder result = new StringBuilder();
        for(int m=0; m<M; m++) {
            st = new StringTokenizer(br.readLine());
            int x1, y1, x2, y2;
            y1 = Integer.parseInt(st.nextToken());
            x1 = Integer.parseInt(st.nextToken());
            y2 = Integer.parseInt(st.nextToken());
            x2 = Integer.parseInt(st.nextToken());
            result.append(arr[y2][x2] - arr[y1-1][x2] - arr[y2][x1-1] + arr[y1-1][x1-1]).append('\n');
        }
        System.out.println(result);
    
	}
}
