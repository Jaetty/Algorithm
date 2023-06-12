package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_연구소_14502_2 {
    static int N, M, max;
    static int[][] map, virusMap;
    static boolean[][] visit;

    // 벽을 놓을 수 있는 위치, virus 위치를 미리 별도릐 자료구조에 담아서 활용
    static ArrayList<Node> zero = new ArrayList<>();
    static ArrayList<Node> virus = new ArrayList<>();
    static int zeroSize;

    static Node[] wall = new Node[3];

    static int[] dy = { -1, 1, 0, 0 };
    static int[] dx = { 0, 0, -1, 1 };

    static Queue<Node> queue = new ArrayDeque<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        max = Integer.MAX_VALUE;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int n = Integer.parseInt(st.nextToken());
                map[i][j] = n;
                if (n == 0)
                    zero.add(new Node(i, j));
                else if (n == 2)
                    virus.add(new Node(i, j));
            }
        }
        zeroSize = zero.size();

        comb(0, 0);
        System.out.println(max);
    }

    static void comb(int srcIdx, int tgtIdx) {
        // 기저조건 (벽이 3개 선택되면)
        if (tgtIdx == 3) {
            // complete code
            // virus 퍼지게
            virus();
            return;
        }

        // 2번 재귀 호출 사용 -> srcIdx에 대해서도 기저조건 처리
        if (srcIdx == zeroSize)
            return;

        // 선택가능한 0 위치를 순서대로 srcIdx를 선택
        wall[tgtIdx] = zero.get(srcIdx);

        comb(srcIdx + 1, tgtIdx + 1);
        comb(srcIdx + 1, tgtIdx);

    }

    static void virus() {
        // virusMap 만들고, visit만들기
        virusMap = new int[N][M];
        visit = new boolean[N][M];
        // virusMap <= map 복사

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                virusMap[i][j] = map[i][j];
            }
        }
        // virusMqp <= 벽표시
        for (int i = 0; i < 3; i++) {
            int y = wall[i].y;
            int x = wall[i].x;
            virusMap[y][x] = 1; // 벽
        }
        // bfs로 virus 확산
        // 초기 virus를 queue에 담음
        for (Node n : virus) {
            visit[n.y][n.x] = true;
            queue.offer(new Node(n.y, n.x));
        }
        while (!queue.isEmpty()) {
            Node n = queue.poll();

            for (int d = 0; d < 4; d++) {
                int ny = n.y + dy[d];
                int nx = n.x + dx[d];
                if (ny < 0 || nx < 0 || ny >= N || nx >= M || visit[ny][nx])
                    continue;

                if (virusMap[ny][nx] == 0) {
                    virusMap[ny][nx] = 2;
                    visit[ny][nx] = true;
                    queue.offer(new Node(ny, nx));
                }
            }
        }

        // 안전영역 계산 => max 값 갱신
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (virusMap[i][j] == 0)
                    sum++;
            }
        }
        max = Math.max(max, sum);
    }

    static class Node {
        int y;
        int x;

        Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

}