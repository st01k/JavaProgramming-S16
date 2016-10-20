package poker;

/*
*****************************************************************************************************************
                                 Name:            Five Card Stud, Client
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
 * Simple client that tests packaged class files for a five card stud
 * poker game.  Implements a play again loop.
 * @author Casey J. Murphy
 * @version
 */

public class Client {

    public static void main(String[] args)
    {
        // would prompt for game type if multiple games were available

        // loop flag for play again
        String again = "";
        do
        {
            // greeting
            gameName();
            Player.resetID();
            System.out.print("How many players at this table (2 - " + Game.getMaxPlayers() + ")? ");
            int iPlayers = Game.checkPlayers(Game.scan.next());
            Player.setStartChips(1000, 5);

            // instantiate game
            Poker fiveStud = new Poker(iPlayers, 5, false);

            // set ante dialog, default is 25 chips
            System.out.print("Would you like to set the ante for this game (Y/N)? ");
            String response = Game.scan.next();
            if (response.matches("[Yy]"))
            {
                System.out.print("Enter ante amount: ");
                fiveStud.setAnte(Player.valNumbers(Game.scan.next()));
                System.out.print("Ante set at " + fiveStud.getAnte() + " chips.\n");
            }
            else if (response.matches("[Nn]"))
            {
                System.out.print("Using default ante of " + fiveStud.getAnte() + " chips.\n");
            }
            else
            {
                System.out.print("Invalid response.  Using default ante of " + fiveStud.getAnte() + " chips.\n");
            }

            // start list of players and chip amounts
            System.out.println("\nAt the table:");
            Game.printLine();
            fiveStud.printPlayers();
            Game.pause();

            // play
            fiveStud.play();

            // play again dialog
            System.out.print("Would you like to play again (resets chips) (Y/N)? ");
            again = Game.scan.next();
            Game.formFeed();

            // end play again loop
        } while(again.matches("[Yy]"));

        Game.scan.close();
        Game.kb.close();
        System.out.println("Thanks for playing...");
        gameName();
    } // end main

    /**
     * Prints game name
     */
    public static void gameName()
    {
        System.out.println(" ___ _            ___             _   ___ _           _");
        System.out.println("| __(_)_ _____   / __|__ _ _ _ __| | / __| |_ _  _ __| |");
        System.out.println("| _|| \\ V / -_) | (__/ _` | '_/ _` | \\__ \\  _| || / _` |");
        System.out.println("|_| |_|\\_/\\___|  \\___\\__,_|_| \\__,_| |___/\\__|\\_,_\\__,_|");
        System.out.println();
    }
}
