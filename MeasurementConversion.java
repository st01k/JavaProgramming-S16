
/*
*****************************************************************************************************************
                                 Name:            ConvertMe - A Measurement Conversion Utility
                                 Version:         1.4
                                 Chapter:         2.1
                                 Author:          Casey J. Murphy
                                 Date Created:    17 Jan 16
                                 Last Modified:   28 Feb 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

  Write an application that presents a user with a menu, and offers to convert various measurements
  between metric and imperial units.  The menu should prompt the user for what type of conversion they
  want (Temperature, Volume, Distance, or Weight).  Depending on the selection, a second menu will offer to
  convert from metric to imperial (i.e. fahrenheit to celsius) or from imperial to metric (celsius to
  fahrenheit).  Once the conversion is selected, the program should then prompt the user for the source
  measurement (i.e. temperature in fahrenheit to convert, etc.), do the conversion, and display the results.

  Your application should convert each of the following:
					Fahrenheit to celsius or vice versa, 			Liters to Gallons or vice versa,
					Miles to Kilometers or vice versa, 				Pounds to Kilograms or vice versa
* ***************************************************************************************************************
* Errors: 		(0) - Normal completion or exited from main menu
* 						(1) - Invalid key press in main menu
* 						(2) - Invalid key press in convertOption submenu system
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 						1.0 - Program working with correct output
* 						1.1 - Added decimal formatting in output
* 						1.2 - Added internal documentation: errors, changelog
* 						1.3 - Added 'Run Again' loop
* 			  		1.4 - Made corrections from Charles' recommendations:
	* 					  		Renamed x and y to source and target
	* 				 		 	  Reformatted switch statement to look better with statements under cases
* ***************************************************************************************************************
*/

import java.util.Scanner;
import java.text.DecimalFormat;

public class MeasurementConversion
{
    // constants
    final static double GAL_PER_LTR = 0.264172;
    final static double LTR_PER_GAL = 3.78541;
    final static double MI_PER_KM   = 0.621371;
    final static double KM_PER_MI   = 1.60934;
    final static double LB_PER_KG   = 2.20462;
    final static double KG_PER_LB   = 0.453592;

    // global
    private static final Scanner scan = new Scanner (System.in);
    private static final DecimalFormat df = new DecimalFormat();
    private static double source = 0;	// used as source conversion variable in converting functions
    private static double target = 0;	// used as target conversion variable in converting functions

    public static void main (String[] args)
    {
        // variables
        boolean again;				//for run again feature
        byte 	cOption;				//conversion option, see convertOption function
        char	menuOption;			//taken as the 0 index of the following variable, selection
        String 	selection;		//user response to main menu

        df.setMaximumFractionDigits(3);

        do
        {
            again = true;	//loop flag

            // main menu
            System.out.println("\n");
            System.out.println("Unit Conversion Software");
            System.out.println("------------------------");
            System.out.println("1.           Temperature");
            System.out.println("2.                Volume");
            System.out.println("3.              Distance");
            System.out.println("4.                Weight");
            System.out.println("5.                  Exit");
            System.out.print("\nPlease make a selection: ");
            selection = scan.next();
            menuOption = selection.charAt(0);

            switch (menuOption)
            {
                case '1':
                    cOption = convertOption("Fahrenheit", "Celsius");
                    if (cOption == 1)
                        convertFtoC();
                    else
                        convertCtoF();
                    break;
                case '2':
                    cOption = convertOption("Gallons", "Liters");
                    if (cOption == 1)
                        convertGtoL();
                    else
                        convertLtoG();
                    break;
                case '3':
                    cOption = convertOption("Miles", "Kilometers");
                    if (cOption == 1)
                        convertMtoK();
                    else
                        convertKtoM();
                    break;
                case '4':
                    cOption = convertOption("Pounds", "Kilograms");
                    if (cOption == 1)
                        convertPtoK();
                    else
                        convertKtoP();
                    break;
                case '5':
                    System.out.println("\n\nExiting...");
                    scan.close();
                    System.exit(0);
                default:
                    System.out.println("\nInvalid Option... Terminating.");
                    System.out.println("Error Code: 01");
                    System.exit(1);
            } // end switch

            System.out.print("\n\nRun again (Y/N)? ");
            String aInput = scan.next();
            aInput = aInput.toLowerCase();
            if (aInput.charAt(0) == 'n')
                again = false;

        }	while (again);
        //end loop

        System.out.print("\n\nProgram Complete... Terminating.");
        scan.close();
        System.exit(0);
    } //end main

