package poker;

/*
*****************************************************************************************************************
                                 Name:            Five Card Stud, Poker Class
                                 Version:         1.3
                                 Chapter:         Final Project
                                 Author:          Casey J. Murphy
                                 Date Created:    11 Apr 16
                                 Last Modified:   02 May 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

	Write a game of 5 card stud, where you play against the computer.  The computer will be the dealer.

	The computer will always be the dealer, and will always match all bets by the player.
	The dealer will start out with 5 times as much money as the player (Remember, the house always wins,
	so they can afford it)

	The player will put money on the table to enter the game (an initial bet), called the ante.
	Both players are dealt 1 card face down, then one card face up.
	Loop until each player has 5 cards – each round gives both players 1 card.
	Each player can bet, check (a bet of 0), or fold (give up this game)
	Once all bets have been made, another card is dealt face up to all players
	Once all players have 5 cards, a final bet is made, or the player can fold
	All players show all cards
	The player with the best hand wins the pot
	The dealer collects all cards and shuffles the deck, and another hand is played until the player
	chooses to quit or one of the two players is out of money.
	If a player folds, the dealer wins the pot automatically.  For simplicity, our dealer (the computer)
	will never fold, and will match all bets from the player.

* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* 				1.1 - Fixed random name generation to not repeat a name
* 					  Added tie/pot splitting functionality
* 					  Added number of players dialog validation
* 					  Added documentation
* 				1.2 - Added validation for betting input
* 					  Changed players array (Game) into an ArrayList, modified all classes appropriately
* 					  Added player folds if they don't have enough chips for the round ante or bets
* 					  Added winning poker hand output on split pot dialog
* 				1.3 - Made hand scoring more accurate
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* 				(1) - Invalid number of cards passed into poker constructor, setCardsDealt
* 				(2) - Invalid number of winners in winStatus
* ***************************************************************************************************************
*/

import java.util.ArrayList;

/**
 * Poker class.  Framework allows for all types of poker.
 * Currently only supports 5 card stud.
 * @author Casey J. Murphy
 * @version 1.3
 */
public class Poker extends Game
{
    // instance variables
    private boolean draw;				// true: draw, false: stud
    private int cardsDealt;				// cards dealt per hand
    private int ante = 25;
    private int pot;
    private Deck deck;

// Constructors --------------------------------------------------------------------------------------------------------
    /**
     * Default constructor<br>
     * Instantiates Game.  Default game is five card stud with 2 players.
     * Clears pot and creates a new deck.
     */
    public Poker()
    {
        super();
        draw = false;
        cardsDealt = 5;
        pot = 0;
        deck = new Deck();
    }

    /**
     * Specified type of poker Constructor<br>
     * Instantiates Game.  Poker started with specified number of players,
     * cards dealt (5 or 7 accepted, and boolean representing whether or not
     * the game is draw or stud.  Clears pot and creates a new deck.
     * @param players number of players
     * @param startCardsDealt number of cards per hand
     * @param startDraw if game is draw (true) or stud (false)
     */
    public Poker(int players, int startCardsDealt, boolean startDraw)
    {
        super(players, startCardsDealt);
        draw = startDraw;
        pot = 0;
        setCardsDealt(startCardsDealt);
        deck = new Deck();
    }

// Accessors -----------------------------------------------------------------------------------------------------------
    /**
     * Returns the number of cards dealt per hand.
     * @return cards dealt per hand
     */
    public int getCardsDealt()
    {
        return cardsDealt;
    }

    /**
     * Returns the set ante for the game.
     * @return ante amount in chips
     */
    public int getAnte()
    {
        return ante;
    }

// Mutators ------------------------------------------------------------------------------------------------------------

    /**
     * Sets ante amount for game.
     * @param newAnte integer, new ante amount in chips
     */
    public void setAnte(int newAnte)
    {
        if (newAnte >= 0 && newAnte < Player.getStartChips()) ante = newAnte;
        else
        {
            System.out.println("Ante amount out of range.");
        }
    }

