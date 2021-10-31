import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GCell {
	static JPanel panel;
	static void btnsAdd(){
		for(int i=0; i<9; ++i){
			String label = ""+(i+1);
			JButton b = new JButton(label);
			Insets s = new Insets(2,2,2,2);
			b.setMargin(s);
			panel.add(b);
		}
	}
// test this mess...
	private static void testSetNotes(){
		TreeSet<Integer> nts = new TreeSet<Integer>();
		nts.add(3);nts.add(5);nts.add(7);   // odd primes
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame("Grid Layout");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(70,90);
		panel = new JPanel();
		frame.add(panel);
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel.setLayout(new GridLayout(3,3));
		testSetNotes();
		btnsAdd();
		frame.setVisible(true);
	}
}
