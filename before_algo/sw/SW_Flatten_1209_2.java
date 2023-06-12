package sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


// bufferedReader 사용
public class SW_Flatten_1209_2 {
    static int dump, maxIdx, minIdx;
    static int[] floor;
    public static void main(String[] args) throws Exception{
    	double start = System.nanoTime();
    	
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        for(int t=1; t<=10; t++) {
            dump = Integer.parseInt(br.readLine());
            floor = new int[100];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i=0; i<100; i++) {
                floor[i] = Integer.parseInt(st.nextToken());
            }
            
            minIdx = 0;
            maxIdx = 0;
            
            for(int i=0; i<dump; i++) {
                //최소, 최대의 index
                reset();
                
                //최대, 최소 차이 비교
                int gap = floor[maxIdx] - floor[minIdx];
                if(gap == 0 || gap == 1) break;
                
                //평탄화 작업
                floor[maxIdx]--;
                floor[minIdx]++;
            }
            
            // 최종적인 min, max의 index 계산
            reset();
            int result = floor[maxIdx] - floor[minIdx];
            
            
            
            
            System.out.println("#" + t + " " + result);
        }
        
        double end = System.nanoTime();
        System.out.println("duration : "+ (end-start)/1_000_000_000);
    }
    static void reset() {
        for(int i=0; i<100; i++) {
            if(floor[i] < floor[minIdx] ) minIdx = i;
            if(floor[i] > floor[maxIdx] ) maxIdx = i;             
        }
    }
}