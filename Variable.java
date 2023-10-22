package ast;
import environment.*;

/**
 * The Variable class is for getting the value stored under a variable
 * name. 
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class Variable extends Expression
{
    // instance variables
    String name;

    /**
     * Creates a new Variable object.
     * 
     * @param name the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * The eval() method gets the value for the given variable that is stored
     * in the HashMap of the given environment.
     * 
     * @param env the given environment
     * @return the value corresponding to the variable name
     */
    public int eval(Environment env)
    {
        return env.getVariable(name);
    }
}
