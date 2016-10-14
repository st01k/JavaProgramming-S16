
/*
*****************************************************************************************************************
                                 Name:            Binary Search
                                 Version:         1.6
                                 Chapter:         2, 3
                                 Author:          Casey J. Murphy
                                 Date Created:    01 Feb 16
                                 Last Modified:   29 Feb 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

  Implement a binary search for guessing a number between a low and high number (1 - 100).
  Your program should prompt the user to pick a number between the low and high number, and
  use a binary search algorithm to search between the low and high number until it correctly
  guesses the number the user picked.  Track how many "guesses" it takes to discover the answer,
  and display the result.
* ***************************************************************************************************************
* Exit Codes:		(0) - Normal completion
* 				      (1) - Invalid input, out of range after 3 attempts to re-enter
* 				      (2) - Invalid input, run again dialogue
* ***************************************************************************************************************
* ChangeLog:		0.1 - Rough draft
*			 		      1.0 - Program working with correct output
*			 		      1.1 - Output formatting support for up to 5 digit ranges (for positive numbers)
*			 		      1.2 - Removed redundant statements in binary search loop
*			 	            	Added documentation
*			 		      1.3 - Fixed search range print out to display the correct range through each iteration
*			 		      1.4 - Fixed bug that caused an endless loop if the target is the upper limit (see comment in code)
*			 		            Changed counters to type byte to conserve memory
*	      		 		      Made boolean statements more readable by changing their order
*			       		      Added more documentation
*			 	      	1.5 - Added run again loop/dialogue with answer validation
*					      1.6 - Replaced threshold string literal with Integer class constants
*						          Errors print to system error
*						          Cleaned up documentation
* ***************************************************************************************************************
*/

import java.util.Scanner;

public class BinarySearch {

    //class constants
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        String again; // loop flag

        do {
            // interface
            System.out.print("\nBinary Search (Threshold: ");
            System.out.println(Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ")");
            System.out.println();
            System.out.print("Enter minimum range: ");
            int minRange = scan.nextInt();
            System.out.print("Enter maximum range: ");
            int maxRange = scan.nextInt();
            System.out.print("Enter search target within the range of " + minRange + " - " + maxRange + ": ");
            int target = scan.nextInt();
            System.out.println();

            // validates user input according to specified range
            boolean inRange = (minRange <= target) && (target <= maxRange);
            byte erCount = 0;  // counter for max errors before exiting program (3)

            // asks for user to re-enter a valid value if not in range
            while (!inRange) {
                // exits if 3 attempts fail
                if (erCount > 2) {
                    System.out.println("Error code (01) - Invalid Input.  Terminating...");
                    System.exit(1);
                } // end if
                System.out.print("Target out of range.  Re-enter: ");
                target = scan.nextInt();
                System.out.println();
                inRange = (minRange <= target) && (target <= maxRange);
                erCount++;
            } // end while, re-entry

            byte counter = 1;  			// counts guesses made by the program
            int lowNum = minRange;
            int highNum = maxRange;
            int rangeHolder;  			// used to display search range
            int guess = (lowNum + highNum) / 2;
            String result;    			// printable result of comparison calculation

            // begin search loop
            while (guess != target) {
                // check for target as upper limit to avoid endless loop that
                // results from division by 2 never reaching the upper limit
                if (target == highNum) {
                    guess = highNum;
                    break;
                }
                //begin binary search for target
                else if (target < guess) {
                    result = "Lower";
                    rangeHolder = highNum;
                    highNum = guess;
                    status(counter, rangeHolder, lowNum, guess, result);
                } // end if
                else {
                    result = "Higher";
                    rangeHolder = lowNum;
                    lowNum = guess;
                    status(counter, highNum, rangeHolder, guess, result);
                } // end else
                counter++;
                guess = (lowNum + highNum) / 2;
            } //end while, guessing

            result = "Found";
            status(counter, highNum, lowNum, guess, result);
            System.out.println("\nTarget (" + target + ") found in " + counter + " guesses.");
            // end binary search for target

            // run again dialogue
            System.out.print("\nWould you like to run this program again (Y/N)? ");
            again = scan.next();
            validateAns(again);
            System.out.println("-------------------------------------------------\n");

        } while (again.equals("y"));
        // end run again loop

        scan.close();
        System.exit(0);
    }  // end main

    // ----------------------------------------------------------------------------------------------------------------
// prints the status information at each iteration (guess) through the binary search loop
// @param counter, highNum, lowNum, guess, and result
// (highNum and lowNum may be substituted with rangeHolder from main)
    public static void status(int c, int h, int l, int g, String r){
        System.out.printf("%2d) Search Range: %4d - %-5d  |  Guess: %4d  |  Result: %s\n", c, l, h, g, r);
    }
    // ----------------------------------------------------------------------------------------------------------------------
// validates input from run again dialogue, exits if users input doesn't match one of: Y y N n
// @param ans: answer to run again dialogue
    private static void validateAns(String ans) {
        ans = ans.toLowerCase();
        if (ans.matches("[yn]")) {
            return;
        }
        System.err.println("Error code (02) - Invalid input.  Terminating...");
        System.exit(2);
    }
} //end class BinarySearch