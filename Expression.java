package ast;
import environment.*;

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
}
