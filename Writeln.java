package ast;
import environment.*;

/**
 * The Writeln class is for WRITELN() statements in the program. It executes
 * by printing the expression in the paranthesis.
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class Writeln extends Statement
{
    // instance variables
    private Expression exp;

    /**
     * Creates a new Writeln object.
     * 
     * @param exp is the expression whose value should be printed
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * The exec() method executes the Writeln statement by printing what the
     * expression evaluates to in a given environment.
     * 
     * @param env the given environment
     * @postcondition the expression has been printed
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
}
