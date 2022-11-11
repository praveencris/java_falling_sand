import java.awt.*;
import java.util.*;

public class SandLab {
  public static void main(String[] args) {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }

  // add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;

  // do not add any more fields
  private int[][] grid;
  private SandDisplay display;

  public SandLab(int numRows, int numCols) {
    String[] names;
    names = new String[3];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    grid = new int[numRows][numCols];

  }

  // called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool) {
    grid[row][col] = tool;
  }

  // copies each element of grid into the display
  public void updateDisplay() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        Color color = null;
        if (grid[i][j] == EMPTY) {
          color = new Color(0, 0, 0);// Black
        } else if (grid[i][j] == METAL) {
          color = new Color(128, 128, 128);// Gray
        } else if (grid[i][j] == SAND) {
          color = new Color(255, 255, 0);// Yellow
        } else {
          color = new Color(0, 0, 0);// Black
        }
        display.setColor(i, j, color);
      }
    }
  }

  // called repeatedly.
  // causes one random particle to maybe do something.
  public void step() {
    int randomRow = new Random().nextInt(grid.length);
    int randomColumn = new Random().nextInt(grid[0].length);
    int randomLocation = grid[randomRow][randomColumn];
    if (randomLocation == SAND) {
      if (randomRow < grid.length - 1) {// If below location is possible
        int belowLocation = grid[randomRow + 1][randomColumn];
        if (belowLocation == EMPTY) {
          grid[randomRow][randomColumn] = EMPTY;
          grid[randomRow + 1][randomColumn] = SAND;
        }
      }

    }
  }

  // do not modify
  public void run() {
    while (true) {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1); // wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null) // test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
