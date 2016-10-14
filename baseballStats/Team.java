package baseballStats;

/*
*****************************************************************************************************************
                                 Name:            Baseball Team Statistics Class
                                 Version:         1.3
                                 Chapter:         8, Class 1
                                 Author:          Casey J. Murphy
                                 Date Created:    11 Mar 16
                                 Last Modified:   28 Mar 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

   	Write a class encapsulating the concept of statistics for a baseball team, which has the following
   	attributes:
   					A number of players
   					A list of number of hits for each player
   					A list of number of at-bats for each player

	Write the following methods:
		A constructor, with two equal length arrays as parameters, the number of hits per player,
		and the number of at-bats per player
		Accessors, mutators, toString, and equals methods
		Generate and return an array of batting averages based on the attributes given
		Calculate and return the total number of hits for the team
		Calculate and return the number of players with a batting average greater than .300
		A method returning an array holding the number of hits, sorted in ascending order
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* 				1.1 - Optimized data types for minimal footprint
* 					  Added exit and error for mismatching data (ref: error 1)
* 					  Incorporated print of batting averages into toString
* 					  Added documentation
* 				1.2 - Changed class name to Team
* 					  Added team name to class with get and set methods
* 					  Included team name in toString class method
* 					  Changed doubles in batting average calculation to floats
* 				1.3 - Modified setTeamName by removing an extraneous variable
* 					  Changed some method variable names to reflect class name changes in 1.2
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* 				(1) - Mismatch of total elements in hits and atBats arrays, in overloaded constructor
* ***************************************************************************************************************
*/

/**
 * A simple baseball statistics class.  Creates a team object to house statistics for the team.
 * Provides over-ridden toString and equals methods, a hits sorter, batting
 * average generator, top hitters calculator, and sums total hits for the team.
 * @author Casey J. Murphy
 * @version 1.3
 */

import java.text.DecimalFormat;

public class Team
{

    // instance variables
    private byte numPlayers;
    private String teamName;
    private int [] hitsPerPlayer;
    private int [] atBatPerPlayer;

//----------------------------------------------------------------------------------------------------------------------
// Constructors

    /**
     * Default Constructor <br>
     * 25 Players
     * Team name is a placeholder
     * At Bats per player (array)
     * Hits per player (array)
     */
    public Team()
    {
        numPlayers = 25;
        teamName = "Placeholder";
        atBatPerPlayer = new int [numPlayers];
        hitsPerPlayer = new int [numPlayers];
    }

    /**
     * Overloaded Constructor <br>
     * Constructs team statistics object.  Number of players is generated based on the number
     * of elements within the array(s).  Both arrays must match in total elements.
     * @param startName name of the team
     * @param startAtBatPerPlayer array with at bat values for each team member
     * @param startHitsPerPlayer array with hit values for each team member
     */
    public Team(String startName, int [] startAtBatPerPlayer, int [] startHitsPerPlayer)
    {
        if (startAtBatPerPlayer.length == startHitsPerPlayer.length)
        {
            numPlayers = (byte) startAtBatPerPlayer.length;
        }
        else
        {
            System.err.println("Mismatching data in statistics arrays.  Terminating...");
            System.exit(1);
        }

        setTeamName(startName);
        setAtBatPerPlayer(startAtBatPerPlayer);
        setHitsPerPlayer(startHitsPerPlayer);
    }

//----------------------------------------------------------------------------------------------------------------------
// Accessors

    /**
     *
     * @return Number of players
     */
    public byte getNumPlayers()
    {
        return numPlayers;
    }

    /**
     *
     * @return Team name
     */
    public String getTeamName()
    {
        return teamName;
    }

    /**
     *
     * @return Array of at bat values for the team
     */
    public int [] getAtBatPerPlayer()
    {
        return atBatPerPlayer;
    }

    /**
     *
     * @return Array of hits values for the team
     */
    public int [] getHitsPerPlayer()
    {
        return hitsPerPlayer;
    }

//----------------------------------------------------------------------------------------------------------------------
// Mutators

    /**
     * Sets team name with first letter capitalized and the rest lowercase
     * @param newTeamName String containing the name of the team
     */
    public void setTeamName(String newTeamName)
    {
        teamName = newTeamName.toLowerCase();
        teamName = Character.toString(teamName.charAt(0)).toUpperCase() + teamName.substring(1);
    }

