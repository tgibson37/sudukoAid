import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *	Suduko aid. Create a file with exactly 9 lines each 
 *	exactly 9 characters plus a newline. 90 bytes total.
 *	Use spaces or zeros for empty cells, numerals elsewhere.
 *	Example: s1.txt.
 */
public class Aid{
	static String currentPath;
    static Cell[][] c;
    static byte[] bytes;
    public static void main(String[] args) throws IOException{
    	currentPath = System.getProperty("user.dir");
        createCells();
		readInitFile("s1.txt");
		processValues();
		computeNeighbors();
		showAll();
		computeNotes();
//dumpAll();
dump(0,0); dump(4,1);
    }
    static void readInitFile(String filename) throws IOException {
    	Path p1 = Paths.get(currentPath + "/s1.txt");
        bytes = Files.readAllBytes(p1);
//System.out.println(new String(bytes));
	}
	static void createCells(){
    	c = new Cell[9][9];
		for(int row=0; row<9; ++row)
			for(int col=0; col<9; ++col)
				c[row][col] = new Cell(row,col);
	}
	static void processValues(){
		for(int i=0; i<90; ++i) {
			if(bytes[i]==10)continue;   // skip newlines
			if(bytes[i]==32)continue;   // skip spaces
			c[i/10][i%10].setValue(bytes[i]-48);
		}
	}
	static void computeNeighbors(){
		for(int i=0; i<9; ++i)
			for(int j=0; j<9; ++j)
				c[i][j].computeNeighbors(c);
	}
	static void dumpAll(){
		for(int row=0; row<9; ++row){
			for(int col=0; col<9; ++col){
				dump(row,col);
			}
			System.out.println("");
		}
	}
	static void dump(int row, int col){
		System.out.print(c[row][col]);
		System.out.print("  ");
	}
		
	static void showAll(){
		for(int row=0; row<9; ++row){
			for(int col=0; col<9; ++col){
				System.out.print(c[row][col].value);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	static void computeNotes(){
		for(int row=0; row<9; ++row){
			for(int col=0; col<9; ++col){
				c[row][col].computeNotes(c);
			}
		}
	}
}

/*
=============== next GOAL =============
print cells in matrix, w/ both notes and data...
	printRow(int row): line of notes, line of
		values, newline, nicely spaced. 
*/