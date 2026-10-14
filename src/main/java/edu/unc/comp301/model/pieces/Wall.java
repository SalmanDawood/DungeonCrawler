package edu.unc.comp301.model.pieces;

public class Wall extends APiece {

  public Wall() {
    super("Wall", "images/wall.png");
  }

  @Override
  public CollisionResult collide(Piece other) {
    return null;
  }
}
