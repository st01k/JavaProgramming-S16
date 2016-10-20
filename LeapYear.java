
/*
*****************************************************************************************************************
                                 Name:            Leap Year Calculator
                                 Version:         1.4
                                 Chapter:         5, Program 1
                                 Author:          Casey J. Murphy
                                 Date Created:    06 Feb 16
                                 Last Modified:   28 Feb 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

  Write a program that takes a string as input from the keyboard, representing a year.
  Your program should do the following:

    If the year is entered as two characters, convert it to an int, and add 2000 to it, and output it
    If the year entered has four characters, convert it to an int and output it
    If the year entered has neither two nor four characters, output that the year is not valid
    Test the year to see if it is a leap year (divisible by 4 with no remainder),
    a leap leap year (divisible by 4 and 400 with no remainder),
    or a leap leap leap year (a leap leap year that is also divisible by 1600).
    Output the result.
* ***************************************************************************************************************
* Exit Codes:	(0) - Normal completion or exited from main menu
* 				    (1) - Invalid input for year
* 				    (2) - Invalid input from run again dialogue
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
*			 				1.0 - Program working with correct output
*			 			  1.1 - Refined output, added documentation, added regex to exit on non-number input
*			 			  1.2 - Removed extraneous variables
*			 			  1.3 - Added run again loop with validation
*			 			  1.4 - Added terminating print statement upon invalid input from run again dialogue
*			 			        Added documentation
* ***************************************************************************************************************
*/
import java.util.Scanner;

public class LeapYear {

    // global
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        String again;   // loop flag

        // begin run again loop
        do {
            // interface
            System.out.println("Leap Year Calculator\n");
            System.out.println("2 digits : any year within the current century (2000 - 2099)");
            System.out.println("4 digits : any year outside of above range\n");
            System.out.print("Enter Year (YY or YYYY): ");
            String input = scan.next();

            // validate input: integer of either 2 or 4 digits in length
            if (input.length() != 2 && input.length() != 4 || !(input.matches("[0-9]+"))) {
                System.out.println("Invalid input.  Terminating...");
                System.exit(1);
            }

            int year = 0;
            // current century
            if (input.length() == 2) {
                year = (Integer.parseInt(input)) + 2000;
            }
            // any other four digit year
            if (input.length() == 4) {
                year = Integer.parseInt(input);
            }

            // cascading leap year checks
            if ((year % 4) == 0) {                                // checks for leap year
                System.out.println("\n" + year + ": ");
                System.out.println("Leap year");
                if ((year % 400) == 0) {                            // checks for double leap year
                    System.out.println("Double leap year");
                    if ((year % 1600) == 0) {                         // checks for triple leap year
                        System.out.println("Triple leap year");
                    } // end triple leap year if
                } // end double leap year if
            } // end leap year if

            // if year fails leap year checks
            else {
                System.out.println("\n" + year + " is not a leap year.");
            }

            // run again dialogue
            System.out.print("\nWould you like to run this program again (Y/N)? ");
            again = scan.next();
            again = again.toLowerCase();

            // validates run again input, exits on fail
            if (!again.matches("[yn]")) {
                System.out.println("Invalid input.  Terminating...");
                System.exit(2);
            }
            System.out.println("-------------------------------------------------\n");

        } while (again.equals("y"));
        // end run again loop

        scan.close();
        System.exit(0);
    } // end main
} // end class LeapYear