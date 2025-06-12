package ast;
import environment.*;
import java.util.*;
import emitter.*;

/**
 * The ProcedureCall objet represents when a procedure is called upon and 
 * therefore needs to run and evaluate. Therefore, it extends the Expression
 * class. The ProcedureCall object has two instance variables: the name of the
 * procedure and the arguments given by the user. 
 * 
 * @author Sanaa Bhorkar
 * @version 10/31/2023
 */
public class ProcedureCall extends Expression
{
    // instance variables
    private String id;
    private List<Expression> args;

    /**
     * The ProcedureCall constructor creates a new ProcedureCall object and 
     * instantiates the variables for its name and the arguments that need
     * to be passed. 
     * 
     * @param id   the name of the procedure
     * @param args the list of arguments to be passed to the procedure
     * @postcondition a new ProcedureCall object is created
     */
    public ProcedureCall(String id, List<Expression> args)
    {
        this.id = id;
        this.args = args;
    }

    /**
     * The eval() method evaluates the procedure call. It does this by first
     * creating the child environment for this procedure. Then, it sets the
     * arguments as the parameters for the procedure in the new child
     * environment. Next, it sets the "return" value as 0. Finally, it executes
     * the body of the procedure and returns its return value.
     * 
     * @param env the given parent environment
     * @return the return value for the procedure (if there is no specified
     *         return value, it returns 0)
     */
    public int eval(Environment env)
    {
        Environment child = null;
        if (env.getParent() == null)
        {
            child = new Environment(env);
        }
        else
        {
            child = new Environment(env.getParent());
        }
        ProcedureDeclaration dec = env.getProcedure(id);
        List<String> params = dec.getParams();
        for (int argsInd = 0; argsInd < args.size(); argsInd++)
        {
            child.declareVariable(params.get(argsInd), args.get(argsInd).eval(env));
        }
        List<String> localVars = dec.getLocalVars();
        for (String localVar : localVars)
        {
            child.declareVariable(localVar, 0);
        }
        child.declareVariable(id, 0);
        if (dec != null) 
        {
            dec.getBody().exec(child);
        }
        return child.getVariable(id);
    }
    
    /**
     * The compile() method creates the MIPS equivalent of the PASCAL code. 
     * 
     * @param e the emitter that emits each line of MIPS code into the output file
     */
    public void compile(Emitter e)
    {
        e.emitPush("$ra");
        for (Expression exp : args)
        {
            exp.compile(e);
            e.emitPush("$v0");
        }
        e.emit("jal proc" + id);
        e.emit("#popping parameters from stack");
        for (int i = 0; i < args.size(); i++)
        {
            e.emitPop("$t0");
        }
        e.emitPop("$ra");
    }
}
