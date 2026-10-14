package edu.unc.comp301.model.enemy;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.Posn;

public interface EnemyMovementStrategy {
  int[] nextMove(Posn enemyPos, Posn heroPos, Board board);
}
