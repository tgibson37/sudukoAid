import java.util.*;
/**
 *  Cell.java
 */
public class Cell{
	int row,col;
    int value;
    ArrayList<Cell> neighbor = new ArrayList<Cell>();
    TreeSet<Integer> notes = new TreeSet<Integer>();
    Boolean seed = false;
    
    public Cell(int r, int c){
    	row=r;col=c;
//if(r<3)if(c<3)System.err.print("Cell~14 rc"+r+""+c);
    }
// initialization used using puzl inputs
    public void seedValue(int v){
    	value=v;
    	seed=true;
    }
    public int getValue(){ return value; }
// used by set command
    public String setValue(int v){
    	if(!seed){
			int[] n = getNotes();
			for(int i=0; i<n.length; ++i){
				if(v==n[i]){
					value=v;
					return null;
				}
			}
			return "Value conflict";
		}
    	return "Cell value is seed";
    }
//recommended tests: Use s1: s177 conflict, s133 seed, s183 OK
    
/**	
 *	@return String formatted to show place, value, and notes
 */
    public String toString(){
    	return Integer.toString(row)
    		+","+Integer.toString(col)
    		+"("+notes.toString()+")"
    		+"="+Integer.toString(value);
    }
    public String rowColPretty(){	
    	return " "+(row+1)+" "+(col+1); 
    }
	public int[] getNotes(){
    	List<Integer> arr = new ArrayList<>(notes);
		int[] nts = new int[arr.size()];
		for(int i = 0; i<nts.length; i++){
			nts[i] = arr.get(i);
		}
		return nts;
    }
/* Used by GCell for testing
    public void setNotes(TreeSet<Integer> nts){
    	notes=nts;
System.out.println("Cell~61 notes: "+nts);
    }
*/
// initializers, used my Aid's mainline after puzl[][] is defined
    public void computeNeighbors(Cell[][] c){
    	//block
    	int r0 = (row/3)*3;
    	int c0 = (col/3)*3;
    	for(int i=r0; i<r0+3; ++i){
    		for(int j=c0; j<c0+3; ++j){
    			if(i==row && j==col)continue;
     			neighbor.add(c[i][j]);
     		}
    	}
    	//row
    	for(int j=0; j<9; ++j){
   			if(j==col)continue;
    		neighbor.add(c[row][j]);
    	}
    	//column
    	for(int i=0; i<9; ++i){
    		if(i==row)continue;
    		neighbor.add(c[i][col]);
    	}
	}
// used by Aid after reading input file
    public void setOriginal(){
    	seed=true;
    }
// used by Aid's init, and by main play loop after each move
	public void computeNotes(){
    	if(value==0){
			for(int i=1; i<10; ++i)notes.add(new Integer(i));
			for(Cell cell : neighbor){
				notes.remove(new Integer(cell.value));
			}
    	}
    	else notes = new TreeSet<Integer>();   // empty list
    }
    public Iterator<Integer> getNotesIterator(){ return notes.iterator(); }
	public String notesAsString(){
		int spaces = 6;  // includes the trailing vertical
		int i=0;
		byte[] bytes = new byte[9];
		Iterator<Integer> nit = getNotesIterator();
		while(nit.hasNext()) {
			int aNote = nit.next()+48;
			bytes[i++] = (byte)aNote; 
			--spaces;
		}
		while(spaces-- > 1)bytes[i++] = ' ';
		bytes[i] = (byte)prettyVertical(col);
		String s = new String(bytes);
		return s;
    }
    public String valueAsString(){
    	char val = (char)(getValue()+48);
    	return "  " + val + "  " + prettyVertical(col);
    }
    public char prettyVertical(int col) {
    	if(col%3 != 2 ) return '|';
    	else return '#';
    }
// debug tools
	public void dump(){
		System.err.print("C"+row+col);
		if(value==0)System.err.print(notes);
		else System.err.print("="+value);
	}
	public void dumpNbr(){
		System.err.print(row+""+col+": ");
		for(Cell cell : neighbor){
			System.err.print(cell.row);
			System.err.print(cell.col+" ");
		}
		System.err.print("\n");
	}
	public void dumpNbrVal(){
		System.err.print(row+""+col+":");
		for(Cell cell : neighbor){
			System.err.print("  "+cell.value);
		}
		System.err.print("\n");
	}
}
