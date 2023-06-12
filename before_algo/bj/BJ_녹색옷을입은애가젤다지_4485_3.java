package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
// dijkstra
public class BJ_녹색옷을입은애가젤다지_4485_3 {
	
	static int N;
	static int[][] map;
	
	static int[] dy = {-1,1, 0, 0};
	static int[] dx = {0,0,-1,1};
	static PriorityQueue<Edge> pqueue = new PriorityQueue<>((e1,e2)->e1.c-e2.c);
	static int[][] cost;
	static final int INF = Integer.MAX_VALUE;
	
	static int callCnt; // ** 
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t=1;
		while(true) {
			N = Integer.parseInt(br.readLine());
			if(N==0) break;
			
			callCnt = 0; // **
			
			map = new int[N][N];
			cost = new int[N][N];
			
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					cost[i][j] = INF;
				}
			}
			
			dijkstra();
			
			System.out.println("Problem "+t+": "+cost[N-1][N-1]);
			System.out.println("Problem "+t+": "+callCnt);
			
			t++;
		}
	}
	
	static void dijkstra() {
		cost[0][0] = map[0][0];
		pqueue.offer(new Edge(0,0, map[0][0]));
		while(!pqueue.isEmpty()) {
			
			callCnt++;
			
			Edge e = pqueue.poll();
			
			for(int d = 0; d<4; d++) {
				int ny = e.y + dy[d];
				int nx = e.x + dx[d];
				if(ny <0 || nx <0 || nx >=N || ny >=N) continue;
				if(e.c + map[ny][nx] < cost[ny][nx]) {
					cost[ny][nx] = e.c + map[ny][nx];
					pqueue.offer(new Edge(ny,nx,cost[ny][nx]));
				}
			}
			
		}
	}
	
	static class Edge{
		int y,x,c;
		public Edge(int y, int x, int c) {
			this.y = y;
			this.x = x;
			this.c = c;
		}
	}
	
}
