package poker;

/*
*****************************************************************************************************************
                                 Name:            Five Card Stud, Card Class
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

/**
 *
 * @author Casey J. Murphy
 *
 */
public class Card
{
    // class constants
    protected static final String[] symbolAry = {"♦", "♣", "♥", "♠"};

    protected static final String[] suitAry = { "Diamonds", "Clubs", "Hearts", "Spades" };
    protected static final String[] rankAry = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };

    // instance variables
    private int rank;
    private int suit;
    private String sRank;
    private String sSuit;
    private String name;
    private String symbol;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default Constructor<br>
     * Default card is the two of diamonds.  Remaining instance variables
     * are dynamically generated.
     */
    public Card()
    {
        setRank(0);
        setSuit(0);
        setName();
    }

    /**
     * Specific card Constructor<br>
     * Creates a playing card based on rank and suit as integers.  Remaining
     * instance variables are dynamically generated.
     * @param r
     * @param s
     */
    public Card(int r, int s)
    {
        setRank(r);
        setSuit(s);
        setName();
    }

// Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns rank as an integer.
     * @return rank integer
     */
    public int getRankAsInt()
    {
        return rank;
    }

    /**
     * Returns suit as an integer.
     * @return suit integer
     */
    public int getSuitAsInt()
    {
        return suit;
    }

    /**
     * Returns rank as a proper playing card rank (2 - A)
     * @return rank string
     */
    public String getRank()
    {
        return sRank;
    }

    /**
     * Returns spelled out suit as a sting (i.e. diamonds)
     * @return suit string
     */
    public String getSuit()
    {
        return sSuit;
    }

    /**
     * Returns suit as a unicode symbol
     * @return suit symbol
     */
    public String getSym()
    {
        return symbol;
    }

    /**
     * Returns string with proper rank and suit in words.
     * @return name string
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns string with proper rank and suit as a unicode symbol.  If show
     * is false, returns '###' to signify card is face down.
     * @param show true for face up, false for face down
     * @return name and symbol string
     */
    public String getNameSym(boolean show)
    {
        if (show) return sRank + symbol;
        return "###";
    }

// Mutators ------------------------------------------------------------------------------------------------------------
    /**
     * Sets card rank.
     * @param newRank new card rank as integer
     */
    private void setRank(int newRank)
    {
        rank = newRank;
        sRank = rankAry[rank];
    }

    /**
     * Sets card suit.
     * @param newSuit new card suit as integer
     */
    private void setSuit(int newSuit)
    {
        suit = newSuit;
        sSuit = suitAry[suit];
        symbol = symbolAry[suit];
    }

    /**
     * Generates card name as text based on array definitions.
     */
    private void setName()
    {
        name = rankAry[rank] + " of " + suitAry[suit];
    }

// Strings for Printing ------------------------------------------------------------------------------------------------

    /**
     * Draws first line of a card.  Meant to be used when drawing
     * the player's hand in Player class.
     * @return first line of picture
     */
    protected String line1()
    {
        return ".------. ";
    }

    /**
     * Draws second line of a card.  Dynamically generates card rank and suit.
     * Meant to be used when drawing the player's hand in Player class.
     * Rank 10 cards are drawn using a 0 instead of 10.
     * @return second line of picture
     */
    protected String line2()
    {
        String r = getRank();
        if (r == "10") r = "0";
        int s = getSuitAsInt();

        switch (s)
        {
            case 0 :
                return String.format("|%s /\\  | ", r);
            case 1 :
                return String.format("|%s  _  | ", r);
            case 2 :
                return String.format("|%s_  _ | ", r);
            case 3 :
                return String.format("|%s  .  | ", r);
            default:
                return null;
        }
    }

    /**
     * Draws third line of a card.  Dynamically generates card suit.
     * Meant to be used when drawing the player's hand in Player class.
     * @return third line of picture
     */
    protected String line3()
    {
        String r = getRank();
        if (r == "10") r = "0";
        int s = getSuitAsInt();

        switch (s)
        {
            case 0 :
                return "| /  \\ | ";
            case 1 :
                return "|  ( ) | ";
            case 2 :
                return "|( \\/ )| ";
            case 3 :
                return "|  / \\ | ";
            default:
                return null;
        }
    }

    /**
     * Draws fourth line of a card.  Dynamically generates card suit.
     * Meant to be used when drawing the player's hand in Player class.
     * @return fourth line of picture
     */
    protected String line4()
    {
        int s = getSuitAsInt();

        switch (s)
        {
            case 0 :
                return "| \\  / | ";
            case 1 :
                return "| (_x_)| ";
            case 2 :
                return "| \\  / | ";
            case 3 :
                return "| (_,_)| ";
            default:
                return null;
        }
    }

    /**
     * Draws fifth line of a card.  Dynamically generates card rank and suit.
     * Meant to be used when drawing the player's hand in Player class.
     * Rank 10 cards are drawn using a 0 instead of 10.
     * @return fifth line of picture
     */
    protected String line5()
    {
        String r = getRank();
        if (r == "10") r = "0";
        int s = getSuitAsInt();

        switch (s)
        {
            case 0 :
                return String.format("|  \\/ %s| ", r);
            case 1 :
                return String.format("|   Y %s| ", r);
            case 2 :
                return String.format("|  \\/ %s| ", r);
            case 3 :
                return String.format("|   I %s| ", r);
            default:
                return null;
        }
    }

    /**
     * Draws sixth line of a card.  Meant to be used when drawing
     * the player's hand in Player class.
     * @return sixth line of picture
     */
    protected String line6()
    {
        return "`------' ";
    }
}
