# sudukoAid
Just an aid, not a full game player. 
The "aid" is computing the pencil notes. Of course the Suduko Guy recommends
using notes very lightly, but I need all of them to look for patterns.

The aid will also look for two patterns: matched pairs and uniques (i.e. just
one note.) You can set a value into an unsolved cell and the notes are 
recomputed.

Read the Usage for options and commands. 

4 java source files (Cell.java, Aid.java, Container.java, Cycle.java)
5 text files with test cases. Each is a specific sudoku (*.sdk)
	These follow explicit syntax, 9 lines, 90 characters
STATUS: Computes the notations for each cell, list of possible entries.
Compile and test:  
	javac *.java
	java Aid
		Result: Usage
	java Aid s1       <<== reads s1.sdk
		Result: The pretty print of the Suduko with notations for empty cells
	java Aid s1 s1.notes
		Same output but into created file s1.notes

Long notes, >5, spoil the pretty print a bit, but its still readable. So that's
a feature, not a bug.

All System.out are done in Aid.java, so it is ready for an interface that can
be used by a graphic version.

(Oct/23/2021)
T. A. Gibson
