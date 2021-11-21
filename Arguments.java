import java.util.*;

public class Arguments {
	List<String> ar = new ArrayList<>();
	List<String> op = new ArrayList<>();
	public Arguments( String args[] ){
		for(int i=0; i<args.length; ++i)
		if( args[i].charAt(0)=='-'){
			op.add(args[i]);
		} else {
			ar.add(args[i]);
		}
	}
	public List<String> getArgs(){ return ar; }
	public String[] getArgsAsArray(){ return foo(ar); }
	public List<String> getOpts(){ return op; }
	public String[] getOptsAsArray(){ return foo(op); }
	private String[] foo(List<String> ls){
		String bar[] = new String[ls.size()];
		for(int i=0; i<bar.length; ++i) bar[i]=ls.get(i);
		return bar;
	}
// test...  Try:  $ java Arguments Foo -x Bar -y Baz -99
	public static void main(String[] args){
		Arguments a = new Arguments(args);
		
		System.out.print("args: "+a.getArgs());
			System.out.print(", as array: "+a.getArgsAsArray()[0]+"...");
			System.out.println(", "+a.getArgsAsArray()[1]+"...");

		System.out.print("opts: "+a.getOpts());
			System.out.print(",  as array: "+a.getOptsAsArray()[0]);
			System.out.println(", "+a.getOptsAsArray()[1]+"...");
	}
}
