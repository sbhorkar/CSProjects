package environment;
import java.util.*;
import ast.*;

/**
 * The Environment class has methods to set and get values under variables.
 * It is used in the Assignment and Variable classes in AST for getting
 * and setting variables. 
 * 
 * @author Sanaa Bhorkar
 * @version 10/13/2023
 */
public class Environment
{
    // instance variables
    Map<String, Integer> varMap;
    Map<String, ProcedureDeclaration> proMap;
    Environment parent;

    /**
     * Creates a new Environment object by instantiating the variable 
     * HashMap.
     * 
     * @param par the parent environment for the given environment (if not
     *        applicable, then null is used)
     * @postcondition a new Environment object is created that is connected to
     *                its parent
     */
    public Environment(Environment par)
    {
        varMap = new HashMap<String, Integer>();
        proMap = new HashMap<String, ProcedureDeclaration>();
        parent = par;
    }

    /**
     * The getVarMap() method returns the variable map for the environment.
     * 
     * @return the variable map with keys as strings and values as integers
     */
    public Map<String, Integer> getVarMap()
    {
        return varMap;
    }

    /**
     * The getProMap() method returns the procedure map for the environment.
     * 
     * @return the procedure map with keys as strings and values as 
     *         procedure declarations
     */
    public Map<String, ProcedureDeclaration> getProMap()
    {
        return proMap;
    }

    /**
     * The setVariable() method associates a value with the variable.
     * 
     * @param variable the variable to associate to
     * @param value    the value to associate with the variable
     * @postcondition  the value to a variable is added/updated to the HashMap
     */
    public void setVariable(String variable, int value)
    {
        if (varMap.get(variable) == null)
        {
            if (parent == null || parent.getVarMap().get(variable) == null)
            {
                varMap.put(variable, value);
            }
            else
            {
                parent.getVarMap().put(variable, value);
            }
        }
        else
        {
            varMap.put(variable, value);
        }
    }
    
    /**
     * The declareVariable() method declares a variable in the current
     * environment's variable map. 
     * 
     * @param variable the name of the variable
     * @param value    the integer value of the variable
     * @postcondition  the variable is added to the variable map of the environment
     */
    public void declareVariable(String variable, int value)
    {
        varMap.put(variable, value);
    }

    /**
     * The getVariable() method returns the value assosciated with the 
     * variable.
     * 
     * @param variable the given variable to find the value for
     * @return the value of the variable
     */
    public int getVariable(String variable)
    {
        if (varMap.get(variable) == null)
        {
            if (parent != null && parent.getVarMap().get(variable) == null)
            {
                varMap.put(variable, 0);
                return 0;
            }
            else
            {
                return parent.getVarMap().get(variable);
            }
        }
        else
        {
            return varMap.get(variable);
        }
    }

    /**
     * The setProcedure() method adds a new procedure and its declaration
     * object to the procedure map of the parent environment. It does this by
     * checking if the environment has a parent. If it does, then it calls upon
     * its procedure map and adds a new entry to that map. Otherwise, this means 
     * that the environment is the parent environment so it adds the object
     * to the current environment's procedure map.
     * 
     * @param name the name of the procedure
     * @param dec  the declaration object of the procedure
     */
    public void setProcedure(String name, ProcedureDeclaration dec)
    {
        if (parent == null)
        {
            proMap.put(name, dec);
        }
        else
        {
            parent.getProMap().put(name, dec);
        }
    }

    /**
     * The getProcedure() method returns the procedure declaration from the 
     * parent environment's procedure map. It does this by checking if the 
     * environment has a parent. If it does, then it calls upon its procedure 
     * map and gets that entry from its map. Otherwise, this means that the 
     * environment is the parent environment so it gets the entry from its own
     * procedure map.
     * 
     * @param name the name of the procedure
     * @return the ProcedureDeclaration object assosciated with the given procedure
     */
    public ProcedureDeclaration getProcedure(String name)
    {
        if (parent == null)
        {
            return proMap.get(name);
        }
        return parent.getProMap().get(name);
    }
    
    /**
     * The getParent() method returns the parent environment for the 
     * environment. 
     * 
     * @return the parent environment
     */
    public Environment getParent()
    {
        return parent;
    }
}