    /**
     * Validates and sets cards dealt per hand.
     * @param newCardsDealt number of cards dealt for game
     */
    private void setCardsDealt(int newCardsDealt)
    {
        if (newCardsDealt == 5 || newCardsDealt == 7) cardsDealt = newCardsDealt;
        else
        {
            System.out.println("Invalid number of cards.  Terminating...");
            System.exit(1);
        }
    }

// Functionality -------------------------------------------------------------------------------------------------------

    /**
     * Drives the poker game.  Implements a loop to re-shuffle and play another
     * hand or to cash out.  Handles calls for players' ante, betting rounds,
     * players' action options while game is in play, and constructing the winners'
     * array.
     */
    public void play()
    {
        // cash out or play another hand dialog loop
        String cont;
        do
        {
            pot = 0;
            deck.shuffle();
            System.out.println();
            dealHand();

            //ante check for human player
            ArrayList<Player> temp = new ArrayList<Player>();
            if (human.getChips() < ante)
            {
                System.out.println("You don't have enough chips to play this round.");
                return;
            }

            //ante check for computer players
            for (Player p : playersAry)
            {
                // if player is out of chips, queues them to be removed from round
                if (!p.payAnte(ante))
                {
                    System.out.println(p.getName() + " doesn't have enough chips to play this round.");
                    temp.add(p);
                    Game.pause();
                }

                else addToPot(ante);
            }

            // removes players who are out of chips
            for (Player p : temp)
            {
                playersAry.remove(p);
            }

            // game winner, checks playersAry for one player
            if (playersAry.size() == 1)
            {
                Player gameWinner = playersAry.get(0);
                System.out.println(gameWinner.getName() + " wins the game with " + gameWinner.getChips() + " chips!!!");
                return;
            }

            // dealer wins if player folds
            ArrayList<Player> winners = new ArrayList<Player>();
            winners.add(dealer);

            // query for player fold, starts betting round
            boolean fold = bettingRounds();
            if (!fold)
            {
                winners.remove(dealer);
                winners = getWinner();
            }

            // prints all hands
            for (Player p : playersAry)
            {
                String s = "";
                s += String.format("%-8s: ", p.getName());

                for (int card = 0; card < 5; card++)
                {
                    s += String.format("%3s ", p.getHand()[card].getNameSym(true));
                }

                s += " (" + handStatus(p.getHand(), p) + ")";

                System.out.println(s);
            }

            winStatus(winners);
            printLine();
            System.out.println("Your chips: " + human.getChips());
            System.out.print("\n(C)ash out or (P)lay another hand? ");
            cont = Game.scan.next();
            Game.formFeed();

        } while(cont.matches("[Pp]"));
    } // end play

    /**
     * Deals a hand from the shuffled deck to each player.
     */
    public void dealHand()
    {
        for (int card = 0; card < cardsDealt; card++)
        {
            for (Player p : playersAry)
            {
                Card c = deck.getCard();
                p.addToHand(c, card);
            }
        }
    }