    /**
     * Loops through array input and assigns it to team's at bat data.
     * @param newAtBatPerPlayer Array with at bat data
     */
    public void setAtBatPerPlayer(int [] newAtBatPerPlayer)
    {
        atBatPerPlayer = new int [numPlayers];

        for (byte i = 0; i < numPlayers; i++)
        {
            atBatPerPlayer[i] = newAtBatPerPlayer[i];
        }
    }

    /**
     * Loops through array input and assigns it to team's hit data.
     * @param newHitsPerPlayer Array with hits data
     */
    public void setHitsPerPlayer(int [] newHitsPerPlayer)
    {
        hitsPerPlayer = new int [numPlayers];

        for (byte i = 0; i < numPlayers; i++)
        {
            hitsPerPlayer[i] = newHitsPerPlayer[i];
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Overridden methods

    /**
     * @return String with team name, total players and a numbered list of the player's stats, including batting average
     */
    public String toString()
    {
        DecimalFormat df = new DecimalFormat(".000");
        float [] bAvg = genBattingAverages();

        String str = teamName + "\n\n" +
                "Total Players:\t\t  " + numPlayers + "\n\n"
                + "\tAB:\tH:\t BA:\n\n";

        for (byte i = 0; i < numPlayers; i++)
        {
            str += (i + 1) + ":\t " + atBatPerPlayer[i]
                    + "\t" + hitsPerPlayer[i]
                    + "\t" + df.format(bAvg[i]) + "\n";
        }

        return str;
    }

    /**
     * @param o Object that the current object is to be compared to
     * @return Boolean result of equality comparison
     */
    public boolean equals(Object o)
    {
        // check for object compatibility
        if (!(o instanceof Team))
        {
            return false;
        }
        // check for content equality
        else
        {
            Team objStats = (Team) o;

            if (numPlayers != objStats.numPlayers)
            {
                return false;
            }

            if (hitsPerPlayer.length != objStats.hitsPerPlayer.length)
            {
                return false;
            }
            if (atBatPerPlayer.length != objStats.atBatPerPlayer.length)
            {
                return false;
            }
            // checks on elements for both arrays
            for (byte i = 0; i < numPlayers; i++)
            {
                if (hitsPerPlayer[i] != objStats.hitsPerPlayer[i])
                {
                    return false;
                }
                if (atBatPerPlayer[i] != objStats.atBatPerPlayer[i])
                {
                    return false;
                }
            }

            return true;
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Class Methods

    /**
     * Sorts incoming array by selection in ascending order.
     * @param array
     * @return sorted array
     */
    private static int [] selectionSort(int [] array)
    {
        int temp;
        int max;

        for (byte i = 0; i < array.length - 1; i++)
        {
            max = indexOfLargestElement(array, (byte) (array.length - i));

            // swap elements
            temp = array[max];
            array[max] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }

        return array;
    }

    /**
     * For use with the selection sort.  Finds the index of the largest array element.
     * @param array Array to search in
     * @param j Subarray size
     * @return Index of the largest array element
     */
    private static byte indexOfLargestElement(int [] array, byte j)
    {
        byte index = 0;
        for (byte i = 0; i < j; i++)
        {
            if (array[i] > array[index])
            {
                index = i;
            }
        }
        return index;
    }

//----------------------------------------------------------------------------------------------------------------------
// Instance Methods

    /**
     * Builds an array of calculated batting averages.
     * @return Array of batting averages
     */
    public float [] genBattingAverages()
    {
        float [] avgAry = new float [numPlayers];

        for (int i = 0; i < numPlayers; i++)
        {
            avgAry[i] = (float) hitsPerPlayer[i] / (float) atBatPerPlayer[i];
        }

        return avgAry;
    }

    /**
     * Calculates total hits per team.
     * @return Number of total hits
     */
    public byte totalHits()
    {
        byte total = 0;

        for (int i = 0; i < numPlayers; i++)
        {
            total += hitsPerPlayer[i];
        }

        return total;
    }

    /**
     * Calculates the number of top hitters on the team, defined by a batting average greater than 0.300.
     * @return Number of top hitters
     */
    public byte topHitters()
    {
        float [] avgAry = genBattingAverages();
        byte top = 0;

        for (byte i = 0; i < avgAry.length; i++)
        {
            if (avgAry[i] > 0.3)
            {
                top++;
            }
        }

        return top;
    }

    /**
     * Builds array sorted by hits per player.
     * @return Sorted array of hits per player
     */
    public int [] sortedHits()
    {
        int [] sortedHitsArray = selectionSort(hitsPerPlayer);

        return sortedHitsArray;
    }
}
