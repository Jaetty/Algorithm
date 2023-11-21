import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int answer, testCase, N, M;
    static char[][] map;
    static boolean[][] visited;
    static boolean[] unlocked;
    static Map<Character, Queue<int[]>> stoppedStep;
    static Queue<int[]> queue;

    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        testCase = Integer.parseInt(br.readLine());

        for(int tc=0; tc<testCase; tc++){
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            answer = 0;
            queue = new ArrayDeque<>();

            map = new char[N][M];
            visited = new boolean[N][M];

            unlocked = new boolean[26];
            stoppedStep = new HashMap<>();

            for(int i=0; i<26; i++){
                stoppedStep.put( (char)('A'+i), new ArrayDeque<int[]>());
            }

            Queue<int[]> entrance = new ArrayDeque<>();

            for(int i=0; i<N; i++){
                String temp = br.readLine();
                for(int j=0; j<M; j++){
                    map[i][j] = temp.charAt(j);

                    if(i==0 || j==0 || j==M-1 || i==N-1 ){
                        if(map[i][j]!='*'){
                            if(map[i][j]=='$') answer++;
                            if('a'<=map[i][j] && map[i][j]<='z') unlocked[map[i][j]-'a'] = true;
                            entrance.add(new int[]{i,j});
                        }
                    }
                }
            }
            String temp = br.readLine();

            if(!temp.equals("0")){
                for(int i=0; i<temp.length(); i++){
                    unlocked[ temp.charAt(i)-'a'] = true;
                }
            }

            while(!entrance.isEmpty()){
                int[] rc = entrance.poll();
                visited[rc[0]][rc[1]] = true;
                if('A'<= map[rc[0]][rc[1]] && map[rc[0]][rc[1]]<='Z'){
                    if(unlocked[map[rc[0]][rc[1]]-'A']){
                        queue.add(new int[]{rc[0],rc[1]});
                    }
                    else stoppedStep.get(map[rc[0]][rc[1]]).add(new int[]{rc[0],rc[1]});
                }else{
                    queue.add(new int[]{rc[0],rc[1]});
                }

            }

            bfs();
            sb.append(answer).append("\n");
        }

        System.out.print(sb);

    }

    static void moveStep(char key){
        while(!stoppedStep.get(key).isEmpty()){
            queue.add(stoppedStep.get(key).poll());
        }
    }

    static void bfs(){

        while(!queue.isEmpty()){

            int[] rc = queue.poll();

            for(int i=0; i<4; i++){
                int nr = rc[0] + dx[i];
                int nc = rc[1] + dy[i];

                if(nr<0 || nc <0 || nr>=N || nc>=M || visited[nr][nc] || map[nr][nc]=='*') continue;

                visited[nr][nc] = true;

                if(map[nr][nc]=='$'){
                    answer++;
                    queue.add(new int[]{nr,nc});
                }
                else if('A'<= map[nr][nc] && map[nr][nc]<='Z'){
                    if(unlocked[map[nr][nc]-'A']){
                        queue.add(new int[]{nr,nc});
                    }
                    else stoppedStep.get(Character.toUpperCase(map[nr][nc])).add(new int[]{nr,nc});
                }
                else if('a'<=map[nr][nc] && map[nr][nc]<='z'){
                    unlocked[map[nr][nc]-'a'] = true;
                    moveStep(Character.toUpperCase(map[nr][nc]));
                    queue.add(new int[]{nr,nc});
                }
                else{
                    queue.add(new int[]{nr,nc});
                }

            }

        }

    }

}

/*
* 그래프 이론 및 구현 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 알파벳 대문자 잠겨있는 문을 뜻하고 문자배열에서 복수개로 나올 수 있습니다.
* 2. 알파벳 소문자는 열쇠를 뜻하고 해당 대문자를 열 수 있습니다. 중복해서 나올 수 있지만 단 하나만 있어도 해당하는 모든 대문자를 열 수 있습니다.
* 3. 상근이는 배열에서 밖과 접해있는 소문자, 문서, 빈공간, 열쇠를 가진 문을 통해 안으로 들어갈 수 있습니다.
*
* 기본적으로 BFS를 통해 풀이를 하며 포인트에 맞게 로직을 구현하면 됩니다.
* 1, 2번으로 생각해 낸 문을 여는 방법은 26 크기의 boolean 배열을 만들어서 열쇠를 주으면 해당 알파벳의 인덱스의 배열 값을 true로 표시하는 방법이었습니다.
* 그래서 BFS 도중 대문자에 해당하는 열쇠를 가지고 있다면 정상적으로 탐색을 수행합니다.
* 열쇠가 없는 경우엔 막혔던 문의 종류와 위치를 기억해두고 나중에 열쇠를 주으면 막혔던 위치를 다시 불러와 BFS의 큐안에 넣어줍니다. 이렇게해서 위치를 다시 찾을 필요가 없습니다.
*
* 3번 포인트는 구현 중 놓칠 수 있는 부분입니다. 밖에 인접한 빈공간('.')으로만 들어갈 수 있는 것이 아닌 소문자, 문서, 대문자를 통해서도 건물 안으로 들어갈 수 있음을 유의해야 합니다.
* 특히 밖과 인접한 값이 소문자라면 그때 열쇠를 주은 것이 되니 밖에 인접한 대문자의 문을 열 수 있게 만들어야함에 주의해야합니다.
* 예를 들어 다음과 같은 입력이 있다면
* 1
* 3 4
* ****
* a*$A
* ****
* 0
* 정답 : 1
* a를 통해 열쇠를 주었기 때문에 A를 통해서도 안으로 들어갈 수 있게 만들어줘야합니다.
*
* 위의 3가지 포인트에 유의하여 BFS 코드로 구현하면 문제를 해결할 수 있습니다.
*
* */