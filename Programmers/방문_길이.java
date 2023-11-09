import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {
    static int[][] map;
    static Set<String> path;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};
    static int[] rc;

    public int solution(String dirs) {

        int val = 1;
        path = new HashSet<>();
        map = new int[11][11];

        for(int i=0; i<11; i++){
            for(int j=0; j<11; j++){
                map[i][j] = val++;
            }
        }

        rc = new int[2];
        rc[0] = 5;
        rc[1] = 5;

        for(int i=0; i<dirs.length(); i++){
            switch(dirs.charAt(i)){
                case 'U' :
                    if(check(0)) move(0);
                    break;
                case 'D' :
                    if(check(1)) move(1);
                    break;
                case 'L' :
                    if(check(2)) move(2);
                    break;
                case 'R' :
                    if(check(3)) move(3);
                    break;
            }
        }

        return path.size()/2;
    }

    static boolean check(int val){
        int nr = rc[0] + dx[val];
        int nc = rc[1] + dy[val];
        if(nr < 0 || nc < 0 || nr >=11 || nc>=11) return false;
        return true;
    }
    static void move(int val){
        int before = map[rc[0]][rc[1]];
        rc[0] += dx[val];
        rc[1] += dy[val];
        String A = before+""+map[rc[0]][rc[1]];
        String B = map[rc[0]][rc[1]]+""+before;
        path.add(A);
        path.add(B);
    }
}

/*
* 그래프 탐색 문제
*
* 해당 문제는 경로를 기억하는 것이 문제의 가장 큰 포인트입니다.
* 이 문제를 해결하기 위해 세운 로직은 다음과 같습니다.
* 1. 11*11의 배열을 만들고 각자 배열의 안에 값은 1~121까지 값을 넣습니다.
* 2. 문제에서 경로는 A라는 값에서 B라는 값으로 갔다면 B에서 A를 가는 것도 경로칩니다. 그렇다면 AtoB 경로와 BtoA 경로를 set에 넣어서 기억해주면 됩니다.
* 3. 그렇게 하면 정답은 set/2의 값이 즉 정답이 됩니다.
*
* 단순한 예를 들자면
* 0,0 이라는 위치의 값이 1이라고 가정하겠습니다. 여기서 0,1의 값이 2이고 여기로 이동한다면 즉 1번 노드와 2번 노드에 양방향 간선이 생기는 것입니다.
* 그러므로 set의 값에 1->2, 2->1이라는 결과를 넣어줍니다. 이를 set에 저장하는 이유는 혹시 돌고돌아 다시 1->2로 이동하는 경우가 발생하더라도
* 이미 set에 들어간 값이기 때문에 set에 add해주는 것만으로 중복을 피할 수 있기 때문입니다.
* 결국 문제는 이렇게 만들어진 무방향 간선이 몇 개인지 물어보고 있기 때문에 set/2의 값이 정답이 되는 것 입니다.
*
* */