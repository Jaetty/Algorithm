import java.io.*;
import java.util.*;

public class Main {


    /*
    * 1. 도메인 별로 pq가 있음
    * 2. check 당시에 이 도메인 별 pq에서 가장 상위에 있는 값을 peek 한걸 wating pq에 모아줌
    * 2-1. 시간을 아끼려면 시간적(history), 작업적(inJudgingDomain)으로 조건을 만족하는 애들만 뽑아와야함
    * 2-2. 모든 도메인을 꺼내서 2-1이 충족되는지 확인해줌
    * 3. wating 중 가장 우선순위 높은 node를 judging으로 만들어줌
    * 4. watingUrl, inJudgingDomain을 수정하고 해당하는 domain을 가진 collection의 pq에서 하나를 poll()해서 갯수를 맞춰줌
    *
    * 이러면 input 시 O(1), end 시 O(1), check 시에는 최대 도메인의 갯수인 300번의 반복만 수행하면 되기 때문에 시간 초과가 되지 않음
    *
    * */

    static StringBuilder sb;
    static HashMap<String, Integer> history;
    static HashMap<String, PriorityQueue<Node>> collection;
    static HashMap<Integer, Node> judging;
    static PriorityQueue<Integer> j_ids;
    static PriorityQueue<Node> wating;
    static Set<String> watingUrl, inJudgingDomain, everyDomain;
    static int watingSize;

    static class Node{

        int t, p;
        String url, domain;

        Node(int t, int p, String url){
            this.t = t;
            this.p = p;
            this.url = url;
        }

    }

    public static void main(String[] args) throws Exception {

//        BufferedReader br = new BufferedReader(new FileReader("D:\\input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st;

        for(int i=0; i<N; i++){

            st = new StringTokenizer(br.readLine());
            int query = Integer.parseInt(st.nextToken());

            switch(query){

                case 100 :
                    init(Integer.parseInt(st.nextToken()), st.nextToken());
                    break;
                case 200 :
                    input(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), st.nextToken());
                    break;
                case 300 :
                    check(Integer.parseInt(st.nextToken()));
                    break;
                case 400 :
                    end(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                    break;
                case 500 :
                    sb.append(watingSize +"\n");
                    break;


            }
        }
        System.out.println(sb.toString());


    }

    static void init(int N, String domain){

        judging = new HashMap<>();
        history = new HashMap<>();
        inJudgingDomain = new HashSet<>();
        watingUrl = new HashSet<>();
        everyDomain = new HashSet<>();
        wating = new PriorityQueue<>((e1, e2)->{
            if(e1.p==e2.p){
                return e1.t-e2.t;
            }
            else return e1.p - e2.p;
        });
        collection = new HashMap<>();

        j_ids = new PriorityQueue<Integer>();

        for(int i=1; i<=N; i++){
            j_ids.add(i);
            judging.put(i,null);
        }

        input(0, 1, domain);
    }

    static void check(int t){

        candidateSet(t);

        if(!j_ids.isEmpty() && !wating.isEmpty()){

            Node node = wating.poll();

            inJudgingDomain.add(node.domain);
            collection.get(node.domain).poll();

            node.t = t;
            judging.put(j_ids.poll(), node);
            watingUrl.remove(node.url);
            watingSize--;
        }

    }
    public static void candidateSet(int t) {

        // everyDomain에서 도메인 꺼낸 후
        // history에서 시간이 괜찮은지 보고?
        // inProcessDomain에 속해있지 않은지 봐서?
        // 둘다 ok면 watting에 peek으로 넣어줌


        Iterator it = everyDomain.iterator();

        wating.clear();

        while(it.hasNext()){

            String domain = it.next().toString();

            if(!inJudgingDomain.contains(domain) && history.get(domain) <= t && !collection.get(domain).isEmpty()){
                wating.add(collection.get(domain).peek());
            }
        }


    }


    static void input(int t, int p, String url){

        // 이미 wating 중인 도메인인지 확인
        if(watingUrl.contains(url)){
            return;
        }
        else{
            watingUrl.add(url);
        }

        Node add = new Node(t, p, url);

        add.domain = url.split("/")[0];

        if(!everyDomain.contains(add.domain)){

            everyDomain.add(add.domain);

            collection.put(add.domain, new PriorityQueue<>((e1, e2)->{
                if(e1.p==e2.p){
                    return e1.t-e2.t;
                }
                else return e1.p - e2.p;
            }));

            history.put(add.domain, 0);

        }

        collection.get(add.domain).add(add);

        watingSize++;

    }

    static void end(int t, int j_id){

        if(judging.get(j_id)==null){
            return;
        }

        Node node = judging.get(j_id);

        inJudgingDomain.remove(node.domain);
        history.put(node.domain, node.t + (t-node.t)*3);

        judging.put(j_id, null);
        j_ids.add(j_id);

    }

}