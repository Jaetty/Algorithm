import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;

        int first=0, last = people.length-1;

        Arrays.sort(people);

        while(first<=last){

            if(people[last] + people[first] <= limit) first++;
            last--;
            answer++;
        }

        return answer;
    }
}

/*
* 그리디 알고리즘 문제
* 해당 문제는 정렬을 이용하여 풀 수 있습니다.
*
* 다음과 같은 입력이 있다면
* people = 70, 50, 80, 50 limit = 100
*
* 먼저 정렬을 하고 나면 [50, 50, 70, 80] 이 될 것입니다.
* 이 문제는 단 2명만 보트에 탈 수 있기 때문에 그리디하게 생각해보면 가장 큰 값하고 가장 작은 값이 더해지면 2명을 태워 보내면 될 것입니다.
* 그렇다면 예제로 생각해보면 80이랑 50이 더해질 수 있는지 보고(80+50 > 100) 안되니 80만 보트로 보내고
* 그 다음으로 가장 큰 값인 70하고 50이 더해지는지 보고 (70 + 50 > 100) 안되니 70만 보트로 보내고
* 다음으로 50 + 50이 (100 == 100) 이므로 둘을 같이 태워 보내게 됩니다.
*
* 이를 코드로 나타내면 위와 같습니다.
*
*
* */