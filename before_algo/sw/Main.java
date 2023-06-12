package sw;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		
		int[] arr = new int[n+1];
		arr[0] = 0;
		
		for(int i=1; i<=n; i++) {
			arr[i] = Integer.parseInt(st.nextToken())+arr[i-1];
		}
		
		while(m-->0) {
			String[] str = br.readLine().split(" ");
			sb.append(arr[ Integer.parseInt(str[1])  ]-arr[ Integer.parseInt(str[0])-1 ]+"\n");
		}
		
		System.out.println(sb);
	}

}
