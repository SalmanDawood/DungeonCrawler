package edu.unc.comp301.model.enemy;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.Posn;

import static java.lang.Integer.compare;

public class ChasingMovementStrategy implements EnemyMovementStrategy {
  @Override
  public int[] nextMove(Posn e, Posn h, Board b) {
    int dr = compare(h.getRow(), e.getRow());
    int dc = compare(h.getCol(), e.getCol());
    if (b.get(new Posn(e.getRow() + dr, e.getCol())) == null) return new int[] {dr, 0};
    if (b.get(new Posn(e.getRow(), e.getCol() + dc)) == null) return new int[] {0, dc};
    return new int[] {0, 0};
  }
}
