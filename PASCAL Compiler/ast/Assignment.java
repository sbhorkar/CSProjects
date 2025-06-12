package ast;
import environment.*;
import emitter.*;

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
    
    /**
     * The compile() method converts the variable assignment to MIPS code. 
     * It compiles the value and stores it into the variable. It uses
     * an emitter to write this code into the output file.
     * 
     * @param e the emitter that writes the code to the output file
     */
    public void compile(Emitter e)
    {
        if (e.isLocalVariable(id))
        {
            value.compile(e);
            int offset = e.getOffset(id);
            e.emit("addu $t0 $sp " + offset);
            e.emit("sw $v0 ($t0)");
        }
        else
        {
            e.emit("#sets variable " + id + " to value stored in $v0");
            value.compile(e);
            e.emit("la $t0 var" + id);
            e.emit("sw $v0 ($t0)");
        }
    }
}
