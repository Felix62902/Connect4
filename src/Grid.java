import java.util.HashMap;

public class Grid {
    //2D array representing the grid
    private Disc[][] grid;
    private HashMap<Integer,Integer> columnDiscCount;
    //variables used for undo logic by saving the last inserted disk.
    private int recentCol;
    private int recentRow;

    public Grid(){
        //initialize an empty 6x7 grid
        grid = new Disc[6][7];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Disc('.',i,j); // default value '.' for empty cells
            }
        }
        columnDiscCount = new HashMap<>(); // tracks the count of each disc in each column to prevent overflow
        for (int col = 0; col < grid[0].length; col++) {
            columnDiscCount.put(col, 0); // Initialize column count to 0
        }
        this.recentCol = -1;
        this.recentRow = -1;
    }

    // Drops a disc into a specified column, return 0 if fail and Y coord if success
    public int dropDisc(int columnNum, char symbol){
        // If column is full;
        if (columnDiscCount.get(columnNum) >= grid.length) {
            return -1; // Column full
        } else {
            // place the disk in the lowest available row
            int row = grid.length - columnDiscCount.get(columnNum) - 1;
            grid[row][columnNum].updateSymbol(symbol); // Update the symbol
            columnDiscCount.put(columnNum, columnDiscCount.get(columnNum) + 1); // Increment disc count in column
            // if successful, save row and column num into variable
            this.recentRow = row;
            this.recentCol = columnNum;
            return row; // Return the row where disc was dropped
        }
    }

    //method returns true if grid is full
    public boolean checkGridFull(){
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j< grid[i].length; j++){
                if (grid[i][j].getSymbol()=='.'){
                    return false;
                }
            }
        }
        return true;
    }

    // method to check winning condition after each move
    public boolean checkWinConditions(Disc disc){
        return (this.checkHorizontalMatch(disc) || this.checkVerticalMatch(disc) || this.checkRightDiagonal(disc) || this.checkLeftDiagonal(disc));
    }

    //check for horizontal win
    public boolean checkHorizontalMatch(Disc disc) {
        int adjacent = 1; // Start counting with the current disc

        // Check left
        int leftX = disc.getXcoord() - 1;
        while (leftX >= 0){
            if(this.grid[disc.getYcoord()][leftX].getSymbol() == disc.getSymbol()){
                adjacent++;

            } else {
                break;
            }
            leftX -=1;

        }
        //check right
        int rightX = disc.getXcoord() + 1;
        while (rightX < grid.length){
            if(this.grid[disc.getYcoord()][rightX].getSymbol() == disc.getSymbol()){
                adjacent++;
            } else {
                break;
            }
            rightX++;
        }
        if (adjacent >= 4){
            return true;
        }
        return false;
    }

    public boolean checkVerticalMatch(Disc disc) {
        int adjacent = 1; // Start counting with the current disc

        //there is no need to check the disk above as it is impossible to have an existing disk above the most recent dropped disk
        // Check bottom
        int bottomY = disc.getYcoord() + 1;
        while (bottomY < grid.length){
            if(this.grid[bottomY][disc.getXcoord()].getSymbol() == disc.getSymbol()){
                adjacent++;
            } else {
                break;
            }
            bottomY++;
        }

        return adjacent >= 4;
    }

    /* check for right diagonal win
                  o
                o
              o
            o
    */
    public boolean checkRightDiagonal(Disc disc){
        int adjacent = 1;

        // check top
        int topX = disc.getXcoord() + 1;
        int topY = disc.getYcoord() - 1;
        while (topX < grid.length && topY >=0){
            if(this.grid[topY][topX].getSymbol() == disc.getSymbol()){
                adjacent++;
            } else {
                break;
            }
            topX++;
            topY--;
        }

        //check bottom
        int bottomX = disc.getXcoord() - 1;
        int bottomY = disc.getYcoord() + 1;
        while (bottomX >= 0 && bottomY < grid.length){
            if(this.grid[bottomY][bottomX].getSymbol() == disc.getSymbol()){
                adjacent++;
            } else {
                break;
            }
            bottomX--;
            bottomY++;
        }

        return adjacent >= 4;
    }


    public boolean checkLeftDiagonal(Disc disc){
        int adjacent = 1;
        /* o
             o
              o
                o
         */
        // check top
        int topX = disc.getXcoord() - 1;
        int topY = disc.getYcoord() -1;
        while (topX >= 0 && topY >= 0){
            if(this.grid[topY][topX].getSymbol() == disc.getSymbol()){
                adjacent++;
            } else{
                break;
            }
            topX--;
            topY--;
        }

        //check bottom
        int bottomX = disc.getXcoord() + 1;
        int bottomY = disc.getYcoord() + 1;
        while (bottomX < grid[disc.getYcoord()].length && bottomY < grid.length){
            if(this.grid[bottomY][bottomX].getSymbol() == disc.getSymbol()){
                adjacent++;
            } else {
                break;
            }
            bottomX++;
            bottomY++;
        }

        return adjacent >= 4;
    }

    // Enhancement feature: a method to undo the last move. (cannot be used if it's the first move of the game)
    public int undoLastMove(){
        // making sure undoing is possible ( it is not the first move of the game )
        if (this.recentCol != -1 && this.recentRow != -1){
            //if move already been undone
            if (this.grid[recentRow][recentCol].getSymbol() == '.')
                return 2; // Not allowing the player to undo again as can only Undo Once
            this.grid[recentRow][recentCol].updateSymbol('.');
            // remove one from the disc Counter
            columnDiscCount.put(recentCol,columnDiscCount.get(recentCol) -1);
            return 0;
        } else {
            return 1;
        }
    }

    public String toString() {
        /*instead of double for-looping over each grid cell
        and printing one by one, this allows the whole grid to be returned at once*/
        // Create a StringBuilder to accumulate the string
        StringBuilder sb = new StringBuilder();
        // Print the column numbers above the grid
        for (int n = 1; n <= grid[0].length; n++) {
            sb.append(String.format("%-6d", n));
        }
        sb.append("\n");

        // Iterate over the grid and build the string representation for each row
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // Format each cell and append to StringBuilder
                sb.append(String.format("%-6s", grid[i][j].toString()));
            }
            // Append a new line after each row
            sb.append("\n");
        }
        // Return the entire grid as a formatted string
        return sb.toString();
    }
}
