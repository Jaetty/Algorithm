package sw;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;

class Solution{
    public static char[][] c_arr;
    static int position1, position2, row , col, direction;
    static int[] row_shoot = {0,0,1,-1};
    static int[] col_shoot = {-1,1,0,0};
    
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        position1 =0; position2 = 0;
        int T = Integer.parseInt(br.readLine());
        
        for(int tc=1; tc<=T; tc++) {
        	String[] s = br.readLine().split(" ");
        	row = Integer.parseInt(s[0]);
        	col = Integer.parseInt(s[1]);
        	
        	c_arr = new char[row][col];
        	String tmp;
        	for(int i=0; i<row; i++) {
        		tmp = br.readLine();
        		if(tmp.contains("<") || tmp.contains("^")|| tmp.contains("v") || tmp.contains(">")) {
        			for(int j=0; j<tmp.length(); j++) {
        				c_arr[i][j] = tmp.charAt(j);
        				if(c_arr[i][j] == '<' || c_arr[i][j] == '^' || c_arr[i][j] == 'v' || c_arr[i][j] == '>') {
        					switch(c_arr[i][j]) {
        					case '<':
        						direction = 0;
        						break;
        					case '>':
        						direction = 1;
        						break;
        					case '^':
        						direction = 3;
        						break;
        					case 'v':
        						direction = 2;
        						break;
        					}
        					position1 = i;
        					position2 = j;
        				}
        			}
        		}
        		else c_arr[i] = tmp.toCharArray();
        	}
        	
        	br.readLine();
        	
        	tmp = br.readLine();
        	for(int i=0; i<tmp.length(); i++) {
        		order(tmp.charAt(i));
        		
        	}
        	sb.append("#"+tc+" ");
        	for(int i=0; i<row; i++) {
        		for(int j=0; j<col; j++) {
        			sb.append(c_arr[i][j]);
        		}
        		sb.append("\n");
        	}
        	
        	
        }
        System.out.println(sb);
  
    }
    
    static void order(char command) {
    	switch(command) {
    	case 'U':
    		direction = 3;
    		c_arr[position1][position2] = '^';
    		if(position1-1 >= 0) {
    			if(c_arr[position1-1][position2]=='.') {
    				c_arr[position1][position2] = '.';
    				position1--;
    				c_arr[position1][position2] = '^';
    			}
    		}
    		break;
    	case 'D':
    		direction = 2;
    		c_arr[position1][position2] = 'v';
    		if(position1+1 < row) {
    			if(c_arr[position1+1][position2]=='.') {
    				c_arr[position1][position2] = '.';
    				position1++;
    				c_arr[position1][position2] = 'v';
    			}
    		}
    		break;
    	case 'L':
			direction = 0;
			c_arr[position1][position2] = '<';
    		if(position2-1 >= 0) {
    			if(c_arr[position1][position2-1]=='.') {
    				c_arr[position1][position2] = '.';
    				position2--;
    				c_arr[position1][position2] = '<';
    			}
    		}
    		break;
    	case 'R':
    		direction = 1;
    		c_arr[position1][position2] = '>';
    		if(position2+1 < col) {
    			if(c_arr[position1][position2+1]=='.') {
    				c_arr[position1][position2] = '.';
    				position2++;
    				c_arr[position1][position2] = '>';
    			}
    		}
    		break;
    	case 'S':
    		int nr=position1,nc=position2;
    		while(true) {
    			nr+=row_shoot[direction];
    			nc+=col_shoot[direction];
    			
    			if(nr<0 || nc<0 || nr>=row || nc >=col) break;
    			
    			if(c_arr[nr][nc]=='#') break;
    			
    			if(c_arr[nr][nc]=='*') {
    				c_arr[nr][nc] = '.';
    				break;
    			}
    			
    		}
    		
    		break;
    	}
    }
    
}