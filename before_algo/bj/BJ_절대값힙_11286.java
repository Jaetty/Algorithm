package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class BJ_절대값힙_11286 {
	static int N;
	static PriorityQueue<Integer> pqueue = new PriorityQueue<>((n1,n2) -> Math.abs(n1)==Math.abs(n2) ? n1-n2 : Math.abs(n1)-Math.abs(n2));
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < N; i++) {
			int num = Integer.parseInt(br.readLine());
			
			if(num==0) { // 꺼내서 출력, 없으면 null 0 출력
				Integer min= pqueue.poll();
				sb.append(min==null ? 0 : min).append("\n");
			}else {
				pqueue.offer(num);
			}
		}
		
		System.out.println(sb);
		
	}
	
}
