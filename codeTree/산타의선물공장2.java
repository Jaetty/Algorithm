import java.util.*;
import java.io.*;

public class Main {
	
	static class Node{
		Node prev, next;
		int id;
	}
	
	static Node[] every;
	static Node[] first;
	static Node[] end;
	static int[] stackSize;
	
	static int N, M, q;
	static StringBuilder sb;
	
    public static void main(String[] args) throws Exception {
        
    	sb = new StringBuilder();
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	q = Integer.parseInt(br.readLine());
    	
    	for(int i=0; i<q; i++) {
    		StringTokenizer st = new StringTokenizer(br.readLine());
    		
    		int cmd = Integer.parseInt(st.nextToken());
    		
    		switch(cmd) {
    			case 100: init(st); break;
    			case 200 : {
    				moveEveryThing(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    				break;
    			}
    			case 300 : {
    				firstChange(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    				break;
    			}
    			case 400 : {
    				divide(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
    				break;
    			}
    			case 500 : {
    				getInfo(every[Integer.parseInt(st.nextToken())]);
    				break;
    			}
    			case 600 : {
    				getBeltInfo(Integer.parseInt(st.nextToken()));
    				break;
    			}
    		}
    		
    	}
    	System.out.print(sb);
    	
    	
    }
    
    static void init(StringTokenizer st) {
    	N = Integer.parseInt(st.nextToken());
    	M = Integer.parseInt(st.nextToken());
    	
    	every = new Node[M+1];
    	first = new Node[N+1];
    	end = new Node[N+1];
    	stackSize = new int[N+1];
    	
    	for(int i=1; i<=N; i++) {
    		first[i] = new Node();
    		end[i] = new Node();
    		first[i].id = -1;
    		end[i].id = -1;
    		
    		first[i].next = end[i];
    		end[i].prev = first[i];
    	}
    	
    	int id = 1;
    	
    	while(st.hasMoreTokens()) {
    		int tmp = Integer.parseInt(st.nextToken());
    		stackSize[tmp]++;
    		Node node = new Node();
    		node.id = id;
    		
    		node.prev = end[tmp].prev;
    		node.next = end[tmp];
    		
    		end[tmp].prev.next = node;
    		end[tmp].prev = node; 
    		every[id++] = node;
    		
    	}
    }
    
    static void moveEveryThing(int src, int dest) {
    	
    	if(stackSize[src]==0) {
    		sb.append(stackSize[dest]).append("\n");
    		return;
    	}
    	
    	int size = stackSize[src];
    	stackSize[src] = 0;
    	
    	Node srcFirstNext = first[src].next;
    	Node srcEndPrev = end[src].prev;
    	
    	srcFirstNext.prev = null;
    	srcEndPrev.next = null;

    	Node destFirstNext = first[dest].next;
    	
    	destFirstNext.prev = null;
    	
    	srcFirstNext.prev = first[dest];
    	first[dest].next = srcFirstNext;
    	
    	srcEndPrev.next = destFirstNext;
    	
    	destFirstNext.prev = srcEndPrev;
    	
    	stackSize[dest] += size;
    	
    	first[src].next = end[src];
    	end[src].prev = first[src];
    	
    	sb.append(stackSize[dest]).append("\n");
    }
    
    
    static void firstChange(int src, int dest) {
    	
    	Node srcFirst = first[src].next;
    	Node destFirst = first[dest].next;
    	
    	remove(srcFirst, src);
    	remove(destFirst, dest);
    	
    	if(srcFirst.id != -1) {
    		
    		srcFirst.next = first[dest].next;
    		srcFirst.prev = first[dest];
    		
    		first[dest].next.prev = srcFirst;
    		first[dest].next = srcFirst;
    		
    		stackSize[dest]++;
    		
    	}
    	
    	if(destFirst.id != -1) {
    		
    		destFirst.next = first[src].next;
    		destFirst.prev = first[src];
    		
    		first[src].next.prev = destFirst;
    		first[src].next = destFirst;
    		
    		stackSize[src]++;
    		
    	}
    	
    	sb.append(stackSize[dest]).append("\n");
    }
    
    static void divide(int src, int dest) {
    	if(stackSize[src] <= 1) {
    		sb.append(stackSize[dest]).append("\n");
    		return;
    	}
    	
    	int halfSize = stackSize[src]/2;
    	
    	Node firstNode = first[src].next;
    	Node endNode = first[src];
    	
    	for(int i=0; i<halfSize; i++) {
    		endNode = endNode.next;
    	}
    	
    	endNode.next.prev = first[src];
    	first[src].next = endNode.next;
    	
    	firstNode.prev = null;
    	
    	Node destFirst = first[dest].next;
    	
    	destFirst.prev = endNode;
    	endNode.next = destFirst;
    	
    	firstNode.prev = first[dest];
    	first[dest].next = firstNode;
    	
    	stackSize[src] -= halfSize;
    	stackSize[dest] += halfSize;
    	
    	sb.append(stackSize[dest]).append("\n");
    	
    }
    
    static void getInfo(Node node) {
    	
    	int a = node.prev.id;
    	int b = node.next.id;
    	
    	sb.append(a + (2*b)).append("\n");
    	
    }
    
    static void getBeltInfo(int src) {
    	int a = first[src].next.id;
    	int b = end[src].prev.id;
    	int c = stackSize[src];
    	
    	sb.append(a+(2*b)+(3*c)).append("\n");
    	
    }
    
    static void remove(Node node, int size) {
    	if(node.id==-1) return;
    	
    	node.prev.next = node.next;
    	node.next.prev = node.prev;
    	node.next = null;
    	node.prev = null;
    	stackSize[size]--;
    }
    
}