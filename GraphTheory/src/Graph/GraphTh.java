package Graph;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import AntColony.Ant;
import GeneticAlgorithm.geneticAlg;
import GeneticAlgorithm.population;
import GeneticAlgorithm.start;

public class GraphTh  implements ActionListener{
	JPanel general_panel;

	public static JTextArea text_area_left_side;
	static JScrollPane scrollpane_left_side;
	static File graph_file;
	static int node_number;
	static int edge_number;
	static int adj [][];
	static JRadioButton greedy;
	static JRadioButton genetic;
	static JRadioButton ant;
	static JRadioButton greedy_with_genetic;

	public GraphTh(){
		JFrame schema = new JFrame("Graph Theory Project/Graph Coloring Resul Tugay 504151522 I.T.U Istanbul-2016 ");
		schema.setLayout(null);
		general_panel = new JPanel();
		general_panel.setLayout(null);
		

		text_area_left_side = new JTextArea();
		text_area_left_side.setSize(200, 300);
		text_area_left_side.setEditable(false);
		text_area_left_side.setLocation(550, 20);	
		
		scrollpane_left_side = new JScrollPane(text_area_left_side);
		scrollpane_left_side.setSize(200, 300);
		scrollpane_left_side.setLocation(550, 20);
		scrollpane_left_side.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		general_panel.add(scrollpane_left_side);
		

	      greedy  = new JRadioButton("Greedy");
	      genetic = new JRadioButton("Genetic");
	      ant =     new JRadioButton("Ant Colony");
	      greedy_with_genetic = new JRadioButton("Greedy With Genetic");

	      greedy.setEnabled(false);
	      greedy.setSize(100,20);
	      greedy.setLocation(100, 10);
	      greedy.addActionListener(this);
	    /*  greedy.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
		        		Greedy.greedy_approach(adj);
		         }           
		      });*/
	      
	      genetic.setEnabled(false);
	      genetic.setSize(100,20);
	      genetic.setLocation(200, 10);
	      genetic.addActionListener(this);
	      
	      

	      ant.setEnabled(false);
	      ant.setSize(100,20);
	      ant.setLocation(300, 10);
	      ant.addActionListener(this);
	  /*    ant.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        		Ant.Ant2(graph_file);
	        		Ant.main(null);
	         }           
	      });*/
	      
	      greedy_with_genetic.setEnabled(false);
	      greedy_with_genetic.setSize(150,20);
	      greedy_with_genetic.setLocation(400, 10);
	      greedy_with_genetic.addActionListener(this);
	      
	      //Group the radio buttons.
	      ButtonGroup group = new ButtonGroup();
	      group.add(greedy);
	      group.add(genetic);
	      group.add(greedy_with_genetic);
	      group.add(ant);
		
	      general_panel.add(greedy);
	      general_panel.add(genetic);
	      general_panel.add(ant);
	      general_panel.add(greedy_with_genetic);

	      
		Dimension screen =  Toolkit.getDefaultToolkit().getScreenSize();		
		schema.setSize(800,500);
		schema.setLocation(230,150);
		schema.setContentPane(general_panel);

		Menu menu = new Menu();
		schema.setJMenuBar(menu.menubar);
		schema.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		schema.setVisible(true);
		
		
	}
	
	public static void read_file_write_to_array(){

		try {
			Scanner scanner = new Scanner(graph_file);			
		    node_number = scanner.nextInt();
		    edge_number = scanner.nextInt();
		    
		    adj = new int[node_number][node_number];
		    
		    for(int i = 0 ; i < edge_number ; i++){
		    	int a = scanner.nextInt();
		    	int b = scanner.nextInt();
		    	adj[a][b] = 1;
		    	adj[b][a] = 1;
		    }
		    
	    /*	System.out.println("Graph :\n");
		    for(int i = 0 ; i < node_number ; i++)
		    {
		    	for(int j = 0 ; j < node_number ; j++){
		    		System.out.print(adj[i][j] + "  ");
		    	}
		    	System.out.println(" ");
		    }
		 */ 	    
		    
		  //  JOptionPane.showMessageDialog(null, "Graph selected\n");
		    text_area_left_side.setText("Graph Selected..");
		    greedy.setEnabled(true);
		    genetic.setEnabled(true);
		    ant.setEnabled(true);
		    greedy_with_genetic.setEnabled(true);


		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No file selected!");
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		GraphTh a = new GraphTh();	
		JOptionPane.showMessageDialog(null, "In order to activate buttons please choose a graph from the menu");

	}
	@Override
	public void actionPerformed(ActionEvent algorithm) {
		if(algorithm.getSource() == greedy){
			geneticAlg.read_file_write_to_list(graph_file);
			population.isGreedy = true;
			population.isGenetic =false;
			population.isGreedywithGenetic = false;
			population.initialization();
		}else if(algorithm.getSource() == ant){
			Ant.Ant2(graph_file);
    		Ant.main(null);
		}else if(algorithm.getSource() == genetic){
			population.isGreedy = false;
			population.isGreedywithGenetic = false;
			population.isGenetic = true;
			geneticAlg.read_file_write_to_list(graph_file);
			population.initialization();	
			start.run(adj);
		}else if(algorithm.getSource() == greedy_with_genetic){
		population.isGreedywithGenetic = true;
		population.isGreedy = false;
		population.isGenetic = false;
		geneticAlg.read_file_write_to_list(graph_file);
		population.initialization();	
		start.run(adj);
		}
	}
}
