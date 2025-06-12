package ast;
import environment.*;
import emitter.*;

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

    /**
     * The compile() method converts the condition to MIPS code. It first 
     * stores each expression, and then creates the conditional statement by 
     * negating the relop in the code. It also takes in a label for the 
     * conditional. It uses an emitter to write this code to the output
     * file.
     * 
     * @param e      the emitter that writes the code to the output file
     * @param target the label that allows you to skip the code if 
     *               the conditional is true
     */
    public void compile(Emitter e, String target)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        if (relop.equals("=")) 
        {
            String code = "# jumps to "+ target +" if value in $t0 doesn't equal value in $v0";
            e.emit(code);
            e.emit("bne $t0 $v0 " + target); 
        }
        else if (relop.equals("<>")) 
        {
            String code = "# jumps to "+ target +" if value in $t0 is equal to value in $v0";
            e.emit(code);
            e.emit("beq $t0 $v0 " + target); 
        }
        else if (relop.equals("<")) 
        {
            String code = "# jumps to "+ target + 
                          " if value in $t0 is more than/equal to value in $v0";
            e.emit(code);
            e.emit("bge $t0 $v0 " + target); 
        }
        else if (relop.equals(">")) 
        {
            String code = "# jumps to "+ target + 
                          " if value in $t0 is less than/equal to value in $t0";
            e.emit(code);
            e.emit("ble $t0 $v0 " + target); 
        }
        else if (relop.equals("<=")) 
        {
            String code = "#jumps to "+ target +" if value in $t0 is more than value in $v0";
            e.emit(code);
            e.emit("bgt $t0 $v0 " + target); 
        }
        else if (relop.equals(">="))
        {
            String code = "# jumps to "+ target +" if value in $t0 is less than value in $v0";
            e.emit(code);
            e.emit("blt $t0 $v0 " + target); 
        }
        e.emit("\n");
    }
}