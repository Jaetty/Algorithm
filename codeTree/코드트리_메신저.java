import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int N;
    static class Node{
        int id, power;
        boolean alarmOff;
        Node parent;
        List<Node> children;

        int[] alarmCounts;

        Node(int id){
            this.id = id;
            this.power = -1;
            children = new ArrayList<>();
            alarmCounts = new int[21];
        }

    }

    static Node[] nodes;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        nodes = new Node[N+1];

        for(int i = 0; i <= N; i++){
            nodes[i] = new Node(i);
        }

        for (int query = 0; query < Q; query++) {

            st = new StringTokenizer(br.readLine());

            switch (st.nextToken()) {
                case "100":
                    // O(LOG N)
                    nodes[0].alarmCounts[0] = 1;

                    for (int cur = 1; cur <= N; cur++) {
                        int parent = Integer.parseInt(st.nextToken());
                        nodes[cur].parent = nodes[parent];
                        nodes[parent].children.add(nodes[cur]);
                    }

                    for (int cur = 1; cur <= N; cur++) {
                        int power = Integer.parseInt(st.nextToken());
                        powerChange(cur, power);
                    }

                    break;
                case "200":
                    // O(LOG N)
                    alarmOnOff(Integer.parseInt(st.nextToken()));
                    break;
                case "300":
                    // O(LOG N)
                    powerChange(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                    break;
                case "400":
                    // O(LOG N)
                    parentChange(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                    break;
                case "500":
                    // O(1)
                    int id = Integer.parseInt(st.nextToken());
                    int answer = nodes[id].alarmCounts[0]-1;
                    sb.append(answer).append("\n");
                    break;
            }

        }
        System.out.print(sb.toString());

    }

    static void dfs_count_change(int id, boolean minus){

        Node cur = nodes[id];
        Node ori = nodes[id];

        int depth = 1;

        while(cur.parent != null){

            cur = cur.parent;

            for(int i = 20-depth; i >=0; i--){

                if(minus){
                    cur.alarmCounts[i] -= ori.alarmCounts[i+depth];
                }
                else{
                    cur.alarmCounts[i] += ori.alarmCounts[i+depth];
                }

            }

            depth++;
            if(cur.alarmOff || cur.id == 0){
                break;
            }

        }
    }



    // 200 시간 : O(20*20)
    static void alarmOnOff(int id) {

        dfs_count_change(id,!nodes[id].alarmOff);
        nodes[id].alarmOff = !nodes[id].alarmOff;

    }

    // 300 시간 : O(20*20)
    static void powerChange(int id, int new_power) {

        new_power = Math.min(new_power, 20);

        Node cur = nodes[id];
        int ori_power = cur.power;
        int depth = 0;

        // 예전 파워만큼 빼줌
        // cur이 루트이거나, cur이 alarmOff 상태거나 <= ???? alarmOff라도 바꿔야하지 않나.., 최대 파워 높이 보다 멀리 갔다면 exit
        while(ori_power >= depth){

            for(int i = ori_power-depth; i >=0; i--){
                cur.alarmCounts[i]--;
            }

            if(cur.alarmOff || cur.parent == null || cur.id == 0){
                break;
            }

            cur = cur.parent;
            depth++;
        }

        cur = nodes[id];
        cur.power = new_power;
        depth = 0;

        while(new_power >= depth){

            for(int i = new_power-depth; i >=0; i--){
                cur.alarmCounts[i]++;
            }

            if(cur.alarmOff || cur.parent == null || cur.id == 0){
                break;
            }

            cur = cur.parent;
            depth++;
        }

    }

    // 400 시간 : O(20*20)
    static void parentChange(int a_id, int b_id) {

        // 우선 부모 값들에게 모든 자식들의 값을 빼게 해주기
        if(!nodes[a_id].alarmOff){
            dfs_count_change(a_id, true);
        }
        if(!nodes[b_id].alarmOff){
            dfs_count_change(b_id, true);
        }

        // 부모 가져와서 각각 자식 목록 수정하기
        Node a_parent = nodes[a_id].parent;
        Node b_parent = nodes[b_id].parent;

        a_parent.children.remove(nodes[a_id]);
        a_parent.children.add(nodes[b_id]);

        b_parent.children.remove(nodes[b_id]);
        b_parent.children.add(nodes[a_id]);

        // 자식들 부모 바꾸기
        nodes[a_id].parent = b_parent;
        nodes[b_id].parent = a_parent;

        // 자식
        if(!nodes[a_id].alarmOff){
            dfs_count_change(a_id, false);
        }
        if(!nodes[b_id].alarmOff){
            dfs_count_change(b_id, false);
        }

    }

}