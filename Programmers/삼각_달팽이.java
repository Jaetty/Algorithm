import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */
class Solution {
    public int[] solution(int n) {

        int[][] visited = new int[n][n];

        int row = 0, col = 0, dir=0, idx=1;
        int[] dx = {1,0,-1}, dy={0,1,-1};


        while(row < n && col < n){

            if(visited[row][col] > 0){
                break;
            }

            visited[row][col] = idx++;

            int nr = row+dx[dir%3];
            int nc = col+dy[dir%3];

            if(nr >= n || nc >= n || visited[nr][nc] > 0){
                dir++;
            }

            row = row+dx[dir%3];
            col = col+dy[dir%3];

        }

        int[] answer = new int[idx-1];
        idx = 0;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){

                if(visited[i][j]==0){
                    break;
                }
                answer[idx++] = visited[i][j];

            }
        }

        return answer;
    }

}

/*
 * 삼각 달팽이
 *
 * 구현 문제
 *
 * 해당 문제의 경우 주어진 문제의 규칙성을 만족하며 진행하도록 짜주는게 포인트입니다.
 *
 * 예시 그림에서의 삼각형을 직각삼각형이라고 생각하고 탐색한다고 생각하고 문제를 접근해보면 규칙성이 보이는데
 *
 * 최초에 [0][0] = [r][c]라고 할 때,
 * 1. [r++][c]를 r<n까지 반복하거나, visited[r++][c]의 값이 없을 때 까지만 반복한다. 만약 조건을 만족하지 못하면 2번으로.
 * 2. [r][c++]를 c<n까지 반복하거나, visited[r][c++]의 값이 없을 때 까지 반복한다. 만약 조건을 만족하지 못하면 3번으로.
 * 3. [r--][c--]를 visited[r--][c--]의 값이 없을 때 까지 반복한다. 만약 조건을 만족하지 못하면 1번으로.
 * 4. 1,2,3번의 조건을 모두 불충족할 때, 탐색을 멈춘다.
 *
 * 그러면 이 규칙성을 바탕으로 코드를 구현하기만 하면 O(2N) 즉 O(N)으로 풀이가 가능하게 됩니다.
 *
 */