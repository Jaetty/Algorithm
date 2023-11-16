import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N,M,K;
    static long[] arr;
    static class SegmentTree{

        long[] tree;
        int treeSize;

        public SegmentTree(int arrSize){
            // 트리의 높이는 이진로그로 h = log arrSize, arrSize는 단순 배열의 크기
            // 세그먼트 트리를 구성할 배열의 크기는 2^(h+1) 값으로 충분하게 된다.
            // arrSize의 값이 2의 제곱 값이 아닐 수 있기 때문에 충분한 배열 크기를 갖기 위해 올림을 해주어야한다.
            int h = (int) Math.ceil(Math.log(arrSize)/Math.log(2));
            this.treeSize = (int) Math.pow(2,h+1);

            tree = new long[this.treeSize];
        }

        // segmentTree 초기화 하는 기능
        // arr : 원본 배열, node : 현재 노드 번호, start : 원본 배열의 첫 값의 인덱스, end : 원본 배열의 마지막 값의 인덱스
        public long init(long[] arr, int node, int start, int end){

            if(start==end){
                return tree[node] = arr[start];
            }
            // 세그먼트 트리의 구조는 어떤 노드의 자식 노드 번호는 해당 노드 번호의 *2와 *2+1 값을 가지게 된다.
            // 노드는 좌우로 반씩 나눠지기 때문에 왼쪽으로 갈 때는 end 값을 (start+end)/2, 오른쪽으로 갈 땐 start에 (start+end)/2+1를 지정하여 원본 배열의 값을 불러올 수 있다.
            // 그리고 tree[node] = init(왼쪽) + init(오른쪽)으로 자식 노드의 합이 부모노드에 가게 만들어 결국 루트 노드의 값도 정해줄 수 있다.
            return tree[node] = init(arr,node*2, start, (start+end)/2)
                    + init(arr, node*2+1, (start+end)/2+1, end);

        }

        // node : 현재 노드, start : 배열의 시작, end : 배열의 끝, idx : 변경된 데이터의 인덱스, diff : 원래 값과 변경된 값의 차이 값
        public void update(int node, int start, int end, int idx, long diff){

            // 변경해야하는 인덱스의 값이 범위 밖이면 리턴
            if(idx < start || end < idx) return;

            // 차이 만큼 계산해줌
            tree[node] += diff;

            // 내 밑에 자식이 있는지 확인하여 자식이 있다면 (즉 리프 노드가 아니라면) 두 자식들의 값도 변경해줌
            if(start!=end){
                update(node*2, start, (start+end)/2, idx, diff);
                update(node*2+1, (start+end)/2+1 , end, idx, diff);
            }

        }

        // node : 현재 노드, start : 배열의 시작, end : 배열의 끝, left : 누적합 시작 인덱스, right : 누적합 마지막 인덱스
        public long sum(int node, int start, int end, int left, int right){

            // left, right가 배열 범위에서 벗어나면 리턴
            if(left > end || right < start ) return 0;

            // left, right가 start와 end 범위 내에 완전히 속한다면 더이상 자식으로 내려가지 않고 리턴
            if(left <= start && end <= right){
                return tree[node];
            }

            // 그 외의 경우라면 왼쪽, 오른쪽 자식을 탐색하여 찾아내서 리턴
            return sum(node*2, start, (start+end)/2, left, right) +
                    sum(node*2+1, (start+end)/2+1, end, left, right);

        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = new long[N+1];

        for(int i=1; i<=N; i++){
            arr[i] = Long.parseLong(br.readLine());
        }

        SegmentTree segmentTree = new SegmentTree(N);
        segmentTree.init(arr, 1, 1, N);

        for(int tc=0; tc<K+M; tc++){
            st = new StringTokenizer(br.readLine());
            int command = Integer.parseInt(st.nextToken());
            int sec = Integer.parseInt(st.nextToken());
            long third = Long.parseLong(st.nextToken());

            if(command==1){
                segmentTree.update(1, 1, N, sec, third - arr[sec]);
                arr[sec] = third;
            }
            else{
                sb.append(segmentTree.sum(1, 1, N, sec, (int)third)+"\n");
            }
        }

        System.out.print(sb.toString());

    }
}


/*
* 세그먼트 트리 문제
*
* 이번 문제는 단순 계산으로는 시간 초과가 발생하게 됩니다.
* 그 이유는 배열의 값 수정에는 O(1)이 걸리지만 합을 구하는데 일일히 O(N)으로 계산해야합니다.
* 누적합을 이용한다고 하더라도 수정을 하는 과정에서 누적합을 다시 수정해야만 합니다. 이 과정도 또한 O(N)이 걸리게 됩니다.
* 따라서 수정 횟수, 구간 합 범위와 횟수가 매우 큰 수라면 시간이 많이 걸리게 됩니다.
*
* 이를 해결하기 위해 세그먼트 트리를 이용하면 O(logN) 시간에 구간합, 수정이 가능하게 만들 수 있습니다.
*
* 해당 문제는 로직의 문제보다는 세그먼트 트리를 처음 이해하고 익히기 위한 문제라고 볼 수 있습니다.
* 주석으로 세그먼트 트리의 기능마다 어떤 역할을 수행하는지 적어놨지만 더욱 상세한 설명을 하기 위해서는
* 제가 세그먼트 트리의 개념을 공부할 때 참고했던 블로그 및 사이트 출처를 아래에 남기도록 하겠습니다.
* https://cano721.tistory.com/38
* https://blog.naver.com/ndb796/221282210534
* https://www.acmicpc.net/blog/view/9
*
* */