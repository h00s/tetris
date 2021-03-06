package me.husak.tetris;

import java.awt.*;

public final class Block extends Point {
  private final Color color;

  public Block(int x, int y, Color color) {
    super(x, y);
    this.color = color;
  }

  public Block(Block block) {
    this(block.getX(), block.getY(), block.getColor());
  }

  public Color getColor() {
    return color;
  }
}
