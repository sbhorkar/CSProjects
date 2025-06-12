package ast;
import environment.*;
import emitter.*;

/**
 * The BinOp class is expressions where there are two other expressions on 
 * either side of a binary operator like +, -, *, /, and mod.
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class BinOp extends Expression
{
    // instance variables
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Creates a new BinOp object.
     * 
     * @param op is the operator used in the operation
     * @param exp1 is the first expression
     * @param exp2 is the second expression
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    /**
     * The eval() method evaluates the expression by evaluating the two
     * expressions and performing whichever operation was in the BinOp 
     * statement between the two expressions.
     * 
     * @param env the given environment
     * @return the value that the BinOp evaluates to
     */
    public int eval(Environment env)
    {
        if (op.equals("+"))
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if (op.equals("-"))
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else if (op.equals("*"))
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        else if (op.equals("/"))
        {
            return exp1.eval(env) / exp2.eval(env);
        }
        else
        {
            return exp1.eval(env) % exp2.eval(env);
        }
    }
    
    /**
     * The compile() method converts the binary operation to MIPS code. It
     * checks which operator the expression is using, and then creates
     * the MIPS instruction for that operator. It also pushes and pops the
     * compiled expressions. It uses an emitter to write this code
     * into the output file.
     * 
     * @param e the emitter that writes the code to the output file
     */
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        if (op.equals("+"))
        {
            e.emit("addu $v0 $t0 $v0");
        }
        else if (op.equals("-"))
        {
            e.emit("subu $v0 $t0 $v0");
        }
        else if (op.equals("*"))
        {
            e.emit("multu $t0 $v0");
            e.emit("mflo $v0");
        }
        else if (op.equals("/"))
        {
            e.emit("divu $t0 $v0");
            e.emit("mflo $v0");
        }
        else if (op.equals("mod"))
        {
            e.emit("divu $t0 $v0");
            e.emit("mfhi $v0");
        }
        e.emit("\n");
    }
}
