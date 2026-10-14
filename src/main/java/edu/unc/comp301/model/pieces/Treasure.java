package edu.unc.comp301.model.pieces;

public class Treasure extends APiece {

  private final int value;

  public Treasure() {
    super("Treasure", "images/treasure.png");
    this.value = 10;
  }

  @Override
  public CollisionResult collide(Piece other) {
    return null;
  }

  public int getValue() {
    return value;
  }
}
