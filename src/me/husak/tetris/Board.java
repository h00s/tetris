package me.husak.tetris;

public class Board {
  // TODO: add ghost piece
  // TODO: add next piece
  private Block[][] blocks = new Block[HEIGHT][WIDTH];
  private RandomGenerator randomGenerator;
  private Tetrimino currentTetrimino;//, ghostTetrimino;
  private int clearedLines;
  private boolean valid;
  private BoardChangeListener boardChangeListener;

  public static final int HEIGHT = 22;
  public static final int WIDTH = 10;

  public Board() {
    clearedLines = 0;
    valid = true;
    randomGenerator = new RandomGenerator();
    spawnTetrimino();
  }

  public interface BoardChangeListener {
    void onBoardChangeListener();
  }

  public void setBoardChangeListener(BoardChangeListener l) {
    boardChangeListener = l;
  }

  public void notifyBoardChange() {
    if (boardChangeListener != null) {
      boardChangeListener.onBoardChangeListener();
    }
  }

  public boolean isValidHorizontalPosition(Tetrimino tetrimino) {
    for (Block block : tetrimino.getBlocks()) {
      if (!isValidHorizontalPosition(block)) {
        return false;
      }
    }
    return true;
  }

  private boolean isValidHorizontalPosition(Block block) {
    return !((block.getX() < 0) ||
        (block.getX() > (WIDTH - 1)) ||
        (blocks[block.getY()][block.getX()] != null));
  }

  public boolean isValidVerticalPosition(Tetrimino tetrimino) {
    for (Block block : tetrimino.getBlocks()) {
      if (!isValidVerticalPosition(block)) {
        return false;
      }
    }
    return true;
  }

  private boolean isValidVerticalPosition(Block block) {
    return !((block.getY() < 0 || block.getX() < 0) ||
        (block.getY() > (HEIGHT - 1) || block.getX() > (WIDTH - 1)) ||
        (blocks[block.getY()][block.getX()] != null));
  }

  private void place(Tetrimino tetrimino) {
    for (Block block : tetrimino.getBlocks()) {
      blocks[block.getY()][block.getX()] = block;
    }
  }

  private void clearLines() {
    for (int i = 0; i < HEIGHT; i++) {
      if (isLineFull(blocks[i])) { // if line is full
        removeLineAt(i);
        i--; // return to position where was line that was removed
        clearedLines++;
      }
    }
  }

  private boolean isLineFull(Block[] blocks) {
    for (Block block : blocks) {
      if (block == null) {
        return false;
      }
    }
    return true;
  }

  private void removeLineAt(int index) {
    for (int j = index; j < (HEIGHT - 1); j++) { // move down all blocks on top of that line
      blocks[j] = blocks[j + 1];
    }
    blocks[HEIGHT - 1] = new Block[WIDTH]; // initialize new line on top
    notifyBoardChange();
  }

  private Point getDropPositionOf(Tetrimino tetrimino) {
    /*for (int i = 0; i < tetrimino.getPosition().getY(); i++) {
      if (blocks[i][tetrimino.getPosition().getX()] == null) {
        boolean validPosition = true;
        for (Block block : tetrimino.getBlocks()) {
          if (blocks[block.getY()][block.getX()] != null) {
            validPosition = false;
            break;
          }
        }
        if (validPosition) {
          return new Point(i, tetrimino.getPosition().getX());
        }
      }
    }*/
    for (int i = tetrimino.getPosition().getY(); i >= 0; i--) {

    }
    return tetrimino.getPosition();
  }

  public void moveCurrentTetriminoLeft() {
    Tetrimino tetrimino = currentTetrimino.moveLeft();
    if (isValidHorizontalPosition(tetrimino)) {
      currentTetrimino = tetrimino;
      notifyBoardChange();
    }
  }

  public void moveCurrentTetriminoRight() {
    Tetrimino tetrimino = currentTetrimino.moveRight();
    if (isValidHorizontalPosition(tetrimino)) {
      currentTetrimino = tetrimino;
      notifyBoardChange();
    }
  }

  public boolean moveCurrentTetriminoDown() {
    Tetrimino tetrimino = currentTetrimino.moveDown();
    if (isValidVerticalPosition(tetrimino)) {
      currentTetrimino = tetrimino;
      notifyBoardChange();
      return true;
    } else {
      place(currentTetrimino);
      clearLines();
      spawnTetrimino();
      notifyBoardChange();
      if (!isValidVerticalPosition(currentTetrimino)) {
        valid = false;
      }
      return false;
    }
  }

  private boolean rotateCurrentTetrimino(boolean clockwise) {
    Tetrimino tetriminos[] = (clockwise) ? currentTetrimino.rotateClockwise() : currentTetrimino.rotateCounterClockwise();
    for (Tetrimino tetrimino : tetriminos) {
      if (isValidVerticalPosition(tetrimino) && isValidHorizontalPosition(tetrimino)) {
        currentTetrimino = tetrimino;
        notifyBoardChange();
        return true;
      }
    }
    return false;
  }

  public boolean rotateCurrentTetriminoClockwise() {
    return rotateCurrentTetrimino(true);
  }

  public boolean rotateCurrentTetriminoCounterClockwise() {
    return rotateCurrentTetrimino(false);
  }

  public void dropCurrentTetriminoDown() {
    //Point dropPosition = getDropPositionOf(currentTetrimino);
    while (moveCurrentTetriminoDown());
  }

  public void spawnTetrimino() {
    currentTetrimino = randomGenerator.nextTetrimino();
    //ghostTetrimino = new Tetrimino(currentTetrimino);
    currentTetrimino.setPosition(WIDTH / 2 - 1, HEIGHT - 2);
  }

  public Tetrimino getCurrentTetrimino() {
    return currentTetrimino;
  }

  public Block[][] getBlocks() {
    return blocks;
  }

  public boolean isValid() {
    return valid;
  }

  public int getClearedLines() {
    return clearedLines;
  }

  @Override
  public String toString() {
    return "";
  }
}
