
/*
*****************************************************************************************************************
                                 Name:            Recursion
                                 Version:         1.0
                                 Chapter:         13, Class and Client
                                 Author:          Casey J. Murphy
                                 Date Created:    11 Apr 16
                                 Last Modified:   18 Apr 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

	Multiplying two integers, a and b, is actually done by adding the first number, a, to itself b times.
	Write a recursive method that will multiply two numbers by adding the first to itself this way.

	Pseudo-code:
	in main
		Get two numbers, make sure they are both positive
		Call multiply with two numbers and result 0

	multiply(int a, int b, int result)
		if b is not 0
			add a to result
			subtract 1 from b
			return multiply with new parameters
		else
			return result
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* ***************************************************************************************************************
*/
// package wvup.edu;

import java.util.Scanner;

/**
 * Simple recursion example using multiplication.
 * @author Casey J. Murphy
 * @version 1.0
 */
public class Recursion {

    static Scanner scan = new Scanner(System.in);

    public static void main (String[] args)
    {
        System.out.print("Enter first number: ");
        int first = valInteger(scan.next());
        System.out.print("Enter second number: ");
        int second = valInteger(scan.next());
        System.out.println("Result: " + multiply(first, second, 0));
    }

    /**
     * Recursive method that takes in factors of a multiplication problem and adds
     * a to itself b times until b is 0.  Returns the result of the calculations.
     * @param a first factor (user input)
     * @param b second factor (user input)
     * @param result result of calculations
     * @return result of calculations
     */
    public static int multiply (int a, int b, int result)
    {
        if (b != 0)
        {
            System.out.println("\nAdding first term, " + a + " to " + result);
            result += a;
            System.out.println("Decrementing second term, " + b + " to " + (b - 1));
            b--;
            System.out.println("Calling multiply again with result: " + result + "\n");
            return (multiply(a, b, result));
        }
        System.out.println("Second term: " + b + ".  Exiting recursive method\n");
        return result;
    }

    /**
     * Validates user input to accept only positive integers.  Re-prompts if invalid until valid.
     * @param value user input as a string
     * @return validated input
     */
    public static int valInteger(String value)
    {
        while (!value.matches("^[0-9]+"))
        {
            System.out.print("Invalid response.  Re-enter: ");
            value = scan.next();
        }
        return Integer.parseInt(value);
    }
}
