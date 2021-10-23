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
	private static Boolean opti = false;  // show inputs
	private static Boolean optv = false;  // verbose
	private static Boolean optu = false;  // hints, unique
	private static Boolean optm = false;  // hints, matched pair
	private static Boolean optp = false;  // hints, pairs

    public static void Usage(){
    	System.out.println("Usage: java Aid [options] <inputfile> [<optionaloutputfile>]");
    	System.out.println("Just the input file's name, not the sdk type.");
    	System.out.println("Options:");
//    	System.out.println("\t-p -- show all pairs");
    	System.out.println("\t-m -- show matched pair hints");
    	System.out.println("\t-u -- show unique hints");
//    	System.out.println("\t-v -- verbose, mainly debug dumps");
    	System.out.println("Commands:   (the prompt is >> )");    	
    	System.out.println("	q -- quit");
    	System.out.println("	sRCV (3 digits: row, column, value)");
    	System.out.println("	Example: s472 sets cell at row 4, column 7 to value 2");
    	System.out.println("");
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
		for(int i=0; i<89; ++i) {
			if(bytes[i]==10)continue;   // skip newlines
			if(bytes[i]==32)continue;   // skip spaces
			int row = i/10;
			int col = i%10;
			puzl[row][col].initValue(bytes[i]-48);
			puzl[row][col].setOriginal();
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
	static void hints(){
		if(optu){ 
			hintu();
		}
		if(optm){ 
			for(int i = 0; i<27; ++i){
				Container con = Container.serial(i);
				String hint = con.hintm();
				if( hint!=null )System.out.println(hint);
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
			System.out.println("Unique:"+cell);
		}
	}

	static void prettyPrint(){	//I   render()
		System.out.println(
"=======================================================");
		for(int row=0; row<9; ++row){
			System.out.format("#");
			for(int col=0; col<9; ++col){
				System.out.print(puzl[row][col].prettyPrintNotes());
			}
			System.out.println("");
			System.out.format("#");
			for(int col=0; col<9; ++col){
				System.out.print(puzl[row][col].prettyPrintValue());
			}
			System.out.println("");
			if(row%3 != 2)System.out.println(
"#------------------------------------------------------");
			else System.out.println(
"#======================================================");
		}
	}
	private static Cell getCellX(int[] x){
		int row = x[1];
		int col = x[2];
		if(row<1 || row>9 || col<1 || col>9) {
			System.err.println("Bad command");return null;
		}
		return puzl[row-1][col-1];
	}
	private static void cmdSet(int[] x){
		int value = x[3];
		Cell c = getCellX(x);
		if(c==null)return;
		if(optv)System.out.println(c.toString()+" set to "+value);	//I msg
		if(value<0 || value>9)System.out.println("bad value");  //I	msg
		else {
			c.setValue(value);
			computeNotes();
			hints();
			prettyPrint();
		}
	} 

	private static void cmdDump(int[] x){
	} 

	private static void dialog(){
		String cmd="";
		int[] x = new int[9];
        BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));
		while(x[0] != 'q'){
			System.out.print(">> ");
			try{
				cmd = reader.readLine();
			}catch(Exception e){
				System.err.println(e.toString());
				System.exit(1);
			}
			for(int i=0;i<cmd.length();++i) 
				x[i]=cmd.charAt(i)-48; // ascii digit to int
			switch(cmd.charAt(0)){
				case 'q': System.exit(0);
				case 's': cmdSet(x); break;
				case 'd': cmdDump(x); break;
				default: continue;
			}
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
// Arguments...
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
    for(int i=0; i<opts.length; ++i) {
    	String opt = opts[i];
    	for(int j=1; j<opt.length(); ++j) {
    		char op = opt.charAt(j);
    		switch(op){
    		case 'i': opti = true; break;
    		case 'u': optu = true; break;
    		case 'm': optm = true; break;
    		case 'p': optp = true; break;
    		case 'A': optu = optm = optp = true; break;
    		default: System.out.println("bad option "+op);
    		}
    	}
    }
// Process...
//System.err.println("~240");
        createCells();
//System.err.println("~242");
		readInitFile(filename);
//System.err.println("~244");
		processValues();
//System.err.println("~246");
		if(opti)showAll();
//System.err.println("~248");
		computeNeighbors();
//System.err.println("~250");
		computeNotes();
//System.err.println("~252");
		hints();
//System.err.println("~254");
		prettyPrint();
//System.err.println("~256");
		dialog();
    }
}
