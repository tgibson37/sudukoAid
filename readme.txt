# sudukoAid
Just an aid so far, not a full game player. 
The "aid" is computing the pencil notes. Of course the Suduko Guy recommends
using notes very lightly, but I need all of them to look for patterns.

The aid will also look for two patterns: matched pairs and uniques (i.e. just
one note.) You can set a value into an unsolved cell and the notes are 
recomputed.

Read the Usage for options and commands. 

6 java source files (Cell.java, Aid.java, AidTTY.java, AidGraphic.java,
	Container.java)
5 text files with test cases. Each is a specific sudoku (*.sdk)
	These follow explicit syntax, 9 lines, 90 characters
STATUS: Computes the notations for each cell, list of possible entries.
Compile and test:  
	javac *.java
	java Aid
		Result: Usage
	java Aid s1            <<== reads s1.sdk
		Result: The graphic display. Looks pretty, but 
	java Aid s1 -tty       <<== reads s1.sdk
		Result: The pretty print of the Suduko with notations for empty cells,
			and two tty commands: q (quit), s257 (set cell 2,5 to value 7.)

(Nov/20/2021)
T. A. Gibson
