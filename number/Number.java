package number;

/*
*****************************************************************************************************************
                                 Name:            Number Superclass (abstract)
                                 Version:         1.1
                                 Chapter:         10, Superclass
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
* 				1.1 - Updated valInteger to accommodate for re-entering fractional parts of a rational number
* 					  Updated all calls to the former
* 					  Broke up formfeed into formfeed and pause
* 					  Added documentation
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* ***************************************************************************************************************
*/

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Number superclass<br>
 * Encapsulates Rational and Complex subclasses
 * @category abstract
 * @version 1.1
 * @author st01k
 *
 */
public abstract class Number
{
    // constants
    protected static final Scanner scan = new Scanner(System.in);
    protected static final DecimalFormat df = new DecimalFormat();
    private static final Scanner keyboard = new Scanner(System.in);

    // class variables
    private static int precision = 0;
    private static int fractionRetryCount = 0;		// for valInteger

    /**
     * Default constructor - nothing
     */
    public Number(){}

//----------------------------------------------------------------------------------------------------------------------
    /**
     * Abstract method
     */
    public abstract void square();

//----------------------------------------------------------------------------------------------------------------------
// Validation

    /**
     * Validates decimal input
     * @param value user input
     * @return input string parsed as a double
     */
    public static double valDecimal(String value)
    {
        while (!value.matches("^-?[0-9]+(\\.\\d+)?$"))
        {
            System.out.print("Invalid response.  Re-enter: ");
            value = scan.next();
        }
        return Double.parseDouble(value);
    }

    /**
     * Validates integer input.  Additional validation dialog for parts of a fraction
     * @param value user input
     * @param fraction true if part of fraction, false otherwise
     * @return input string parsed as an integer
     */
    public static int valInteger(String value, boolean fraction)
    {
        while (!value.matches("^-?[0-9]+"))
        {
            System.out.print("Invalid response.\nRe-enter");

            if (fraction)
            {
                fractionRetryCount++;
                if (fractionRetryCount % 2 == 0)
                {
                    System.out.print(" Numerator");
                }
                else
                {
                    System.out.print(" Denominator");
                }
            }

            System.out.print(": ");
            value = scan.next();
        }
        return Integer.parseInt(value);
    }

//----------------------------------------------------------------------------------------------------------------------
// Precision

    /**
     * Sets precision for formatted decimal output
     * @param p precision amount (number of decimal places)
     */
    private static void setPrecision(int p)
    {
        df.setMaximumFractionDigits(precision);
    }

    /**
     * Checks if precision has been previously set
     * @return true if so, false otherwise
     */
    public static boolean precisionFlag()
    {
        if (precision > 0) return true;
        return false;
    }

    /**
     * Prompts for precision amount, calls setPrecision with validated input
     */
    public static void precisionDialog()
    {
        System.out.println("\nEnter 0 to reset.");
        System.out.print("Decimal precision:    ");
        precision = valInteger(scan.next(), false);
        if (precisionFlag())
        {
            setPrecision(precision);
            System.out.println("Precision set.\n");
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Scanner functions

    /**
     * Scans for next string
     * @return input from user
     */
    public static String scanner()
    {
        return scan.next();
    }

    /**
     * Closes all scanners and notifies user that program is terminating
     */
    public static void close()
    {
        System.out.println("\nProgram terminating...");
        scan.close();
        keyboard.close();
    }

    /**
     * Waits for 'Enter' to be pressed
     */
    public static void pause()
    {
        keyboard.nextLine();
    }
} // end class