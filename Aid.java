import java.io.*;
import java.util.*;
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
    public static void Usage(){
    	System.out.println("Usage: java Aid <inputfile> [<optionaloutputfile>]");
    }
    public static void main(String[] args) throws IOException{
    	String filename="";
		if(args.length>1){
			PrintStream o = new PrintStream(new File(args[1]));
			System.setOut(o);
		}
    	if(args.length>0){
    		filename=args[0];
    	}
    	else{
    		Usage();
    		System.exit(0);
    	}
        createCells();
		readInitFile(filename);
		processValues();
		showAll();
		computeNeighbors();
		computeNotes();
//dumpAll();
//dump(0,0);dump(4,1);
		prettyPrint();
		patterns();
    }
    static void patterns(){
    	patOnesy();
//    	patMatchedPair()
//    	pat322();
//    	patCycle();
    }
    static void patOnesy(){
    	int s=0;
    	for(int row=0; row<9; ++row){
    		for(int col=0; col<9; ++col){
    			s=c[row][col].notes.size();
    			if(s==1)System.out.format("Onesy at %d%d\n",row,col);
    		}
    	}
    }
    static void readInitFile(String filename) throws IOException {
    	try{
			currentPath = System.getProperty("user.dir");
			Path p1 = Paths.get(currentPath + "/" + filename);
			bytes = Files.readAllBytes(p1);
        } catch(Exception e) {
        	System.err.println(e);
        	System.exit(1);
        }
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
			System.err.println("");
		}
	}
	static void dump(int row, int col){
		System.err.print(c[row][col]);
		System.err.print("  ");
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
	static void prettyPrint(){
		System.out.println(
"=========================================================================");		for(int row=0; row<9; ++row){
			System.out.format("#");
			for(int col=0; col<9; ++col){
				c[row][col].prettyPrintNotes();
			}
			System.out.println("");
			System.out.format("#");
			for(int col=0; col<9; ++col){
				c[row][col].prettyPrintValue();
			}
			System.out.println("");
			if(row%3 != 2)System.out.println(
"#------------------------------------------------------------------------");
			else System.out.println(
"#========================================================================");
		}
	}
}
