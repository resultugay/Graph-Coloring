package GeneticAlgorithm;

import static GeneticAlgorithm.geneticAlg.*;
import static GeneticAlgorithm.population.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Animation.animation;
import Graph.GraphTh;



public class start{
	static int generation_number = 100;
  public static void run(int [][] adj){
		for(int i = 0 ; i < generation_number ; i++){
			//selection(pop, 70);
			//cross_over(pop);
			//mutation(pop);
			selection2(pop, 20, 10);
			cross_over(pop);
			mutation(pop);
			//System.out.println("fitness " + i + " " +get_fitness_number_of_individual(pop[0]));

		}
    //	System.out.println(  " " + get_fitness_number_of_individual(pop[0]) + " ");
		if(get_fitness_number_of_individual(pop[0]) >= 1000){
			GraphTh.text_area_left_side.setText(GraphTh.text_area_left_side.getText()+"\n"
					+"Genetic failed for given graph");
			int max_degree = get_max_degree_of_the_population();
	    	//System.out.println( max_degree);
		    for(int i = 0 ; i < pop.length ; i++){
	    		///System.out.print(i + ")");
	    		for(int j = 0 ; j < pop[0].length ; j++)
	    		{  
	    			pop[i][j] = (int)(Math.random()*max_degree);
	    			//System.out.print(pop[i][j] + " ");
		    	}
		    //	System.out.println(  " " + get_fitness_number_of_individual(pop[i]) + " ");
	    	}
		}
		
	
		int [] adj2 = new int[pop[0].length];
	/*	ArrayList ajdar = new ArrayList<>();
		int counter = 0;
		for (int i = 0; i < pop[0].length; i++) {
			ajdar.add(pop[0][i]);
		}
		adj2[0] = 0;*/
		 HashMap hm = new HashMap();
		/*for (int i = 0; i < pop[0].length; i++) {
			System.out.print(" "+pop[0][i]);
		}
		 */		
		 hm.put(pop[0][0],0); // onemli
		for (int i = 1; i < adj2.length; i++) {
			for (int j = 0; j < i ; j++) {
				if(pop[0][i] != pop[0][j]){
					hm.put(pop[0][i], 0);
					}
			}
		}	
		

		 Set set = hm.entrySet();
		 Iterator a = set.iterator();
		 int as = 0;
	      // Display elements
	      while(a.hasNext()) {
	         Map.Entry me = (Map.Entry)a.next();
	       //  System.out.print(me.getKey() + ": ");
	       //  System.out.println(me.getValue());
	         me.setValue(as++);
	      }
	      
	      Set set2 = hm.entrySet();
	      Iterator iterator2 = set2.iterator();
	      while(iterator2.hasNext()) {
	          Map.Entry mentry2 = (Map.Entry)iterator2.next();
	          //System.out.print("Key is: "+mentry2.getKey() + " & Value is: ");
	          //System.out.println(mentry2.getValue());
	       }
	      
	      for (int i = 0; i < adj2.length; i++) {
	    	  adj2[i] = (int) hm.get(pop[0][i]);
	      } 
	  	/*System.out.println("");

		for (int i = 0; i < pop[0].length; i++) {
			System.out.print(" "+adj2[i]);
		}*/
	      
	      int max1 = 0;
			for (int i = 0; i < adj2.length; i++) {
				if(adj2[i] > max1)
					max1 = adj2[i];
			}

	    if(isGenetic){   
		GraphTh.text_area_left_side.setText(GraphTh.text_area_left_side.getText()+"\n"
				+"Genetic used totally \n " + (max1 + 1) + " colors for given graph");
		animation.animate(adj,adj2,"Genetic");
		animation.main(null);
	    }else if(isGreedywithGenetic){

			GraphTh.text_area_left_side.setText(GraphTh.text_area_left_side.getText()+"\n"
					+"Genetic with Greedy used totally \n " + (max1 + 1) + " colors for given graph");
		animation.animate(adj,adj2,"Genetic with Greedy");
		animation.main(null);
	    }
  }
  public static int number_of_unique_elements(int [] individual){
  	ArrayList<Integer> temp_list = new ArrayList<Integer>();
  	for(int i = 0 ; i < individual.length ; i++ ){
  		if(!temp_list.contains(individual[i]))
  			temp_list.add(individual[i]);
  	}
  	return temp_list.size();
  }
  private static void selection2(int [][] pop,int ratio_rand,int ratio_eiga){
	  System.arraycopy( pop, 0, temp_pop, 0, pop.length );
  	 /* System.out.println("Before Selection");
	  for(int i = 0 ; i < temp_pop.length ; i++){
  		System.out.print(i + ")");
  		for(int j = 0 ; j < temp_pop[0].length ; j++)
  		{  
  			System.out.print(temp_pop[i][j] + " ");
	    	}
	    	System.out.println(  " " + get_fitness_number_of_individual(temp_pop[i]) + " ");
  	   }
	  */
	  int best_fitt = choose_first_individual(temp_pop);
	  population.best_individual = temp_pop[0];
	  random_selection2(temp_pop, ratio_rand, best_fitt);
	  temp_pop[0] = population.best_individual.clone() ;
	  eiga_selection2(temp_pop, ratio_eiga,best_fitt);
	  temp_pop[0] = population.best_individual.clone() ;
	  System.arraycopy( temp_pop, 0, pop, 0, pop.length );
	  tournament_selection2(temp_pop);
	  System.arraycopy( temp_pop, 0, pop, 0, pop.length );
	  /*
	  for(int i = 0 ; i < 20 ; i++){
	  		System.out.print(i + ")");
	  		for(int j = 0 ; j < temp_pop[0].length ; j++)
	  		{  
	  			System.out.print(temp_pop[i][j] + " ");
		    }
		    	System.out.println(  " " + get_fitness_number_of_individual(temp_pop[i]) + " ");
	  }	 
	   */
  }
  public static void tournament_selection2(int [][] pop){
	    int first_individual ;
	  	int second_individual;
	  	for(int i = 1 ; i < number_of_pop ; i++){
	      	 first_individual = 1+(int)(Math.random()*(number_of_pop -  1));
	      	 while(true){
	      	 second_individual = 1+(int)(Math.random()*(number_of_pop - 1));
	      	 if(first_individual != second_individual) 
	      		 break;
	      	 }
	    //   	System.out.println(i + ") first : " + first_individual + " second :" + second_individual);
	  		if(get_fitness_number_of_individual(pop[first_individual]) < get_fitness_number_of_individual(pop[second_individual])){
	  			temp_pop[i] = pop[first_individual].clone();
	  		}
	  		else 
	  			temp_pop[i] = pop[second_individual].clone();
	  	}
	 /* 	System.out.println("tornament'ten sonra");
		  for(int i = 0 ; i < 20 ; i++){
		  		System.out.print(i + ")");
		  		for(int j = 0 ; j < temp_pop[0].length ; j++)
		  		{  
		  			System.out.print(temp_pop[i][j] + " ");
			    }
			    	System.out.println(  " " + get_fitness_number_of_individual(temp_pop[i]) + " ");
		  }	 */
  }
  public static void eiga_selection2(int [][] pop,int ratio_eiga,int best_fit){
	 double pm = 0.8;
	 for(int i = 0 ; i <  (pop.length*ratio_eiga)/100 ; i++){
		 int [] temp_ind = new int[node_number];
		 temp_ind = population.best_individual.clone();
		 int color = 0;
		 int ind = (int)  (Math.random()* number_of_pop);
		for(int u = 0; u < (node_number*30)/100 ; u++) {
		 double p = Math.random();
		 if(p < pm){
			 int gene = (int) (Math.random()*node_number);
			 if(best_fit < 1000)
			  color = (int) (Math.random() * best_fit);
			 else
			  color = (int) (Math.random() * population.get_max_degree_of_the_population());

			 temp_ind[gene] = color;
			 temp_pop[ind] = temp_ind;
			// System.out.print("eiga ile " + ind + ".birey) " + (gene + 1) + ".gen degisti " + color + " olarak " );
			/* for(int j = 0 ; j < temp_pop[0].length ; j++)
		  		{  
		  			System.out.print(" " + temp_pop[ind][j]);
			    }
			 System.out.println(" ");*/
		 }
		}
		 		 
	 }
	/* System.out.println("eiga'dan sonra");
	  for(int i = 0 ; i < 20 ; i++){
	  		System.out.print(i + ")");
	  		for(int j = 0 ; j < temp_pop[0].length ; j++)
	  		{  
	  			System.out.print(temp_pop[i][j] + " ");
		    }
		    	System.out.println(  " " + get_fitness_number_of_individual(temp_pop[i]) + " ");
	  }	 */
  }
  public static void random_selection2(int [][] pop,int ratio_rand,int best_fitness){
	  
	  for(int i = 0 ; i < (pop.length*ratio_rand)/100 ; i++){
		int rand1 = (int)(Math.random()* (population.number_of_pop - 1)) + 1;
			//System.out.print(rand1 + ")");
  		for(int j = 0 ; j < pop[0].length ; j++)
  		{  
  			if(best_fitness < 1000)	  			
  			 pop[rand1][j] = (int)(Math.random()*best_fitness);
  			else
  	  		 pop[rand1][j] = (int)(Math.random()*population.get_max_degree_of_the_population());

  		//	System.out.print(pop[rand1][j] + " ");
	    }
	    //	System.out.println(  " " + get_fitness_number_of_individual(pop[rand1]) + " ");
  	 }	  
	  
	 /*System.out.println("random'dan sonra");
	  for(int i = 0 ; i < 20 ; i++){
	  		System.out.print(i + ")");
	  		for(int j = 0 ; j < temp_pop[0].length ; j++)
	  		{  
	  			System.out.print(temp_pop[i][j] + " ");
		    }
		    	System.out.println(  " " + get_fitness_number_of_individual(temp_pop[i]) + " ");
	  }	 */
  }
  private static void selection(int [][] pop,int ratio_tournament){
	  	choose_first_individual(pop);
	  	System.out.println("Ratio : %" + ratio_tournament);
	  	int ratio = (number_of_pop*ratio_tournament)/100;
	  	tournament_selection(pop, ratio);
	  	randomly_selection(ratio);
	  /*	for(int i = 0 ; i < pop.length ; i++){
    		System.out.print(i + ")");
    		for(int j = 0 ; j < pop[0].length ; j++)
    		{  
    			System.out.print(pop[i][j] + " ");
	    	}
	    	System.out.println(  " " + get_fitness_number_of_individual(pop[i]) + " ");
    	}*/
	  }
  public static int choose_first_individual(int [][] pop){
	  	int min_fitness_index = 0;
	  	for(int i = 0 ; i < population.fitness.length ; i++){
	  		//System.out.println("min index : " + min_fitness_index);
	  		if(population.get_fitness_number_of_individual(pop[i]) <= population.get_fitness_number_of_individual(pop[min_fitness_index]))
	  			min_fitness_index = i;
	  	}
	  	if(population.get_fitness_number_of_individual(pop[min_fitness_index]) < population.get_fitness_number_of_individual(population.best_individual))
  			population.best_individual = pop[min_fitness_index].clone();  	
	  	temp_pop[0] = pop[min_fitness_index].clone();
	  	//System.out.println("\nmin index : " + min_fitness_index);
    	return min_fitness_index;  	
  }
  private static void tournament_selection(int [][] pop,int ratio){
	  	int first_individual ;
	  	int second_individual;
	  	for(int i = 1 ; i < ratio ; i++){
	      	 first_individual = 1+(int)(Math.random()*(number_of_pop -  1));
	      	 while(true){
	      	 second_individual = 1+(int)(Math.random()*(number_of_pop - 1));
	      	 if(first_individual != second_individual) 
	      		 break;
	      	 }
	       	//System.out.println("first : " + first_individual + " second :" + second_individual);
	  		if(get_fitness_number_of_individual(pop[first_individual]) < get_fitness_number_of_individual(pop[second_individual])){
	  			temp_pop[i] = pop[first_individual].clone();
	  		}
	  		else 
	  			temp_pop[i] = pop[second_individual].clone();
	  	}
  }
  private static void randomly_selection(int ratio){
	  	for(int i = ratio ; i < pop.length ; i++){
	  		//System.out.print("Random " + i);
	  		for(int j = 0 ; j < pop[0].length ; j++)
	  		{
	  			temp_pop[i][j] = (int)(Math.random()* get_fitness_number_of_individual(temp_pop[0]));
		  		//System.out.print(" " + temp_pop[i][j]);
		    }
	  		//System.out.println(" ");

	  	}
	  /*	text_area_left_side.setText(text_area_left_side.getText() +
					"\n" + (pop.length-ratio) + " individual are chosen randomly");*/
	  	  	
	  //pop = temp_pop.clone();
	  	System.arraycopy( temp_pop, 0, pop, 0, pop.length );

	  }
  private static void cross_over(int [][] pop){
	//	System.out.println("crossover'a girmeden once pop");
	/*	for(int i = 0 ; i < pop.length ; i++){
    		System.out.print(i + ")");
    		for(int j = 0 ; j < pop[0].length ; j++)
    		{  
    			System.out.print(pop[i][j] + " ");
	    	}
	    	System.out.println(  " " + get_fitness_number_of_individual(pop[i]) + " ");
    	}*/
	    int first_individual ;
	  	int second_individual;
    // 	System.out.println("\nCross-over started\n" );
	  	for(int u = 1 ; u < pop.length ; u++){
	  		first_individual =  1+(int)(Math.random()*(number_of_pop -  1));
	      	second_individual =  1+(int)(Math.random()*(number_of_pop -  1));
	      //  System.out.println(u +  ")first : " + first_individual + " second :" + second_individual);
	      	System.arraycopy( pop[first_individual], 0 , temp_pop[u], 0, pop[first_individual].length/2 );
	      	System.arraycopy( pop[second_individual], pop[second_individual].length/2 ,temp_pop[u],pop[second_individual].length/2, pop[second_individual].length/2 );
	      	
	  	}
	  	System.arraycopy( temp_pop, 0, pop, 0, pop.length );
		/*for(int i = 0 ; i < pop.length ; i++){
    		System.out.print(i + ")");
    		for(int j = 0 ; j < pop[0].length ; j++)
    		{  
    			System.out.print(pop[i][j] + " ");
	    	}
	    	System.out.println(  " " + get_fitness_number_of_individual(pop[i]) + " ");
    	}*/
  }
  private static void  mutation(int [][] pop){
	  double p ;
	  double pm = 0.2 ; //(double) 1/pop.length;
	  
	  int first_pos ;
	  int sec_pos ;
  	//System.out.println("\nmutation started \n" );
	  for(int i = 1 ; i < pop.length ; i++){
	      	 p = Math.random();
	    	 first_pos = (int)(Math.random()*(node_number ));
	    	 sec_pos = (int)(Math.random()*(node_number));
		    // System.out.println("first : " + first_pos + " second :" + sec_pos + "p: " + p +" pm :" + pm);
	      	 if(p < pm){
	      		 int temp = pop[i][first_pos];
	      		 pop[i][first_pos] = pop[i][sec_pos];
	      		 pop[i][sec_pos] = temp;
	      	 }
	      	 
	  }
	/*  for(int i = 0 ; i < number_of_pop ; i++)
	    {
	    	for(int j = 0 ; j < node_number ; j++){
	    		System.out.print(pop[i][j] + "  ");
	    	}
	    	System.out.println(  " " + get_fitness_number_of_individual(pop[i]) + " ");
	    }
  	
  	*/
  	
  }
}
