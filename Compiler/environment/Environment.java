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
    
    /**
     * Creates a new Environment object by instantiating the variable 
     * HashMap.
     */
    public Environment()
    {
        varMap = new HashMap<String, Integer>();
        proMap = new HashMap<String, ProcedureDeclaration>();
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
        return varMap.get(variable);
    }
    
    /**
     * 
     */
    public void setProcedure(String name, ProcedureDeclaration dec)
    {
        proMap.put(name, dec);
    }
    
    /**
     * 
     */
    public ProcedureDeclaration getProcedure(String name)
    {
        return proMap.get(name);
    }
}
