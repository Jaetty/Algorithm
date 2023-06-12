package basic;

import java.util.Arrays;

// binarySerach 이전에 반드시 search 의 대상이 되는 배열 (Collection) 이 정렬되어 있어야 한다.
public class BASIC_BinarySearch {
	
	public static void main(String[] args) {
//		int[] input = {1,5,7,10,13};
		
//		System.out.println( Arrays.binarySearch(input, 1));
//		System.out.println( Arrays.binarySearch(input, 4)); // 음수 -2 : 단순히 못찾았다가 아니고 만약에 찾았다면 어느 위치에 와야 한다.
//		System.out.println( Arrays.binarySearch(input, 8));
//		System.out.println( Arrays.binarySearch(input, 0));
		
		// {     1,          5,       7,        10,         13       }
		//       0           1        2         3           4
		//  [-1]      [-2]      [-3]      [-4]       [-5]      [-6] <= 못 찾았을 때 리턴해주는 값의 위치
		
//		System.out.println( Arrays.binarySearch(input, 0, 3, 12));
		
		// {      1,          5,        7,       -...   }
		//        0           1         2         
		//  [-1]      [-2]       [-3]       [-4]     <= 못 찾았을 때 리턴해주는 값의 위치
		
//		System.out.println( Arrays.binarySearch(input, 2, 4, 8));
//		System.out.println( Arrays.binarySearch(input, 2, 4, 4));
//		System.out.println( Arrays.binarySearch(input, 2, 4, 1));
//		System.out.println( Arrays.binarySearch(input, 2, 4, 13));
		// {     -,          -,       7,       10,         - }
		//       0           1        2         3          4
		//  [-1]      [-2]      [-3]      [-4]      [-5]   <= 못 찾았을 때 리턴해주는 값의 위치
		
		
		Node[] list = {new Node(1,3), new Node(5,2), new Node(3,7), new Node(2,4)};
		Arrays.sort(list);
		System.out.println(Arrays.toString(list));
		
		// [Node [y=1, x=3], Node [y=2, x=4], Node [y=3, x=7], Node [y=5, x=2]]
		//      0                   1                 2                3
		//  -1             -2               -3               -4               -5
		
		System.out.println(Arrays.binarySearch(list, new Node(3,6)));
		
		
		
		
		Arrays.sort(list, (n1, n2) -> n1.y - n2.y);
		System.out.println(Arrays.toString(list));
//		[Node [y=1, x=3], Node [y=2, x=4], Node [y=3, x=7], Node [y=5, x=2]]
//		        0                1                2                 3
//		  -1            -2               -3                -4               -5
		System.out.println(Arrays.binarySearch(list, new Node(3,8)));
	}
	
	static class Node implements Comparable<Node>{
		int y,x;
		Node(int y, int x){
			this.y = y;
			this.x = x;
		}
		
		@Override
		public String toString() {
			return "Node [y=" + y + ", x=" + x + "]";
		}
		
		@Override
		public int compareTo(Node o) {
			return this.y - o.y;
		}
		
	}
}