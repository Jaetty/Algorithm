package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class SW_암호문1_1228 {
	
	static int N, M;
	static List<String> list = new LinkedList<>();
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		for(int t=1; t<=10; t++) {
			// 초기화
			list.clear();
			
			N = Integer.parseInt(br.readLine()); // 첫째 줄
			StringTokenizer st = new StringTokenizer(br.readLine()); // 둘째 줄
			for(int i=0; i < N; i++) {
				list.add(st.nextToken());
			}
			M = Integer.parseInt(br.readLine()); // 셋째 줄
			st = new StringTokenizer(br.readLine()); // 넷째 줄
			for(int i=0; i<M; i++) {
				st.nextToken(); // I 명령어
				int x = Integer.parseInt(st.nextToken()); // x : index 
				int y = Integer.parseInt(st.nextToken()); // x : index
				// x의 위치에 y개 만큼 문자열 추가
				int count = x+y;
				for (int j=x; j < count; j++) {
					list.add(j, st.nextToken());
				}
			}
			sb.append("#").append(t).append(" ");
			for(int i=0; i<10; i++) {
				sb.append(list.get(i)).append(" ");
			}
			sb.append("\n");
		}
		
		System.out.println(sb);
	}
}