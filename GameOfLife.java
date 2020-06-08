import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.SwingConstants;

public class GameOfLife
{

  private JFrame frame;
  private JCheckBox[][] grid;
  private JLabel cellCountLabel;
  private JLabel generationCountLabel;
  private Matrix matrix;
  private static int generation = 0;

  /**
   * Launch the application.
   */
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          GameOfLife window = new GameOfLife();
          window.frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public GameOfLife()
  {
    initializeFrame();
    populateFrame();
    initializeGrid();
    initializeColorSelectors();
  }

  private void initializeFrame()
  {
    matrix = new Matrix();
    frame = new JFrame();
    frame.setIconImage(Toolkit.getDefaultToolkit()
      .getImage(GameOfLife.class.getResource("/images/icon.png")));
    frame.setTitle("Conway's Game of Life");
    frame.getContentPane().setBackground(Color.WHITE);
    frame.setBounds(100, 100, 512, 598);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    frame.setResizable(false);
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private void populateFrame()
  {
    // button that resets board to default
    JButton resetButton = new JButton("Reset");
    resetButton.setBounds(10, 528, 89, 23);
    resetButton.setFocusPainted(false);
    resetButton.setToolTipText("Kill all cells");
    resetButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        resetBoard();
      }
    });
    frame.getContentPane().add(resetButton);

    // button that spawns next gen
    JButton nextButton = new JButton("Next");
    nextButton.setBounds(105, 528, 89, 23);
    nextButton.setFocusPainted(false);
    nextButton.setToolTipText("Spawn next generation of cells");
    nextButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        renderNextGeneration();
      }
    });
    frame.getContentPane().add(nextButton);

    // displays the number of live cells at the current generation
    cellCountLabel = new JLabel("");
    cellCountLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
    cellCountLabel.setBounds(10, 510, 184, 14);
    frame.getContentPane().add(cellCountLabel);

    // displays the current generation number
    generationCountLabel = new JLabel("");
    generationCountLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
    generationCountLabel.setBounds(10, 495, 186, 14);
    generationCountLabel.setText("Generation: " + generation);
    frame.getContentPane().add(generationCountLabel);
  }

  /*
   * Creates external grid that the suer sees.
   */
  private void initializeGrid()
  {
    grid = new JCheckBox[40][40];
    for (int row = 0; row < grid.length; row++)
    {
      for (int col = 0; col < grid[row].length; col++)
      {
        grid[row][col] = new JCheckBox("");
        grid[row][col].setBounds(6 + col * 12, 7 + 12 * row, 16, 16);
        grid[row][col].setContentAreaFilled(false);
        grid[row][col].setIcon(new ImageIcon(
          GameOfLife.class.getResource("/images/whiteSquare.jpg")));
        grid[row][col].setSelectedIcon(new ImageIcon(
          GameOfLife.class.getResource("/images/blackSquare.png")));
        frame.getContentPane().add(grid[row][col]);
      }
    }
  }

  /*
   * Basically handles what happens when user clicks Next. First, the user's
   * actions are registered to the internal matrix. Then, the matrix applies the
   * Conway logic to the changed matrix. These changes are then read to the
   * external grid for the user to see. Finally the cell count and generation
   * counts are updated.
   */
  private void renderNextGeneration()
  {
    writeChecksToMatrix(); // send data to matrix
    matrix.nextGeneration(); // handle data
    readCellsToGrid(); // read data from matrix
    cellCountLabel.setText("Cells: " + checkCount());
    // for debugging // matrix.matrixToConsole();
    generation++;
    generationCountLabel.setText("Generation: " + generation);
  }

  /*
   * Reads cell data from internal matrix after generation logic has been
   * performed. Writes this new cell data to the outer grid.
   */
  private void readCellsToGrid()
  {
    for (int r = 0; r < matrix.getMatrix().length; r++)
    {
      for (int c = 0; c < matrix.getMatrix()[r].length; c++)
      {
        if (matrix.getMatrix()[r][c])
        {
          grid[r][c].setSelected(true);
        }
        else
        {
          grid[r][c].setSelected(false);
        }
      }
    }
  }

  /*
   * Sends data of squares that have been clicked by the user to the internal
   * matrix, copies that data to matrix.
   */
  private void writeChecksToMatrix()
  {
    for (int r = 0; r < grid.length; r++)
    {
      for (int c = 0; c < grid[r].length; c++)
      {
        if (grid[r][c].isSelected())
        {
          matrix.setCoordinate(r, c, true);
        }
        else
        {
          matrix.setCoordinate(r, c, false);
        }
      }
    }
  }

  /*
   * Counts the number of checks, aka cells
   */
  private int checkCount()
  {
    int count = 0;
    for (int r = 0; r < grid.length; r++)
    {
      for (int c = 0; c < grid[r].length; c++)
      {
        if (grid[r][c].isSelected())
          count++;
      }
    }
    return count;
  }

  /*
   * resets internal game matrix and outer grid to default, effectively kills
   * all cells
   */
  private void resetBoard()
  {
    matrix = new Matrix();
    for (int row = 0; row < grid.length; row++)
    {
      for (int column = 0; column < grid[row].length; column++)
      {
        if (grid[row][column].isSelected())
          grid[row][column].setSelected(false);
      }
    }
    cellCountLabel.setText("Cells: " + checkCount());
    generation = 0;
    generationCountLabel.setText("Generation: 0");
  }

  /*
   * Sets all of the selected icons of the cells in the grid to the color given
   */
  private void setSelectedSquareColor(ImageIcon colorSquare)
  {
    for (int r = 0; r < grid.length; r++)
    {
      for (int c = 0; c < grid[r].length; c++)
      {
        grid[r][c].setSelectedIcon(colorSquare);
      }
    }
  }

  /*
   * Creates color selection buttons so user can change the color of the cells
   * on the grid.
   */
  private void initializeColorSelectors()
  {
    JButton[] colorButtons = new JButton[7];
    String[] colors =
      {"black", "red", "orange", "yellow", "green", "blue", "purple"};
    for (int i = 0; i < colorButtons.length; i++)
    {
      colorButtons[i] = new JButton("");
      ImageIcon coloredSquareIcon =
        new ImageIcon(
          GameOfLife.class.getResource("/images/" + colors[i] + "Square.png"));
      colorButtons[i].setBounds(300 + 15 * i, 530, 15, 16);
      colorButtons[i].setIcon(coloredSquareIcon);
      colorButtons[i].setFocusPainted(false);
      colorButtons[i].setToolTipText(colors[i]);
      colorButtons[i].addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          setSelectedSquareColor(coloredSquareIcon);
        }
      });
      frame.getContentPane().add(colorButtons[i]);
    }
  }

}
