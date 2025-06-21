import java.util.*;
import java.io.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int[] opposite = {5, 4, 3, 2, 1, 0};
    static int[] dice;
    static int N;
    static long answer;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        dice = new int[6];
        answer = Long.MAX_VALUE;

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < 6; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }

        if(N==1){

            for(int i=0; i<6; i++){
                long temp = dice[i];
                for (int j = 0; j< 6; j++) {
                    if(i == j || opposite[i] == j){
                        continue;
                    }
                    temp += dice[j];
                }
                answer = Math.min(temp, answer);
            }

        }
        else{
            for (int i = 0; i < 6; i++) {
                setMin(i);
            }
        }

        System.out.println(answer);

    }

    static void setMin(int start){

        // 각 옆면의 경우를 구함. {옆면의 값, 옆면의 위치}
        int[][] side = new int[4][2];
        int idx = 0;


        // 뒷면을 제외한 옆면들을 모음.
        for(int i = 0; i < 6; i++){

            if(start == i || opposite[start] == i){
                continue;
            }
            side[idx][0] = dice[i];
            side[idx++][1] = i;
        }

        // 묶음을 만들었는지 확인하는 변수
        boolean[] visited = new boolean[6];

        // 옆면들 중, 서로 반대편이 아닌 면끼리 모아야함.
        List<int[]> bundle = new ArrayList<>();

        // 서로 양옆으로 이어진 값들로 묶음을 만들어준다.
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){

                // 이미 서로 묶은 값이 아니면서, 나와 다른 값이 내 반대편이 아니라면
                if(i != j && !visited[side[j][1]] && opposite[side[i][1]] != side[j][1]){
                    bundle.add(new int[] {Math.min(side[j][0], side[i][0]), Math.max(side[j][0], side[i][0])});
                }
            }
            visited[side[i][1]] = true;
        }

        for(int[] bun : bundle){

            long result = Long.MAX_VALUE;

            // ((4방면 * N줄 * 윗면) - (4개의 꼭짓점 * 윗면) + (4개의 꼭짓점 * 옆면 묶음 중 최솟값)) * (N 높이 - 1)
            result = ((((4L * N) - 4) * dice[start]) + (4L * bun[0])) * (long) (N-1);

            // 가장 윗면은 N*N 크기의 최솟값이 됨
            result += (long) dice[start] * N * N;

            // 가장 윗면의 밑의 줄은 옆면 묶음 중 최솟값으로 N-4개 있고, 4개는 옆면 묶음 중 큰 값
            result += ((((4L * N) - 4) * bun[0]) + (4L * bun[1]));

            answer = Math.min(result, answer);

        }

    }

}

/*

1041 주사위

수학 + 구현 문제

해당 문제는 정사각형의 큐브가 탁자 위에 놓여있을때, 바닥 면을 제외하고 우리가 볼 수 있는 다섯 면의 합이 최소인 경우를 묻고 있습니다.

입력이 다음과 같다고 해보겠습니다.
3
1 2 3 4 5 6

그러면 주사위로 만든 큐브는 3*3*3의 크기를 갖게 됩니다.
그렇다면 피라미드처럼 아래서부터 차곡차곡 쌓는다고 생각하고 우선 가장 맨 마지막, 바닥과 접하는 부분을 생각해보겠습니다.
우선 그리디하게 모든 노출되는 면을 최솟값인 1로 만든다고 해봅시다. 그런데, 각 꼭지점 부분은 주사위가 겹치는 구간입니다.
반대로 생각하면, 각 꼭지점을 제외한 부분들은 딱 최솟값 1이 적힌 면만 노출시킬 수 있다는 것이 됩니다.
왜냐하면 아래 탁자 바닥으로 가려져있고, 양옆은 다른 주사위로 가려져있고, 윗면에는 한칸 더 쌓아올릴 예정이니 가려지기 때문입니다.

그렇다면, 각 꼭지점의 4개의 주사위의 경우 1과 나머지 다른 면이 노출되야 하기 때문에, 노출할 수 있는 면 중 최솟값을 선택하면 됩니다.
그리고 조금만 생각해보면 이게 마지막 맨윗칸을 제외하곤 N-1줄까지 똑같은 방식으로 쌓아올릴 수 있다는 점을 알 수 있습니다.
결국, 다음과 같은 공식을 세울 수 있습니다.
answer += ((4방면 * N * 최솟값) - (4개의 꼭짓점 * 최솟값) + (4개의 꼭짓점 * 옆면 묶음 중 최솟값)) * (N 높이 - 1)

그 다음, 가장 윗면의 경우는 당연히 N*N 만큼 숫자가 필요합니다. 그렇기에 다음과 같은 공식을 쉽게 떠올릴 수 있습니다.
answer += N * N * 윗면 숫자.
당연하지만 이 값이 가장 클테니까 이 값은 최솟값이여야 합니다. 즉 윗면 = 최솟값을 뜻합니다.

그리고 맨 윗줄의 윗면을 최솟값으로 하게 된다면 맨 윗줄의 옆면은 윗면과 옆으로 접하는 면중에서 최솟값을 선택하면 됩니다.
answer += ((4방면 * N * 옆면 묶음 중 최솟값) - (4개의 꼭짓점 * 옆면 묶음 중 최솟값) + (4개의 꼭짓점 * 옆면 묶음 중 최댓값))

즉 이와 같은 공식을 세운 후, 주사위 윗면을 바꿔갔을 때, 가장 작았던 합을 출력하면 문제를 해결할 수 있습니다.

주의점 )
1. N = 1일 때의 경우는 따로 처리해주면 됩니다.
2. 윗면과 접하는 옆면들은 서로 반대쪽 면에 있어서 양 옆에 존재 못하는 경우가 있으니 서로 옆면에 존재할 수 있는 애들끼리 묶어줘야합니다.



*/