import java.util.ArrayList;
/**
 * The MyBoundedGrid<E> class creates a 2d grid that can store any object at
 * a valid location in the grid.
 *
 * @author Sanaa Bhorkar
 * @version 03/08/2023
 */
public class MyBoundedGrid<E>
{
    // instance variables
    private Object[][] grid;

    /**
     * Constructor for objects of class MyBoundedGrid that instantiates the
     * number of rows and columns.
     * 
     * @param r the # of rows
     * @param c the # of columns
     */
    public MyBoundedGrid(int r, int c)
    {
        grid = new Object[r][c];
    }

    /**
     * The getNumRows() method returns the number of rows in the grid.
     * 
     * @return the number of rows
     */
    public int getNumRows()
    {
        return grid.length;
    }

    /**
     * The getNumCols() method returns the number of columns in the grid.
     * 
     * @return the number of columns
     */
    public int getNumCols()
    {
        return grid[0].length;
    }

    /**
     * The isValid() method takes in a location and checks if its a valid
     * location in the grid.
     * 
     * @param loc the location to check
     * @return true if valid; false if not
     */
    public boolean isValid(Location loc)
    {
        int r = loc.getRow();
        int c = loc.getCol();
        return ((r < this.getNumRows() && c < this.getNumCols()) 
            && (r >= 0 && c >= 0));
    }

    /**
     * The put() method puts an object at a specified location. 
     * 
     * @param loc the location to put the object at
     * @param obj the object to put in the grid at the location
     * @return the old object at that location
     */
    public E put(Location loc, E obj)
    {
        if (isValid(loc))
        {
            int r = loc.getRow();
            int c = loc.getCol();
            E old = (E) grid[r][c];
            grid[r][c] = obj;
            return old;
        }
        return null;
    }

    /**
     * The remove() method removes the object at a specified location
     * 
     * @param loc the location to remove the object from
     * @return the old object at that location
     */
    public E remove(Location loc)
    {
        int r = loc.getRow();
        int c = loc.getCol();
        E obj = (E) grid[r][c];
        grid[r][c] = null;
        return obj;
    }

    /**
     * The get() method gets the object at a specified location
     * 
     * @param loc the location to get the object fromm
     * @return the object at that location
     */
    public E get(Location loc)
    {
        int r = loc.getRow();
        int c = loc.getCol();
        return ((E) (grid[r][c]));
    }

    /**
     * The getOccupiedLocations() method returns an array list of all of
     * the locations in the grid that contain an object (are not null).
     * 
     * @return the array list of locations
     */
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (int r = 0; r < this.getNumRows(); r++)
        {
            for (int c = 0; c < this.getNumCols(); c++)
            {
                if (grid[r][c] != null)
                {
                    locs.add(new Location(r, c));
                }
            }
        }
        return locs;
    }
}
