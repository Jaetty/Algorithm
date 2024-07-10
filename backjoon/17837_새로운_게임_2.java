import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Piece{

        int number, row, col, direction;
        Piece up;
        Piece down;

        Piece(int number, int row, int col, int direction){
            this.number = number;
            this.row = row;
            this.col = col;
            this.direction = direction;
            this.up = null;
            this.down = null;
        }
    }
    static int[] dx = {0,-1,0,1};
    static int[] dy = {1,0,-1,0};
    static boolean[][][] stay;
    static int[][] map;

    static Piece pieces[];

    static int N, K, turn;
    static boolean flag;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        stay = new boolean[N][N][K];
        pieces = new Piece[K];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i=0; i<K; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken())-1;
            int c = Integer.parseInt(st.nextToken())-1;
            int d = Integer.parseInt(st.nextToken());

            switch (d){
                case 1 : d = 0;
                break;
                case 2 : d = 2;
                break;
                case 3 : d = 1;
                break;
                case 4 : d = 3;
                break;
            }

            stay[r][c][i] = true;

            pieces[i] = new Piece(i, r, c, d);
        }



        while(!flag && turn++ < 1000){

            for(int i=0; i<K; i++){
                move(pieces[i]);
                if(flag) break;
            }

        }

        if(turn>=1000) System.out.println(-1);
        else System.out.println(turn);


    }

    static void move(Piece piece){

        int r = piece.row;
        int c = piece.col;

        int nr = r + dx[piece.direction];
        int nc = c + dy[piece.direction];

        boolean blueCheck = false;

        // 움직임을 먼저 전처리 (밖으로 나가고도 또 파란색 칸을 만날 수 있으므로)
        if(nr < 0 || nc < 0 || nr >= N || nc >= N || map[nr][nc] == 2){
            blueSequence(piece);
            nr = r + dx[piece.direction];
            nc = c + dy[piece.direction];
            blueCheck = true;
        }

        // 방향도 바꿨는데 밖으로 나가거나 파란색 발판이면 그냥 스킵한다.
        if(blueCheck && nr < 0 || nc < 0 || nr >= N || nc >= N || map[nr][nc] == 2){
            return;
        }

        // 움직이는 것이 가능하다면 현재 위치에 있는 말을 모두 다 움직여준다.
        List<Integer> list = new ArrayList<>();
        Piece temp = piece;

        while(temp != null){
            list.add(temp.number);
            temp = temp.up;
        }

        for(int val : list){
            pieces[val].row = nr;
            pieces[val].col = nc;
            stay[r][c][val] = false;
        }

        // 만약 내 아래에 체스 말이 있었다면 아래에 체스말과 연결을 끊어준다.
        if(piece.down != null){
            piece.down.up = null;
        }
        piece.down = null;


        // 빨간 칸 만났을 때 위 아래를 바꾸고 가장 아래의 체스말을 기준으로 바꾼다.
        if(map[nr][nc]==1){
            redSequence(piece);
            while(piece.down !=null) piece = piece.down;
        }

        // 얹히기 작업
        board(nr, nc, piece);

        // 새로 들어온 말들을 기억해준다.
        for(int val : list){
            stay[nr][nc][val] = true;
        }

        // 체스 말들이 4개 이상인지 확인한다.
        int count = 0;

        for(int i=0; i<K; i++){
            if(stay[nr][nc][i]){
                count++;
            }
        }

        if(count>=4) flag = true;

    }

    // 이동하는 모든 체스 말들의 위 아래의 위치를 바꿔준다.
    static void redSequence(Piece piece){

        Piece change = piece;

        while(change != null){

            Piece temp = change.up;
            change.up = change.down;
            change.down = temp;
            change = temp;

        }

    }

    static void blueSequence(Piece piece){
        piece.direction = (piece.direction +2) % 4;
    }

    static void board(int x, int y, Piece piece){

        List<Piece> list = new ArrayList<>();
        for(int i=0; i<K; i++){
            if(stay[x][y][i]){
                list.add(pieces[i]);
            }
        }

        Piece top = null;

        for(int i=0; i<list.size(); i++){

            if(list.get(i).up ==null){
                top = list.get(i);
                break;
            }
        }

        piece.down = top;
        if(top != null) top.up = piece;

    }

}

/*
 * 구현 + 시뮬레이션 문제
 *
 * 해당 문제는 다음과 같은 순서로 작동하게 만들어주면 됩니다.
 * 1. 1000번 미만으로 반복하며, 말이 4개 이상 쌓이는 순간 종료된다.
 * 2. 말을 움직여준다. 각각 상 하 좌 우로 움직임이 정해져있다.
 * 3. 칸은 흰색, 빨간색, 파란색이 있는데 하얀색은 무시하고 빨간색 파란색은 각각 다음과 같이 작동한다.
 *  빨간색 : 현재 이동 중인 체스 말들의 순서를 바꿔준다.
 *  파란색(배열 바깥은 파란색 칸과 같다) : 지금 움직이는 체스의 이동 방향을 반대로 바꾼다. 만약 바꾸고 움직인 칸도 파란색 칸이면 그대로 있는다.
 * 4. 현재 이동한 말을 기존에 이동한 위치에 있던 체스말 맨 위에 얹진다.
 *
 * 여기서 포인트는 현재 말들이 어디 위치에 있고, 그리고 말을 다른 말 위에 어떻게 얹히느냐 입니다.
 * 이 부분을 해결하는 여러가지 방법들이 있겠지만 저는 연결 리스트를 사용하였습니다.
 * 말을 각각 위, 아래로 연결할 수 있게 만들면 빨간 색을 만났을 때 위 아래를 교환해주기만 하면 순서를 거꾸로 하기 편하다고 생각했기 때문입니다.
 *
 * 연결리스트를 사용할 때 고려해야하는 점은 아래와 같습니다.
 * 1. 말을 다른 말 위에 얹는 과정에서 움직이는 말이 아닌 가장 아래에 있는 말을 선택하여 연결해야하는 점. (왜냐하면 빨간색을 만나게 되면 원래 움직이던 말이 가장 위의 말이 되기 때문에)
 * 2. 중간 위치에 있던 말이 출발하면 아래에 말과 연결을 끊어줘야 한다는 점
 * 3. 움직여서 도착한 칸에 기존에 있던 말 중에서 가장 맨 위의 말을 찾아야하는 점
 *
 * 연결리스트를 이용한다면 위의 부분들에 유의하면서 구현하면 문제를 풀이할 수 있습니다.
 *
 *
 * */