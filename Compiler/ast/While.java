package ast;
import environment.*;

/**
 * The While class is for while blocks in the program. It checks against a 
 * condition and performs the inside of the block until the condition is false.
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class While extends Statement
{
    // instance variables
    private Condition cond;
    private Statement stmt;

    /**
     * Creates a new While object.
     * 
     * @param cond is the condition checked in the while block
     * @param stmt is the statement that executes while the condition is true
     */
    public While(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * The exec() method checks to see if the condition is true. While it is, 
     * the while block executes the statements for the given environment. 
     * 
     * @param env the given environment
     * @postcondition the while loop has been executed as long as the condition
     *                is true
     */
    public void exec(Environment env)
    {
        while (cond.eval(env))
        {
            stmt.exec(env);
        }
    }
}
