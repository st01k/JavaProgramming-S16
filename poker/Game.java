package poker;

/*
*****************************************************************************************************************
                                 Name:            Five Card Stud, Game Class
                                 Version:         1.3
                                 Chapter:         Final Project
                                 Author:          Casey J. Murphy
                                 Date Created:    11 Apr 16
                                 Last Modified:   30 Apr 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

	See full description in Poker.java

* ***************************************************************************************************************
*/

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simple Game class for card games
 * @author Casey J. Murphy
 *
 */
public class Game
{
    // class variables
    public static Scanner scan = new Scanner(System.in);
    public static Scanner kb = new Scanner(System.in);
    private static int maxPlayers = 6;

    // instance variables
    protected ArrayList<Player> playersAry;
    protected Player human;
    protected Player dealer;
    private int players;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default Constructor<br>
     * Creates Game with 2 players, user as player 1, and dealer as player 2.
     */
    public Game()
    {
        setPlayers(2);
        setPlayerAry();
        human = playersAry.get(0);
        dealer = playersAry.get(1);
    }

    /**
     * Specified Game Constructor<br>
     * Creates Game with specified number of players.  Player 1 is the user, Player 2
     * is the dealer.  All other players are randomly generated and added to the players
     * array.
     * @param numPlayers number of players
     * @param cardsDealt number of cards in a hand for this game
     */
    public Game(int numPlayers, int cardsDealt)
    {
        setPlayers(numPlayers);
        Player.setHandSize(cardsDealt);
        setPlayerAry();
        human = playersAry.get(0);
        dealer = playersAry.get(1);
    }

// Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns current number of players.
     * @return number of players current in players array
     */
    public int getPlayers()
    {
        return playersAry.size();
    }

    /**
     * Returns maximum number of players allowed.
     * @return max players allowed per game
     */
    public static int getMaxPlayers()
    {
        return maxPlayers;
    }

// Mutators ------------------------------------------------------------------------------------------------------------
    /**
     * Sets starting number of players.
     * @param numPlayers number of players game starts with
     */
    private void setPlayers(int numPlayers)
    {
        players = numPlayers;
    }

    /**
     * Creates players array.  User is first player, dealer second,
     * and all others follow.
     */
    private void setPlayerAry ()
    {
        playersAry = new ArrayList<Player>();

        System.out.print("Enter first name: ");
        String name = scan.next();
        playersAry.add(new Player(name));
        boolean dealer = true;
        playersAry.add(new Player(dealer));

        for (int i = 2; i < players; i++)
        {
            playersAry.add(new Player(playersAry));
        }
    }

// Functionality -------------------------------------------------------------------------------------------------------
    /**
     * Checks that the number of players per game requested is a valid amount.
     * Re-prompts if out of range.  Returns validated answer.
     * @param s user input
     * @return validated number of players
     */
    public static int checkPlayers(String s)
    {
        while (!(s.matches("[2-" + maxPlayers + "]")))
        {
            System.out.print("Invalid player amount.  Re-enter: ");
            s = scan.next();
        }

        return Integer.parseInt(s);
    }

    /**
     * Pauses game and prompts for user to hit enter.
     */
    public static void pause()
    {
        printLine();
        System.out.print("Press Enter to Continue...");
        kb.nextLine();
        printLine();
        formFeed();
    }

// Printing ------------------------------------------------------------------------------------------------------------
    /**
     * Prints a list of player's names and chip amounts, with formatting.
     */
    public void printPlayers ()
    {
        for (Player p : playersAry)
        {
            String listing = String.format("%s\t\t\t\t(Chips: %s)", p.getName(), p.getChips());
            System.out.println(listing);
        }
    }

    /**
     * Prints a line.
     */
    public static void printLine()
    {
        System.out.println("------------------------------------------------");
    }

    /**
     * Simulates a form feed.
     */
    public static void formFeed()
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}
