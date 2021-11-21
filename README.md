# sudukoAid
Intended as just an aid, not a full game player. But the new set command
makes playing possible. Mainly it computes all the "pencil notes."
Three java source files (Aid.java, Container.java, Cell.java), and
readme.txt: how to compile/run. You'll need javac and java for now, a jar
is on my TODO list.
Four text files with test cases, specific sudoku puzzles: (sN.txt, N=1..4).
STATUS: Prints text version of puzzle with pencil notes. Two commands: 
	sRCV -- set cell at row R, column C (both 1..9 digits) to value V 
	q    -- quit
Future: hints where to look for easiest move.
