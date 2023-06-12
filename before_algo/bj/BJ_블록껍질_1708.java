package bj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class BJ_블록껍질_1708 {
	static int N;
	static long sx, sy;
	static long[][] point; // point[3][0] 4번째 x 좌표의 수
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		point = new long[N][2];
		
		// point 배열에 입력 저장
		
		sx = 9000;
		sy = 9000;
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			point[i][0] = Integer.parseInt(st.nextToken());
			point[i][1] = Integer.parseInt(st.nextToken());
			
			if(point[i][1]<sy) {
				sy = point[i][1];
				sx = point[i][0];
			}
			else if(point[i][1]==sy && sx>point[i][0]) {
				sx = point[i][0];
			}
		}
		
		// 시작점 sx, sy 지정 ( 맨 앞의 점을 시작점으로)
		// 시작점을 y가 가장 작은 점, y가 같은 점이 있다면 x가 가장 작은 점 계산 => sy, sx 로
		
		// point 배열을 반시계 방향으로 정렬
//		Arrays.sort(point, (p1, p2) -> {
//			int ret = ccw( sx, sy, p1[0], p2[0], p2[0], p2[1]);
//			
//			if(ret>0) {
//				return -1;
//			}
//			else if(ret<0){
//				return 1;
//			}
//			else {
//				long diff = distance(sx,sy,p1[0],p2[0]) - distance(sx, sy, p2[0],p2[1];)
//				return diff>0?1:-1;
//			}
//		});
		
		
		Arrays.sort(point, (e1,e2)->{
			int ret = ccw(sx,sy,e1[0],e1[1],e2[0],e2[1]);
			if(ret>0) {
				return -1;
			}
			else if(ret<0){
				return 1;
			}
			else {
				long diff = distance(sx,sy,e1[0],e1[1]) - distance(sx, sy, e2[0],e2[1]);
				return diff>0?1:-1;
			}
		});
		
		// Stack 객체 생성
		// 시작점을 Stack 에 넣는다.
		Stack<long[]> stack = new Stack<>();
		stack.add(new long[] {sx,sy});
		
		// 시작점을 제외한 모든 점을 순회
		// for 문을 이용해서 각 i 점에 대해서
		//	stack 에 들어가 있는 이전 2개의 점과 i점과의 ccw를 확인해서
		//	 반시계방향이 아니면 계속 꺼낸다.(반복적으로)
		//	 => stack 에 i 점을 넣는다.
		
		for(int i=1; i<N; i++) {
			while(stack.size()>1 && ccw(stack.get(stack.size()-2)[0], stack.get(stack.size()-2)[1]
					,stack.get(stack.size()-1)[0], stack.get(stack.size()-1)[1]
					,point[i][0],point[i][1]) <= 0) {
				stack.pop();
			}
			
			stack.add(point[i]);
		}
		
		// stack 에 들어가 있는 점들이 바로 블록껍질을 형성한다.
		
		System.out.println(stack.size());
		
	}
	
	static int ccw(long x1, long y1, long x2, long y2, long x3, long y3) {
		int ret;
		// ret 는 양수 음수 여부를 따져서 반시계방향(1), 시계방향(-1) 여부를 리턴, 같으면 (0)
		long a = x1*y2 + x2*y3 + x3*y1 - (x2*y1 + x3*y2 + x1*y3);
		
		if(a>0)ret = 1;
		else if(a<0) ret = -1;
		else ret = 0;
		
		return ret;
	}
	
	static long distance(long x1, long y1, long x2, long y2) {
		// 맨하튼 거리를 계산해서 return 
		return Math.abs(x2-x1) + Math.abs(y2-y1);
	}

}
