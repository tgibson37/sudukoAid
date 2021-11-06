import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/*
class Block extends JPanel{
        Block(){
//                setMinimumSize(50,50);
                setLayout(new GridLayout(3,3));
                Border blackline = BorderFactory.createLineBorder(Color.black);
                JPanel contentPanel = new JPanel();
                contentPanel.setLayout(new GridLayout(3,3));
                for(int i=0; i<9; ++i)contentPanel.add(new GCell());
                JPanel borderPanel = new JPanel();
                borderPanel.setBorder(blackline);
                borderPanel.add(contentPanel);
                this.add(borderPanel);
        }
}
*/

public class GCell extends JPanel
        //, ActionListener, MouseListener 
{
	public GCell(){
		Border blackline = BorderFactory.createLineBorder(Color.black,2);
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setLayout(new GridLayout(3,3));
		for(int i=0; i<9; ++i){
			String label = ""+(i+1);
			JButton b = new JButton(label);
			Insets s = new Insets(0,0,0,0);
			b.setMargin(s);
			this.add(b);
		}
		this.setBorder(blackline);
	}
/* test one: one GCell w/ 9 buttons
	public static void main(String[] args) {   // just a GCell, 9 buttons
		JFrame frame = new JFrame("A Cell");
		frame.add(new GCell());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(60,100);
		frame.setVisible(true);
	}
*/
	
/* test two: 3x3 block with 9 cells, border
	public static void main(String[] args) {
	Border blackline = BorderFactory.createLineBorder(Color.black,3);
		JPanel block = new JPanel(new GridLayout(3,3));
		block.setBorder(blackline);
		for(int i=0; i<9; ++i)
			block.add(new GCell());
		JFrame frame = new JFrame("A Block");
		frame.setContentPane(block);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200,240);
		frame.setVisible(true);
	}
}
*/

/* test three: full board
*/
	public static void main(String[] args) {
	Border blackline = BorderFactory.createLineBorder(Color.black,3);
		JPanel board = new JPanel(new GridLayout(3,3));
		for(int bl=0; bl<9; ++bl) {
			JPanel block = new JPanel(new GridLayout(3,3));
			block.setBorder(blackline);
			for(int i=0; i<9; ++i){
				block.add(new GCell());
			}
			board.add(block);
		}
		JFrame frame = new JFrame("Suduko");
		frame.setContentPane(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,640);
		frame.setVisible(true);
	}
}
