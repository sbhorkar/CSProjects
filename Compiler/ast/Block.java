package ast;
import java.util.*;
import environment.*;

/**
 * The Block class is for a block of statements in the program. It goes
 * through each statement one by one and executes them. 
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class Block extends Statement
{
    // instance variables
    List<Statement> stmts;

    /**
     * Creates a new Block object.
     * 
     * @param stmts the list of statements in the block
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * The exec() method goes through each statement in the block and
     * executes them in the given environment.
     * 
     * @param env the given environment
     * @postcondition the statements in the block have been executed
     */
    public void exec(Environment env)
    {
        for (Statement stmt : stmts)
        {
            stmt.exec(env);
        }
    }
}