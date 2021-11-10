import java.util.*;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

class Separator extends Canvas{
	Separator(int w, int h){
		setSize(w,h);
	}
}

class AidGraphic extends JPanel implements AidPresentation {
	int SIZE = 600;
	JFrame frame;
	GCell gcarray[][] = new GCell[9][9];		// cell access
	public static AidPresentation instance() {	// public access
		return (AidPresentation)me;
	}
	static AidGraphic me = new AidGraphic();    // self instantiate
	private AidGraphic(){          // private enforces singleton 
		super();
		Border blackline = BorderFactory.createLineBorder(Color.black,3);
		JPanel board = new JPanel(new GridLayout(3,3));       // board
		for(int bl=0; bl<9; ++bl) {
			JPanel block = new JPanel(new GridLayout(3,3));   // 9 blocks
			block.setBorder(blackline);
			for(int cl=0; cl<9; ++cl){
//System.out.print(" "+bl+cl);
				block.add(new GCell(bl,cl));              // 81 cells
			}
			board.add(block);
		}
		frame = new JFrame("Suduko");
		frame.setContentPane(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,640);
	}

// presentation interface stuff...
	public void outln(String s){}
	public void out(String s){}
	public void displayBoard(){
		int value;
		for(int row=0; row<9; ++row) {
			for(int col=0; col<9; ++col) {
				value = Aid.puzl[row][col].getValue();
				if(value>0){   //value
//System.out.print(" "+row+""+col+"="+value);
					this.add(new JPanel());   // and paint the value
				}
				else {         // buttons
				}
			}
		}
		frame.setVisible(true);
	}
	public String presentationStyle(){ return "graphic style"; }
	public void doThePuzzle(){
		displayBoard();
	}
}

class MyFrame extends JFrame{
	MyFrame(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}