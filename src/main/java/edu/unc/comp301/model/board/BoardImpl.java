package edu.unc.comp301.model.board;

import edu.unc.comp301.model.enemy.ChasingMovementStrategy;
import edu.unc.comp301.model.enemy.EnemyMovementStrategy;
import edu.unc.comp301.model.enemy.RandomMovementStrategy;
import edu.unc.comp301.model.pieces.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoardImpl implements Board {

  private final int width;
  private final int height;
  private final Piece[][] board;
  private final List<Enemy> enemies = new ArrayList<>();
  private Hero hero;
  private Exit exit;
  private final Random rand = new Random();
  private EnemyMovementStrategy moveStrat = new RandomMovementStrategy();

  public BoardImpl(int width, int height) {
    this.width = width;
    this.height = height;
    this.board = new Piece[height][width];
  }

  public void setHardMode(boolean hard) {
    moveStrat = hard ? new ChasingMovementStrategy() : new RandomMovementStrategy();
  }

  public BoardImpl(Piece[][] initial) {
    this.height = initial.length;
    this.width = initial[0].length;
    this.board = new Piece[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Piece p = initial[r][c];
        if (p != null) {
          board[r][c] = p;
          p.setPosn(new Posn(r, c));
          switch (p) {
            case Hero hero1 -> hero = hero1;
            case Exit exit1 -> exit = exit1;
            case Enemy enemy -> enemies.add(enemy);
            default -> {}
          }
        }
      }
    }
  }

  @Override
  public void init(int enemies, int treasures, int walls) {
    for (int r = 0; r < height; r++) {
      Arrays.fill(board[r], null);
    }
    this.enemies.clear();

    int total = 1 + 1 + enemies + treasures + walls;
    if (total > width * height) {
      throw new IllegalArgumentException("Too many pieces for available spaces");
    }

    hero = new Hero();
    exit = new Exit();
    placeRandom(hero);
    placeRandom(exit);

    for (int i = 0; i < enemies; i++) {
      Enemy e = new Enemy();
      placeRandom(e);
      this.enemies.add(e);
    }

    for (int i = 0; i < treasures; i++) {
      placeRandom(new Treasure());
    }

    for (int i = 0; i < walls; i++) {
      placeRandom(new Wall());
    }

    placeRandom(new Portal());
  }

  private void placeRandom(Piece p) {
    int r, c;
    do {
      r = rand.nextInt(height);
      c = rand.nextInt(width);
    } while (board[r][c] != null);
    board[r][c] = p;
    p.setPosn(new Posn(r, c));
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public Piece get(Posn posn) {
    int r = posn.getRow(), c = posn.getCol();
    if (r < 0 || r >= height || c < 0 || c >= width) {
      return null;
    }
    return board[r][c];
  }

  @Override
  public void set(Piece p, Posn newPos) {
    Posn old = p.getPosn();
    if (old != null) {
      board[old.getRow()][old.getCol()] = null;
    }
    board[newPos.getRow()][newPos.getCol()] = p;
    p.setPosn(newPos);
  }

  @Override
  public CollisionResult moveHero(int row, int col) {
    Posn curr = hero.getPosn();
    int newR = curr.getRow() + row;
    int newC = curr.getCol() + col;

    if (newR < 0
      || newR >= height
      || newC < 0
      || newC >= width
      || board[newR][newC] instanceof Wall) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    }

    Piece target = board[newR][newC];
    board[curr.getRow()][curr.getCol()] = null;
    hero.setPosn(new Posn(newR, newC));
    board[newR][newC] = hero;
    CollisionResult hRes = hero.collide(target);
    if (hRes.getResults() != CollisionResult.Result.CONTINUE) {
      return hRes;
    }

    if (target instanceof Portal) {
      placeRandom(hero);
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    }

    int accumulated = hRes.getPoints();

    for (Enemy e : new ArrayList<>(enemies)) {
      Posn ep = e.getPosn();
      int[] d = moveStrat.nextMove(ep, hero.getPosn(), this);
      int rr = ep.getRow() + d[0];
      int cc = ep.getCol() + d[1];
      if (rr < 0 || rr >= height || cc < 0 || cc >= width) continue;
      Piece destPiece = board[rr][cc];
      if (destPiece instanceof Wall || destPiece instanceof Exit || destPiece instanceof Enemy) {
        continue;
      }
      board[ep.getRow()][ep.getCol()] = null;
      e.setPosn(new Posn(rr, cc));
      board[rr][cc] = e;
      CollisionResult eRes = e.collide(destPiece);
      if (eRes.getResults() == CollisionResult.Result.GAME_OVER) {
        return new CollisionResult(accumulated, CollisionResult.Result.GAME_OVER);
      }
    }

    return new CollisionResult(accumulated, CollisionResult.Result.CONTINUE);
  }
}
