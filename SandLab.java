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
  public static final int WATER = 3;
  public static final int GAS = 4;
  public static final int ACID = 5;

  // do not add any more fields
  private int[][] grid;
  private SandDisplay display;

  public SandLab(int numRows, int numCols) {
    String[] names;
    names = new String[6];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[GAS] = "Gas";
    names[ACID] = "Acid";
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
        } else if (grid[i][j] == WATER) {
          color = new Color(51, 152, 255);// Blue
        } else if (grid[i][j] == GAS) {
          color = new Color(255, 194, 151);// Orange
        } else if (grid[i][j] == ACID) {
          color = new Color(255, 0, 0);// Red
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
          moveDown(randomRow, randomColumn, EMPTY, SAND);
        }
        if (belowLocation == WATER) {
          moveDown(randomRow, randomColumn, WATER, SAND);
        }
      }
    } else if (randomLocation == WATER) {
      int direction = new Random().nextInt(3);// 0-DOWN,1-LEFT,2->RIGHT
      if (direction == Direction.DOWN.ordinal()) {
        moveDown(randomRow, randomColumn, EMPTY, WATER);
      } else if (direction == Direction.LEFT.ordinal()) {
        moveLeft(randomRow, randomColumn, EMPTY, WATER);
      } else if (direction == Direction.RIGHT.ordinal()) {
        moveRight(randomRow, randomColumn, EMPTY, WATER);
      }
    }
  }

  private void moveDown(int randomRow, int randomColumn, int fromLocation, int toLocation) {
    if (randomRow < grid.length - 1) {// If below location is possible
      int belowLocation = grid[randomRow + 1][randomColumn];
      if (belowLocation == fromLocation) {
        grid[randomRow][randomColumn] = fromLocation;
        grid[randomRow + 1][randomColumn] = toLocation;
      }
    }
  }

  private void moveLeft(int randomRow, int randomColumn, int fromLocation, int toLocation) {
    if (randomColumn > 0) {// If left location is possible
      int leftLocation = grid[randomRow][randomColumn - 1];
      if (leftLocation == fromLocation) {
        grid[randomRow][randomColumn] = fromLocation;
        grid[randomRow][randomColumn - 1] = toLocation;
      }
    }
  }

  private void moveRight(int randomRow, int randomColumn, int fromLocation, int toLocation) {
    if (randomColumn < grid[0].length - 1) {// If right location is possible
      int rightLocation = grid[randomRow][randomColumn + 1];
      if (rightLocation == fromLocation) {
        grid[randomRow][randomColumn] = fromLocation;
        grid[randomRow][randomColumn + 1] = toLocation;
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

  enum Direction {
    LEFT, RIGHT, DOWN
  }
}
