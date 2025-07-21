import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static class Query{
        int cmd, t, x, n;
        String name;

        Query(int cmd, int t, int x, String name, int n){
            this.cmd = cmd;
            this.t = t;
            this.x = x;
            this.n = n;
            this.name = name;
        }

    }

    static class Customer{

        // 고객 입장 시간, 위치, 몇개 먹어야하는지, 언제 자리를 뜨는지
        int t, x, n, last_time;
        String name;

        Customer(int t, int x, int n, int last_time, String name){
            this.t = t;
            this.x = x;
            this.n = n;
            this.last_time = 0;
            this.name = name;
        }

    }

    // 순수 쿼리 리스트
    static List<Query> queries;

    // 고객 리스트
    static List<Customer> customer_list;

    // 초밥이 나둬진 쿼리
    static Map<String, List<Query>> queries_100;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        queries = new ArrayList<>();
        customer_list = new ArrayList<>();
        queries_100 = new HashMap<>();

        int L = Integer.parseInt(st.nextToken()), Q = Integer.parseInt(st.nextToken());

        for(int i = 0; i < Q; i++){

            st = new StringTokenizer(br.readLine());

            int cmd = -1;
            int t = -1, x = -1, n = -1;
            String name = "";
            cmd = Integer.parseInt(st.nextToken());
            if(cmd == 100) {
                t = Integer.parseInt(st.nextToken());
                x = Integer.parseInt(st.nextToken());
                name = st.nextToken();
            }
            else if(cmd == 200) {
                t = Integer.parseInt(st.nextToken());
                x = Integer.parseInt(st.nextToken());
                name = st.nextToken();
                n = Integer.parseInt(st.nextToken());
            }
            else {
                t = Integer.parseInt(st.nextToken());
            }

            queries.add(new Query(cmd, t, x, name, n));

            if(cmd == 100){
                queries_100.computeIfAbsent(name, k -> new ArrayList<>()).add(new Query(cmd, t, x, name, n));
            }
            else if(cmd == 200){
                Customer customer = new Customer(t, x, n, 0, name);
                customer_list.add(customer);
            }

        }

        for(Customer customer : customer_list){

            for(Query query : queries_100.get(customer.name)){

                int time_to_remove = 0;

                if(query.t < customer.t){

                    // 시간차이 + 원래 위치 % L = > 손님이 앉았을 때 초밥의 위치.
                    int sushi_cur_x = ((customer.t - query.t) + query.x)%L;

                    // 스시가 현재 위치에서 손님 위치까지 가려면 얼마나 더 가야하는지
                    int additional_time = (customer.x - sushi_cur_x + L) %L;
                    time_to_remove = customer.t + additional_time;
                }
                else{
                    int additional_time = (customer.x - query.x + L) %L;
                    time_to_remove = query.t + additional_time;

                }

                // 손님이 자리를 뜨는 마지막 시간을 갱신
                customer.last_time = Math.max(customer.last_time,time_to_remove);

                // 초밥이 사라지는 쿼리는 111
                queries.add(new Query(111, time_to_remove, -1, customer.name, -1));

            }

        }

        for(Customer customer : customer_list){
            queries.add(new Query(222, customer.last_time, -1, customer.name, -1));
        }

        queries.sort((e1,e2)-> {
            if(e1.t == e2.t){
                return e1.cmd - e2.cmd;
            }
            return e1.t - e2.t;
        });

        int sushi = 0;
        int customer = 0;

        for(int i = 0; i < queries.size(); i++){

            Query q = queries.get(i);

            if(q.cmd == 100){
                sushi++;
            }
            else if(q.cmd == 111){
                sushi--;
            }
            else if(q.cmd == 200){
                customer++;
            }
            else if(q.cmd == 222){
                customer--;
            }
            else{
                sb.append(customer +" "+sushi).append("\n");
            }

        }

        System.out.print(sb.toString());

    }

}

/*
@@ 해당 문제는 풀이에 실패하여 해설을 참고했습니다!

반드시 300 입력이 주어질 때 결과물을 내야한다고 고정관념에 사로잡혀있었는데,
해당 문제를 통해 그걸 뒤로 미뤄도 된다는걸 알게되었습니다.

*/