# sudukoAid
Just an aid, not a full game player.
2 java source files (Cell.java, Aid.java)
One text file with test case, a specific sudoku (s1.txt)
STATUS: Computes the notations for each cell, list of possible entries.
Compile and test:  
	javac *.java
	java Aid
		Result: Usage
	java Aid s1.txt
		Result: The pretty print of the Suduko with notations for empty cells
	java Aid s1.txt s1.notes
		Same output but into created file s1.notes
(Sept/17/2021)
