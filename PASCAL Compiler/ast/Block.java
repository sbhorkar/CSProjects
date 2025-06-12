package ast;
import java.util.*;
import environment.*;
import emitter.*;

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
    
    /**
     * The compile() method converts each statement in the block to 
     * MIPS assembly code. It uses an emitter to write the converted code
     * to an output file.
     * 
     * @param e the emitter that writes the code to the output file
     */
    public void compile(Emitter e)
    {
        for (Statement stmt : stmts)
        {
            stmt.compile(e);
        }
    }
}