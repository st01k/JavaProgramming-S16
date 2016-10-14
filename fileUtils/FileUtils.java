package fileUtils;

/*
*****************************************************************************************************************
                                 Name:            File Utils Class
                                 Version:         1.0
                                 Chapter:         11, Class
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
* 				1.1 - Added Documentation
* 					  Removed unnecessary imports
* 					  Commented out lines intended for not yet implemented functionality
* ***************************************************************************************************************
* Error Code:	(0) - Normal termination
* 				(1) - File Not Found Exception
* 				(2) - File already closed
* 				(3) - Null pointer exception
* ***************************************************************************************************************
*/

import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;

/**
 * File utilities class<br>
 * A class to manipulate and work with files.<br>
 * Currently only compares two files and prints differences.
 * @author Casey J. Murphy
 * @version 1.1
 */
public class FileUtils {

    // instance variables
    private String filename;
    private File file;
    private Scanner openFile;
    private ArrayList<char[]> lines = new ArrayList<char[]>();
    private String path;

// Constructor ---------------------------------------------------------------------------------------------------------
    /**
     * Creates file object based on the name of the file as a parameter passed in.  Opens file for use.
     * @param name name of file with extension
     */
    public FileUtils (String name)
    {
        setFilename(name);
        file = new File(filename);
        path = file.getAbsolutePath();
        openFile(filename);
    }

// Mutators and Accessors ----------------------------------------------------------------------------------------------
    /**
     * Returns the file's path
     * @return path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Returns the file's name
     * @return filename
     */
    public String getFilename()
    {
        return filename;
    }

    /**
     * Returns file object
     * @return file
     */
    public File getFile()
    {
        return file;
    }

    /**
     * Sets the filename
     * @param newName
     */
    private void setFilename(String newName)
    {
        filename = newName;
    }

// Quasi-Overrides -----------------------------------------------------------------------------------------------------
    /**
     * Returns a string with the file and path.
     */
    public String toString()
    {
        return "File: " + filename +
                "\nPath: " + path;
    }

    /**
     * Compares the current file with the FileUtils object passed in
     * @param target file to compare current file to
     * @return true if files are the same in content, false otherwise
     */
    public boolean compare(FileUtils target)
    {
        File tarFile = target.getFile();

        // check if both exist
        if (file.exists() != tarFile.exists()) return false;
        // check file size
        if (file.length() != tarFile.length()) return false;
        // check file extension
        String sFE = filename.substring(filename.indexOf("."), filename.length());
        String tFE = filename.substring(target.filename.indexOf("."), target.filename.length());
        if (!sFE.matches(tFE)) return false;

        scanFile();
        target.scanFile();

        // check line count
        if (lines.size() != target.lines.size()) return false;
        // check character count
        if (countChars(lines) != countChars(target.lines)) return false;
        // check contents
        if (!checkChars(target)) return false;

        return true;
    }

// Printing ------------------------------------------------------------------------------------------------------------
    /**
     * Prints the contents of the file.
     */
    public void printFile()
    {
        for (char[] line : lines)
        {
            for (int i = 0; i < line.length; i++)
            {
                System.out.print(line[i]);
            }
            System.out.println();
        }
    }

