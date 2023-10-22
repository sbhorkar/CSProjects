import java.awt.*;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * The Tetrad class holds a tetrad of four blocks for the Tetris game.
 *
 * @author Sanaa Bhorkar
 * @version 03/09/2023
 */
public class Tetrad
{
    // instance variables
    private Block[] blocks;
    private int p0;
    private Semaphore lock;

    /**
     * The default Tetrad() constructor chooses a random tetrad to instatiate
     * and puts it in the center of the grid.
     * 
     * @postcondition a new Tetrad is added to the center of the top row
     */
    public Tetrad(MyBoundedGrid<Block> newGrid)
    {
        lock = new Semaphore(1, true);
        Color[] colors = {Color.RED, Color.GRAY, Color.CYAN, Color.YELLOW, 
                Color.MAGENTA, Color.BLUE, Color.GREEN};
        Color color = colors[(int) (Math.random() * colors.length)];
        blocks = new Block[4];
        for (int i = 0; i < 4; i++)
        {
            blocks[i] = new Block(color);
        }
        Location[] locations = new Location[4];
        if (color == Color.RED)
        {
            locations[0] = new Location(0, 5);
            locations[1] = new Location(1, 5);
            locations[2] = new Location(2, 5);
            locations[3] = new Location(3, 5);
            p0 = 1;
        }
        else if (color == Color.GRAY)
        {
            locations[0] = new Location(0, 4);
            locations[1] = new Location(0, 5);
            locations[2] = new Location(0, 6);
            locations[3] = new Location(1, 5);
            p0 = 1;
        }
        else if (color == Color.CYAN)
        {
            locations[0] = new Location(0, 4);
            locations[1] = new Location(0, 5);
            locations[2] = new Location(1, 4);
            locations[3] = new Location(1, 5);
            p0 = 0;
        }
        else if (color == Color.YELLOW)
        {
            locations[0] = new Location(0, 5);
            locations[1] = new Location(1, 5);
            locations[2] = new Location(2, 5);
            locations[3] = new Location(2, 6);
            p0 = 1;
        }
        else if (color == Color.MAGENTA)
        {
            locations[0] = new Location(0, 5);
            locations[1] = new Location(1, 5);
            locations[2] = new Location(2, 4);
            locations[3] = new Location(2, 5);
            p0 = 1;
        }
        else if (color == Color.BLUE)
        {
            locations[0] = new Location(0, 4);
            locations[1] = new Location(0, 5);
            locations[2] = new Location(1, 3);
            locations[3] = new Location(1, 4);
            p0 = 1;
        }
        else
        {
            locations[0] = new Location(0, 4);
            locations[1] = new Location(0, 5);
            locations[2] = new Location(1, 5);
            locations[3] = new Location(1, 6);
            p0 = 1;
        }
        addToLocations(newGrid, locations);
    }

    /**
     * The addToLocations() method adds all blocks in the blocks array
     * to the given locations on a given grid.
     * 
     * @param grid the grid to add the blocks to
     * @param locs the locations to add each block to
     * @postcondition the tetrad is placed at the given locations on the grid
     */
    private void addToLocations(MyBoundedGrid<Block> grid, Location[] locs)
    {
        for (int i = 0; i < blocks.length; i++)
        {
            blocks[i].putSelfInGrid(grid, locs[i]);
        }
    }

    /**
     * The removeBlocks() method removes all of the blocks in the blocks 
     * array from the current grid they are on.
     * 
     * @return an array of the previous locations of the blocks
     */
    private Location[] removeBlocks()
    {
        Location[] locs = new Location[4];
        for (int i = 0; i < blocks.length; i++)
        {
            locs[i] = blocks[i].getLocation();
            blocks[i].removeSelfFromGrid();
        }
        return locs;
    }

    /**
     * The areEmpty() method checks to see if the given locations on a given 
     * grid are not occupied by the current blocks. It also checks if these
     * locations are valid, and not occupied by another tetrad.
     * 
     * @param grid the grid to check the locations of
     * @param locs the locations to check
     * @return true if empty and valid; false if not
     */
    private boolean areEmpty(MyBoundedGrid<Block> grid, Location[] locs)
    {
        ArrayList<Location> occLocs = grid.getOccupiedLocations();
        Location[] currentLocs = new Location[4];
        for (int i = 0; i < blocks.length; i++)
        {
            currentLocs[i] = blocks[i].getLocation();
        }
        ListIterator<Location> it = occLocs.listIterator();
        while(it.hasNext())
        {
            Location occ = it.next();
            for (Location curr : currentLocs)
            {
                if (occ.equals(curr))
                {
                    it.remove();
                }
            }
        }
        for (Location location : locs)
        {
            if (!(grid.isValid(location)) || occLocs.contains(location))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * The translate() method moves all of the blocks deltaRow rows down and
     * deltaCol cols to the right. It only moves the blocks if the new 
     * locations are empty (pass the areEmpty() method).
     * 
     * @param deltaRow the change in row
     * @param deltaCol the change in column
     * @return true if possible; false if not
     **/
    public boolean translate(int deltaRow, int deltaCol)
    {
        try
        {
            lock.acquire();
            Location[] locs = new Location[4];
            MyBoundedGrid<Block> grid = blocks[0].getGrid();
            for (int i = 0; i < blocks.length; i++)
            {
                Location l = blocks[i].getLocation();
                int r = l.getRow();
                int c = l.getCol();
                locs[i] = new Location(r + deltaRow, c + deltaCol);
            }
            if (areEmpty(grid, locs))
            {
                removeBlocks();
                addToLocations(grid, locs);
                return true;
            }
            return false;
        }
        catch (InterruptedException e)
        {
            return false;
        }
        finally
        {
            lock.release();
        }
    }

    /**
     * The rotate() method rotates the tetrad by 90 degrees on the grid.
     * 
     * @return true if the rotate was successful; false if not
     */
    public boolean rotate()
    {
        try
        {
            lock.acquire();
            if (blocks[0].getColor() == Color.CYAN)
            {
                return true;
            }
            Location[] neededLocs = new Location[4];
            Location loc0 = blocks[p0].getLocation();
            MyBoundedGrid<Block> grid = blocks[0].getGrid();
            for (int i = neededLocs.length - 1; i >= 0; i--)
            {
                Location curr = blocks[i].getLocation();
                int r = loc0.getRow() - loc0.getCol() + curr.getCol();
                int c = loc0.getRow() + loc0.getCol() - curr.getRow();
                neededLocs[i] = new Location(r,c);
            }
            if (areEmpty(grid, neededLocs))
            {
                removeBlocks();
                addToLocations(grid, neededLocs);
                return true;
            }
            return false;
        }
        catch (InterruptedException e)
        {
            return false;
        }
        finally
        {
            lock.release();
        }
    }
}
