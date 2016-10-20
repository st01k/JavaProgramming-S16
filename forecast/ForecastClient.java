package forecast;

/*
*****************************************************************************************************************
                                 Name:            Weather Forecast Client
                                 Version:         1.6
                                 Chapter:         7, Program 1
                                 Author:          Casey J. Murphy
                                 Date Created:    23 Feb 16
                                 Last Modified:   9 Mar 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

   Write a client class (a driver) to test all the methods in your weather class.
   Submit both your class and your driver.
* ***************************************************************************************************************
* Exit Codes:	(0) - Normal completion or exited from main menu
* 				(1) - Invalid input from menu
* 				(2) - Invalid input from check consistency dialog
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
*             	1.0 - Working with correct output
*             	1.1 - Removed loadRandom, createNew, and loadTestData from client
*             		  Added them to Forecast class
*             	1.2 - Removed forecastGroup array creation from UI and added it to Forecast class
*             	1.3 - Created separator function
*             	1.4 - Changed conversion function to receive only the new value and handle output to screen
*             		  Added more values for testable data in class, changed limits to reflect additional input
*             	1.5 - Put menu in its own function
*             	1.6 - Fixed incorrect output with selection dialog
*             		  Updated documentation
*             		  Fixed indentation
* ***************************************************************************************************************
*/
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Menu driven client to work with the Forecast class.
 * @author Casey Murphy
 * @version 1.6
 *
 */
public class ForecastClient
{
    //global
    private static final Scanner scan = new Scanner(System.in);
    private static final Scanner keyboard = new Scanner(System.in);
    private static final DecimalFormat df = new DecimalFormat();