    /**
     * Controls betting mechanism and prints appropriate confirmation.
     * Currently only applies to the rules of Five Card Stud but easily
     * modified to fit rules for other types of poker. (ref: printRound(rnd+1))
     * @return true if player folds, false otherwise
     */
    private boolean bettingRounds()
    {
        for (int rnd = 1; rnd < cardsDealt; rnd++)
        {
            System.out.println();
            printLine();
            // prints first two cards
            printRound(rnd + 1);

            int bet = playerOptions();
            // human player folds
            if (bet == -1) return true;

            // check players for enough chips to meet user's bet
            ArrayList<Player> outOfChips = new ArrayList<Player>();
            for (int i = 1; i < playersAry.size(); i++)
            {
                if (playersAry.get(i).getChips() < bet)
                {
                    outOfChips.add(playersAry.get(i));
                    System.out.println(playersAry.get(i).getName() + " folds due to too few chips.");
                }
            }
            // remove players without enough chips to meet user's bet
            for (Player p : outOfChips) playersAry.remove(p);

            // all remaining players mimic user's bet/action
            for (int i = 1; i < playersAry.size(); i++)
            {
                int temp = playersAry.get(i).placeBet(bet);
                addToPot(temp);

                if (bet > 0)
                {
                    System.out.println(playersAry.get(i).getName() + " calls your bet of " + bet + ".");
                }
                else
                {
                    System.out.println(playersAry.get(i).getName() + " checks.");
                }
            } // end mimic
            Game.pause();
        } // end round
        return false;
    } // end bettingRounds

    private void addToPot(int c)
    {
        pot += c;
    }

// Interactions --------------------------------------------------------------------------------------------------------

    /**
     * Calculates point value ranking of the hand passed in based on the conditional hierarchy where
     * if a higher hand matches, the lower categories are not checked.  Some checks do not process
     * until their existence is possible in the number of cards shown on the table.
     * @param hand hand to be checked
     * @param p player whose hand is being passed in, used to assign point value
     * @return string displaying the highest matching combination
     */
    public static String handStatus(Card[] hand, Player p)
    {
        // number of cards currently in hand to be checked
        int tempCards = hand.length;

        if (tempCards == 5 && royalStraightFlush(hand, p) > 0) return "Royal Straight Flush";
        if (tempCards == 5 && straightFlush(hand, p) > 0) return "Straight Flush";
        if (tempCards == 5 && flush(hand, p) > 0) return "Flush";
        if (tempCards == 5 && straight(hand, p) > 0) return "Straight";
        if (tempCards >= 4 && fourKind(hand, p) > 0) return "Four of a Kind";
        if (tempCards == 5 && fullHouse(hand, p) > 0) return "Full House";
        if (tempCards >= 3 && set(hand, p) > 0) return "Three of a Kind";
        if (tempCards >= 4 && twoPair(hand, p) > 0) return "Two Pair";
        if (tempCards >= 2 && onePair(hand, p) > 0) return "One Pair";
        return "High Card: " + highCard(hand, p);
    }

    /**
     * Drives players' action dialog.  Player can fold, check, or bet.
     * Responses are validated and the loop is recycled if an invalid
     * response is given.  Returns players bet, 0 if checked, and -1
     * if player folds.
     * @return players bet
     */
    private int playerOptions()
    {
        int bet = 0;
        boolean invalid;
        do
        {
            invalid = false;
            System.out.print("(F)old, (C)heck, (B)et? ");
            String response = scan.next();		// validate
            System.out.println();
            response = response.toLowerCase();
            char s = response.charAt(0);

            switch(s)
            {
                // user folds
                case 'f' :
                    bet = -1;
                    printLine();
                    System.out.println("You folded... so did everyone else.");
                    System.out.println("Pot goes to the dealer.");
                    printLine();
                    dealer.addChips(pot);
                    return bet;

                // user checks
                case 'c' :
                    bet = 0;
                    human.placeBet(bet);
                    break;

                // user places a bet
                case 'b' :
                    System.out.println("You have " + human.getChips() + " chips.");
                    System.out.print("Bet amount: ");
                    bet = human.placeBet(Player.valNumbers(scan.next()));
                    addToPot(bet);
                    System.out.println();
                    break;

                // user enters invalid input, cycles loop
                default  :
                    invalid = true;
                    System.out.print("Invalid Response.  Try again.\n");
                    break;
            } // end switch
        } while (invalid);

        return bet;
    } // end playerOptions

