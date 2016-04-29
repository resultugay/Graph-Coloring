package Graph;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu implements ActionListener{
	JMenuBar menubar;
	JMenu file;
	JMenuItem exit;
	JMenuItem graph_menuItem;
	
	public Menu(){
		menubar = new JMenuBar();
		file = new JMenu("File");
		
		graph_menuItem = new JMenuItem("Graph");
		graph_menuItem.addActionListener(this);
		file.add(graph_menuItem);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);	
		
		menubar.add(file);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit){
			System.exit(0);
		}
		if(e.getSource() == graph_menuItem){
			 JFileChooser graph_chooser = new JFileChooser();
			 //FileNameExtensionFilter filter = new FileNameExtensionFilter("Graph Files", ".graph");
			 //graph_chooser.setFileFilter(filter);
			 int return_val = graph_chooser.showOpenDialog(null);
			 if(return_val == JFileChooser.APPROVE_OPTION) {
				 GraphTh.graph_file = graph_chooser.getSelectedFile();
		     }

			 GraphTh.read_file_write_to_array();
			 
		}
	
	}

}
