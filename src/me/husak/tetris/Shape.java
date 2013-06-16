package me.husak.tetris;

public class Shape {
  private Point[] points_;

  public Shape(Point[] points) {
    points_ = points;
  }

  public Shape rotateClockwise() {
    for (Point point : points_) {
      point.rotateClockwise();
    }
    return this;
  }

  public Shape rotateCounterClockwise() {
    for (Point point : points_) {
      point.rotateCounterClockwise();
    }
    return this;
  }

  public int minY() {
    int y = points_[0].getCoordinates().getY();
    for (int i = 1; i < points_.length; i++) {
      if (points_[i].getCoordinates().getY() < y) {
        y = points_[i].getCoordinates().getY();
      }
    }
    return y;
  }

  public int minX() {
    int x = points_[0].getCoordinates().getX();
    for (int i = 1; i < points_.length; i++) {
      if (points_[i].getCoordinates().getX() < x) {
        x = points_[i].getCoordinates().getX();
      }
    }
    return x;
  }

  @Override
  public String toString() {
    String output = "[";
    for (Point point : points_) {
      output += point.toString() + ",";
    }
    return output + "]";
  }

}
