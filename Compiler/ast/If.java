package ast;
import environment.*;

/**
 * The If class is for if blocks in the program. It checks against a 
 * condition and performs the inside of the block if the condition is true.
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class If extends Statement
{
    // instance variables
    private Condition cond;
    private Statement stmt;

    /**
     * Creates a new If object.
     * 
     * @param cond is the condition checked in the if block
     * @param stmt is the statement that executes if the condition is true
     */
    public If(Condition cond, Statement stmt)
    {
        this.cond = cond;
        this.stmt = stmt;
    }

    /**
     * The exec() method checks to see if the condition is true. If it is, 
     * the if block executes for the given environment. 
     * 
     * @param env the given environment
     * @postcondition the if block has been executed, if the condition is true
     */
    public void exec(Environment env)
    {
        if (cond.eval(env))
        {
            stmt.exec(env);
        }
    }
}
