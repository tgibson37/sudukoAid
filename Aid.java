import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

interface AidPresentation{
	void outln(String s);
	void out(String s);
	void createCells();
	void buildBoard();
	void displayBoard();
	String presentationStyle();
	void doThePuzzle();
}

/**
 *	Suduko aid. Input: a file of type sdku with exactly 9 lines, each 
 *	exactly 9 characters plus a newline or carriage return. 90 bytes total.
 *	Use spaces or zeros for empty cells, numerals elsewhere.
 *	Example: s1.dku.
 */
public class Aid {
	static AidPresentation presentation;
	static String currentPath;
    static byte[] bytes;
	public static final int ROW = 1;
	public static final int COL = 2;
	public static final int BLK = 3;
	private static Boolean opti = false;  // show inputs
	public static Boolean optv = false;   // show values
	private static Boolean optu = false;  // hints, unique
	private static Boolean optm = false;  // hints, matched pair
	private static Boolean optp = false;  // hints, pairs
    public static Cell[][] puzl;

    public static void Usage(){
    	System.out.println("Usage: java Aid [options] <inputfile> [<optionaloutputfile>]");
    	System.out.println("\tJust the input file's name, not the sdk type.");
    	System.out.println("Options:");
//    	System.out.println("\t-p -- show all pairs");
    	System.out.println("\t-m -- show matched pair hints");
    	System.out.println("\t-u -- show unique hints");
    	System.out.println("\t-i -- show input values");
    	System.out.println("\t-tty -- Use tty output, REQUIRED until graphics work");
//    	System.out.println("\t-v -- verbose, mainly debug dumps");
    	System.out.println("Commands:   (the prompt is >> )");    	
    	System.out.println("	q -- quit");
    	System.out.println("	sRCV (3 digits: row, column, value)");
    	System.out.println("	Example: s472 sets cell at row 4, column 7 to value 2");
    	System.out.println("");
    }
    
    public static void outln(String s){ presentation.outln(s); } 
    public static void out(String s){ presentation.out(s); } 

// MAINLINE...
	public static Cell getCellX(int[] x){
		int row = x[1];
		int col = x[2];
		if(row<1 || row>9 || col<1 || col>9) {
			System.out.println("Bad command");return null;
		}
		return Aid.puzl[row-1][col-1];
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
// ASSUMES each line is EXACTLY 10 bytes. 
	static void processSeeds(){
//System.err.println("Aid~78");
		for(int i=0; i<89; ++i) {
			if(opti)System.out.printf("%c",(bytes[i]));
			if(bytes[i]==10)continue;   // skip newlines
			if(bytes[i]==13)continue;   // skip newlines
			if(bytes[i]==32)continue;   // skip spaces
			int row = i/10;
			int col = i%10;
			int value = bytes[i]-48;
//System.err.print(""+i);
//System.err.print(": "+row+""+col+"="+value+";  ");
			puzl[row][col].seedValue(value);
			puzl[row][col].setOriginal();
		}
	}
	static void showValues(){
		for(int row=0; row<9; ++row){
			for(int col=0; col<9; ++col){
				System.out.print(puzl[row][col].value+" ");
			}
			System.out.println("");
		}
	}
	//debug aid, prereq: Cell puzl[9][9] fully populated.
	public static void dumpRCV(String label){
		System.err.println(label);
		for(int r=0; r<9; ++r) {
			for(int c=0; c<9; ++c){
				Cell cl = Aid.puzl[r][c];
				System.err.print(" "+cl.row+""+cl.col+" "+cl.value);
			}
			System.err.println("");
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
//System.err.print("Aid~102: "+row+""+col+":  ");
				puzl[row][col].computeNotes();
			}
		}
	}
	static void hints(){
		if(optu){ 
			hintu();
		}
		if(optm){ 
			for(int i = 0; i<27; ++i){
				Container con = Container.serial(i);
				String hint = con.hintm();
				if( hint!=null )outln(hint);
			}
		}
	}
	public static Set<Cell> uniques(){
		HashSet<Cell> uu = new HashSet<Cell>();
		for(int i=0; i<9; ++i){
			for(int j=0; j<9; ++j){
				if(puzl[i][j].notes.size()==1){
					uu.add(puzl[i][j]);
				}
			}
		}
		return uu;
	}
	private static void hintu(){
		Set<Cell> uq = uniques();
		Iterator it = uq.iterator();
		while(it.hasNext()){
			Cell c1 = (Cell)it.next();
			String cell = c1.rowColPretty();
			outln("Unique:"+cell);
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
		System.err.println("");
	}

/*	MAIN
 */
	public static void main(String[] mingled) throws IOException{
		String filename=null;
// sort arguments and options...
		Arguments arguments = new Arguments(mingled); 
		String[] args = arguments.getArgs();
		String[] opts = arguments.getOpts();

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
		presentation = AidGraphic.instance();  //default presentation
		for(int i=0; i<opts.length; ++i) {
			String opt = opts[i];
			if(opt.equals("-tty")){
				presentation = AidTTY.instance();    // overide
			}
//System.err.println("Aid~185: "+presentation.presentationStyle());
//set all presentations here   ^^^^^^
			for(int j=1; j<opt.length(); ++j) {
				char op = opt.charAt(j);
				switch(op){
				case 'i': opti = true; break;
				case 'u': optu = true; break;
				case 'm': optm = true; break;
				case 'p': optp = true; break;
				case 'A': optu = optm = optp = true; break;
				case 't':
				case 'y': break;
				default: outln("bad option "+op);
				}
			}
		}
// Process...
System.err.println("~200");
        presentation.createCells();
System.err.println("~202");
        presentation.buildBoard();
System.err.println("~204");
		readInitFile(filename);
System.err.println("~206");
		processSeeds();
System.err.println("~208");
		if(optv)showValues();
System.err.println("~210");
		computeNeighbors();
System.err.println("~212");
		computeNotes();
System.err.println("~214");
		hints();
System.err.println("~218");
		presentation.doThePuzzle();
    }
}
