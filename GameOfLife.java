
/**
 * Simple console simulation of Conway's Game of Life
 * I made this in memory of the late mathematician John Conway, 
 * who died of coronavirus complications on April 11, 2020.
 * 
 * @author Jack Donofrio
 * @date 4/14/20
 */

import java.util.ArrayList;
import java.util.Arrays;

public class GameOfLife
{
  public static void main(String[] args) throws InterruptedException
  {
    int size = 40;
    String[][] matrix = new String[size][size];

    for (String[] e : matrix)
    {
      Arrays.fill(e, ".");
    }

    // This is just an example of a start pattern
    // in the future i will add direct user input with GUI
    matrix[10][5] = "O";
    matrix[10][6] = "O";
    matrix[10][7] = "O";
    matrix[11][5] = "O";
    matrix[11][7] = "O";
    matrix[12][5] = "O";

    /*
     * This set creates a cool glider 
     * matrix[5][5] = "O"; 
     * matrix[5][4] = "O";
     * matrix[5][6] = "O";
     * matrix[4][6] = "O"; 
     * matrix[3][5] = "O";
     */

    int generation = 0;
    int maxGenerations = 1000;
    do
    {
      System.out.println("GENERATION " + (1 + generation));

      for (int row = 0; row < matrix.length; row++)
      {
        for (int column = 0; column < matrix.length; column++)
        {
          System.out.print(matrix[row][column]);
        }
        System.out.println();
      }
      System.out.println();
      simulate(matrix);
      int waitTime = 2000; // time between generation in milliseconds
      Thread.sleep(waitTime);
      generation++;
    }
    while (hasCells(matrix) && generation < maxGenerations);

    System.out.println("You have lasted " + generation + " generations.");
  }

  public static void simulate(String[][] matrix)
  {
    ArrayList<Coordinate> points = new ArrayList<>();
    for (int row = 0; row < matrix.length; row++)
    {
      for (int column = 0; column < matrix.length; column++)
      {
        int neighborCount = 0;

        // Determine Neighbors
        /////////////////////////
        if (hasUp(matrix, row, column))
        {
          neighborCount++;
        }
        if (hasDown(matrix, row, column))
        {
          neighborCount++;
        }
        if (hasLeft(matrix, row, column))
        {
          neighborCount++;
        }
        if (hasRight(matrix, row, column))
        {
          neighborCount++;
        }
        if (hasUpRight(matrix, row, column))
        {
          neighborCount++;
        }
        if (hasUpLeft(matrix, row, column))
        {
          neighborCount++;
        }
        if (hasDownRight(matrix, row, column))
        {
          neighborCount++;
        }
        if (hasDownLeft(matrix, row, column))
        {
          neighborCount++;
        }
        ///////////////////////////
        Coordinate coord = new Coordinate(row, column, neighborCount);
        points.add(coord);
      }
    }
    for (int i = 0; i < points.size(); i++)
    {
      Coordinate coord = points.get(i);
      String point = matrix[coord.getRow()][coord.getColumn()];
      int neighborCount = coord.getNeighborCount();

      // Any live cell with two or three live neighbors continues to live,
      // but those with less than two or more than three die.
      if (point.equals("O") && (neighborCount < 2 || neighborCount > 3))
      {
        matrix[coord.getRow()][coord.getColumn()] = ".";
      }
      // Any dead cell with exactly three live neighbors becomes a live cell,
      // as if by reproduction.
      if (point.equals(".") && neighborCount == 3)
      {
        matrix[coord.getRow()][coord.getColumn()] = "O";
      }
    }
  }

  public static boolean hasCells(String[][] matrix)
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int column = 0; column < matrix.length; column++)
      {
        if (matrix[row][column].equals("O"))
          return true;
      }
    }
    return false;
  }

  public static boolean hasUp(String[][] matrix, int row, int column)
  {
    if (row == 0)
    {
      return false;
    }
    return matrix[row - 1][column].equals("O");
  }

  public static boolean hasDown(String[][] matrix, int i, int j)
  {
    if (i == matrix.length - 1)
    {
      return false;
    }
    return matrix[i + 1][j].equals("O");
  }

  public static boolean hasLeft(String[][] matrix, int i, int j)
  {
    if (j == 0)
    {
      return false;
    }
    return matrix[i][j - 1].equals("O");
  }

  public static boolean hasRight(String[][] matrix, int i, int j)
  {
    if (j == matrix.length - 1)
    {
      return false;
    }
    return matrix[i][j + 1].equals("O");
  }

  public static boolean hasUpLeft(String[][] matrix, int i, int j)
  {
    if (i == 0 || j == 0)
    {
      return false;
    }
    return matrix[i - 1][j - 1].equals("O");
  }

  public static boolean hasUpRight(String[][] matrix, int i, int j)
  {
    if (i == 0 || j == matrix.length - 1)
    {
      return false;
    }
    return matrix[i - 1][j + 1].equals("O");
  }

  public static boolean hasDownLeft(String[][] matrix, int i, int j)
  {
    if (i == matrix.length - 1 || j == 0)
    {
      return false;
    }
    return matrix[i + 1][j - 1].equals("O");
  }

  public static boolean hasDownRight(String[][] matrix, int i, int j)
  {
    if (i == matrix.length - 1 || j == matrix.length - 1)
    {
      return false;
    }
    return matrix[i + 1][j + 1].equals("O");
  }
}