package scanner;

import java.util.*;
import java.io.*;
/**
 * The ScannerTester class uses files ScannerTest.txt and 
 * scannerTestAdvanced.txt in order to check the Scanner class and its methods.
 * 
 * @author Sanaa Bhorkar
 *
 * @version 08/31/2023
 */
public class ScannerTester 
{
    /**
     * The main function creates a new scanner and prints out all the tokens 
     * in order to check that they are correct.
     *
     * @param args arguments from the command line
     */
    public static void main(String[] args) throws IOException, ScanErrorException, 
                                                  FileNotFoundException
    {
        InputStream inStream = new FileInputStream(
                new File("//Users/sanaabhorkar/Desktop/cs/Java/Programming Projects/Compilers/"
                    +"CompilerBhorkar/parser/parserTest4.txt"));

        Scanner scanner = new Scanner(inStream);
        while (scanner.hasNext())
        {
            System.out.println(scanner.nextToken());
        }
    }
}
