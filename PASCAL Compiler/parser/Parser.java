package parser;
import scanner.Scanner;
import scanner.ScanErrorException;
import ast.*;
import java.io.*;
import java.util.*;

/**
 * The Parser class represents the second phase of a compiler. It uses the tokens
 * created by the Scanner and parses through them. It also uses a grammar to
 * do this.
 *
 * @author Sanaa Bhorkar
 * @version 09/25/2023
 */
public class Parser
{
    // instance variables
    private Scanner scanner;
    private String currToken;

    /**
     * The Parser constructor takes in a Scanner and asks it for the next 
     * token. This token is saved as the current token.
     * 
     * @param s the Scanner used
     */
    public Parser(Scanner s)
    {
        scanner = s;
        try
        {
            currToken = scanner.nextToken();
        } catch (IOException io)
        {
            io = io; // to avoid Checkstyle error
        } catch (ScanErrorException exception)
        {
            exception = exception; // to avoid Checkstyle error
        }
    }

    /**
     * The eat() method takes in a string, representing the expected token,
     * and checks to see if it is the current token. If it does, the parser
     * asks the scanner for the next token. Otherwise, it throws an illegal
     * argument exception.
     * 
     * @param expectedToken is what the parser is meant to eat
     */
    private void eat(String expectedToken) throws IllegalArgumentException
    {
        if (expectedToken.equals(currToken))
        {
            try
            {
                currToken = scanner.nextToken();
            } catch (IOException io)
            {
                io = io; // to avoid Checkstyle error
            } catch (ScanErrorException exception)
            {
                exception = exception; // to avoid Checkstyle error
            }
        }
        else 
        {
            throw new IllegalArgumentException("Expected: " + currToken + 
                ", Found: " + expectedToken);
        }
    }

    /**
     * The parseProgram() method is the starting line in the grammar. It
     * parses through PROCEDURE and other statements. It calls upon
     * parseStatement() as it is the next line in the grammar.
     * 
     * @precondition  current token begins a program
     * @postcondition current token is the first one after the program
     * @return a Program object for the program
     */
    public Program parseProgram()
    {
        List<String> vars = new ArrayList<String>();
        List<ProcedureDeclaration> procedures = new ArrayList<ProcedureDeclaration>();
        while (currToken.equals("VAR"))
        {
            eat("VAR");
            while (!currToken.equals(";"))
            {
                vars.add(currToken);
                eat(currToken);
                if (currToken.equals(","))
                {
                    eat(",");
                }
            }
            eat(";");
        }
        while (currToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String name = currToken;
            eat(name);
            List<String> params = new ArrayList<String>();
            eat("(");
            while (!currToken.equals(")"))
            {
                params.add(currToken);
                eat(currToken);
                if (!currToken.equals(")"))
                {
                    eat(",");
                }
            }
            eat(")");
            eat(";");
            List<String> localVar = new ArrayList<String>();
            while (currToken.compareTo("VAR") == 0)
            {
                eat("VAR");
                while (!currToken.equals(";"))
                {
                    localVar.add(currToken);
                    eat(currToken);
                    if (!currToken.equals(";"))
                    {
                        eat(",");
                    }
                }
                eat(";");
            }            
            Statement stmt = parseStatement();
            ProcedureDeclaration procDec = new ProcedureDeclaration(name, stmt, params);
            procDec.setLocalVars(localVar);
            procedures.add(procDec);
        }
        Statement main = parseStatement();
        return new Program(vars, procedures, main);
    }

