import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N, M;
    static long[] arr;
    static long min, max;

    static class SegementTree{
        long[] treeMin;
        long[] treeMax;
        int treeSize;

        SegementTree(int arrSize){
            int h = (int) Math.ceil(Math.log(arrSize)/Math.log(2));
            this.treeSize = (int)Math.pow(2, h+1);
            this.treeMin = new long[treeSize];
            this.treeMax = new long[treeSize];

            for(int i=0; i<treeSize; i++){
                treeMin[i] = Long.MAX_VALUE;
                treeMax[i] = Long.MIN_VALUE;
            }

        }

        public void init(int node, int start, int end){

            if(start==end){
                treeMin[node] = arr[start];
                treeMax[node] = arr[start];
                return;
            }
            init(node*2, start, (start+end)/2);
            init(node*2+1, (start+end)/2+1, end);

            treeMin[node] = Math.min(treeMin[node*2], treeMin[node*2+1]);
            treeMax[node] = Math.max(treeMax[node*2], treeMax[node*2+1]);
        }

        public void find(int node, int start, int end, int left, int right){

            if(left > end || right <start) return;

            if(left <= start && end <= right){
                min = Math.min(min, treeMin[node]);
                max = Math.max(max, treeMax[node]);
            }
            else{
                find(node*2, start, (start+end)/2, left, right);
                find(node*2+1, (start+end)/2+1, end, left, right);
            }


        }


    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        arr = new long[N+1];

        for(int i=1; i<N+1; i++){
            arr[i] = Integer.parseInt(br.readLine());
        }

        SegementTree segementTree = new SegementTree(N);
        segementTree.init(1,1,N);

        for(int i=0; i<M; i++){

            st = new StringTokenizer(br.readLine());

            min = Long.MAX_VALUE;
            max = Long.MIN_VALUE;

            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            segementTree.find(1, 1,N,left,right);
            sb.append(min+" "+max).append("\n");
        }

        System.out.print(sb);
    }
}


/*
* 세그먼트 트리 문제
*
* 해당 문제는 로직의 문제보다는 세그먼트 트리를 처음 이해하고 익히기 위한 문제라고 볼 수 있습니다.
* 주석으로 세그먼트 트리의 기능마다 어떤 역할을 수행하는지 적어놨지만 더욱 상세한 설명을 하기 위해서는
* 제가 세그먼트 트리의 개념을 공부할 때 참고했던 블로그 및 사이트 출처를 아래에 남기도록 하겠습니다.
* https://cano721.tistory.com/38
* https://blog.naver.com/ndb796/221282210534
* https://www.acmicpc.net/blog/view/9
*
* 이제 세그먼트 트리를 안다는 가정하에 문제를 설명해주면
* 위의 사이트에서 나타난 세그먼트 트리는 구간합을 기억하게 만들어줬습니다.
* 하지만 해당 문제는 최솟값과 최대값을 물어보고 있습니다. 그렇다면 세그먼트 트리의 모든 부모 노드들이 자신의 자식 노드들 중에서 가장 작은 값과 가장 큰 값을 기억하게 해줍니다.
* 즉 구간합이었던 노드의 값을 구간 내에 가장 작은 값과 가장 큰 값을 기억하게 코드를 수정해주면 문제를 해결할 수 있습니다.
*
* */