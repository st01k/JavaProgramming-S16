package poker;

/*
*****************************************************************************************************************
                                 Name:            Five Card Stud, Deck Class
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
 * Simple deck class.
 * @author Casey J. Murphy
 *
 */
public class Deck extends Card
{
    // class constants
    private static final int SUITS = Card.suitAry.length;
    private static final int RANKS = Card.rankAry.length;
    private static final int SIZE = SUITS * RANKS;

    // class variables
    private static int deckCount = 0;

    // instance variables
    private Card[] deck;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default Constructor<br>
     * Creates and initializes a new deck of 52 standard playing cards.
     */
    public Deck()
    {
        deck = new Card[SIZE];
        initDeck();
    }

// Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns deck size
     * @return deck size
     */
    public int getSize()
    {
        return SIZE;
    }

    /**
     * Returns a card from the top of the deck.  If too few cards remain, reshuffles
     * deck, resets deckCount, and deals from the top.
     * @return top card
     */
    public Card getCard ()
    {
        while (!(deckCount <= SIZE))
        {
            System.out.println("Not enough cards.  Reshuffling...");
            shuffle();
            deckCount = 0;
        }
        Card temp = deck[deckCount];
        deckCount++;
        return temp;
    }

    /**
     * Returns current deck count based on the number of cards that have been pulled
     * from the top of the deck.
     * @return deck count
     */
    public int getDeckCount()
    {
        return deckCount;
    }

// Functionality -------------------------------------------------------------------------------------------------------
    /**
     * Initializes a deck of cards with proper suits and ranks.
     */
    private void initDeck()
    {
        for (int i = 0; i < SIZE; i++)
        {
            deck[i] = new Card(i % RANKS, i / RANKS);
        }
    }

    /**
     * Shuffles deck of cards.
     */
    public void shuffle()
    {
        deckCount = 0;

        for (int i = 0; i < SIZE; i++)
        {
            int rand = i + (int) (Math.random() * (SIZE - i));
            Card temp = deck[rand];
            deck[rand] = deck[i];
            deck[i] = temp;
        }
    }

    /**
     * Prints deck of cards in four rows.
     */
    public void printDeck()
    {
        for (int i = 0; i < SIZE; i++) {
            System.out.print(deck[i].getRank() + deck[i].getSym() + " ");
            if ((i + 1) % 4 == 0) System.out.println();
        }
    }
}
