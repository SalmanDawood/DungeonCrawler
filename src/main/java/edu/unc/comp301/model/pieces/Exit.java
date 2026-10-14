package edu.unc.comp301.model.pieces;

public class Exit extends APiece {

  public Exit() {
    super("Exit", "images/exit.png");
  }

  @Override
  public CollisionResult collide(Piece other) {
    return null;
  }
}
