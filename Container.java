import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *	Container.java -- looks for patterns in one container
 */
public class Container{
	public static final int ROW = 1;
	public static final int COL = 2;
	public static final int BLK = 3;
	int row, col, type;
	Cell[] cells = new Cell[9];
	String id;

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
	public String identity(){return id;}
	public Container serial(int num)
	{  //rows, then cols, then blocks
		int type = num/9+1;
		int particular = num%9;
		int p1=particular/3;
		int p2=particular%3;
		switch(type){
			case 1: return new Container(particular,0,ROW);
			case 2: return new Container(0,particular,COL);
			case 3: return new Container (p1,p2,BLK);
		}
		return null;
	}
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
	public Set<Cell> uniques(){
		HashSet<Cell> uu = new HashSet<Cell>(); 
		for(int i=0; i<9; ++i){
			if(cells[i].notes.size()==1){
				uu.add(cells[i]);
			}
		}
		return uu;
	}
/*	From cells with notes size ==2, return a subset 
 *	that form a cycle, or null for no cycle.
 */
	public HashSet<Cell> cycleTest(){
		HashSet<Cell> candidates = pairs();
		int num = candidates.size();
System.out.println("candidates: ("+num+"): "+candidates);
		if(num<3)return null;
		Cycle g1 = new Cycle(9);
		for( Cell can : candidates ){
			int fromto[] = can.notes();
			g1.addEdge(fromto[0],fromto[1]);
        }
        g1.isCyclic();
    	return null;   // TEMPORARY
    
    }
	public HashSet<Cell> pairs() {
		HashSet<Cell> pp = new HashSet<Cell>();
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
					}
				}
			}
		}
		return uu;
	}
}