    /**
     * The parseStatement() method is a line in the grammar. It
     * parses through BEGIN/END statements, WRITELN statements, and assigns
     * variables. It calls upon parseExpression() as it is the next
     * line in the grammar.
     * 
     * @precondition  current token begins a statement
     * @postcondition current token is the first one after the statement
     * @return a statement object for the statement
     */
    public Statement parseStatement()
    {
        if (currToken.equals("BEGIN"))
        {
            eat("BEGIN");
            List<Statement> statements = new ArrayList<Statement>();
            while (!currToken.equals("END"))
            {
                statements.add(parseStatement());
            }
            Block block = new Block(statements);
            eat("END");
            eat(";");
            return block;
        }
        else if (currToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if (currToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement stmt = parseStatement();
            return new If(cond, stmt);
        }
        else if (currToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement stmt = parseStatement();
            return new While(cond, stmt);
        }
        else
        {
            String id = currToken;
            eat(id);
            eat(":=");
            Expression value = parseExpression();
            eat(";");
            return new Assignment(id, value);
        }
    }

    /**
     * The parseExpression() method is a line in the grammar. It
     * parses through any sums or differences. By the end of the method, 
     * the number that the expression is equal to is returned. It calls upon 
     * parseTerm() as it is the next line in the grammar.
     * 
     * @precondition  current token is an expression
     * @postcondition current token is the first one after the expression
     * @return an expression object for the expression
     */
    private Expression parseExpression()
    {
        Expression num = parseTerm();
        while (currToken.equals("+") || currToken.equals("-"))
        {
            if (currToken.equals("+"))
            {
                eat("+");
                Expression term = parseTerm();
                num = new BinOp("+", num, term);
            }
            else if (currToken.equals("-"))
            {
                eat("-");
                Expression term = parseTerm();
                num = new BinOp("-", num, term);
            }
        }
        return num;
    }

    /**
     * The parseTerm() method is a line in the grammar. It
     * parses through any products, quotients, or mods. By the end of the 
     * method, the number that the expression is equal to is returned. It 
     * calls upon parseFactor() as it is the next line in the grammar.
     * 
     * @precondition  current token is a term
     * @postcondition current token is the first one after the term
     * @return an expression object that represents the term
     */
    private Expression parseTerm()
    {
        Expression num = parseFactor();
        while (currToken.equals("*") || currToken.equals("/") || currToken.equals("mod"))
        {
            if (currToken.equals("*"))
            {
                eat("*");
                Expression factor = parseFactor();
                num = new BinOp("*", num, factor);
            }
            else if (currToken.equals("/"))
            {
                eat("/");
                Expression factor = parseFactor();
                num = new BinOp("/", num, factor);
            }
            else if (currToken.equals("mod"))
            {
                eat("mod");
                Expression factor = parseFactor();
                num = new BinOp("%", num, factor);
            }
        }
        return num;
    }

    /**
     * The parseFactor() method is a line in the grammar. It
     * parses through any negatives or (). By the end of the method, 
     * the number that the factor is equal to is returned. It calls upon 
     * parseNumber() as it is the next line in the grammar.
     * 
     * @precondition  current token is a factor
     * @postcondition current token is the first one after the factor
     * @return an expression object that represents the factor
     */
    private Expression parseFactor()
    {
        if (currToken.equals("-"))
        {
            eat(currToken);
            return new BinOp("-", new ast.Number(0), parseFactor());
        }
        else if (currToken.equals("("))
        {
            eat("(");
            Expression num = parseExpression();
            eat(")");
            return num;
        }
        else
        {
            String currChar = currToken.substring(0, 1);
            if (currChar.compareTo("0") >= 0 && currChar.compareTo("9") <= 0)
            {
                return parseNumber();
            }
            String name = currToken;
            eat(currToken);
            if (!currToken.equals("("))
            {
                return new Variable(name);
            }
            else
            {
                List<Expression> args = new ArrayList<Expression>();
                eat("(");
                while (!currToken.equals(")"))
                {
                    Expression exp = parseExpression();
                    args.add(exp);
                    if (!currToken.equals(")"))
                    {
                        eat(",");
                    }
                }
                eat(")");
                return new ProcedureCall(name, args);
            }
        }
    }

    /**
     * The parseCondition() method is a line in the grammar. It finds the
     * relop and the two expressions and passes it to a new Condition
     * object that it then returns.
     * 
     * @precondition  current token is a condition
     * @postcondition current token is the first one after the condition
     * @return a condition object that represents the condition
     */
    public Condition parseCondition()
    {
        Expression exp1 = parseExpression();
        String relop = currToken;
        eat(currToken);
        Expression exp2 = parseExpression();
        return new Condition(relop, exp1, exp2);
    }

    /**
     * The parseNumber() method checks to see if the current token is a 
     * number. If it isn't, it will throw an exception. Otherwise, it will
     * call the eat() method and return the number parsed.
     * 
     * @precondition  current token is a number
     * @postcondition current token is the first one after the number
     * @return a number object
     */
    private ast.Number parseNumber()
    {
        int num = Integer.parseInt(currToken);
        eat(currToken);
        return new ast.Number(num);
    }
}
