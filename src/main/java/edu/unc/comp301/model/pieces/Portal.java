package edu.unc.comp301.model.pieces;

public class Portal extends APiece {
  public Portal() {
    super("Portal", "images/portal.png");
  }

  @Override
  public CollisionResult collide(Piece other) {
    return new CollisionResult(0, CollisionResult.Result.CONTINUE);
  }
}
