package number;

/*
*****************************************************************************************************************
                                 Name:            Number, Rational Subclass
                                 Version:         1.1
                                 Chapter:         10, Subclass 1
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
* 				1.1 - Added one parameter constructor for whole numbers
* 					  Added square variable and getSquare method
* 					  Changed square method to populate square instance variable instead of printing out
* 					  Removed query, added it to main to simplify Rational api functionality
* 					  Updated all calls to valInteger with 'is a fraction' boolean parameter
* 					  Added documentation
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* ***************************************************************************************************************
*/

/**
 * Rational subclass of Number superclass
 * @author Casey J. Murphy
 * @version 1.1
 */
public class Rational extends Number
{
    // instance variables
    private int numerator;
    private int denominator;
    private String square;

//----------------------------------------------------------------------------------------------------------------------
// Constructors

    /**
     * Default - Rational object is 0
     */
    public Rational()
    {
        super();
        numerator = 0;
        denominator = 1;
    }

    /**
     * Overloaded x 1 - Rational object is whole number
     * @param w whole number
     */
    public Rational(int w)
    {
        super();
        setNumerator(w);
        setDenominator(1);
    }

    /**
     * Overloaded x 2 - Rational object is fraction
     * @param n numerator
     * @param d denominator
     */
    public Rational(int n, int d)
    {
        super();
        setNumerator(n);
        setDenominator(d);
    }

//----------------------------------------------------------------------------------------------------------------------
// Accessors & Mutators

    /**
     * Returns string with equation and squared value
     * @return square
     */
    public String getSquare()
    {
        square();
        return square;
    }

    /**
     * Sets numerator
     * @param n
     */
    private void setNumerator(int n)
    {
        numerator = n;
    }

    /**
     * Sets denominator.  Does not allow 0, re-prompts and validates
     * @param d
     */
    private void setDenominator(int d)
    {
        while (d == 0)
        {
            System.out.print("Division by 0 not allowed.  Re-enter: ");
            d = Number.valInteger(scan.next(), true);
        }

        denominator = d;
    }

//----------------------------------------------------------------------------------------------------------------------
// Completed abstract method from Number

    @Override
    /**
     * Populates instance variable 'square' with equation used and result of calculation
     */
    public void square()
    {
        int sqn = numerator * numerator;
        int sqd = denominator * denominator;
        double squared = (double) sqn / sqd;

        square = "(" + numerator + "/" + denominator + ")^2 = ";

        if (precisionFlag())
        {
            String fSquared = df.format(squared);
            square += sqn + "/" + sqd + " = " + fSquared;
            return;
        }

        square += sqn + "/" + sqd + " = " + squared;
    }
} // end class
