import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GCell extends Cell
        //, ActionListener, MouseListener 
{
	int row, col;    // zero based
	JPanel pane;

	// prereq: puzl[][] is available...
	public void renderSeed(){
		renderValue();
	}
	// render as button which will clear the value.
	public void renderValue(){
		pane.removeAll();
		pane.setLayout(new GridLayout(1,1));
		JButton v = new JButton(""+value);
		v.setFont(new Font("Arial", Font.BOLD, 18));
		pane.add(v);
	}

	public GCell(int row, int col){
		super(row,col);
		pane = new JPanel();
		Border blackline = BorderFactory.createLineBorder(Color.black,2);
		pane.setBorder(blackline);
//if(row<3)if(col<3)System.err.println(" GCell~45: "+bl+""+cl+""+row+""+col);
	}

	public JPanel getPane(){ return pane; }
	public int getRow(){ return row; }
	public int getCol(){ return col; }
	// adds all buttons == notes   BUG: notes is garbage
	public void setButtons(){
		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pane.setLayout(new GridLayout(3,3));
//if((row==0)&&(col==4))dumpStuffG();
//if((row==0)&&(col==5))dumpStuffG();
		for(int nn=1; nn<10; ++nn){
			if(notes.contains(nn)){
				String label = ""+nn;
				JButton b = new JButton(label);
				Insets s = new Insets(0,0,0,0);
				b.setMargin(s);
				pane.add(b);
			} else {
				pane.add(new JPanel());
			}
		}
	}
public void dumpStuffG(){
	System.err.println("GCell~54/69 notes: "+notes);
}
/*
		int value;
		value = Aid.puzl[row][col].getValue();
		if(value>0){   //value
			this.add(new JPanel());   // and paint the value
		}
		else {         // buttons
		}
*/
	
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
			for(int cl=0; cl<9; ++cl){
				GCell cell = new GCell(bl,cl);
//				cell.setPosition(bl,cl);
				block.add(cell.getPane());
			}
			board.add(block);
		}
		JFrame frame = new JFrame("Suduko");
		frame.setContentPane(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,640);
		frame.setVisible(true);
	}

/* test four: seedValue
	public static void main(String[] args) {
	Border blackline = BorderFactory.createLineBorder(Color.black,3);
		JPanel board = new JPanel(new GridLayout(3,3));
		for(int bl=0; bl<9; ++bl) {
			JPanel block = new JPanel(new GridLayout(3,3));
			block.setBorder(blackline);
			for(int cl=0; cl<9; ++cl){
				block.add(new GCell()); //test four
				cell.setPosition(bl,cl);
			}
			board.add(block);
		}
		JFrame frame = new JFrame("Suduko");
		frame.setContentPane(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,640);
		frame.setVisible(true);
	}
*/
}
/* GOALS...
		remove notes, 
		setSeed, 
		setValue, 
		iterate over neighbors...
			for(Cell cell : neighbor){...}
				works in Cell
				NEEDed in GCell
					GCell extends JPanel, not Cell
					Can Cell provide a service to GCell ???
					Cell computes. GCell displays !!!
*/