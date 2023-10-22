import java.util.*;

/**
 * The Tetris class allows a user to play a game of Tetris. The Tetris class
 * specifically manages the active tetrad and continues making moves in the 
 * game.
 *
 * @author Sanaa Bhorkar
 * @version 03/09/2023
 */
public class Tetris implements ArrowListener
{
    // instance variables
    private MyBoundedGrid<Block> grid;
    private BlockDisplay display;
    private Tetrad active;
    private int level;
    private int score;
    private int clearedRows;
    private static boolean gameOver;

    /**
     * The default constructor for Tetris creates a new 20x10 grid and creates
     * a display to show the grid.
     */
    public Tetris()
    {
        grid = new MyBoundedGrid<Block>(20, 10);
        display = new BlockDisplay(grid);
        active = new Tetrad(grid);
        level = 1;
        score = 0;
        clearedRows = 0;
        gameOver = false;
        display.setTitle("Level: " + level + " Score: " + score);
        display.setArrowListener(this);
        display.showBlocks();
    }

    /**
     * The main method that creates a tetris object and plays.
     * 
     * @param args the arguments to pass to the main method
     */
    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
        while (!gameOver)
        {
            tetris.play();
        }
    }

    /**
     * The rightPressed() method is called once the right arrow is pressed on
     * the keyboard. The game responds by moving the active tetrad one column
     * to the right, if valid.
     */
    public void rightPressed()
    {
        active.translate(0, 1);
        display.showBlocks();
    }

    /**
     * The leftPressed() method is called once the left arrow is pressed on
     * the keyboard. The game responds by moving the active tetrad one column
     * to the left, if valid.
     */
    public void leftPressed()
    {
        active.translate(0, -1);
        display.showBlocks();
    }

    /**
     * The rightPressed() method is called once the up arrow is pressed on
     * the keyboard. The game responds by rotating the active tetrad by 90
     * degrees, if valid.
     */
    public void upPressed()
    {
        active.rotate();
        display.showBlocks();
    }

    /**
     * The downPressed() method is called once the down arrow is pressed on
     * the keyboard. The game responds by moving the active tetrad one row
     * down, if valid.
     */
    public void downPressed()
    {
        active.translate(1, 0);
        display.showBlocks();
    }

    /**
     * The spacePressed() method is called once the space bar is pressed on
     * the keyboard. The game responds by moving the active tetrad all the way
     * to the last available row.
     */
    public void spacePressed()
    {
        for (int i = grid.getNumRows() - 1; i >= 0; i--)
        {
            if (active.translate(i, 0))
            {
                display.showBlocks();
                return;
            }
        }
        display.showBlocks();
    }

    /**
     * The play() method moves the active tetrad down to the bottom of the 
     * grid, as time goes on. Once it reaches the bottom, it creates a new
     * tetrad for the user to manipulate.
     */
    public void play()
    {
        try
        {
            if (level > 10)
            {
                Thread.sleep(100);
            }
            else {
                Thread.sleep(1100 - (100 * level));
            }
            if (active.translate(1, 0) == false)
            {
                clearCompletedRows();
                ArrayList<Location> occLocs = grid.getOccupiedLocations();
                if (occLocs.contains(new Location(0, 5)))
                {
                    gameOver = true;
                    System.out.println("You lost! You ended on level " + level + 
                        " with a score of " + score + " points.");
                    return;
                }
                active = new Tetrad(grid);
            }
            display.showBlocks();
        }
        catch(InterruptedException e)
        {
            //ignore
        }
    }

    /**
     * The isCompletedRow() method checks to see if a given row is full of
     * blocks.
     * 
     * @param row the row to check
     * @return true if full; false if not
     */
    private boolean isCompletedRow(int row)
    {
        for (int i = 0; i < grid.getNumCols(); i++)
        {
            if (grid.get(new Location(row, i)) == null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * The clearRow() method clears a given row of all blocks and moves any
     * block in the rows above down one row.
     * 
     * @param row the row to clear
     * @postcondition all blocks above the row to clear are now moved down one
     */
    private void clearRow(int row)
    {
        for (int c = 0; c < grid.getNumCols(); c++)
        {
            grid.get(new Location(row, c)).removeSelfFromGrid();
        }
        for (int r = row - 1; r >= 0; r--)
        {
            for (int c = 0; c < grid.getNumCols(); c++)
            {
                Block b = grid.get(new Location(r, c));
                if (b != null)
                {
                    b.moveTo(new Location(r + 1, c));
                }
            }
        }
        clearedRows++;
        if (clearedRows == 10)
        {
            level++;
            clearedRows = 0;
            display.setTitle("Level: " + level + " Score: " + score);
        }
    }

    /**
     * The clearCompletedRows() method checks to see how many rows are full
     * and clears every one of them.
     */
    private void clearCompletedRows()
    {
        int count = 0;
        for (int i = 0; i < grid.getNumRows(); i++)
        {
            if (isCompletedRow(i))
            {
                clearRow(i);
                count++;
                if (count == 1)
                {
                    score += (40 * level);
                }
                else if (count == 2)
                {
                    score += (100 * level);
                }
                else if (count == 3)
                {
                    score += (300 * level);
                }
                else
                {
                    score += (1200 * level);
                }
            }
        }
        display.setTitle("Level: " + level + " Score: " + score);
    }
}
