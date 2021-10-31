class AidTTY implements AidPresentation{
	static AidTTY me = new AidTTY();  // self instantiate
	private AidTTY(){ super(); }          // enforce singleton
	public static AidPresentation instance() {  // public access
		return (AidPresentation)me;
	}
	
/**	interface implementation for TTY
 */
    public void out(String s){System.out.print(s);}
    public void outln(String s){System.out.println(s);}
    public void displayBoard(){
    	System.out.println("TTY displayAll");
    	prettyPrint();
    };
	void prettyPrint(){	//I   render()
		outln(
"=======================================================");
		for(int row=0; row<9; ++row){
			System.out.format("#");
			for(int col=0; col<9; ++col){
				out(Aid.puzl[row][col].prettyPrintNotes());
			}
			outln("");
			System.out.format("#");
			for(int col=0; col<9; ++col){
				out(Aid.puzl[row][col].prettyPrintValue());
			}
			outln("");
			if(row%3 != 2)outln(
"#------------------------------------------------------");
			else outln(
"#======================================================");
		}
	}
}
