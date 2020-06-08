import java.util.ArrayList;

public class Matrix
{
  /*
   * Internal matrix class to handle logic for cell generation
   */
  private boolean[][] matrix;

  public Matrix()
  {
    matrix = new boolean[40][40];
  }

  /*
   * Applies Conway logic to determine the next generation of cells in the
   * internal matrix
   */
  public void nextGeneration()
  {
    ArrayList<Coordinate> points = new ArrayList<>();

    for (int row = 0; row < matrix.length; row++)
    {
      for (int column = 0; column < matrix.length; column++)
      {
        int neighborCount = 0;
        if (hasUp(row, column))
          neighborCount++;
        if (hasDown(row, column))
          neighborCount++;
        if (hasLeft(row, column))
          neighborCount++;
        if (hasRight(row, column))
          neighborCount++;
        if (hasUpRight(row, column))
          neighborCount++;
        if (hasUpLeft(row, column))
          neighborCount++;
        if (hasDownRight(row, column))
          neighborCount++;
        if (hasDownLeft(row, column))
          neighborCount++;
        Coordinate coordinate = new Coordinate(row, column, neighborCount);
        points.add(coordinate);
      }
    }
    for (int i = 0; i < points.size(); i++)
    {
      Coordinate coordinate = points.get(i);
      boolean pointHasCell =
        matrix[coordinate.getRow()][coordinate.getColumn()];
      int neighborCount = coordinate.getNeighborCount();

      // any live cell with 2-3 live neighbors continues to live, but those with
      // less than two or more than three die.
      if (pointHasCell && (neighborCount < 2 || neighborCount > 3))
        matrix[coordinate.getRow()][coordinate.getColumn()] = false;

      // any dead cell with exactly 3 live neighbor becomes a live cell, as if
      // by reproduction
      if (!pointHasCell && neighborCount == 3)
        matrix[coordinate.getRow()][coordinate.getColumn()] = true;
    }
  }

  /*
   * The following methods return true if a cell occupies the location
   * indicated. For example, 'hasUp()' will return true if the space above the
   * given coordinates is occupied by a cell.
   */

  public boolean hasUp(int row, int column)
  {
    if (row == 0)
      return false;
    return matrix[row - 1][column];
  }

  public boolean hasDown(int row, int column)
  {
    if (row == matrix.length - 1)
      return false;
    return matrix[row + 1][column];
  }

  public boolean hasLeft(int row, int column)
  {
    if (column == 0)
      return false;
    return matrix[row][column - 1];
  }

  public boolean hasRight(int row, int column)
  {
    if (column == matrix.length - 1)
      return false;
    return matrix[row][column + 1];
  }

  public boolean hasUpLeft(int row, int column)
  {
    if (row == 0 || column == 0)
      return false;
    return matrix[row - 1][column - 1];
  }

  public boolean hasUpRight(int row, int column)
  {
    if (row == 0 || column == matrix.length - 1)
      return false;
    return matrix[row - 1][column + 1];
  }

  public boolean hasDownLeft(int row, int column)
  {
    if (row == matrix.length - 1 || column == 0)
      return false;
    return matrix[row + 1][column - 1];
  }

  public boolean hasDownRight(int row, int column)
  {
    if (row == matrix.length - 1 || column == matrix.length - 1)
      return false;
    return matrix[row + 1][column + 1];
  }

  public boolean[][] getMatrix()
  {
    return matrix;
  }

  public void setCoordinate(int row, int column, boolean activated)
  {
    matrix[row][column] = activated;
  }

  // For Debugging purposes; (displays internal matrix to console)
  public void matrixToConsole()
  {
    for (int i = 0; i < matrix.length; i++)
    {
      for (int j = 0; j < matrix[i].length; j++)
      {
        if (matrix[i][j])
        {
          System.out.print("O");
        }
        else
        {
          System.out.print("-");
        }
      }
      System.out.println();
    }
  }
  //////
}