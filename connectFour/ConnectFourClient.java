package connectFour;

/*
*****************************************************************************************************************
                                 Name:            Connect Four Client
                                 Version:         1.2
                                 Chapter:         9, Client 1
                                 Author:          Casey J. Murphy
                                 Date Created:    19 Mar 16
                                 Last Modified:   05 Apr 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

	Write a client (driver) class, where the main method is located, to test all the methods in your class and
	enable the user to play.

	In the main method of your client class, your program will simulate a connect-4 game from the command line,
	doing the following:

		Create a Connect4 object, and instantiate it

		In a loop, prompt for plays (42 possible plays), asking which column to "drop" their token into.  Make
		sure the column is a valid column, and that the column isn't full.  At each iteration of the loop, you
		will need to call methods of the Connect4 class to update the Connect4 object.  You need to keep track
		of who is playing (player1 or player2), enforce the rules, check if either player has won the game.

		If a player wins, exit the loop and present the results of the game.  If the game ends in a tie, you
		should output that result.
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* 				1.1 - Moved gameplay controller functionality to main as per assignment instructions
* 					  Tidied up, added some documentation
* 				1.2 - Added run again dialog
* 					  Made client re-instantiate the game object inside the run again loop to clear the object
* 					  for a new game (not sure of the impact on memory).  What would be optimal?
* 					  Rearranged a little for the run again functionality
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* ***************************************************************************************************************
*/

/**
 * Client for a Connect Four game using the ConnectFour class.
 * @author Casey J. Murphy
 * @version 1.2
 */
public class ConnectFourClient {

    public static void main (String[] args)
    {
        Boolean again = true;								// loop flag for run again
        ConnectFour game = new ConnectFour();				// instantiate 2d array as game board

        while (again)
        {
            game = new ConnectFour();						// re-instantiate game object to clear for new game
            boolean win = false;							// used as a flag for final output
            byte moves = game.getMoves();					// moves for countdown
            byte totMoves = game.getTotMoves();				// total moves for win check suppression (first 6 moves)
            char[] players = game.getPlayers();				// array with player tokens

            game.formFeed();
            System.out.println("\t  CONNECT FOUR");
            game.line();
            System.out.println("Use 1 - 7 to drop your token\ninto the corresponding column.");
            game.line();

            // alternates players until someone wins or no more moves are left (tie)
            for (byte player = 0; moves-- > 0; player = (byte) (1 - player))
            {
                game.makeMove(players[player]);

                // checks for winning move starting on the seventh move (first possible win)
                if (moves < totMoves - 6 && game.winMove(players[player]))
                {
                    win = true;
                    game.formFeed();
                    // final board print with winning move
                    game.drawBoard();
                    System.out.println("\n\t   PLAYER " + Character.toUpperCase(players[player]) + " WINS!");
                    break;
                }

                game.formFeed();
            } // end for loop

            if (!win)
            {
                // final board print with tied board
                game.drawBoard();
                System.out.println("\nTIE GAME");
            }

            again = game.playAgain();
        } // end while loop

        System.out.println();
        game.line();
        System.out.println("Thank you! \t\tCome again!\n\n\tProgram Terminated.");
        game.closeScan();
        System.exit(0);
    } // end main
} // end class
