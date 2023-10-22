package ast;
import environment.*;

/**
 * The Assignment class is for variable assignments in the program. It
 * stores the value for each variable name in a HashMap.
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class Assignment extends Statement
{
    // instance variables
    private String id;
    private Expression value;
    
    /**
     * Creates a new Assignment object.
     * 
     * @param id    the name of the variable
     * @param value the value assigned to the variable
     */
    public Assignment(String id, Expression value)
    {
        this.id = id;
        this.value = value;
    }
    
    /**
     * The exec() method sets the value of each variable in the HashMap for
     * a given environment.
     * 
     * @param env the given environment
     * @postcondition the value of the variable has been set in the HashMap
     */
    public void exec(Environment env)
    {
        env.setVariable(id, value.eval(env));
    }
}
