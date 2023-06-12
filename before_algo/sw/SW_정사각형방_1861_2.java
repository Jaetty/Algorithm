package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

import sw.SW_정사각형방_1861.Node;

// DFS
public class SW_정사각형방_1861_2 {

	static int[][] map;
	static int T, N, NO, COUNT;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());

		for (int t = 1; t <= T; t++) {
			// 초기화
			NO = 0;
			COUNT = 1; // 출발하는 방 번호도 count에 추가

			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			// 입력 처리
			for (int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			// dfs
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					// 모든 방에서 검색해 봐야 한다.
					dfs(i, j, map[i][j], 1);
				}
			}
			System.out.println("#" + t + " " + NO + " " + COUNT);
		}

	}

	// delta
	static int[] dy = { -1, 1, 0, 0 };
	static int[] dx = { 0, 0, -1, 1 };

	static void dfs(int y, int x, int no, int cnt) {
		if (cnt > COUNT) {
			COUNT = cnt; // 최대 방문수 갱신
			NO = no; // 출발할 때 방 번호
		} else if (cnt == COUNT) {
			NO = no < NO ? no : NO;
		}

		for (int d = 0; d < 4; d++) {
			int ny = y + dy[d];
			int nx = x + dx[d];

			if (ny < 0 || nx < 0 || ny >= N || nx >= N || map[ny][nx] != map[x][y] + 1) continue;
			dfs(ny, nx, no, cnt +1);
			break; // 조건에 맞는 다음 방 번호는 한개이므로 더 이상 delta를 따져볼 필요가 없다.
		}
	}
}
