package basic;

import java.util.Arrays;

public class BASIC_Subset { // 부분집합
	
	static int COUNT = 0;
	static int[] src = {1,2,3,4,5};
	// 					O O ... 
	// 					O X ...
	// 					X O ...
	// 					X X ...
	// 각 자리수 (각 index) 의 수를 선택 비선택 모두 따져주면 된다. <= select
	// tgt 없다.
	
	static boolean[] select = new boolean[src.length];
	
	public static void main(String[] args)throws Exception{
		subset(0); // 앞 src, 뒤 tgt
		System.out.println(COUNT);
	}
	
	static void subset(int srcIdx) {
		// 기저 조건
		if(srcIdx == src.length) {
			// complete cod <= select 배열의 현재 상태
			printSubset();
			COUNT++;
			return;
		}
		select[srcIdx] = true; // 선택
		subset(srcIdx+1); // 다음 선택
		
		select[srcIdx] = false; // 비선택
		subset(srcIdx+1); // 다음 선택
	}
	
	static void printSubset() {
		System.out.print("{");
		for(int i=0; i<select.length; i++) {
			if(select[i]) System.out.print(src[i]+ " ");
		}
		System.out.print("}");
		System.out.println();
	}
	
}