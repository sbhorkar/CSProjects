package ast;
import environment.*;
import emitter.*;

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

    /**
     * The compile() method converts the Variable code into MIPS code. It uses an 
     * emitter to write the converted code to an output file.
     * 
     * @param e the emitter that writes the code to the output file
     */
    public void compile(Emitter e)
    {
        if (e.isLocalVariable(name)) 
        {
            e.emit("# finds value of local variable stored in stack");
            int offset = e.getOffset(name);
            e.emit("addu $t0 $sp " + offset);
            e.emit("lw $v0 ($t0)");
        }
        else 
        {
            e.emit("la $t0 var" + name);
            e.emit("lw $v0 ($t0)");
        }
    }
}
