package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_DNA비밀번호_12891_2 {
	static int P, S, ans;
	static int minA,minC,minG,minT;
	static char[] dna;
	// A B C D E F G H I J K   L  M  N  O P  Q   R   S  T  /U  V W  X  Y  Z
	// 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17  18 19 /20 21 22 23 24 25
	static int[] cnt = new int[20];
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		S =  Integer.parseInt(st.nextToken());
		P =  Integer.parseInt(st.nextToken());
		dna = br.readLine().toCharArray();
		
		st = new StringTokenizer(br.readLine());
		minA = Integer.parseInt(st.nextToken());
		minC = Integer.parseInt(st.nextToken());
		minG = Integer.parseInt(st.nextToken());
		minT = Integer.parseInt(st.nextToken());
		
		
		solve();
		System.out.println(ans);
	}
	
	static void solve() {
		for(int i=0; i<P; i++) cnt[(int)dna[i]-'A']++;
		
		check();
		
		for(int i=P; i<S; i++) {
			
			cnt[(int)dna[i-P]-'A']--;
			cnt[(int)dna[i]-'A']++;
			check();
		}
	}
	
	static void check() {
		if(cnt[0]>=minA && cnt[2]>=minC && cnt[6]>=minG && cnt[19]>=minT) ans++;
	}
}