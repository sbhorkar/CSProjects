package scanner;

import java.io.*;
/**
 * The Scanner lab is the first part of making a compiler. It has two 
 * constructors and the methods getNextChar(), eat(), hasNext(), isDigit(), 
 * isLetter(), isWhiteSpace(), scanNumber(), scanIdentifier(), scanOperator(),
 * and nextToken(), and more.The scanner is used to remove whitespace 
 * from the code and make tokens.
 * 
 * @author Sanaa Bhorkar
 * @version 08/30/2023
 */
public class Scanner
{
    // instance variables
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * Scanner constructor for construction of a scanner that 
     * uses an input stream for input.  
     * 
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * 
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * The getNextChar() method reads the next character in a line. If it's
     * the last character, it changes the boolean eof to true.
     */
    private void getNextChar()
    {
        try
        {
            currentChar = (char) in.read();
        } catch(IOException io)
        {
            IOException x = io; // done to avoid checkstyle issue of return statements
        } finally
        {
            if (currentChar == -1)
            {
                eof = true;
            }
        }
    }

    /**
     * The eat() method checks if the current char is equal to the expected
     * char and eats it if true. Otherwise, it throws a ScanErrorException.
     * 
     * @param expected the character the scanner is expecting
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar)
        {
            getNextChar();
        }
        else 
        {
            String message = "Illegal character - expected " + currentChar + 
                " and found " + expected;
            throw new ScanErrorException(message);
        }
    }

    /**
     * The isDigit() method checks if the character is a digit.
     * 
     * @param c is the character being checked
     * @return true if c is a digit, otherwise false
     */
    public static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }

    /**
     * The isLetter() method checks if the character is a letter.
     * 
     * @param c is the character being checked
     * @return true if c is a letter, otherwise false
     */
    public static boolean isLetter(char c)
    {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * The isWhiteSpace() method checks if the character is a white space.
     * 
     * @param c is the character being checked
     * @return true if c is a white space, otherwise false
     */
    public static boolean isWhiteSpace(char c)
    {
        return (c == ' ') || (c == '\t') || (c == '\r') || (c == '\n');
    }

    /**
     * The isOperator() method checks if the character is an operator.
     * 
     * @param c is the character being checked
     * @return true if c is an operator, otherwise false
     */
    public static boolean isOperand(char c)
    {
        return (c == '=' || c == '+' || c == '-' || c == '*' || c == '%' || 
            c == '<' || c == '>' || c == ':');
    }

    /**
     * The isSeparator() method checks if the character is a terminator or
     * a separator.
     * 
     * @param c is the character being checked
     * @return true if c is an terminator, otherwise false
     */
    public static boolean isSeparator(char c)
    {
        return (c == '(' || c == ')' || c == '{' || c == '}' || c == ';' 
            || c == '.' || c == ',');
    }

    /**
     * The scanNumber() method scans the input and returns the entire lexeme 
     * after terminated.
     * 
     * @return the number in the form of a string
     * @throws ScanErrorException if there are non-digit characters in the number
     * @precondition the current character is a digit
     * @postcondition a new number token has been formed
     */
    private String scanNumber() throws ScanErrorException
    {
        String s = "";
        while (hasNext() && isDigit(currentChar))
        {
            s += currentChar;
            eat(currentChar);
        }
        if (!isWhiteSpace(currentChar) && !isSeparator(currentChar) &&
            !isOperand(currentChar))
        {
            throw new ScanErrorException();
        }
        return s;
    }

    /**
     * The scanIdentifier() method scans the input and returns the entire 
     * lexeme after terminated.
     * 
     * @return the identifier in the form of a string
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String s = "";
        while (hasNext() && (isDigit(currentChar) || isLetter(currentChar)))
        {
            s += currentChar;
            eat(currentChar);
        }
        if (!isWhiteSpace(currentChar) && !isSeparator(currentChar) &&
            !isOperand(currentChar))
        {
            throw new ScanErrorException();
        }
        return s;
    }

    /**
     * The scanOperand method() scans the input and returns the operator. 
     * 
     * @return the operand in the form of a string
     */
    private String scanOperand() throws ScanErrorException
    {
        String s = "" + currentChar;
        if (currentChar == '>')
        {
            eat(currentChar);
            if (currentChar == '=')
            {
                s += currentChar;
                eat(currentChar);
            }
            return s;
        }
        if (currentChar == '<')
        {
            eat(currentChar);
            if (currentChar == '=')
            {
                s += currentChar;
                eat(currentChar);
            }
            else if (currentChar == '>')
            {
                s += currentChar;
                eat(currentChar);
            }
            return s;
        }
        if (currentChar == ':')
        {
            eat(currentChar);
            if (currentChar == '=')
            {
                s += currentChar;
                eat(currentChar);
            }
            return s;
        }
        eat(currentChar);
        return s;
    }

    /**
     * The scanSeparator() method scans the input and returns the separator.
     * 
     * @return the separator in the form of a string
     */
    private String scanSeparator() throws ScanErrorException
    {
        String s = "" + currentChar;
        eat(currentChar);
        return s;
    }

    /**
     * The hasNext() method checks if the file has more characters to read.
     * 
     * @return true if yes, otherwise false
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * The nextToken() method returns the next token or the value "EOF" if 
     * it is the end of the file.
     * 
     * @return the next token
     */
    public String nextToken() throws IOException, ScanErrorException
    {
        while (hasNext()) 
        {
            while (hasNext() && isWhiteSpace(currentChar))
            {
                eat(currentChar);   
            }
            if (isLetter(currentChar))
            {
                return scanIdentifier();   
            } 
            else if (isDigit(currentChar))
            {
                return scanNumber();   
            } 
            else if (isOperand(currentChar))
            {
                return scanOperand();
            }
            else if (currentChar == '/')
            {
                eat(currentChar);
                if (currentChar == '/') 
                {
                    while (hasNext() && currentChar != '\n')
                    {
                        eat(currentChar);
                    }
                }
                else
                {
                    return "/";
                }
            }
            else if (currentChar == '.') 
            {
                eof = true;
            }
            else if (isSeparator(currentChar))
            {
                return scanSeparator();
            } 
            else
            {
                throw new ScanErrorException(currentChar + "");
            }
        }
        return "EOF";
    }    
}
