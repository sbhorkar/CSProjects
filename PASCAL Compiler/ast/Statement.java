package ast;
import environment.*;
import emitter.*;

/**
 * The Statment class is an abstract class that is the base of several
 * statements in the program such as If, While, and Writeln.
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public abstract class Statement
{
    /**
     * The exec() method executes the statement in a given environment.
     * 
     * @param env the environment for the statement
     * @postcondition the statement has been executed
     */
    public abstract void exec(Environment env);

    /**
     * The compile() method converts the PASCAL code for the statement into
     * MIPS code. It uses an emitter to write this code into an output file.
     * 
     * @param e the emitter that writes the code into an output file
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}