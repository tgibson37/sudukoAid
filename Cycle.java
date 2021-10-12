import java.io.*;
import java.util.*;
class Cycle
{
	private int V;
	private Integer[] cycle;
	private int recording = -1;	 // counts recording during pass 2
	private LinkedList<Integer>[] adj;
	private Boolean visited[];
	private int firstOfCycle = -1;
	private int firstRecorded = -1;

	Cycle(int v)
	{
		adj = new LinkedList[v];
		V = v;
		for(int i=0; i<v; ++i){	  // all inner lists...
			adj[i] = new LinkedList<Integer>();
		}
		cycle = new Integer[v];	  // ordered visits during pass 2
		visited = new Boolean[v];
	}

	Integer[] getCycle(){
		int len = recording>=0 ? recording : 0;
		Integer[] tidy = new Integer[len];
		for(int i=0; i<len; ++i) tidy[i]=cycle[i];
		return tidy;
	}
 
	void addEdge(int v,int w)
	{
		adj[v].add(w);	 // bi directional for undirected
		adj[w].add(v);	 //	   graph
	}
	
/*	Pass one: does a cycle exist? If so, sets firstOfCycle for pass two.  
 *	Pass two records the cycle. -1 means not set, >=0 means set.
 */
	Boolean isCyclicUtil(int v, int parent) {
		if(recording<0 && v==firstOfCycle){
			recording = 0;   // turns on recording
		}
		visited[v] = true;
		
		if(recording>=0 && v!=firstRecorded) {  // avoids possible duplicate
			cycle[recording++] = v;
			if(recording==1) {
				firstRecorded = v;
			}
		}
		else {   // handy for println debugging
		}
		Integer nextv;
		Iterator<Integer> it = adj[v].iterator();
		while (it.hasNext()) {
			nextv = it.next();
			if (!visited[nextv]) {
				if (isCyclicUtil(nextv, v))
				return true;
			}
			else if(nextv != parent) {   // VISITED hit, we have cycle
				if(firstOfCycle < 0){
					firstOfCycle = nextv;
					return true;
				}
				else return true;
			}
		}
		return false;
	}

	// return search start that succeeded, isCyclicUtil sets firstOfCycle 
	private int setFirstVisited(){
		for (int u = 0; u < V; u++) {  // try every node as search start
			clearVisited();
			if (isCyclicUtil(u, -1)) return u;
		}
		return -1;	// tried all, no cycle
	}
	private int getFirstVisited(){
		return firstOfCycle;
	}

	// Returns true if the graph contains a cycle, else false.
	Boolean isCyclic() {
		Boolean visited[] = new Boolean[V];
		int goodStart = setFirstVisited();
		if(goodStart<0)return false;
		clearVisited();
		if (isCyclicUtil(goodStart, -1)) return true;
		return false;  // tried all, no cycle
	}
	
	// erase visit marks
	private void clearVisited(){
		for(int i=0; i<V; ++i)visited[i]=false;
	}
}
/*******************
	// test above methods
	public static void main(String args[])
	{
		// Create a graph given
		// in the above diagram
		  Cycle g1 = new Cycle(5);
		g1.addEdge(1, 0);
		g1.addEdge(0,
		g1.addEdge(0, 3);
		g1.addEdge(3, 4);
		g1.dumpGraph("G1");
		if (g1.isCyclic()){
			System.out.print("Cycle g1 contains cycle");
			Integer[] cycle = g1.getCycle();
			for(int i=0; i<cycle.length; ++i)
				System.out.print(" "+cycle[i]);
			System.out.println("	should be 012");
		}
		else
			System.out.println("Cycle g1 doesn't contain cycle");

// g2
		Cycle g2 = new Cycle(3);
		g2.addEdge(0, 1);
		g2.addEdge(1, 2);
		g2.dumpGraph("G2");
		if (g2.isCyclic())
			System.out.println("Cycle g2 contains cycle");
		else
			System.out.println("Cycle g2 doesn't contain cycle");

// g3  transition from pass 1 to pass 2...
		Cycle g3 = new Cycle(5);
		g3.addEdge(0, 1);
		g3.addEdge(1, 2);
		g3.addEdge(2, 4);
		g3.addEdge(2, 3);
		g3.addEdge(3, 4);
		g3.dumpGraph("G3");
		if (g3.isCyclic()){
			System.out.print("g3 contains cycle: ");
			Integer[] cycle = g3.getCycle();
			for(int i=0; i<cycle.length; ++i)System.out.print(" "+g3.cycle[i]);
			System.out.println("	should be 243");
		}
		else
			System.out.println("g3 doesn't contain cycle");

	}

	private void dumpGraph(String name){
		System.out.println("\ngraph "+name+"...");
		for(int i=0; i<V; ++i) {
			System.out.println("cell "+i+" to: "+adj[i]);
		}
	}
}
********************** */
// This code is contributed by Aakash Hasija.
// Modified by Tom Gibson to retrieve the cycle.
