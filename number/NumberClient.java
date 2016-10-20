package number;

/*
*****************************************************************************************************************
                                 Name:            Number Client
                                 Version:         1.1
                                 Chapter:         10, Client
                                 Author:          Casey J. Murphy
                                 Date Created:    03 Apr 16
                                 Last Modified:   12 Apr 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

	Write an abstract superclass encapsulating a number; this class has one abstract void method: square.
	This class has two non-abstract subclasses: one representing a rational number, and the other encapsulating
	a complex number.  A rational number is represented by two integers, a numerator and a denominator.  A
	complex number is represented by two real numbers, the real part and the complex part of the complex number.
	You also need to include a class to test these two classes
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* 				1.1 - Added rQuery and cQuery from Rational and Complex classes for interface functionality
* 					  Rewrote query methods to instantiate respective objects
* 					  Updated user instructions
* 					  Added interface methods, removed from Number class
* 					  Added documentation
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* ***************************************************************************************************************
*/

/**
 * Simple client to work with the Number superclass and all subclasses thereof
 * @author Casey J. Murphy
 * @version 1.1
 */
public class NumberClient
{
    public static void main (String [] args)
    {
        formFeed();

        while (true)
        {
            System.out.println("Simple Number Interface");
            System.out.println("(Squares input values)");
            System.out.println("-----------------------");
            System.out.println("1.             Rational");
            System.out.println("2.              Complex");
            System.out.println("3.        Set precision");
            System.out.println("4.                 Exit");
            System.out.print("\nEnter selection:      ");
            String menuOption = Number.scanner();

            switch (menuOption)
            {
                case "1" :
                    Rational r = rQuery();
                    System.out.println();
                    System.out.println(r.getSquare());
                    break;

                case "2" :
                    Complex c = cQuery();
                    System.out.println();
                    System.out.println(c.getSquare());
                    break;

                case "3" :
                    Number.precisionDialog();
                    break;

                case "4" :
                    Number.close();
                    return;

                default  :
                    System.out.println("Invalid response.");
                    break;
            }

            pauseDialog();
            formFeed();
        }
    } // end main

//----------------------------------------------------------------------------------------------------------------------
// Queries

    /**
     * Queries user for and validates values to make up a rational number
     * @return Rational object
     */
    public static Rational rQuery()
    {
        System.out.println();
        System.out.println("Enter a whole number or a\nfraction of the form x/y. ");
        System.out.print("(If no '/' is provided, \nprogram assumes input as\na whole number): ");
        String num = Number.scanner();
        // create array for fraction
        String[] numAry = new String[2];
        // parse fraction array
        numAry = num.split("/");

        // if whole number
        if (num.indexOf("/") == -1)
        {
            int w = Number.valInteger(numAry[0], false);
            Rational o = new Rational(w);
            return o;
        }

        // if fraction
        int n = Number.valInteger(numAry[0], true);
        int d = Number.valInteger(numAry[1], true);
        Rational o = new Rational(n, d);
        return o;
    }

    /**
     * Queries user for and validates values that make up a complex number
     * @return Complex object
     */
    public static Complex cQuery()
    {
        System.out.println();
        System.out.print("Enter real part: ");
        double r = Number.valDecimal(Number.scanner());
        System.out.println("Enter imaginary part");
        System.out.print("(Do not include 'i'): ");
        double i = Number.valDecimal(Number.scanner());
        Complex o = new Complex(r, i);
        return o;
    }

//----------------------------------------------------------------------------------------------------------------------
// Interface

    /**
     *  Simulates a form feed.
     */
    public static void formFeed()
    {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * 'Press Enter to Continue' dialog
     */
    public static void pauseDialog()
    {
        System.out.println("\n\nPress Enter to Continue...");
        Number.pause();
    }
} // end class
