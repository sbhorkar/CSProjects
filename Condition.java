package ast;
import environment.*;

/**
 * The Condtion class is for boolean conditions used in while and if loops. 
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class Condition
{
    // instance variables
    private String relop;
    private Expression exp1;
    private Expression exp2;

    /**
     * Creates a new Condition object.
     * 
     * @param relop is the operator used in the operation
     * @param exp1 is the first expression
     * @param exp2 is the second expression
     */
    public Condition(String relop, Expression exp1, Expression exp2)
    {
        this.relop = relop;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    /**
     * The eval() method checks the condition and returns true if true and
     * false if false for the given environment. 
     * 
     * @param env the given environment
     * @return true if the condition is true, otherwise false
     */
    public boolean eval(Environment env)
    {
        if (relop.equals("="))
        {
            return (exp1.eval(env) == exp2.eval(env));
        }
        else if (relop.equals("<>"))
        {
            return (exp1.eval(env) != exp2.eval(env));
        }
        else if (relop.equals("<"))
        {
            return (exp1.eval(env) < exp2.eval(env));
        }
        else if (relop.equals(">"))
        {
            return (exp1.eval(env) > exp2.eval(env));
        }
        else if (relop.equals("<="))
        {
            return (exp1.eval(env) <= exp2.eval(env));
        }
        else
        {
            return (exp1.eval(env) >= exp2.eval(env));
        }
    }
}
