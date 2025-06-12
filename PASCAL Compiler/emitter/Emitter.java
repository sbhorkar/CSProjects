package emitter;
import java.util.*;
import java.io.*;
import ast.*;

/**
 * The Emitter class writes code to an output file.
 * 
 * @author Skeleton provided by Anu Datar
 * @version 12/01/2023
 */
public class Emitter
{
    private PrintWriter out;
    private int idCounter;
    private ProcedureDeclaration currProc;
    private int excessStackHeight;
    private Program prog;

    /**
     * The Emitter constructor creates sets the idCounter to 0 and cretes a
     * new PrintWriter object for the given output file.
     * 
     * @param outputFileName the file name for the outputted MIPS code
     * @param p              the name of the program
     */
    public Emitter(String outputFileName, Program p)
    {
        idCounter = 0;
        excessStackHeight = 0;
        prog = p;
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        } catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * The emit() writes the given line of code to the file.
     * 
     * @param code the line of code
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
        {
            code = "\t" + code;
        }
        out.println(code);
    }

    /**
     * The close() method closes the file written by PrintWriter.
     * 
     * @precondition all code is written to the file
     */
    public void close()
    {
        out.close();
    }

    /**
     * The emitPush() method writes code to push whatever is stored in a given
     * register to the stack.
     * 
     * @param reg push the contents of this register to the stack
     */
    public void emitPush(String reg)
    {
        emit("subu $sp $sp 4");
        emit("sw " + reg + " ($sp)");
        excessStackHeight++;
    }

    /**
     * The emitPop() method writes code to pop whatever is stored on the stack
     * to a given register.
     * 
     * @param reg pop the contents of the stack to this register
     */
    public void emitPop(String reg)
    {
        emit("lw " + reg + " ($sp)");
        emit("addu $sp $sp 4");
        excessStackHeight--;
    }

    /**
     * The emitNewLine() method writes MIPS code to emit a new line in the
     * program.
     */
    public void emitNewLine()
    {
        emit("li $v0 4");
        emit("la $a0 newline");
        emit("syscall");
    }

    /**
     * The nextLabelID() method keeps track of the idCounter for the If/While
     * labels.
     * 
     * @return the next idCounter
     */
    public int nextLabelId()
    {
        idCounter++;
        return idCounter;
    }

    /**
     * The setProcedureContext() method sets the given declaration as the
     * current procedure context, which changes the next lines of MIPS code.
     * 
     * @param proc the procedure declaration
     * 
     */
    public void setProcedureContext(ProcedureDeclaration proc)
    {
        currProc = proc;
        excessStackHeight = 0;
    }

    /**
     * The clearProcudureContext() method clears the current procedure context.
     */
    public void clearProcedureContext()
    {
        currProc = null;
    }

    /**
     * The isLocalVariable sees if a given string is a local variable by seeing
     * if the name matchs the current procedure name or if it is a part of the
     * local variables in the current procedure. Otherwise, if the variable
     * is not a local variable, the variable is added to the list of variables 
     * for the procudure. 
     * 
     * @param varName the variable to check
     * @return true if the given variable is a local variable; otherwise false
     */
    public boolean isLocalVariable(String varName)
    {
        if (currProc == null) 
        {
            prog.addVars(varName);
            return false;
        }
        String procname = currProc.getName();
        if (procname.equals(varName))
        { 
            return true;
        }
        List<String> params = currProc.getParams();
        for (String s : params)
        {
            if (varName.equals(s))
            { 
                return true;
            }
        }
        List<String> localVars = currProc.getLocalVars();
        for (String s : localVars)
        {
            if (varName.equals(s))
            { 
                return true;
            }
        }
        prog.addVars(varName);
        return false;
    }

    /**
     * The getOffset() method determines the offset required on the stack
     * due to the variables.
     * 
     * @param localVarName the name of a local variable for the current 
     *                          procedure
     * @return the offset
     */
    public int getOffset(String localVarName)
    {
        List<String> params = currProc.getParams();
        List<String> localVars = currProc.getLocalVars();
        int ind = params.size() + localVars.size();
        String procname = currProc.getName();
        if (procname.compareTo(localVarName) == 0)
        {
            return 4 * (localVars.size () + excessStackHeight);   
        }
        for (String s : params)
        {
            if (localVarName.compareTo(s) == 0)
            {
                return 4*(ind+excessStackHeight);
            }
            ind--;
        }
        ind--;
        for (String str : localVars)
        {
            if (localVarName.equals(str))
            {
                return 4*(ind+excessStackHeight);
            }
            ind--;
        }
        return 4*(ind+excessStackHeight);
    }
}