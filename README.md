Connect Four - Assignment Specification
Overview
Connect Four is a two-player connection game where players take turns dropping colored discs into a vertically suspended grid. The objective is to be the first to form a line of four of one's own discs.
Game Rules

Played on a 6x7 grid
Players use X and O symbols
Win by connecting four symbols horizontally, vertically, or diagonally

Marking Scheme
Total Marks: 25

Functional correctness
Sound Object-Oriented design
Clean, tidy, and commented code

Project Sections
Part 1: Player and Disc Classes [5 marks]
Disc Class

Attributes:

Symbol (X or O)
Position in the grid



Player Class

Private attributes:

Name
Symbol
Reference to the game grid


Constructor takes player name and symbol
takeTurn() method for player actions

Part 2: Grid Class [5 marks]

2D array representation of the grid
Methods:

Initialize 6x7 grid
Drop a disc into a specified column
Check if grid is full
Check for win conditions



Part 3: Game Logic [5 marks]

Player name and symbol input
Main game loop
takeTurn() method implementation
Game state updates:

Validate moves
Announce game status (ongoing/won)



Part 4: Game Visualization [5 marks]

toString() method for grid representation
Console display of current grid state
Properly aligned cell formatting

Part 5: Optional Enhancements [5 marks]

Undo last move
Save and load game state
Graphical User Interface (GUI)

Notes

Partial marks will be awarded
You do not need to complete the entire project to receive marks

Recommended Approach

Implement classes incrementally
Test each component thoroughly
Focus on code quality and design principles
