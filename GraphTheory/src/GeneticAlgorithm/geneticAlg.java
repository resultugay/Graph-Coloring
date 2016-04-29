package GeneticAlgorithm;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class geneticAlg{ 
	JPanel general_panel;
	JPanel animation_panel;
	static JTextArea text_area_right_side;
	JScrollPane scrollpane_right_side;
	static JButton button_start;
	//static File graph_file;	
	static int node_number;
	static int edge_number;
	public static void read_file_write_to_list(File graph_file){

		try {
			Scanner scanner = new Scanner(graph_file);		
		    node_number = scanner.nextInt();
		    edge_number = scanner.nextInt();
		    population population = new population(20,node_number);

		    for(int i = 0 ; i < edge_number ; i++){
		    	int a = scanner.nextInt();
		    	int b = scanner.nextInt();
		    	population.nodes[a].adjacents.add(b);
		    	population.nodes[b].adjacents.add(a);
		    }		    
		    //population.show_adjacency_List();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No file selected!");
			e.printStackTrace();
		} 
	}

}
