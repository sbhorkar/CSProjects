package parser;
import java.util.*;
import java.io.*;
import scanner.Scanner;
import scanner.ScanErrorException;
import environment.*;
import ast.*;

/**
 * The ParserTester class first uses a Scanner to tokenize the user input, 
 * then uses the Parser object in order to evaluate its numerical value.
 * 
 * @author Sanaa Bhorkar
 * @version 9/30/2023
 */
public class ParserTester 
{
    /**
     * Creates a new ParserTester object
     * 
     * @param args arguments from the command line
     */
    public static void main(String [ ] args) throws ScanErrorException, FileNotFoundException
    {
        InputStream inStream = new FileInputStream(
                new File("//Users/sanaabhorkar/Desktop/cs/Java/Programming Projects/Compilers/"
                    +"CompilerBhorkar/parser/parserTest6.txt"));
        Scanner scanner = new Scanner(inStream);
        Parser parser = new Parser(scanner);
        Environment env = new Environment();
        while (scanner.hasNext())
        {
            Statement stmt = parser.parseStatement();
            stmt.exec(env);
        }
    }
}
