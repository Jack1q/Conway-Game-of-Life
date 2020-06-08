public class Coordinate
{
  private int row;
  private int col;
  private int neighborCount;

  public Coordinate(int row, int col, int neighborCount)
  {
    this.row = row;
    this.col = col;
    this.neighborCount = neighborCount;
  }

  public int getRow()
  {
    return row;
  }

  public int getColumn()
  {
    return col;
  }

  public int getNeighborCount()
  {
    return neighborCount;
  }
}