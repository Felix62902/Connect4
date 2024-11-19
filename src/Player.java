import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private String name;
    private char symbol;
    // a reference to the grid
    private Grid grid;

    public Player(String name, char symbol, Grid grid){
        this.name = name;
        this.symbol = symbol;
        this.grid = grid;
    }

    public String getName(){
        return this.name;
    }

    //return 0 to signal switch to next player, 1 if repeating, 2 if end game
    public int takeTurn(Scanner scan, Grid gamePanel, Player tempPlayer) {
        System.out.println(this.name + "(" + this.symbol + ")" + ", please provide a column. It should be a whole number between 1 and 7." +
                "\n" + tempPlayer.getName() + ", if you wish to undo the last move, please type in -1.");

        boolean validInput = false; //must be integer or "undo"
        int colNum = 0;
        // prompt player to input column Number
        while (!validInput) {
            try {
                colNum = scan.nextInt() - 1;  // Adjusting for zero-based indexing
                //Check for "undo"
                if (colNum == -2){ // -2 instead of -1 since its been shifted in line 32
                    //logic to undo
                    if(gamePanel.undoLastMove() == 0){
                        validInput = true;
                        System.out.println(gamePanel);
                        System.out.println("The last move has been undone. ");
                        return 0; //switch back to previous player
                    } else if (gamePanel.undoLastMove() == 1){
                        System.out.println("Unable to undo the last move. It may have been the first move of the game.");
                        return 1;
                    } else {
                        System.out.println("Unable to undo the last move as undo can only be used once!");
                        return 1;
                    }
                }

                if(colNum < 0 || colNum > 6) {
                    System.out.println("Invalid Column! Please provide a number between 1 and 7");
                } else {
                    validInput = true;  // Successfully got an integer, exit the loop
                }
            } catch (InputMismatchException e) {
                System.out.println("Please provide a whole number value!");
                scan.nextLine();  // Consume the invalid input
            }
        }
//      row Number the disk is dropped to in the column, 0 if failure
        int rowNum = gamePanel.dropDisc(colNum, this.symbol);
        if ( rowNum != -1){
//            gamePanel.dropDisc(colNum, player.getSymbol());
            System.out.println("successfully added disc to " + (colNum +1));
            System.out.println(gamePanel);
        } else {
            System.out.println("Column is full, try another column!");
            return 1;
        }

        //newly Dropped disk
        Disc tempDisk = new Disc(this.symbol,colNum,rowNum);
        if (gamePanel.checkWinConditions(tempDisk)){
            //announce player won
            System.out.println("Congratulations " + this.name + "! You have won the game!");
            return 2; // return 2 as there is no need to take another turn, game is finished
        }
        // switches between player 1 and 2
        return 0;
    }
}
