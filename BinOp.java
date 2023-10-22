package ast;
import environment.*;

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
}
