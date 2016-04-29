package GeneticAlgorithm;

import static GeneticAlgorithm.geneticAlg.*;

import java.util.ArrayList;

import Animation.animation;
import GeneticAlgorithm.Nodes;
import Graph.GraphTh;


public class population {
	static int number_of_pop;
	static Nodes [] nodes;
	static int [] best_individual;
	static int [][] pop;
	static int fitness [];
	static int temp_pop [][];
	public static boolean  isGreedy = false;
	public static boolean  isGreedywithGenetic = false;
	public static boolean  isGenetic = false;


	public population(int number_of_pop,int number_of_nodes){
		population.number_of_pop = number_of_pop;
		nodes = new Nodes[number_of_nodes];
		for(int i = 0 ; i < number_of_nodes ; i++)
			nodes[i] = new Nodes();
		pop = new int[number_of_pop][number_of_nodes];
		best_individual = new int[number_of_nodes];

	}	
	public static void initialization(){
	      
    /*
    	for(int i = 0 ; i < number_of_pop ; i++){
    		fitness[i] = get_fitness_number_of_individual(pop[i]);
    		System.out.print(i+".birey :"+fitness[i]);
    		for(int j = 0; j < pop[0].length ; j++)
    			System.out.print(" " +pop[i][j]);
    		System.out.println(" ");
    	}
	   */ 
	    
	    //show_adjacency_List();
		if(isGreedy){
			int adj [][] = new int[pop[0].length][pop[0].length];
		    int res = greedy_approach(pop);	  
		    for (int i = 0; i < nodes.length; i++) {
				for (int j = 0; j < nodes[i].adjacents.size(); j++) {
					adj[i][nodes[i].adjacents.get(j)] = 1;
				}
			}
		  /*  for(int i = 0 ; i < node_number ; i++)
		    {
		    	for(int j = 0 ; j < node_number ; j++){
		    		System.out.print(adj[i][j] + "  ");
		    	}
		    	System.out.println(" ");
		    }
		    */
		    
			GraphTh.text_area_left_side.setText(GraphTh.text_area_left_side.getText()+"\n"
					+"Greedy used totally \n " + (res) + " colors for given graph");
			
			animation.animate(adj,pop[0],"Greedy");
			animation.main(null);
		}
		if(isGenetic){
			int max_degree = get_max_degree_of_the_population();
		    for(int i = 0 ; i < pop.length ; i++){
	    		///System.out.print(i + ")");
	    		for(int j = 0 ; j < pop[0].length ; j++)
	    		{  
	    			pop[i][j] = (int)(Math.random()*max_degree);
	    			//System.out.print(pop[i][j] + " ");
		    	}
		    	//System.out.println(  " " + get_fitness_number_of_individual(pop[i]) + " ");
	    	}
			
		}
		if(isGreedywithGenetic){
			greedy_approach(pop);
			int max_degree = get_max_degree_of_the_population();
		    for(int i = 1 ; i < pop.length ; i++){
	    		///System.out.print(i + ")");
	    		for(int j = 0 ; j < pop[0].length ; j++)
	    		{  
	    			pop[i][j] = (int)(Math.random()*max_degree);
	    			//System.out.print(pop[i][j] + " ");
		    	}
		    	//System.out.println(  " " + get_fitness_number_of_individual(pop[i]) + " ");
	    	}
		    int adj [][] = new int[pop[0].length][pop[0].length];
		    for (int i = 0; i < nodes.length; i++) {
				for (int j = 0; j < nodes[i].adjacents.size(); j++) {
					adj[i][nodes[i].adjacents.get(j)] = 1;
				}
			}		    
		}
	    best_individual = pop[0];
    	temp_pop = new int[number_of_pop][node_number];
    	fitness = new int[number_of_pop];
    	for(int i = 0 ; i < number_of_pop ; i++){
    		fitness[i] = get_fitness_number_of_individual(pop[i]);
    		//System.out.println(i+".birey :"+fitness[i]);
    	}
	}
	public static int get_max_degree_of_the_population(){
		int max = 0;
		for(int i = 0 ; i < nodes.length ; i++){
			if(nodes[i].adjacents.size() > max){
				max = nodes[i].adjacents.size();
			}
		}
		return max;
	}
 	public static int get_fitness_number_of_individual(int [] individual){
 		int violation = 0;
 		int total_color = number_of_unique_elements(individual);
 	   	for(int i = 0 ; i < node_number ; i++){
    		for(int j = i + 1 ; j < node_number ; j++){
    			if((nodes[i].adjacents.contains(j)) && (i != j) && (individual[i] == individual[j]))
    			  violation++;	    			
    		}
    	}   		
 	   if(violation == 0)
   		return total_color;
 	   else
   		return total_color * 1000;
 	
 	}
    public static int number_of_unique_elements(int [] individual){
    	ArrayList<Integer> temp_list = new ArrayList<Integer>();
    	for(int i = 0 ; i < individual.length ; i++ ){
    		if(!temp_list.contains(individual[i]))
    			temp_list.add(individual[i]);
    	}
    	return temp_list.size();
    }
/*	public static void show_adjacency_List(){
		text_area_right_side.setText(text_area_right_side.getText()+"\n");
		for(int i = 0 ; i < population.nodes.length ; i++){
    		text_area_right_side.setText(text_area_right_side.getText()+i+"-->");
		    text_area_right_side.setCaretPosition(text_area_right_side.getDocument().getLength());
	    			for(int j = 0 ; j < population.nodes[i].adjacents.size() ; j++){
    			//System.out.print(population.nodes[i].adjacents.get(j) + " ");
    		    text_area_right_side.setText(text_area_right_side.getText()+" "+population.nodes[i].adjacents.get(j));
    		    text_area_right_side.setCaretPosition(text_area_right_side.getDocument().getLength());
			}   		

		    text_area_right_side.setText(text_area_right_side.getText()+"\n");
	    	text_area_right_side.setCaretPosition(text_area_right_side.getDocument().getLength());
    		//System.out.println(" ");	    		
    	} 
	}*/
    static int greedy_approach(int [][] pop){
    	ArrayList<Integer> color = new ArrayList<>();
    	ArrayList<Integer> color2 = new ArrayList<>();
    	ArrayList<Integer> color2temp = new ArrayList<>();
    
    	for(int u = 0 ; u < get_max_degree_of_the_population() ; u++)
    		color2temp.add(u);
    	
	//	System.out.print("greedy started \n");
	/*	for(int i = 0 ; i < population.number_of_pop ; i++){
    		for(int j = 1 ; j < node_number ; j++){
    			pop[i][j] = -1;
    		}
    	}
		*/
    	for(int i = 0 ; i < node_number; i++){    		
    		for(int j = 0 ; j < nodes[i].adjacents.size(); j++){
    			if(nodes[nodes[i].adjacents.get(j)].color != -1)
	    			{
	    				color.add(nodes[nodes[i].adjacents.get(j)].color);
	    				//System.out.println("node " + i + " icin " + nodes[nodes[i].adjacents.get(j)].color + " eklendi");
	    			}	    				    			
	    		}
	    		
	    		for(int u = 0 ; u < color.size() ; u++){
	    			if(color2temp.contains(color.get(u))){
	    				color2temp.remove(color.get(u));
	    			}
	    		}
	    		nodes[i].color = get_minumum_color(color2temp);
	    		color2.add(nodes[i].color);
	    		color2temp.clear();
	    		color.clear();
    			
	    		for(int u = 0 ; u < get_max_degree_of_the_population() ; u++)
	    			color2temp.add(u);
    	}
    	
    	/*for(int u = 0 ; u < color2.size() ; u++){
    		System.out.println(u + " )" + color2.get(u));
		}	 
    	*/
    	for(int u = 0 ; u < color2.size(); u++){
    		pop[0][u] = color2.get(u);
    	}
   /* 	System.out.println("\nNoUE using greedy approach is " + number_of_unique_elements(pop[0]));
    	text_area_right_side.setText(text_area_right_side.getText() 
    			+ "\nNoUE using greedy is " + number_of_unique_elements(pop[0]));
	    text_area_right_side.setCaretPosition(text_area_right_side.getDocument().getLength());*/
	    return number_of_unique_elements(pop[0]);

    }
 	public static int get_minumum_color(ArrayList<Integer> list){
		int min = 1000000;
		for(int i = 0 ; i < list.size(); i++){
			if ( (int)list.get(i) < min)
				min = (int)list.get(i);
		}
		return min;
	}
 	
 	
}
