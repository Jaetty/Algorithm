package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_토마토_7576 {
	
	static int M,N,max;
	static int[] dy = {-1, 1, 0, 0};
	static int[] dx = {-1, 1, 0, 0};
	
	static int[][] map;
	static Queue<Node> queue = new ArrayDeque<>();
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				int n = Integer.parseInt(st.nextToken());
				map[i][j] = n;
				
				if(n==1) queue.offer(new Node(i,j,0));
			}
		}
		
		// bfs
		while(!queue.isEmpty()) {
			Node n = queue.poll();
			int y = n.y;
			int x = n.x;
			
			max = Math.max(max, n.d);
			
			for(int d = 0; d<4; d++) {
				int ny = y + dy[d];
				int nx = x + dx[d];
				
				if(ny<0 || nx<0 || ny>=N || nx>=M) continue;
				if (map[ny][nx] == 0) {
					map[ny][nx] = 1;
					queue.offer(new Node(ny,nx, n.d+1));
				}
			}
			
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(map[i][j]==0) {
					System.out.println(-1);
					return;
				}
			}
		}
		
		System.out.println(max);
	}

	
	static class Node{
		int x,y,d;
		public Node(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}
	
}
