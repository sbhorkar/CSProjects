package ast;
import environment.*;
import emitter.*;

/**
 * The Expression class is an abstract class that is the base of several
 * expressions in the program such as BinOp, Number, and Variable.
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public abstract class Expression 
{
    /**
     * The eval() method evaluates the expression using a given environment.
     * 
     * @param env the environment for the statement
     * @return the integer that the expression evaluates to
     */
    public abstract int eval(Environment env);

    /**
     * The compile() method converts the PASCAL code for the expression into
     * MIPS code. It uses an emitter to write this code into an output file.
     * 
     * @param e the emitter that writes the code into an output file
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
