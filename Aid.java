import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *	Suduko aid. Input: a file of type sdku with exactly 9 lines, each 
 *	exactly 9 characters plus a newline. 90 bytes total.
 *	Use spaces or zeros for empty cells, numerals elsewhere.
 *	Example: s1.dku.
 */
public class Aid{
	static String currentPath;
    static Cell[][] puzl;
    static byte[] bytes;
	public static final int ROW = 1;
	public static final int COL = 2;
	public static final int BLK = 3;

    public static void Usage(){
    	System.out.println(
"Usage: java Aid <inputfile> [<optionaloutputfile>]\njust the name, not the sdk type."
    	);
    }

// mainline...
	static void createCells(){
    	puzl = new Cell[9][9];
		for(int row=0; row<9; ++row)
			for(int col=0; col<9; ++col)
				puzl[row][col] = new Cell(row,col);
	}
    static void readInitFile(String filename) throws IOException {
    	try{
			currentPath = System.getProperty("user.dir");
			Path p1 = Paths.get(currentPath + "/" + filename + ".sdk");
			bytes = Files.readAllBytes(p1);
        } catch(Exception e) {
        	System.err.println(e);
        	System.exit(1);
        }
	}
	static void processValues(){
		for(int i=0; i<90; ++i) {
			if(bytes[i]==10)continue;   // skip newlines
			if(bytes[i]==32)continue;   // skip spaces
			puzl[i/10][i%10].setValue(bytes[i]-48);
		}
	}
	static void showAll(){
		for(int row=0; row<9; ++row){
			for(int col=0; col<9; ++col){
				System.out.print(puzl[row][col].value);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	static void computeNeighbors(){
		for(int i=0; i<9; ++i)
			for(int j=0; j<9; ++j)
				puzl[i][j].computeNeighbors(puzl);
	}
	static void computeNotes(){
		for(int row=0; row<9; ++row){
			for(int col=0; col<9; ++col){
				puzl[row][col].computeNotes(puzl);
			}
		}
	}
	static void prettyPrint(){
		System.out.println(
"=======================================================");
		for(int row=0; row<9; ++row){
			System.out.format("#");
			for(int col=0; col<9; ++col){
				puzl[row][col].prettyPrintNotes();
			}
			System.out.println("");
			System.out.format("#");
			for(int col=0; col<9; ++col){
				puzl[row][col].prettyPrintValue();
			}
			System.out.println("");
			if(row%3 != 2)System.out.println(
"#------------------------------------------------------");
			else System.out.println(
"#======================================================");
		}
	}

// dumps and main...
	static void dumpAll(){
		for(int row=0; row<9; ++row){
			for(int col=0; col<9; ++col){
				dump(row,col);
			}
			System.err.println("");
		}
	}
	static void dump(int row, int col){
		System.err.print(puzl[row][col]);
		System.err.print("  ");
	}

/*	MAIN...
 */
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
//		showAll();
		computeNeighbors();
		computeNotes();
//dumpAll();
//dump(0,0);dump(4,1);
		prettyPrint();
//Container con = new Container(0,8,COL);
//assumes s4.sdk, has 4 cycle...
Container con = new Container(7,7,BLK);         // <<=== CONTAINER
		System.out.println(con.identity());
//		con.cycleTest();     <<== BAD
//		System.out.println("~50 uniques: "+con.uniques());
//		System.out.println("~51 matchedPair: "+con.matchedPair());
    }
}
