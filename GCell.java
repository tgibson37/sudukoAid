import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import javax.swing.border.Border;
import java.awt.event.*;

public class GCell extends Cell 
	//implements ActionListener, MouseListener 
{
	int row, col;    // zero based
	JPanel pane;
	NoteButton nba[] = new NoteButton[9];

/*	// prereq: puzl[][] is available...
	public void setSeed(){
		setValue();
	}
*/
	// render as button with label value.
	public String setValue(int value){
		this.value = value;
		pane.removeAll();
		pane.setLayout(new GridLayout(1,1));
		JButton v = new JButton(""+value);
		v.setFont(new Font("Arial", Font.BOLD, 18));
		pane.add(v);
		return null;
	}

	public GCell(int row, int col){
		super(row,col);
		pane = new JPanel();
		Border blackline = BorderFactory.createLineBorder(Color.black,2);
		pane.setBorder(blackline);
	}

	public JPanel getPane(){ return pane; }
	public int getRow(){ return row; }
	public int getCol(){ return col; }

// add button for each note
	public void setButtons(){
		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pane.setLayout(new GridLayout(3,3));
		for(int nn=1; nn<10; ++nn){    // external number
			if(notes.contains(nn)){
				NoteButton b = new NoteButton(this,nn);
				nba[nn-1] = b;  // handle needed for removal, internal number
				b.addActionListener(b);
				Insets s = new Insets(0,0,0,0);
				b.setMargin(s);
				String label = ""+nn;
				b.setText(label);
				pane.add(b);
			} else {
				pane.add(new JPanel());
			}
		}
	}
	public void removeButton(int nn){   // external nn
			NoteButton b = nba[nn-1];
		if(notes.contains(nn)){
			pane.remove(b);
			pane.revalidate();
			pane.repaint();
		}
	}
}

class NoteButton extends JButton implements ActionListener {
	Cell cell;
	int value;
	public NoteButton(Cell cl, int vl){
		super();
		cell = cl;
		value = vl;
	}
	public String toString(){
		return ("NoteButton " + cell.row +""+ cell.col);
	}
// cmdSet uses external row/col number
	public void actionPerformed(ActionEvent e){
		Aid.cmdSet(cell.row+1, cell.col+1, value);
	}
}

//  GCell standalone tests. Keep these for awhile...

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
*/

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

