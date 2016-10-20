
/*
*****************************************************************************************************************
                                 Name:            Base Converter
                                 Version:         1.6
                                 Chapter:         5, Program 2
                                 Author:          Casey J. Murphy
                                 Date Created:    06 Feb 16
                                 Last Modified:   28 Feb 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

  Write a program that presents a menu asking the user to convert a binary number to a decimal number, or a
  decimal number to a binary number.  Prompt the user for their input number, and output the converted number.
  For a bonus, also offer to convert numbers to and from hexadecimal. (hint - you can use a dictionary data type)
* ***************************************************************************************************************
* Exit Codes:	(0) - Normal completion or exited from main menu
* 				    (1) - Invalid option in main menu
* 				    (2) - Input data, does not match specified format
* 				    (3) - Invalid input in run again dialogue
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
*			 				1.0 - Program working with correct output
*			 			  1.1 - Added getData method to reduce clutter in main, moved validation and data input
*			 			        into the conversion methods
*			 			  1.2 - Removed binary built-in conversion methods and replaced them step by step code
*			 			  1.3 - Removed hex built-in conversion methods and reworked them with same logic as the
*			 			        binary methods
*			 			  1.4 - Added run again loop in main
*			 			  1.5 - Added binary and hex formatting
*			 			  1.6 - Added documentation
 *			 			      Renamed some variables for clarification
 *			 			      Added error code to output on early termination
* ***************************************************************************************************************
*/

import java.util.Scanner;

public class BaseConvert {

    // global
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        String again; // loop flag

        // begin run again loop
        do {
            // menu
            System.out.println("Base Converter\n");
            System.out.println("1) Binary\t---> Decimal");
            System.out.println("2) Decimal\t---> Binary");
            System.out.println("3) Hex\t\t---> Decimal");
            System.out.println("4) Decimal\t---> Hex");
            System.out.println();
            System.out.println("5) Exit Program");
            System.out.println();
            System.out.print("Enter Selection: ");
            String menuOption = scan.next();

            switch (menuOption) {
                case "1" :
                    binToDec();
                    break;
                case "2" :
                    decToBin();
                    break;
                case "3" :
                    hexToDec();
                    break;
                case "4" :
                    decToHex();
                    break;
                case "5" :
                    System.exit(0);
                default  :
                    error(1);
            }

            // run again dialogue
            System.out.print("\nWould you like to run this program again (Y/N)? ");
            again = scan.next();
            validateAns(again);
            System.out.println("-------------------------------------------------\n");

        } while (again.equals("y"));
        // end run again loop

        scan.close();
        System.exit(0);
    } // end main
    //----------------------------------------------------------------------------------------------------------------------
// gets data to be converted from the user
// @return data (string): value from user input
    private static String getData() {

        System.out.print("\nEnter the value to be converted: ");
        return scan.next();
    }
    //----------------------------------------------------------------------------------------------------------------------
// validates binary data, exits if users input doesn't match binary values
// @param value: value to be converted
    private static void validateBin(String value) {

        if (value.matches("[01]+")) {
            return;
        }
        error(2);
    }
    //----------------------------------------------------------------------------------------------------------------------
// validates decimal data, exits if users input doesn't match decimal values
// @param value: value to be converted
    private static void validateDec(String value) {
        if (value.matches("[0-9]+")) {
            return;
        }
        error(2);
    }
    //----------------------------------------------------------------------------------------------------------------------
// validates hexadecimal input, exits if users input doesn't match hex values
// @param value: value to be converted
    private static void validateHex(String value) {

        if (value.matches("[0-9a-fA-F]+")) {
            return;
        }
        error(2);
    }
    //----------------------------------------------------------------------------------------------------------------------
// validates input from run again dialogue, exits if users input doesn't match one of: Y y N n
// @param value: answer to run again dialogue
    private static void validateAns(String ans) {
        ans = ans.toLowerCase();
        if (ans.matches("[yn]")) {
            return;
        }
        error(3);
    }
    //----------------------------------------------------------------------------------------------------------------------
// gets, validates, and converts data from binary to decimal, prints result
// calls getData and validateBin
    private static void binToDec() {

        String sInput = getData();
        validateBin(sInput);

        String digits = "01";

        int decVal = 0;
        for (int i = 0; i < sInput.length(); i++) {
            char c = sInput.charAt(i);        // finds each subsequent digit
            int d = digits.indexOf(c);        // value of digit based on digits string
            decVal = 2 * decVal + d;          // increases by powers of 2 with each iteration and adds remaining bit
        }

        //alternate method: Integer.parseInt(sInput, 2)
        System.out.println("Converted value: " + decVal);
    }
    //----------------------------------------------------------------------------------------------------------------------
// gets, validates, and converts data from decimal to binary, prints result
// calls getData and validateDec
    private static void decToBin() {

        String sInput = getData();
        validateDec(sInput);

        int input = Integer.parseInt(sInput);
        String binString = "";

        if (input == 0) {             // checks for 0 input and assigns to string if true
            binString = "0";            // necessary because >0 is the sentinel for while loop
        }

        int count = 0;
        while (input > 0) {
            int bit = input % 2;         // least significant digit used to find in digit string
            input = input / 2;           // find value to convert on next iteration

            // builds binary string with nibble formatting
            binString = bit + binString;
            count++;
            if (count > 0 && count % 4 == 0) {
                binString = " " + binString;
            }
        } // end while loop

        // alternate method: Integer.toHexString(sInput converted to int)
        System.out.println("Converted value: " + binString);
    }
    //----------------------------------------------------------------------------------------------------------------------
// gets, validates, and converts data from hex to decimal, prints result
// calls getData and validateHex
    private static void hexToDec() {

        String sInput = getData();
        validateHex(sInput);
        sInput = sInput.toUpperCase();

        String digits = "0123456789ABCDEF";

        int decVal = 0;
        for (int i = 0; i < sInput.length(); i++) {
            char c = sInput.charAt(i);        // finds each subsequent digit
            int d = digits.indexOf(c);        // value of digit based on digits string
            decVal = 16 * decVal + d;         // increases by powers of 16 with each iteration and adds remaining byte
        }

        // alternate method: Integer.parseInt(sInput, 16)
        System.out.println("Converted value: " + decVal);
    }
    //----------------------------------------------------------------------------------------------------------------------
// gets, validates, and converts data from decimal to hex, prints result
// calls getData and validateDec
    private static void decToHex() {

        String sInput = getData();
        validateDec(sInput);
        int input = Integer.parseInt(sInput);

        String hexString = "";
        String digits = "0123456789ABCDEF";

        if (input == 0) {                                 // checks for 0 input and assigns to string if true
            hexString = "0";                                // necessary because >0 is the sentinel for while loop
        }

        while (input > 0) {
            int digit = input % 16;                         // least significant digit used to find in digit string
            input = input / 16;                             // find value to convert on next iteration
            hexString = digits.charAt(digit) + hexString;   // builds converted hex string
        }

        // alternate method: Integer.toHexString(sInput converted to int)
        System.out.println("Converted value: 0x" + hexString);
    }
    //----------------------------------------------------------------------------------------------------------------------
// notifies user of error, exits program with appropriate exit code
// @param in, code: exit code
    private static void error(int code) {
        System.err.println("\nProgram Error (0" + code + "): Invalid input.  Terminating...\n");
        System.exit(code);
    }
} // end class BaseConvert