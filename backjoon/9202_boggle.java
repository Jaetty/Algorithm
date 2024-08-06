import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Trie {

        Trie root;
        Map<Character, Trie> child;
        boolean end;

        Trie(Trie trie){
            this.root = trie;
            this.child = new HashMap<>();
        }

    }
    static int[] dx = {-1,-1,-1,0,0,0,1,1,1};
    static int[] dy = {-1,0,1,-1,0,1,-1,0,1};
    static Trie root;
    static char[][] map;
    static int score;
    static Set<String> found;
    static PriorityQueue<String> pqueue;


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int W = Integer.parseInt(br.readLine());
        root = new Trie(null);

        for(int w=0; w<W; w++){

            String str = br.readLine();

            Trie trie = root;

            for(int i=0; i<str.length(); i++){
                Trie nTrie;
                if(trie.child.containsKey(str.charAt(i))){
                    nTrie = trie.child.get(str.charAt(i));
                }
                else{
                    nTrie = new Trie(trie);
                    trie.child.put(str.charAt(i), nTrie);
                }
                trie = nTrie;
            }
            trie.end = true;
        }

        br.readLine();
        int T = Integer.parseInt(br.readLine());

        for(int tc=0; tc<T; tc++){

            map = new char[4][4];
            pqueue = new PriorityQueue<>((e1,e2)-> {
                if(e2.length()==e1.length()){
                    return e1.compareTo(e2);
                }
                return e2.length() -e1.length();
            });
            found = new HashSet<>();
            score = 0;

            for(int i=0; i<4; i++){
                map[i] = br.readLine().toCharArray();
            }

            for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    if(root.child.containsKey(map[i][j])){
                        Trie nTrie = root.child.get(map[i][j]);
                        if(nTrie.end && !found.contains(map[i][j]+"")){
                            found.add(map[i][j]+"");
                            pqueue.add(map[i][j]+"");
                        }
                        bfs(i,j, nTrie);
                    }
                }
            }

            sb.append(score + " " + pqueue.poll() + " " + found.size()+"\n");

            if(tc==T-1) break;
            else br.readLine();

        }


        System.out.print(sb.toString());

    }

    static class Node{

        int r, c, count;
        boolean[] visited;
        Trie trie;
        StringBuilder word;

        Node(int r, int c, int count, Trie trie, StringBuilder word, boolean[] visited){
            this.r = r;
            this.c = c;
            this.count = count+1;

            this.visited = visited.clone();
            this.trie = trie;
            this.word = new StringBuilder(word.toString());
            this.word.append(map[r][c]);
            this.visited[ r * 4 + c] = true;
        }

    }

    static void bfs(int r, int c, Trie trie){

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(r,c, 0, trie, new StringBuilder(),new boolean[16]));

        while(!queue.isEmpty()){

            Node node = queue.poll();

            for(int i=0; i<9; i++){

                int nr = node.r + dx[i];
                int nc = node.c + dy[i];

                if(nr < 0 || nc < 0 || nr >= 4 || nc >= 4 || node.visited[nr*4+nc] || !node.trie.child.containsKey(map[nr][nc])) continue;

                Node next = new Node(nr, nc, node.count, node.trie.child.get(map[nr][nc]), node.word,node.visited);

                if(next.trie.end && !found.contains(next.word.toString())){
                    setScore(next);
                }
                queue.add(next);


            }

        }


    }

    static void setScore(Node node){

        switch (node.count){
            case 3 :
                score += 1;
                break;
            case 4 :
                score += 1;
                break;
            case 5 :
                score += 2;
                break;
            case 6 :
                score += 3;
                break;
            case 7 :
                score += 5;
                break;
            case 8 :
                score += 11;
                break;
            default: break;
        }

        pqueue.add(node.word.toString());
        found.add(node.word.toString());

    }

}

/*
 * 트라이 + 브루트포스 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 시간 제한은 10초이다.
 * 2. 글자의 갯수는 w <300_000, 최대 문자의 길이는 8이며 알파벳 대문자로만 이루어져있다.
 * 3. boggle 게임 보드의 사용 횟수는 b<30, 보드당 크기는 4*4이다.
 *
 * 먼저 3번 포인트로 생각해보면 4*4 보드에서 만들어질 수 있는 문자를 모두 찾는 방법을 사용하면 이는 매우 많은 시간을 소비하게 됩니다.
 * 3번 포인트는 결국 시간을 꽤나 소비하게 만듭니다. 1번 포인트로 10초의 시간을 받았지만 단순히 모든 경우를 따지게 된다면 10초는 가볍게 넘겨버릴 것 입니다.
 * 그러므로 떠올릴 수 있는 가지치기 방법은 만약 다음 방향에 있는 문자를 포함했을 때 이 단어가 단어 사전에 있는지 확인하여 진행하는 방법입니다.
 * 그러면 set, 배열, map과 같은 뭐 여러가지 방법으로 단어 사전의 문자열을 저장해서 조회하는 해결법을 떠올릴 수 있을 것 입니다.
 * 여기서 2번 포인트를 생각해보면 최대 문자의 길이가 8, 대문자로만 이루어져있음을 통해 알 수 있는 것은 트라이를 구성하기 좋은 환경이라는 점입니다.
 * 트라이는 메모리를 많이 차지하게 되지만 길이가 짧고 문자의 종류가 효율적으로 문자열을 관리할 수 있습니다.
 * 또한, 문자 단위로 트라이를 구성하면 3번 포인트의 가지치기가 훨씬 수월해집니다.
 *
 * 그러므로 핵심은
 * 1. 트라이를 통해 단어 사전을 구성합니다.
 * 2. 그래프 탐색(bfs 혹은 dfs)으로 트라이에서 조회가 가능한 문자에 맞게 탐색을 진행합니다.
 * 3. 단어가 완성되면 이에 맞춰 점수, 최대로 긴 문자, 찾은 문자를 기록해줍니다.
 * 4. 2~3 구간을 입력된 b의 값 만큼 반복한 후 결과를 출력해줍니다.
 *
 * 이렇게 로직을 구성한 후 이를 코드로 나타내면 문제를 풀이할 수 있습니다.
 *
 * */