    /**
     * Prints user's hand as card pictures based on the round and a
     * live display of the highest card or highest poker hand.
     * Displays the current table based on the betting round.  At each round
     * an additional card is displayed for each player.  Shows chip amounts
     * for each player and the current pot amount.
     * @param round which betting round it is (starts at 2 to print 2 cards)
     */
    public void printRound(int round)
    {
        // draws players hand
        human.drawHandPic(round);

        // build temp array of current cards in hand
        // for live readout of player's hand status
        Card[] temp = new Card[round];
        for (int i = 0; i < round; i++)
        {
            temp[i] = human.getHand()[i];
        }

        // prints live readout of player's poker hand
        System.out.println(handStatus(temp, human));
        printLine();

        // prints current table
        System.out.println("Showing table:   \t\tPot: " + pot + " Chips");
        printLine();
        for (int i = 0; i < playersAry.size(); i++)
        {
            // prints player's name and chips
            String playerStatus = String.format("%s\t(Chips: %s)", playersAry.get(i).getName(), playersAry.get(i).getChips());
            System.out.println(playerStatus);

            // prints player's hand
            String s = "";
            for (int card = 0; card < round; card++)
            {
                // prints first card on table face down
                if (card == 0)
                {
                    s += String.format("%3s ", playersAry.get(i).getHand()[card].getNameSym(false));
                }
                // prints remaining cards face up
                else
                {
                    s += String.format("%3s   ", playersAry.get(i).getHand()[card].getNameSym(true));
                }
            }
            System.out.println(s + "\n");
        }
    }

    /**
     * Handles winner's array by allocating the pot to either one winner
     * or multiple, appropriately.  Prints confirmations of all actions.
     * NOTE:  If pot is split, the dealer receives anything not evenly split.
     * @param w winner's array
     */
    private void winStatus(ArrayList<Player> w)
    {
        printLine();

        // one winner
        if (w.size() == 1)
        {
            System.out.println(w.get(0).getName() + " wins " + pot + " chips!");
            System.out.print("Winning hand: ");
            w.get(0).printHand();
            System.out.println(handStatus(w.get(0).getHand(), w.get(0)));
            w.get(0).addChips(pot);
            return;
        }

        // multiple winners
        else if (w.size() > 1)
        {
            String s = "";

            // dealer gets remainder of split
            int dShare = pot % w.size();
            dealer.addChips(dShare);

            int share = pot / w.size();
            for (int i = 0; i < w.size(); i++)
            {
                // proper grammar
                if (i < w.size() - 1 && w.size() == 2)
                {
                    s += w.get(i).getName() + " ";
                }
                else if (i < w.size() - 1 && w.size() > 2)
                {
                    s += w.get(i).getName() + ", ";
                }
                else
                {
                    s += "and " + w.get(i).getName();
                }

                // chips added to each winner
                w.get(i).addChips(share);
            }

            System.out.println(s + " split the pot!");
            System.out.println("Each winner gets " + share + " chips!");
            // prints tying poker hand for split pots
            String temp = handStatus(w.get(0).getHand(), w.get(0));
            if (temp.contains("High Card: "))
            {
                System.out.println(temp.substring(0, temp.length() - 1));
            }
        }
        else
        {
            System.out.println("Unknown error.  Terminating...");
            System.exit(2);
        }
    }


// Win Checks ----------------------------------------------------------------------------------------------------------

    /**
     * Finds the highest player's score using returned values from poker hand checks.
     * Looks for multiples of the high score in the case of a tie.  Builds the winner's
     * array based on findings.
     * @return winner's array
     */
    public ArrayList<Player> getWinner()
    {
        ArrayList<Player> w = new ArrayList<Player>();
        double temp = 0;

        // gets highest player score
        for (Player p : playersAry)
        {
            handStatus(p.getHand(), p);

            if (p.getHandValue() > temp)
            {
                temp = p.getHandValue();
            }

        }

        // finds tied scores
        for (Player p : playersAry)
        {
            if (p.getHandValue() == temp)
            {
                w.add(p);
            }
        }

        return w;
    }

