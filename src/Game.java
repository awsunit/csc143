import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Manages the game Tetris. Keeps track of the current piece and the grid. Updates the display
 * whenever the state of the game has changed.
 *
 * @author CSC 143
 */
public class Game {

  private Grid grid; // the grid that makes up the Tetris board

  private Tetris display; // the visual for the Tetris game

  private LShape currentPiece; // the current piece that is dropping

  private boolean isOver; // has the game finished?

  /**
   * Creates a Tetris game
   *
   * @param Tetris the display
   */
  public Game(Tetris display) {
    grid = new Grid();
    this.display = display;
    currentPiece = new LShape(1, Grid.WIDTH / 2 - 1, grid);
    isOver = false;
  }

  /**
   * Draws the current state of the game
   *
   * @param g the Graphics context on which to draw
   */
  public void draw(Graphics g) {
    grid.draw(g);
    if (currentPiece != null) {
      currentPiece.draw(g);
    }
  }

  /**
   * Moves the piece in the given direction
   *
   * @param the direction to move
   */
  public void movePiece(Direction direction) {
    if (currentPiece != null) {
      currentPiece.move(direction);
    }
    updatePiece();
    display.update();
    grid.checkRows();
  }

  /**
   * Returns true if the game is over
   */
  public boolean isGameOver() {
    // game is over if the piece occupies the same space as some non-empty
    // part of the grid. Usually happens when a new piece is made
    if (currentPiece == null) {
      return false;
    }

    // check if game is already over
    if (isOver) {
      return true;
    }

    // check every part of the piece
    Point[] p = currentPiece.getLocations();
    for (int i = 0; i < p.length; i++) {
      if (grid.isSet((int) p[i].getX(), (int) p[i].getY())) {
        isOver = true;
        return true;
      }
    }
    return false;
  }

  /**
   * Updates the piece
   */
  private void updatePiece() {
    if (currentPiece == null) {
      // CREATE A NEW PIECE HERE
    }

    // set Grid positions corresponding to frozen piece
    // and then release the piece
    else if (!currentPiece.canMove(Direction.DOWN)) {
      Point[] p = currentPiece.getLocations();
      Color c = currentPiece.getColor();
      for (int i = 0; i < p.length; i++) {
        grid.set((int) p[i].getX(), (int) p[i].getY(), c);
      }
      currentPiece = null;
    }

  }

}
