import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[][][] order= {
            { {5,0,0}, {5,0,1}, {5,0,2}, {2,0,0}, {2,0,1}, {2,0,2}, {4,0,0}, {4,0,1}, {4,0,2}, {3,0,0}, {3,0,1}, {3,0,2} },
            { {5,2,0}, {5,2,1}, {5,2,2}, {3,2,0}, {3,2,1}, {3,2,2}, {4,2,0}, {4,2,1}, {4,2,2}, {2,2,0}, {2,2,1}, {2,2,2} },
            { {0,2,0}, {0,2,1}, {0,2,2}, {5,0,0}, {5,1,0}, {5,2,0}, {1,0,2}, {1,0,1}, {1,0,0}, {4,2,2}, {4,1,2}, {4,0,2} },
            { {0,0,2}, {0,0,1}, {0,0,0}, {4,0,0}, {4,1,0}, {4,2,0}, {1,2,0}, {1,2,1}, {1,2,2}, {5,2,2}, {5,1,2}, {5,0,2} },
            { {0,0,0}, {0,1,0}, {0,2,0}, {2,0,0}, {2,1,0}, {2,2,0}, {1,0,0}, {1,1,0}, {1,2,0}, {3,2,2}, {3,1,2}, {3,0,2} },
            { {0,0,2}, {0,1,2}, {0,2,2}, {3,2,0}, {3,1,0}, {3,0,0}, {1,0,2}, {1,1,2}, {1,2,2}, {2,0,2}, {2,1,2}, {2,2,2} }
    };

    static int N;
    static Map<Character, Integer> map;
    static char[][][] cube;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(br.readLine());
        map = new HashMap<>();
        map.put('U', 0);
        map.put('D', 1);
        map.put('F', 2);
        map.put('B', 3);
        map.put('L', 4);
        map.put('R', 5);
        map.put('+', 6);
        map.put('-', 7);
        StringTokenizer st;

        for(int i=0; i<N; i++){

            int limit = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());

            init();

            for(int j=0; j<limit; j++){
                String input = st.nextToken();
                int sector = map.get(input.charAt(0));
                int direction = map.get(input.charAt(1));

                rotateGlobal(sector, direction);
                rotateItself(sector, direction);

            }


            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    sb.append(cube[0][j][k]);
                }
                sb.append("\n");
            }


        }

        System.out.print(sb.toString());


    }

    static void rotateGlobal(int sector, int direction){

        char[][][] replica = copy();

        if(direction%2==0){

            for(int i=0; i<12; i+=3){
                for(int j=0; j<3; j++){
                    int[] curr = order[sector][i+j];
                    int[] next = order[sector][(i+j+3)%12];

                    replica[next[0]][next[1]][next[2]] = cube[curr[0]][curr[1]][curr[2]];
                }
            }

        }
        else{

            for(int i=11; i>=0; i-=3){
                for(int j=2; j>=0; j--){
                    int[] curr = order[sector][i-j];
                    int[] next = order[sector][(i-j-3) < 0 ? 11 - j : (i-j-3)];

                    replica[next[0]][next[1]][next[2]] = cube[curr[0]][curr[1]][curr[2]];
                }
            }
        }

        cube = replica;

    }

    static void rotateItself(int sector, int direction){

        char[][] replica = new char[3][3];

        if(direction%2==0){

            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    replica[j][2-i] = cube[sector][i][j];
                }
            }

            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    cube[sector][i][j] = replica[i][j];
                }
            }

        }
        else{
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    replica[2-j][i] = cube[sector][i][j];
                }
            }

            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    cube[sector][i][j] = replica[i][j];
                }
            }
        }

    }

    static void init(){

        char[] color = {'w','y','r','o','g','b'};

        cube = new char[6][3][3];

        for(int i=0; i<6; i++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    cube[i][j][k] = color[i];
                }
            }
        }
    }

    static char[][][] copy(){

        char[][][] clone = new char[6][3][3];

        for(int i=0; i<6; i++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    clone[i][j][k] = cube[i][j][k];
                }
            }
        }
        return clone;
    }

}
/*
 * 시뮬레이션 문제
 *
 * 해당 문제는 구현 문제로 포인트는 다음과 같습니다.
 * 1. 먼저 큐브의 기준이 되는 면을 정한다. (저 같은 경우는 F를 기준으로 삼았습니다.)
 * 2. 다른 면에 영향을 미치는 전체적인 회전을 수행해준다.
 * 3. 자기 자신에게만 영향을 미치는 회전을 수행해준다.
 *
 * 2번의 경우 어떠한 면을 회전하게 될 시 영향을 미치는 것은 상 하 좌 우에 접한 면들이 교환됩니다.
 * 3번의 경우는 배열 +-90도 회전만 수행해주면됩니다.
 *
 * 이 문제를 해결하는 방법은 여러가지 있을 것이라고 생각합니다.
 * 저 같은 경우는 맨 위의 order라는 배열을 이용하여 움직임을 미리 저장해두고 copy를 통해 cube를 깊은 복사한 배열을 만들고 회전한 결과를 다시 cube에 넣어주면서 해결해주었습니다.
 * 그러나 저는 이 풀이에 대해서 조금 아쉬운 부분이 메모리와 시간이 좀 더 최적화 시킬 수 있다고 생각한 점 입니다.
 * 저의 백준 기준 평균 속도는 450ms +- 20ms 만큼이고 평균 메모리는 60100 +- 100kb 입니다.
 * 이렇게 시간과 메모리가 소비되는 가장 큰 이유는 copy의 존재 때문일 것 입니다.
 *
 * 제가 대략 예상해봤을 때 copy 없이 변화하는 3가지만 기억해서 교환하는 방법을 사용했다면 아마 메모리도 적고, 속도도 더 빨라졌을 것으로 예상합니다.
 * 다만 이 경우 코드가 매우 매우 길어질 것으로 예상했습니다.... 그걸 잘 줄이는 방법을 고민하다가 생각한 방법이 지금의 order와 copy의 방법인데
 * 분명 최적화할 방법이 더 존재할 것이라고 생각해서 아쉬울 따름입니다.
 *
 * 아무튼, 저는 이 문제를 이렇게 해결했고, 여러가지 방법을 통해 더욱 최적화된 풀이가 존재할 것으로 예상합니다.
 *
 *
 * */