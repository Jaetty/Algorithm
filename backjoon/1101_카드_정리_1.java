import java.util.*;
import java.io.*;
/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static class Box{
        List<Integer> color;

        Box(){
            this.color = new ArrayList<>(50);
        }

    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Box[] boxes = new Box[N];

        for(int i=0; i<N; i++){

            st = new StringTokenizer(br.readLine());
            boxes[i] = new Box();

            for(int j=0; j<M; j++){
                if(Integer.parseInt(st.nextToken()) > 0){
                    boxes[i].color.add(j);
                }
            }
        }

        int answer = Integer.MAX_VALUE;

        for(int joker=0; joker<N; joker++){

            int change = 0;
            boolean[] visited = new boolean[M];

            for(int i=0; i<N; i++){

                if(joker==i) continue;

                if(boxes[i].color.size()<1){
                    continue;
                }
                else if(boxes[i].color.size()==1 && !visited[boxes[i].color.get(0)] ){
                    visited[boxes[i].color.get(0)] = true;
                    continue;
                }
                else{
                    change++;
                }

            }
            answer = Math.min(answer, change);
        }

        System.out.println(answer);

    }

}

/*
 * 1101 카드 정리1
 *
 * 브루트포스 + 그리디 문제
 *
 * 해당 문제의 조건을 보면, 어떤 a 박스에서 b박스로 카드의 종류, 갯수에 상관 없이 단 1번의 움직임으로 옮길 수 있습니다.
 *
 * 그렇다면, 그리디하게 생각해보면?
 * 만약 조커 박스가 아닌 어떤 박스에 여러 색상의 카드가 존재한다면? 그 박스 내용물을 전부 조커 박스에 넣어주면 단 1번의 비용으로 옮길 수 있습니다.
 *
 * 그럼 비용을 아끼는 방법은 결국, 옮기지 말아야하는 경우를 정의하는 것입니다.
 * 옮기지 않는 경우는 다음과 같습니다.
 * 1. 주어진 박스가 원래 비어있을 때.
 * 2. 주어진 박스에 하나의 색만 가진 카드만 있을 때
 *
 * 여기서 2번의 경우 조건이 붙는데, 조커를 제외하고 모든 같은 색상의 카드들은 하나의 박스에 들어있어야한다고 했습니다.
 * 그렇다면 2번의 경우를 만났을 때, 특정 색깔의 카드로만 구성되어있는 박스가 전에 있었는지 기억해두면 됩니다.
 *
 * 예를 들어 조커가 없다고 가정하고 다음과 같은 입력이 있다면?
 * 4 2
 * 1 0 <= 1번 박스에 R 색상이 하나
 * 1 0 <= 2번 박스에 R 색상이 하나
 * 0 1 <= 3번 박스에 B 색상이 하나
 * 0 0 <= 4번 박스는 비었음.
 *
 * 여기서 보자마자 1번 박스와 2번 박스 둘 중 하나는 다른 박스로 옮겨줘야한다는 생각이 들었다면.
 * 그걸 반대로 생각하면, 1번 박스나 2번 박스 하나는 옮기지 않아도 된다도 성립됨을 쉽게 이해할 것입니다.
 * 그렇다면 그 기준을, '전에 R 색상으로만 구성된 박스가 있었으니, 다음에 같은 조건의 박스가 나오면 걔는 옮겨줘야한다.'를 기억하면 끝입니다.
 *
 *
 * 여기까지의 로직을 정리하면, '기본적으로 모든 박스를 1번의 비용으로 옮기는데, 옮기지 않는 특정 경우들은 비용에서 빼준다' 입니다.
 *
 * 이제 조커의 선정인데, 보통 문제를 읽으면 '가장 다양한 카드를 지닌 박스로 선정해야지' 라는 생각이 들 수 있는데, 결론은 모든 경우의 수를 다 따져야합니다.
 * 왜냐하면, 어차피 1의 비용으로 다 옮길 수 있으니 다양한 카드를 얼마나 지녔냐는 기준이 되지 못합니다.
 * 조커의 기준은 어떤 박스를 지정했을 때, 그 박스로 인해 옮기지 않아도 되는 경우가 늘어나는 박스가 어디인지 찾아야합니다.
 *
 * 예를 들어, 다음 입력의 경우
 * 6 2
 * 1 1
 * 1 1
 * 1 1
 * 1 0 <- 4번 박스
 * 1 0 <- 5번 박스
 * 0 1
 *
 * 조커 박스는 4번 혹은 5번으로 해야합니다, 만약 1번 박스를 조커로 하면 4번 5번 둘 중 하나는 옮겨야합니다. 하지만 4번으로 조커를 만들면
 * 5번은 그대로 있어도 되니까 비용이 1 개 줄게 됩니다. 즉 조커는 점수를 줄여주는 박스를 찾아야하는겁니다.
 * 그런데 이걸 찾으려면? 결국, 모든 경우의 수를 다 돌아가며 확인하는게 가장 적당하므로 브루트포스를 적용해주면됩니다.
 *
 * 이렇게 하면, 입력을 제외하고 O(50*50)만에 정답을 찾을 수 있게 됩니다.
 *
 *
  */