    //----------------------------------------------------------------------------------------------------------------------
//  dynamically populated user selection for conversion option:
// english -> metric or metric -> english: selection is returned
    public static byte convertOption(String eUnit, String mUnit)
    {
        byte option;
        System.out.println("\n");
        System.out.println("1. " + eUnit + "   --->   " + mUnit);
        System.out.println("2. " + mUnit + "   --->   " + eUnit);
        System.out.print("\nEnter your selection: ");
        option = scan.nextByte();
        if (option < 1 || option > 2)
        {
            System.out.println("\nInvalid Option... Terminating.");
            System.out.println("Error Code: 02");
            System.exit(2);
        }
        return option;
    }
    //----------------------------------------------------------------------------------------------------------------------
// temperature conversion functions: fahrenheit -> celsius, celsius -> fahrenheit:
// calculates conversions and prints result
    public static void convertFtoC()
    {
        System.out.print("\nEnter temperature in °F: ");
        source = scan.nextDouble();
        target = (source - 32) * (5.0 / 9.0);
        System.out.println(df.format(source) + " °F = " + df.format(target) + " °C");
    }
    //--------------------------------------------------------------
    public static void convertCtoF()
    {
        System.out.print("\nEnter temperature in °C: ");
        source = scan.nextDouble();
        target = (source * (9.0 / 5.0)) + 32;
        System.out.println(df.format(source) + " °C = " + df.format(target) + " °F");
    }
    //----------------------------------------------------------------------------------------------------------------------
// volume conversion functions: gallons -> liters, liters -> gallons:
// calculates conversions and prints result
    public static void convertGtoL()
    {
        System.out.print("\nEnter number of gallons: ");
        source = scan.nextDouble();
        target = source * LTR_PER_GAL;
        System.out.println(df.format(source) + " Gallons = " + df.format(target) + " Liters");
    }
    //--------------------------------------------------------------
    public static void convertLtoG()
    {
        System.out.print("\nEnter number of liters: ");
        source = scan.nextDouble();
        target = source * GAL_PER_LTR;
        System.out.println(df.format(source) + " Liters = " + df.format(target) + " Gallons");
    }
    //----------------------------------------------------------------------------------------------------------------------
// distance conversion functions: miles -> kilometers, kilometers -> miles:
// calculates conversions and prints result
    public static void convertMtoK()
    {
        System.out.print("\nEnter number of miles: ");
        source = scan.nextDouble();
        target = source * KM_PER_MI;
        System.out.println(df.format(source) + " Miles = " + df.format(target) + " Kilometers");
    }
    //--------------------------------------------------------------
    public static void convertKtoM()
    {
        System.out.print("\nEnter number of kilometers: ");
        source = scan.nextDouble();
        target = source * MI_PER_KM;
        System.out.println(df.format(source) + " Kilometers = " + df.format(target)+ " Miles");
    }
    //----------------------------------------------------------------------------------------------------------------------
// weight conversion functions: pounds -> kilograms, kilograms -> pounds:
// calculates conversions and prints result
    public static void convertPtoK()
    {
        System.out.print("\nEnter number of pounds: ");
        source = scan.nextDouble();
        target = source * KG_PER_LB;
        System.out.println(df.format(source) + " Pounds = " + df.format(target) + " Kilograms");
    }
    //--------------------------------------------------------------
    public static void convertKtoP()
    {
        System.out.print("\nEnter number of kilograms: ");
        source = scan.nextDouble();
        target = source * LB_PER_KG;
        System.out.println(df.format(source) + " Kilograms = " + df.format(target) + " Pounds");
    }
//----------------------------------------------------------------------------------------------------------------------
} // end class MeasurementConversion
