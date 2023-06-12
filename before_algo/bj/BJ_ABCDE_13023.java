package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 시간 초과
public class BJ_ABCDE_13023 {

    static int N, M;
    static boolean[][] friends; // matrix
    // visit -> bit masking으로 대체
    static boolean done; // 성공 여부
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        friends = new boolean[N][N];
        // 입력처리
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            friends[a][b] = friends[b][a] = true;
        }
        
        // dfs 탐색
        // 모든 정점에서 따져본다. 가능한 경우를 만나면 바로 종료
        for (int i = 0; i < N; i++) {
            dfs(i, 0, 1 << i);
        }
        if(done) System.out.println(1);
        else System.out.println(0);
    }

    static void dfs(int num, int cnt, int visit) {
        // 기저 조건
        if(done) return;
        
        if(cnt == 4) {
            done = true;
            return;
        }
        
        // 모든 정점을 대상으로
        for (int i = 0; i < N; i++) {
            if(i==num || (visit & (1 << i)) != 0) continue;
            if(friends[num][i]) dfs(i, cnt+1, visit | (1<<i));
        }
        
    }
}