    /**
     * Prints the differences between the current file and the target file.<br>
     * Currently only properly accomplishes this if the two files have the same number of lines.
     * @param target target file to compare current file to
     */
    public void printDiff(FileUtils target)
    {
        System.out.println("---------------------------------------");
        System.out.println("Difference in: " + filename + " and " + target.filename);
        System.out.println("---------------------------------------");

        scanFile();
        target.scanFile();

        // outer loop condition
        int oLoopMax =  (lines.size() <= target.lines.size()) ? target.lines.size() : lines.size();

        // for differences if files have different numbers of lines (not yet implemented)
        //boolean sMoreLines = sourceLarger(lines.size(), target.lines.size());

        for (int i = 0; i < oLoopMax; i++)
        {
            char [] s = lines.get(i);			// grabs current file line
            char [] t = target.lines.get(i);	// grabs target file line

            boolean sMoreChars = sourceLarger(s.length, t.length);	// determines which char array will drive loop
            int iLoopMax = (sMoreChars) ? s.length : t.length;  	// array size when comparing two lines
            int iLoopMin = (sMoreChars) ? t.length : s.length;		// inner loop condition when comparing chars
            int iLoopDiff = iLoopMax - iLoopMin;

            // adds caret to array where the file line and target file lines don't match
            char [] caret = new char[iLoopMax];
            for (int j = 0; j < iLoopMin; j++)
            {
                caret[j] = (lines.get(i)[j] != target.lines.get(i)[j]) ? '^' : ' ';
            }

            // if the lines have differing amounts of characters, adds carets to remaining space
            if (iLoopDiff > 0)
            {
                for (int k = 0; k < Math.abs(iLoopDiff); k++)
                {
                    caret[iLoopMin + k] = '^';
                }
            }

            // convert char array to string
            String sCaret = new String(caret);

            // prints lines with caret notation of differences if lines are different
            if (sCaret.indexOf("^") != -1)
            {
                System.out.println("Line " + (i + 1) + ": ");
                System.out.println(lines.get(i));
                System.out.println(target.lines.get(i));
                System.out.println(caret);
            }
        }
    }

// Private Methods -----------------------------------------------------------------------------------------------------
    /**
     * Comparison method to determine which parameter is larger in size.
     * Can be used for anything that reduces to an integer.
     * @param source source
     * @param target target
     * @return true if source is larger, false otherwise
     */
    private boolean sourceLarger(int source, int target)
    {
        if (source > target) return true;
        return false;
    }

    /**
     * Counts characters in a character array based ArrayList.
     * @param list ArrayList character array to count
     * @return number of characters
     */
    private int countChars(ArrayList<char[]> list)
    {
        int count = 0;
        for (char[] line : list) count += line.length;
        return count;
    }

    /**
     * Checks if the characters in the target FileUtils object are the same as those in the
     * current FileUtils object.
     * @param target object to compare current to
     * @return true if they match, false otherwise
     */
    private boolean checkChars(FileUtils target)
    {
        for (int i = 0; i < lines.size(); i++)
        {
            if (lines.get(i).length != target.lines.get(i).length) return false;
            for (int j = 0; j < lines.get(i).length; j++)
            {
                if (lines.get(i)[j] != target.lines.get(i)[j]) return false;
            }
        }

        return true;
    }

    /**
     * Reads a line within a file.
     * @return the string read
     */
    private String readLine()
    {
        return openFile.nextLine();
    }

// File Functionality --------------------------------------------------------------------------------------------------
    /**
     * Opens a file.  Catches file not found and illegal state exceptions.
     * @param name name of file to open
     */
    private void openFile(String name)
    {
        try
        {
            openFile = new Scanner(new File(name));
        }
        catch (FileNotFoundException fnfe)
        {
            System.err.println("FILE: " + file.getAbsolutePath() + " not found.");
            System.out.println("Exiting...");
            System.exit(1);
        }
        catch (IllegalStateException ise)
        {
            System.err.println("File has already been closed.");
            System.out.println("Exiting...");
            System.exit(2);
        }
    }

    /**
     * Scans a file and loads its contents into an ArrayList.<br>
     * Each line in the file is a character array as an element of the ArrayList.
     */
    public void scanFile()
    {
        try
        {
            while (openFile.hasNext())
            {
                char[] temp = readLine().toCharArray();
                lines.add(temp);
            }
        }
        catch(NullPointerException npe)
        {
            System.err.println("Null pointer to " + filename);
            System.out.println("Exiting...");
            System.exit(3);
        }
        catch(NoSuchElementException nsee)
        {
            System.out.println("End of file.");
        }
    }

    /**
     * Closes file.
     */
    public void closeFile()
    {
        openFile.close();
    }
}
