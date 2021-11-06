import java.util.*;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

class Separator extends Canvas{
	Separator(int w, int h){
		setSize(w,h);
	}
}

class AidGraphic extends JPanel implements AidPresentation {
	int SIZE = 600;
	Box board;				// graphics view of GCell array
	GCell gcarray[][] = new GCell[9][9];		// cell access
	public static AidPresentation instance() {	// public access
		return (AidPresentation)me;
	}
	static AidGraphic me = new AidGraphic();    // self instantiate
	private AidGraphic(){          // private enforces singleton 
		super();
		board= Box.createVerticalBox();
		for(int r=0; r<9; ++r){
			Box row = Box.createHorizontalBox();
			for(int c=0; c<9; ++c){
				GCell gc = gcarray[r][c] = new GCell();
				row.add(gc);
				if((c==2)||(c==5)) row.add(new Separator(1,SIZE/9));
			}
			board.add(row);
			if((r==2)||(r==5)) board.add(new Separator(SIZE,10));
		}
	}

// presentation interface stuff...
	public void outln(String s){}
	public void out(String s){}
	public void displayBoard(){
		
	}
	public String presentationStyle(){ return "graphic style"; }
	public void doThePuzzle(){
		AidGraphic ag = new AidGraphic();
		JFrame frame = new JFrame("Suduko");
		frame.setSize(SIZE,SIZE);
		JPanel cp = (JPanel)frame.getContentPane();
		cp.add(board);
		frame.setVisible(true);
	}
}

class MyFrame extends JFrame{
	MyFrame(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}