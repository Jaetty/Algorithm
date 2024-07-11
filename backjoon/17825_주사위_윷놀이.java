import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static List<List<Integer>> map;
    static int answer;
    static int[] dice;
    static int[][] pieces;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        dice = new int[10];

        for(int i=0; i<10; i++){
            dice[i] = Integer.parseInt(st.nextToken());
        }

        setMap();

        answer = 0;

        pieces = new int[4][2];
        backTracking(0, 0);

        System.out.println(answer);

    }

    static void setMap(){

        map = new ArrayList<>();

        for(int i=0; i<4; i++){
            map.add(new ArrayList<>());
        }

        for(int i=0; i<42; i+=2){
            map.get(0).add(i);
        }

        map.get(1).add(0);
        map.get(1).add(13);
        map.get(1).add(16);
        map.get(1).add(19);
        setBranchMap(1);

        map.get(2).add(0);
        map.get(2).add(22);
        map.get(2).add(24);
        setBranchMap(2);

        map.get(3).add(0);
        map.get(3).add(28);
        map.get(3).add(27);
        map.get(3).add(26);
        setBranchMap(3);
    }

    static void setBranchMap(int number){

        map.get(number).add(25);
        map.get(number).add(30);
        map.get(number).add(35);
        map.get(number).add(40);

    }

    static void changePath(int i, int c){

        pieces[i][0] = c;
        pieces[i][1] = 0;

    }

    static boolean check(int index, int next){

        boolean result = false;

        for(int i=0; i<4; i++){

            // 지금 내 말과 같은 번호이거나, 이미 도착한 말이라면 넘어간다.
            if(index==i || map.get(pieces[i][0]).size() <= pieces[i][1] ){
                continue;
            }

            int s1 = map.get(pieces[i][0]).get(pieces[i][1]);
            int s2 = map.get(pieces[index][0]).get(next);

            // 위치가 정확히 같다면
            if(pieces[i][0] == pieces[index][0] && pieces[i][1] == next){
                result = true;
            }
            //  어느 방향에서 출발하던 40을 만나게 되면
            else if(s1 == 40 && s2 == 40){
                result = true;
            }
            // 어느 방향이건 25를 만나게 되면
            else if(s1 == 25 && s2 == 25){
                result = true;
            }
            // 어느 방향이건 35를 만나게 되면
            else if(s1 == 35 && s2 == 35){
                result = true;
            }
            // 기존 루트에서 벗어난 말들이 30을 만나게 되면
            else if( pieces[i][0] > 0 && pieces[index][0] > 0 && s1 == 30 && s2 == 30){
                result = true;
            }

        }

        return result;
    }

    static void backTracking(int depth, int totalScore){

        answer = Math.max(answer, totalScore);

        if(depth>=10){
            return;
        }

        for(int i=0; i<4; i++){

            // 이미 도착한 말인지 확인
            if(pieces[i][1] >= map.get(pieces[i][0]).size()){
                continue;
            }

            // 현재 index 값 기억 및 다음 칸 index 값 구하기
            int before = pieces[i][1];
            int next = pieces[i][1] + dice[depth];

            // 다음 칸이 도착을 넘을 경우
            if (next >= map.get(pieces[i][0]).size()){
                pieces[i][1] = next;
                backTracking(depth+1, totalScore);
            }
            else{
                // 도착 칸 미만이라면

                // 다음 칸에 다른 말이 있다면 넘어간다.
                pieces[i][1] = next;
                int score = map.get(pieces[i][0]).get(next);

                if(pieces[i][0]==0 && next % 5 == 0 && next < 20){

                    switch (next){
                        case 5 :
                            changePath(i,1);
                            if(!check(i, 0)){
                                backTracking(depth+1, totalScore+score);
                            }
                            changePath(i, 0);
                            break;
                        case 10 :
                            changePath(i,2);
                            if(!check(i, 0)){
                                backTracking(depth+1, totalScore+score);
                            }
                            changePath(i, 0);
                            break;
                        case 15 :
                            changePath(i,3);
                            if(!check(i, 0)){
                                backTracking(depth+1, totalScore+score);
                            }
                            changePath(i, 0);
                            break;
                    }

                }else{
                    if(!check(i, next)){
                        backTracking(depth+1, totalScore+score);
                    }
                }

            }

            pieces[i][1] = before;

        }

    }

}

/*
 * 구현 + 백트래킹 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 말은 4개, 주사위는 10번 굴린다(턴이 10번 까지라는 뜻).
 * 2. 다른 말이 이미 있는 자리로는 이동 시킬 수 없다.
 * 3. 말이 움직여서 도착한 자리의 점수 만큼 점수를 얻는다 가장 점수를 많이 얻는 경우를 구해야한다.
 *
 * 포인트로 알 수 있는 점은 백트래킹 문제라는 것을 알 수 있습니다.
 * 말은 4번 주사위 10번이면 최대 O(4^10)이고 이는 백트래킹으로는 문제없는 반복입니다.
 * 또한, 2번 포인트는 가지치기를 제공하고 있기 때문에 실제 반복횟수는 이보다 낮을 것 입니다.
 *
 * 문제의 경우 크게 어려운 부분은 없을테지만 가장 주의 깊게 봐야할 부분이 2번 포인트입니다.
 * 여러가지 방법으로 2번을 구현할 수 있겠지만 저 같은 경우 도착한 곳의 점수와 현재 위치를 고려하여 비교했습니다.
 *
 * 예를 들어 윷놀이판에 22 24 30 40은 2번 이상 나오는 숫자입니다.
 * 그러므로 점수만으로 비교해서는 정확히 구분하지 못합니다.
 * 또한, 가운데에 있는 25, 30, 35, 40은 2개 이상의 경로에서 공통적으로 들리게 되는 점수입니다.
 *
 * 그러므로 제가 비교한 방법은 경로를 0,1,2,3으로 나눈 후 1번 루트이면서 현재 위치가 x인 곳에 먼저 온 말이 있는지 확인하는 방법과
 * 어떤 루트이건 상관없이 25,35,40에 이미 말이 있다면 무시하고 30의 경우는 가운데의 30인지도 구분해줬습니다.
 *
 * 위와 같이 체스가 겹치는 확인하는 부분에 주의를 기울여서 백트래킹으로 코드를 구현하면 풀이할 수 있는 문제입니다.
 *
 *
 * */