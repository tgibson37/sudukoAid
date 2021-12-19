import java.io.*;

class AidTTY implements AidPresentation{
	static AidTTY me = new AidTTY();  // self instantiate
	private AidTTY(){ super(); }          // enforce singleton
	public static AidPresentation instance() {  // public access
		return (AidPresentation)me;
	}
	
/**	interface implementation for TTY
*/
	public String presentationStyle(){ return "tty style..."; }
    public void out(String s){System.out.print(s);}
    public void outln(String s){System.out.println(s);}
    public void displayBoard(){ prettyPrint(); };   
    public void updateNotes(Cell c){ 
		Aid.computeNotes();
		prettyPrint(); 
    };   

// created all 81 Cells, row-col wise
	public void createCells(){
    	Aid.puzl = new Cell[9][9];
		for(int row=0; row<9; ++row)
			for(int col=0; col<9; ++col)
				Aid.puzl[row][col] = new Cell(row,col);
	}
// satisfies the interface...
	public void buildBoard(){
	}
	//   renders the board, tty style
	void prettyPrint(){	             
		System.out.println("=======================================================");
		for(int row=0; row<9; ++row){
			System.out.format("#");
			for(int col=0; col<9; ++col){
				System.out.print(Aid.puzl[row][col].notesAsString());
			}
			System.out.println("");
			System.out.format("#");
			for(int col=0; col<9; ++col){
				System.out.print(Aid.puzl[row][col].valueAsString());
			}
			System.out.println("");
			if(row%3 != 2)System.out.println("#------------------------------------------------------");
			else System.out.println("#======================================================");
		}
	}
/*
private void cmdSet(int[] x){
		int value = x[3];
		Cell c = Aid.getCellX(x);
		if(c==null)return;
		if(Aid.optv)System.out.println(c.toString()+" set to "+value);
		if(value<0 || value>9)System.out.println("bad value");
		else {
			String bad = c.setValue(value);
			if(bad!=null){
				System.out.println(bad);
			}
			else{
				Aid.computeNotes();
				Aid.hints();
				displayBoard();
			}
		}
	}
*/
public void doThePuzzle(){
		prettyPrint();
		dialog();
	}
	private static void cmdDump(int row, int col){
	} 

	private void dialog(){
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
			for(int i=1;i<cmd.length();++i) {
				x[i-1]=cmd.charAt(i)-48; // ascii digit to int
			}
			switch(cmd.charAt(0)){
				case 'q': System.exit(0);
				case 's': Aid.cmdSet(x[0],x[1],x[2]); break;
				case 'd': cmdDump(x[0],x[1]); break;
				default: continue;
			}
		}
	}
}
