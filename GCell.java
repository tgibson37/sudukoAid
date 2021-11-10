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

public class GCell extends JPanel
        //, ActionListener, MouseListener 
{
	int row, col;
	int block,clInBl;
	public GCell(int bl, int cl ){
		block = bl;
		clInBl = cl;
		row = (bl/3)*3 + cl/3;
		col = (bl%3)*3 + cl%3;
		Border blackline = BorderFactory.createLineBorder(Color.black,2);
		this.setBorder(blackline);
		setButtons();
	}
	// adds all 9 buttons
	public void setButtons(){
		String label;
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setLayout(new GridLayout(3,3));
		for(int i=0; i<9; ++i){
//			if(value==0){
				label = ""+(i+1);
//			} else {
//				label = "";
//			}
			JButton b = new JButton(label);
			Insets s = new Insets(0,0,0,0);
			b.setMargin(s);
			this.add(b);
		}
	}
	// prereq: puzl[][] is available...
	public void renderSeedValue(){
	}
	public void renderValue(){
	}

/*
		int value;
		row = (bl/3)*3 + cl/3;
		col = (bl%3)*3 + cl%3;
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
				block.add(new GCell(bl,cl)); //test
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
				block.add(new GCell(bl,cl)); //test
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