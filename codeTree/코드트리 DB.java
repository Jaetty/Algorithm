import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static final int MAX = (int) 1e9;

    static int idx;
    static int[] values = new int[100005];
    static String[] names = new String[100005];
    static Map<String, Integer> nameToId = new HashMap<>();
    static Set<Integer> existedValues = new HashSet<>();

    static class Node{
        int left=0, right=0, index=0;
        int count=0;
        long sum=0;
    }

    static List<Node> segmentTree = new ArrayList<>();

    static void update(int node, int start, int end, int number, int index, int count, long sum){

        if(start > number || end < number){
            return;
        }

        if(start == end){
            segmentTree.get(node).index = index;
            segmentTree.get(node).count = count;
            segmentTree.get(node).sum = sum;
            return;
        }

        int mid = (start + end)/2;
        if(number <= mid){

            if (segmentTree.get(node).left == 0) {
                segmentTree.get(node).left = segmentTree.size();
                segmentTree.add(new Node());
            }
            update(segmentTree.get(node).left, start, mid, number, index, count, sum);

        }
        else{

            if (segmentTree.get(node).right == 0) {
                segmentTree.get(node).right = segmentTree.size();
                segmentTree.add(new Node());
            }
            update(segmentTree.get(node).right, mid+1, end, number, index, count, sum);

        }

        int leftCount = segmentTree.get(segmentTree.get(node).left).count;
        int rightCount = segmentTree.get(segmentTree.get(node).right).count;
        long leftSum = segmentTree.get(segmentTree.get(node).left).sum;
        long rightSum = segmentTree.get(segmentTree.get(node).right).sum;
        segmentTree.get(node).count = leftCount + rightCount;
        segmentTree.get(node).sum = leftSum + rightSum;

    }

    public static long querySum(int node, int start, int end, int left, int right) {

        if(right < start || end < left || node == 0){
            return 0;
        }

        if(left <= start && right >= end){
            return segmentTree.get(node).sum;
        }
        int mid = (start + end)/2;
        long result = 0;

        if(segmentTree.get(node).left != 0){
            result += querySum(segmentTree.get(node).left, start , mid, left, right);
        }

        if(segmentTree.get(node).right != 0){
            result += querySum(segmentTree.get(node).right, mid+1 , end, left, right);
        }

        return result;

    }

    public static int queryRank(int node, int start, int end, int rank){

        if(start == end){
            return segmentTree.get(node).index;
        }

        int mid = (start + end)/2;
        int leftCount = segmentTree.get(segmentTree.get(node).left).count;

        // 왼쪽 자식 노드가 존재하고, 왼쪽 구간의 데이터 개수가 rank 이상이면 왼쪽 구간에서 찾습니다.
        if (segmentTree.get(node).left != 0 && leftCount >= rank) {
            return queryRank(segmentTree.get(node).left, start, mid, rank);
        }

        // 그렇지 않으면 오른쪽 구간에서 rank - 왼쪽 구간의 데이터 개수 번째로 작은 값을 찾습니다.
        return queryRank(segmentTree.get(node).right, mid + 1, end, rank - leftCount);

    }

    public static void main(String[] args) throws Exception {

        System.setIn(new FileInputStream("d://input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        String name = "";
        int value = 0;

        int Q = Integer.parseInt(br.readLine());
        for(int i=0; i<Q; i++){
            st = new StringTokenizer(br.readLine());

            String cmd = st.nextToken();

            switch(cmd){
                case "init":
                    init();
                    break;
                case "insert":
                    name = st.nextToken();
                    value = Integer.parseInt(st.nextToken());
                    sb.append(insert(name, value)).append("\n");
                    break;
                case "delete":
                    name = st.nextToken();
                    sb.append(delete(name)).append("\n");
                    break;
                case "rank":
                    value = Integer.parseInt(st.nextToken());
                    sb.append(getRank(value)).append("\n");
                    break;
                default :
                    value = Integer.parseInt(st.nextToken());
                    sb.append(getSum(value)).append("\n");
                    break;
            }


        }

        System.out.print(sb.toString());
    }

    static void init(){
        idx = 0;
        nameToId.clear();
        existedValues.clear();
        segmentTree.clear();
        segmentTree.add(new Node());
        segmentTree.add(new Node());
    }

    static int insert(String name, int value){

        if(nameToId.containsKey(name) || existedValues.contains(value)){
            return 0;
        }

        nameToId.put(name, ++idx);
        names[idx] = name;
        values[idx] = value;
        existedValues.add(value);

        update(1, 1, MAX, value, idx, 1, value);

        return 1;
    }

    static int delete(String name){

        if(!nameToId.containsKey(name)){
            return 0;
        }

        int index = nameToId.get(name);
        nameToId.remove(name);
        existedValues.remove(values[index]);

        update(1, 1, MAX, values[index], index, 0,0);
        return values[index];
    }

    static String getRank(int k){

        if(segmentTree.get(1).count < k){
            return "None";
        }

        int index = queryRank(1, 1, MAX, k);
        return names[index];
    }


    static long getSum(int k){
        return querySum(1, 1, MAX, 1, k);
    }

}