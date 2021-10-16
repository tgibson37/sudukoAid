# sudukoAid
Just an aid, not a full game player.
4 java source files (Cell.java, Aid.java, Container.java, Cycle.java)
4 text files with test case, a specific sudoku (*.sdk)
	These follow explicit syntax, 9 lines, 91 characters
STATUS: Computes the notations for each cell, list of possible entries.
Compile and test:  
	javac *.java
	java Aid
		Result: Usage
	java Aid s1       <<== reads s1.sdk
		Result: The pretty print of the Suduko with notations for empty cells
	java Aid s1 s1.notes
		Same output but into created file s1.notes
(Oct/15/2021)
T. A. Gibson
