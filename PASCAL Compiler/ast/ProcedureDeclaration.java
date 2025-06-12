package ast;
import environment.*;
import java.util.*;
import emitter.*;

/**
 * The ProcedureDeclaration class represents a procedure once it has been
 * declared in the PASCAL program. Therefore, it has an identifier, the body 
 * of the procedure (the statement), and the list of parameters. 
 * 
 * @author Sanaa Bhorkar
 * @version 10/31/2023
 */
public class ProcedureDeclaration extends Statement
{
    // instance variables
    private String id;
    private Statement stmt;
    private List<String> params;
    private List<String> localVars;
    
    /**
     * The ProcedureDeclaration constructor creates a new ProcedureDeclaration
     * and instantiates the id, statement, and parameter objects.
     * 
     * @param id     the name of the procedure
     * @param stmt   the statement in the body of the procedure
     * @param params the list of parameters for the procedure
     * @postcondition a new ProcedureDeclaration object is created
     */
    public ProcedureDeclaration(String id, Statement stmt, List<String> params)
    {
        this.id = id;
        this.stmt = stmt;
        this.params = params;
    }
    
    /**
     * The setLocalVars() method sets the local variables for the procudure to
     * a given list. 
     * 
     * @param v the list that is the list of local variables
     */
    public void setLocalVars(List<String> v)
    {
        localVars = v;
    }
    
    /**
     * The getParams() method returns the params of the ProcedureDeclaration.
     * 
     * @return the list that is the params of the ProcedureDeclaration
     */
    public List<String> getParams()
    {
        return params;
    }
    
    /**
     * The getBody() method returns the body of the ProcedureDeclaration.
     * 
     * @return the statement that is the body of the ProcedureDeclaration
     */
    public Statement getBody()
    {
        return stmt;
    }
    
    /**
     * The getName() method returns the name of the procedure.
     * 
     * @return the name of the procedure
     */
    public String getName()
    {
        return id;
    }
    
    /**
     * The getLocalVars() method returns a list of the local variables of
     * the procedure.
     * 
     * @return a list of local variables
     */
    public List<String> getLocalVars()
    {
        return localVars;
    }

    /**
     * The exec() method executes the ProcedureDeclaration by adding the
     * procedure to the environment. 
     * 
     * @param env the given environment
     * @postcondition the procudure is added to the map of procedures in the
     *                environment
     */
    public void exec(Environment env)
    {
        env.setProcedure(id, this);
    }
    
    /**
     * The compile() method creates the MIPS equivalent of the PASCAL code. 
     * 
     * @param e the emitter that emits each line of MIPS code into the output file
     */
    public void compile(Emitter e)
    {
        e.emit("proc" + id + ":");
        e.emitPush("$zero");
        for (String st : localVars)
        {
            e.emitPush("$zero");
        }
        e.setProcedureContext(this);
        stmt.compile(e);
        for (String localVar : localVars)
        {
            e.emitPop("$v0");
        }
        e.emitPop("$v0");
        e.emit("jr $ra");
        e.clearProcedureContext();
    }
}
