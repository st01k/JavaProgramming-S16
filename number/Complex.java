package number;

/*
*****************************************************************************************************************
                                 Name:            Number, Complex Subclass
                                 Version:         1.1
                                 Chapter:         10, Subclass 2
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
* 				1.1 - Added square variable and getSquare method
* 					  Changed square method to populate square instance variable instead of printing out
* 					  Removed query, added it to main to simplify Complex api functionality
* 					  Added pString to facilitate correct sign usage in building the square string variable
* 					  Added documentation
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* ***************************************************************************************************************
*/

/**
 * Complex subclass of Number superclass
 * @author Casey J. Murphy
 * @version 1.1
 */
public class Complex extends Number
{
    // instance variables
    private double real;
    private double imag;
    private String square;

//----------------------------------------------------------------------------------------------------------------------
// Constructors

    /**
     * Default Complex object is 0 + i
     */
    public Complex()
    {
        super();
        real = 0;
        imag = 1;
    }

    /**
     * Overloaded - Complex object is real part + imaginary part
     * @param r
     * @param i
     */
    public Complex(double r, double i)
    {
        super();
        setReal(r);
        setImag(i);
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
     * Sets real part of complex number
     * @param r
     */
    private void setReal(double r)
    {
        real = r;
    }

    /**
     * Sets imaginary part of complex number
     * @param i
     */
    private void setImag(double i)
    {
        imag = i;
    }

//----------------------------------------------------------------------------------------------------------------------
// Completed abstract method from Number

    @Override
    /**
     * Populates instance variable 'square' with equation used and result of calculation
     */
    public void square()
    {
        // uses quadratic form: (a + bi)^2 = a^2 + 2abi + bi^2
        double f = real * real;				// first: a^2
        double oi = 2 * real * imag;		// outer/inner: 2abi
        double l = imag * imag * -1;		// last: bi^2 (i^2 = -1)

        // therefore, becomes the form: a^2 - b^2 + 2abi
        double realPart = f - l;			// a^2 - b^2

        // prints the equation to be calculated, implements proper sign usage
        if (imag >= 0)
        {
            square = ("(" + real + " + " + imag + "i)^2 = ");
        }
        else
        {
            square = "(" + real + " - " + Math.abs(imag) + "i)^2 = ";
        }

        // if user has set precision
        if (precisionFlag())
        {
            String fReal = df.format(realPart);
            String fImag = df.format(Math.abs(oi));
            boolean flag = true;
            if (oi < 0) flag = false;
            pString(fReal, fImag, flag);
            return;
        }

        // add imaginary part to output, implements proper sign usage
        if (oi < 0)
        {
            square += realPart + " - " + Math.abs(oi) + "i";
            return;
        }

        square += realPart + " + " + oi + "i";
    }

    /**
     * Populates 'square' with real number part of result of calculation
     * to the set precision, implements proper sign usage
     * @param rP real part
     * @param iP imaginary part
     * @param iSign sign of imaginary part
     */
    private void pString(String rP, String iP, boolean iSign)
    {
        if (!iSign)
        {
            square += rP + " - " + iP + "i";
            return;
        }

        square += rP + " + " + iP + "i";
    }
} // end class
