package edu.unc.comp301.model.enemy;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.board.Posn;

import java.util.List;
import java.util.Random;

public class RandomMovementStrategy implements EnemyMovementStrategy {
  private final Random rand = new Random();
  private final List<int[]> dirs =
    List.of(new int[] {-1, 0}, new int[] {1, 0}, new int[] {0, -1}, new int[] {0, 1});

  @Override
  public int[] nextMove(Posn e, Posn h, Board b) {
    return dirs.get(rand.nextInt(dirs.size()));
  }
}
