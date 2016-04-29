package GeneticAlgorithm;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Greedy {
	public static void greedy_approach(int [][] adj){
		
 	
}
	private static int get_max_degree_of_an_array(int [][] adj){
	    	int max_degree = 0;
	    	int temp = 0 ;
	    	
	    	for(int i = 0 ; i < adj.length ; i++){
	    		for(int j = 0 ; j < adj[0].length ; j++)
	    		{
	    			if(adj[i][j] == 1)
	    				temp++;
	    		}
	    		if(max_degree < temp)
	    			max_degree = temp;
	    		temp = 0;    			
	    				
	    	}
	    	//JOptionPane.showMessageDialog(null, "Max_degree " + max_degree);
	    	return max_degree;
	    }
	
}
