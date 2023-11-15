/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */
class Solution {
    public int solution(String s) {
        int answer = s.length();

        for(int sub_size=1; sub_size<=s.length()/2; sub_size++){
            int size = s.length();
            boolean flag = false;
            String sub = s.substring(0, sub_size);
            int count = 1;

            for(int i=sub_size; i<s.length(); i+= sub_size){
                if(i+sub_size>s.length()) break;
                String temp = s.substring(i, i+sub_size);
                if(sub.equals(temp)){
                    size -= sub_size;
                    count++;
                }
                else{
                    sub = temp;
                    if(count>1){
                        size+= ten_count(count);
                        count = 1;
                    }
                }
            }
            if(count>1) size+= ten_count(count);

            answer = Math.min(size, answer);
        }

        return answer;
    }

    static int ten_count(int count){
        int result = 1;
        while(count>=10){
            count/=10;
            result++;
        }
        return result;
    }

}

/*
* 문자열 문제
*
* 해당 문제는 포인트는 문자열을 몇 개 줄였는지 표시하는 부분입니다.
* 즉 abcabc 는 2abc이고 줄여진 문자열 길이는 4입니다. abcabcabc....abc로 abc가 11개 있을 땐 11abc로 표시해야합니다. 줄여진 문자열 길이는 5입니다.
* abc가 100개 있어서 그것을 줄였다면 100abc로 줄여진 문자열 길이는 6이 됩니다.
* 그렇다면 결과 문자열을 계속 줄이다가 더이상 못줄이는 경우가 발생하면 줄여진 숫자의 자릿수 만큼 더해주면 된다는 결과가 나옵니다.
*
* 위 포인트만 주의해서 로직을 세우면 다음과 같습니다.
* 1. 최대 부분 압축 가능한 문자열 길이는 원문의 반 크기이다.
* 2. 최악의 경우는 문자열을 하나도 줄이지 못한 경우로 원문 문자열 크기와 같은 경우다. 그러므로 answer의 초기값과 비교용 변수 size의 초기값은 원문 문자열의 길이로해준다.
* 3. 인덱스를 이동하면서 부분 문자열을 찾으면 줄여진 횟수를 카운팅해주고 size -= 부분 문자열 길이로 연산해준다.
* 4. 문자열이 끝났거나, 문자열이 서로 맞지 않다면 이때까지 줄인 문자열의 자리수만큼 size에 더해준다.
* 5. answer에 이때까지 줄여진 숫자 중 가장 작은 수를 넣어준다.
*
* 해당 로직을 코드로 구현해주면 문제를 해결할 수 있습니다.
*
* */