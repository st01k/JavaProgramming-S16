package connectFour;

/*
*****************************************************************************************************************
                                 Name:            Connect Four Class
                                 Version:         1.4
                                 Chapter:         9, Class 1
                                 Author:          Casey J. Murphy
                                 Date Created:    19 Mar 16
                                 Last Modified:   07 Apr 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

	Write a class to represent a connect 4 game, using a 2-dimensional array (I would recommend an array of
	strings, with each cell initially set to " ")

	Two players, player 1 and player 2, will be playing.  Use a string of X and O, or * and O, or something
	similar to represent each player's token.

	The board is represented by a 2-dimensional array, 6 rows of 7 columns, of strings.

	The value of a " " represents that this space is available; the value X indicates the space is occupied by
	player 1, the value O indicates the space is occupied by player 2. (Replace X and O with whichever token
	symbols you choose to use)

	In your Connect4 class, you will need to code the following methods:

		A default constructor instantiating the 2-dimensional array representing the board.

		A method that allows a player to make a move; it takes two arguments: the player number (or token) and
		the column on the board.

		A method checking if a play is legal.

		A method checking if a player has won the game; you can break up that method into several methods if you
		like (for instance, check if a player has won with vertical 4 in a row, horizontal 4 in a row,
		/ diagonal 4 in a row, or \ diagonal 4 in a row).
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* 				1.1 - Optimized resource usage by changing all appropriate integer variables to bytes
* 					  Added documentation
* 				1.2 - Moved gameplay controller into client as per assignment instructions
* 					  Added accessors
* 					  Changed line formatting to dynamically grow based on constant COLS
* 				1.3 - Added playAgain for run again functionality inside client
* 					  More documentation
* 				1.4 - Fixed bug 1
* ***************************************************************************************************************
* Bug List:		1)	After 'column is full' dialog, enter an invalid number, then re-enter the full column number.
* 					The new 'column is full' dialog displays erroneous column data.
* 					FIXED ver 1.4 - Changed var printed from 'input' to '(col + 1)' : last print in makeMove
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* ***************************************************************************************************************
*/
import java.util.Arrays;
import java.util.Scanner;

/**
 * Connect Four game object class.
 * @author Casey J. Murphy
 * @version 1.4
 */
public class ConnectFour
{
    // input
    private static final Scanner scan = new Scanner(System.in);

    // class constants
    private static final char[] PLAYERS = new char[] {'x', 'o'};
    private static final byte ROWS = 6;
    private static final byte COLS = 7;
    private static final byte TOTMOVES = ROWS * COLS;

    // instance variables
    private char[][] board;
    private byte moves = ROWS * COLS;
    private byte lastRow;
    private byte lastCol;

//----------------------------------------------------------------------------------------------------------------------
//Constructor

    /**
     * Default Constructor <br>
     * Creates game board as a two-dimensional array.
     */
    public ConnectFour()
    {
        board = new char[ROWS][];
        for (byte row = 0; row < ROWS; row++)
        {
            Arrays.fill(board[row] = new char[COLS], ' ');
        }
    }
//----------------------------------------------------------------------------------------------------------------------
// Accessors

    /**
     * Returns variable used in countdown of total allowed moves per game
     * @return moves
     */
    public byte getMoves()
    {
        return moves;
    }

    /**
     * Static constant - returns variable used to suppress win checks for first 6 moves
     * @return total moves
     */
    public byte getTotMoves()
    {
        return TOTMOVES;
    }

    /**
     * Static constant - returns variable used for identification and the alternation of player turns
     * @return players array which holds to two player tokens, X and O
     */
    public char[] getPlayers()
    {
        return PLAYERS;
    }
//----------------------------------------------------------------------------------------------------------------------
// Functionality

    /**
     * Notifies user of whose turn it is.  Accepts their move, validates it, checks for full column,
     * and  fills game board according to game rules.
     * @param tok Player whose turn it is
     */
    public void makeMove(char tok)
    {
        drawBoard();
        boolean full = false;	// loop flag for full column

        do
        {
            System.out.print("\nPlayer " + Character.toUpperCase(tok) + "'s turn:\t\t  ");
            String input = scan.next();
            byte col = valInput(input);

            // fills 2d array from bottom up
            for (byte row = ROWS - 1; row >= 0; row--)
            {
                if (board[row][col] == ' ')
                {
                    board[row][col] = tok;
                    lastRow = row;
                    lastCol = col;
                    return;
                }
            }

            full = true;
            System.out.println("Column " + (col + 1) + " is full.  ");

        } while (full);	// loop if column is full
    }
//-----------------------------------------------------------

    /**
     * Draws the game board with all previous moves in their appropriate positions.
     */
    public void drawBoard()
    {
        System.out.println();
        for (byte row = 0; row < ROWS; row++)
        {
            for (byte col = 0; col < COLS; col++)
            {
                System.out.print("[ " + board[row][col] + " ]");
                // new line when last column in row is printed
                if (col == COLS - 1)
                {
                    System.out.println();
                }
            } // end inner loop

            line();
        } // end outer loop

        // prints column numbers on bottom of board
        for (byte num = 0; num < COLS; num++)
        {
            System.out.print("  " + (num + 1) + "  ");
        }

        System.out.println();
        line();
    }
//-----------------------------------------------------------

