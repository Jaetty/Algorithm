import java.util.*;
import java.io.*;
public class Main {

    static class Player{
        int x,y,d,s, id;
        int gun, score;
        Player(int x, int y, int d, int s, int id){
            this.id = id;
            this.x = x;
            this.y = y;
            this.d = d;
            this.s = s;
            this.gun = 0;
            this.score = 0;
        }
    }

    static int N,M,K;

    static PriorityQueue<Integer>[][] map;

    static int[][] playerMap;

    static Player[] players;

    static int[] dr, dc;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        players = new Player[M];

        map = new PriorityQueue[N][N];
        playerMap = new int[N][N];

        dr = new int[]{-1,0,1,0};
        dc = new int[]{0,1,0,-1};

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = new PriorityQueue<Integer>( (e1, e2) -> e2-e1 );
                map[i][j].add(Integer.parseInt(st.nextToken()));
            }
        }

        for(int i=0; i<M; i++){
            st = new StringTokenizer(br.readLine());

            Player player = new Player(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1,
                    Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), i+1);
            players[i] = player;

            playerMap[player.x][player.y] = i+1;
        }

        while(K-->0){
            for(int i=0; i<M; i++){
                Player player = players[i];
                move(player);
            }
        }

        for(int i=0; i<M; i++){
            System.out.print(players[i].score+" ");
        }
    }

    static void move(Player player){
        int x = player.x;
        int y = player.y;
        int d = player.d;
        playerMap[x][y] = 0;

        int nx = x+dr[d];
        int ny = y+dc[d];

        if(nx >= N || ny >= N || nx < 0 || ny < 0 ){
            d = (player.d+2)%4;
            nx = x+dr[d];
            ny = y+dc[d];
        }

        player.x = nx;
        player.y = ny;
        player.d = d;

        // 다른 플레이어를 만났을 때
        if(playerMap[nx][ny] != 0){
            battle(player, players[playerMap[nx][ny]-1]);
        }
        // 만나지 않았다면
        else{
            if(!map[nx][ny].isEmpty() && map[nx][ny].peek() > player.gun){
                pickUpGun(player, nx, ny);
            }
            playerMap[nx][ny] = player.id;
        }


    }

    static void lose(Player looser){
        int x = looser.x;
        int y = looser.y;
        int d = looser.d;

        map[x][y].add(looser.gun);
        looser.gun = 0;

        int nx = x+dr[d];
        int ny = y+dc[d];

        while(true){
            if(nx >= N || ny >= N || nx < 0 || ny < 0 || playerMap[nx][ny]>0){
                d = (d+1)%4;
                nx = x+dr[d];
                ny = y+dc[d];
            }else{
                looser.x = nx;
                looser.y = ny;
                looser.d = d;
                break;
            }
        }

        if(!map[nx][ny].isEmpty() && map[nx][ny].peek() > looser.gun){
            pickUpGun(looser, nx, ny);
        }
        playerMap[nx][ny] = looser.id;

    }

    static void pickUpGun(Player player, int x, int y){
        int tmp = player.gun;
        player.gun = map[x][y].poll();
        if(tmp>0) map[x][y].add(tmp);
    }

    static void battle(Player player, Player opponent){

        int x = player.x;
        int y = player.y;

        if(player.s + player.gun > opponent.s + opponent.gun){
            playerMap[x][y] = player.id;
            player.score += (player.s + player.gun) - (opponent.s + opponent.gun);
            lose(opponent);
            if(!map[x][y].isEmpty() && map[x][y].peek()>player.gun) pickUpGun(player, x, y);
        }
        else if(player.s + player.gun < opponent.s + opponent.gun){
            playerMap[x][y] = opponent.id;
            opponent.score += (opponent.s + opponent.gun) - (player.s + player.gun);
            lose(player);
            if(!map[x][y].isEmpty() && map[x][y].peek()>opponent.gun) pickUpGun(opponent, x, y);
        }
        else{
            if(player.s > opponent.s){
                playerMap[x][y] = player.id;
                lose(opponent);
                if(!map[x][y].isEmpty() && map[x][y].peek()>player.gun) pickUpGun(player, x, y);
            }
            else{
                playerMap[x][y] = opponent.id;
                lose(player);

                if(!map[x][y].isEmpty() && map[x][y].peek()>opponent.gun) pickUpGun(opponent, x, y);
            }
        }

    }

}