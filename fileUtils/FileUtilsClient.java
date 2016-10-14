package fileUtils;

/*
*****************************************************************************************************************
                                 Name:            File Utils Client
                                 Version:         1.0
                                 Chapter:         11, Client
                                 Author:          Casey J. Murphy
                                 Date Created:    11 Apr 16
                                 Last Modified:   18 Apr 16
 ----------------------------------------------------------------------------------------------------------------
  Assignment:

	Design a class that checks if the contents of two text files (named file1.txt and file2.txt) are identical
	and, if not, determines how many lines are different.  Lines are different if they differ in one or more
	characters.  Test your class with a client program, and submit your class, your client program, and your
	two test files.
* ***************************************************************************************************************
* ChangeLog:	0.1 - Rough draft
* 				1.0 - Working with correct output
* 				1.1 - Made printing differences conditional
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* 				(1) - File Not Found Exception
* 				(2) - File already closed
* 				(3) - Null pointer exception
* ***************************************************************************************************************
*/
import java.io.FileNotFoundException;

/**
 * Test client for FileUtils class
 * @author Casey J. Murphy
 * @version 1.1
 */
public class FileUtilsClient {

    public static void main(String[] args) throws FileNotFoundException
    {
        FileUtils a = new FileUtils("file1.txt");
        System.out.println(a.toString() + "\nFound");

        System.out.println();

        FileUtils b = new FileUtils("file2.txt");
        System.out.println(b.toString() + "\nFound");

        System.out.println();

        FileUtils c = new FileUtils("file3.txt");
        System.out.println(c.toString() + "\nFound");

        System.out.println();
        System.out.println("Testing:");
        System.out.println("--------");

        System.out.println("Files 1 & 2, same type and content: " + a.compare(b));
        System.out.println();
        System.out.println("Files 1 & 3, same type and content: " + a.compare(c));

        System.out.println();
        if (!a.compare(b))
        {
            a.printDiff(b);
        }
        if (!a.compare(c))
        {
            a.printDiff(c);
        }

        a.closeFile();
        b.closeFile();
        c.closeFile();
    }
}
