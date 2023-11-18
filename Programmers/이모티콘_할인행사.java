/*
* 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
* */

class Solution {

    static int amount, people;
    static int[][] price;
    static int[] answer;

    public int[] solution(int[][] users, int[] emoticons) {

        people = users.length;
        amount = emoticons.length;

        answer = new int[2];

        price = new int[amount][4];

        for(int i=0; i<amount; i++){
            for(int j=0; j<4; j++){
                price[i][j] = (int)(emoticons[i] * (100-(j+1)*10)/100);
            }
        }

        int[] val = new int[people];

        backTrack(0, new int[people], users);


        return answer;
    }


    public static void backTrack(int depth, int[] sale, int[][] users){

        if(depth>=amount){

            // sale의 값이 음수값이면 가입된것.
            int join = 0;
            int total = 0;

            for(int i=0; i<sale.length; i++){
                if(sale[i]<0)  join++;
                else total += sale[i];
            }

            if(answer[0]<join){
                answer[0] = join;
                answer[1] = total;
            }
            else if(answer[0]==join && answer[1] < total){
                answer[1] = total;
            }


            return;
        }

        for(int i=0; i<4; i++){

            int[] n_sale = sale.clone();

            for(int j=0; j<people; j++){
                if(users[j][0] > (i+1)*10 || n_sale[j]<0) continue;
                n_sale[j] += price[depth][i];
                if(n_sale[j]>=users[j][1]) n_sale[j] = -1;
            }

            backTrack(depth+1, n_sale, users);

        }


    }


}

/*
* 백트래킹 문제
*
* 해당 문제의 포인트는 다음과 같습니다.
* 1. 할인율은 각각 10%, 20%, 30%, 40% 이다.
* 2. 이모티콘의 최대 갯수는 7개, 사용자 최대 수는 100명이다.
* 3. 사용자는 원하는 할인율 이상이어야만 구매하고 일정 금액 이상 구매시 모든 구매를 취소하고 서비스에 가입한다.
* 4. 우선 순위는 1. 서비스 가입자, 2. 판매 가격 이다.
*
* 1, 2번을 통해서 알 수 있는 사실은 이모티콘이 각각 10%,20%,30%,40% 할인으로 팔아야 되기 때문에 이모티콘이 7개면 가격은 최대 24가지가 나오게 됩니다.
* 또한 사용자 수는 100명이므로 이는 충분히 완전 탐색으로 풀 수 있는 문제라는 사실을 알 수 있습니다.
*
* 4번 포인트를 통해서 정답으로 return해야하는 배열 값의 우선순위 조건을 알 수 있습니다.
*
* 3번을 통해서 백트래킹 과정 중 어떤 연산을 해야하는지 생각할 수 있습니다.
* 그러면 생각이 드는게 "어떤 사용자가 얼마를 구매했고 서비스에 가입했는지 안했는지를 어떻게 기억하면 될까?"
* 아마 여러 방법이 있겠지만 저는 배열을 이용하였습니다.
*
* 사용자의 수 크기의 배열을 생성하고 각각의 인덱스는 몇 번째 사용자인지를 나타내고 인덱스의 값은 구매한 총 금액을 뜻합니다.
* 백트래킹 중에 할인율이 사용자의 조건과 일치하면 배열의 사용자의 인덱스의 값에 구매한 이모티콘 값을 더해줍니다.
* 그리고 더한 후의 값이 사용자의 한계 금액을 넘으면 값을 음수값으로 변화해줍니다.
*
* 음수 값인 이유는 아무것도 안샀다고 해도 구매값은 0이 최저값이기 때문에 음수를 넣어주면 서비스 가입자임을 구분할 수 있기 때문입니다.
* 이렇게 백트래킹 과정에서 배열의 값을 수정해주며 마지막에 도달하면 배열을 순회합니다.
*
* 음수일 땐 서비스 가입자 count를 증가, 양수일 땐 양수 값을 모두 더해서 최종적으로 서비스 가입자 수와 총 구매금액을 구할 수 있습니다.
*
* 이렇게 세워진 로직을 코드로 구현하여 문제를 해결하였습니다.
*
*
* */