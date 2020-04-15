public class Coordinate
{
  private int row;
  private int column;
  private int neighborCount;

  public Coordinate(int row, int column, int neighborCount)
  {
    this.row = row;
    this.column = column;
    this.neighborCount = neighborCount;
  }

  public int getRow()
  {
    return row;
  }

  public int getColumn()
  {
    return column;
  }

  public int getNeighborCount()
  {
    return neighborCount;
  }

}