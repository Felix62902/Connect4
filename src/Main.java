import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //initialize required Objects
        Scanner scan = new Scanner(System.in);
        Grid gamePanel = new Grid();

        System.out.println(gamePanel);

        //Prompt player 1 for their name
        System.out.println("Welcome to Connect 4!");
        System.out.println("Player 1, Enter your name:");
        String p1Name = scan.nextLine();

        char p1Sym; // player 1's symbol
        // continuously re-prompt user if given symbol is invalid
        while (true) {
            System.out.println(p1Name + ", please select a symbol: ( O or X ):");
            String input = scan.nextLine().trim(); // Get input and remove any extra spaces

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please choose either 'O' or 'X'.");
                continue; // Prompt again
            }

            p1Sym = Character.toUpperCase(input.charAt(0));
            if (p1Sym == 'O' || p1Sym == 'X') {
                break; // Exit the loop if symbol is vliad
            } else {
                System.out.println("Invalid selection. Please choose either 'O' or 'X'.");
            }
        }
        System.out.println("Player 2, please enter your name:");
        String p2Name = scan.nextLine();

        char p2Sym;

        // automatically allocate p2's symbol since p1 selected it already
        if (p1Sym == 'O'){
            p2Sym = 'X';
        } else {
            p2Sym = 'O';
        }

        System.out.println(p2Name + ", your symbol is " + p2Sym + ".");

        // initialize Players
        Player player1 = new Player(p1Name, p1Sym, gamePanel);
        Player player2 = new Player(p2Name, p2Sym ,gamePanel);
        Player activePlayer = player1;
        // the player that is not active, used in the instructions of the undo logic within the taketurn method of player object
        Player tempPlayer = player2;

        //display initial grid
        System.out.println("Let's begin the game!");
        System.out.println(gamePanel);

        //Game Loop
        while (true) {
            // Break if grid is full
            if (gamePanel.checkGridFull()){
                System.out.println("The grid is full! It's a draw.");
                break;
            }

            // Call playerMove once and store the result 0 = change to next palyer, 1 = not change,2 = stop game
            int status = activePlayer.takeTurn(scan, gamePanel, tempPlayer);

            /* The condition (moveResult == 0) for invalid input was removed to avoid redundancy.
             The loop naturally continues if the input is invalid, as no further actions are needed. */
            if (status == 0) {
                // Move to the next player if it returns true
                tempPlayer = activePlayer;
                activePlayer = (activePlayer == player1) ? player2 : player1;
            } else if (status == 1){
                // Don't switch
                continue;
            } else if (status == 2){
                // end the game as a player has won
                break;
            }
        }
    }
}