    /**
     * Creates a key based on the player whose turn it is, and checks each possible winning pattern for the key.
     * @param tok Player whose turn it is.
     * @return True is winning move is found, false if not.
     */
    public boolean winMove(char tok)
    {
        // creates key to search for based on players turn
        String key = String.format("%c%c%c%c", tok, tok, tok, tok);

        // implement checks
        if (horizontal(key)) return true;
        if (vertical(key)) return true;
        if (bSlash(key)) return true;
        if (slash(key)) return true;
        return false;
    }
//-----------------------------------------------------------

    /**
     * Closes scanner
     */
    public void closeScan()
    {
        scan.close();
    }
//-----------------------------------------------------------

    /**
     * Prompts for run again, validates answer and returns response
     * @return validated answer to run again dialog
     */
    public boolean playAgain()
    {
        System.out.println();
        line();
        System.out.print("\nPlay again (Y/N)? \t\t  ");
        String ans = scan.next();

        while (!ans.matches("[YyNn]"))
        {
            System.out.print("Invalid answer.  Enter again: ");
            ans = scan.next();
        }

        if (ans.matches("[Yy]")) return true;
        return false;
    }
//----------------------------------------------------------------------------------------------------------------------
// Validation

    /**
     * Checks if user input for move is valid based on number of columns on the board.
     * @param input User's input
     * @return True if input is valid, false otherwise.
     */
    private boolean inRange(String input)
    {
        return (input.matches("[1-" + COLS + "]"));
    }
//-----------------------------------------------------------

    /**
     * Prompt users to re-enter input when invalid move input was given.
     * @param input User's input
     * @return new validated input
     */
    private byte valInput(String input)
    {
        while (!inRange(input))
        {
            System.out.print("Invalid.  Re-enter: \t\t  ");
            input = scan.next();
        }

        // converts input to integer and actual array element
        return (byte) (Integer.parseInt(input) - 1);
    }
//----------------------------------------------------------------------------------------------------------------------
// Checks

    /**
     * Builds string of all values in the horizontal row of the last played token<br>
     * and returns true if the winning key is found.
     * @param pattern A string containing four tokens from the current player
     * @return True if the pattern is found, false otherwise.
     */
    private boolean horizontal(String pattern)
    {
        // build horizontal string - starts at leftmost of row
        String s = new String(board[lastRow]);

        // search for pattern
        if (s.indexOf(pattern) == -1) return false;
        return true;
    }
//-----------------------------------------------------------

    /**
     * Builds string of all values in the vertical column of the last played token<br>
     *  and returns true if the winning key is found.
     * @param pattern A string containing four tokens from the current player
     * @return True if the pattern is found, false otherwise.
     */
    private boolean vertical(String pattern)
    {
        StringBuilder s = new StringBuilder(ROWS);

        // build vertical string - starts at top of column
        for (byte row = 0; row < ROWS; row++)
        {
            // appends associated column element
            s.append(board[row][lastCol]);
        }

        // search for pattern
        if (s.indexOf(pattern) == -1) return false;
        return true;
    }
//-----------------------------------------------------------

    /**
     * Builds string of all values in the backslash-shaped diagonal of the last played token<br>
     *  and returns true if the winning key is found.
     * @param pattern A string containing four tokens from the current player
     * @return True if the pattern is found, false otherwise.
     */
    private boolean bSlash(String pattern)
    {
        StringBuilder s = new StringBuilder(ROWS);

        // build diagonal string - starts at top row
        for (byte row = 0; row < ROWS; row++)
        {
            // calculate column of diagonal start and step down (row inc) and right
            byte col = (byte) (lastCol - lastRow + row);
            // controls out of bounds (array grid)
            if (0 <= col && col < COLS)
            {
                // appends element at each step
                s.append(board[row][col]);
            }
        }

        // search for pattern
        if (s.indexOf(pattern) == -1) return false;
        return true;
    }
//-----------------------------------------------------------

    /**
     * Builds string of all values in the slash-shaped diagonal of the last played token<br>
     * and returns true if the winning key is found.
     * @param pattern A string containing four tokens from the current player
     * @return True if the pattern is found, false otherwise.
     */
    private boolean slash(String pattern)
    {
        StringBuilder s = new StringBuilder(ROWS);

        // build diagonal string - starts at top row
        for (byte row = 0; row < ROWS; row++)
        {
            // calculate column of diagonal start and step down (row inc) and left
            byte col = (byte) (lastCol + lastRow - row);
            // controls out of bounds (array grid)
            if (0 <= col && col < COLS)
            {
                // appends element at each step
                s.append(board[row][col]);
            }
        }

        // search for pattern
        if (s.indexOf(pattern) == -1) return false;
        return true;
    }
//----------------------------------------------------------------------------------------------------------------------
// Formatting

    /**
     * Prints a dynamically generated line to fit the number of columns in the game. <br>
     * NOTE: Columns can only be changed in the class constants section.  For testing.
     */
    public void line()
    {
        StringBuilder line = new StringBuilder(COLS * 5);
        for (byte i = 0; i < COLS; i++)
        {
            line.append("-----");
        }

        System.out.println(line);
    }
//-----------------------------------------------------------

    /**
     *  Simulates a form feed.
     */
    public void formFeed()
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}