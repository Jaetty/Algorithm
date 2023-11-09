/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

class Solution {

    static int[] alphabet;

    public int solution(String skill, String[] skill_trees) {
        int answer = 0;
        alphabet = new int[26];

        for(int i=skill.length()-1; i>=0; i--){
            alphabet[skill.charAt(i)-'A'] = i;
        }

        for(int i=0; i<skill_trees.length; i++){

            int index = 0;
            String str = skill_trees[i];

            for(int j=0; j<str.length(); j++){
                if(index < skill.length() && str.charAt(j)==skill.charAt(index)){
                    index++;
                }
                if(alphabet[str.charAt(j)-'A'] - index > 0) break;
                if(j==str.length()-1) answer++;
            }
        }


        return answer;
    }
}

/*
* 위상 정렬 문제
*
* 해당 문제는 위상 정렬을 이용하여 문제를 해결할 수 있습니다.
* 예제 입력으로 예시를 들어가며 설명하겠습니다.
*
* skill 입력은 "CBD" 이고 skill_trees는 ["BACDE", "CBADF", "AECB", "BDA"] 이며 정답은 2입니다.
* 먼저 생각해보면 B와 D는 각각 C가 먼저 나오고 다음에 B가 나와야지 나올 수 있는 문자입니다.
* 그렇다면 D를 배우려면 선수적으로 앞에 2개 C, B를 먼저 배워야하고 B의 경우 앞의 1개인 C를 먼저 배워야합니다.
*
* 이를 배열로 한번 나타내보면 먼저 ABC...Z까지의 알파벳 대문자를 나타내는 26개의 배열을 만들면
*  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
* [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
*
* 여기서 각각 D와 B의 값을 먼저 배워야하는 개수 만큼 올려줍니다.
*  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
* [0,1,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
*
* 그렇다면 목표는 이 배열의 값이 0이하일 경우는 배울 수 있고 0 초과이면 선수 스킬이 있다는 뜻이므로 배우는 스킬의 값이 모두 0이하인지 확인하면 됩니다.
* 그리고 선행 스킬 순서가 담긴 SKILL의 입력 값인 C, B, D가 최초로 만날 때 배열에 있던 B와 D의 값을 각각 1나씩 내려주면 됩니다.
* 이를 저는 CBD의 INDEX로 하여 최초의 INDEX값은 0이고 C를 만나면 다음 B를 가르키는 1이 될 것이고 D를 만나면 2가 될 것입니다.
* 그래서 배열의 값 - INDEX값을 통하여 배열의 값을 수정하지 않더라도 선행 스킬을 배웠는지 확인할 수 있습니다.
*
* 이러한 로직을 구현하면 해당 문제를 해결할 수 있습니다.
*
* */