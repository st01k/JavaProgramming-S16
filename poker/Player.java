package poker;

/*
*****************************************************************************************************************
                                 Name:            Five Card Stud, Player Class
                                 Version:         1.3
                                 Chapter:         Final Project
                                 Author:          Casey J. Murphy
                                 Date Created:    11 Apr 16
                                 Last Modified:   02 May 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

	See full description in Poker.java

* ***************************************************************************************************************
*/

import java.util.ArrayList;

/**
 * Simple player class
 * @author Casey J. Murphy
 *
 */
public class Player {

    // class variables
    private static int id = 0;
    private static int handSize;
    private static int dealerModifier;
    private static int startChips = 100;
    private static String[] names = new String[] {"Casey", "Isaac C", "Issac V",
            "Gordon", "Matt", "Zak", "Erika", "Evan", "John", "Robert",
            "Larry", "Brandon", "Dalton", "Andrew", "Stephen", "Charles"};

    // instance variables
    private Card[] hand;
    private String name;
    private int playerID;
    private int chips;
    private double handValue = 0;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default Constructor<br>
     * Player with a generic name and unique ID is generated.  Hand is initialized
     * and the player gets a starting amount of chips.  Default is 100.  It can be
     * changed using the static method, setStartChips.
     */
    public Player()
    {
        name = "Anonymous";
        playerID = id;
        id ++;
        chips = getStartChips();
        initHand();
    }

    /**
     * Player by name Constructor<br>
     * Player with passed name and unique ID is generated.  Hand is initialized
     * and the player gets a starting amount of chips.  Default is 100.  It can be
     * changed using the static method, setStartChips.
     * @param startName name of human player
     */
    public Player(String startName)
    {
        name = startName;
        playerID = id;
        id ++;
        chips = getStartChips();
        initHand();
    }

    /**
     * Player with randomly generated name Constructor<br>
     * Player with a random name and unique ID is generated.  Hand is initialized
     * and the player gets a starting amount of chips.  Default is 100.  It can be
     * changed using the static method, setStartChips.
     * @param usedNames a list of the already used names
     */
    public Player (ArrayList<Player> usedNames)
    {
        playerID = id;
        setRandomName(usedNames, playerID);
        id ++;
        chips = getStartChips();
        initHand();
    }

    /**
     * Player dealer Constructor<br>
     * Player dealer is generated with a unique ID.  Hand is initialized
     * and the player gets a starting amount of chips that is 5 times the
     * amount of starting chips for other players.
     * @param dealer token to flag the dealer in player creation
     */
    public Player (boolean dealer)
    {
        name = "Dealer";
        playerID = id;
        id ++;
        chips = getStartChips() * dealerModifier;
        initHand();
    }

// Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns player's name.
     * @return player's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns player's unique ID.
     * @return player's ID
     */
    public int getID()
    {
        return playerID;
    }

    /**
     * Returns player's hand.
     * @return player's hand
     */
    public Card[] getHand()
    {
        return hand;
    }

    /**
     * Returns the amount of chips a player has.
     * @return player's chips
     */
    public int getChips()
    {
        return chips;
    }

    /**
     * Returns the point value of a player's hand.  Will remain 0 unless calculated
     * and modified by specified games with their own ranking rules.
     * @return player's hand point value
     */
    public double getHandValue()
    {
        return handValue;
    }

    /**
     * Returns the amount of chips a player starts with.  Static method.
     * @return regular player's start chips
     */
    public static int getStartChips()
    {
        return startChips;
    }

// Mutators ------------------------------------------------------------------------------------------------------------
    /**
     * Sets the amount of chips a regular player starts off with and the offset modifier
     * for dealer's chips (the number to be multiplied to regular player's chips for dealer's
     * starting chips.
     * @param newStartChips amount of chips regular player's start with
     * @param newMod multiplier to newStartChips for dealer's starting amount
     */
    public static void setStartChips(int newStartChips, int newMod)
    {
        startChips = newStartChips;
        dealerModifier = newMod;
    }

    /**
     * Sets player's hand value in points.
     * @param newValue hand value in points
     */
    public void setHandValue(double newValue)
    {
        handValue = newValue;
    }

    /**
     * Sets player's hand size.  Used for games with different hand sizes.
     * @param s hand size
     */
    public static void setHandSize(int s)
    {
        handSize = s;
    }

