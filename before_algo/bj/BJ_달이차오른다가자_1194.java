package bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Main {

static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
static StringBuilder sb = new StringBuilder();
static StringTokenizer st;

static Queue<Node> q = new ArrayDeque<>();
static int N, M;
static boolean[][][] visit;
static char[][] board;
static int[] dx = {1,-1,0,0};    
static int[] dy = {0,0,1,-1};

public static void main(String[] args) throws IOException {
    input();
    
}

static void input() throws IOException {
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    board = new char[N][M];
    visit = new boolean[N][M][1<<6];
    for(int i = 0; i < N; i++) {
        String str = br.readLine();
        for(int j = 0; j < M; j++) {
            char ch = str.charAt(j);
            if(ch == '0') {
                q.offer(new Node(i,j,0,0));
                visit[i][j][0] = true;
            }
            board[i][j] = ch;
        }
    }
    
    
    System.out.println(bfs());
    
}


static int bfs() {
    while(!q.isEmpty()) {
        Node node = q.poll();
        if(board[node.x][node.y] == '1') {
            return node.cnt;
        }
        
        for(int d = 0; d < 4; d++) {
            int nx = node.x + dx[d];
            int ny = node.y + dy[d];
            int key = node.key;
            
            
            if(0 <= nx && nx < N && 0<= ny && ny < M && board[nx][ny] != '#') {
                
                // 새로 가려는 좌표가 a, b... f 키 좌표이면 획득
                if('a' <= board[nx][ny] && board[nx][ny] <= 'f') {
                    key |= (1 << ( board[nx][ny] - 'a' ));
                }
                
                if('A' <= board[nx][ny] && board[nx][ny] <= 'F') {
                    if((key & (1 << (board[nx][ny]-'A') ) )== 0 ) continue;
                    
                }
                
                if(visit[nx][ny][key]) continue;
                visit[nx][ny][key] = true;
                q.offer(new Node(nx, ny, key, node.cnt+1));
            }
        }
        
    }
    
    
    return -1;
}
static class Node{
    int x, y, key, cnt;
    Node(int x, int y, int key, int cnt){
        this.x = x;
        this.y = y;
        this.key = key;
        this.cnt = cnt;
    }
    
    
}
}