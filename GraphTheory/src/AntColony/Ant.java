package AntColony;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Animation.animation;
import Graph.GraphTh;

public class Ant {
	static int adj [][];	
	static int trail_update [][];
	static int node_number;
	static int edge_number;
	static double ro [][];
	static double temp_ro[][];
	static int ants = 100;
	static int cycles = 100;
	static int q = 1;
	static double ta [][];
	static double t [];
	static int alpha = 2;
	static int beta = 5;
	static ArrayList<Integer> fea_nodes;
	static ArrayList<Integer> not_fea_nodes;
	static double rho_value = 0.08;
	static double [] p;
	static double [] eta ;
	static ArrayList<Integer> [] ColorClass;
	static ArrayList<Integer> [] temp_ColorClass;
	static int [] UnColoredSet;
	static int [] temp_UnColoredSet;
	static int ColoredUse = 0;
	static Boolean tmp;
	int z = 1;
	int w = 1;
	int r = 1;
	static int [] result;
	ArrayList<Integer> notFeasible = new ArrayList<>();
	public static void Ant2(File graph_file){

		Scanner scanner;
		try {
			//dosyadan okuma islemi yapiliyor.
			//scanner = new Scanner(new FileReader("C:\\Users\\rsltgy\\Documents\\gc_20_1"));
			scanner = new Scanner(new FileReader(graph_file));
			node_number = scanner.nextInt();
			edge_number = scanner.nextInt();
			ants = node_number;
			adj = new int[node_number][node_number];		
			ro =  new double[node_number][node_number];	
			temp_ro = new double[node_number][node_number];
			ta = new double[node_number][node_number];		
			UnColoredSet =  new int[node_number];
			temp_UnColoredSet =  new int[node_number];
			ColorClass = new ArrayList[node_number];
			temp_ColorClass = new ArrayList[node_number];
			p = new double[node_number];
			for (int i = 0; i < node_number; i++) {
				for (int j = 0; j < node_number; j++) {
					ro[i][j] = 1;
					temp_ro[i][j] = 1;
				}
			}
			
			
			//Graph secilip komsuluk matrisi olan adj olusturuluyor
		    for(int i = 0 ; i < edge_number ; i++){
		    	int a = scanner.nextInt();
		    	int b = scanner.nextInt();
		    	adj[a][b] = 1;
		    	adj[b][a] = 1;
		    	ro[a][b] = 0;
		    	ro[b][a] = 0;
		    	temp_ro[a][b] = 0;
		    	temp_ro[b][a] = 0;
		    	ta[a][b] = 1;
		    	ta[b][a] = 1;
		    }
		    //trail matrisi initilize ediliyor
		    t = new double[node_number];
		    for (int i = 0; i < node_number; i++) {				
					t[i] = 1;				
			}
		    
		    for (int i = 0; i < node_number; i++) {
				p[i] = 0;
			}
		    eta = new double[node_number];
		    for (int i = 0; i < adj.length; i++) {
				eta[i] = 1/node_number;
				//System.out.println("eta " + i + " " + eta[i]);
			}
		    fea_nodes = new ArrayList<Integer>(); 
		    not_fea_nodes = new ArrayList<Integer>(); 
		    for(int j = 0 ; j < node_number ; j++){
		    		UnColoredSet[j] = -1;
		    		temp_UnColoredSet[j] = -1;
		    }
		    
		    for(int j = 0 ; j < node_number ; j++){
	    		ColorClass[j] = new ArrayList<>();
	    		temp_ColorClass[j] = new ArrayList<>();
	    	}
			result = new int[node_number];
			for (int i = 0; i < result.length; i++) {
				result[i] = -1;
			}
		/*    for(int i = 0 ; i < node_number ; i++)
		    {
		    	for(int j = 0 ; j < node_number ; j++){
		    		System.out.print(adj[i][j] + "  ");
		    	}
		    	System.out.println(" ");
		    }
		 */ 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public void ANTCOL(){

	    trail_update = new int[node_number][node_number];
		int vertex;
	    //genel antcol algoritmasi
	    for (int cycle = 1; cycle < cycles; cycle++) {
	    	//trail_update matrisi sifirlaniyor.
	    	//System.out.println(cycle);
	    	ro = temp_ro.clone();
		    for (int ant = 0; ant < ants; ant++) {
				ColorClass = temp_ColorClass.clone();
				//UnColoredSet = temp_UnColoredSet;
				 for(int j = 0 ; j < node_number ; j++){
			    		UnColoredSet[j] = -1;
			    		temp_UnColoredSet[j] = -1;
			    }
				ColoredUse = -1;
				for(int i = 0 ; i < node_number ; i++){
					if(UnColoredSet[i] == -1){
						tmp = true;
						break;
					}else
						tmp = false;
				}
				while(tmp){
						ColoredUse += 1;
						fea_nodes.clear();
						for (int i = 0; i < UnColoredSet.length; i++) {
							if(UnColoredSet[i] == -1){
								fea_nodes.add(i);
								}
						}
						for (int j = 0; j < fea_nodes.size(); j++) {
							p[fea_nodes.get(j)] = (double)1/fea_nodes.size();
						}
						for (int i = 0; i < result.length; i++) {
							if(result[i] != -1)
								p[i] = 0;
						}						
						vertex = selectVertex(p);
						if(vertex == -1){
							break;
						}
						ColorVertex(vertex, UnColoredSet, fea_nodes, ColorClass, adj, ColoredUse);
						Iterator it = fea_nodes.iterator();
						while(it.hasNext()){
							int m = ColorClass[ColoredUse].size();
							int temp_sum = 0;
							int sum = 0;
							for (int a = 0; a < m; a++) {
								temp_sum += ta[vertex][ColorClass[ColoredUse].get(a)];
							}
							t[vertex] = temp_sum/m;
							p[vertex] = Math.pow(t[vertex],alpha)*Math.pow(eta[vertex],beta);
						//tum nodelardan feasible nodelari cikararak not feasible elde ediyoruz
							for (int i = 0; i < node_number; i++) {
								not_fea_nodes.add(i);
							}
							for (int i = 0; i < fea_nodes.size(); i++) {
								not_fea_nodes.remove(fea_nodes.get(i));
							}							
							for (int a = 0; a < not_fea_nodes.size(); a++) {
								p[not_fea_nodes.get(a)] = 0;
							}
							for (int a = 0; a < fea_nodes.size(); a++) {
								sum += t[fea_nodes.get(a)];
							}
							if(sum != 0){
								for (int a = 0; a < p.length; a++) {
									p[a] = p[a]/sum;
								}
								vertex = selectVertex(p);
							}
							else{
								for (int a = 0; a < fea_nodes.size(); a++) {
									p[fea_nodes.get(a)] = 1/fea_nodes.size();
								}
								vertex = selectVertex(p);				
							}

							if(vertex == -1){
								break;
							}
							ColorVertex(vertex, UnColoredSet, fea_nodes, ColorClass, adj, ColoredUse);
						}
						for (int a = 0; a < ColorClass[ColoredUse].size(); a++) {
							result[ColorClass[ColoredUse].get(a)] = ColoredUse;
						}
					
					}					
				z = 1;
				if(!ColorClass[ColoredUse].isEmpty()){
					if(ColorClass[ColoredUse].size() >= 2)
					{
						while(z < ColorClass[ColoredUse].size()){
							w = ColorClass[ColoredUse].get(z);
							r = ColorClass[ColoredUse].get(z+1);
							ro[w][r] += 1/ColoredUse;
							z += 1;
						}
					}
				}
				for (int i = 0; i < ColorClass.length; i++) {
					ColorClass[i].clear();
				}
			}
	    	z = 1;
	    	//while(z < edge_number){
	    		for (int i = 0; i < result.length; i++) {
					for (int j = 0; j < result.length; j++) {
						if(adj[i][j] == 1){
							ta[i][j] = rho_value*ta[i][j]+ro[i][j];
						}
					}
				}
	    	//}
		}
	    
	}
	public void ColorVertex(int vertex,int [] unColoredSet, ArrayList<Integer> feasible, ArrayList<Integer> [] ColorClass,int [][] adj,int ColorUsed){
		
		ArrayList<Integer> neigborho_valueod = new ArrayList<>();
		unColoredSet[vertex] = 0;
		ColorClass[ColorUsed].add(vertex);
		
		for (int i = 0; i < adj.length; i++) {
			if(adj[vertex][i] == 1)
				neigborho_valueod.add(i);
		}
		int index = feasible.indexOf(vertex);
		feasible.remove(index);
		for (int i = 0; i < neigborho_valueod.size(); i++) {
			feasible.remove(neigborho_valueod.get(i));
		}
		
	}
	
	public static int selectVertex(double [] p){
		double [] cumSum = new double[p.length];
		double rand;
		Boolean tmp = true;
		Boolean flag = true;
		int returne = 0;
		int cnt = 0;
		for (int i = 0; i < cumSum.length; i++) {
			cumSum[i] = p[i];
			if(i != 0)
				cumSum[i] += cumSum[i-1];
		}
		/*for (int i = 0; i < cumSum.length; i++) {
			if(p[i] == 0)
				cumSum[i] = 0;
		}
		*/
		for (int i = 0; i < cumSum.length; i++) {
			if(p[i] == 0)
				cnt += 1;
		}
		double min = 100000;
		for (int i = 0; i < cumSum.length; i++) {
			if(cumSum[i] < min)
				min = cumSum[i];
		}
		double max = 0;
		for (int i = 0; i < cumSum.length; i++) {
			if(cumSum[i] > max)
				max = cumSum[i];
		}
		if(cnt != p.length){
			while(flag){
				rand = (double)(Math.random()*(max-0)) + 0;
				for(int i = 0 ; i <p.length ; i++){
					if(cumSum[i] >= rand){
						returne = i;
						flag = false;
						break;
					}
				}
			}

			return returne;
		}else
			return -1;
	}
	public static void main(String [] args){
		Ant ant = new Ant();
		ant.ANTCOL();
	/*	for (int i = 0; i < result.length; i++) {
			System.out.print(" "+result[i]);
		}
		System.out.println(" ");
	*/	
		int max = 0;
		for (int i = 0; i < result.length; i++) {
			if(result[i] > max)
				max = result[i];
		}


		Graph.GraphTh.text_area_left_side.setText(GraphTh.text_area_left_side.getText()+"\n"
				+"Ant Colony used totally \n " + (max + 1) + " colors for given graph");
		
		animation.animate(adj,result,"Ant Colony");
		animation.main(null);
		
	}
}
