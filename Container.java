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

	public Container(int rw, int cl, int tp){
		row=rw; col=cl; type=tp;
		if(type==ROW){
			for(int rr=0; rr<9; ++rr)cells[rr]=Aid.c[rr][col];
		}
		if(type==COL){
			for(int cc=0; cc<9; ++cc)cells[cc]=Aid.c[row][cc];
		}
		if(type==BLK){
	    	int r0 = (row/3)*3;
	    	int c0 = (col/3)*3;
			for(int i=0; i<3; ++i){
				for(int j=0; j<3; ++j){
					cells[3*i+j]=Aid.c[i+r0][j+c0];
				}
			}
		}
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
	public Set uniques(){
		HashSet<Cell> uu = new HashSet<Cell>(); 
		for(int i=0; i<9; ++i)
			if(cells[i].notes.size()==1)
				uu.add(cells[i]);
		return uu;
	}
	public Set matchedPair(){
		HashSet<Cell> uu = new HashSet<Cell>(); 
		for(int i=0; i<9; ++i){
			TreeSet inotes = cells[i].notes;
			if(inotes.size() == 2) {
				for(int j=i+1; j<9; ++j) {
					TreeSet jnotes = cells[j].notes;
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