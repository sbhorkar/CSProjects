import java.awt.Color;
/**
 * The Block class creates a block that can be placed in a 'MyBoundedGrid'.
 * The blocks can be customized with different colors and moved around in the
 * grid.
 * 
 * @author Sanaa Bhorkar
 * @version 03/08/2023
 */
public class Block
{
    // instance variables
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;

    /**
     * The default Block() constructor creates a new blue block, which is not 
     * in any location on any grid.
     */
    public Block()
    {
        color = Color.BLUE;
        grid = null;
        location = null;
    }
    
    /**
     * The Block() constructor creates a new block, which is not 
     * in any location on any grid.
     */
    public Block(Color newColor)
    {
        color = newColor;
        grid = null;
        location = null;
    }

    /**
     * The getColor() method returns the color of the block.
     * 
     * @return the color of the block
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * The setColor() method changes the color of the block to what the user
     * desires.
     * 
     * @param newColor the color to change the block to
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * The getGrid() method returns the grid the block is in.
     * 
     * @return the block's grid
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * The getLocation() method returns the location the block is at.
     * 
     * @return the block's location
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * The removeSelfFromGrid() method removes the block from the grid it's 
     * in and therefore sets its grid and location to null.
     * 
     * @postcondition the block is no longer a part of any grid
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * The putSelfInGrid() method puts the block in a given grid at a given
     * location. If a object is already at that location, it replaces it.
     * 
     * @param gr  the grid to put the block in
     * @param loc the location to put the block at
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        grid = gr;
        if (grid.isValid(loc))
        {
            location = loc;   
        }
        Block old = grid.get(location);
        if (old != null)
        {
            old.removeSelfFromGrid();
        }
        grid.put(location, this);
    }

    /**
     * The moveTo() method moves the block to a new location. If a block 
     * already exists there, the current block replaces it.
     * 
     * @param newLocation the location to move to
     */
    public void moveTo(Location newLocation)
    {
        grid.remove(location);
        location = newLocation;
        Block old = grid.get(location);
        if (old != null)
        {
            old.removeSelfFromGrid();
        }
        grid.put(location, this);
    }

    /**
     * The toString() method returns a string with the location and color 
     * of this block.
     * 
     * @return the block's location and color
     */
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }
}