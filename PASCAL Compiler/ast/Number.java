package ast;
import environment.*;
import emitter.*;

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
    
    /**
     * The compile() method converts the expression into MIPS code. It loads
     * the value of the number into $v0. It uses an emitter to write this 
     * code into the output file.
     * 
     * @param e the emitter that writes the code to the output file
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}
