public class GameOfLife
{
  public static void main(String[] args) throws InterruptedException 
  {
    game();
  }

  public static void game() throws InterruptedException
  {
    Matrix matrix = new Matrix();
    int gen = 0;
    int maxGen = 1000;
    do
    {
      System.out.println("Generation " + (1 + gen));
      matrix.showMatrix();
      matrix.simulate();
      Thread.sleep(1000); // wait 1000 ms = 1 s
      gen++;
    }
    while (matrix.hasCells() && gen < maxGen);

  }
}
