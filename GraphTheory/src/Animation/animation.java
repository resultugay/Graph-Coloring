package Animation;


import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author resultugay
 */
public class animation {
    public static void animate(int [][] adj,int [] color,String name) {

    	Color [] chromes = new Color[color.length];
    	for (int i = 0; i < chromes.length; i++) {
        	Random rand = new Random();
        	float r = rand.nextFloat();
        	float g = rand.nextFloat();
        	float b = rand.nextFloat();
    		Color randomColor = new Color(r, g, b);
			chromes[i] = randomColor;
		}
    	
    	
    	
        Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
    	for (int i = 0; i < adj.length; i++) {
			g.addVertex(i);
		}
        for (int i = 0; i < adj.length; i++) {
			for (int j = 0; j < i; j++) {
				if(adj[i][j] == 1)
					g.addEdge("edge"+i+j,i,j);
			}
		}
        
        // Layout implements the graph drawing logic
       // Layout<Integer, String> layout = new CircleLayout<Integer, String>(g);
        Layout  layout = new ISOMLayout(g);

        layout.setSize(new Dimension(300,300));

        // VisualizationServer actually displays the graph
        //BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<Integer,String>(layout);
        VisualizationViewer vv=new VisualizationViewer(layout);

        vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        // Transformer maps the vertex number to a vertex property
        Transformer<Integer,Paint> vertexColor = new Transformer<Integer,Paint>() {
            public Paint transform(Integer i) {
            	int a = color[i];
            	return chromes[a];

            }
        };
     

        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);        
        
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);
        
        vv.getRenderContext().setVertexFillPaintTransformer(vertexColor);

        JFrame frame = new JFrame("Graph Theory App " + name);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(vv); 
        frame.pack();
        frame.setVisible(true);    
    }

    public static void main(String[] args) {
        
    	new animation();
    }
}