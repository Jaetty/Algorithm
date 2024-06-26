import java.io.*;
import java.util.*;

/*
 * 스포일러 방지를 위해 맨 밑에 주석으로 풀이가 있습니다.
 * */

public class Main {

    static int M, N, L;
    static List<int[]> animals;
    static int[] spot;

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        spot = new int[M];

        st = new StringTokenizer(br.readLine());

        for(int i=0; i<M; i++){
            spot[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(spot);
        animals = new ArrayList<>();

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            animals.add(new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
        }

        int answer = 0;

        for(int i=0; i<N; i++){

            answer += binary_search(animals.get(i));

        }

        System.out.println(answer);

    }

    static int binary_search(int[] animal){

        int lo = 0;
        int hi = M;

        while(lo<=hi){

            int mid = (lo+hi)/2;
            if(mid==M) return 0;

            int distance = distance(spot[mid], animal);

            if( L < distance &&  animal[0] < spot[mid]){
                hi = mid-1;
            }
            else if(L < distance && animal[0] >= spot[mid]){
                lo = mid+1;
            }
            else if (L >= distance){
                return 1;
            }
        }
        return 0;
    }

    static int distance(int x, int[] ab){
        return Math.abs(x-ab[0])+ab[1];
    }

}

/*
 * 이분 탐색 문제
 *
 * 해당 문제의 가장 큰 포인트는 동물이 특정 사대의 범위 안에 들어있는가를 체크하는 것 입니다.
 *
 * 처음 문제를 읽었을 때 이분 탐색으로 사대를 기준으로 몇 마리의 동물을 잡을 수 있고, 이를 어떻게 중복체크 할지 고민했는데
 * 반대로 생각하면 어떤 동물이 사대의 중 한 곳의 범위 안에 있는지 확인만 해주면 됩니다.
 * 거리 값은 |x-a| + b 이므로 이를 기준으로 거리와 입력된 L값에 소속되는지 확인하고, 범위를 맞춰주는 로직을 구상하면 문제를 해결할 수 있습니다.
 *
 *
 * */