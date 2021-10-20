import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *	Container.java -- looks for patterns in one container. Container objects
 *	are transient, used analytically. They are not part of the game structure
 *	which is just Aid.puzl array, and the 81 Cells.
 */
class Container{
	public static final int ROW = 1;
	public static final int COL = 2;
	public static final int BLK = 3;
	int row, col, type;
	Cell[] cells = new Cell[9];
	String id;

/**
 *	@args A cell, and which of the three types of its containers.
 *	Builds String id returned by toString(), "Container(row,col,TYPE)".
 */
	public Container(int rw, int cl, int tp){
		row=rw; col=cl; type=tp;
		id = "Container(" + row + "," + col + ",";
		if(type==ROW){
			id += "ROW)";
			for(int cc=0; cc<9; ++cc)cells[cc]=Aid.puzl[row][cc];
		}
		else if(type==COL){
			id += "COL)";
			for(int rr=0; rr<9; ++rr)cells[rr]=Aid.puzl[rr][col];
		}
		else if(type==BLK){
			id += "BLK)";
	    	int r0 = (row/3)*3;
	    	int c0 = (col/3)*3;
			for(int i=0; i<3; ++i){
				for(int j=0; j<3; ++j){
					cells[3*i+j]=Aid.puzl[i+r0][j+c0];
				}
			}
		}
		else{
			System.err.print("illegal Container type:"+type);
			System.exit(1);
		}
	}
	public String toString(){return id;}
	
/**	@param num Containers are conceptually numbered 0..26,
 *		all lines, then all rows, then all blocks
 *	@return specified Container.
 */
	public static Container serial(int num)
	{  //rows, then cols, then blocks
		int type = num/9+1;
		int particular = num%9;
		int p1 = particular/3;
		int p2 = particular%3;
		switch(type){
			case ROW: return new Container(particular,0,ROW);
			case COL: return new Container(0,particular,COL);
			case BLK: return new Container (p1,p2,BLK);
		}
		return null;
	}
// debug aid, more info than id, dumps all cells on one line.
	public void dump(){
		System.err.print("\n"+row+""+col);
		if(type==ROW)System.err.print("ROW: ");
		else if(type==COL)System.err.print("COL: ");
		else if(type==BLK)System.err.print("BLK: ");
		else System.err.print("???: ");
		for(int i=0; i<9; ++i){
			cells[i].dump();
			if(i<8)System.err.print(",");
		}
		System.err.println();
	}
	
// patterns follow: unique, pairs, matched pair, cycle
// Each examines the container and returns a Set<Cell> that are that pattern.
// Test the sets size()>0 for match, else ==0 for no match.


//MOVE TO NEW CLASS: HINTS, each below is a subclass
    public List<Cell> pairs() {
		ArrayList<Cell> pp = new ArrayList<Cell>();
		for( int i=0; i<9; ++i) {
			if(cells[i].notes.size()==2) { 
				pp.add(cells[i]);
			}
		}
		return pp;
	}
	public Set<Cell> matchedPair(){
		HashSet<Cell> uu = new HashSet<Cell>(); 
		for(int i=0; i<9; ++i){
			TreeSet<Integer> inotes = cells[i].notes;
			if(inotes.size() == 2) {
				for(int j=i+1; j<9; ++j) {
					TreeSet<Integer> jnotes = cells[j].notes;
					if( inotes.equals(jnotes) ){
						uu.add(cells[i]);
						uu.add(cells[j]);
						return uu;
					}
				}
			}
		}
		return uu;
	}
	public void hintm(){
		Set<Cell> mp = matchedPair();
		if( mp.size()>1 ){
			Iterator it = mp.iterator();
			Cell c1 = (Cell)it.next();
			Cell c2 = (Cell)it.next();
			String pair = c1.rowColPretty()+" & "+c2.rowColPretty();
			System.out.println("Matched pair:"+pair);
		}
	}
}
