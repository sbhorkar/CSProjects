package parser;
import java.util.*;
import java.io.*;
import scanner.Scanner;
import scanner.ScanErrorException;
import environment.*;
import ast.*;
import emitter.*;

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
        String filepath = "//Users/sanaabhorkar/Desktop/cs/Java/Programming Projects/Compilers/"
                    +"CompilerBhorkar/parser/";
        InputStream inStream = new FileInputStream(
                new File(filepath + "myParserTest1.txt"));
        Scanner scanner = new Scanner(inStream);
        Parser parser = new Parser(scanner);
        Environment env = new Environment(null);
        Program prog = parser.parseProgram();
        
        prog.compile("output.asm");
        // prog.exec(env);
    }
}
