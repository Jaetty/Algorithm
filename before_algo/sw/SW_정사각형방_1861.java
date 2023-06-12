package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;
// BFS
public class SW_정사각형방_1861 {
	
	static int[][] map;
	static int T, N, NO, COUNT;
	static Queue<Node> queue = new ArrayDeque<>();
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		
		for( int t= 1; t<=T; t++) {
			// 초기화
			NO = 0;
			COUNT = 1; // 출발하는 방 번호도 count에 추가
			
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			// 입력 처리
			for(int i=0; i<N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			// bfs
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					// 모든 방에서 검색해 봐야 한다.
					queue.add(new Node(i,j,map[i][j],1));
					bfs();
				}
			}
			System.out.println("#"+t+" "+NO + " " + COUNT);
		}
		
	}
	
	//delta
	static int[] dy = {-1, 1, 0 ,0};
	static int[] dx = {0, 0, -1, 1};
	
	static void bfs() {
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			
			// 문제의 답 체크
			if(node.cnt > COUNT) {
				COUNT = node.cnt; // 최대 방문수 갱신
				NO = node.no; // 출발할 때 방 번호
			}else if(node.cnt == COUNT) {
				NO = node.no < NO ? node.no : NO;
			}
			
			for(int d = 0; d<4; d++) {
				int ny = node.y + dy[d];
				int nx = node.x + dx[d];
				
				if(ny < 0 || nx < 0 || ny >= N || nx >= N || map[ny][nx] != map [node.x][node.y]+1) continue;
				queue.offer(new Node(ny, nx, node.no, node.cnt + 1));
				break; // 조건에 맞는 다음 방 번호는 한개이므로 더 이상 delta를 따져볼 필요가 없다.
			}
			
		}
	}
	
	static class Node{
		int y;
		int x; // 시작 방 번호(계속 따라다녀야한다.)
		int no; // 방문 횟수( 방문할 때 마다 계속 증가)
		int cnt;
		
		public Node(int y, int x, int no, int cnt) {
			this.y = y;
			this.x = x;
			this.no = no;
			this.cnt = cnt;
		}
	}

}