    /**
     * Initializes player's hand as an empty card array of the
     * proper size for the game.
     */
    public void initHand()
    {
        hand = new Card[handSize];
    }

    /**
     * Assigns a random name to player specified by `id`.  Uses passed in array list
     * to suppress duplicate names.
     * @param namesUsed a list of names that have already been assigned
     * @param id id of the player being assigned a name
     */
    private void setRandomName(ArrayList<Player> namesUsed, int id)
    {
        // create array sized to current number of players
        Player[] temp = new Player[id];
        for (int i = 0; i < temp.length; i++)
        {
            temp[i] = namesUsed.get(i);
        }

        String randName = names[(int) (Math.random() * (names.length))];
        for (int i = 0; i < temp.length; i++)
        {
            // if random name is already being used re-generate and cycle back through
            if (randName.equals(temp[i].getName()))
            {
                randName = names[(int) (Math.random() * (names.length))];
                i = 0;
                continue;
            }
        }
        name = randName;
    }

// Interactions --------------------------------------------------------------------------------------------------------
    /**
     * Adds a card to player's hand at element e.
     * @param c card to be added
     * @param element where to add card
     */
    public void addToHand(Card c, int e)
    {
        hand[e] = c;
    }

    /**
     * Places a bet with the amount passed in.  Amount is checked to be in range.
     * If it is not, user is re-prompted until a valid amount has been entered.
     * Bet amount is deducted from player's chips and the validated bet amount is
     * returned.
     * @param b bet amount input from user
     * @return validated bet amount
     */
    public int placeBet(int b)
    {
        while (!(chips >= b && b >= 0))
        {
            System.out.print("Invalid amount.  Re-enter bet: ");
            b = valNumbers(Game.scan.next());
        }
        chips -= b;
        return b;
    }

    /**
     * If player has sufficient chips, ante is deducted from player's chips
     * and method returns true.  If not, returns false.
     * @param ante amount of ante to be deducted from player's chips
     * @return true if payer paid, false if not
     */
    public boolean payAnte(int ante)
    {
        while (ante > chips)
        {
            return false;
        }
        chips -= ante;
        return true;
    }

    /**
     * Adds chip amount passed in to player's chips.
     * @param c amount to be added
     */
    public void addChips(int c)
    {
        chips += c;
    }

// Utilities -----------------------------------------------------------------------------------------------------------
    /**
     * Resets id count.  Used when resetting the whole player array.
     */
    public static void resetID()
    {
        id = 0;
    }

    /**
     * Validates that input is made up of only positive numbers.
     * @param s string passed in
     * @return converted integer value
     */
    public static int valNumbers(String s)
    {
        while (!(s.matches("[0-9]+")))
        {
            System.out.print("Re-enter as a positive number: ");
            s = Game.scan.next();
        }
        return Integer.parseInt(s);
    }

// Printing ------------------------------------------------------------------------------------------------------------
    /**
     * Prints player's hand on one line.
     */
    public void printHand()
    {
        for (int i = 0; i < hand.length; i++)
        {
            System.out.print(hand[i].getRank() + hand[i].getSym() + " ");
        }

        System.out.println();
    }

    /**
     * Prints a number of cards in the player's hand with card pictures.
     * @param cardsToShow number of cards to be shown
     */
    public void drawHandPic(int cardsToShow)
    {
        System.out.println(buildPic(cardsToShow));
    }

    /**
     * Builds picture of cards to be printed, line by line.  Calls to
     * line methods located in the Card class for dynamic representation
     * of the specified card in player's hand.
     * @param c number of cards to be printed
     * @return the picture as a printable string
     */
    private String buildPic(int c)
    {
        String l1 = "";
        String l2 = "";
        String l3 = "";
        String l4 = "";
        String l5 = "";
        String l6 = "";

        for (int i = 0; i < c; i++)
        {
            l1 += hand[i].line1();
            l2 += hand[i].line2();
            l3 += hand[i].line3();
            l4 += hand[i].line4();
            l5 += hand[i].line5();
            l6 += hand[i].line6();
        }

        l1 += "\n";
        l2 += "\n";
        l3 += "\n";
        l4 += "\n";
        l5 += "\n";

        return l1 + l2 + l3 + l4 + l5 + l6;
    }
}
