import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        Stack<int[]> stack = new Stack<>();

        int H = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int answer = 0;
        int[] height = new int[W];
        height[0] = Integer.parseInt(st.nextToken());
        stack.push(new int[] {height[0], 0});

        for(int i=1; i<W; i++){

            height[i] = Integer.parseInt(st.nextToken());

            if(stack.peek()[0] <= height[i]){

                int[] before = stack.pop();

                for(int j=before[1]+1; j<i; j++){
                    answer += before[0] - height[j];
                }

                stack.push(new int[] {height[i], i});
            }

        }

        int[] val = stack.pop();
        stack.push(new int[] {height[W-1], W-1});

        for(int i=W-2; i>=val[1]; i--){
            if(stack.peek()[0] <= height[i]){

                int[] before = stack.pop();

                for(int j=before[1]-1; j>i; j--){
                    answer += before[0] - height[j];
                }
                stack.push(new int[] {height[i], i});
            }
        }

        System.out.println(answer);

    }

}

/*
 * 구현 문제
 *
 * 해당 문제의 포인트는 다음과 같습니다.
 * 1. 빗물이 쌓이려면 양쪽에 기둥(블록)이 필요하다. 만약 왼쪽에서 오른쪽으로 본다고 한다면 오른쪽 기둥은 왼쪽 기둥보다 크거나 같아야 하고, 반대의 경우 왼쪽 기둥이 오른쪽 보다 크거나 같아야 한다.
 * 2. 빗물은 양 기둥(블록) 중에서 높이가 작은 값을 기준으로 기둥 사이에 있는 기둥들의 크기를 뺀 값 만큼 고인다.
 *
 * 위의 포인트들을 기준으로 이렇게 생각해보겠습니다.
 * 왼쪽부터 오른쪽으로 진행한다고 해보겠습니다. 맨 처음부터 주어지는 블록의 높이를 기억하고 나와 같거나 큰 기둥이 나올 때 까지 순차적으로 오른쪽으로 탐색을 해봅니다.
 * 조건을 만족하는 오른쪽 기둥이 나온다면 나와 오른쪽 기둥을 제외하고 그 사이에 있는 블록들을 통해 얼마나 물이 고이는지 확인해야합니다.
 * 즉 (왼쪽 기둥 - 기둥 사이의 블록의 높이) 값이 물의 양입니다. 이를 모두 더해주면 물이 얼마나 고였는지 확인할 수 있습니다.
 *
 * 여기서 주의할 점은 왼쪽에서 오른쪽으로만 진행하게 되면 다음의 테스트 케이스에 막히게 됩니다.
 * 4 5
 * 2 4 1 1 2
 *
 * 이 경우 4를 기준으로 오른쪽을 탐색했을 때 4보다 크거나 같은 블록이 없어서 뒤에 물의 값 2를 도출할 수 없습니다.
 * 이를 해결하기 위해 왼쪽으로 탐색 중 마지막으로 가장 컸던 블록의 크기와 위치를 기억해줍니다. (여기선 높이 : 4, index : 1)
 * 다시 오른쪽부터 시작해서 왼쪽 탐색 중 가장 컸던 값의 위치까지만 탐색을 진행해줍니다.
 * 그러면 2가 4를 만났을 때 그 사이의 블록 크기의 차인 1이 2개 있으므로 물의 양 2를 구할 수 있습니다.
 *
 * 이러한 방법을 코드로 구현하면 O(2*W) = O(W)으로 문제를 해결할 수 있습니다.
 *
 *
 * */