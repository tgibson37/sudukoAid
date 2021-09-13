import java.util.*;
/**
 *  Cell.java
 */
public class Cell{
	int row,col;
    int value;
    ArrayList<Cell> nbr = new ArrayList<Cell>();
    HashSet<Integer> notes = new HashSet<Integer>();
    int n;

    //public void setWriteTo(java.lang.PrintStream ps){System.out=ps;}
    
    public Cell(int r, int c){row=r;col=c;}
    public void setValue(int v){value=v;}
    public int getValue(){return value;}
    public String toString(){
    	return Integer.toString(row)
    		+","+Integer.toString(col)
    		+"("+notes.toString()+")"
    		+"="+Integer.toString(value);
    }
    public void computeNeighbors(Cell[][] c){
    	//block
    	int r0 = (row/3)*3;
    	int c0 = (col/3)*3;
    	for(int i=r0; i<r0+3; ++i){
    		for(int j=c0; j<c0+3; ++j){
    			if(i==row && j==col)continue;
     			nbr.add(c[i][j]);
     		}
    	}
    	//row
    	for(int j=0; j<9; ++j){
   			if(j==col)continue;
    		nbr.add(c[row][j]);
    	}
    	//column
    	for(int i=0; i<9; ++i){
    		if(i==col)continue;
    		nbr.add(c[i][col]);
    	}
    }
    public void computeNotes(Cell[][] c){
    	if(value==0){
			for(int i=1; i<10; ++i)notes.add(new Integer(i));
			for(Cell cell : nbr){
				notes.remove(new Integer(cell.value));
			}
    	}
    }
	public void prettyPrintNotes(){
		int spaces = 8;
    	Iterator<Integer> nit = notes.iterator();
		while(nit.hasNext()) {
			Integer itgr = nit.next();
			System.out.format("%1d",itgr);
			--spaces;
		}
		while(spaces-- > 1)System.out.format(" ");
		prettyVertical(col);
    }
    public void prettyPrintValue(){
    	System.out.format("   %1d   ",value);
    	prettyVertical(col);
    }
    public void prettyVertical(int col) {
    	if(col%3 != 2 ) System.out.format("|");
    	else System.out.format("#");
    }
}
