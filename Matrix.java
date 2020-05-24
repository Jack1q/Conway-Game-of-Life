import java.util.ArrayList;
import java.util.Arrays;

public class Matrix
{
  private String[][] matrix = new String[50][50];

  public Matrix()
  {
    for (String[] e : matrix)
    {
      Arrays.fill(e, " ");
    }
    matrix[19][18] = "O";
    matrix[19][19] = "O";
    matrix[19][20] = "O";
    matrix[19][22] = "O";
    matrix[20][18] = "O";
    matrix[21][21] = "O";
    matrix[21][22] = "O";
    matrix[22][19] = "O";
    matrix[22][20] = "O";
    matrix[22][22] = "O";
    matrix[23][18] = "O";
    matrix[23][20] = "O";
    matrix[23][22] = "O";
  }

  public void showMatrix()
  {
    for (int row = 0; row < matrix.length; row++)
    {
      for (int column = 0; column < matrix.length; column++)
      {
        System.out.print(matrix[row][column]);
      }
      System.out.println();
    }
    System.out.println();
  }

  public void simulate()
  {
    ArrayList<Coordinate> points = new ArrayList<>();
    for (int row = 0; row < matrix.length; row++)
    {
      for (int column = 0; column < matrix.length; column++)
      {
        int neighborCount = 0;

        // Determine Neighbors
        /////////////////////////
        if (hasUp(row, column))
        {
          neighborCount++;
        }
        if (hasDown(row, column))
        {
          neighborCount++;
        }
        if (hasLeft(row, column))
        {
          neighborCount++;
        }
        if (hasRight(row, column))
        {
          neighborCount++;
        }
        if (hasUpRight(row, column))
        {
          neighborCount++;
        }
        if (hasUpLeft(row, column))
        {
          neighborCount++;
        }
        if (hasDownRight(row, column))
        {
          neighborCount++;
        }
        if (hasDownLeft(row, column))
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
        matrix[coord.getRow()][coord.getColumn()] = " ";
      }
      // Any dead cell with exactly three live neighbors becomes a live cell,
      // as if by reproduction.
      if (point.equals(" ") && neighborCount == 3)
      {
        matrix[coord.getRow()][coord.getColumn()] = "O";
      }
    }
  }

  public boolean hasCells()
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

  public boolean hasUp(int i, int j)
  {
    if (i == 0)
      return false;
    return matrix[i - 1][j].contentEquals("O");
  }

  public boolean hasDown(int i, int j)
  {
    if (i == matrix.length - 1)
      return false;
    return matrix[i + 1][j].equals("O");
  }

  public boolean hasLeft(int i, int j)
  {
    if (j == 0)
      return false;
    return matrix[i][j - 1].equals("O");
  }

  public boolean hasRight(int i, int j)
  {
    if (j == matrix.length - 1)
      return false;
    return matrix[i][j + 1].equals("O");
  }

  public boolean hasUpLeft(int i, int j)
  {
    if (i == 0 || j == 0)
      return false;
    return matrix[i - 1][j - 1].equals("O");
  }

  public boolean hasUpRight(int i, int j)
  {
    if (i == 0 || j == matrix.length - 1)
      return false;
    return matrix[i - 1][j + 1].equals("O");
  }

  public boolean hasDownLeft(int i, int j)
  {
    if (i == matrix.length - 1 || j == 0)
      return false;
    return matrix[i + 1][j - 1].equals("O");
  }

  public boolean hasDownRight(int i, int j)
  {
    if (i == matrix.length - 1 || j == matrix.length - 1)
      return false;
    return matrix[i + 1][j + 1].equals("O");
  }
}