package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_DNA비밀번호_12891 {
	static int cntA,cntC,cntG,cntT, P, S;
	static int minA,minC,minG,minT, ans;
	static char[] dna;

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
		for(int i=0; i<P; i++) {
			switch(dna[i]) {
			case 'A': cntA++; break;
			case 'C': cntC++; break;
			case 'G': cntG++; break;
			case 'T': cntT++; break;
			}
		}
		
		check();
		
		for(int i=P; i<S; i++) {
			switch(dna[i-P]) {
				case 'A': cntA--; break;
				case 'C': cntC--; break;
				case 'G': cntG--; break;
				case 'T': cntT--; break;
			}
			switch(dna[i]) {
				case 'A': cntA++; break;
				case 'C': cntC++; break;
				case 'G': cntG++; break;
				case 'T': cntT++; break;
			}
			check();
		}
	}
	
	static void check() {
		if(cntA>=minA && cntC>=minC && cntG>=minG && cntT>=minT) ans++;
	}
}