    /**
     * Sets players hand value to a calculated point value
     * based on the sorted positioning and face values of
     * all cards in their hand.
     * @param h hand
     * @param p player whose hand it is
     * @return String of the highest card
     */
    private static String highCard(Card[] h, Player p)
    {
        String s = "";
        int high = 0;

        sortByRank(h);
        s = h[h.length - 1].getNameSym(true);


        for (int i = h.length; i > 0; i--)
        {
            high += Math.pow(h[i - 1].getRankAsInt(), i);
        }

        // set hand value per player
        p.setHandValue((double) high / (double) Math.pow(10, h.length));
        return s;
    }

    /**
     * Finds a pair and returns base 2000, plus the face value of the
     * highest paired card (times 10) plus the face value of remaining
     * paired card, if found.  Returns -1 if no pair is found.  Point
     * value set to player only if match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int onePair(Card[] h, Player p)
    {
        int points = 0;
        int tempCards = h.length;

        sortByRank(h);

        boolean a = false;
        boolean b = false;
        boolean c = false;
        boolean d = false;

        switch (tempCards)
        {
            case 2 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt();
                break;

            case 3 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt();
                b = h[1].getRankAsInt() == h[2].getRankAsInt();
                break;

            case 4 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt();
                b = h[1].getRankAsInt() == h[2].getRankAsInt();
                c = h[2].getRankAsInt() == h[3].getRankAsInt();
                break;

            case 5 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt();
                if (a)
                {
                    highCard(h, p);
                    points += p.getHandValue();
                    points += h[1].getRankAsInt() * 10;
                }

                b = h[1].getRankAsInt() == h[2].getRankAsInt();
                if (b)
                {
                    highCard(h, p);
                    points += p.getHandValue();
                    points += h[2].getRankAsInt() * 10;
                }

                c = h[2].getRankAsInt() == h[3].getRankAsInt();
                if (c)
                {
                    highCard(h, p);
                    points += p.getHandValue();
                    points += h[3].getRankAsInt() * 10;
                }

                d = h[3].getRankAsInt() == h[4].getRankAsInt();
                if (d)
                {
                    highCard(h, p);
                    points += p.getHandValue();
                    points += h[4].getRankAsInt() * 10;
                }
                break;
        }


        if (a || b || c || d)
        {
            points += 2000;
            p.setHandValue(points);
        }
        else points = -1;

        return points;
    }

    /**
     * Finds two pair and returns base 3000, plus the face value of the
     * highest paired card (times 10) plus the face value of the remaining
     * card, if one is found.  Returns -1 if not found Point value set to
     * player only if match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int twoPair(Card[] h, Player p)
    {
        int points = 0;
        int tempCards = h.length;

        sortByRank(h);

        boolean a = false;
        boolean b = false;
        boolean c = false;

        switch (tempCards)
        {
            case 4 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                        h[2].getRankAsInt() == h[3].getRankAsInt();
                break;

            case 5 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                        h[2].getRankAsInt() == h[3].getRankAsInt();
                if (a) points += h[0].getRankAsInt() + h[3].getRankAsInt() * 10;

                b = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                        h[3].getRankAsInt() == h[4].getRankAsInt();
                if (b) points += h[0].getRankAsInt() + h[4].getRankAsInt() * 10;

                c = h[1].getRankAsInt() == h[2].getRankAsInt() &&
                        h[3].getRankAsInt() == h[4].getRankAsInt();
                if (c) points += h[1].getRankAsInt() + h[4].getRankAsInt() * 10;
                break;
        }

        if (a || b || c)
        {
            highCard(h, p);
            points += p.getHandValue();
            points += 3000;
            p.setHandValue(points);
        }
        else points = -1;

        return points;
    }

    /**
     * Finds a set (three of a kind) and returns base 4000, plus the
     * face value of the set.  Returns -1 if not found.
     * Point value set to player only if match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int set(Card[] h, Player p)
    {
        int points = 0;
        int tempCards = h.length;

        sortByRank(h);

        boolean a = false;
        boolean b = false;
        boolean c = false;

        switch (tempCards)
        {
            case 3 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                        h[1].getRankAsInt() == h[2].getRankAsInt();
                break;

            case 4 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                        h[1].getRankAsInt() == h[2].getRankAsInt();

                b = h[1].getRankAsInt() == h[2].getRankAsInt() &&
                        h[2].getRankAsInt() == h[3].getRankAsInt();
                break;

            case 5 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                        h[1].getRankAsInt() == h[2].getRankAsInt();
                if (a) points += h[2].getRankAsInt();

                b = h[1].getRankAsInt() == h[2].getRankAsInt() &&
                        h[2].getRankAsInt() == h[3].getRankAsInt();
                if (b) points += h[3].getRankAsInt();

                c = h[2].getRankAsInt() == h[3].getRankAsInt() &&
                        h[3].getRankAsInt() == h[4].getRankAsInt();
                if (c) points += h[4].getRankAsInt();
                break;
        }

        if (a || b || c)
        {
            highCard(h, p);
            points += p.getHandValue();
            points += 4000;
            p.setHandValue(points);
        }
        else points = -1;

        return points;
    }

    /**
     * Finds a full house (set and pair) and returns base 5000,
     * plus the face value of the pair rank, plus face value of the set
     * rank times 10.  Returns -1 if not found.  Point value set to
     * player only if match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int fullHouse(Card[] h, Player p)
    {
        int points = 0;

        sortByRank(h);

        boolean a, b;

        a = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                h[1].getRankAsInt() == h[2].getRankAsInt() &&
                h[3].getRankAsInt() == h[4].getRankAsInt();

        b = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                h[2].getRankAsInt() == h[3].getRankAsInt() &&
                h[3].getRankAsInt() == h[4].getRankAsInt();

        if (a)
        {
            points += h[0].getRankAsInt() * 10;
            points += h[4].getRankAsInt();
            points += 5000;
            p.setHandValue(points);
        }
        if (b)
        {
            points += h[4].getRankAsInt() * 10;
            points += h[0].getRankAsInt();
            points += 5000;
            p.setHandValue(points);
        }
        else points = -1;

        return points;
    }

    /**
     * Finds a four of a kind and returns base 6000, plus the
     * face value of the match.  Returns -1 if not found.
     * Point value set to player only if match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int fourKind(Card[] h, Player p)
    {
        int points = 0;
        int tempCards = h.length;

        sortByRank(h);

        boolean a = false;
        boolean b = false;

        switch (tempCards)
        {
            case 4 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                        h[1].getRankAsInt() == h[2].getRankAsInt() &&
                        h[2].getRankAsInt() == h[3].getRankAsInt() ;
                break;

            case 5 :
                a = h[0].getRankAsInt() == h[1].getRankAsInt() &&
                        h[1].getRankAsInt() == h[2].getRankAsInt() &&
                        h[2].getRankAsInt() == h[3].getRankAsInt() ;
                if (a) points += h[3].getRankAsInt();

                b = h[1].getRankAsInt() == h[2].getRankAsInt() &&
                        h[2].getRankAsInt() == h[3].getRankAsInt() &&
                        h[3].getRankAsInt() == h[4].getRankAsInt() ;
                if (b) points += h[4].getRankAsInt();
                break;
        }

        if (a || b)
        {
            highCard(h, p);
            points += p.getHandValue();
            points += 6000;
            p.setHandValue(points);
        }
        else points = -1;

        return points;
    }

    /**
     * Finds a straight (five cards in numerical order)
     * and returns base 7000, plus the face value of
     * the highest card.  If ace is high, points are
     * set to 7500.  Returns -1 if not found.
     * Point value set to player only if match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int straight(Card[] h, Player p)
    {
        int points = 0;
        int testRank;

        sortByRank(h);

        // if ace in hand
        if (h[h.length - 1].getRankAsInt() == 14)
        {
            // ace low
            boolean a = h[0].getRankAsInt() == 2 && h[1].getRankAsInt() == 3 &&
                    h[2].getRankAsInt() == 4 && h[3].getRankAsInt() == 5;
            if (a) points += h[3].getRankAsInt() + 7000;

            // ace high
            boolean b = h[0].getRankAsInt() == 10 && h[1].getRankAsInt() == 11 &&
                    h[2].getRankAsInt() == 12 && h[3].getRankAsInt() == 13;
            if (b) points += h[4].getRankAsInt() + 7500;

            p.setHandValue(points);
            return points;
        }
        // general case
        else
        {
            testRank = h[0].getRankAsInt() + 1;

            for (int i = 1; i < h.length; i++)
            {
                if (h[i].getRankAsInt() != testRank)
                {
                    points = -1;
                    return points;
                }
                testRank++;
            }
            highCard(p.getHand(), p);
            points += p.getHandValue() + 7000;
            p.setHandValue(points);
        }
        points = -1;
        return points;
    }

    /**
     * Finds a flush (five cards of same suit) and returns
     * base 8000, plus the face value of the highest card.
     * Returns -1 if not found.  Point value set to player
     * only if match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int flush(Card[] h, Player p)
    {
        int points = 0;
        sortBySuit(h);

        if (h[0].getSuitAsInt() == h[h.length - 1].getSuitAsInt())
        {
            highCard(h, p);
            points += p.getHandValue();
            points += 8000;
            p.setHandValue(points);
        }
        else points = -1;

        return points;
    }

    /**
     * Finds a straight flush and returns base 9000, plus
     * the value set it flush.  Returns -1
     * if not found.  Point value set to player only if
     * match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int straightFlush(Card[] h, Player p)
    {
        int points = 0;

        if (straight(h, p) > 0 && flush(h, p) > 0)
        {
            points += p.getHandValue();
            points += 9000;
            p.setHandValue(points);
        }
        else points = -1;

        return points;
    }

    /**
     * Finds a royal straight flush and returns base 10000, plus
     * the value set in flush.  Returns -1 if not found.  Point
     * value set to player only if match is found.
     * @param h hand
     * @param p player whose hand it is
     * @return point value assigned to player, -1 if not found
     */
    private static int royalStraightFlush(Card[] h, Player p)
    {
        int points = 0;

        if (straight(h, p) >= 7500 && flush(h, p) > 0)
        {
            points += p.getHandValue();
            points += 10000;
            p.setHandValue(points);
        }
        else points = -1;

        return points;
    }

// Sorts ---------------------------------------------------------------------------------------------------------------

    /**
     * Sorts hand by rank via selection in ascending order
     * (smallest first).
     * @param h hand
     */
    private static void sortByRank(Card[] h)
    {
        for (int i = 0; i < h.length; i ++)
        {
            int min_j = i;
            for (int j = i + 1; j < h.length; j++)
            {
                if ( h[j].getRankAsInt() < h[min_j].getRankAsInt())
                {
                    min_j = j;
                }
            }

            Card temp = h[i];
            h[i] = h[min_j];
            h[min_j] = temp;
        }
    }

    /**
     * Sorts hand by suit via selection in ascending order
     * (smallest first).
     * @param h hand
     */
    private static void sortBySuit(Card[] h)
    {
        for (int i = 0 ; i < h.length ; i++)
        {
            int min_j = i;
            for (int j = i + 1; j < h.length; j++)
            {
                if (h[j].getSuitAsInt() < h[min_j].getSuitAsInt())
                {
                    min_j = j;
                }
            }
            Card temp = h[i];
            h[i] = h[min_j];
            h[min_j] = temp;
        }
    }
}

