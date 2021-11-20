# sudukoAid
Currently just an aid, not a full game player. But the new set command
makes playing possible. Mainly it computes all the "pencil notes." Graphic
version displays these as buttons, but they are not hooked up yet.
CONTENT:
Six java source files and readme.txt: how to compile/run. I use command line.
You'll need a JDK for now (command line: javac & java.) A jar is on my TODO list.
Five text files with test cases, specific sudoku puzzles: (sN.sdk, N=1..5).
STATUS:
  default graphic option: Shows the puzzle. Buttons not functional yet.
  -tty option: text version of puzzle with pencil notes. Two commands: 
	sRCV -- set cell at row R, column C (both 1..9 digits) to value V 
	q    -- quit
Future:
  fully playable graphic version (buttons hooked up.)
  hints where to look for easiest move.
