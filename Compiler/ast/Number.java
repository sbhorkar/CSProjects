package ast;
import environment.*;

/**
 * The Number class is specifically for fully evaluated numbers in the 
 * program. It only stores the value of the number and returns it when
 * evaluated.
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class Number extends Expression
{
    // instance variables
    private int value;
    
    /**
     * Creates a new Number object.
     * 
     * @param value is the stored value of the number
     */
    public Number(int value)
    {
        this.value = value;
    }
    
    /**
     * The eval() method returns the number's value in a given environment.
     * 
     * @param env the given evironment
     * @return the value of the number
     */
    public int eval(Environment env)
    {
        return value;
    }
}
