package sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

// ArrayList 사용 <= 속도 개선 X, 오히려 더 걸린다.
// bufferedReader 사용
public class SW_Flatten_1209_3 {
    static int dump, maxIdx, minIdx;
    static ArrayList<Integer> floor;
    public static void main(String[] args) throws Exception{
    	double start = System.nanoTime();
    	
        System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        for(int t=1; t<=10; t++) {
        	
            dump = Integer.parseInt(br.readLine());
            floor = new ArrayList<>();
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i=0; i<100; i++) {
                floor.add(Integer.parseInt(st.nextToken()));
            }
            
            minIdx = 0;
            maxIdx = 0;
            
            for(int i=0; i<dump; i++) {
                //최소, 최대의 index
                reset();
                
                //최대, 최소 차이 비교
                int gap = floor.get(maxIdx) - floor.get(minIdx);
                if(gap == 0 || gap == 1) break;
                
                //평탄화 작업
                floor.set(maxIdx, floor.get(maxIdx)-1);
                floor.set(minIdx, floor.get(minIdx)+1);
            }
            
            // 최종적인 min, max의 index 계산
            reset();
            int result = floor.get(maxIdx) - floor.get(minIdx);
            System.out.println("#" + t + " " + result);
        }
        
        double end = System.nanoTime();
        System.out.println("duration : "+ (end-start)/1000000000);
    }
    static void reset() {
        for(int i=0; i<100; i++) {
            if(floor.get(i) < floor.get(minIdx) ) minIdx = i;
            if(floor.get(i) > floor.get(maxIdx) ) maxIdx = i;             
        }
    }
}