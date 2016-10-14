package baseballStats;

/*
*****************************************************************************************************************
                                 Name:            Baseball Statistics Client
                                 Version:         1.3
                                 Chapter:         8, Client 1
                                 Author:          Casey J. Murphy
                                 Date Created:    11 Mar 16
                                 Last Modified:   28 Mar 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

		Write a client class to test all the methods in your Baseball Team class
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* 				1.1 - Removed print of batting averages, incorporated into toString
* 					  Moved arrays out of global, into main
* 					  Added documentation
* 				1.2 - Changed client name to TeamClient
* 					  Changed how the comparisons print their results to reduce clutter
* 				1.3 - Added object creation status function
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* 				(1) - Mismatch of total elements in hits and atBats arrays, in overloaded constructor
* ***************************************************************************************************************
*/


/**
 * Client tester for Team class - Not interactive
 * Creates statistics objects for 3 baseball teams.  Prints output appropriate to each method in the class.
 * @author Casey J. Murphy
 *
 */
public class TeamClient
{
    // global variables

    public static void main (String[] args)
    {
        // test arrays, yanks used throughout client, orioles and rangers created for comparisons
        // orioles has different values, rangers has same values
        int [] yankeesAtBat = {2,6,2,2,2,24,18,21,18,3,12,25,17,21,25,17,21,25,18,11,24,20,16,4,17,22,14,14,15,12,18,22,17,9,18,14,14,21,2,1,1,1,2,2,1,5,1};
        int [] yankeesHits  = {1,3,1,1,1,10,6,7,6,1,4,8,5,6,7,5,3,6,5,4,1,4,5,3,3,3,2,3,3,2,1,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0};

        int [] oriolesAtBat = {2,6,2,2,2,24,18,21,18,3,12,25,17,21,25,17,21,25,18,11,24,20,16,4,17,22,14,14,15,12,18,22,17,9,18,14,14,21,2,1,1,1,2,2,1,5,1};
        int [] oriolesHits  = {2,3,1,1,1,10,6,7,6,1,4,8,5,6,7,5,3,6,5,4,1,4,5,3,3,3,2,3,3,2,1,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0};

        int [] rangersAtBat = {2,6,2,2,2,24,18,21,18,3,12,25,17,21,25,17,21,25,18,11,24,20,16,4,17,22,14,14,15,12,18,22,17,9,18,14,14,21,2,1,1,1,2,2,1,5,1};
        int [] rangersHits  = {1,3,1,1,1,10,6,7,6,1,4,8,5,6,7,5,3,6,5,4,1,4,5,3,3,3,2,3,3,2,1,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0};

        Team yankees = new Team("yankees", yankeesAtBat, yankeesHits);
        status(yankees);
        Team orioles = new Team("orioles", oriolesAtBat, oriolesHits);
        status(orioles);
        Team rangers = new Team("rangers", rangersAtBat, rangersHits);
        status(rangers);
        separator();

        // print object array data
        System.out.println(yankees.toString());
        separator();

        // print results of equality checks
        System.out.println("Comparing Team Statistics:\n");
        System.out.println("Yankees stats same as Orioles stats: " + yankees.equals(orioles));
        System.out.println("Yankees stats same as Rangers stats: " + yankees.equals(rangers));
        separator();

        // print total hits
        System.out.println("Total Hits: " + yankees.totalHits());
        separator();

        //print number of players with BA > .3
        System.out.println("Top Hitters (BA > .300): " + yankees.topHitters());
        separator();

        // print array sorted by hits (ascending), grouped by value
        System.out.println("Sorted Number of Hits:\n");
        int [] hitsSort = yankees.sortedHits();
        for (int i = 0; i < hitsSort.length; i++)
        {
            System.out.print(hitsSort[i] + " ");

            // prints new line if next value is different, checks for end of array before comparing
            if (i + 1 < hitsSort.length && hitsSort[i] != hitsSort[i + 1])
            {
                System.out.println();
            }
        }
        System.out.println();
        separator();

        System.exit(0);
    } // end main

    /**
     * Prints a separator line.
     */
    public static void separator()
    {
        System.out.println("----------------------------------------------------");
    }

    /**
     * Prints result of team object creation by name
     * @param obj team object
     */
    public static void status(Team obj)
    {
        System.out.println(obj.getTeamName() + " successfully created.");
    }

} // end class TeamClient
