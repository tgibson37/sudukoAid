import java.util.*;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

class AidGraphic extends JPanel
        implements AidPresentation
        //, ActionListener, MouseListener 
{
	static AidGraphic me = new AidGraphic();  // self instantiate
	private AidGraphic(){ super(); }          // inforce singleton
	public static AidPresentation instance() {  // public access
		return (AidPresentation)me;
	}
	public void outln(String s){}
	public void out(String s){}
	public void displayBoard(){
System.err.println("Graphic displayBoard");
	}

}

class MyFrame extends JFrame{
	MyFrame(String title) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}