    public static void main(String[] args)
    {
        menu();
        System.out.println("\t\tExiting program...");
        keyboard.close();
        scan.close();
        System.exit(0);

    }// end main
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Implements a looped menu with display of current elements in forecast group.
     */
    private static void menu()
    {
        int max = Forecast.getMaxRecords();
        do
        {
            System.out.println("Weather Forecast Client Software");
            System.out.println("Forecasts loaded: " + Forecast.getCount() + " of " + max);
            System.out.println();
            System.out.println("1) Create a new forecast");
            System.out.println("2) Load easy test data");
            System.out.println("3) Load random data");
            System.out.println("4) Alter a forecast");
            System.out.println("5) Compare two forecasts");
            System.out.println("6) Convert forecast temperature");
            System.out.println("7) Check forecast consistency");
            System.out.println("8) List all forecasts");
            System.out.println("0) Exit");
            System.out.println();
            System.out.print("Enter Selection: ");
            String menuInput = scan.next();
            separator();

            switch (menuInput)
            {
                case "1" :
                    insertNew();
                    pause();
                    break;
                case "2" :
                    testData();
                    pause();
                    break;
                case "3" :
                    insertRandom();
                    pause();
                    break;
                case "4" :
                    alterForecast();
                    pause();
                    break;
                case "5" :
                    compareForecasts();
                    pause();
                    break;
                case "6" :
                    convertForecast();
                    pause();
                    break;
                case "7" :
                    checkConsistency();
                    pause();
                    break;
                case "8":
                    listForecasts();
                    pause();
                    break;
                case "0" :
                    return;
                default:
                    error(1);
            }
        } while (true);
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Allows user to create a new forecast within forecast group.  Confirms upon completion.
     */
    private static void insertNew()
    {
        // checks if there is room for 1 record to be created
        if (limitReached(1))
        {
            return;
        }

        System.out.print("\nEnter temperature in degrees Fahrenheit: ");
        double temp = scan.nextDouble();
        System.out.println("\nCondition options: Sunny, Cloudy, Rain, Snow");
        System.out.print("Enter sky condition: ");
        String sky = scan.next();
        System.out.println();
        Forecast.createNew(temp, sky);
        separator();
        System.out.println("      Forecast successfully created.");
        separator();
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Allows user to fill forecast group with desired amount of random forecasts.  Confirms upon completion.
     */
    private static void insertRandom()
    {
        System.out.print("\nEnter desired number of forecasts: ");
        int input = scan.nextInt();

        // checks if there is room for 'input' number of records to be created
        if (limitReached(input))
        {
            return;
        }

        Forecast.loadRandom(input);
        separator();
        System.out.println("     Random data successfully loaded.");
        separator();
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Allows user to load test data into forecast group.  Confirms upon completion.
     */
    private static void testData() {

        // checks if there is room for 42 records to be created
        if (limitReached(42))
        {
            return;
        }
        Forecast.loadTestData();
        System.out.println("      Test data successfully loaded.");
        separator();
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Allows user to list forecasts currently loaded in forecast group.
     */
    private static void listForecasts() {
        for (int i = 0; i < Forecast.getCount(); i++) {
            Forecast item = Forecast.getForecastGroup()[i];
            System.out.print(item.getId());
            System.out.println(") " + item.toString());
        }
        separator();
        System.out.println("\t--- Listing Complete ---");
        separator();
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Allows user to alter an individual forecast within the group.
     */
    private static void alterForecast()
    {
        Forecast[] group = Forecast.getForecastGroup();
        if (initCheck())
        {
            int option = getSelection(1);
            System.out.println("Editing: \n" + group[option].toString());
            separator();
            System.out.print("Enter new temperature: ");
            group[option].setTempF(scan.nextDouble());
            System.out.print("Enter new sky condition: ");
            group[option].setSkyCondition(scan.next());
            separator();
            System.out.println("Forecast " + group[option].getId() + " has been changed to:");
            System.out.println(group[option].toString());
            separator();
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Allows user to see the converted fahrenheit temperature of a forecast
     * in degrees celsius.  Uses decimal formatting.
     */
    private static void convertForecast()
    {
        Forecast[] group = Forecast.getForecastGroup();
        if (initCheck())
        {
            df.setMaximumFractionDigits(3);
            int option = getSelection(1);
            double f = group[option].getTempF();
            separator();
            System.out.print("\t\t" + f + "°F");
            System.out.println(" = " + df.format(group[option].convertToC(f)) + "°C");
            separator();
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Allows user to compare two forecasts' data.
     */
    private static void compareForecasts()
    {
        Forecast[] group = Forecast.getForecastGroup();
        if (initCheck())
        {
            System.out.print("\nEnter first forecast to compare: ");
            int first = getSelection(0);
            System.out.print("Enter second forecast to compare: ");
            int second = getSelection(0);
            boolean flag = group[first].equals(group[second]);
            separator();
            if (flag){
                System.out.println("\tThe two forecasts are the same.");
            }
            else {
                System.out.println("\tThe two forecasts are not the same.");
            }
            separator();
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Allows user to check for consistency among one or all of the forecasts
     * in the forecast group.
     */
    private static void checkConsistency()
    {
        Forecast[] group = Forecast.getForecastGroup();
        if (initCheck())
        {
            System.out.print("\nCheck for consistency in (A)ll or (O)ne? ");
            String option = scan.next();
            switch (option)
            {
                case "A":
                case "a":
                    separator();
                    for (int i = 0; i < Forecast.getCount(); i++)
                    {
                        System.out.print(group[i].getId() + ": \t"
                                + group[i].getTempF() + " \t"
                                + group[i].getSkyCondition() + "\t = ");
                        if (group[i].forecastConsistent())
                        {
                            System.out.println("Consistent");
                        }
                        else
                        {
                            System.out.println("Not Consistent");
                        }
                    }
                    separator();
                    break;

                case "O":
                case "o":
                    int item = getSelection(1);
                    separator();
                    System.out.print("\t" + group[item].getTempF() + " \t"
                            + group[item].getSkyCondition() + " = \t");
                    if (group[item].forecastConsistent())
                    {
                        System.out.println("Consistent");
                    }
                    else
                    {
                        System.out.println("Not Consistent");
                    }
                    separator();
                    break;

                default:
                    error(2);
            }
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Pauses program to allow user to read program output and provides a 'Press Enter to Continue' dialog.
     */
    private static void pause()
    {
        System.out.println("\tPress Enter to Continue...");
        keyboard.nextLine();
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Checks whether the forecast group array has data in it before performing
     * any functionality which requires that data.
     * @return true if there is at least one forecast in the group, false if not.
     */
    private static boolean initCheck()
    {
        if (Forecast.getCount() > 0) {
            return true;
        }
        System.out.println("\nAt least one forecast record is ");
        System.out.println("required for this functionality.\n");
        return false;
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Checks if the array is full.
     * @param input number of forecasts to be added.
     * @return true if array is full, false if not
     */
    private static boolean limitReached(int input)
    {
        int current = Forecast.getCount();
        if (input + current > Forecast.getMaxRecords())
        {
            System.out.println("\n\tRequest exceeds capacity.\n");
            separator();
            return true;
        }
        return false;
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * User query for the ID of the forecast they wish to work with.
     * @param cnt used internally to distinguish between when one ID number
     * is needed versus more than one for any given function
     * @return one than less the inputed number to match array designation
     */
    private static int getSelection(int cnt)
    {
        if (cnt == 1)
        {
            System.out.print("\nEnter Forecast ID: ");
            return scan.nextInt() - 1;
        }
        else
        {
            return scan.nextInt() - 1;
        }
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Prints a separator line to console.
     */
    private static void separator()
    {
        System.out.println("---------------------------------------------");
    }
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Early program termination with error message and code.
     * @param code error number
     */
    private static void error(int code)
    {
        System.err.println("Program Error 0x000" + code + ": Terminating...");   //just to make it look cool
        System.exit(code);
    }
}// end class ForecastClient
