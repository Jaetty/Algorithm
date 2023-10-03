import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int Q,N,M,P;
    // sum은 모든 토끼들의 경주마다 얻는 점수(r+c)를 저장해둔다.
    // 그리고 r+c를 얻지 못하는 토끼만 r+c만큼 빼주고 나중에 점수를 구할때 모든 토끼한테 sum을 더해준다.
    static long sum, answer;
    // id로 토끼를 조회할 수 있게 해주는 Map
    static Map<Integer, Rabbit> rabbits;
    // 경주에 나오는 토끼들을 저장하는 PriorityQueue
    static PriorityQueue<Rabbit> wait;

    static class Rabbit{
        int jump;
        int row;
        int col;
        int id;
        int d;
        long score;

        Rabbit(int id, int d){
            this.id = id;
            this.d = d;
            this.row = 1;
            this.col = 1;
            this.score = 0;
            this.jump = 0;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        rabbits = new HashMap<>();
        sum = 0;
        // 순서는 1. 점프를 가장 적게한 2. 현 위치 r+c가 가장 작은 3. row(행)가 적은 4. col(열)이 적은 5. id가 적은 순
        wait = new PriorityQueue<Rabbit>((e1,e2)->{
            if(e1.jump==e2.jump){
                if(e1.row+e1.col == e2.row+e2.col){
                    if(e1.row==e2.row){
                        if(e1.col == e2.col){
                            return e1.id - e2.id;
                        }
                        else return e1.col - e2.col;
                    }
                    else return e1.row - e2.row;
                }
                else return (e1.row+e1.col) - (e2.row+e2.col);
            }
            else return e1.jump - e2.jump;
        });

        Q = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());

        st.nextToken(); // 맨 앞의 100 입력 무시
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        // 수정을 위해서 id에 따라 rabbit을 꺼낼 수 있게 해준다.
        for(int i=0; i<P; i++){
            int id = Integer.parseInt(st.nextToken());
            Rabbit rabbit = new Rabbit(id, Integer.parseInt(st.nextToken()));
            rabbits.put(id, rabbit);
            wait.add(rabbit);
        }

        for(int i=0; i<Q-1; i++){
            st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            switch (order){
                case 200 :
                    race(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
                    break;
                case 300:
                    change(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
                    break;
                case 400:
                    winner();
                    break;
            }

        }
        System.out.println(answer);


    }

    static void race(int K, int S){

        // 4방향 중 가장 좋은 위치를 구하기 위한 PriorityQueue
        PriorityQueue<int[]> location = new PriorityQueue<>((e1,e2)->{
            if(e1[0]+e1[1] == e2[0]+e2[1]){
                if(e1[0]==e2[0]){
                    return e2[1] - e1[1];
                }
                else return e2[0]-e1[0];
            }
            else return (e2[0]+e2[1]) - (e1[0]+e1[1]);
        });

        // 이번 K경주에서 뛴 적 있는 토끼를 기억하기 위한 Set
        Set<Rabbit> jumped = new HashSet<>();

        // K경주에서 뛴 적 있는 토끼 중 우승자를 찾기 위한 PriorityQueue
        PriorityQueue<Rabbit> winner = new PriorityQueue<>((e1,e2)->{
            if(e1.row+e1.col == e2.row+e2.col){
                if(e1.row==e2.row){
                    if(e1.col == e2.col){
                        return e2.col - e1.col;
                    }
                    else return e2.id - e1.id;
                }
                else return e2.row - e1.row;
            }
            else return (e2.row+e2.col) - (e1.row+e1.col);
        });

        for(int i=0; i<K; i++){

            Rabbit rabbit = wait.poll();
            location.clear();

            // 기본적인 로직은 다음과 같다.
            // 만약 뛴 거리가 격자를 넘지 않는다면 그 방향대로 움직이게 만들어준다.
            // 격자를 넘어간다면 일단 진행방향의 격자 끝에 도달하게 해주고 거리만큼 빼준다.
            // 이제 이 위치 기준으로 얼마만큼 왕복하는지 알아내준다.
            // 그럼 그만큼을 제외하고 나머지 거리 만큼 다시 뛰게 만들어준다.
            for(int j=0; j<4; j++){
                int row = rabbit.row;
                int col = rabbit.col;
                int distance = rabbit.d;
                switch (j){
                    case 0: // 상 일때
                        // 먼저 범위를 벗어나는지 확인해보고
                        if( row - distance <= 0){
                            // 벗어났으면 격자 맨 끝으로 이동시키고 거리만큼 점프력을 빼준 후
                            distance -= (row-1);
                            // 왔다갔다 수행할 만큼 없애준다.
                            distance %= (N*2)-2;
                            // 만약 왕복은 아니지만 격자를 벗어나는 경우는 반대편 격자 끝으로 이동 후 남은 거리만큼 움직여준다.
                            if(distance>=N){
                                row = N;
                                distance-=(N-1);
                                row -= distance;
                            }
                            else{
                                // 모든 왕복 이후 남은 뛸 수 있는 거리가 격자를 벗어나지 않는다면 그냥 움직여준다.
                                row = 1;
                                row += distance;
                            }
                            // 그리고 반대 방향으로 남은 거리만큼 이동시킨다.
                        }
                        else{
                            row -= distance;
                        }
                        break;
                    case 1: // 좌 일때
                        if( col - distance <= 0){
                            distance -= (col - 1);
                            distance %= (M*2)-2;

                            if(distance>=M){
                                col = M;
                                distance-=(M-1);
                                col -= distance;
                            }
                            else{
                                col = 1;
                                col += distance;
                            }

                        }
                        else{
                            col -= distance;
                        }
                        break;
                    case 2: // 하 일때
                        if( row + distance > N){
                            distance -= (N - row);
                            distance %= (N*2)-2;

                            if(distance>=N){
                                row = 1;
                                distance-=(N-1);
                                row += distance;
                            }
                            else{
                                row = N;
                                row -= distance;
                            }

                        }
                        else{
                            row += distance;
                        }
                        break;
                    case 3: // 우 일때
                        if( col + distance > M){
                            distance -= (M - col);
                            distance %= (M*2)-2;

                            if(distance>=M){
                                col = 1;
                                distance-=(M-1);
                                col += distance;
                            }
                            else{
                                col = M;
                                col -= distance;
                            }
                        }
                        else{
                            col += distance;
                        }
                        break;
                }
                location.add(new int[]{row,col});

            }

            int[] rc = location.poll();
            rabbit.row = rc[0];
            rabbit.col = rc[1];
            rabbit.jump++;

            // 나를 제외한 모든 토끼가 내가 있는 위치 r+c 값 만큼 점수를 얻어야한다.
            // 그러므로 r+c를 sum 변수에 더해주고 자신의 score 값은 r+c만큼 감소시킨다.
            sum += (rc[0]+rc[1]);
            rabbit.score -= (rc[0]+rc[1]);
            jumped.add(rabbit);
            wait.add(rabbit);

        }

        // 여기서 부터 밑은 실제 뛴 토끼들 중 우승자를 뽑고 점수를 부여한다..
        Iterator<Rabbit> it = jumped.iterator();

        while(it.hasNext()){
            winner.add(it.next());
        }

        Rabbit rabbit = winner.poll();
        winner.clear();
        rabbit.score += S;

    }

    static void change(int id, int L){

        Rabbit rabbit = rabbits.get(id);
        rabbit.d *= L;

    }

    static void winner(){
        answer = 0;

        // 이때까지 모은 r+c값의 총합을 각 토끼의 개인 score점수에 더해주면 최종 결과 값을 뽑을 수 있다.
        for(Rabbit rabbit : rabbits.values()){
            long score = rabbit.score + sum;
            if (score > answer){
                answer = score;
            }
        }

    }

}