import java.util.*;

class Solution {

    static int row, col, size;
    static int[] choice;
    static String[][] input;
    static List<Integer> answer;
    static Set<String> set;

    public int solution(String[][] relation) {
        answer = new ArrayList<>();
        input = relation;

        row = relation.length;
        col = relation[0].length;


        // 최대 조합 개수를 나타냄. 1~8개
        for(int i=1; i<=col; i++){
            choice = new int[i];
            size = i;

            for(int j=0; j<col; j++){
                choice[0] = j;
                comb(1, j);
            }
        }

        return answer.size();
    }

    static void comb(int depth, int index){

        // 튜플 내용들을 문자로 조합해서 유일성이 지켜지는지 확인
        if(depth>=size){
            set = new HashSet<String>();
            StringBuilder sb;

            for(int i=0; i<row; i++){
                sb = new StringBuilder();
                for(int j=0; j<size; j++){
                    // 아래는 혹시나 속성 값이 절묘하게 이어져서 마치 하나의 문자가 되는 것을 방지하기 위해서 몇번째 속성에서 나온 문자열인지 같이 적어준다.
                    // 예를 들면 [["a", "bc"], ["ab", "c"], ["a", "c"]] 와 같이 입력이 오면 정답이 1인데
                    // 문자를 조합하게 되면 "abc" "abc" "ac" 인데 set에서는 abc가 같다고 보기 때문에 유일성이 충족되지 않았다고 볼 수 있으므로
                    // "0a1bc" "0ab1c" "0a1c" 와 같이 명확히 구분하기 위함.
                    sb.append(choice[j]);
                    sb.append(input[i][choice[j]]);
                }
                set.add(sb.toString());
            }

            // 만약 유일성이 확보되었다면
            // 이미 정답이 만들어진 것과 비교하여 최소성을 만족하는지 조사 후 정답 모음집에 추가
            if(set.size()==row){

                int val = 0;
                boolean flag = true;

                for(int i=0; i<size; i++){
                    val |= 1 << (choice[i]);
                }

                for(int i=0; i<answer.size(); i++){
                    int comp = answer.get(i);
                    if( (comp & val) == comp){
                        flag = false;
                        break;
                    }
                }

                if(flag){
                    answer.add(val);
                }
            }

            return;
        }

        for(int i=index+1; i<col; i++){
            choice[depth] = i;
            comb(depth+1, i);
        }

    }
}

/*
* 조합 문제
*
* 해당 문제는 조합을 응용해서 풀 수 있는 문제입니다.
* 이 문제의 포인트는 단 두가지 입니다. 조합을 만든 후
* 1. 유일성을 만족하는지 조사한다.
* 2. 최소성을 만족하는지 조사한다.
*
* 여러가지 방법으로 이를 해결할 수 있겠지만 저의 경우 1번은 Set을 이용하여 검사하고, 2번은 비트마스킹을 이용하였습니다.
* 그리고 조합을 만들 때 순서는
* 1, 2, 3, 4, 12, 13, 14, 23, 24, 34, 123, 124, 134, 234, 1234... 이런 순으로
* 먼저 작은 순서로 만들 수 있는지 확인하고, 다음으로 점점 큰 수를 만들 수 있는지 확인합니다.
* 그 이유는 최소성을 만족하는지 조사하려면 먼저 가장 작은 단위에서 만들어질 수 있는지 확인해야하기 때문입니다.
*
* 예를 들어 예제 입력이 다음과 같다면
* [["a", "1", "4"], ["2", "1", "5"], ["a", "2", "4"]] (정답은 2)
*
* 먼저 1개의 속성으로만 조합을 만들고 포인트 1번과 2번을 만족하는지 확인합니다.
* 그 방법은 속성으로 만든 값에서 문자열을 가져와 set에 넣어서 set 사이즈가 tuple만큼의 크기가 되는지 확인하면 됩니다.
* 그렇게해서 만들면 set은 각각 [a, 2], [1, 2], [4, 5] 로 튜플 사이즈가 3인데 set의 사이즈는 각각 2이므로 속성 1개만 쓰는 조합으로는 유일성이 없다는 것을 알 수 있습니다.
* 그렇다면 조합 크기가 2가 되면 각각 [a1, 21, a2]와 같이 set크기가 tuple값과 같아 집니다. 그러면 유일성이 보장되게 됩니다.
* 이렇게 유일성이 보장되며 최소성도 보장되는 결과는 {[0,1], [1,2]} 조합입니다.
*
* 똑같이 조합의 크기가 3이되면 유일성은 만족하는데 최소성이 보장되지 않는 이유는 무엇인지 생각해보면
* 0,1이 이미 후보키가 된다면 또한 1,2로만 후보키를 만들 수 있다면 최소성이 만족된 것입니다.
* 0,1이나 1,2를 더 큰 크기의 조합에서 포함하게 되면 뒤에 최소성이 만족되지 못한다는 뜻 입니다.
*
* 이를 비트마스킹으로 새로운 유일성을 만족한 조합을 가져왔을 때 이미 만들어진 조합을 포함하고 있다면 정답으로 치지 않게해주면 됩니다.
* 이와 같은 방식으로 문제를 해결할 수 있습니다.
* */