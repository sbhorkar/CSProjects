package ast;
import environment.*;

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
}